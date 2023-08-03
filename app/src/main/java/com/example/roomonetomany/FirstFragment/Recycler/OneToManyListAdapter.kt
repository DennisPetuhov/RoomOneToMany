package com.example.roomonetomany.FirstFragment.Recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.roomonetomany.FirstFragment.OnClick
import com.example.roomonetomany.databinding.ContactsViewBinding
import com.example.roomonetomany.db.OneToManyRelation

import javax.inject.Inject


class OneToManyListAdapter @Inject constructor()  :ListAdapter<OneToManyRelation, OneToManyVievHolder>(OneToManyDiffUtill()) {
    lateinit var binding: ContactsViewBinding
    var onClick:OnClick? = null
    fun bind(onclick:OnClick){
        this.onClick = onclick
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OneToManyVievHolder {
        val inflater=LayoutInflater.from(parent.context)
        binding=ContactsViewBinding.inflate(inflater,parent,false)
       return OneToManyVievHolder(binding)
    }

    override fun onBindViewHolder(holder: OneToManyVievHolder, position: Int) {
        val item =getItem(position)
       holder.bindTo(item)
        holder.binding.myItem.setOnClickListener{
            onClick?.onClick(item)



        }

    }


}