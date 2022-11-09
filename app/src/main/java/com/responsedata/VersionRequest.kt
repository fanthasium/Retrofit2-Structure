package com.responsedata

import com.google.gson.annotations.SerializedName

data class VersionRequest(
    @SerializedName("type") val type: String
)
