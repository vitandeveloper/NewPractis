package com.example.contactrandom

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

/** Created by marlon on 27/5/23. **/
@HiltAndroidApp
class MasterApp : Application() {
    companion object {
        private lateinit var ctx: Context

        fun getContext() = ctx
    }

    override fun onCreate() {
        super.onCreate()
         ctx = applicationContext
    }

}