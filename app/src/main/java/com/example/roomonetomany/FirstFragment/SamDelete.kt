package com.example.roomonetomany.FirstFragment

import com.example.roomonetomany.db.OneToManyRelation

fun interface SamDelete{
  fun delete(item:OneToManyRelation)
}