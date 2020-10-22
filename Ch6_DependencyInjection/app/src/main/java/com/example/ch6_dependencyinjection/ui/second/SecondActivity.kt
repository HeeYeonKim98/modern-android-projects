package com.example.ch6_dependencyinjection.ui.second

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ch6_dependencyinjection.R
import com.example.ch6_dependencyinjection.data.MyRepo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SecondActivity : AppCompatActivity() {

    lateinit var myrepo : MyRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
    }
}