package com.example.bookunities

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun CommunityHomeScreen(
    onProfileClick: () -> Unit,
    onAnnouncementsClick: () -> Unit,
    onFindBooksClick: () -> Unit,
    onPostBooksClick: () -> Unit,
    onRentedBooksClick: () -> Unit,
    onMyLibraryClick: () -> Unit
) {
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    val db = FirebaseFirestore.getInstance()
    var communityName by remember { mutableStateOf("") }
    var communityImage by remember { mutableStateOf("") }

    LaunchedEffect(currentUser) {
        currentUser?.let { user ->
            // Fetch the user's communityUserId
            db.collection("users").document(user.uid).get()
                .addOnSuccessListener { document ->
                    val communityUserId = document.getString("communityUserId")
                    // Now fetch the community details
                    communityUserId?.let { communityId ->
                        db.collection("communities").document(communityId).get()
                            .addOnSuccessListener { communityDoc ->
                                communityName = communityDoc.getString("name") ?: ""
                                communityImage = communityDoc.getString("imageUrl") ?: ""
                                // Use communityName and communityImage in your UI
                            }
                    }
                }
        }
    }
    // Same-sized buttons centered in the middle of the page
    val buttonModifier = Modifier
        .height(IntrinsicSize.Min)
        .width(200.dp) // You can adjust the width to your preference

    Column {
        Navbar(onProfileClick = onProfileClick)
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            AsyncImage(
                model = communityImage,
                contentDescription = "Community Image",
                modifier = Modifier
                    .size(150.dp) // Square size
                    .padding(top = 16.dp),
                contentScale = ContentScale.Crop
            )

            Text(
                text = communityName,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onAnnouncementsClick,
                modifier = buttonModifier
            ) {
                Text("Announcements")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onFindBooksClick,
                modifier = buttonModifier
            ) {
                Text("Find Books")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onPostBooksClick,
                modifier = buttonModifier
            ) {
                Text("Post Books")
            }
            Spacer(modifier = Modifier.height(32.dp))

            // Row for the last two buttons, spaced evenly and of the same size
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    Button(onClick = onRentedBooksClick, modifier = buttonModifier) {
                        Text("Rented Books")
                    }
                }
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    Button(onClick = onMyLibraryClick, modifier = buttonModifier) {
                        Text("My Library")
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
