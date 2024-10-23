package com.skoda.launcher.model

import com.google.gson.annotations.SerializedName
import com.skoda.launcher.model.IncludedServices


data class Subscriptions(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("imageLink") var imageLink: String? = null,
    @SerializedName("length") var length: Int? = null,
    @SerializedName("price") var price: Double? = null,
    @SerializedName("includedServices") var includedServices: ArrayList<IncludedServices> = arrayListOf(),
    @SerializedName("status") var status: String? = null,
    @SerializedName("startDate") var startDate: String? = null,
    @SerializedName("endDate") var endDate: String? = null

)