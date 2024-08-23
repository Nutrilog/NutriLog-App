package com.nutrilog.app.core.data.remote

import com.nutrilog.app.core.data.pref.UserSessionPreference
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(
    private val pref: UserSessionPreference,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val token =
            runBlocking {
                pref.getToken()
            }

        return if (token.isNotEmpty()) {
            val authorized =
                original.newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .addHeader("Content-Type", "application/json")
                    .build()
            chain.proceed(authorized)
        } else {
            chain.proceed(original)
        }
    }
}
