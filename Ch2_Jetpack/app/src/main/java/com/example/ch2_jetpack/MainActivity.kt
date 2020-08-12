package com.example.ch2_jetpack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = Room.databaseBuilder(
            applicationContext,
            TodoDatabase::class.java,"TodoDB"
        ).allowMainThreadQueries().build()

        db.todoDao().getAll().observe(this, Observer {
            ResultText.text=it.toString()
        })

        ResultText.text=db.todoDao().getAll().toString()

        TodoButton.setOnClickListener {
            db.todoDao().Insert(Todo(TodoEditText.text.toString()))
//            ResultText.text=db.todoDao().getAll().toString()
            TodoEditText.text.clear()
            Toast.makeText(this, "할 일이 추가되었습니다.", Toast.LENGTH_SHORT).show()
        }

    }
}