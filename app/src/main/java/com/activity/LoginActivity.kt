package com.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.benfordslaw.R
import com.example.benfordslaw.databinding.LoginMainBinding
import com.http.Http
import com.requestdata.LoginRequest
import com.sharedpref.PreferenceUtil
import kotlinx.coroutines.*
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import java.lang.Exception


class LoginActivity() : AppCompatActivity() {

    private lateinit var binding: LoginMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.login_main)


        Log.e("line", "---------------------------------------------")

        Http.clientBuilder.addInterceptor(Http.loggingInterceptor)
        Http.loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        //  response가 비동기 실행이기에 코루틴 사용해야함 , 그 외 비동기적 실행 구성으로 짜야함

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
                        Log.e("LoginActivity(Body)","${resultData.body()}")


                        val accessToken = resultData.body()?.result?.access
                        val user = resultData.body()?.result!!.user

                        PreferenceUtil(this@LoginActivity).setString(
                            PreferenceUtil.ACCESS_TOKEN,
                            accessToken.toString()
                        )

                        val intent = Intent(this@LoginActivity, InfoActivity::class.java)
                        intent.apply {
                            putExtra(  "name", user.name)
                            putExtra(  "gender", user.gender)
                            putExtra(  "birth", user.birth)
                        }
                        startActivity(intent)
                    }
                } catch (e: Exception) {
                    Log.e("FAILED : ", "${resultData.code()}")
                    e.printStackTrace()
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

