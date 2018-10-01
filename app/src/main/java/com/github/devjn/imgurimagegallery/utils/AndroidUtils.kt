package com.github.devjn.imgurimagegallery.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.github.devjn.imgurimagegallery.R
import com.github.devjn.imgurimagegallery.data.DataItem
import com.github.devjn.imgurimagegallery.widgets.AspectRatioView
import com.github.devjn.moviessample.App
import com.github.devjn.moviessample.utils.GlideApp


/**
 * Created by @author Jahongir on 23-Aug-18
 * devjn@jn-arts.com
 * AndroidUtils
 */

@BindingAdapter("imgurImage")
fun ImageView.loadImgurImage(data: DataItem) {
    //First check if there is gif image and if not load normal one
    val link = data.link
    if (!data.gifv.isNullOrEmpty()) {
        GlideApp.with(this).load(data.gifv).error(GlideApp.with(this).load(link)).placeholder(R.drawable.ic_image).into(this)
    } else {
        GlideApp.with(this).load(data.images?.get(0)?.link)
                .error(GlideApp.with(this).load(link))
                .placeholder(R.drawable.ic_image).into(this)
    }
}

@BindingAdapter("dataAspectRation")
fun AspectRatioView.setAspect(data: DataItem) = data.images?.get(0)?.let {
    this.aspectRatio = it.width.toFloat() / it.height.toFloat()
    this.requestLayout()
}

object AndroidUtils {
    val density = App.appContext.resources.displayMetrics.density

    fun dp(value: Int) = Math.ceil((density * value).toDouble()).toInt()
}