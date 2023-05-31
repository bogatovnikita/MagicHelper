package ar.cleaner.second.pf.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

inline fun ViewModel.mainScope(crossinline block: suspend CoroutineScope.() -> Unit): Job {
    return viewModelScope.launch(Dispatchers.Main) {
        block()
    }
}