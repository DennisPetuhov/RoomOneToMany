package com.example.roomonetomany.FirstFragment

import com.example.roomonetomany.db.OneToManyRelation

fun interface OnClick {
    fun onClick(list:OneToManyRelation)
}