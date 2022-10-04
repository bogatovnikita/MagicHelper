package com.hedgehog.ar154.ui.boost

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.hedgehog.ar154.R
import com.hedgehog.ar154.databinding.FragmentProgressBinding
import com.hedgehog.ar154.ui.ads.showAds
import com.hedgehog.ar154.ui.progress.ActionsAdapter
import com.hedgehog.ar154.utils.MenuItems
import com.hedgehog.ar154.utils.NativeProvider
import com.hedgehog.ar154.utils.OptimizationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class BoostProgressFragment(
    private val onComplete: Fragment.(
        menuItem: MenuItems,
        data: Any?,
        simpleData: String?
    ) -> Unit
) : Fragment(R.layout.fragment_progress) {

    private val binding: FragmentProgressBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.tvOptimization.text = getString(MenuItems.boost.title)
        boosting()
    }

    private fun boosting() {
        val items = resources.getStringArray(R.array.optimization)
        stringActions(items.toList())
    }

    private fun stringActions(items: List<String>) {
        val adapter = ActionsAdapter(items.toList())
        binding.rvActions.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }

        val repeat = items.size
        startPercents()
        lifecycleScope.launch(Dispatchers.Default) {
            repeat(items.size) {
                delay(TimeUnit.SECONDS.toMillis(8) / repeat)
                withContext(Dispatchers.Main) {
                    adapter.removeFirst()
                }
            }
            withContext(Dispatchers.Main) {
                binding.apply {
                    showAds(
                        tvOptimization,
                        tvPercents,
                        ivDone,
                    ) {
                        goNext()
                    }
                }

            }
        }
    }

    private fun startPercents() {
        lifecycleScope.launch(Dispatchers.Default) {
            val step = 7000 / 100
            for (i in 0..100) {
                withContext(Dispatchers.Main) {
                    binding.tvPercents.text = getString(R.string.d_percents, i)
                }
                delay(step.toLong())
            }
        }
    }

    private fun goNext() {
        val data = boostingData()
        onComplete(MenuItems.boost, data, null)
    }

    private fun boostingData(): Any {
        val result = OptimizationProvider.getRamUsageInfo()
        result.percentsToFree =
            OptimizationProvider.getVarArgs(MenuItems.boost)[0].toString().toInt()
        NativeProvider.boost(requireContext())
        return result
    }

}