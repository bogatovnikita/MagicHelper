package ar.cleaner.first.pf.host

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ar.cleaner.first.pf.databinding.ActivityMainBinding
import com.yin_kio.ads.initAds
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAds()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}