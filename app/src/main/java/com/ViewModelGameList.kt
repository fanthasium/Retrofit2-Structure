package com


import android.content.ContentValues.TAG
import android.content.Context
import android.security.identity.ResultData
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.http.Http
import com.requestdata.GameListReqeust
import com.responsedata.GameResult
import com.responsedata.ReIssueResult
import com.sharedpref.PreferenceUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.lang.Exception


class ViewModelGameList : ViewModel() {
    //외부에서 liveData로 넣어 접근 못 하게, Mutable 형식으로 내부에선 읽쓰 가능
    private val _liveData = MutableLiveData<List<GameResult>?>()
    val liveData: LiveData<List<GameResult>?> = _liveData

    private val _refreshData = MutableLiveData<String>()
    val refreshData: LiveData<String> = _refreshData

    private val getListRepository = GetListRepository()
    // 초기값 설정
    init {
        _liveData.value = listOf()
        _refreshData.value = ""
    }


    fun gameList(context: Context) {
        val sharedPref = PreferenceUtil(context).getString(PreferenceUtil.ACCESS_TOKEN, "")
        val REFRESH_TOKEN = PreferenceUtil(context).getString(PreferenceUtil.REFRESH_TOKEN, "")

        viewModelScope.launch {
     /*       val gameListData = Http.service.getGameList(
                "Bearer $sharedPref", GameListReqeust("RCT")
            )
            val c = gameListData.errorBody()
*/
            val listResponse = getListRepository.getGameList(sharedPref)
            if (listResponse.isSuccessful) {
            _liveData.value = listResponse.body()?.result

        } else {
                //토큰 재생성 클라이언트
                val reIssue = Http.service.getReissue("Bearer $REFRESH_TOKEN")
                try {

                    if (reIssue.isSuccessful) {
                        //재생성된 access 토큰 저장
                        PreferenceUtil(context).setString(
                            "ACCESS_TOKEN",
                            "${reIssue.body()?.result?.access}"
                        )
                    }
                } catch (e: Exception) {
                    Timber.tag("ERROR").e("${reIssue.code()}")
                    e.printStackTrace()
                }

            }


        }

    }


}