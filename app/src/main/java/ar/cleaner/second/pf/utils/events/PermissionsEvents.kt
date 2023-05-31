package ar.cleaner.second.pf.utils.events

interface PermissionsEvent : BaseEvent

data class RuntimePermission(val permissions: List<String>) : PermissionsEvent
