package com.example.ch5_transfer

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.*
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_first.*

//class FirstFragment : Fragment(R.layout.fragment_first) {
////    val mainViewModel by activityViewModels<MainViewModel>()//액티비티가 주인이 아
//
//    val getContent =
//        registerForActivityResult(ActivityResultContracts.GetContent()){
//        imageView.setImageURI(it)
//    }
//
//    val getStartActivityForResult = registerForActivityResult(
//        ActivityResultContracts.StartActivityForResult()
//    ){activityResult ->
//        //null이 아닐 때 수행한다
//        activityResult.data?.let {intent->
//            intent.extras?.let {bundle ->
//                Log.d("FirstFrag", "result: ${bundle.getString("data","world")}")
//            }
//        }
//    }
//
////여기엔 화면이 다 그려지고 나서 동작하는 코드 작성
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//    //fragment간 데이터 주고받기
////        button.setOnClickListener {
////            //네비게이트 찾기
//////            mainViewModel.data = "dkss"
////            setFragmentResult("requestKey", bundleOf("data" to "hello"))
////            findNavController().navigate(R.id.action_firstFragment_to_secondFragment)
////        }
////
////    setFragmentResultListener("requestKey"){requestKey, bundle ->
////        val data = bundle.getString("data", "")
////        Toast.makeText(requireContext(), data , Toast.LENGTH_LONG).show()
////
////    }
//
//    //startActivityForResult() 막혔다 이제는!
//    button.setOnClickListener {
//        //MINE TYPE(media type) image/*
////        getContent.launch("image/*")
//        Intent(requireContext(), ResultActivity::class.java).apply {
//            getStartActivityForResult.launch(this)
//        }
//    }
//    }
//
//}

//permission 요청 버튼 누르면 성공
class FirstFragment : Fragment(R.layout.fragment_first) {
    val requestPermission = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ){map->
        if(map[Manifest.permission.ACCESS_FINE_LOCATION]!!){
            Toast.makeText(requireContext(),"ACCESS_FINE_LOCATION 성공",Toast.LENGTH_SHORT).show()
        }
        if(map[Manifest.permission.ACCESS_COARSE_LOCATION]!!){
            Toast.makeText(requireContext(), "ACCESS_COARSE_LOCATION 성공",Toast.LENGTH_SHORT).show()
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button.setOnClickListener {
            requestPermission.launch(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            //fine이 되면 coarse도 되는것으로 봄 의미는 없음
            ))
        }
    }

}