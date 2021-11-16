package com.keelim.free.ui.main.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.keelim.data.model.open.Url
import com.keelim.free.databinding.ItemDetailBinding
import com.keelim.free.ui.main.detail.memo.MemoFragment

class DetailAdapter(
    val click_move: (Url) -> Unit,
    val click_memo: (String) -> Unit
) : ListAdapter<Url, DetailAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(val binding: ItemDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Url) = with(binding) {
            header.text = item.url
            move.setOnClickListener {
                click_move(item)
            }
            if(item.thumbnail.isEmpty()){
                thumbnail.visibility = View.GONE
            } else{
                thumbnail.load(item.thumbnail)
            }
            memo.setOnClickListener {
                click_memo(item.memos_id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Url>() {
            override fun areItemsTheSame(oldItem: Url, newItem: Url): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Url, newItem: Url): Boolean {
                return oldItem == newItem
            }
        }
    }
}