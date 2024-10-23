package com.skoda.launcher.core

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.skoda.launcher.MainApplication

open class BaseViewModel(val app: Application) : AndroidViewModel(app) {
    val mainApplication: MainApplication
        get() {
            return app as MainApplication
        }
}
