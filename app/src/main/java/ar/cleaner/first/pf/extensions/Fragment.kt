package ar.cleaner.first.pf.extensions

import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

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

fun Fragment.checkPermissions(vararg permission: String): Boolean {
    return permission.all { perm ->
        ActivityCompat.checkSelfPermission(
            requireContext(),
            perm
        ) == PackageManager.PERMISSION_GRANTED
    }
}

inline fun Fragment.multiplePermissionLauncher(crossinline block: (Map<String, Boolean>) -> Unit) =
    registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        block(it)
    }

inline fun <reified T> Flow<T>.observeEvent(
    lifecycle: Lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    launchInScope: CoroutineScope,
    crossinline block: suspend (T) -> Unit
) {
    this.flowWithLifecycle(lifecycle, minActiveState)
        .onEach {
            block(it)
        }.launchIn(launchInScope)
}

fun Fragment.openSettings(packageName: String? = null) {
    startActivity(
        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", packageName ?: requireContext().packageName, null)
        }
    )
}

fun Fragment.checkWriteSettings(): Boolean = Settings.System.canWrite(requireContext())

fun Fragment.openWriteSettings() {
    val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
    intent.data = Uri.parse("package:" + requireActivity().packageName)
    startActivity(intent)
}

fun Fragment.openManageExternalSettings() {
    startActivity(requireContext().manageExternal())
}
