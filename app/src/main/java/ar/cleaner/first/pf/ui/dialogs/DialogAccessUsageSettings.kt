package ar.cleaner.first.pf.ui.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import ar.cleaner.first.pf.R
import ar.cleaner.first.pf.databinding.LayoutRequestForPermissionBinding
import ar.cleaner.first.pf.extensions.openAccessUsageSettings

class DialogAccessUsageSettings : DialogFragment() {

    private var _binding: LayoutRequestForPermissionBinding? = null
    private val binding get() = _binding!!

    override fun onResume() {
        super.onResume()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LayoutRequestForPermissionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogStyle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            crossExitIv.setOnClickListener {  dismiss()}
            cancelBtn.setOnClickListener { dismiss() }
            allowBtn.setOnClickListener{
                openAccessUsageSettings()
                dismiss()
            }
        }
    }
}

