package com.github.devjn.imgurimagegallery.viewmodel

import android.util.Log
import android.view.View
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.devjn.imgurimagegallery.api.ImgurApi
import com.github.devjn.imgurimagegallery.api.ImgurService
import com.github.devjn.imgurimagegallery.data.ImgurGalleryAlbum
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel() {

    val loadingBarVisibility = ObservableInt(View.GONE)
    val emptyViewVisibility = ObservableInt(View.GONE)
    val data = MutableLiveData<ImgurGalleryAlbum>()
    private val compositeDisposable = CompositeDisposable()

    private var allData: ImgurGalleryAlbum? = null
    private var viral = false
    internal var section = "";
    internal var window = "day"
    internal var sort = "viral"

    // This can be done cleaner using RxJava and Dagger but it's overhead for such small project
    fun doRequest(imgurService: ImgurService = ImgurApi.createService(ImgurService::class.java)) {
        imgurService.getGalley(section = section, sort = sort, window = window, showViral = viral)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it ->
                    data.value = it
                    allData = it
                }, { e -> Log.e("ERROR", "exception", e) }).disposeOnClear()

//        loadingBarVisibility.set(View.VISIBLE)
//        try {
//            emptyViewVisibility.set(View.GONE)
//        } catch (e: Exception) {
//            Log.w(App.TAG, "Exception during loading movie data", e)
//            emptyViewVisibility.set(View.VISIBLE)
//        }
//        loadingBarVisibility.set(View.GONE)
    }

    fun toggleViral() = ++viral


    private fun Disposable.disposeOnClear() {
        compositeDisposable.add(this)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}

operator fun Boolean.inc() = !this
