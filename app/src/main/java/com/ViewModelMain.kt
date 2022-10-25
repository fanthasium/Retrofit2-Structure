package com


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.responsedata.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ViewModelMain : ViewModel() {
    private val _liveData = MutableLiveData<List<User>>()
    val liveData: LiveData<List<User>> = _liveData


    // 초기값 설정
    init {
        _liveData.value = listOf()
    }

    fun gameList(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            _liveData.value?.plus(user)
        }
    }


}