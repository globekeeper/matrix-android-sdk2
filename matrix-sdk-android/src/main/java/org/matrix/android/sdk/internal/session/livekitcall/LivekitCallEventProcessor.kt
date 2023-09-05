package org.matrix.android.sdk.internal.session.livekitcall

import io.realm.Realm
import org.matrix.android.sdk.api.logger.LoggerTag
import org.matrix.android.sdk.api.session.events.model.Event
import org.matrix.android.sdk.api.session.events.model.EventType
import org.matrix.android.sdk.internal.database.model.EventInsertType
import org.matrix.android.sdk.internal.session.EventInsertLiveProcessor
import org.matrix.android.sdk.internal.session.SessionScope
import timber.log.Timber
import javax.inject.Inject

private val loggerTag = LoggerTag("LivekitCallEventProcessor", LoggerTag.VOIP)

@SessionScope
internal class LivekitCallEventProcessor @Inject constructor(private val callSignalingHandler: LivekitCallSignalingHandler) :
    EventInsertLiveProcessor {

    private val allowedTypes = listOf(
        EventType.GK_CALL_ANSWER,
        EventType.GK_CALL_SELECT_ANSWER,

        EventType.CALL_REJECT,
        EventType.CALL_HANGUP,

        EventType.GK_CALL_REJECT,
        EventType.GK_CALL_INVITE,
        EventType.GK_CALL_HANGUP,
        EventType.ENCRYPTED,
    )

    private val eventsToPostProcess = mutableListOf<Event>()

    override fun shouldProcess(eventId: String, eventType: String, insertType: EventInsertType): Boolean {
        if (insertType != EventInsertType.INCREMENTAL_SYNC) {
            return false
        }
        return allowedTypes.contains(eventType)
    }

    override fun process(realm: Realm, event: Event) {
        eventsToPostProcess.add(event)
    }

    fun shouldProcessFastLane(eventType: String): Boolean {
        return eventType == EventType.GK_CALL_INVITE
    }

    fun processFastLane(event: Event) {
        dispatchToCallSignalingHandlerIfNeeded(event)
    }

    override suspend fun onPostProcess() {
        eventsToPostProcess.forEach {
            dispatchToCallSignalingHandlerIfNeeded(it)
        }
        eventsToPostProcess.clear()
    }

    private fun dispatchToCallSignalingHandlerIfNeeded(event: Event) {
        event.roomId ?: return Unit.also {
            Timber.tag(loggerTag.value).w("Event with no room id ${event.eventId}")
        }
        callSignalingHandler.onCallEvent(event)
    }
}