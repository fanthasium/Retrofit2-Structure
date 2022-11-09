package com.responsedata

import com.google.gson.annotations.SerializedName

data class VersionData(
    @SerializedName("result") val result: Version? = null
)

data class Version(
    @SerializedName("info") val info: String,
    @SerializedName("type") val type: String
)

