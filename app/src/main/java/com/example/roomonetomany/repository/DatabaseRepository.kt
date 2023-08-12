package com.example.roomonetomany.repository

import com.example.roomonetomany.db.MyDao
import com.example.roomonetomany.db.MainEntity
import com.example.roomonetomany.db.OneToManyRelation
import com.example.roomonetomany.db.SubEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import javax.inject.Inject

class DatabaseRepository @Inject constructor(private val dao: MyDao) {

    fun saveMainEntity(entity: MainEntity): Flow<Unit> {
        return flow {
            dao.saveMainEntity(entity)
            emit(Unit)
        }
    }

    fun saveSubEntity(entity: SubEntity): Flow<Unit> {
        return flow {
            dao.saveSubContacts(entity)
            emit(Unit)
        }
    }
    fun getSubEntities(): Flow<List<SubEntity>> {
        return dao.getSubEntityes()
    }

    fun getMainEntities(): Flow<List<MainEntity>> {
        return dao.getMainEntities()
    }

    fun getOneToMaNyEntities(): Flow<List<OneToManyRelation>> {
        return dao.getOneToMaNyEntities()

    }
    fun findSubEntityByMainEntityId(subId:Long) :Flow<List<SubEntity>>{
        return dao.findSubEntityByMainEntityId(subId)
    }




    fun saveOneToMany(oneToManyRelation: OneToManyRelation): Flow<Unit> {
        return flow {
            dao.saveOneToMany(oneToManyRelation)

            emit(Unit)

        }
    }

    fun updateOneToMany(oneToManyRelation: OneToManyRelation):Flow<Unit>{
        return flow {
            dao.updateOneToMany(oneToManyRelation)
            emit(Unit)
        }
    }

    fun deleteMainEntity(item: OneToManyRelation):Flow<Unit> {
        return flow {
     dao.deleteMainEntity(item.mainEntity)
        emit(Unit)}
    }


    suspend fun updateTask(entity: MainEntity) = dao.updateContact(entity)
    suspend fun deleteContact(entity: MainEntity) = dao.deleteMainEntity(entity)
    fun getDetailsContact(id: Int) = dao.getContact(id)
    fun getAllContacts() = dao.getAllContacts()
    fun deleteAllContacts() = dao.deleteAllContacts()
    fun getSortedListASC() = dao.sortedASC()
    fun getSortedListDESC() = dao.sortedDESC()
    fun searchContact(name: String) = dao.searchContact(name)
    fun deleteSubEntity(item: SubEntity):Flow<Unit> {
        return flow {
            dao.deleteSubEntity(item)
            emit(Unit)
        }
    }


}