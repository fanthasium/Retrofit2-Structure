package com.fragment

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.createBitmap
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager

import com.ViewModelGameList
import com.activity.InfoActivity
import com.adapter.ListAdapter

import com.example.benfordslaw.R
import com.example.benfordslaw.databinding.FragmentGameListBinding


class ListFragment : Fragment() {

    lateinit var binding: FragmentGameListBinding
    lateinit var adapter: ListAdapter
    val viewModel: ViewModelGameList by viewModels()

    interface callBack {
        fun callBack(){
            //fragment call back 패턴
        }
    }

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


        val rcy = binding.rcyView

        context?.let { viewModel.gameList(it) }
        viewModel.liveData.observe(viewLifecycleOwner, Observer {
            val data = viewModel.liveData.value
            adapter = ListAdapter(data!!)
            rcy.setHasFixedSize(true)
            rcy.adapter = adapter
            rcy.layoutManager =
                GridLayoutManager(activity, 5, GridLayoutManager.HORIZONTAL, false)
        })

        binding.closeBtn.setOnClickListener{
            val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.remove(this).commit()
        }

    }

}
