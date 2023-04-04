package org.matrix.android.sdk.internal.session.multiroomlocation

import androidx.lifecycle.LiveData
import com.zhuinden.monarchy.Monarchy
import io.realm.kotlin.where
import org.matrix.android.sdk.internal.database.mapper.UserLocationSummary
import org.matrix.android.sdk.internal.database.mapper.asDomain
import org.matrix.android.sdk.internal.database.model.location.UserLocationEntity
import org.matrix.android.sdk.internal.database.model.location.UserLocationEntityFields
import org.matrix.android.sdk.internal.di.SessionDatabase
import javax.inject.Inject

internal class MultiRoomLocationsDataSource @Inject constructor(
    @SessionDatabase private val monarchy: Monarchy
) {
    fun getMultiRoomLocationsLive(userIds: List<String>): LiveData<List<UserLocationSummary>> {
        return monarchy.findAllMappedWithChanges(
            { realm ->
                realm
                    .where<UserLocationEntity>()
                    .`in`(UserLocationEntityFields.USER_ID, userIds.distinct().toTypedArray())
            },
            {
                it.asDomain()
            }
        )
    }

    fun getMultiRoomLocations(userIds: List<String>): List<UserLocationSummary> {
        return monarchy.fetchAllMappedSync(
            { realm ->
                realm
                    .where<UserLocationEntity>()
                    .`in`(UserLocationEntityFields.USER_ID, userIds.distinct().toTypedArray())
            },
            {
                it.asDomain()
            }
        )
    }
}
