package org.matrix.android.sdk.api.session.livekitcall

import org.matrix.android.sdk.api.session.room.model.call.CallAnswerContent
import org.matrix.android.sdk.api.session.room.model.call.CallAssertedIdentityContent
import org.matrix.android.sdk.api.session.room.model.call.CallHangupContent
import org.matrix.android.sdk.api.session.room.model.call.CallNegotiateContent
import org.matrix.android.sdk.api.session.room.model.call.CallRejectContent
import org.matrix.android.sdk.api.session.room.model.call.CallSelectAnswerContent
import org.matrix.android.sdk.api.session.room.model.livekitcall.LivekitCallInviteContent

interface LivekitCallListener {
    /**
     * Called when there is an incoming call within the room.
     */
    fun onCallInviteReceived(mxCall: MxLivekitCall, callInviteContent: LivekitCallInviteContent)

    /**
     * An outgoing call is started.
     */
    fun onCallAnswerReceived(callAnswerContent: CallAnswerContent)

    /**
     * Called when a called has been hung up.
     */
    fun onCallHangupReceived(callHangupContent: CallHangupContent)

    /**
     * Called when a called has been rejected.
     */
    fun onCallRejectReceived(callRejectContent: CallRejectContent)

    /**
     * Called when an answer has been selected.
     */
    fun onCallSelectAnswerReceived(callSelectAnswerContent: CallSelectAnswerContent)

    /**
     * Called when a negotiation is sent.
     */
    fun onCallNegotiateReceived(callNegotiateContent: CallNegotiateContent)

    /**
     * Called when the call has been managed by an other session.
     */
    fun onCallManagedByOtherSession(callId: String)

    /**
     * Called when an asserted identity event is received.
     */
    fun onCallAssertedIdentityReceived(callAssertedIdentityContent: CallAssertedIdentityContent)
}
