package com.skoda.launcher.ui.viewmodel

import android.app.Application
import com.skoda.launcher.core.BaseViewModel
import com.skoda.launcher.db.AppDatabase
import javax.inject.Inject

class CarViewModel(app: Application) : BaseViewModel(app) {

    @Inject
    lateinit var db: AppDatabase
    init {
        mainApplication.component.inject(this)
    }

}