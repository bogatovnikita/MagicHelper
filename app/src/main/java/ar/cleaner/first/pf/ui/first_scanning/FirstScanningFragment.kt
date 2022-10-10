package ar.cleaner.first.pf.ui.first_scanning

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import ar.cleaner.first.pf.R
import ar.cleaner.first.pf.databinding.FragmentProgressBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FirstScanningFragment(
    private val onComplete: Fragment.() -> Unit
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
                    binding.percentProgressBarTv.text = getString(R.string.percent_d, i)
                    binding.progressBar.progress = i.toFloat()
                    animationProgressBar(i)
                    delay(70)
                }
            }
            withContext(Dispatchers.Main) {
                binding.apply {
//                    showAds() {
                    onComplete()
//                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}