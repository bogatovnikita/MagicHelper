package ar.cleaner.first.pf.app

import android.app.Application
import ar.cleaner.first.pf.BuildConfig
import com.yin_kio.ads.onAdsError
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppClass : Application() {

    override fun onCreate() {
        super.onCreate()

        val config = YandexMetricaConfig.newConfigBuilder(BuildConfig.YANDEX_KEY).build()
        YandexMetrica.activate(applicationContext, config)
        YandexMetrica.enableActivityAutoTracking(this)
        FirebaseApp.initializeApp(this)
        FirebaseMessaging.getInstance().isAutoInitEnabled = true
        onAdsError{type, _, _ -> YandexMetrica.reportEvent(type) }
    }
}