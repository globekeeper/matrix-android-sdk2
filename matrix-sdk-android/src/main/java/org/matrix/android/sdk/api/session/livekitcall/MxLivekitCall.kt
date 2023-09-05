package org.matrix.android.sdk.api.session.livekitcall

import org.matrix.android.sdk.api.session.room.model.call.CallCapabilities
import org.matrix.android.sdk.api.session.room.model.call.EndCallReason
import org.matrix.android.sdk.api.util.Optional

interface MxLivekitCallDetail {
    val callId: String
    val isOutgoing: Boolean
    val roomId: String
    val isVideoCall: Boolean
    val ourPartyId: String
    val opponentPartyId: Optional<String>?
    val opponentVersion: Int
    val opponentUserId: String
    val capabilities: CallCapabilities?
}

/**
 * Define both an incoming call and on outgoing call.
 */
interface MxLivekitCall : MxLivekitCallDetail {

    companion object {
        const val VOIP_PROTO_VERSION = 1
    }

    var state: LivekitCallState

    /**
     * Pick Up the incoming call.
     * It has no effect on outgoing call.
     */
    fun accept()

    /**
     * This has to be sent by the caller's client once it has chosen an answer.
     */
    fun selectAnswer()

    /**
     * Reject an incoming call.
     */
    fun reject()

    /**
     * End the call.
     */
    fun hangUp(reason: EndCallReason? = null, duration: Long? = null)

    /**
     * Start a call.
     * Send call type (voice, video) to the other participant.
     */
    fun inviteParticipantToCall(callType: String)


    fun addListener(listener: StateListener)
    fun removeListener(listener: StateListener)

    interface StateListener {
        fun onStateUpdate(call: MxLivekitCall)
    }
}
