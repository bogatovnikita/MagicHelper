package ar.cleaner.second.pf.ui.base_fragment

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment

open class BaseFragment(idLayout: Int): Fragment(idLayout) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        disableBackPressed()
    }

    private fun disableBackPressed(){
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner, object : OnBackPressedCallback(true){
                override fun handleOnBackPressed() {

                }
            }
        )
    }
}