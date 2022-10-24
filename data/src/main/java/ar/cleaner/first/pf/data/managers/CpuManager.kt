package ar.cleaner.first.pf.data.managers

import android.app.Application
import android.content.Context
import ar.cleaner.first.pf.domain.models.App
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.random.Random

class CpuManager @Inject constructor(
    private val context: Application,
    private val defaultScope: CoroutineScope
) {

    fun getCpuTemperature(isOptimized: Boolean): Double {
        return if (isOptimized) Random.nextDouble(25.0, 30.0)
        else Random.nextDouble(40.2, 45.3)
    }

    fun getAppsInBackground(): Flow<List<App>> {
        TODO()
    }
}