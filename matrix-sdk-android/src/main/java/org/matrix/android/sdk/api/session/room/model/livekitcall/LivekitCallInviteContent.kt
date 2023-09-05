package org.matrix.android.sdk.api.session.room.model.livekitcall

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.matrix.android.sdk.api.session.room.model.call.CallCapabilities
import org.matrix.android.sdk.api.session.room.model.call.CallSignalingContent

/**
 * This event is sent by the caller when they wish to establish a call.
 */
@JsonClass(generateAdapter = true)
data class LivekitCallInviteContent(
        /**
         * Required. A unique identifier for the call.
         */
        @Json(name = "call_id") override val callId: String?,
        /**
         * Required. ID to let user identify remote echo of their own events
         */
        @Json(name = "party_id") override val partyId: String? = null,
        /**
         * Required. The call type
         */
        @Json(name = "type") val type: String?,
        /**
         * Required. The version of the VoIP specification this message adheres to.
         */
        @Json(name = "version") override val version: String?,
        /**
         * Required. The time in milliseconds that the invite is valid for.
         * Once the invite age exceeds this value, clients should discard it.
         * They should also no longer show the call as awaiting an answer in the UI.
         */
        @Json(name = "lifetime") val lifetime: Int?,
        /**
         * The field should be added for all invites where the target is a specific user.
         */
        @Json(name = "invitee") val invitee: String? = null,
        /**
         * Capability advertisement.
         */
        @Json(name = "capabilities") val capabilities: CallCapabilities? = null
) : CallSignalingContent {

    companion object {
        const val VIDEO = "video"
        const val VOICE = "voice"
    }

    fun isVideo() = type?.contains(VIDEO) == true
}
