package com

import com.http.Http
import com.requestdto.GameListReqeust

import com.responsedata.GameResultData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class GetListRepository @Inject constructor() {
    @Singleton
    @Provides
    suspend fun getGameList(access: String, pref: PreferenceUtil): Response<GameResultData> {
        return Http.preference(pref).getGameList(
            "Bearer $access", GameListReqeust("RCT")
        )
    }

}