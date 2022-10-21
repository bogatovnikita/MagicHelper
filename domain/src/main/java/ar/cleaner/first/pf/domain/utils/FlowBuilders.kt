package ar.cleaner.first.pf.domain.utils

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlin.random.Random

fun emulateProgressWorkFlow(): Flow<Int> = flow<Int> {
    delay(500)
    for (i in 0..100) {
        emit(i)
        val randomDelay = Random.nextLong(110) + 30
        delay(randomDelay)
    }
}.cancellable()

suspend inline fun <T> FlowCollector<T>.isWorking(): Boolean = currentCoroutineContext().isActive

suspend fun <T> Flow<T>.pause(
    pauseFlow: StateFlow<Boolean>,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
) = transform {
    if (pauseFlow.value) pauseFlow.first { isPaused ->
        !isPaused
    }
    emit(it)
}.flowOn(dispatcher)

inline fun <T> lazyStateFlow(
    defaultValue: T,
    scope: CoroutineScope,
    crossinline block: suspend ProducerScope<T>.() -> Unit
): StateFlow<T> = callbackFlow {
    block(this)
    awaitClose {
        cancel()
    }
}.stateIn(scope = scope, started = SharingStarted.Lazily, defaultValue)

fun <T> Flow<T>.stateFlowReceiver(
    scope: CoroutineScope,
    defaultValue: T,
    started: SharingStarted? = null
): StateFlow<T> =
    this.flowOn(Dispatchers.IO)
        .stateIn(scope, started = started ?: SharingStarted.Eagerly, defaultValue)

suspend fun FlowCollector<Int>.emitProgressWithDelay(
    delayMillis: Long = 0,
    isRandomDelay: Boolean = true,
    from: Int,
    to: Int
) {
    delay(500)
    for (i in from..to) {
        emit(i)
        val delay = if (isRandomDelay) Random.nextLong(35) + 30
        else delayMillis
        delay(delay)
    }
}
