package org.matrix.android.sdk.api.session.livekitcall

import org.matrix.android.sdk.api.session.room.model.call.EndCallReason

sealed class LivekitCallState {

    /** Idle, setting up objects. */
    object Idle : LivekitCallState()

    /**
     * CreateOffer. Intermediate state between Idle and Dialing.
     */
    object CreateOffer : LivekitCallState()

    /** Dialing.  Outgoing call is signaling the remote peer */
    object Dialing : LivekitCallState()

    /** Local ringing. Incoming call offer received */
    object LocalRinging : LivekitCallState()

    /** Answering.  Incoming call is responding to remote peer */
    object Answering : LivekitCallState()

    /**
     * Connected. Incoming/Outgoing call
     * */
    object Connected : LivekitCallState()

    /** Ended. Incoming/Outgoing call, the call is terminated */
    data class Ended(val reason: EndCallReason? = null) : LivekitCallState()
}
