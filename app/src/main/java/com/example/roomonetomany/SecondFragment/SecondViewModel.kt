package com.example.roomonetomany.SecondFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomonetomany.db.MainEntity
import com.example.roomonetomany.db.OneToManyRelation
import com.example.roomonetomany.db.SubEntity
import com.example.roomonetomany.repository.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SecondViewModel @Inject constructor(val repository: DatabaseRepository) : ViewModel() {


    init {

    }

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


    val subEntityState: StateFlow<MutableList<SubEntity>> get() = _subEntityState
    private var _subEntityState: MutableStateFlow<MutableList<SubEntity>> = MutableStateFlow(
        mutableListOf(

        )
    )


    fun getSubEntities() {
        viewModelScope.launch {
            repository.getSubEntities().collect {
                _subEntityState.value = it.toMutableList()
                println("&&&" + it.toString())
            }
        }
    }


    val oneToManyEntityState: StateFlow<OneToManyRelation> get() = _oneToManyEntityState
    private var _oneToManyEntityState: MutableStateFlow<OneToManyRelation> = MutableStateFlow(
       OneToManyRelation(MainEntity(), listOf())
    )
    fun setOneTomany(oneToManyRelation: OneToManyRelation){
        viewModelScope.launch {
            _oneToManyEntityState.value = oneToManyRelation
        }
    }



}