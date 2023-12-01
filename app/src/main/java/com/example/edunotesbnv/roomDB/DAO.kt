package com.example.edunotesbnv.roomDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(task : Result)
    @Query("SELECT * FROM RESULT_LIST Order by id desc")
    fun getAllResult(): List<Result>
}