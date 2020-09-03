package com.example.ch2_jetpack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.ch2_jetpack.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,R.layout.activity_main) //데이터 바인딩 -> xml에 있는 데이터가 모두 담겨있다.
        binding.lifecycleOwner=this //livedata를 활용하기 위해 , livedata를 관찰해야

        val viewModel=ViewModelProvider(this , ViewModelProvider.AndroidViewModelFactory(application)).get(MainViewModel::class.java)
        binding.viewModel=viewModel


//        viewModel.getAll().observe(this, Observer {
//            ResultText.text=it.toString()
//        })
        //->databinding을 사용하면 생략가능

//        TodoButton.setOnClickListener {
//            lifecycleScope.launch(Dispatchers.IO){
//                viewModel.insert(TodoEditText.text.toString())
//                TodoEditText.text.clear()
//            }
//
//        }

    }
}