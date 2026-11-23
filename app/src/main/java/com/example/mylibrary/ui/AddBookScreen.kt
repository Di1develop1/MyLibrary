package com.example.mylibrary.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.mylibrary.model.Book

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBookScreen(
    existingBook: Book? = null,
    onSaveBook: (title: String, author: String, year: Int) -> Unit,
    onCancel: () -> Unit
) {
    var title by remember(existingBook) { mutableStateOf(existingBook?.title ?: "") }
    var author by remember(existingBook) { mutableStateOf(existingBook?.author ?: "") }
    var yearText by remember(existingBook) { mutableStateOf(existingBook?.year?.toString() ?: "") }

    val parsedYear = yearText.toIntOrNull()
    val isFormValid = title.isNotBlank() && author.isNotBlank() && parsedYear != null && parsedYear > 0

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = if (existingBook == null) "Нова книга" else "Редагування книги",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = LibraryPurple
                )
            )
        },
        containerColor = Color(0xFFF5F5F5)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Назва книги") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            OutlinedTextField(
                value = author,
                onValueChange = { author = it },
                label = { Text("Автор") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            OutlinedTextField(
                value = yearText,
                onValueChange = { input ->
                    // Дозволяємо вводити лише цифри
                    if (input.length <= 4 && input.all { it.isDigit() }) {
                        yearText = input
                    }
                },
                label = { Text("Рік видання") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (parsedYear != null) {
                        onSaveBook(title, author, parsedYear)
                    }
                },
                enabled = isFormValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                )
            ) {
                Text(
                    text = if (existingBook == null) "Додати" else "Зберегти",
                    fontWeight = FontWeight.SemiBold
                )
            }

            OutlinedButton(
                onClick = onCancel,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                )
            ) {
                Text(
                    text = "Скасувати",
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}