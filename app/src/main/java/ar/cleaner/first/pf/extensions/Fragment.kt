package ar.cleaner.first.pf.extensions

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