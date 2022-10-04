package com.hedgehog.ar154.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.hedgehog.ar154.databinding.FragmentSplashBinding
import com.hedgehog.ar154.ui.ads.preloadAd
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashFragment(
    private val onComplete: Fragment.() -> Unit
) : Fragment() {

    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preloadAd()

        lifecycleScope.launch(Dispatchers.Default) {
            delay(2000)
            withContext(Dispatchers.Main) {
                onComplete()
            }
        }
    }

}