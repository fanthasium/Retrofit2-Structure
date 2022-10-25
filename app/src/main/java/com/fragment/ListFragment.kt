package com.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.ViewModelMain

class ListFragment: Fragment() {

    lateinit var viewModel : ViewModelMain

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
