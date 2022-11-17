package com.dto

import com.requestdata.GameListReqeust
import com.requestdata.LoginRequest
import com.responsedata.GameResultData
import com.responsedata.ReIssueResult
import com.responsedata.VersionData
import com.responsedata.VersionRequest
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface HttpService {

    companion object {
        const val CONTENT_TYPE = "content-type: application/json"
        private const val HEADERS_JSON2 = "X-PATH:ADMIN"
        const val xAPI_SERVICE = "x-api-service: rct"
        const val xAPI_VERSION = "x-api-version: 1"
        const val LOGIN_API = "/user/check/login"
        const val ALL_LIST_API = "/app/get/contents"
        const val RE_ISSUE_API= "/user/reissue/token"
        const val VERSION_API= "/app/get/version"
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

    @Headers(CONTENT_TYPE, HEADERS_JSON2, xAPI_VERSION)
    @POST(RE_ISSUE_API)
    fun getReissue(
        @Header(AUTHORIZATION) reToken: String
    ): Call<ReIssueResult>

    @Headers(CONTENT_TYPE, HEADERS_JSON2, xAPI_VERSION)
    @POST(VERSION_API)
    suspend fun getVersion(
        @Body type: VersionRequest
    ): Response<VersionData>

}