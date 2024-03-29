package com.example.bookunities

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JoinScreen(
    currentUser: User,
    onProfileClick: () -> Unit,
    onBackPress: () -> Unit,
    onJoinedSuccessfully: () -> Unit,
    onUpdateUser: (User, Community) -> Unit
) {
    var communityCode by remember { mutableStateOf("") }
    var communities by remember { mutableStateOf<List<Community>>(emptyList()) }
    var filteredCommunities by remember { mutableStateOf<List<Community>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var loadError by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val db = FirebaseFirestore.getInstance()
        db.collection("communities").get().addOnSuccessListener { result ->
            communities = result.mapNotNull { document ->
                val name = document.getString("name") ?: return@mapNotNull null
                val imageUrl = document.getString("imageUrl") ?: return@mapNotNull null
                Community(name, imageUrl, document.id)
            }
            isLoading = false
            filteredCommunities = communities
        }.addOnFailureListener {
            loadError = true
            isLoading = false
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Navbar(currentUser = currentUser, onProfileClick = onProfileClick)
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = communityCode,
                onValueChange = { communityCode = it },
                label = { Text("Code") },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = {
                filteredCommunities = communities.filter {
                    it.name.contains(communityCode, ignoreCase = true)
                }
            }) {
                Text("Find")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else if (loadError) {
            Text(
                "Failed to load communities",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            Column(modifier = Modifier.fillMaxSize()) {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(filteredCommunities) { community ->
                        CommunityCard(
                            communityImage = rememberAsyncImagePainter(community.imageUrl),
                            communityName = community.name,
                            communityId = community.id,
                            onJoinClicked = { communityId ->
                                currentUser.communityUserId = communityId
                                handleJoinCommunity(
                                    communityId,
                                    currentUser
                                ) { updatedUser, updatedCommunity ->
                                    onUpdateUser(updatedUser, updatedCommunity)
                                    onJoinedSuccessfully()
                                }
                            }
                        )
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
    }
}

fun handleJoinCommunity(
    communityId: String,
    currentUser: User,
    onSuccess: (User, Community) -> Unit
) {
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    val userId = auth.currentUser?.uid ?: return

    db.collection("users").document(userId)
        .update("communityUserId", communityId)
        .addOnSuccessListener {
            // Fetch the community details
            db.collection("communities").document(communityId).get()
                .addOnSuccessListener { communityDoc ->
                    val updatedCommunity = communityDoc.toObject(Community::class.java)
                    if (updatedCommunity != null) {
                        val updatedUser = currentUser.copy(communityUserId = communityId)
                        onSuccess(updatedUser, updatedCommunity)
                    }
                }
                .addOnFailureListener {
                }
        }
        .addOnFailureListener {
        }
}