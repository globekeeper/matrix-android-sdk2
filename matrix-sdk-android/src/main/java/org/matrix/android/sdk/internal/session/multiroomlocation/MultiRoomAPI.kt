package org.matrix.android.sdk.internal.session.multiroomlocation

import org.matrix.android.sdk.internal.network.NetworkConstants
import org.matrix.android.sdk.internal.session.multiroomlocation.model.RecentLocationsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MultiRoomAPI {
    /**
     * Fetch all recent locations of room members since after joining the room
     * some of them will be outdated and will not be fetched because of timestamp in the filter
     * of the sync
     */
    @GET(NetworkConstants.URI_API_PREFIX_PATH_R0 + "rooms/{roomId}/location_sync")
    suspend fun recentLocationSync(
        @Path("roomId") roomId: String
    ): RecentLocationsResponse
}
