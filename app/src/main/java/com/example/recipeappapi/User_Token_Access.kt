package com.example.recipeappapi

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.recipeappapi.MainActivity.Companion.REQUEST_CODE
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_user__token__access.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class User_Token_Access : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user__token__access)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode== REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            var dadosUsuario = intent.extras.get("dados") as String
            var nomeUser = dadosUsuario.substringAfter("name\":")
                .replace("\"", "")
                .replace("}", "")

            val emailUser = dadosUsuario.substringAfter("email\":")
                .substringBefore(",")
                .replace("\"", "")
            etEmail.setText(emailUser)
            Log.v("Email USer", emailUser)

        }
    }

    val retrofitClient =
        RetrofitInitializer.getRetrofitInstance()
    val endpoint = retrofitClient.create(Requests::class.java)
    val retrofitClient2 =
        RetrofitInitializer.retrofitAuth()
    val endpoint2 = retrofitClient2.create(Requests::class.java)


    fun alterarNomeUser(){ val call =
        endpoint2.alterarNome(
            tvName.text.toString()
        )

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == 200) {
                    Toast.makeText(baseContext, "Alterado com sucesso", Toast.LENGTH_SHORT).show()
                    //{"email": "blax@gmail.com", "name": "birl"}
                    //meUser = response.body().toString()
                    Log.v("PATCH", response.body().toString())
                } else {
                    var res = response.code().toString()
                    Toast.makeText(baseContext, res, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.v("PATCH", "Wrong call", t)
                Toast.makeText(baseContext, "failure", Toast.LENGTH_LONG).show()
            }
        })
    }

}
