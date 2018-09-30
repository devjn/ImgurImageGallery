package com.github.devjn.imgurimagegallery.api

import com.github.devjn.moviessample.App
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


/**
 * Created by @author Jahongir on 21-Sep-18
 * devjn@jn-arts.com
 * ImgurApi
 */
object ImgurApi {

    private const val CACHE_CONTROL = "Cache-Control"
    private const val BASE_URL = "https://api.imgur.com/3/"

    private val okHttp: OkHttpClient

    init {
        val httpCacheDirectory = File(App.appContext.cacheDir, "responses")
        val cacheSize = 10L * 1024 * 1024 // 10 MiB
        val cache = Cache(httpCacheDirectory, cacheSize)

        okHttp = OkHttpClient.Builder()
                .addInterceptor(AuthenticationInterceptor("522a69913b78a58"))
                .addInterceptor(provideOfflineCacheInterceptor())
                .addNetworkInterceptor(provideCacheInterceptor())
                .cache(cache)
                .build()
    }

    private val builder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttp)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())

    private fun provideCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            val response = chain.proceed(chain.request())

            // re-write response header to force use of cache
            val cacheControl = CacheControl.Builder()
                    .maxAge(10, TimeUnit.MINUTES)
                    .build()

            response.newBuilder()
                    .header(CACHE_CONTROL, cacheControl.toString())
                    .build()
        }
    }

    private fun provideOfflineCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()

            if (!App.isNetworkAvailable()) {
                val cacheControl = CacheControl.Builder()
                        .maxStale(7, TimeUnit.DAYS)
                        .build()

                request = request.newBuilder()
                        .cacheControl(cacheControl)
                        .build()
            }

            chain.proceed(request)
        }
    }

    @JvmStatic
    fun changeApiBaseUrl(newApiBaseUrl: String) {
        builder.baseUrl(newApiBaseUrl)
    }


    fun <S> createService(serviceClass: Class<S>): S {
        val retrofit = builder.build()
        return retrofit.create(serviceClass)
    }

    class AuthenticationInterceptor(private val authToken: String) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val original = chain.request()

            val builder = original.newBuilder().header("Authorization", "Client-ID $authToken")

            val request = builder.build()
            return chain.proceed(request)
        }
    }

}