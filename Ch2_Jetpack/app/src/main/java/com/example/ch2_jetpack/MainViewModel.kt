package com.example.ch2_jetpack

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.room.Room

class MainViewModel(application: Application) : AndroidViewModel(application){
    private val db = Room.databaseBuilder(
        application,
        TodoDatabase::class.java,"TodoDB"
    ).allowMainThreadQueries().build()

    fun getAll(): LiveData<List<Todo>> {
        return db.todoDao().getAll()
    }

    suspend fun insert(todo:Todo){
        db.todoDao().Insert(todo)
    }
}