package com.example.recipeappapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.recipeappapi.MainActivity.Companion.meUser
import kotlinx.android.synthetic.main.activity_user__token__access.*

class User_Token_Access : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user__token__access)

        tvCongrats.setText("Parabéns você conseguiu ${nomeUser}")
        tvEmail.setText(emailUser)
        tvName.setText(nomeUser)
        val meUser = intent?.extras?.get("dados") as String
    }


    //{"email":"meAmo@gmail.com","name":"José"}
    val nomeUser = meUser?.substringAfter("name\":")
                    ?.replace("\"","")
                    ?.replace("}","")

    val emailUser= meUser?.substringAfter("email\":")
                    ?.substringBefore(",")
                    ?.replace("\"","")
}
