package org.matrix.android.sdk.api.session.room.model.livekitcall

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.matrix.android.sdk.api.session.room.model.call.CallCapabilities
import org.matrix.android.sdk.api.session.room.model.call.CallSignalingContent

/**
 * This event is sent by the callee when they wish to answer the call.
 */
@JsonClass(generateAdapter = true)
data class LivekitCallAnswerContent(
        /**
         * Required. The ID of the call this event relates to.
         */
        @Json(name = "call_id") override val callId: String,
        /**
         * Required. ID to let user identify remote echo of their own events
         */
        @Json(name = "party_id") override val partyId: String? = null,
        /**
         * Required. The version of the VoIP specification this messages adheres to.
         */
        @Json(name = "version") override val version: String?,
        /**
         * Capability advertisement.
         */
        @Json(name = "capabilities") val capabilities: CallCapabilities? = null,
        /**
         * The timestamp of the answered event.
         */
        @Json(name = "origin_server_ts") val originServerTs: Long? = null,
) : CallSignalingContent
