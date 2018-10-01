package com.github.devjn.imgurimagegallery

import androidx.lifecycle.MutableLiveData
import com.github.devjn.imgurimagegallery.data.DataItem
import com.github.devjn.imgurimagegallery.data.ImgurGalleryAlbum


/**
 * Used to store and share data (oversimplified version)
 * Created by @author Jahongir on 01-Oct-18
 * devjn@jn-arts.com
 * DataCenter
 */
object DataCenter {

    val galleryAlbumData = MutableLiveData<ImgurGalleryAlbum>()

    var selectedDataItem: DataItem? = null

}