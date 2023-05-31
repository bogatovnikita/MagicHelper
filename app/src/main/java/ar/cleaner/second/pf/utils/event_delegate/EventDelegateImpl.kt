package ar.cleaner.second.pf.utils.event_delegate

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

class EventDelegateImpl<E> : EventDelegate<E> {

    override val eventsChannel: Channel<E> = Channel(onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override val eventFlow: Flow<E> = eventsChannel.receiveAsFlow()

    override fun sendEvent(event: E) {
        eventsChannel.trySend(event)
    }
}
