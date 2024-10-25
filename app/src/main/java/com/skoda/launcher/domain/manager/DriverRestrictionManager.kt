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
import com.skoda.launcher.utils.extensions.hasPermission

/**
 * DriverRestrictionManager is responsible for managing the distraction optimization
 * logic based on the driving state of the vehicle and the UX restrictions.
 *
 * It listens for changes in driving state and UX restrictions, enabling or disabling
 * UI interactions accordingly.
 *
 * @param context The application context.
 * @param car The Car instance to access car services.
 */
class DriverRestrictionManager private constructor(private val context: Context, car: Car) {

    /**
     * CarDrivingStateManager instance to manage driving state events.
     */
    private var carDrivingStateManager: CarDrivingStateManager? = null

    /**
     * CarUxRestrictionsManager instance to manage UX restrictions in the car.
     */
    private val carUxRestrictionsManager: CarUxRestrictionsManager =
        car.getCarManager(Car.CAR_UX_RESTRICTION_SERVICE) as CarUxRestrictionsManager

    /**
     * LiveData holding the current CarDrivingStateEvent, which indicates the driving state.
     */
    private val _carDrivingStateEvent = MutableLiveData<CarDrivingStateEvent?>(null)

    /**
     * LiveData holding the current CarUxRestrictions, which indicates the UX restrictions.
     */
    private val _carUxRestrictionsLiveData = MutableLiveData<CarUxRestrictions?>(null)

    /**
     * Exposed LiveData for observing driving state events.
     */
    val carDrivingState: LiveData<CarDrivingStateEvent?>
        get() = _carDrivingStateEvent

    /**
     * Exposed LiveData for observing UX restrictions.
     */
    val carUxRestrictions: LiveData<CarUxRestrictions?>
        get() = _carUxRestrictionsLiveData

    init {
        if (context.hasPermission(DRIVING_STATE_PERMISSION)) {
            Log.i(TAG, "has driver permission")
            carDrivingStateManager =
                car.getCarManager(Car.CAR_DRIVING_STATE_SERVICE) as CarDrivingStateManager
        } else {
            Log.i(TAG, "does not have driver permission")
        }
    }

    /**
     * Singleton instance of the DriverRestrictionManager.
     *
     * This instance is created when needed and shared across the application.
     */
    companion object {
        @SuppressLint("StaticFieldLeak") // Application Context used
        @Volatile
        private var INSTANCE: DriverRestrictionManager? = null

        /**
         * Tag for logging purposes.
         */
        val TAG = DriverRestrictionManager::class.java.simpleName

        /**
         * Permission required to access driving state information.
         */
        const val DRIVING_STATE_PERMISSION = "android.car.permission.CAR_DRIVING_STATE"

        /**
         * Provides a thread-safe way to obtain the singleton instance.
         *
         * @return The singleton instance of DriverRestrictionManager.
         */
        fun getInstance(): DriverRestrictionManager {
            if (INSTANCE == null) {
                Log.i(TAG, "instance: is null")
            }
            return INSTANCE!!
        }

        /**
         * Initializes the DriverRestrictionManager singleton instance.
         *
         * @param appContext The application context.
         * @param car The Car instance to access car services.
         * @return The singleton instance of DriverRestrictionManager.
         */
        fun init(appContext: Context, car: Car): DriverRestrictionManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: DriverRestrictionManager(appContext, car).also {
                    INSTANCE = it
                    INSTANCE?.registerListeners()
                }
            }
        }
    }

    /**
     * Registers listeners for driving state events and UX restrictions.
     * Updates LiveData when events or restrictions change.
     */
    private fun registerListeners() {
        carDrivingStateManager?.registerListener { event ->
            _carDrivingStateEvent.postValue(event)
        }
        carUxRestrictionsManager.registerListener { restrictions ->
            _carUxRestrictionsLiveData.postValue(restrictions)
        }
        _carUxRestrictionsLiveData.postValue(carUxRestrictionsManager.currentCarUxRestrictions)
    }
}
