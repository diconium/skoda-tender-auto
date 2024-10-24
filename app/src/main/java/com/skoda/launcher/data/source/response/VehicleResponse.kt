package com.skoda.launcher.data.source.response

import com.google.gson.annotations.SerializedName


data class VehicleResponse(

    @SerializedName("user") var user: User? = User(),
    @SerializedName("car") var car: Car? = Car(),
    @SerializedName("subscriptions") var subscriptions: ArrayList<Subscriptions> = arrayListOf()

)