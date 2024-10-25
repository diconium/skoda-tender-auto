package com.skoda.launcher.di.module

import com.skoda.launcher.domain.manager.DriverRestrictionManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DriverDistractionModule {

    @Provides
    @Singleton
    fun provideDriverDistractionManager(): DriverRestrictionManager {
        return DriverRestrictionManager.getInstance()
    }
}
