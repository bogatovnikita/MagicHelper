package ar.cleaner.first.pf.domain.repositorys.base

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface BaseRepository {

    val lastOptimizationMillis: StateFlow<Long>
    fun setLastOptimizationMillis(time: Long)

}