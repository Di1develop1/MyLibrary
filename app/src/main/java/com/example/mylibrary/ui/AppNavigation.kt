package com.example.mylibrary.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mylibrary.data.local.DatabaseProvider
import com.example.mylibrary.data.repository.BookRepository
import com.example.mylibrary.ui.screens.AddBookScreen
import com.example.mylibrary.ui.screens.BooksListScreen
import com.example.mylibrary.ui.screens.DetailsBookScreen
import com.example.mylibrary.ui.viewmodel.LibraryViewModel
import com.example.mylibrary.ui.viewmodel.LibraryViewModelFactory

@Composable
fun AppNavigation() {
    val context = LocalContext.current
    val database = DatabaseProvider.getDatabase(context)
    val repository = BookRepository(database.bookDao())
    val libraryViewModel: LibraryViewModel = viewModel(
        factory = LibraryViewModelFactory(repository)
    )

    val navController = rememberNavController()
    val books by libraryViewModel.books.collectAsState()

    NavHost(
        navController = navController,
        startDestination = "books_list"
    ) {
        composable("books_list") {
            BooksListScreen(
                books = books,
                onBookClick = { bookId ->
                    navController.navigate("details/$bookId")
                },
                onAddClick = {
                    navController.navigate("add_book")
                }
            )
        }

        composable("add_book") {
            AddBookScreen(
                existingBook = null,
                onSaveBook = { title, author, year ->
                    libraryViewModel.addBook(title, author, year)
                    navController.popBackStack()
                },
                onCancel = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = "edit_book/{bookId}",
            arguments = listOf(navArgument("bookId") { type = NavType.IntType })
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getInt("bookId") ?: return@composable
            val book by libraryViewModel.getBookById(bookId).collectAsState(initial = null)

            AddBookScreen(
                existingBook = book,
                onSaveBook = { title, author, year ->
                    libraryViewModel.updateBook(
                        id = bookId,
                        title = title,
                        author = author,
                        year = year,
                        currentRating = book?.rating ?: 5
                    )
                    navController.popBackStack()
                },
                onCancel = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = "details/{bookId}",
            arguments = listOf(navArgument("bookId") { type = NavType.IntType })
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getInt("bookId") ?: return@composable
            val book by libraryViewModel.getBookById(bookId).collectAsState(initial = null)

            DetailsBookScreen(
                book = book,
                onRatingChange = { newRating ->
                    book?.let { currentBook ->
                        libraryViewModel.updateRating(currentBook, newRating)
                    }
                },
                onEditClick = { id ->
                    navController.navigate("edit_book/$id")
                },
                onDeleteClick = { _ ->
                    book?.let { currentBook ->
                        libraryViewModel.deleteBook(currentBook)
                        navController.popBackStack()
                    }
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}