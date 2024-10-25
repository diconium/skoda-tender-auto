package com.skoda.launcher.di.component.di


import com.skoda.launcher.domain.manager.DriverRestrictionManager
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
    fun provideSubscriptionsUseCase(subscriptionsRepository: SubscriptionsRepository): SubscriptionsUseCase {
        return SubscriptionsUseCase(subscriptionsRepository)
    }

    @Singleton
    @Provides
    fun provideDriverUseCase(driverRestrictionManager: DriverRestrictionManager): DriverDistractionUseCase {
        return DriverDistractionUseCase(driverRestrictionManager)
    }

}