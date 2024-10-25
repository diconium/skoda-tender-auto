package com.skoda.launcher.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.skoda.launcher.core.BaseViewModel
import com.skoda.launcher.domain.usecase.DriverDistractionUseCase
import javax.inject.Inject

class CarViewModel(app: Application) : BaseViewModel(app) {

    @Inject
    lateinit var driverDistractionUseCase: DriverDistractionUseCase
    val uiUxEnableState = MutableLiveData<Boolean?>()
    private val TAG = CarViewModel::class.java.simpleName

    init {
        mainApplication.component.inject(this)
    }

    fun getDriverDistractionStates() {

        driverDistractionUseCase.getCarDrivingState().observeForever {
            Log.i(TAG, "getDriver:getCarDrivingState " + it)
        }

        driverDistractionUseCase.getCarUxRestrictions().observeForever {
            Log.i(TAG, "getDriver:getCarUxRestrictions " + it)
            if (it != null)
                uiUxEnableState.postValue(it.isRequiresDistractionOptimization)
            else
                uiUxEnableState.postValue(null)
        }
    }

}