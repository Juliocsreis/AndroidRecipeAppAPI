package com.example.recipeappapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody
import retrofit2.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buPost.setOnClickListener {
            getData()
        }



        buLogar.setOnClickListener {
            var simples = "simples"
            var intent= Intent(this@MainActivity,User_Token_Access::class.java)
            intent.putExtra("simples", simples)
            startActivity(intent)
        }


    }


    var TAG = "mainActivity"

    //Retrofit Utilities
    companion object{
        var token: String= ""
        var REQUEST_CODE = 1}




    val retrofitClient =
        RetrofitInitializer.getRetrofitInstance()
    val endpoint = retrofitClient.create(Requests::class.java)
    val retrofitClient2 =
        RetrofitInitializer.retrofitAuth()
    val endpoint2 = retrofitClient2.create(Requests::class.java)



   fun getData(){

            val call =
                endpoint.apiUserCreate(
                    etEmail.text.toString(),
                    etNome.text.toString(),
                    etPassword.text.toString()
                )

        call.enqueue(object : Callback<Model> {
                override fun onResponse(call: Call<Model>, response: Response<Model>) {
                    if (response.code() == 201) {
                        Toast.makeText(baseContext, "Cadastrado com sucesso", Toast.LENGTH_SHORT).show()
                        response.body().toString()
                        getTokenCode()
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


   fun getTokenCode(){

          val call =
              endpoint.getTokenCode(
                  etEmail.text.toString(),
                  etPassword.text.toString()
              )
          call.enqueue(object : Callback<ResponseBody> {
              override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                  if (response.code() == 200) {
                      token = response.body()!!.string()
                          .replace("\"", "")
                          .replace(":"," ")
                          .removeSurrounding("{","}")
                      // {"token": "1ff6b71e385e4b97615be86906de9a672509ece9"}
                      tokenAccess()
                  } else {
                      var res = response.code().toString()
                      Toast.makeText(baseContext, res, Toast.LENGTH_SHORT).show()
                  }
              }

              override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                  Log.e(TAG, "Erro to Generate Token", t)
                  Toast.makeText(baseContext, "No Token", Toast.LENGTH_LONG).show()
              }
          })
      }


    fun tokenAccess(){

        val call= endpoint2.mePage()

        call.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == 200) {
                    Toast.makeText(baseContext, "Fazendo Login", Toast.LENGTH_SHORT).show()
                    val meUser = response.body()!!.string()
                    Log.e("Token Gerado", meUser)
                    //{"email":"meAmo@gmail.com","name":"José"}

                    var userName = meUser.substringAfter("name\":")
                        .replace("\"", "")
                        .replace("}", "")



//                    startActivityForResult(intent, REQUEST_CODE)

                    Log.e("INTENT", meUser)
                    Log.e("INTENT2", intent.toString())
//                    Log.e("INTENT3", intent.getStringExtra("dados"))

            }else {
                    var res = response.code().toString()
                    Toast.makeText(baseContext, res, Toast.LENGTH_SHORT).show()
                    Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
                    Log.e("Token", token)
                    Log.e("Response", res)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e(TAG, "Não consegui acesso", t)
                Toast.makeText(baseContext, "Failed Me Page", Toast.LENGTH_LONG).show()
            }
        })

    }



}
