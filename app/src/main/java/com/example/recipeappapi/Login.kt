package com.example.recipeappapi

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface Login {

    @FormUrlEncoded
    @POST("create/")
    fun createUser(
      @Field(encoded = false, value = "email") email:String,
      @Field("name") name:String,
      @Field("password") password:String
    ): Call<Model>


}