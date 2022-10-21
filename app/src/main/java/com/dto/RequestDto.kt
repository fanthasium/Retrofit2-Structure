package com.dto

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RequestDto {

 @Headers("content-type: application/json")
 @POST("/user/check/login")
 suspend fun getLogin(@Body data: RequestData) : Response<ResultData>
}