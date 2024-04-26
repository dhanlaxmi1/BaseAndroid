package com.example.basesetupforandroid.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.basesetupforandroid.R
import com.example.basesetupforandroid.data.model.PictureResponseItem
import com.example.basesetupforandroid.databinding.YourAdapterItemBinding
import com.example.basesetupforandroid.util.base.BaseAdapter

class MyAdapter() : BaseAdapter<PictureResponseItem,YourAdapterItemBinding>(R.layout.your_adapter_item){
    private var selectedItemIndex: Int = RecyclerView.NO_POSITION

    override fun createViewHolder(binding: YourAdapterItemBinding): ViewHolder<PictureResponseItem, YourAdapterItemBinding> {
        return MyViewHolder(binding)
    }
    inner class MyViewHolder(binding: YourAdapterItemBinding) : ViewHolder<PictureResponseItem, YourAdapterItemBinding>(binding) {
        init {
            binding.cb.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    selectedItemIndex = position
                    notifyDataSetChanged()
                }
            }
        }

        override fun bind(item: PictureResponseItem) {
            binding.cb.isChecked = adapterPosition == selectedItemIndex

            binding.item = item
            binding.executePendingBindings()
        }
    }
}