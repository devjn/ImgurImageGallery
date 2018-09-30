package com.github.devjn.imgurimagegallery.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.devjn.imgurimagegallery.R
import com.github.devjn.imgurimagegallery.data.DataItem
import com.github.devjn.imgurimagegallery.utils.loadImage
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val data: DataItem = intent.extras?.getSerializable("data") as DataItem
        expanded_image.loadImage(data.images?.get(0)?.link)
    }
}
