package com.responsedata

import com.dto.LoginResult
import com.google.gson.annotations.SerializedName

data class GameResultData(
    @SerializedName("result") val result: List<GameResult>? = null
)

data class GameResult(

    @SerializedName("title") val title: String,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("kind") val kind: String,
    @SerializedName("category") val category: String
)