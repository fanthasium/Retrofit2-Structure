package com

import android.util.Log
import com.activity.InfoActivity
import com.fragment.ListFragment
import com.http.Http
import com.requestdata.GameListReqeust

import com.responsedata.GameResultData
import com.responsedata.ReIssueResult
import com.sharedpref.PreferenceUtil

import retrofit2.Response


class GetListRepository {

    suspend fun getGameList(access: String, pref: PreferenceUtil): Response<GameResultData> {
        val gameListData = Http.preference(pref).getGameList(
            "Bearer $access", GameListReqeust("RCT")
        )
        return gameListData
    }

}