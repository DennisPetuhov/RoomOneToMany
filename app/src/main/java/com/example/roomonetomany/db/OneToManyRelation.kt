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
    var subEntityList: MutableList<SubEntity>
) : Parcelable {
 fun  addSubEntityToList(subEntity: SubEntity){
     subEntityList.add(subEntity)
 }
}