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
import com.requestdata.LoginRequest
import com.responsedata.VersionRequest
import com.sharedpref.PreferenceUtil
import kotlinx.coroutines.*
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: LoginMainBinding
    var check = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.login_main)

        Timber.plant(Timber.DebugTree())

        val intent = Intent(this@LoginActivity, InfoActivity::class.java)
        val checkRec = PreferenceUtil(this@LoginActivity).getString(PreferenceUtil.CHECK_BOX, "")

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
                PreferenceUtil(this@LoginActivity).setString(
                    PreferenceUtil.CHECK_BOX,
                    check.toString()
                )
            }
        }

        //version Check
        CoroutineScope(Dispatchers.Main).launch {
            val versionClient = Http.service.getVersion(VersionRequest("RCT"))


            if (versionClient.body()!!.result!!.info != "1.6.7") {
                Toast.makeText(this@LoginActivity, "(3초뒤 종료됩니다) 버전 업데이트를 해주세요!", Toast.LENGTH_SHORT)
                    .show()
                //BuildConfig.version name check해야함
                delay(3000)
                // 백그라운드에서 종료
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
       response 방법 이며 비동기 실행하기에 편리함
       call로 하게되면 onResponse 메서드 내의 response 를 꺼내와 데이터를 얻을 수 있는게 단점
       비동기 실행에서 mvvm 패턴을 사용한 방법이 더 효율적인 것 같다
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
                     //요청로그
                     Log.e(TAG, "initMyAPI : " + Http.retrofit.getLogin(info[0]).request())
                     //응답로그
                     val result: ResultData? = response.body()

                     if (response.isSuccessful) {
                         // 정상적으로 통신이 성공된 경우
                         Log.e("happy", "onResponse 성공: " + result?.toString())

                     } else {
                         // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                         Log.e("sad", "onResponse 실패" + response.code())
                     }
                 }

                 override fun onFailure(call: Call<ResultData>, t: Throwable) {
                     // 통신 실패 (인터넷 끊김, 예외 발생 등 시스템적인 이유)
                     Log.e("so sad", "onFailure 에러: " + t.message.toString());
                 }
             })*/
    }

}

