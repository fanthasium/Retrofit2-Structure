package com.requestdata

import com.google.gson.annotations.SerializedName

data class GameListReqeust (
    @SerializedName("type") val type : String,
    )