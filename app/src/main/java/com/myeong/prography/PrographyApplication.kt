package com.myeong.prography

import android.app.Application
import android.content.Context

/**
 * Created by MyeongKi.
 */
class PrographyApplication: Application() {
    init {
        this.also { instance = it }
    }


    companion object {
        private var instance: PrographyApplication? = null
        val currentApplication
            get() = instance!!

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }
}