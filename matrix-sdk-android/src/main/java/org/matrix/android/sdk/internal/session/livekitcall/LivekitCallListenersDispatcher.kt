package org.matrix.android.sdk.internal.session.livekitcall

import org.matrix.android.sdk.api.extensions.tryOrNull
import org.matrix.android.sdk.api.session.livekitcall.LivekitCallListener
import org.matrix.android.sdk.api.session.livekitcall.MxLivekitCall
import org.matrix.android.sdk.api.session.room.model.call.CallAssertedIdentityContent
import org.matrix.android.sdk.api.session.room.model.call.CallHangupContent
import org.matrix.android.sdk.api.session.room.model.call.CallNegotiateContent
import org.matrix.android.sdk.api.session.room.model.call.CallRejectContent
import org.matrix.android.sdk.api.session.room.model.call.CallSelectAnswerContent
import org.matrix.android.sdk.api.session.room.model.livekitcall.LivekitCallAnswerContent
import org.matrix.android.sdk.api.session.room.model.livekitcall.LivekitCallInviteContent

/**
 * Dispatch each method safely to all listeners.
 */
internal class LivekitCallListenersDispatcher(private val listeners: Set<LivekitCallListener>) : LivekitCallListener {

    override fun onCallInviteReceived(mxCall: MxLivekitCall, callInviteContent: LivekitCallInviteContent) = dispatch {
        it.onCallInviteReceived(mxCall, callInviteContent)
    }

    override fun onCallAnswerReceived(callAnswerContent: LivekitCallAnswerContent) = dispatch {
        it.onCallAnswerReceived(callAnswerContent)
    }

    override fun onCallHangupReceived(callHangupContent: CallHangupContent) = dispatch {
        it.onCallHangupReceived(callHangupContent)
    }

    override fun onCallRejectReceived(callRejectContent: CallRejectContent) = dispatch {
        it.onCallRejectReceived(callRejectContent)
    }

    override fun onCallManagedByOtherSession(callId: String) = dispatch {
        it.onCallManagedByOtherSession(callId)
    }

    override fun onCallSelectAnswerReceived(callSelectAnswerContent: CallSelectAnswerContent) = dispatch {
        it.onCallSelectAnswerReceived(callSelectAnswerContent)
    }

    override fun onCallNegotiateReceived(callNegotiateContent: CallNegotiateContent) = dispatch {
        it.onCallNegotiateReceived(callNegotiateContent)
    }

    override fun onCallAssertedIdentityReceived(callAssertedIdentityContent: CallAssertedIdentityContent) = dispatch {
        it.onCallAssertedIdentityReceived(callAssertedIdentityContent)
    }

    private fun dispatch(lambda: (LivekitCallListener) -> Unit) {
        listeners.toList().forEach {
            tryOrNull {
                lambda(it)
            }
        }
    }
}
