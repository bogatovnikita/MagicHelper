package ar.cleaner.first.pf.domain.gateways.boosting

interface BoostStatusRepository {

    fun saveOptimizationStatus()

    fun getOptimizationStatus(): Boolean

    fun getLastOptimizeRam(): Long

    fun saveLastOptimizeRam(value: Long)
}