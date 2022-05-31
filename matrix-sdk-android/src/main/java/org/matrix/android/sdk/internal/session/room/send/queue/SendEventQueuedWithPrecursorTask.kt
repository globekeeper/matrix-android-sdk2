/*
 * Copyright 2020 The Matrix.org Foundation C.I.C.
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

package org.matrix.android.sdk.internal.session.room.send.queue

import kotlinx.coroutines.withTimeoutOrNull
import org.matrix.android.sdk.api.session.crypto.CryptoService
import org.matrix.android.sdk.api.session.events.model.Event
import org.matrix.android.sdk.internal.crypto.tasks.SendEventTask
import org.matrix.android.sdk.internal.session.room.send.CancelSendTracker
import org.matrix.android.sdk.internal.session.room.send.LocalEchoRepository

internal class SendEventQueuedWithPrecursorTask(
    event: Event,
    encrypt: Boolean,
    sendEventTask: SendEventTask,
    cryptoService: CryptoService,
    localEchoRepository: LocalEchoRepository,
    cancelSendTracker: CancelSendTracker,
    private val precursorTimeout: Long,
    val precursor: suspend (Event) -> Event
) : SendEventQueuedTask(event, encrypt, sendEventTask, cryptoService, localEchoRepository, cancelSendTracker) {

    override suspend fun doExecute() {
        withTimeoutOrNull(precursorTimeout) {
            precursor(event)
        }?.also {
            //TODO GK checks if its the same event (only not critical changes allowed)
            event = it
        }
        super.doExecute()
    }
}
