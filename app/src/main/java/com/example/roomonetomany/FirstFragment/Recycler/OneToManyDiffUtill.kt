package com.example.roomonetomany.FirstFragment.Recycler

import androidx.recyclerview.widget.DiffUtil
import com.example.roomonetomany.db.OneToManyRelation

class OneToManyDiffUtill:DiffUtil.ItemCallback<OneToManyRelation>() {
    override fun areItemsTheSame(oldItem: OneToManyRelation, newItem: OneToManyRelation): Boolean {
        return  oldItem==newItem
    }

    override fun areContentsTheSame(oldItem: OneToManyRelation, newItem: OneToManyRelation): Boolean {
       return  oldItem==newItem
    }
}