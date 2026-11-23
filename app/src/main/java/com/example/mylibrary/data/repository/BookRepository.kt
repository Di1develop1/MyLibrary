package com.example.mylibrary.data.repository

import com.example.mylibrary.data.local.BookDao
import com.example.mylibrary.data.local.BookEntity
import kotlinx.coroutines.flow.Flow

class BookRepository(
    private val bookDao: BookDao
) {
    val books: Flow<List<BookEntity>> = bookDao.getAllBooks()

    fun getBookById(id: Int): Flow<BookEntity?> {
        return bookDao.getBookById(id)
    }

    suspend fun addBook(book: BookEntity) {
        bookDao.insertBook(book.copy(id = 0))
    }

    suspend fun updateBook(book: BookEntity) {
        bookDao.updateBook(book)
    }

    suspend fun deleteBook(book: BookEntity) {
        bookDao.deleteBook(book)
    }
}