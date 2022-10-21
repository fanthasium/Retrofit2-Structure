package com.responsedata

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("name") val name: String?,
    @SerializedName("birth") val birth: String,
    @SerializedName("education") val education: Int,
    @SerializedName("gender") val gender: String,
    @SerializedName("weight") val weight: Double? = null,
    @SerializedName("height") val height: Double? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("phone") val phone: String? = null,
    @SerializedName("job") val job: String? = null,
    @SerializedName("create_at") val createAt: String
)