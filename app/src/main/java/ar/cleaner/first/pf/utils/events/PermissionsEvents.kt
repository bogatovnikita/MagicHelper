package ar.cleaner.first.pf.utils.events

interface PermissionsEvent : BaseEvent

data class RuntimePermission(val permissions: List<String>) : PermissionsEvent
