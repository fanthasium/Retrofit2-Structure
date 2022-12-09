package com


import android.util.Log
import androidx.lifecycle.*
import com.responsedata.GameResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ViewModelGameList @Inject constructor(
    private val getListRepository: GetListRepository,
    private val sharedPref: PreferenceUtil
) : ViewModel() {
    //외부에서 liveData로 넣어 접근 못 하게, Mutable 형식으로 내부에선 읽쓰 가능
    private val _liveData = MutableLiveData<List<GameResult>?>()
    val liveData: LiveData<List<GameResult>?> = _liveData


    /* interceptor 사용중
      private val _refreshData = MutableLiveData<String>()
      val refreshData: LiveData<String> = _refreshData*/


    // 초기값 설정
    init {
        _liveData.value = listOf()
        /*_refreshData.value = ""*/
    }


    fun gameList() {

        viewModelScope.launch {
            val listResponse =
                getListRepository.getGameList(
                    sharedPref.getString(PreferenceUtil.ACCESS_TOKEN, ""),
                    sharedPref
                )
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