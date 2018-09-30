package com.github.devjn.imgurimagegallery.api

import com.github.devjn.imgurimagegallery.data.ImgurGalleryAlbum
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Created by @author Jahongir on 21-Sep-18
 * devjn@jn-arts.com
 * ImgurService
 */

interface ImgurService {

    @GET("gallery/{section}/{sort}/{window}/{page}?showViral=bool")
    fun getGalley(@Path("section") section: String = "hot",
                  @Path("sort") sort: String = "viral",
                  @Path("window") window: String = "day",
                  @Path("page") page: Int = 0,
                  @Query("showViral") showViral: Boolean = false): Single<ImgurGalleryAlbum>

}