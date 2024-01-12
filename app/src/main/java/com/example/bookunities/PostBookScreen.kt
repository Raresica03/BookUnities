package com.example.bookunities

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostBookScreen(
    onBackPress: () -> Unit
) {
    var bookName by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var userCommunityId by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    val storageRef = FirebaseStorage.getInstance().reference
    val db = FirebaseFirestore.getInstance()

    val auth = FirebaseAuth.getInstance()
    val currentUserId = auth.currentUser?.uid

    LaunchedEffect(currentUserId) {
        if (currentUserId != null) {
            db.collection("users").document(currentUserId).get()
                .addOnSuccessListener { document ->
                    userCommunityId = document.getString("communityUserId")
                }
                .addOnFailureListener { e ->
                    Toast.makeText(
                        context,
                        "Error fetching user data: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
        }
    }


    val imagePickerLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            imageUri = it
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = bookName,
            onValueChange = { bookName = it },
            label = { Text("Book title") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { imagePickerLauncher.launch("image/*") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Pick Image")
        }

        Spacer(modifier = Modifier.height(8.dp))

        imageUri?.let { uri ->
            Image(
                painter = rememberAsyncImagePainter(model = uri),
                contentDescription = "Picked image",
                modifier = Modifier.size(150.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                imageUri?.let { uri ->
                    uploadImageToFirebaseStorage(uri, storageRef) { imageUrl ->
                        val bookData = hashMapOf(
                            "name" to bookName,
                            "bookUserId" to currentUserId,
                            "communityBookId" to userCommunityId,
                            "imageUrl" to imageUrl
                        )
                        db.collection("books").add(bookData)
                            .addOnSuccessListener {
                                Toast.makeText(
                                    context,
                                    "Book posted successfully.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(
                                    context,
                                    "Error posting book: ${e.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    }
                } ?: Toast.makeText(context, "Please pick an image first.", Toast.LENGTH_SHORT)
                    .show()
            },
        ) {
            Text("Post Book")
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Button(onClick = onBackPress) {
                Text("Back")
            }
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}