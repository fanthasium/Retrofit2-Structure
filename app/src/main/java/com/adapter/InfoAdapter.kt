package com.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.benfordslaw.databinding.ItemAdapterBinding

class InfoAdapter: RecyclerView.Adapter<InfoAdapter.InfoViewHoler>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InfoViewHoler {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: InfoViewHoler, position: Int) {
        TODO("Not yet implemented")
    }


    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    class InfoViewHoler(val binding : ItemAdapterBinding): RecyclerView.ViewHolder(binding.root)
}