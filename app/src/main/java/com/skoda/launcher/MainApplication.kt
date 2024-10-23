package com.skoda.launcher

import com.skoda.launcher.di.component.DaggerApplicationComponent
import com.skoda.launcher.di.module.ApplicationModule

class MainApplication : android.app.Application() {

    val component by lazy {
        DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }
}

