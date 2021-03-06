package com.github.devjn.imgurimagegallery.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.github.devjn.imgurimagegallery.DataCenter
import com.github.devjn.imgurimagegallery.R
import com.github.devjn.imgurimagegallery.data.DataItem
import com.github.devjn.imgurimagegallery.databinding.ActivityDetailsBinding
import com.github.devjn.imgurimagegallery.utils.loadImgurImage

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val data: DataItem = DataCenter.selectedDataItem!!

        binding.expandedImage.loadImgurImage(data)
        binding.aspectRatioParent.aspectRatio = data.images?.get(0)?.let {
            it.width.toFloat() / it.height.toFloat()
        } ?: data.width.toFloat() / data.height.toFloat()

        binding.data = data
    }
}
