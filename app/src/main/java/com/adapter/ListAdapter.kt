package com.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dto.LoginResult
import com.example.benfordslaw.databinding.ItemListBinding
import com.responsedata.GameResult

class ListAdapter(val list: List<GameResult>) : RecyclerView.Adapter<ListAdapter.InfoViewHoler>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InfoViewHoler {
        context = parent.context
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InfoViewHoler(binding)
    }

    override fun onBindViewHolder(holder: InfoViewHoler, position: Int) {

        val binding = holder.binding
        val url = list[position].thumbnail

        Glide.with(context)
            .load(url)
            .into(binding.imgView)
    }


    override fun getItemCount() =  list.size

    class InfoViewHoler(val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {}

}