package com.example.roomonetomany.FirstFragment.Recycler

import androidx.recyclerview.widget.DiffUtil
import com.example.roomonetomany.db.SubEntity

class SubEntityDiffUtill:DiffUtil.ItemCallback<SubEntity>() {
    override fun areItemsTheSame(oldItem: SubEntity, newItem: SubEntity): Boolean {
        return  oldItem==newItem
    }

    override fun areContentsTheSame(oldItem: SubEntity, newItem: SubEntity): Boolean {
       return  oldItem==newItem
    }
}