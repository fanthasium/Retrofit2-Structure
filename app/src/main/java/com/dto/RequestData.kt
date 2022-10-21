package com.dto

import com.google.gson.annotations.SerializedName

data class RequestData(
    @SerializedName("id") val id : String,
    @SerializedName("password") val password : String,
    @SerializedName("role") val role : String,
    @SerializedName("token ") val token : String,
)