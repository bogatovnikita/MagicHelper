package ar.cleaner.first.pf.ui.cpu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import ar.cleaner.first.pf.R
import ar.cleaner.first.pf.databinding.FragmentProgressBinding
import ar.cleaner.first.pf.ui.ads.showAds
import ar.cleaner.first.pf.utils.BatInfoReceiver
import ar.cleaner.first.pf.utils.MenuItems
import ar.cleaner.first.pf.utils.NativeProvider
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

    private var _binding: FragmentProgressBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProgressBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        binding.tvOptimization.text = getString(MenuItems.cooling.title)
        cooling()
    }

    private fun cooling() {
        startPercents()
        lifecycleScope.launch(Dispatchers.Default) {
            delay(8000)
            withContext(Dispatchers.Main) {
                binding.apply {
                    showAds(){
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
//                    binding.tvPercents.text = getString(R.string.d_percents, i)
                }
                delay(step.toLong())
            }
        }
    }

    private fun goNext(){
        val data = cpuData()
//        onComplete(MenuItems.cooling, null, data)
    }

    private fun cpuData(): String {
        val data = BatInfoReceiver.getBatteryInfo().value.toString()
        BatInfoReceiver.updateInfo()
        NativeProvider.cpu(requireContext())
        return data
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}