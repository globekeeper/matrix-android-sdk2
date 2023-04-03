package org.matrix.android.sdk.internal.session.multiroomlocation

import androidx.lifecycle.LiveData
import org.matrix.android.sdk.api.util.Optional
import org.matrix.android.sdk.internal.database.mapper.UserLocationSummary
import org.matrix.android.sdk.internal.session.SessionScope
import javax.inject.Inject


@SessionScope
internal class DefaultMultiRoomService @Inject constructor(
    private val dataSource: MultiRoomLocationsDataSource
): MultiRoomService {

    override fun getUserLocations(userIds: List<String>): LiveData<Optional<UserLocationSummary>> {
        return dataSource.getMultiRoomLocationsLive(userIds)
    }
}
