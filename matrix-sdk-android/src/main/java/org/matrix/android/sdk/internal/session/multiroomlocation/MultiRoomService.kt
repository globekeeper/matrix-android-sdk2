package org.matrix.android.sdk.internal.session.multiroomlocation

import androidx.lifecycle.LiveData
import org.matrix.android.sdk.internal.database.mapper.UserLocationSummary

interface MultiRoomService {
    fun getLiveUserLocations(userIds: List<String>): LiveData<List<UserLocationSummary>>

    fun getUserLocations(userIds: List<String>): List<UserLocationSummary>

    suspend fun doLocationSyncIfNeeded(roomId: String): Boolean
}
