package com.example.roomonetomany.SecondFragment.Recycler

import com.example.roomonetomany.db.SubEntity

sealed class StateOfSubEntityListAdapter{
    object Idle:StateOfSubEntityListAdapter()
    class Remove(val position: Int):StateOfSubEntityListAdapter()
    class Add(val subEntity: SubEntity):StateOfSubEntityListAdapter()
}