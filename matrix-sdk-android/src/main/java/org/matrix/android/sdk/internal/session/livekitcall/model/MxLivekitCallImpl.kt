package org.matrix.android.sdk.internal.session.livekitcall.model

import org.matrix.android.sdk.api.MatrixConfiguration
import org.matrix.android.sdk.api.logger.LoggerTag
import org.matrix.android.sdk.api.session.call.MxCall
import org.matrix.android.sdk.api.session.events.model.*
import org.matrix.android.sdk.api.session.room.model.call.*
import org.matrix.android.sdk.api.session.room.model.livekitcall.LivekitCallInviteContent
import org.matrix.android.sdk.api.util.Optional
import org.matrix.android.sdk.internal.session.livekitcall.DefaultLivekitCallSignalingService
import org.matrix.android.sdk.api.session.livekitcall.LivekitCallState
import org.matrix.android.sdk.api.session.livekitcall.MxLivekitCall
import org.matrix.android.sdk.api.session.room.model.livekitcall.LivekitCallAnswerContent
import org.matrix.android.sdk.internal.session.room.send.LocalEchoEventFactory
import org.matrix.android.sdk.internal.session.room.send.queue.EventSenderProcessor
import org.matrix.android.sdk.internal.util.time.Clock
import timber.log.Timber
import java.math.BigDecimal

private val loggerTag = LoggerTag("MxLivekitCallImpl", LoggerTag.VOIP)

internal class MxLivekitCallImpl(
    override val callId: String,
    override val isOutgoing: Boolean,
    override val roomId: String,
    private val userId: String,
    override val isVideoCall: Boolean,
    override val ourPartyId: String,
    private val localEchoEventFactory: LocalEchoEventFactory,
    private val eventSenderProcessor: EventSenderProcessor,
    private val matrixConfiguration: MatrixConfiguration,
    private val clock: Clock,
) : MxLivekitCall {

    override var opponentPartyId: Optional<String>? = null
    override var opponentVersion: Int = MxCall.VOIP_PROTO_VERSION
    override lateinit var opponentUserId: String
    override var capabilities: CallCapabilities? = null

    fun updateOpponentData(userId: String, content: CallSignalingContent, callCapabilities: CallCapabilities?) {
        opponentPartyId = Optional.from(content.partyId)
        opponentVersion = content.version?.let { BigDecimal(it).intValueExact() } ?: MxLivekitCall.VOIP_PROTO_VERSION
        opponentUserId = userId
        capabilities = callCapabilities ?: CallCapabilities()
    }

    override var state: LivekitCallState = LivekitCallState.Idle
        set(value) {
            field = value
            dispatchStateChange()
        }

    private val listeners = mutableListOf<MxLivekitCall.StateListener>()

    override fun addListener(listener: MxLivekitCall.StateListener) {
        listeners.add(listener)
    }

    override fun removeListener(listener: MxLivekitCall.StateListener) {
        listeners.remove(listener)
    }

    private fun dispatchStateChange() {
        listeners.forEach {
            try {
                it.onStateUpdate(this)
            } catch (failure: Throwable) {
                Timber.tag(loggerTag.value).d("dispatchStateChange failed for call $callId : ${failure.localizedMessage}")
            }
        }
    }

    init {
        state = if (isOutgoing) {
            LivekitCallState.Idle
        } else {
            // because it's created on reception of an offer
            LivekitCallState.LocalRinging
        }
    }

    override fun inviteParticipantToCall(callType: String) {
        if (!isOutgoing) return
        Timber.tag(loggerTag.value).v("offerSdp $callId")
        state = LivekitCallState.Dialing
        LivekitCallInviteContent(
            callId = callId,
            partyId = ourPartyId,
            lifetime = DefaultLivekitCallSignalingService.CALL_TIMEOUT_MS,
            type = callType,
            version = MxCall.VOIP_PROTO_VERSION.toString()
        )
                .let { createEventAndLocalEcho(type = EventType.GK_CALL_INVITE, roomId = roomId, content = it.toContent()) }
                .also { eventSenderProcessor.postEvent(it) }
    }

    override fun reject() {
        if (opponentVersion < 1) {
            Timber.tag(loggerTag.value).v("Opponent version is less than 1 ($opponentVersion): sending hangup instead of reject")
            hangUp(EndCallReason.USER_HANGUP)
            return
        }
        Timber.tag(loggerTag.value).v("reject $callId")
        CallRejectContent(
            callId = callId,
            partyId = ourPartyId,
            version = MxCall.VOIP_PROTO_VERSION.toString()
        )
                .let { createEventAndLocalEcho(type = EventType.GK_CALL_REJECT, roomId = roomId, content = it.toContent()) }
                .also { eventSenderProcessor.postEvent(it) }
        state = LivekitCallState.Ended(reason = EndCallReason.USER_HANGUP)
    }

    override fun hangUp(reason: EndCallReason?, duration: Long?) {
        Timber.tag(loggerTag.value).v("hangup $callId")
        CallHangupContent(
            callId = callId,
            partyId = ourPartyId,
            reason = reason,
            duration = duration,
            version = MxCall.VOIP_PROTO_VERSION.toString()
        )
                .let { createEventAndLocalEcho(type = EventType.GK_CALL_HANGUP, roomId = roomId, content = it.toContent()) }
                .also { eventSenderProcessor.postEvent(it) }
        state = LivekitCallState.Ended(reason)
    }

    override fun accept() {
        Timber.tag(loggerTag.value).v("accept $callId")
        if (isOutgoing) return
        state = LivekitCallState.Answering
        LivekitCallAnswerContent(
            callId = callId,
            partyId = ourPartyId,
            version = MxCall.VOIP_PROTO_VERSION.toString(),
            capabilities = buildCapabilities()
        )
                .let { createEventAndLocalEcho(type = EventType.GK_CALL_ANSWER, roomId = roomId, content = it.toContent()) }
                .also { eventSenderProcessor.postEvent(it) }
    }

    override fun selectAnswer() {
        Timber.tag(loggerTag.value).v("select answer $callId")
        if (!isOutgoing) return
        // This is an outgoing call, select the remote client that answered.
        if (state != LivekitCallState.Dialing && state !is LivekitCallState.Connected) {
            Timber.tag(loggerTag.value).w("Expected state is CallState.Dialing or CallState.Connected got $state.")
        }
        CallSelectAnswerContent(
            callId = callId,
            partyId = ourPartyId,
            selectedPartyId = opponentPartyId?.getOrNull(),
            version = MxCall.VOIP_PROTO_VERSION.toString()
        )
                .let { createEventAndLocalEcho(type = EventType.GK_CALL_SELECT_ANSWER, roomId = roomId, content = it.toContent()) }
                .also { eventSenderProcessor.postEvent(it) }
    }

    private fun createEventAndLocalEcho(localId: String = LocalEcho.createLocalEchoId(), type: String, roomId: String, content: Content): Event {
        return Event(
            roomId = roomId,
            originServerTs = clock.epochMillis(),
            senderId = userId,
            eventId = localId,
            type = type,
            content = content,
            unsignedData = UnsignedData(age = null, transactionId = localId)
        )
                .also { localEchoEventFactory.createLocalEcho(it) }
    }

    private fun buildCapabilities(): CallCapabilities? {
        return if (matrixConfiguration.supportsCallTransfer) {
            CallCapabilities(true)
        } else {
            null
        }
    }
}