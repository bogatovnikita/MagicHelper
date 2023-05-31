package ar.cleaner.second.pf.utils.event_delegate

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow

interface EventDelegate<E> {

    val eventsChannel: Channel<E>
    val eventFlow: Flow<E>

    fun sendEvent(event: E)
}
