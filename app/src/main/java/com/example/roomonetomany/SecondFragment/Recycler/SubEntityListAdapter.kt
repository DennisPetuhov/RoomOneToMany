package com.example.roomonetomany.FirstFragment.Recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.roomonetomany.databinding.ContactsViewBinding
import com.example.roomonetomany.db.SubEntity

import javax.inject.Inject


class SubEntityListAdapter @Inject constructor()  :ListAdapter<SubEntity, SubEntityVievHolder>(SubEntityDiffUtill()) {
    lateinit var binding: ContactsViewBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubEntityVievHolder {
        val inflater=LayoutInflater.from(parent.context)
        binding=ContactsViewBinding.inflate(inflater,parent,false)
       return SubEntityVievHolder(binding)
    }

    override fun onBindViewHolder(holder: SubEntityVievHolder, position: Int) {
        val myModel =getItem(position)
       holder.bindTo(myModel)

    }


}