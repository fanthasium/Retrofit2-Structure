package com.responsedata

import com.google.gson.annotations.SerializedName

data class GameResultData(
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: List<GameResult>? = null
)

data class GameResult(
    @SerializedName("title") val title: String,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("kind") val kind: String,
    @SerializedName("category") val category: String
)