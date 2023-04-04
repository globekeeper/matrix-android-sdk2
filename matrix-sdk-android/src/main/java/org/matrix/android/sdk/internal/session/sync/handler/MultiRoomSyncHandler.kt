/*
 * Copyright 2021 The Matrix.org Foundation C.I.C.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.matrix.android.sdk.internal.session.sync.handler

import io.realm.Realm
import org.matrix.android.sdk.api.MatrixConfiguration
import org.matrix.android.sdk.api.session.sync.model.LocationSync
import org.matrix.android.sdk.internal.database.mapper.ContentMapper
import org.matrix.android.sdk.internal.database.model.location.UserLocationEntity
import javax.inject.Inject

internal class MultiRoomSyncHandler @Inject constructor(private val matrixConfiguration: MatrixConfiguration) {

    fun handle(realm: Realm, multiRoomSyncResponse: Map<String, LocationSync>?) {
        multiRoomSyncResponse
                ?.forEach {
                    val userId = it.key
                    val location = it.value.location
                    val panic = it.value.panic
                    val locationContent = ContentMapper.map(location?.content)
                    val panicContent = ContentMapper.map(panic?.content)
                    val userLocationEntity = UserLocationEntity(
                        userId = userId,
                        locationContent = locationContent,
                        locationTimestamp = location?.originServerTs,
                        panicContent = panicContent,
                        panicTimestamp = panic?.originServerTs
                    )

                    storeLocationToDB(realm, userLocationEntity)
                }
    }

    /**
     * Store user location to DB.
     */
    private fun storeLocationToDB(realm: Realm, userLocationEntity: UserLocationEntity) =
            realm.copyToRealmOrUpdate(userLocationEntity)
}
