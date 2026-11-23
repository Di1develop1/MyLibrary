package com.example.mylibrary.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mylibrary.data.repository.BookRepository
import com.example.mylibrary.domain.model.Book
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LibraryViewModel(
    private val repository: BookRepository
) : ViewModel() {

    val books: StateFlow<List<Book>> = repository.books.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun getBookById(id: Int) = repository.getBookById(id)

    fun addBook(title: String, author: String, year: Int) {
        viewModelScope.launch {
            repository.addBook(
                Book(
                    title = title.trim(),
                    author = author.trim(),
                    year = year,
                    rating = 5
                )
            )
        }
    }

    fun updateBook(id: Int, title: String, author: String, year: Int, currentRating: Int) {
        viewModelScope.launch {
            repository.updateBook(
                Book(
                    id = id,
                    title = title.trim(),
                    author = author.trim(),
                    year = year,
                    rating = currentRating
                )
            )
        }
    }

    fun updateRating(book: Book, newRating: Int) {
        viewModelScope.launch {
            repository.updateBook(
                book.copy(rating = newRating.coerceIn(1, 5))
            )
        }
    }

    fun deleteBook(book: Book) {
        viewModelScope.launch {
            repository.deleteBook(book)
        }
    }
}

class LibraryViewModelFactory(
    private val repository: BookRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LibraryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LibraryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}