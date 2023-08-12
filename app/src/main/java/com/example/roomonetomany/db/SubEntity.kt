package com.example.roomonetomany.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import com.example.roomonetomany.utils.Constants.SUB_ENTITY_TABLE
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = SUB_ENTITY_TABLE,
    foreignKeys = [ForeignKey(
        entity = MainEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("subId"),
        onDelete = CASCADE
    )]
)
data class SubEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var subId: Long = 0,
    val surname: String = ""
) : Parcelable