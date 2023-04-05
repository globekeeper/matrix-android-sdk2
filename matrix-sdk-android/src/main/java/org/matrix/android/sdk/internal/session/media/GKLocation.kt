package org.matrix.android.sdk.internal.session.media

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class GKLocation(val type: String,
                      val time: Long,
                      @Json(name = "geometry") val geometry: Geometry,
                      @Json(name = "properties") val properties: Properties) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Geometry(val type: String, val coordinates: List<String>) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Properties(val name: String?,
                      val visible: Boolean?,
                      val vAccuracy: Float?,
                      val hAccuracy: Float?,
                      val speed: Float?,
                      val altitude: Double?) : Parcelable
