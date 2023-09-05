package org.matrix.android.sdk.internal.session.livekitcall

import org.matrix.android.sdk.api.session.livekitcall.LivekitCallListener
import org.matrix.android.sdk.api.session.livekitcall.LivekitCallSignalingService
import org.matrix.android.sdk.api.session.livekitcall.MxLivekitCall
import org.matrix.android.sdk.internal.session.SessionScope
import javax.inject.Inject

@SessionScope
internal class DefaultLivekitCallSignalingService @Inject constructor(
    private val callSignalingHandler: LivekitCallSignalingHandler,
    private val mxCallFactory: MxLivekitCallFactory,
    private val activeCallHandler: ActiveLivekitCallHandler,
) : LivekitCallSignalingService {

    override fun createOutgoingCall(roomId: String, otherUserId: String, isVideoCall: Boolean): MxLivekitCall {
        return mxCallFactory.createOutgoingCall(roomId, otherUserId, isVideoCall).also {
            activeCallHandler.addCall(it)
        }
    }

    override fun addCallListener(listener: LivekitCallListener) {
        callSignalingHandler.addCallListener(listener)
    }

    override fun removeCallListener(listener: LivekitCallListener) {
        callSignalingHandler.removeCallListener(listener)
    }

    override fun getCallWithId(callId: String): MxLivekitCall? {
        return activeCallHandler.getCallWithId(callId)
    }

    override fun isThereAnyActiveCall(): Boolean {
        return activeCallHandler.getActiveCallsLiveData().value?.isNotEmpty() == true
    }

    companion object {
        const val CALL_TIMEOUT_MS = 120_000
    }
}
