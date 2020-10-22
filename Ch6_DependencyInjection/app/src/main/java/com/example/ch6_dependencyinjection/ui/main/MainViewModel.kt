package com.example.ch6_dependencyinjection.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.ch6_dependencyinjection.data.MyRepo

class MainViewModel @ViewModelInject constructor(
    private val myrepo :MyRepo
):ViewModel() {

    fun getRepoHash()=myrepo.toString()
}