package com.example.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yin_kio.ads.initAds

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initAds()
    }
}