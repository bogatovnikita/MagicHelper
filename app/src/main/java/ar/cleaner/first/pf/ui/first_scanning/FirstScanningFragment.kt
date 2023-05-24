package ar.cleaner.first.pf.ui.first_scanning

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import ar.cleaner.first.pf.R
import ar.cleaner.first.pf.ads.appShowAds
import ar.cleaner.first.pf.databinding.FragmentFirstScanningBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import com.yin_kio.ads.preloadAd
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FirstScanningFragment : Fragment(R.layout.fragment_first_scanning) {

    private val binding: FragmentFirstScanningBinding by viewBinding()
    private var scanIsDone = false

    override fun onResume() {
        super.onResume()
        if (scanIsDone) goNext()
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        preloadAd()
        firstOptimization()
    }

    @SuppressLint("Recycle")
    private fun animationProgressBar(usageState: Int) {
        ObjectAnimator.ofInt(binding.progressBar.progress, "progress", usageState).apply {
            duration = 300
            interpolator = LinearInterpolator()
        }.start()
    }

    private fun firstOptimization() {
        lifecycleScope.launch(Dispatchers.Default) {
            for (i in 0..100) {
                withContext(Dispatchers.Main) {
                    when (i) {
                        50 -> {
                            binding.titleTv.text = getString(R.string.collect_data)
                        }
                        75 -> {
                            binding.titleTv.text = getString(R.string.theres_not_much_left)
                        }
                        100 -> {
                            binding.titleTv.text = getString(R.string.done)
                        }
                    }
                    binding.percentProgressBarTv.text = getString(R.string.percent_D, i)
                    binding.progressBar.progress = i.toFloat()
                    animationProgressBar(i)
                    delay(100)
                }
            }
            scanIsDone = true
            if (scanIsDone) goNext()
        }
    }

    private fun goNext() {
        appShowAds {
            findNavController().navigate(R.id.action_to_menuFragment)
        }
    }


}