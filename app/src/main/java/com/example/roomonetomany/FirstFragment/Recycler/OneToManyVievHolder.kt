package com.example.roomonetomany.FirstFragment.Recycler


import androidx.recyclerview.widget.RecyclerView
import com.example.roomonetomany.databinding.ContactsViewBinding
import com.example.roomonetomany.db.OneToManyRelation

class OneToManyVievHolder(val binding: ContactsViewBinding):RecyclerView.ViewHolder(binding.root) {


    fun bindTo(model: OneToManyRelation){

       binding.name.text=model.mainEntity.name
//        binding.id.text=model.mainEntity.toString()


    }
}