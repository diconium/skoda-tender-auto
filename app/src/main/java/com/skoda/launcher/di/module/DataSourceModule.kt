package com.skoda.launcher.di.component.di


import com.skoda.launcher.data.datasource.SubscriptionsDataSource
import com.skoda.launcher.data.datasourceimpl.SubscriptionsDataSourceImpl
import com.skoda.launcher.data.source.remote.SubscriptionsApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DataSourceModule {

    @Singleton
    @Provides
    fun provideSubscriptionsDataSource(storiesApi: SubscriptionsApi): SubscriptionsDataSource {
        return SubscriptionsDataSourceImpl(storiesApi)
    }
}