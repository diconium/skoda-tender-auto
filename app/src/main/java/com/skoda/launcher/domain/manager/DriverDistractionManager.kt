package com.skoda.launcher.domain.manager

import android.annotation.SuppressLint
import android.car.Car
import android.car.drivingstate.CarDrivingStateEvent
import android.car.drivingstate.CarDrivingStateManager
import android.car.drivingstate.CarUxRestrictions
import android.car.drivingstate.CarUxRestrictionsManager
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * DriverDistractionManager is responsible for managing the distraction optimization
 * logic based on the driving state of the vehicle and the UX restrictions.
 *
 * It listens for changes in driving state and UX restrictions, enabling or disabling
 * UI interactions accordingly.
 *
 * @param context The application context.
 * @param car The Car instance to access car services.
 */
class DriverDistractionManager private constructor(private val context: Context, car: Car) {

    private val carDrivingStateManager: CarDrivingStateManager =
        car.getCarManager(Car.CAR_DRIVING_STATE_SERVICE) as CarDrivingStateManager
    private val carUxRestrictionsManager: CarUxRestrictionsManager =
        car.getCarManager(Car.CAR_UX_RESTRICTION_SERVICE) as CarUxRestrictionsManager

    private val _carDrivingStateEvent = MutableLiveData<CarDrivingStateEvent?>(null)
    private val _carUxRestrictionsFlow = MutableLiveData<CarUxRestrictions?>(null)

    public val carDrivingState: LiveData<CarDrivingStateEvent?>
        get() = _carDrivingStateEvent

    public val carUxRestrictions: LiveData<CarUxRestrictions?>
        get() = _carUxRestrictionsFlow

    /**
     * Singleton instance of the DriverDistractionManager.
     *
     * This instance is created when needed and shared across the application.
     */
    companion object {
        @SuppressLint("StaticFieldLeak")//Application Context used
        @Volatile
        private var INSTANCE: DriverDistractionManager? = null

        val TAG = DriverDistractionManager::class.java.simpleName


        /**
         * Provides a thread-safe way to obtain the singleton instance.
         *
         * @return The singleton instance of DriverDistractionManager.
         */
        fun getInstance(): DriverDistractionManager {
            if (INSTANCE == null) {
                Log.i(TAG, "instance: is null  ")
            }
            return INSTANCE!!
        }

        /**
         * Provides a thread-safe way to obtain the singleton instance.
         *
         * @param context The application context.
         * @param car The Car instance to access car services.
         * @return The singleton instance of DriverDistractionManager.
         */
        fun init(appContext: Context, car: Car): DriverDistractionManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: DriverDistractionManager(appContext, car).also {
                    INSTANCE = it
                    INSTANCE?.registerListeners()
                }
            }
        }

    }


    private fun registerListeners() {
        carDrivingStateManager.registerListener { event ->
            handleDrivingStateChange(event)
            _carDrivingStateEvent.postValue(event)
        }
        carUxRestrictionsManager.registerListener { restrictions ->
            handleUxRestrictionsChange(restrictions)
            _carUxRestrictionsFlow.postValue(restrictions)
        }


        //_carDrivingStateEvent.postValue(carUxRestrictionsManager.)
        Log.i(TAG, "registerListeners: " + carUxRestrictionsManager.currentCarUxRestrictions)
        Log.i(TAG, "registerListeners: " + _carUxRestrictionsFlow)
        _carUxRestrictionsFlow.postValue(carUxRestrictionsManager.currentCarUxRestrictions)


    }

    /**
     * Handles changes in UX restrictions to determine the allowed features.
     *
     * @param restrictions The current UX restrictions.
     */
    private fun handleUxRestrictionsChange(restrictions: CarUxRestrictions) {
        if (restrictions.isRequiresDistractionOptimization) {
            handleDrivingState()
        } else {
            handleParkedState()
        }
    }

    /**
     * Handles UI changes when distraction optimization is required (e.g., while driving).
     */
    private fun handleDrivingState() {
        Log.d(TAG, "Driving: Limited features available")
    }

    /**
     * Handles UI changes when full functionality is allowed (e.g., while parked).
     */
    private fun handleParkedState() {
        Log.d(TAG, "Not Driving: All features enabled")
    }

    /**
     * Handles driving state changes and updates the UI accordingly.
     *
     * @param event The driving state event.
     */
    private fun handleDrivingStateChange(event: CarDrivingStateEvent) {
        when (event.eventValue) {
            CarDrivingStateEvent.DRIVING_STATE_PARKED -> {
                Log.d(TAG, "Car is parked")
            }

            CarDrivingStateEvent.DRIVING_STATE_IDLING,
            CarDrivingStateEvent.DRIVING_STATE_MOVING -> {
                Log.d(TAG, "Car is moving")
            }

            else -> {
                Log.d(TAG, "State Unknown")
            }
        }
    }
}
