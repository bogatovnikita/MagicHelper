package ar.cleaner.first.pf.ui.menu

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MenuViewModel() : ViewModel() {

    private val _state: MutableStateFlow<MenuState> = MutableStateFlow(MenuState())
    val state = _state.asStateFlow()

    init {
        _state.value = state.value.copy()
    }
}