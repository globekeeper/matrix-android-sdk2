package org.matrix.android.sdk.internal.session.multiroomlocation

import com.zhuinden.monarchy.Monarchy
import org.matrix.android.sdk.api.session.sync.model.LocationSync
import org.matrix.android.sdk.internal.di.SessionDatabase
import org.matrix.android.sdk.internal.network.GlobalErrorReceiver
import org.matrix.android.sdk.internal.network.executeRequest
import org.matrix.android.sdk.internal.session.sync.handler.MultiRoomSyncHandler
import org.matrix.android.sdk.internal.task.Task
import org.matrix.android.sdk.internal.util.awaitTransaction
import timber.log.Timber
import javax.inject.Inject

internal interface GKSyncLocationTask : Task<GKSyncLocationTask.Params, Boolean> {
    data class Params(val roomId: String)
}

internal class DefaultGKSyncLocationTask @Inject constructor(
    private val multiRoomPI: MultiRoomAPI,
    private val multiRoomSyncHandler: MultiRoomSyncHandler,
    @SessionDatabase private val monarchy: Monarchy,
    private val globalErrorReceiver: GlobalErrorReceiver
) : GKSyncLocationTask {

    override suspend fun execute(params: GKSyncLocationTask.Params): Boolean {
        val locationResponse = try {
            executeRequest(globalErrorReceiver) {
                multiRoomPI.recentLocationSync(params.roomId)
            }
        } catch (failure: Throwable) {
            Timber.e(GKSyncLocationTask::class.java.name, failure)
            return false
        }

        handleLocations(locationResponse.recentLocations)
        return true
    }

    private suspend fun handleLocations(recentLocations: Map<String, LocationSync>) {
        monarchy.awaitTransaction {
            multiRoomSyncHandler.handle(it, recentLocations)
        }
    }
}
