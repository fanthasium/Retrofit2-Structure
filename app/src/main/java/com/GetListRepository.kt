package com

import com.http.Http
import com.requestdata.GameListReqeust
import com.responsedata.GameResult
import com.responsedata.GameResultData
import com.sharedpref.PreferenceUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import timber.log.Timber

class GetListRepository {

    suspend fun getGameList(access: String): Response<GameResultData> {

            val gameListData = Http.service.getGameList(
                "Bearer $access", GameListReqeust("RCT")
            )
            val c = gameListData.errorBody()
        return gameListData

    }
}