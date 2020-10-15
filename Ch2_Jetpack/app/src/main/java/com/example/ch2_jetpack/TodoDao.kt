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