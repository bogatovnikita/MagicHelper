package ar.cleaner.first.pf.ads

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.ads.library.AdsDelegate
import com.ads.library.AdsManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//fun Fragment.showAds(
//    onClosed: () -> Unit
//) {
//    lifecycleScope.launch(Dispatchers.Default) {
//        delay(1000)
//        withContext(Dispatchers.Main) {
//            AdsManager.showInterstitial(requireActivity(), object : AdsDelegate {
//                override fun adsClosed() {
//                    onClosed()
//                }
//            })
//        }
//    }
//}

//fun Fragment.preloadAd() {
//    if (!AdsManager.checkAdsLoaded()) {
//        AdsManager.preloadAd(requireActivity(), BuildConfig.ADMOB_INTERSTITIAL)
//    }
//}