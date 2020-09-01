package com.rakuishi.falldetection.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rakuishi.falldetection.R
import com.rakuishi.falldetection.presentation.sensor.SensorFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SensorFragment.newInstance())
                .commitNow()
        }
    }
}