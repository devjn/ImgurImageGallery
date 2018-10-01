package com.github.devjn.imgurimagegallery

import com.github.devjn.imgurimagegallery.api.ImgurApi
import com.github.devjn.imgurimagegallery.api.ImgurService


/**
 * Created by @author Jahongir on 01-Oct-18
 * devjn@jn-arts.com
 * Provider
 */
object Provider {

    val imgurService: ImgurService by lazy { ImgurApi.createService(ImgurService::class.java) }

}