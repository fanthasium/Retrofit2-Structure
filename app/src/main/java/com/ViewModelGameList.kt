package com


import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.responsedata.GameResult
import com.sharedpref.PreferenceUtil
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


    fun gameList(sharedPref: PreferenceUtil) {

        viewModelScope.launch {
            val listResponse =
                getListRepository.getGameList(sharedPref.getString(PreferenceUtil.ACCESS_TOKEN, ""),sharedPref)
            Log.e("VIEWMODEL ", "GOOOD?")

            //실패시 토큰 만료 or 통신실패
            if (listResponse.isSuccessful) {
                _liveData.value = listResponse.body()?.result
                Log.e("VIEWMODEL : SUCCESS", "${_liveData.value}")
                Log.e("VIEWMODEL : CODE", "${listResponse.code()}")
            }
        }

    }


}