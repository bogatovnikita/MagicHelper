package ar.cleaner.second.pf.utils.events

/**
 * Base-interface for UI-single-events
 */
interface BaseEvent

/**
 * Default UI-events
 */
abstract class ToastEvent : BaseEvent {
    abstract val msg: String
}

data class SnackbarEvent(override val msg: String) : ToastEvent()
