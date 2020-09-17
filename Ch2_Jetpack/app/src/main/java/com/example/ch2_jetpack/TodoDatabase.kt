package com.example.ch2_jetpack

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Todo::class], version = 1, exportSchema = false)
abstract class TodoDatabase:RoomDatabase()  {
    abstract fun todoDao():TodoDao
}