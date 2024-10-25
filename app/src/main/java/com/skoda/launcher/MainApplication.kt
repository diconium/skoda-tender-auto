package com.skoda.launcher

import android.car.Car
import com.skoda.launcher.di.component.DaggerApplicationComponent
import com.skoda.launcher.di.module.ApplicationModule
import com.skoda.launcher.domain.manager.DriverRestrictionManager

class MainApplication : android.app.Application() {

    val component by lazy {
        DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
       var car = Car.createCar(this)
        DriverRestrictionManager.init(this, car)

    }
}

