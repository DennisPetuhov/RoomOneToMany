package com.example.roomonetomany.FirstFragment

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
import com.example.roomonetomany.FirstFragment.Recycler.OneToManyListAdapter
import com.example.roomonetomany.R
import com.example.roomonetomany.databinding.FragmentFirstBinding
import com.example.roomonetomany.db.MainEntity
import com.example.roomonetomany.db.OneToManyRelation
import com.example.roomonetomany.db.SubEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FirstFragment : Fragment() {
    val viewModel: FirstViewModel by viewModels()

    @Inject
    lateinit var adapter: OneToManyListAdapter

    val subContact = SubEntity(surname = "XO")

    val oneToMAnyEntity = OneToManyRelation(
        MainEntity(name = "WW"), listOf(
            SubEntity(surname = "ww"),
            SubEntity(surname = "44")
        )
    )
    val listOfOneToManyRelation = listOf(
        OneToManyRelation(
            MainEntity(name = "WW"), listOf(
                SubEntity(surname = "ww"),
                SubEntity(surname = "44")
            )
        ),
        OneToManyRelation(
            MainEntity(name = "qq"), listOf(
                SubEntity(surname = "qq"),
                SubEntity(surname = "55")
            )
        )
    )

    companion object {
        fun newInstance() = FirstFragment()
    }

    lateinit var binding: FragmentFirstBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        saveOneToManyEntity(listOfOneToManyRelation)
        navigare()
        getOneToManyEntities()



//        binding.button3.setOnClickListener {
//            viewModel.getOneToMAnyEntities()
//            Toast.makeText(requireContext(), "###", Toast.LENGTH_SHORT).show()
//        }

        sendAction()
    }

    fun navigare() {
        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_firstFragment_to_secondFragment)
        }
    }

    fun sendAction() {
        adapter.bind {
            val action =
                FirstFragmentDirections.actionFirstFragmentToSecondFragment(it)
            action.onetomany
            findNavController().navigate(action)

        }

    }


    private fun saveOneToManyEntity(listOfOneToManyRelation: List<OneToManyRelation>) {
        listOfOneToManyRelation.forEach {
            viewModel.saveOneToMany(it)
        }

    }


    private fun getOneToManyEntities() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.oneToManyEntityState.collect {
                    initRecycler(it)

                }
            }

        }

    }

    fun initRecycler(list: MutableList<OneToManyRelation>?) {

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }
        binding.recyclerView.adapter = adapter
        adapter.submitList(list)
    }


    private fun saveMainEntity() {
        val name = binding.editText.text
        binding.button3.setOnClickListener {
            viewModel.saveMainEntity(name.toString())
        }

    }

    private fun getMainEntities() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mainEntityState.collect {


                }
            }

        }

    }

}