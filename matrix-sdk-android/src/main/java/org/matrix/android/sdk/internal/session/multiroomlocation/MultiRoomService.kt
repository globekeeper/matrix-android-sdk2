package org.matrix.android.sdk.internal.session.multiroomlocation

import androidx.lifecycle.LiveData
import org.matrix.android.sdk.api.util.Optional

interface MultiRoomService {
    fun getUserLocations(userIds: List<String>): LiveData<Optional<Boolean>>
}
