package ar.cleaner.first.pf.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import ar.cleaner.first.pf.R
import ar.cleaner.first.pf.databinding.FragmentMenuBinding
import ar.cleaner.first.pf.utils.MenuItems
import ar.cleaner.first.pf.utils.OptimizationProvider

class MenuFragment(
    val onBattery: Fragment.() -> Unit,
    val onBoost: Fragment.() -> Unit,
    val onCpu: Fragment.() -> Unit,
    val onJunk: Fragment.() -> Unit,
) : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), R.color.dark_blue)

        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.adapter = MenuAdapter(OptimizationProvider) {
            when (it) {
                MenuItems.Boost -> onBoost()
                MenuItems.BatteryPower -> onBattery()
                MenuItems.CoolingCpu -> onCpu()
                MenuItems.CleaningJunk -> onJunk()
            }
        }
        val infoRam = OptimizationProvider.getRamUsageInfo()
        val infoStorage = OptimizationProvider.getMemoryStorage()
        with(binding) {
            ramProgressBar.progressPercent = infoRam.percent.toFloat()
            ramPercentTv.text = getString(R.string.percent_d, infoRam.percent)
            descriptionRamTv.text =
                getString(R.string._2f_gb_2f_gb, infoRam.usageGb, infoRam.totalGb)

            storageProgressBar.progressPercent = infoStorage.percent.toFloat()
            storagePercentTv.text = getString(R.string.percent_d, infoStorage.percent)
            descriptionStorageTv.text = getString(
                R.string._2f_gb_2f_gb,
                infoStorage.occupiedStorageMemory,
                infoStorage.totalStorageMemory
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}