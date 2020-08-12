package com.example.ch2_jetpack

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(var title:String) {
    @PrimaryKey(autoGenerate = true) var id:Int = 0
}

//Todo table 선언