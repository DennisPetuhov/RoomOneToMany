package com.example.roomonetomany.SecondFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomonetomany.SecondFragment.Recycler.StateOfSubEntityListAdapter
import com.example.roomonetomany.db.MainEntity
import com.example.roomonetomany.db.OneToManyRelation
import com.example.roomonetomany.db.SubEntity
import com.example.roomonetomany.repository.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SecondViewModel @Inject constructor(private val repository: DatabaseRepository) :
    ViewModel() {


    fun saveSubEntity(name: String) {
        viewModelScope.launch {
            val entity = SubEntity(surname = name)
            repository.saveSubEntity(entity)
                .flatMapConcat {
                    flow {
                        emit(entity)
                        println("### PERSON ${entity.surname} ADDED")
                    }

                }
                .flowOn(Dispatchers.IO)
                .collect {
                }
        }
    }

    private val _subEntityAdapterState: MutableStateFlow<StateOfSubEntityListAdapter> =
        MutableStateFlow(StateOfSubEntityListAdapter.Idle)
    val subEntityAdapterState: StateFlow<StateOfSubEntityListAdapter> = _subEntityAdapterState





    val oneToManyEntityState: StateFlow<OneToManyRelation> get() = _oneToManyEntityState
    private var _oneToManyEntityState: MutableStateFlow<OneToManyRelation> = MutableStateFlow(
        OneToManyRelation(MainEntity(), mutableListOf())
    )

    val subEntityState: StateFlow<List<SubEntity>> get() = _subEntityState
    private var _subEntityState: MutableStateFlow<MutableList<SubEntity>> = MutableStateFlow(
         mutableListOf())


    fun setOneToMany(oneToManyRelation: OneToManyRelation) {
        viewModelScope.launch {
            _oneToManyEntityState.value = oneToManyRelation
            _subEntityState.value=oneToManyRelation.subEntityList
        }
    }





    fun deleteSubEntity(item: SubEntity) {

        viewModelScope.launch {
            repository.deleteSubEntity(item).collect{}
//            val removedUserListIndex = _oneToManyEntityState.value.subEntityList.indexOf(item)
//            _oneToManyEntityState.value.subEntityList.removeAt(removedUserListIndex)
//            _subEntityAdapterState.value = StateOfSubEntityListAdapter.Remove(removedUserListIndex)


            val index= _subEntityState.value.indexOf(item)
            _subEntityState.value.removeAt(index)
            _subEntityAdapterState.value = StateOfSubEntityListAdapter.Remove(index)



        }

    }
    fun saveAndAddNewSubEntity(newEntity: SubEntity) {
        viewModelScope.launch {
            val oneToMAAnyEntity = _oneToManyEntityState.value
            oneToMAAnyEntity.addSubEntityToList(newEntity)
            repository.updateOneToMany(oneToMAAnyEntity).collect {}
            _subEntityAdapterState.value = StateOfSubEntityListAdapter.Add(newEntity)


        }
    }
    fun getSubEntities() {
        viewModelScope.launch {
            repository.getSubEntities().collect {
                _oneToManyEntityState.value.subEntityList = it.toMutableList()
                println("&&&" + _oneToManyEntityState.value.subEntityList.toString())
            }
        }
    }
    fun findSubEntityByMainEntityId(subiD: Long) {
        viewModelScope.launch {
            repository.findSubEntityByMainEntityId(subiD).collect {
                var list = it.toMutableList()
                var one = _oneToManyEntityState.value
                one.subEntityList = list
                _oneToManyEntityState.value = one
            }
//
//            repository.findSubEntityByMainEntityId(subiD).collect{
//                val newFlow= _oneToManyEntityState.value
//                newFlow.subEntityList.addAll(it)
//                _oneToManyEntityState.emit(newFlow)
//
//
//            }
        }


    }
}


