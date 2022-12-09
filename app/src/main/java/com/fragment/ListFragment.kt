package com.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager

import com.ViewModelGameList
import com.adapter.ListAdapter

import com.example.benfordslaw.R
import com.example.benfordslaw.databinding.FragmentGameListBinding
import com.http.Http
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ListFragment(viewModel : ViewModelGameList) : Fragment() {

    lateinit var binding: FragmentGameListBinding
    lateinit var adapter: ListAdapter
    val liveData = viewModel.liveData



    interface callBack {
        fun callBack() {
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

/*        CoroutineScope(Dispatchers.Main).launch{
            context?.let { viewModel.gameList(it) }
        }*/

        liveData.observe(viewLifecycleOwner) {
            val data = liveData.value
            adapter = ListAdapter(data!!)
            rcy.setHasFixedSize(true)
            rcy.adapter = adapter
            rcy.layoutManager =
                GridLayoutManager(activity, 5, GridLayoutManager.HORIZONTAL, false)
        }

        binding.closeBtn.setOnClickListener {
            val transaction: FragmentTransaction =
                requireActivity().supportFragmentManager.beginTransaction()
            transaction.remove(this).commit()
        }

    }

}
