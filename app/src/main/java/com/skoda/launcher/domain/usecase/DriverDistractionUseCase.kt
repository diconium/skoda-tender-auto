package com.skoda.launcher.domain.usecase

import androidx.lifecycle.LiveData
import com.skoda.launcher.domain.manager.DriverDistractionManager
import android.car.drivingstate.CarDrivingStateEvent
import android.car.drivingstate.CarUxRestrictions


class DriverDistractionUseCase(private val distractionManager: DriverDistractionManager) {

    /**
     * Exposes the car driving state as LiveData.
     */
    fun getCarDrivingState(): LiveData<CarDrivingStateEvent?> {
        return distractionManager.carDrivingState
    }

    /**
     * Exposes the car UX restrictions as LiveData.
     */
    fun getCarUxRestrictions(): LiveData<CarUxRestrictions?> {
        return distractionManager.carUxRestrictions
    }
}
