package ar.cleaner.first.pf

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig

class AppClass : Application() {

    companion object {
        lateinit var instance: AppClass
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        val config = YandexMetricaConfig.newConfigBuilder(BuildConfig.YANDEX_KEY).build()
        YandexMetrica.activate(applicationContext, config)
        YandexMetrica.enableActivityAutoTracking(this)
        FirebaseApp.initializeApp(this)
        FirebaseMessaging.getInstance().isAutoInitEnabled = true
//        AdsManager.setOnAdsError { type, key, error ->
//            YandexMetrica.reportEvent(type)
//        }
//        SubscriptionProvider.getInstance(this).init(this)
//        AdsManager.init(this, BuildConfig.DEBUG)
//        AdsManager.initBanner(binding.adView)
    }

}