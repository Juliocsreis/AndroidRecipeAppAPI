package com.example.recipeappapi

interface CallbackResponse<T> {
    fun success(response: T)
}