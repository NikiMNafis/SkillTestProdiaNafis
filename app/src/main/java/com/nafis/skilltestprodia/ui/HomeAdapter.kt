package com.nafis.skilltestprodia.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nafis.skilltestprodia.response.ResultsItem
import com.nafis.skilltestprodia.databinding.ItemArticleBinding

class HomeAdapter (private val list: List<ResultsItem>) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallBack(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ResultsItem)
    }

    class ViewHolder(var binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentArticle = list[position]
        holder.binding.tvTitle.text = currentArticle.title
        if (!currentArticle.imageUrl.isNullOrEmpty()) {
            Glide.with(holder.itemView.context)
                .load(currentArticle.imageUrl)
                .into(holder.binding.ivArticle)
        }

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(list[holder.adapterPosition])
        }
    }
}