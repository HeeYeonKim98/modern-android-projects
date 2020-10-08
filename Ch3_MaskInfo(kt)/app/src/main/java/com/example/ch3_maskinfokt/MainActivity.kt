package com.example.ch3_maskinfokt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ch3_maskinfokt.model.Store
import kotlinx.android.synthetic.main.activity_main.*

//코틀린에서는 var과 null타입을 잘 안쓰려고 한다.
class MainActivity : AppCompatActivity() {
    private val viewModel :MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val storeAdapter = StoreAdapter()

        recyclerview_store.apply {
            //setter
            recyclerview_store.layoutManager = LinearLayoutManager(applicationContext,
                RecyclerView.VERTICAL,
                false
            )
            adapter = storeAdapter
        }

        //임시 데이터
//        val items = listOf(
//            Store("abd","111","111",111.111,222.222, "약국",
//            "plenty","33","33") ,
//            Store("abd","111","111",111.111,222.222, "약국",
//            "plenty","33","33")
//        )
//
//        storeAdapter.updateItems(items)

    }
}