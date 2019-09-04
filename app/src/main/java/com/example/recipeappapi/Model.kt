package com.example.recipeappapi

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose




class Model {

    @SerializedName("email")
    @Expose
    var email: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null

}





