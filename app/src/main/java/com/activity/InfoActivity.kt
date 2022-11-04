package com.activity


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ViewModelGameList

import com.example.benfordslaw.R
import com.example.benfordslaw.databinding.UserInformBinding
import com.fragment.ListFragment
import com.sharedpref.PreferenceUtil


class InfoActivity : AppCompatActivity() {

    lateinit var binding: UserInformBinding
    var lastTimeBackPressed = 0L
    val viewModel: ViewModelGameList by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.user_inform)
        binding.listBtn.visibility = View.VISIBLE

       binding.userInfoTxtView.text = ""
      /* viewModels 라이브러리로 viewModelProvider 필요없이 바로 상속받을 수 있다
       viewModel = ViewModelProvider(this)[ViewModelLogin::class.java]
       */
       binding.listBtn.setOnClickListener {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.screenView,ListFragment())
                .commit()
        }
        binding.logOutTxtView.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)

            PreferenceUtil(this).apply {
                remove(PreferenceUtil.ACCESS_TOKEN)
                remove(PreferenceUtil.CHECK_BOX)
            }
            startActivity(intent)
            finish()
        }
    }


    override fun onBackPressed() {
        if(System.currentTimeMillis() - lastTimeBackPressed < 1500){
            finish()
        }
        lastTimeBackPressed = System.currentTimeMillis()
        Toast.makeText(this,"한번더 누르면 종료됩니다.",Toast.LENGTH_SHORT).show()
    }

   /* fun remove(){
        requireActivity().supportFragmentManager.beginTransaction().remove(this)
    }*/
}
