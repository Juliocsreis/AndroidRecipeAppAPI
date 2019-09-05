package com.example.recipeappapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buPost.setOnClickListener {
           runBlocking {getData()  }
            getToken()
        }
    }

    var TAG = "mainActivity"

   fun getData(){
            val retrofitClient =
                RetrofitInitializer.getRetrofitInstance("http://recipe-app-julio.herokuapp.com/api/user/")

            val endpoint = retrofitClient.create(Requests::class.java)


            val call =
                endpoint.apiUserCreate(
                    etEmail.text.toString(),
                    etNome.text.toString(),
                    etPassword.text.toString()
                )


        call.enqueue(object : Callback<Model> {
                override fun onResponse(call: Call<Model>, response: Response<Model>) {
                    if (response.code() == 201) {
                        Toast.makeText(baseContext, "Success", Toast.LENGTH_SHORT).show()
                    } else {
                        var res = response.code().toString()
                        Toast.makeText(baseContext, res, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Model>, t: Throwable) {
                    Log.e(TAG, "Wrong call", t)
                    Toast.makeText(baseContext, "failure", Toast.LENGTH_LONG).show()
                }
            })

          }


   fun getToken(){

          val retrofitClient =
              RetrofitInitializer.getRetrofitInstance("http://recipe-app-julio.herokuapp.com/api/user/")

          val endpoint = retrofitClient.create(Requests::class.java)

          val call =
              endpoint.getToken(
                  etEmail.text.toString(),
                  etPassword.text.toString()
              )
          call.enqueue(object : Callback<Model> {
              override fun onResponse(call: Call<Model>, response: Response<Model>) {
                  if (response.code() == 200) {
                      Toast.makeText(baseContext, "Generated Token!", Toast.LENGTH_SHORT).show()

                  } else {
                      var res = response.code().toString()
                      Toast.makeText(baseContext, res, Toast.LENGTH_SHORT).show()
                  }
              }

              override fun onFailure(call: Call<Model>, t: Throwable) {
                  Log.e(TAG, "Erro to Generate Token", t)
                  Toast.makeText(baseContext, "No Token", Toast.LENGTH_LONG).show()
              }
          })

      }



}
