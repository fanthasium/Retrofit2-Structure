package com.http

import com.dto.RequestDto
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Http {
    companion object {
        private val _LOGIN = "http://testapi.super-brain.co.kr"

        val clientBuilder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()


        val retrofit = Retrofit.Builder()
            .baseUrl(_LOGIN)
            .addConverterFactory(GsonConverterFactory.create())
            .client(clientBuilder.build())
            .build()

        val service = retrofit.create(RequestDto::class.java)

}

}
