package com.example.ch5_transfer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.fragment_second.*

class SecondFragment : Fragment(R.layout.fragment_second) {
    val mainViewModel by activityViewModels<MainViewModel>()//액티비티가 주인이 아

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //fragment간 데이터 주고받기
        //startActivityforResult 찾아보기
        setFragmentResultListener("requestKey"){requestKey, bundle ->
            val data = bundle.getString("data", "")
            Toast.makeText(requireContext(), data ,Toast.LENGTH_LONG).show()

        }

        button2.setOnClickListener {
            //네비게이트 찾기
            findNavController().navigate(R.id.action_secondFragment_to_firstFragment)
            setFragmentResult("requestKey", bundleOf("data" to "123"))
        }
    }


}