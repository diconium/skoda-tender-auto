package com.skoda.launcher.domain.repository;

import com.skoda.launcher.api.TelematicsApiService
import retrofit2.Response
import javax.inject.Inject

class TelematicsRepository @Inject constructor(val apiService: TelematicsApiService) {

    fun getVehicleInfo(): Response<String> {
        return apiService.getVehicleData()
    }

    fun sendVehicleSpeed(json: String): Response<String> {
        return apiService.sendVehicleSpeed(json)
    }
}