package com.skoda.launcher.di.module

import android.car.Car
import android.content.Context
import com.skoda.launcher.domain.manager.DriverDistractionManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DriverDistractionModule {

    @Provides
    @Singleton
    fun provideDriverDistractionManager(): DriverDistractionManager {
        return DriverDistractionManager.getInstance()
    }
}
