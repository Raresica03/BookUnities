package com.example.bookunities

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


@Composable
fun FindBookPage(
    currentUser: User,
    onProfileClick: () -> Unit,
    onBackPress: () -> Unit,
) {
    var books by remember { mutableStateOf<List<Book>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var loadError by remember { mutableStateOf(false) }
    var userCommunityId by remember { mutableStateOf<String?>(null) }
    val db = FirebaseFirestore.getInstance()

    val auth = FirebaseAuth.getInstance()
    val currentUserId = auth.currentUser?.uid

    LaunchedEffect(currentUserId) {
        if (currentUserId != null) {
            db.collection("users").document(currentUserId).get()
                .addOnSuccessListener { document ->
                    userCommunityId = document.getString("communityUserId")
                }
                .addOnFailureListener {
                    /* TODO */
                }
        }
    }

    LaunchedEffect(userCommunityId) {
        FirebaseFirestore.getInstance().collection("books")
            .whereEqualTo("communityBookId", userCommunityId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                books = querySnapshot.documents.mapNotNull { doc ->
                    doc.toObject(Book::class.java)
                }
                isLoading = false
            }
            .addOnFailureListener {
                isLoading = false
                // Handle the error
            }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Navbar(currentUser = currentUser, onProfileClick = onProfileClick)
        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else if (loadError) {
            Text(
                "Failed to load books",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            LazyColumn {
                items(books) { book ->
                    BookCard(
                        bookImage = rememberAsyncImagePainter(book.imageUrl),
                        bookName = book.name
                    )
                }
            }
        }

        Button(
            onClick = onBackPress,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(16.dp)
        ) {
            Text("Back")
        }
    }
}