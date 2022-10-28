package ar.cleaner.first.pf.extensions

import android.app.AppOpsManager
import android.content.Context
import android.content.pm.PackageManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

inline fun <reified T> Flow<T>.observeWhenResumed(
    lifecycleScope: LifecycleCoroutineScope,
    collector: FlowCollector<T>
) {
    lifecycleScope.launchWhenResumed {
        this@observeWhenResumed.collect(collector)
    }
}

val Fragment.fragmentLifecycleScope
    get() = this.viewLifecycleOwner.lifecycleScope

fun Fragment.checkUsageStatsAllowed(): Boolean {
    return try {
        val packageManager = requireContext().packageManager
        val applicationInfo =
            packageManager.getApplicationInfo(context!!.packageName, 0)
        val appOpsManager =
            requireContext().getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode: Int = appOpsManager.checkOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            applicationInfo.uid,
            applicationInfo.packageName
        )
        mode == AppOpsManager.MODE_ALLOWED
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
}

fun Fragment.openAccessUsageSettings() {
    startActivity(requireContext().usageAccessSettings())
}