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
    suspend fun saveSubContacts(subEntity: SubEntity)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMainEntity(mainEntity: MainEntity): Long

    @Transaction
    @Query("SELECT * FROM $MAIN_ENTITY_TABLE")
    fun getOneToMaNyEntities(): Flow<List<OneToManyRelation>>

    @Transaction
    suspend fun saveOneToMany(oneToManyRelation: OneToManyRelation) {
        saveMainEntity(oneToManyRelation.mainEntity)
        println("%%%" + oneToManyRelation.mainEntity.name)
        for (sub in oneToManyRelation.subEntityList) {
            saveSubContacts(sub)
        }

    }

    @Transaction
    suspend fun updateOneToMany(oneToManyRelation: OneToManyRelation) {
        val myId = saveMainEntity(oneToManyRelation.mainEntity)

        println("%%%" + oneToManyRelation.mainEntity.name + myId)
        for (sub in oneToManyRelation.subEntityList) {
            sub.subId = myId
            saveSubContacts(sub)
        }

    }

    @Query("SELECT * FROM $SUB_ENTITY_TABLE WHERE subId = :subId ")
    fun findSubEntityByMainEntityId(subId: Long): Flow<List<SubEntity>>


    @Update
    suspend fun updateContact(mainEntity: MainEntity)

    @Delete
  suspend  fun deleteMainEntity(mainEntity: MainEntity)

    @Delete
    suspend fun deleteSubEntity(item: SubEntity)

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