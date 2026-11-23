package com.example.mylibrary.model

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val year: Int,
    val rating: Int = 5 // оцінка від 1 до 5 зірок
)