package com.task.noteapp.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding> : Fragment() {
    private var _binding: VB? = null

     val binding: VB
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding()
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onTitleChange()
    }

    abstract fun getViewBinding(): VB

    abstract fun onTitleChange()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}