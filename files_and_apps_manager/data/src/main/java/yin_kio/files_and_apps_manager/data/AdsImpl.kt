package yin_kio.files_and_apps_manager.data

import android.content.Context
import com.yin_kio.ads.preloadAd
import file_manager.scan_progress.gateways.Ads

class AdsImpl(
    private val context: Context
) : Ads {

    override suspend fun preloadAd() {
        context.preloadAd()
    }
}