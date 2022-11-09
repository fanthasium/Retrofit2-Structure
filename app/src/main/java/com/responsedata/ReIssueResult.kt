package com.responsedata

import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.internal.SynchronizedObject
import kotlinx.parcelize.Parcelize


data class ReIssueResult(
    @SerializedName("result") val result: Result?,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
)

data class Result(
    @SerializedName("access") val access: String,
    @SerializedName("refresh") val refresh: String?,
    )


