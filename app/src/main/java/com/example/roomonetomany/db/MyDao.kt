package com.example.roomonetomany.db

import androidx.room.*
import com.example.roomonetomany.utils.Constants.MAIN_ENTITY_TABLE
import com.example.roomonetomany.utils.Constants.SUB_ENTITY_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface MyDao {

    @Query("SELECT * FROM $MAIN_ENTITY_TABLE ")
    fun getMainEntities(): Flow<List<MainEntity>>

    @Query("SELECT * FROM $SUB_ENTITY_TABLE ")
    fun getSubEntityes(): Flow<List<SubEntity>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
   fun saveSubContacts(subEntity: SubEntity)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun saveContact(mainEntity: MainEntity)

    @Transaction
    @Query("SELECT * FROM $MAIN_ENTITY_TABLE")
    fun getOneToMaNyEntities(): Flow<List<OneToManyRelation>>

    @Transaction
   fun saveOneToMany(oneToManyRelation: OneToManyRelation) {
        saveContact(oneToManyRelation.mainEntity)
        println("%%%" + oneToManyRelation.mainEntity.name)
        for (sub in oneToManyRelation.subComponentList) {
            saveSubContacts(sub)
        }

    }


    @Update
    suspend fun updateContact(mainEntity: MainEntity)

    @Delete
    suspend fun deleteContact(mainEntity: MainEntity)

    @Query("SELECT * FROM $MAIN_ENTITY_TABLE WHERE id ==:id")
    fun getContact(id: Int): Flow<MainEntity>

    @Query("SELECT * FROM $MAIN_ENTITY_TABLE")
    fun getAllContacts(): Flow<MutableList<MainEntity>>

    @Query("DELETE FROM $MAIN_ENTITY_TABLE")
    fun deleteAllContacts()

    @Query("SELECT * FROM $MAIN_ENTITY_TABLE ORDER BY name ASC")
    fun sortedASC(): Flow<MutableList<MainEntity>>

    @Query("SELECT * FROM $MAIN_ENTITY_TABLE ORDER BY name DESC")
    fun sortedDESC(): Flow<MutableList<MainEntity>>

    @Query("SELECT * FROM $MAIN_ENTITY_TABLE WHERE name LIKE '%' || :name || '%' ")
    fun searchContact(name: String): Flow<MutableList<MainEntity>>
}