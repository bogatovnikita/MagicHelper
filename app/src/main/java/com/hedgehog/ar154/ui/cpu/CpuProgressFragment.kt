package com.hedgehog.ar154.ui.cpu

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.hedgehog.ar154.R
import com.hedgehog.ar154.databinding.FragmentProgressBinding
import com.hedgehog.ar154.ui.ads.showAds
import com.hedgehog.ar154.utils.BatInfoReceiver
import com.hedgehog.ar154.utils.MenuItems
import com.hedgehog.ar154.utils.NativeProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CpuProgressFragment(
    private val onComplete: Fragment.(
        menuItem: MenuItems,
        data: Any?,
        simpleData: String?
    ) -> Unit
) : Fragment(R.layout.fragment_progress) {

    private val binding: FragmentProgressBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.tvOptimization.text = getString(MenuItems.cooling.title)
        cooling()
    }

    private fun cooling() {
        startPercents()
        lifecycleScope.launch(Dispatchers.Default) {
            delay(8000)
            withContext(Dispatchers.Main) {
                binding.apply {
                    showAds(tvOptimization,
                        tvPercents,
                        ivDone){
                        goNext()
                    }
                }
            }
        }
    }

    private fun startPercents() {
        lifecycleScope.launch(Dispatchers.Default) {
            val step = 7000 / 100
            for (i in 0 .. 100) {
                withContext(Dispatchers.Main) {
                    binding.tvPercents.text = getString(R.string.d_percents, i)
                }
                delay(step.toLong())
            }
        }
    }

    private fun goNext(){
        val data = cpuData()
        onComplete(MenuItems.cooling, null, data)
    }

    private fun cpuData(): String {
        val data = BatInfoReceiver.getBatteryInfo().value.toString()
        BatInfoReceiver.updateInfo()
        NativeProvider.cpu(requireContext())
        return data
    }

}