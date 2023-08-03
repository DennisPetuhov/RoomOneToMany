package com.example.roomonetomany.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = ([MainEntity::class,SubEntity::class]),
    version = 13, exportSchema = false)
abstract class ContactsDB : RoomDatabase() {
    abstract fun contactsDao(): MyDao
}