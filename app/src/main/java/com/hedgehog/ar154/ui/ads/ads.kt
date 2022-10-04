package com.hedgehog.ar154.ui.ads

import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.ads.library.AdsDelegate
import com.ads.library.AdsManager
import com.hedgehog.ar154.BuildConfig
import com.hedgehog.ar154.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun Fragment.showAds(
    modeText: TextView,
    percents: TextView,
    done: ImageView,
    onClosed: () -> Unit
){
    lifecycleScope.launch(Dispatchers.Main) {
        modeText.text = getString(R.string.done)
        percents.animate()
            .alpha(0.0f)
            .setDuration(300)
            .start()
        done.animate()
            .alpha(1.0f)
            .setStartDelay(300)
            .setDuration(300)
            .start()
    }
    lifecycleScope.launch(Dispatchers.Default){
        delay(2000)
        withContext(Dispatchers.Main){
            AdsManager.showInterstitial(requireActivity(), object : AdsDelegate {
                override fun adsClosed() {
                    onClosed()
                }
            })
        }
    }
}

fun Fragment.preloadAd(){
    if (!AdsManager.checkAdsLoaded()){
//        AdsManager.preloadAd(requireActivity(), BuildConfig.ADMOB_INTERSTITIAL)
    }
}