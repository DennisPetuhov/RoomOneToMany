package com.example.roomonetomany.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.roomonetomany.utils.Constants.SUB_ENTITY_TABLE
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = SUB_ENTITY_TABLE)
data class SubEntity(
    @PrimaryKey(autoGenerate = true)
    var subId: Int = 0,
    val surname:String
) :Parcelable