package com.example.mylibrary.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mylibrary.viewmodel.LibraryViewModel

@Composable
fun AppNavigation(
    libraryViewModel: LibraryViewModel = viewModel()
) {
    val navController = rememberNavController()
    val books by libraryViewModel.books.collectAsState()

    NavHost(
        navController = navController,
        startDestination = "books_list"
    ) {
        // Екран 1: Список книг
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

        // Екран 2: Додавання нової книги
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

        // Екран 2 (режим редагування): Редагування існуючої книги за ID
        composable(
            route = "edit_book/{bookId}",
            arguments = listOf(navArgument("bookId") { type = NavType.IntType })
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getInt("bookId") ?: return@composable
            val book = books.find { it.id == bookId }

            AddBookScreen(
                existingBook = book,
                onSaveBook = { title, author, year ->
                    libraryViewModel.updateBook(bookId, title, author, year)
                    navController.popBackStack()
                },
                onCancel = {
                    navController.popBackStack()
                }
            )
        }

        // Екран 3: Деталі книги
        composable(
            route = "details/{bookId}",
            arguments = listOf(navArgument("bookId") { type = NavType.IntType })
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getInt("bookId") ?: return@composable
            val book = books.find { it.id == bookId }

            DetailsBookScreen(
                book = book,
                onRatingChange = { newRating ->
                    libraryViewModel.updateRating(bookId, newRating)
                },
                onEditClick = { id ->
                    navController.navigate("edit_book/$id")
                },
                onDeleteClick = { id ->
                    libraryViewModel.deleteBook(id)
                    navController.popBackStack()
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}