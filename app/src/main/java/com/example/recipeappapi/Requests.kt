package com.example.recipeappapi


import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface Requests {

    @FormUrlEncoded
    @POST("create/")
    fun apiUserCreate(
      @Field(encoded = false, value = "email") email:String,
      @Field("name") name:String,
      @Field("password") password:String
    ): Call<Model>

    @FormUrlEncoded
    @POST("token/")
    fun getTokenCode(
        @Field(encoded = false, value = "email") email:String,
        @Field("password") password:String
    ): Call<ResponseBody>



    @GET("me/")
    fun mePage(): Call<ResponseBody>

    @PATCH("me/")
    fun alterarNome(
        @Field("name") name: String
    ):Call<ResponseBody>


}