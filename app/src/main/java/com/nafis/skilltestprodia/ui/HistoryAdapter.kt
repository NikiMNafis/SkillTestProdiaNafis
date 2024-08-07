package com.nafis.skilltestprodia.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nafis.skilltestprodia.database.SearchHistory
import com.nafis.skilltestprodia.response.ResultsItem
import com.nafis.skilltestprodia.databinding.ItemArticleBinding
import com.nafis.skilltestprodia.databinding.ItemSearchHistoryBinding

class HistoryAdapter (private val list: List<SearchHistory>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    interface OnItemClickCallback {
        fun onItemClicked(data: SearchHistory)
    }

    class ViewHolder(var binding: ItemSearchHistoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSearchHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = list[position]
        holder.binding.tvTitle.text = currentItem.name

    }
}