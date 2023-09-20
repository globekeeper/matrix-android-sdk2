package org.matrix.android.sdk.api.session.room.model.ptt

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Class representing the EventType.PTT state event content.
 */
@JsonClass(generateAdapter = true)
data class PTTContent(@Json(name = "enabled") val enabled: Boolean)
