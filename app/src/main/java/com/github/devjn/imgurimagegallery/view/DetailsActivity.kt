package com.github.devjn.imgurimagegallery.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.github.devjn.imgurimagegallery.R
import com.github.devjn.imgurimagegallery.data.DataItem
import com.github.devjn.imgurimagegallery.databinding.ActivityDetailsBinding
import com.github.devjn.imgurimagegallery.utils.loadImage
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val data: DataItem = intent.extras?.getSerializable("data") as DataItem

        data.images?.get(0)?.let {
            aspect_ratio_parent.aspectRatio = it.width.toFloat() / it.height.toFloat()
            expanded_image.loadImage(it.link)
        }

        binding.data = data
    }
}
