package com.example.edunotesbnv.roomDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RESULT_LIST")
data class Result(
    @PrimaryKey(autoGenerate = true)
    var id : Int,
    var timeStamp : String,
    var topic : String,
    var resultScore : Int,
    var quizSize : Int
)
