package com.example.roomonetomany.FirstFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomonetomany.db.MyDao
import com.example.roomonetomany.db.MainEntity
import com.example.roomonetomany.db.OneToManyRelation
import com.example.roomonetomany.repository.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirstViewModel @Inject constructor(var dao: MyDao, val repository: DatabaseRepository) :
    ViewModel() {
    val state: StateFlow<MutableList<OneToManyRelation>> get() = _state
    private var _state: MutableStateFlow<MutableList<OneToManyRelation>> = MutableStateFlow(
        mutableListOf(

        )
    )


    init {
//        getMainEntities()
        getOneToMAnyEntities()

    }

//    fun getContacts() {
//        viewModelScope.launch {
//            dao.getMainEntityes().collect {
//                _state.value = it
//            }
//        }
//    }

    fun saveMainEntity(name: String) {
        viewModelScope.launch {
            val entity = MainEntity(name = name)
            repository.saveMainEntity(entity)
//
//                .flatMapConcat {
//                flow {
//                    emit(entity)
//                    println("### PERSON ${entity.name} ADDED")
//                }
//
//            }
                .flowOn(Dispatchers.IO)
                .collect {
                }
        }
    }


    val mainEntityState: StateFlow<MutableList<MainEntity>> get() = _mainEntityState
    private var _mainEntityState: MutableStateFlow<MutableList<MainEntity>> = MutableStateFlow(
        mutableListOf(

        )
    )



    fun getMainEntities() {
        viewModelScope.launch {
            repository.getMainEntities().collect {
                _mainEntityState.value = it.toMutableList()
                println("___"+it.toMutableList())
            }
        }
    }


    val oneToManyEntityState: StateFlow<MutableList<OneToManyRelation>> get() = _oneToManyEntityState
    private var _oneToManyEntityState: MutableStateFlow<MutableList<OneToManyRelation>> =
        MutableStateFlow(
            mutableListOf()
        )

    fun getOneToMAnyEntities() {
        viewModelScope.launch {
            repository.getOneToMaNyEntities().collect {
                _oneToManyEntityState.value = it.toMutableList()

            }

        }
    }

    fun saveOneToMany(oneToManyRelation: OneToManyRelation) {
        viewModelScope.launch {
//            repository.saveOneToMany(oneToManyRelation).flatMapConcat {
            repository.updateOneToMany(oneToManyRelation).flatMapConcat {
                flow {
                    emit(oneToManyRelation)
                    println("### PERSON ${oneToManyRelation.mainEntity.name} ADDED")
                }
            }.flowOn(Dispatchers.IO)
                .collect {
                }

        }

    }

    fun deleteMainEntity(item: OneToManyRelation) {
        viewModelScope.launch {
            repository.deleteMainEntity(item).collect{}
        }
    }
}