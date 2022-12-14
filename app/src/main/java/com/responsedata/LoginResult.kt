package com.clientserivce

import com.google.gson.annotations.SerializedName
import com.responsedata.User


data class LoginResultData(
    @SerializedName("result") val result: LoginResult? = null
)

data class LoginResult(
    @SerializedName("access") val access: String,
    @SerializedName("refresh") val refresh: String,
    @SerializedName("user") val user: User
)