package ar.cleaner.first.pf.domain.gateways.base

import kotlinx.coroutines.flow.StateFlow

interface BaseRepository {

    val lastOptimizationMillis: StateFlow<Long>
    fun setLastOptimizationMillis(time: Long)

}