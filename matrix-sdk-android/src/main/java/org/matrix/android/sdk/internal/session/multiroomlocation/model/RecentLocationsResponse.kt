package org.matrix.android.sdk.internal.session.multiroomlocation.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.matrix.android.sdk.api.session.sync.model.LocationSync

@JsonClass(generateAdapter = true)
data class RecentLocationsResponse(
    @Json(name = "recent_locations") val  recentLocations: Map<String, LocationSync>
)
