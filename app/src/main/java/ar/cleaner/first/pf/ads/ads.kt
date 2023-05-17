package ar.cleaner.first.pf.ads

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.yin_kio.ads.preloadAd
import com.yin_kio.ads.showAds
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun Fragment.appShowAds(
    onClosed: () -> Unit
) {
    lifecycleScope.launch(Dispatchers.Default) {
        delay(1000)
        withContext(Dispatchers.Main) {
            showAds(onClosed)
        }
    }
}

fun Fragment.appPreloadAd() {
    preloadAd()
}