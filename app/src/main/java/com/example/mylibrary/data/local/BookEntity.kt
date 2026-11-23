package com.example.mylibrary.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mylibrary.model.Book

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val author: String,
    val year: Int,
    val rating: Int = 5
)

fun BookEntity.toDomain(): Book = Book(
    id = id,
    title = title,
    author = author,
    year = year,
    rating = rating
)

fun Book.toEntity(): BookEntity = BookEntity(
    id = id,
    title = title,
    author = author,
    year = year,
    rating = rating
)