package com.example.edunotesbnv.dataApi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIInterface {

    @GET("questions?apiKey=NYcouhHPx9oT8CBnxPK69Rs5otL5HIAsW7kbDu4d&limit=10")
    fun getQuiz(@Query("tags") tags :String): Call<QuizQuestionsList>
}