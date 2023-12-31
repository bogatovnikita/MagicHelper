package ar.cleaner.second.pf.ui.temperature

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ar.cleaner.second.pf.ads.appShowAds
import ar.cleaner.first.pf.domain.usecases.temperature.TemperatureOptimizerUseCase
import ar.cleaner.second.pf.R
import ar.cleaner.second.pf.databinding.FragmentProgressBinding
import ar.cleaner.second.pf.ui.base_fragment.BaseFragment
import ar.cleaner.second.pf.ui.progress.ActionsAdapter
import by.kirich1409.viewbindingdelegate.viewBinding
import com.yin_kio.ads.preloadAd
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class TemperatureProgressFragment : BaseFragment(R.layout.fragment_progress) {

    private val binding: FragmentProgressBinding by viewBinding()

    @Inject
    lateinit var temperatureOptimizerUseCase: TemperatureOptimizerUseCase
    private var scanIsDone = false

    override fun onResume() {
        super.onResume()
        if (scanIsDone) scanIsDone()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preloadAd()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val strings = resources.getStringArray(R.array.progress_temperature).toList()
        val adapter = ActionsAdapter(strings)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
        val repeat = strings.size
        lifecycleScope.launch(Dispatchers.Default) {
            repeat(repeat) {
                delay(TimeUnit.SECONDS.toMillis(8) / repeat)
                withContext(Dispatchers.Main) {
                    adapter.removeFirst()
                }
            }
            scanIsDone = true
            if (scanIsDone) scanIsDone()
        }
    }

    private fun scanIsDone() {
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                temperatureOptimizerUseCase.saveTimeTemperatureOptimization()
                delay(500)
                withContext(Dispatchers.Main) {
                    binding.recyclerView.visibility = View.GONE
                    binding.isDoneTv.visibility = View.VISIBLE
                }
                delay(1000)
                withContext(Dispatchers.Main) { goScreenResult() }
            }
        }
    }

    private fun goScreenResult() {
        appShowAds {
            findNavController().navigate(R.id.action_to_temperatureResultListFragment)
        }
    }

}