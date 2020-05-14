package com.example.catantournament.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, fn: (T) -> Unit) {
    liveData.observe(this, Observer {
        fn(it)
    })
}