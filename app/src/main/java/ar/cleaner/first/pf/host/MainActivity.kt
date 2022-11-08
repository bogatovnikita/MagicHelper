package ar.cleaner.first.pf.host

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ar.cleaner.first.pf.BuildConfig
import ar.cleaner.first.pf.databinding.ActivityMainBinding
import com.ads.library.AdsManager
import com.ads.library.SubscriptionProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        SubscriptionProvider.getInstance(this).init(this)
//        AdsManager.init(this, BuildConfig.DEBUG)
//        AdsManager.initBanner(binding.adView)
    }
}