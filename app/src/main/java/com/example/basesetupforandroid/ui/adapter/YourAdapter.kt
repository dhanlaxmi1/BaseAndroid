package com.example.basesetupforandroid.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.basesetupforandroid.data.model.PictureResponseItem
import com.example.basesetupforandroid.databinding.YourAdapterItemBinding

class YourAdapter(private var dataList: List<PictureResponseItem> = listOf()) :
    RecyclerView.Adapter<YourAdapter.YourViewHolder>() {

    inner class YourViewHolder(val binding: YourAdapterItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YourViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = YourAdapterItemBinding.inflate(inflater, parent, false)
        return YourViewHolder(binding)
    }

    override fun onBindViewHolder(holder: YourViewHolder, position: Int) {
        val item = dataList[position]
        holder.binding.tv.text = "${item.albumId.toString()}"
        holder.binding.cb.isChecked = item.isSelected
        holder.binding.root.setOnClickListener {
            toggleItemSelection(position)
        }
    }

    override fun getItemCount(): Int = dataList.size

    fun setData(dataList: List<PictureResponseItem>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

    private fun toggleItemSelection(position: Int) {
        val item = dataList[position]
        item.isSelected = !item.isSelected
        notifyItemChanged(position)
    }
}
