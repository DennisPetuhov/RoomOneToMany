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
import com.example.roomonetomany.SecondFragment.Recycler.StateOfSubEntityListAdapter
import com.example.roomonetomany.databinding.FragmentSecondBinding
import com.example.roomonetomany.db.OneToManyRelation
import com.example.roomonetomany.db.SubEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SecondFragment : Fragment() {
    private lateinit var binding: FragmentSecondBinding
    private val viewModel: SecondViewModel by viewModels()

    @Inject
    lateinit var adapter: SubEntityListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSecondBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeSubEntity()
        observeAdapterState()
        navigate()
        makeAction()
        putArgs()
        binding.button5.setOnClickListener {
            viewModel.getSubEntities()
        }
        binding.buttonToDb.setOnClickListener {
            addSubEntityToDB()

        }


    }

    private fun observeSubEntity() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.subEntityState.collect {
                    initRecycler(it)
                }
            }
        }
    }


    private fun observeAdapterState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.subEntityAdapterState.collect {
                    when (it) {
                        is StateOfSubEntityListAdapter.Idle -> {}
                        is StateOfSubEntityListAdapter.Remove -> {
                            adapter.notifyItemRemoved(it.position)
                                 }

                        is StateOfSubEntityListAdapter.Add -> {

                             adapter.notifyItemInserted(viewModel.subEntityState.value.lastIndex)
                        }
                    }
                }
            }
        }
    }

    private fun initRecycler(list: List<Any>) {

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }
        val new = list as List<SubEntity>
        binding.recyclerView.adapter = adapter
        new.toList()
        adapter.submitList(new)



    }

    private fun makeAction() {

        adapter.bindAction { action ->
            lifecycleScope.launch {
                viewModel.deleteSubEntity(action)

            }
        }

    }
    private fun addSubEntityToDB() {
        val newEntity = SubEntity(surname = binding.editTextText.text.toString())
        viewModel.saveAndAddNewSubEntity(newEntity)
    }

    private fun navigate() {
        binding.button2.setOnClickListener {
            findNavController().navigate(R.id.action_secondFragment_to_firstFragment)
        }
    }

    private fun getArgs(): OneToManyRelation {
        val args = SecondFragmentArgs.fromBundle(requireArguments())
        val onetomany = args.onetomany
        println("GETARGS")
        return onetomany
    }

    private fun putArgs() {
        val entity = getArgs()
        viewModel.setOneToMany(entity)
        println("BUNDLE")
    }
    private fun observeOneToMAny() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.oneToManyEntityState.collect {
                    val list = it.subEntityList.toList()
                    adapter.notifyDataSetChanged()
                    println("qwerty" + list)
                    initRecycler(list)

                }
            }
        }
    }

    private fun saveSubEntity() {
        val name = binding.editTextText.text
        binding.buttonToDb.setOnClickListener {
            viewModel.saveSubEntity(name.toString())
        }

    }

    private fun getSubEntities() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.subEntityState.collect {
                    initRecycler(it)

                }
            }

        }

    }
}