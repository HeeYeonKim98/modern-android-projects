package com.example.ch4_counterkt

import android.app.Activity
import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.ch4_counterkt.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //현재는 액티비티가 사라지면 데이터도 같이 죽어버린다.
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        binding.viewModel = viewModel

        //MainActivity 의 라이프사이클에 맞춰서 동작하겠다.
        binding.lifecycleOwner = this

        //ui 갱신을 라이브 데이터로 구현
//        viewModel.countLiveData.observe(this, Observer {count->
//            counter_text.text = "$count"
//        }) -> binding 으로 옵저버는 제거함

        button_plus.setOnClickListener {
            viewModel.increaseCount()
        }

        button_minus.setOnClickListener {
            viewModel.decreaseCount()
        }

    }

//    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
//        super.onSaveInstanceState(outState, outPersistentState)
//        outState.putInt("count", viewModel.count )
//    }
//
//    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
//        super.onRestoreInstanceState(savedInstanceState)
//        viewModel.count = savedInstanceState.getInt("count")
//    }
}