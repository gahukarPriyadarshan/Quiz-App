package com.example.edunotesbnv.dataApi

import com.example.edunotesbnv.dataApi.Answers
import com.example.edunotesbnv.dataApi.CorrectAnswers

data class Quiz(
    val answers: Answers,
    val category: String,
    val correct_answers: CorrectAnswers,
    val description: String,
    val difficulty: String,
    val explanation: String,
    val id: Int,
    val multiple_correct_answers: String,
    val question: String,
    val tags: List<Any>,
    val tip: Any
)