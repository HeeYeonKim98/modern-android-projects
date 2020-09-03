package com.example.ch2_jetpack

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application){
    private val db = Room.databaseBuilder(
        application,
        TodoDatabase::class.java,"TodoDB"
    ).build()

    var todos:LiveData<List<Todo>>

    var newTodo:String? = null

    init {
        todos=getAll()
    }

    fun getAll(): LiveData<List<Todo>> {
        return db.todoDao().getAll()
    }

//    suspend fun insert(todo:Todo){
//        db.todoDao().Insert(todo)
//    }

    fun insert(todo:String){
        viewModelScope.launch(Dispatchers.IO) {
            db.todoDao().Insert(Todo(todo))

        }
    }
}