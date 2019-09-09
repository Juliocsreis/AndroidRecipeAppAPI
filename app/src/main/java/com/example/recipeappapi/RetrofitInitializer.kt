package com.example.recipeappapi

import android.util.Log
import com.example.recipeappapi.MainActivity.Companion.token
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitInitializer{

    companion object {
        @JvmStatic
        val API_BASE_UREL = "http://recipe-app-julio.herokuapp.com/api/user/"


        var headerIntercepetor: Interceptor = object : Interceptor{
            override fun intercept(chain: Interceptor.Chain): Response {
                var request = chain.request()


                request = request.newBuilder()
                    .addHeader("Authorization", token)
                    //.addHeader("Accept-Language", Locale.getDefault().language)
                    .build()

                val response = chain.proceed(request)
                return response
            }
        }


        val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
            this.level = HttpLoggingInterceptor.Level.HEADERS
        }


        val client1 : OkHttpClient = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
                    // Usando tempo longo de timeOut porque Dyno Heroku Free -> Sleeps
                .callTimeout(15,TimeUnit.SECONDS)
        }.build()

        //Usando dois Clients por enquanto, depois criar condição If Else para quando A API
        // solicitar Headers diferentes
        val client2 : OkHttpClient = OkHttpClient.Builder().apply {
            this.addInterceptor(headerIntercepetor)
            this.addInterceptor(interceptor)
                // Usando tempo longo de timeOut porque Dyno Heroku Free -> Sleeps
                .callTimeout(15,TimeUnit.SECONDS)
        }.build()




        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(API_BASE_UREL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client1)
                .build()
        }

        fun retrofitAuth(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(API_BASE_UREL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client2)
                .build()
        }
    }
}





