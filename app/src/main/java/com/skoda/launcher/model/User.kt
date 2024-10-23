package com.skoda.launcher.model

import com.google.gson.annotations.SerializedName


data class User(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("name") var name: String? = null

)