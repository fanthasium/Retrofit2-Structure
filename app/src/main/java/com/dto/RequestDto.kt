package com.dto

import com.requestdata.GameListReqeust
import com.requestdata.LoginRequest
import com.responsedata.GameResultData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface RequestDto {

    companion object {
        const val CONTENT_TYPE = "content-type: application/json"
        const val xAPI_SERVICE = "x-api-service: rct"
        const val xAPI_VERSION = "x-api-version: 1"
        const val LOGIN_API = "/user/check/login"
        const val ALL_LIST_API = "/app/get/contents"
        private const val AUTHORIZATION = "Authorization"
    }

    @Headers(CONTENT_TYPE)
    @POST(LOGIN_API)
    suspend fun getLogin(@Body data: LoginRequest): Response<LoginResultData>

    @Headers(CONTENT_TYPE, xAPI_SERVICE, xAPI_VERSION)
    @POST(ALL_LIST_API)
    suspend fun getGameList(
        @Header(AUTHORIZATION) token: String,
        @Body data: GameListReqeust
    ): Response<GameResultData>


}