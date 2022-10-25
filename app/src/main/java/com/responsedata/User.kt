package com.responsedata

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("name") val name: String?,
    @SerializedName("birth") val birth: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("create_at") val createAt: String
)