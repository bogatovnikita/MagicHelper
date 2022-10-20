package ar.cleaner.first.pf.ui.first_scanning

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import ar.cleaner.first.pf.R
import ar.cleaner.first.pf.databinding.FragmentFirstScanningBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FirstScanningFragment() : Fragment() {

    private var _binding: FragmentFirstScanningBinding? = null
    private val binding get() = _binding!!
    private var scanIsDone = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstScanningBinding.inflate(inflater, container, false)
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
                    binding.percentProgressBarTv.text = getString(R.string.percent_D, i)
                    binding.progressBar.progress = i.toFloat()
                    animationProgressBar(i)
                    delay(70)
                }
            }
            scanIsDone = true
            if (scanIsDone) goNext()
        }
    }

    private fun goNext() {
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                binding.apply {
                    //TODO переход на главный экран
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (scanIsDone) goNext()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}