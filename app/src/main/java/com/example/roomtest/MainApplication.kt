package com.example.roomtest

import android.app.Application

class MainApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}