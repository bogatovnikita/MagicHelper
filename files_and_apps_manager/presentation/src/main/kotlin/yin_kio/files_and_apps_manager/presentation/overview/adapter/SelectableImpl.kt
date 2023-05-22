package yin_kio.files_and_apps_manager.presentation.overview.adapter

import android.widget.CheckBox
import file_manager.doman.overview.ui_out.Selectable

class SelectableImpl(
    private val checkBox: CheckBox
) : Selectable{

    override fun setSelected(isSelected: Boolean) {
        checkBox.isChecked = isSelected
    }
}