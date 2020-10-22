package com.example.ch6_dependencyinjection.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.ch6_dependencyinjection.R
import com.example.ch6_dependencyinjection.data.MyRepo
import com.example.ch6_dependencyinjection.di.qualifier.ActivityHash
import com.example.ch6_dependencyinjection.di.qualifier.AppHash
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_second.*
import javax.inject.Inject

@AndroidEntryPoint
class SecondFragment : Fragment(R.layout.fragment_second) {
    private val viewModel by viewModels<MainViewModel>()


    lateinit var myrepo : MyRepo

    @AppHash
    @Inject
    lateinit var applicationHash:String

    @ActivityHash
    @Inject
    lateinit var activityHash:String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button.setOnClickListener {
            findNavController().navigate(R.id.action_secondFragment_to_mainFragment)


        }
        Log.d("SecondFragment", applicationHash)
        Log.d("SecondFragment", activityHash)
        Log.d("SecondFragment", viewModel.getRepoHash())
    }

}