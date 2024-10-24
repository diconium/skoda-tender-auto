package com.skoda.launcher.di.component.di


import com.skoda.launcher.domain.manager.DriverDistractionManager
import com.skoda.launcher.domain.repository.SubscriptionsRepository
import com.skoda.launcher.domain.usecase.DriverDistractionUseCase
import com.skoda.launcher.domain.usecase.SubscriptionsUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class UseCaseModule {

    @Singleton
    @Provides
    fun provideSubscriptionsUseCase(storiesRepository: SubscriptionsRepository): SubscriptionsUseCase {
        return SubscriptionsUseCase(storiesRepository)
    }

    @Singleton
    @Provides
    fun provideDriverUseCase(driverDistractionManager: DriverDistractionManager): DriverDistractionUseCase {
        return DriverDistractionUseCase(driverDistractionManager)
    }

}