package com.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.ViewModelLogin
import com.adapter.ListAdapter

import com.example.benfordslaw.R
import com.example.benfordslaw.databinding.FragmentGameListBinding


class ListFragment : Fragment() {

    lateinit var binding: FragmentGameListBinding
    lateinit var adapter: ListAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel: ViewModelLogin by viewModels()
        val rcy = binding.rcyView

        context?.let { viewModel.gameList(it) }
        viewModel.liveData.observe(this, Observer {
            val data = viewModel.liveData.value
            adapter = ListAdapter(data!!)
            rcy.setHasFixedSize(true)
            rcy.adapter = adapter
            rcy.layoutManager =
                GridLayoutManager(activity, 5, GridLayoutManager.HORIZONTAL, false)
        })

    }
}
