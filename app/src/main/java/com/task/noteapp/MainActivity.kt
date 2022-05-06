package com.task.noteapp

import android.os.Bundle
import com.task.noteapp.base.BaseActivity
import com.task.noteapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)
}