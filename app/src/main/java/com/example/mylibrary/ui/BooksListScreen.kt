package com.example.mylibrary.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mylibrary.model.Book

val LibraryPurple = Color(0xFF8E24AA)
val StarGold = Color(0xFFFFB300)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BooksListScreen(
    books: List<Book>,
    onBookClick: (Int) -> Unit,
    onAddClick: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Моя бібліотека",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = LibraryPurple
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = Color.White,
                contentColor = Color.Black,
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Додати книгу"
                )
            }
        },
        containerColor = Color(0xFFF5F5F5)
    ) { paddingValues ->
        if (books.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Список книг порожній. Натисніть «+», щоб додати.",
                    color = Color.Gray
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(books, key = { it.id }) { book ->
                    BookItemCard(
                        book = book,
                        onClick = { onBookClick(book.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun BookItemCard(
    book: Book,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Імітація обкладинки книги згідно з макетом
            Box(
                modifier = Modifier
                    .width(44.dp)
                    .height(64.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(LibraryPurple)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = book.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = book.author,
                    fontSize = 13.sp,
                    color = Color.DarkGray
                )
                Spacer(modifier = Modifier.height(6.dp))
                RatingStarsRow(rating = book.rating)
            }
        }
    }
}

@Composable
fun RatingStarsRow(rating: Int) {
    Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
        for (i in 1..5) {
            Icon(
                imageVector = if (i <= rating) Icons.Filled.Star else Icons.Outlined.StarOutline,
                contentDescription = null,
                tint = if (i <= rating) StarGold else Color.Gray,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}