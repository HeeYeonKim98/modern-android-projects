package com.example.ch6_dependencyinjection.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.ch6_dependencyinjection.R
import com.example.ch6_dependencyinjection.data.MyRepo
import com.example.ch6_dependencyinjection.di.qualifier.ActivityHash
import com.example.ch6_dependencyinjection.di.qualifier.AppHash
import com.example.ch6_dependencyinjection.ui.second.SecondActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {
    private val viewModel by viewModels<MainViewModel>()
    private val activityViewModel by activityViewModels<MainViewModel>()

    @Inject
    lateinit var myrepo : MyRepo

    @AppHash
    @Inject
    lateinit var applicationHash:String

    @ActivityHash
    @Inject
    lateinit var activityHash:String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        button_activity.setOnClickListener {
            val intent = Intent(requireContext(),SecondActivity::class.java)
            startActivity(intent)
        }
        button_fragment.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_secondFragment)
        }
        Log.d("MainFragment", applicationHash)
        Log.d("MainFragment", activityHash)
        Log.d("MainFragment", viewModel.getRepoHash())
    }
}