package com.skoda.launcher.api

import com.skoda.launcher.model.VehicleResponse
import retrofit2.Response
import retrofit2.http.GET

interface APIService {

    @GET("https://www.jsonkeeper.com/b/G057")
    suspend fun getApi(): Response<VehicleResponse>
}