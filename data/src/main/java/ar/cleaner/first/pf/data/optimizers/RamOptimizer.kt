package ar.cleaner.first.pf.data.optimizers

import android.annotation.SuppressLint
import android.app.Application
import ar.cleaner.first.pf.data.extensions.activityManager
import ar.cleaner.first.pf.data.extensions.getAppsOnPhone
import ar.cleaner.first.pf.data.optimizers.base.BaseOptimizer
import ar.cleaner.first.pf.data.preferences.PreferencesManager
import ar.cleaner.first.pf.domain.mapper.asPercents
import ar.cleaner.first.pf.domain.models.BackgroundApp
import ar.cleaner.first.pf.domain.utils.emulateProgressWorkFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RamOptimizer @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val context: Application
) : BaseOptimizer() {
    override val lastOptimizationWorkMillis: StateFlow<Long>
        get() = preferencesManager.ramOptimizationFlow

    override fun setLastOptimizationTime(time: Long) {
        preferencesManager.ramLastOptimizationMillis = time
    }

    fun emulateOptimization(): Flow<Int> = emulateProgressWorkFlow()

    @SuppressLint("MissingPermission")
    fun runOptimization(backgroundApps: List<BackgroundApp>): Flow<Int> = flow {
        backgroundApps.forEachIndexed { i, app ->
            context.activityManager.killBackgroundProcesses(app.packageName)
        }
    }

    @SuppressLint("MissingPermission")
    fun fastOptimization(): Flow<Int> = flow {
        val backgroundAppList = context.getAppsOnPhone()
        val size = backgroundAppList.indices.last
        backgroundAppList.forEachIndexed { i, app ->
            context.activityManager.killBackgroundProcesses(app.packageName)
            delay(50)
            emit(i.asPercents(size))
        }
    }
}