package com.example.edunotesbnv.roomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// list of entities and version
@Database(entities = [Result::class] , version = 1)
abstract class RoomDataB : RoomDatabase() {

    // Dao object creation
    abstract fun dao() : DAO

    companion object{
        @Volatile
        private var INSTANCE: RoomDataB? = null

        fun getDatabase(context : Context): RoomDataB {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDataB::class.java,
                    "user_database"
                ).build()
                INSTANCE =instance
                return instance
            }
        }
    }
}