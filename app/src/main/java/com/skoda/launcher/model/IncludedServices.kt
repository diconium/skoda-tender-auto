package com.skoda.launcher.model

import com.google.gson.annotations.SerializedName


data class IncludedServices(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("imageLink") var imageLink: String? = null

)