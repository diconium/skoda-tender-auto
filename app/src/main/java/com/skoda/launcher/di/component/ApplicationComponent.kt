package com.skoda.launcher.di.component

import android.content.Context
import android.content.SharedPreferences
import com.skoda.launcher.MainApplication
import com.skoda.launcher.di.module.ApplicationModule
import com.skoda.launcher.di.module.DatabaseModule
import com.skoda.launcher.ui.viewmodel.CarViewModel

import com.skoda.launcher.di.module.NetModule
import com.skoda.launcher.ui.fragment.ServiceListFragment

import dagger.Component
import javax.inject.Singleton


@Singleton

@Component(modules = arrayOf(ApplicationModule::class,NetModule::class,DatabaseModule::class))


interface ApplicationComponent {
    fun app(): MainApplication
    fun context(): Context

    fun preferences(): SharedPreferences

    fun inject(carViewModel: CarViewModel)

    fun inject(serviceList: ServiceListFragment)
}
