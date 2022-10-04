package com.hedgehog.ar154

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.ads.library.AdsManager
import com.ads.library.SubscriptionProvider
import com.hedgehog.ar154.databinding.ActivityMainBinding
import com.hedgehog.ar154.navigation.Actions
import com.hedgehog.ar154.navigation.NavigationFragmentFactory
import com.hedgehog.ar154.utils.BatInfoReceiver
import com.hedgehog.ar154.utils.BatteryChargeReceiver

class MainActivity : AppCompatActivity() {

    private var blockBackButton = false
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = NavigationFragmentFactory()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        SubscriptionProvider.getInstance(this).init(this)
        AdsManager.init(this, BuildConfig.DEBUG)
        AdsManager.initBanner(binding.adView)
    }

    override fun onResume() {
        super.onResume()
        BatInfoReceiver.registerReceiver(this)
        BatteryChargeReceiver.registerReceiver(this)
    }

    override fun onPause() {
        super.onPause()
        BatInfoReceiver.unregisterReceiver(this)
        BatteryChargeReceiver.unregisterReceiver(this)
    }

    fun blockBackButton(b: Boolean) {
        blockBackButton = b
    }

    override fun onBackPressed() {
        if (!blockBackButton) {
            val fragment = binding.fragmentContainerView.getFragment<Fragment>()
            Actions.goBackIfCan(fragment)
        }
    }
}