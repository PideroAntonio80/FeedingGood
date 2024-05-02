package com.diusframi.feedinggood.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.diusframi.feedinggood.MainActivity
import com.diusframi.feedinggood.databinding.ActivitySplashBinding

class StartActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {

//        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        splashScreen.setKeepOnScreenCondition { false }

        Thread.sleep(1000)

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}