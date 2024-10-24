package com.samad_talukder.androidcleanarchitecturemvvm.di


import com.skoda.launcher.domain.repository.SubscriptionsRepository
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
}