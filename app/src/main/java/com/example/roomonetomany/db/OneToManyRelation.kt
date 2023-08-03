package com.example.roomonetomany.db

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import kotlinx.parcelize.Parcelize

@Parcelize
data class OneToManyRelation(
    @Embedded val mainEntity: MainEntity,
    @Relation(

        parentColumn = "id",
        entityColumn = "subId"
    )
    val subComponentList: List<SubEntity>
):Parcelable