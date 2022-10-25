package com.activity


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.benfordslaw.R
import com.example.benfordslaw.databinding.UserInformBinding
import com.http.Http
import com.requestdata.GameListReqeust
import com.sharedpref.PreferenceUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class InfoActivity : AppCompatActivity() {

    lateinit var binding: UserInformBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.user_inform)

        val sharedPref = PreferenceUtil(this).getString(PreferenceUtil.ACCESS_TOKEN, "")


        binding.userInfoTxtView.text = intent.getStringExtra("name")



        binding.listBtn.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val resultData = Http.service.getGameList(
                    "Bearer $sharedPref",GameListReqeust("RCT")
                )
              Log.e("zzzzzzz","${resultData.code()}")
            }
        }

    }
}