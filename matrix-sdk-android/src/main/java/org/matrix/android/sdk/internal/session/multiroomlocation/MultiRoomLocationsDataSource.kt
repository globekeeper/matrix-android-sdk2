package org.matrix.android.sdk.internal.session.multiroomlocation

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.zhuinden.monarchy.Monarchy
import io.realm.kotlin.where
import org.matrix.android.sdk.api.util.Optional
import org.matrix.android.sdk.api.util.toOptional
import org.matrix.android.sdk.internal.database.mapper.UserLocationSummary
import org.matrix.android.sdk.internal.database.mapper.asDomain
import org.matrix.android.sdk.internal.database.model.location.UserLocationEntity
import org.matrix.android.sdk.internal.database.model.location.UserLocationEntityFields
import org.matrix.android.sdk.internal.di.SessionDatabase
import javax.inject.Inject

internal class MultiRoomLocationsDataSource @Inject constructor(
    @SessionDatabase private val monarchy: Monarchy
) {
    fun getMultiRoomLocationsLive(userIds: List<String>): LiveData<Optional<UserLocationSummary>> {
        val liveData = monarchy.findAllMappedWithChanges(
            { realm ->
                realm
                    .where<UserLocationEntity>()
                    .`in`(UserLocationEntityFields.USER_ID, userIds.distinct().toTypedArray())
            },
            {
                it.asDomain()
            }
        )
        return Transformations.map(liveData) { results ->
            results.firstOrNull().toOptional()
        }
    }
}
