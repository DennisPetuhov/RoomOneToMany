package com.example.roomonetomany.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = ([MainEntity::class,SubEntity::class]),
    version = 16, exportSchema = false)
abstract class MyDB : RoomDatabase() {
    abstract fun MyDao(): MyDao
}