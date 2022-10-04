package com.hedgehog.ar154.ui.first_scanning

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.hedgehog.ar154.R
import com.hedgehog.ar154.databinding.FragmentProgressBinding
import com.hedgehog.ar154.ui.ads.showAds
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FirstScanningFragment(
    private val onComplete: Fragment.() -> Unit
) : Fragment(R.layout.fragment_progress) {

    private val binding: FragmentProgressBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        firstOptimization()
    }

    private fun firstOptimization() {
        startPercents()
        lifecycleScope.launch(Dispatchers.Default) {
            val step = 8000 / 3
            val steps = resources.getStringArray(R.array.first_items)
            for (i in 0 until 3) {
                withContext(Dispatchers.Main) {
                    binding.tvOptimization.text = steps[i]
                }
                delay(step.toLong())
            }
            withContext(Dispatchers.Main) {
                binding.apply {
                    showAds(tvOptimization,
                        tvPercents,
                        ivDone){
                        onComplete()
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

}