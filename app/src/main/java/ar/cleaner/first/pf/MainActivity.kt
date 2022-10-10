package ar.cleaner.first.pf

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ar.cleaner.first.pf.databinding.ActivityMainBinding
import ar.cleaner.first.pf.navigation.Actions
import ar.cleaner.first.pf.navigation.NavigationFragmentFactory
import ar.cleaner.first.pf.utils.BatInfoReceiver
import ar.cleaner.first.pf.utils.BatteryChargeReceiver
import com.ads.library.AdsManager
import com.ads.library.SubscriptionProvider

class MainActivity : AppCompatActivity() {

    private var blockBackButton = false
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = NavigationFragmentFactory()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        SubscriptionProvider.getInstance(this).init(this)
//        AdsManager.init(this, BuildConfig.DEBUG)
//        AdsManager.initBanner(binding.adView)
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