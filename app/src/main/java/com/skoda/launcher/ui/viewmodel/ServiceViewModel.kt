package com.skoda.launcher.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skoda.launcher.R
import com.skoda.launcher.core.BaseViewModel
import com.skoda.launcher.data.source.remote.config.APIConfig
import com.skoda.launcher.data.source.response.ApiResult
import com.skoda.launcher.data.source.response.NotificationPayload
import com.skoda.launcher.data.source.response.Subscriptions
import com.skoda.launcher.data.source.response.VehicleResponse
import com.skoda.launcher.domain.usecase.DriverDistractionUseCase
import com.skoda.launcher.domain.usecase.SubscriptionsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for managing subscriptions data and interacting with the use case layer.
 *
 * This ViewModel handles fetching subscriptions data and exposes it to the UI
 * through LiveData. It uses dependency injection to obtain the necessary use case.
 *
 * @param application The application context.
 */
class ServiceViewModel(application: Application) : BaseViewModel(application) {

    @Inject
    lateinit var subscriptionsUseCase: SubscriptionsUseCase

    @Inject
    lateinit var driverDistractionUseCase: DriverDistractionUseCase

    init {
        mainApplication.component.inject(this)
    }

    /**
     * LiveData that holds the result of the subscriptions API call.
     */
    private val responseSubscriptions: MutableLiveData<ApiResult<VehicleResponse>> =
        MutableLiveData()
    val subscriptionsLiveData: LiveData<ApiResult<VehicleResponse>> = responseSubscriptions

    /**
     * A list of subscriptions retrieved from the API.
     */
    var subscriptions: List<Subscriptions>? = null

    var currentSubscriptionId: Int = 0

    var subscriptionState: MutableLiveData<Boolean?> = MutableLiveData<Boolean?>()


    /**
     * Fetches subscriptions data from the use case and updates LiveData.
     *
     * This function launches a coroutine to call the subscriptions API and
     * collects the results. It updates the LiveData with loading status,
     * the resulting subscriptions, or any errors encountered during the process.
     */
    fun getSubscriptionsData() = viewModelScope.launch {
        responseSubscriptions.value = ApiResult.Loading()
        subscriptionsUseCase.callSubscriptionsApi()
            .collect { values ->
                subscriptions = values.data?.subscriptions
                responseSubscriptions.value = values
            }
        getDriver()
    }

    fun getDriver() {
        driverDistractionUseCase.getCarDrivingState().observeForever {
            Log.i("TAG", "getDriver:getCarDrivingState " + it)
        }

        driverDistractionUseCase.getCarUxRestrictions().observeForever {
            Log.i("TAG", "getDriver:getCarUxRestrictions " + it)
        }
    }


    fun getSubscriptionById(subId: Int): Subscriptions? {
        currentSubscriptionId = subId
        return subscriptions?.find { it.id == subId }
    }

    fun sendNotification() = viewModelScope.launch {
        getSubscriptionById(currentSubscriptionId)?.let {
            responseSubscriptions.value = ApiResult.Loading()
            subscriptionsUseCase.sendNotifications(
                NotificationPayload(
                    it.id,
                    APIConfig.VIN_NO,
                    it.name,
                    app.getString(R.string.subscription_in_progress)
                )
            ).collect { values ->
                Log.i("TAG", "sendNotification: done " + values)
                subscriptionState.postValue(true)
            }
        }
    }
}
