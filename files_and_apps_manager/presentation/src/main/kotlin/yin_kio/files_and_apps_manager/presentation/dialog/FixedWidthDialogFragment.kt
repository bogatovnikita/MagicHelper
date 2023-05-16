package yin_kio.files_and_apps_manager.presentation.dialog

import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

abstract class FixedWidthDialogFragment(layoutId: Int) : DialogFragment(layoutId) {

    override fun onStart() {
        super.onStart()

        val scale = requireContext().resources.displayMetrics.density
        val pixels = (276 * scale + 0.5f).toInt()

        dialog?.window?.setLayout(
            pixels,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }


}