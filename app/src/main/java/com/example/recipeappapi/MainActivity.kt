package com.example.recipeappapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        buPost.setOnClickListener {getData()}




    }



   fun getData(){
       var emailPronto = etEmail.text.toString()
       val retrofitClient =
           RetrofitInitializer.getRetrofitInstance("http://recipe-app-julio.herokuapp.com/api/user/")

       val endpoint = retrofitClient.create(Login::class.java)
       val call =
           endpoint.createUser(emailPronto,etNome.text.toString(),etPassword.text.toString())

       call.enqueue(object: Callback<Model>{
           override fun onFailure(call: Call<Model>, t: Throwable) {
               var TAG = "mainActivity"
               Log.e(TAG, "Erro da solicitação", t)
               Toast.makeText(baseContext, "failure", Toast.LENGTH_LONG).show()
           }

           override fun onResponse(call: Call<Model>, response: Response<List<Model>>) {
              if (response.code() == 201) {
                  Toast.makeText(baseContext, "Funcionou!", Toast.LENGTH_SHORT).show()
              }else {
                  var res = response.code().toString()
                  Toast.makeText(baseContext,res, Toast.LENGTH_SHORT).show()
              }
           }
       })
   }

}
