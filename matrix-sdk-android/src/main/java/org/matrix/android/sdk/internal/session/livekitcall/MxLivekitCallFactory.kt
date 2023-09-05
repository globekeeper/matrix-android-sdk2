package org.matrix.android.sdk.internal.session.livekitcall

import org.matrix.android.sdk.api.MatrixConfiguration
import org.matrix.android.sdk.api.session.call.CallIdGenerator
import org.matrix.android.sdk.api.session.livekitcall.MxLivekitCall
import org.matrix.android.sdk.api.session.room.model.call.CallCapabilities
import org.matrix.android.sdk.api.session.room.model.call.CallSignalingContent
import org.matrix.android.sdk.api.session.room.model.livekitcall.LivekitCallInviteContent
import org.matrix.android.sdk.internal.di.DeviceId
import org.matrix.android.sdk.internal.di.UserId
import org.matrix.android.sdk.internal.session.livekitcall.model.MxLivekitCallImpl
import org.matrix.android.sdk.internal.session.profile.GetProfileInfoTask
import org.matrix.android.sdk.internal.session.room.send.LocalEchoEventFactory
import org.matrix.android.sdk.internal.session.room.send.queue.EventSenderProcessor
import org.matrix.android.sdk.internal.util.time.Clock
import javax.inject.Inject

internal class MxLivekitCallFactory @Inject constructor(
        @DeviceId private val deviceId: String?,
        private val localEchoEventFactory: LocalEchoEventFactory,
        private val eventSenderProcessor: EventSenderProcessor,
        private val matrixConfiguration: MatrixConfiguration,
        @UserId private val userId: String,
        private val clock: Clock,
) {

    fun createIncomingCall(roomId: String, opponentUserId: String, content: LivekitCallInviteContent): MxLivekitCall? {
        content.callId ?: return null
        return MxLivekitCallImpl(
            callId = content.callId,
            isOutgoing = false,
            roomId = roomId,
            userId = userId,
            ourPartyId = deviceId ?: "",
            isVideoCall = content.isVideo(),
            localEchoEventFactory = localEchoEventFactory,
            eventSenderProcessor = eventSenderProcessor,
            matrixConfiguration = matrixConfiguration,
            clock = clock,
        ).apply {
            updateOpponentData(opponentUserId, content, content.capabilities)
        }
    }

    fun createOutgoingCall(roomId: String, opponentUserId: String, isVideoCall: Boolean): MxLivekitCall {
        return MxLivekitCallImpl(
                callId = CallIdGenerator.generate(),
                isOutgoing = true,
                roomId = roomId,
                userId = userId,
                ourPartyId = deviceId ?: "",
                isVideoCall = isVideoCall,
                localEchoEventFactory = localEchoEventFactory,
                eventSenderProcessor = eventSenderProcessor,
                matrixConfiguration = matrixConfiguration,
                clock = clock,
        ).apply {
            // Setup with this userId, might be updated when processing the Answer event
            this.opponentUserId = opponentUserId
        }
    }

    fun updateOutgoingCallWithOpponentData(
            call: MxLivekitCall,
            userId: String,
            content: CallSignalingContent,
            callCapabilities: CallCapabilities?
    ) {
        (call as? MxLivekitCallImpl)?.updateOpponentData(userId, content, callCapabilities)
    }
}
