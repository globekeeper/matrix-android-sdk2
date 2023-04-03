package org.matrix.android.sdk.internal.database.mapper

import org.matrix.android.sdk.internal.database.model.location.UserLocationEntity

internal object UserLocationEntityMapper {

    fun map(locationEntity: UserLocationEntity): UserLocationSummary {
        return UserLocationSummary(
            userId = locationEntity.userId,
            locationContent = locationEntity.locationContent,
            locationTimestamp = locationEntity.locationTimestamp,
            panicContent = locationEntity.panicContent,
            panicTimestamp = locationEntity.panicTimestamp
        )
    }
}

internal fun UserLocationEntity.asDomain(): UserLocationSummary {
    return UserLocationEntityMapper.map(this)
}

data class UserLocationSummary constructor(
    val userId: String ,
    val locationContent: String? = null,
    val locationTimestamp: Long? = null,
    val panicContent: String? = null,
    val panicTimestamp: Long? = null,
)
