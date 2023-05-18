package ar.cleaner.first.pf.data.providers.kill_background_processes

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import ar.cleaner.first.pf.data.providers.apps.AppsProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class KillBackgroundProcessesProviderImpl @Inject constructor(
    private val appsProvider: AppsProvider,
    @ApplicationContext private val context: Context
) : KillBackgroundProcessesProvider {

    override suspend fun killBackGroundProcessesInstalledApps() {
        val am = context.getSystemService(Activity.ACTIVITY_SERVICE) as ActivityManager
        appsProvider.getInstalledApp().forEach { app ->
            am.killBackgroundProcesses(app.packageName)
        }
    }

    override suspend fun killBackGroundProcessesSystemApps() {
        val am = context.getSystemService(Activity.ACTIVITY_SERVICE) as ActivityManager
        appsProvider.getSystemApp().forEach { app ->
            am.killBackgroundProcesses(app.packageName)
        }
    }

}