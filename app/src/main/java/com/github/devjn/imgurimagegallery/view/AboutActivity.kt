package com.github.devjn.imgurimagegallery.view


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.devjn.imgurimagegallery.BuildConfig
import com.github.devjn.imgurimagegallery.R
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        text.text = getString(R.string.about_info, BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE.toString())
    }

}