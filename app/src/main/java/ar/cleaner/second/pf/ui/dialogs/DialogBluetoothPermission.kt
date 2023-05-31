package ar.cleaner.second.pf.ui.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import ar.cleaner.second.pf.R
import ar.cleaner.second.pf.databinding.LayoutRequestForBluetoothPermissionBinding

class DialogBluetoothPermission : DialogFragment() {

    private var _binding: LayoutRequestForBluetoothPermissionBinding? = null
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
        _binding = LayoutRequestForBluetoothPermissionBinding.inflate(inflater, container, false)
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

