package com.activity


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelStore
import com.ViewModelMain
import com.example.benfordslaw.R
import com.example.benfordslaw.databinding.UserInformBinding
import com.responsedata.User

class InfoActivity:AppCompatActivity() {

    lateinit var binding : UserInformBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.user_inform)

      ViewModelMain().liveData.observe(this){
            binding.userInfoTxtView.text = it[0].name
        }


    }
}