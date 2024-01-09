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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateScreen() {
    var communityName by remember { mutableStateOf("") }
    var communityDescription by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val storageRef = FirebaseStorage.getInstance().reference
    val db = FirebaseFirestore.getInstance()

    // Image picker launcher
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
            value = communityName,
            onValueChange = { communityName = it },
            label = { Text("Community Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = communityDescription,
            onValueChange = { communityDescription = it },
            label = { Text("Community Description") },
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
                    uploadImageToFirebaseStorage(uri, storageRef) { url ->
                        val communityData = hashMapOf(
                            "name" to communityName,
                            "description" to communityDescription,
                            "imageUrl" to url
                        )
                        db.collection("communities").add(communityData)
                            .addOnSuccessListener {
                                Toast.makeText(context, "Community created successfully.", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(context, "Error creating community: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    }
                } ?: Toast.makeText(context, "Please pick an image first.", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = communityName.isNotEmpty() && communityDescription.isNotEmpty() && imageUri != null
        ) {
            Text("Create Community")
        }
    }
}

fun uploadImageToFirebaseStorage(uri: Uri, storageRef: StorageReference, onUrlAvailable: (String) -> Unit) {
    val fileName = "community_images/${System.currentTimeMillis()}-${uri.lastPathSegment}"
    val uploadTask = storageRef.child(fileName).putFile(uri)
    uploadTask.continueWithTask { task ->
        if (!task.isSuccessful) {
            task.exception?.let { throw it }
        }
        storageRef.child(fileName).downloadUrl
    }.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val downloadUrl = task.result.toString()
            onUrlAvailable(downloadUrl)
        } else {
            // Handle failure, e.g., log the error or show a message
        }
    }
}