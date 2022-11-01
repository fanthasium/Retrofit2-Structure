package com.activity


import android.os.Bundle
import android.util.Log
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil

import com.example.benfordslaw.R
import com.example.benfordslaw.databinding.UserInformBinding
import com.fragment.ListFragment


class InfoActivity : AppCompatActivity() {

    lateinit var binding: UserInformBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.user_inform)
        binding.listBtn.visibility = View.VISIBLE


      /* viewModels 라이브러리로 viewModelProvider 필요없이 바로 상속받을 수 있다
       viewModel = ViewModelProvider(this)[ViewModelLogin::class.java]
       */
        binding.userInfoTxtView.text = intent.getStringExtra("name")

        binding.listBtn.setOnClickListener {
            binding.listBtn.visibility = View.GONE
            supportFragmentManager.beginTransaction().add(R.id.screenView, ListFragment()).commit()
        }

    }
}