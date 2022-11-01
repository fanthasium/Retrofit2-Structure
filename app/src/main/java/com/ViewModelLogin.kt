package com


import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.http.Http
import com.requestdata.GameListReqeust
import com.responsedata.GameResult
import com.sharedpref.PreferenceUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ViewModelLogin : ViewModel() {
    //외부에서 liveData로 넣어 접근 못 하게, Mutable 형식으로 내부에선 읽쓰 가능
    private val _liveData = MutableLiveData<List<GameResult>?>()
    val liveData: LiveData<List<GameResult>?> = _liveData


    // 초기값 설정
    init {
        _liveData.value = listOf()
    }


    fun gameList(context: Context) {
        val sharedPref = PreferenceUtil(context).getString(PreferenceUtil.ACCESS_TOKEN, "")

        viewModelScope.launch {
            val resultData = Http.service.getGameList(
                "Bearer $sharedPref", GameListReqeust("RCT")
            )

            if (resultData.isSuccessful) {
                _liveData.value = resultData.body()?.result
                Log.e("ViewModel: LiveData","${liveData.value}")
            }

        }

    }

}