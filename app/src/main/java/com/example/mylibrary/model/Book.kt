package com.example.mylibrary.model

data class Book(
    val id: Int = 0,
    val title: String,
    val author: String,
    val year: Int,
    val rating: Int = 5
)