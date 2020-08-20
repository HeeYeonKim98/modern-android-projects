package com.example.ch2_jetpack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel=ViewModelProvider(this , ViewModelProvider.AndroidViewModelFactory(application)).get(MainViewModel::class.java)
//        val viewModel=ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.getAll().observe(this, Observer {
            ResultText.text=it.toString()
        })

//        TodoButton.setOnClickListener {
//            viewModel.insert(Todo(TodoEditText.text.toString()))
//            TodoEditText.text.clear()
//            Toast.makeText(this, "할 일이 추가되었습니다.", Toast.LENGTH_SHORT).show()
//        }

        TodoButton.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO){
                viewModel.insert(Todo(TodoEditText.text.toString()))
            }
            TodoEditText.text.clear()
            Toast.makeText(this, "할 일이 추가되었습니다.", Toast.LENGTH_SHORT).show()
        }

    }
}