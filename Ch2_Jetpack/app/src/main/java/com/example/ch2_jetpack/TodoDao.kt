package com.example.ch2_jetpack

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TodoDao {
    @Query("SELECT * FROM Todo")
    fun getAll():LiveData<List<Todo>>

    @Insert
    fun Insert(todo: Todo)

    @Update
    fun Update(todo: Todo)

    @Delete
    fun Delete(todo: Todo)
}

//Todo.kt에서 만든 Todo table에 대한 쿼리문을 작성하고, insert;update;delete 기능 선언