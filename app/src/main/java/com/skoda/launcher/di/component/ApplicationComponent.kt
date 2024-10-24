package com.skoda.launcher.di.component

import android.content.Context
import com.samad_talukder.androidcleanarchitecturemvvm.di.DataSourceModule
import com.samad_talukder.androidcleanarchitecturemvvm.di.RepositoryModule
import com.samad_talukder.androidcleanarchitecturemvvm.di.UseCaseModule
import com.skoda.launcher.MainApplication
import com.skoda.launcher.di.module.ApplicationModule
import com.skoda.launcher.di.module.DatabaseModule
import com.skoda.launcher.ui.viewmodel.CarViewModel
import com.skoda.launcher.di.module.NetModule
import com.skoda.launcher.ui.fragment.ServiceListFragment
import com.skoda.launcher.ui.viewmodel.ServiceViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * Dagger component for dependency injection in the application.
 *
 * This component is responsible for providing application-level dependencies,
 * including modules for networking, database, use cases, data sources, and repositories.
 */
@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        NetModule::class,
        DatabaseModule::class,
        UseCaseModule::class,
        DataSourceModule::class,
        RepositoryModule::class
    ]
)
interface ApplicationComponent {

    /**
     * Provides the [MainApplication] instance.
     *
     * @return The application instance.
     */
    fun app(): MainApplication

    /**
     * Provides the application context.
     *
     * @return The application context.
     */
    fun context(): Context

    /**
     * Injects dependencies into the [CarViewModel].
     *
     * @param carViewModel The [CarViewModel] instance to inject dependencies into.
     */
    fun inject(carViewModel: CarViewModel)

    /**
     * Injects dependencies into the [ServiceListFragment].
     *
     * @param serviceList The [ServiceListFragment] instance to inject dependencies into.
     */
    fun inject(serviceList: ServiceListFragment)

    /**
     * Injects dependencies into the [ServiceViewModel].
     *
     * @param serviceViewModel The [ServiceViewModel] instance to inject dependencies into.
     */
    fun inject(serviceViewModel: ServiceViewModel)
}
