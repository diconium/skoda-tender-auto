package com.skoda.launcher.di.component.di


import com.skoda.launcher.data.datasource.SubscriptionsDataSource
import com.skoda.launcher.domain.repository.SubscriptionsRepository
import com.skoda.launcher.domain.repositoryimpl.SubscriptionsRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton



@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun providesSubscriptionsRepository(subscriptionsDataSource: SubscriptionsDataSource): SubscriptionsRepository {
        return SubscriptionsRepositoryImpl(subscriptionsDataSource)
    }
}