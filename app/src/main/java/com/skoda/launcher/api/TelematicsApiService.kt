package com.skoda.launcher.api

import retrofit2.http.GET
import retrofit2.http.POST


interface TelematicsApiService {
    @GET("/api/vehicle")
    fun getVehicleData(): retrofit2.Response<String>

    @POST("/api/update")
    fun sendVehicleSpeed(value: String): retrofit2.Response<String>
}