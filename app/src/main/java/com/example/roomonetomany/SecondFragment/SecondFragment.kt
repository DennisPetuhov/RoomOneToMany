package com.example.roomonetomany.SecondFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomonetomany.FirstFragment.Recycler.SubEntityListAdapter
import com.example.roomonetomany.R
import com.example.roomonetomany.databinding.FragmentSecondBinding
import com.example.roomonetomany.db.MainEntity
import com.example.roomonetomany.db.OneToManyRelation
import com.example.roomonetomany.db.SubEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SecondFragment : Fragment() {
    lateinit var  binding:FragmentSecondBinding
    val viewModel:SecondViewModel by viewModels()
    @Inject
    lateinit var adapter:SubEntityListAdapter

    companion object {
        fun newInstance() = SecondFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigate()

        observeOneToMAny()
        putArgs()
        binding.button5.setOnClickListener {
            viewModel.getSubEntities()
        }


    }

    fun navigate(){
        binding.button2.setOnClickListener {
            findNavController().navigate(R.id.action_secondFragment_to_firstFragment)
        }
    }
    fun getArgs():OneToManyRelation{
        val args = SecondFragmentArgs.fromBundle(requireArguments())
        val onetomany= args.onetomany
        return onetomany
    }
    fun putArgs(){

        viewModel.setOneTomany(getArgs())
    }


    fun initRecycler(list: MutableList<SubEntity>?) {

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }
        binding.recyclerView.adapter = adapter
        adapter.submitList(list)
    }

  fun  observeOneToMAny(){
      lifecycleScope.launch{
          repeatOnLifecycle(Lifecycle.State.STARTED){
              viewModel.oneToManyEntityState.collect{
                  initRecycler(it.subComponentList.toMutableList())
              }
          }
      }
  }


    private fun getSubEntities(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.subEntityState.collect{
                    initRecycler(it)

                }
            }

        }

    }
    private fun saveSubEntity(){
        val name = binding.editTextText.text
        binding.buttonToDb.setOnClickListener {
            viewModel.saveSubEntity(name.toString())
        }

    }

}