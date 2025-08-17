package com.example.remoterepository

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val newUrl = original.url.newBuilder()
            .addQueryParameter("apikey", apiKey)
            .build()
        val request = original.newBuilder().url(newUrl).build()
        return chain.proceed(request)
    }
}