package com.http

import com.clientserivce.HttpService
import com.PreferenceUtil
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object Http {


    private const val baseUrl = "http://testapi.super-brain.co.kr"

    val clientBuilder = OkHttpClient.Builder()
    val loggingInterceptor = HttpLoggingInterceptor()


    private val httpLogger =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

    //통신
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(HttpService::class.java)

    //refresh
    private fun provideOkHttpClient(tokenAuthenticator: TokenInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLogger)
            .authenticator(tokenAuthenticator) // Called when access token or refresh token expired
            .connectTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .callTimeout(10, TimeUnit.SECONDS)
            .build()


    //refresh
    fun withTokenInterceptor(pref: PreferenceUtil): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttpClient(TokenInterceptor(pref)))
            .build()
    }
    fun preference(pref: PreferenceUtil) = withTokenInterceptor(pref).create(HttpService::class.java)


}
