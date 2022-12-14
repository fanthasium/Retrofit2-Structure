package com.activity

import android.content.Intent

import android.os.Bundle
import android.util.Log
import android.widget.Toast


import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.example.benfordslaw.R
import com.example.benfordslaw.databinding.LoginMainBinding
import com.http.Http
import com.requestdto.LoginRequest
import com.responsedata.VersionRequest
import com.PreferenceUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: LoginMainBinding
    var check = false
    @Inject lateinit var preferenceUtil: PreferenceUtil


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.login_main)

        Timber.plant(Timber.DebugTree())

        val intent = Intent(this@LoginActivity, InfoActivity::class.java)
        val checkRec = preferenceUtil.getString(PreferenceUtil.CHECK_BOX, "")

        Timber.tag("line").e("---------------------------------------------")


        if (checkRec == "true") {
            startActivity(intent)
            finish()
            Timber.e("AUTO CHECK: SUCCESS")
        }

        Http.clientBuilder.addInterceptor(Http.loggingInterceptor)
        Http.loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        // checkbox o,x
        binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                check = true
                preferenceUtil.setString(
                    PreferenceUtil.CHECK_BOX,
                    check.toString()
                )
            }
        }

        //version Check
        CoroutineScope(Dispatchers.Main).launch {
            val versionClient = Http.service.getVersion(VersionRequest("RCT"))


            if (versionClient.body()!!.result!!.info != "1.6.7") {
                Toast.makeText(this@LoginActivity, "(3?????? ???????????????) ?????? ??????????????? ????????????!", Toast.LENGTH_SHORT)
                    .show()
                //BuildConfig.version name check?????????
                delay(3000)
                // ????????????????????? ??????
                ActivityCompat.finishAffinity(this@LoginActivity)
            }

        }

        binding.loginTxtView.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {

                val resultData = Http.service.getLogin(
                    data = LoginRequest(
                        id = binding.idEditTxtView.text.toString(),
                        password = binding.passwordEditTxtView.text.toString(),
                        role = "RCT",
                        token = ""
                    )
                )

                try {
                    if (resultData.isSuccessful) {
                        Timber.e("${resultData.body()}")
                        Log.e("LoginActivity(Body)", "${resultData.body()}")

                        val accessToken = resultData.body()?.result?.access
                        val reFreshToken = resultData.body()?.result?.refresh
                        val user = resultData.body()?.result!!.user


                        //set Token
                        PreferenceUtil(this@LoginActivity).apply {
                            setString(
                                PreferenceUtil.ACCESS_TOKEN,
                                accessToken.toString()
                            )
                            setString(
                                PreferenceUtil.REFRESH_TOKEN,
                                reFreshToken.toString()
                            )
                        }

                        intent.apply {
                            putExtra("name", user.name)
                            putExtra("gender", user.gender)
                            putExtra("birth", user.birth)
                        }

                        startActivity(intent)
                        finish()
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e("FAILED : ", "${resultData.code()}")
                }
            }
        }


        /*
       response ?????? ?????? ????????? ??????????????? ?????????
       call??? ???????????? onResponse ????????? ?????? response ??? ????????? ???????????? ?????? ??? ????????? ??????
       ????????? ???????????? mvvm ????????? ????????? ????????? ??? ???????????? ??? ??????
       */


/*      responseData["token"] = responseResult?.result?.access.toString()

   val sharedPreferences = getSharedPreferences("access", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        editor.putString("pastToken",responseData["token"])

        if(responseResult?.result?.access == sharedPreferences.getString("pastToken","")){
            editor.putString("pastToken",responseData["token"])
            toast()
        }*/


        /* Http.retrofit.getLogin(info[0])
             .enqueue(object : Callback<ResultData> {
                 override fun onResponse(call: Call<ResultData>, response: Response<ResultData>) {
                     //????????????
                     Log.e(TAG, "initMyAPI : " + Http.retrofit.getLogin(info[0]).request())
                     //????????????
                     val result: ResultData? = response.body()

                     if (response.isSuccessful) {
                         // ??????????????? ????????? ????????? ??????
                         Log.e("happy", "onResponse ??????: " + result?.toString())

                     } else {
                         // ????????? ????????? ??????(???????????? 3xx, 4xx ???)
                         Log.e("sad", "onResponse ??????" + response.code())
                     }
                 }

                 override fun onFailure(call: Call<ResultData>, t: Throwable) {
                     // ?????? ?????? (????????? ??????, ?????? ?????? ??? ??????????????? ??????)
                     Log.e("so sad", "onFailure ??????: " + t.message.toString());
                 }
             })*/
    }

}

