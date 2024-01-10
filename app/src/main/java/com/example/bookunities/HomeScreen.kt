package com.example.bookunities

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun HomeScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToJoinCreate: () -> Unit,
    onNavigateToAboutUs: () -> Unit,
    onNavigateToCommunity: () -> Unit
) {
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    var currentUser by remember { mutableStateOf(auth.currentUser) }
    var communityUserId by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(currentUser) {
        if (currentUser != null) {

            db.collection("users").document(currentUser!!.uid).get()
                .addOnSuccessListener { document ->
                    communityUserId = document.getString("communityUserId")
                }
                .addOnFailureListener {
                }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f, true)) // Flexible spacer to push content to center

        if (currentUser == null) {
            // User not logged in UI
            Text("Login to see the Home Page", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = onNavigateToLogin) {
                Text("Go to Login")
            }
        } else {
            // User logged in UI
            Text("Welcome to the Home Page", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = {
                auth.signOut()
                currentUser = null
            }) {
                Text("Logout")
            }
            Spacer(modifier = Modifier.height(20.dp))
            if(communityUserId.isNullOrEmpty()){
                Button(onClick = onNavigateToJoinCreate) {
                    Text("Go to Join/Create")
                }
            } else{
                Button(onClick = onNavigateToCommunity) {
                    Text("Go to Community")
                }
            }

        }

        Spacer(modifier = Modifier.weight(1f, true)) // Flexible spacer to balance the layout

        // About Us button at the bottom
        Button(onClick = onNavigateToAboutUs) {
            Text("About Us")
        }

        Spacer(modifier = Modifier.height(80.dp)) // Fixed spacer to maintain 80.dp from the bottom
    }
}
