package com.rakuishi.falldetection.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rakuishi.falldetection.R
import com.rakuishi.falldetection.presentation.main.MainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
    }
}