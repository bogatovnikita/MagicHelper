package ar.cleaner.first.pf.ui.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import ar.cleaner.first.pf.R
import ar.cleaner.first.pf.databinding.LayoutRequestForBluetoothPermissionBinding
import ar.cleaner.first.pf.databinding.LayoutRequestForPermissionBinding
import ar.cleaner.first.pf.databinding.LayoutRequestForReadPermissionBinding
import ar.cleaner.first.pf.extensions.openAccessUsageSettings
import ar.cleaner.first.pf.extensions.openWriteSettings

class DialogReadPermission : DialogFragment() {

    private var _binding: LayoutRequestForReadPermissionBinding? = null
    private val binding get() = _binding!!

    private var callBackDialogPermission: CallBackDialogPermission? = null
    fun addCallBackDialogPermissionWriteSetting(callBackDialogPermission: CallBackDialogPermission) {
        this.callBackDialogPermission = callBackDialogPermission
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LayoutRequestForReadPermissionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogStyle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            crossExitIv.setOnClickListener {
                callBackDialogPermission?.crossExitIvClick(isClick = true)
                dismiss()
            }
            allowBtn.setOnClickListener {
                callBackDialogPermission?.crossAllowBtnClick(isClick = true)
                dismiss()
            }
        }
    }

    interface CallBackDialogPermission {
        fun crossExitIvClick(isClick: Boolean)
        fun crossAllowBtnClick(isClick: Boolean)
    }
}

