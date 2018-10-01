package com.github.devjn.imgurimagegallery.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.devjn.imgurimagegallery.DataCenter
import com.github.devjn.imgurimagegallery.Provider
import com.github.devjn.moviessample.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val layoutType = MutableLiveData<Int>().apply { value = 2 }

    internal var isShowViral = false
        private set
    internal var section = "";
    internal var window = "day"
    internal var sort = "isShowViral"

    fun doRequest() = Provider.imgurService.getGalley(section = section, sort = sort, window = window, showViral = isShowViral)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ it ->
                DataCenter.galleryAlbumData.value = it
            }, { e -> Log.e(App.TAG, "Exception during data request", e) }).disposeOnClear()


    fun toggleViral() = ++isShowViral

    private fun Disposable.disposeOnClear() {
        compositeDisposable.add(this)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}

operator fun Boolean.inc() = !this
