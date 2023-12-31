package com.example.roomonetomany.di

import android.content.Context
import androidx.room.Room
import com.example.roomonetomany.db.MyDB
import com.example.roomonetomany.db.MainEntity
import com.example.roomonetomany.utils.Constants.CONTACTS_DATABASE


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context):MyDB = Room.databaseBuilder(
        context, MyDB::class.java, CONTACTS_DATABASE
    ).allowMainThreadQueries()
        .fallbackToDestructiveMigration().build()


    @Provides
    @Singleton
    fun provideDao(db: MyDB) = db.MyDao()


    @Provides
    fun provideEntity()= MainEntity()

}