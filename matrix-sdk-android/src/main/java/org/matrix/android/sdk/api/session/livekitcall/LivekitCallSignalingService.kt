package org.matrix.android.sdk.api.session.livekitcall

interface LivekitCallSignalingService {

    /**
     * Create an outgoing call.
     */
    fun createOutgoingCall(roomId: String, otherUserId: String, isVideoCall: Boolean): MxLivekitCall

    fun addCallListener(listener: LivekitCallListener)

    fun removeCallListener(listener: LivekitCallListener)

    fun getCallWithId(callId: String): MxLivekitCall?

    fun isThereAnyActiveCall(): Boolean
}
