package org.matrix.android.sdk.internal.database.mapper

import org.matrix.android.sdk.api.session.sync.model.LocationDataEvent
import org.matrix.android.sdk.internal.database.model.location.UserLocationEntity

internal object UserLocationEntityMapper {

    fun map(locationEntity: UserLocationEntity): UserLocationSummary {
        return UserLocationSummary(
            userId = locationEntity.userId,
            location = ContentMapper.map(locationEntity.locationContent)?.let { LocationDataEvent(it, locationEntity.locationTimestamp) },
            panic = ContentMapper.map(locationEntity.panicContent)?.let { LocationDataEvent(it, locationEntity.panicTimestamp) }
        )
    }
}

internal fun UserLocationEntity.asDomain(): UserLocationSummary {
    return UserLocationEntityMapper.map(this)
}

data class UserLocationSummary constructor(
    val userId: String,
    val location: LocationDataEvent? = null,
    val panic: LocationDataEvent? = null,
)
