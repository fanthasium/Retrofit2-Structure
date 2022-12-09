package com.http

import android.util.Log
import com.PreferenceUtil
import okhttp3.*

class TokenInterceptor(
    val pref: PreferenceUtil
) : Authenticator {

    companion object {
        private val TAG = TokenInterceptor::class.java.simpleName
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        val refreshToken = pref.getString(PreferenceUtil.REFRESH_TOKEN, "")
        Log.e("401 ERROR", "${response.code()}")
        val c = response.request()


        if (response.code() == 401) {
            val c1 = Http.preference(pref).getReissue("Bearer $refreshToken").execute()


            // refreshToken
            if (c1.isSuccessful) {
                Log.e("200", "SUCCESS")

                //재생성된 access 토큰 저장
                pref.setString(
                    "ACCESS_TOKEN",
                    "${c1.body()?.result?.access}"
                )

                //수동 x 자동으로 하게 끔
                if (c1.body()?.result?.access != pref.getString("ACCESS_TOKEN", "")) {
                    Log.e("200", "")
                    return c.newBuilder()
                        .removeHeader("Authorization")
                        .header("Authorization", "Bearer $refreshToken")
                        .build()
                }

            }
        }
        //리프레쉬 토큰 만료에대한 예외 처리가 없어서 일단 null
        return null
    }

}
