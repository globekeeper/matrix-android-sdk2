package org.matrix.android.sdk.internal.session.multiroomlocation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.zhuinden.monarchy.Monarchy
import org.matrix.android.sdk.api.util.Optional
import org.matrix.android.sdk.api.util.toOptional
import org.matrix.android.sdk.internal.di.SessionDatabase
import javax.inject.Inject

internal class MultiRoomLocationsDataSource @Inject constructor(
    @SessionDatabase private val monarchy: Monarchy
) {
    // Will fetch data from DB
    fun getMultiRoomLocationsLive(): LiveData<Optional<Boolean>> {
        /*val liveData = monarchy.findAllMappedWithChanges(
            { realm -> RoomSummaryEntity.where(realm, roomId).isNotEmpty(RoomSummaryEntityFields.DISPLAY_NAME) },
            { roomSummaryMapper.map(it) }
        )
        return Transformations.map(liveData) { results ->
            results.firstOrNull().toOptional()
        }*/
        return Transformations.map(MutableLiveData<Boolean>().apply { postValue(true)}) { results ->
            results.toOptional()
        }
    }
}
