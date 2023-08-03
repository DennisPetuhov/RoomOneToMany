package com.example.roomonetomany.FirstFragment.Recycler


import androidx.recyclerview.widget.RecyclerView
import com.example.roomonetomany.databinding.ContactsViewBinding
import com.example.roomonetomany.db.SubEntity

class SubEntityVievHolder(val binding: ContactsViewBinding):RecyclerView.ViewHolder(binding.root) {


    fun bindTo(model: SubEntity){

       binding.name.text=model.surname
        binding.id.text=model.subId.toString()



    }
}