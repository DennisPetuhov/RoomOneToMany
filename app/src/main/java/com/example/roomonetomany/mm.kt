//package com.example.roomonetomany
//
//class mm {
//}
//
//class Box<out T>
//open class Dog
//class Puppy : Dog()
//
//fun main() {
//    val d: Dog = Puppy() // Puppy is a subtype of Dog
//
//    val bd: Box<Dog> = Box<Puppy>() // OK
//    val bp: Box<Puppy> = Box<Dog>() // Error: Type mismatch
//
//    val bn: Box<Number> = Box<Int>() // OK
//    val bi: Box<Int> = Box<Number>() // Error: Type mismatch
//}