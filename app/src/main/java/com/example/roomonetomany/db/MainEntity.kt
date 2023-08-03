package com.example.roomonetomany.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.roomonetomany.utils.Constants.MAIN_ENTITY_TABLE
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = MAIN_ENTITY_TABLE)
data class MainEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String = "",
    var phone: String = "",
):Parcelable