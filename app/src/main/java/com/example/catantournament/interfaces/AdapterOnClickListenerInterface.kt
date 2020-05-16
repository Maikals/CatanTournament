package com.example.catantournament.interfaces

interface AdapterOnClickListenerInterface {
    interface Adapter {
        fun onClick(position: Int)
    }

    interface Screen<T> {
        fun onClick(data: T)
    }
}