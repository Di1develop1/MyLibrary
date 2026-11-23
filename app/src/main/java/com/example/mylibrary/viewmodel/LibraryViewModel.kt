package com.example.mylibrary.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mylibrary.model.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LibraryViewModel : ViewModel() {

    private val _books = MutableStateFlow(
        listOf(
            Book(id = 1, title = "Тіні забутих предків", author = "Михайло Коцюбинський", year = 1911, rating = 5),
            Book(id = 2, title = "1984", author = "Джордж Орвелл", year = 1949, rating = 4),
            Book(id = 3, title = "Ловець у житі", author = "Джером Селінджер", year = 1951, rating = 3)
        )
    )
    val books: StateFlow<List<Book>> = _books.asStateFlow()

    fun getBookById(id: Int): Book? {
        return _books.value.find { it.id == id }
    }

    fun addBook(title: String, author: String, year: Int) {
        val nextId = (_books.value.maxOfOrNull { it.id } ?: 0) + 1
        val newBook = Book(
            id = nextId,
            title = title.trim(),
            author = author.trim(),
            year = year,
            rating = 5
        )
        _books.update { currentList -> currentList + newBook }
    }

    fun updateBook(id: Int, title: String, author: String, year: Int) {
        _books.update { currentList ->
            currentList.map { book ->
                if (book.id == id) {
                    book.copy(
                        title = title.trim(),
                        author = author.trim(),
                        year = year
                    )
                } else {
                    book
                }
            }
        }
    }

    fun updateRating(id: Int, newRating: Int) {
        _books.update { currentList ->
            currentList.map { book ->
                if (book.id == id) book.copy(rating = newRating.coerceIn(1, 5)) else book
            }
        }
    }

    fun deleteBook(id: Int) {
        _books.update { currentList ->
            currentList.filterNot { it.id == id }
        }
    }
}