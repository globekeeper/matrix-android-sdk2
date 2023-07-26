package org.matrix.android.sdk.internal.session.multiroomlocation

import androidx.lifecycle.LiveData
import org.matrix.android.sdk.internal.database.mapper.UserLocationSummary
import org.matrix.android.sdk.internal.session.SessionScope
import javax.inject.Inject


@SessionScope
internal class DefaultMultiRoomService @Inject constructor(
    private val dataSource: MultiRoomLocationsDataSource,
    private val gkSyncLocationTask: GKSyncLocationTask,
): MultiRoomService {

    override fun getLiveUserLocations(userIds: List<String>): LiveData<List<UserLocationSummary>> {
        return dataSource.getMultiRoomLocationsLive(userIds)
    }

    override fun getUserLocations(userIds: List<String>): List<UserLocationSummary> {
        return dataSource.getMultiRoomLocations(userIds)
    }
    override suspend fun doLocationSyncIfNeeded(roomId: String): Boolean {
        val syncLocationsParams = GKSyncLocationTask.Params(roomId)
        return gkSyncLocationTask.execute(syncLocationsParams)
    }
}
