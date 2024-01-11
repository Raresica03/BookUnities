package com.example.bookunities

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun ProfileScreen(
    currentUser: User?,
    currentCommunity: Community?,
    onLogout: () -> Unit,
    onDeleteAccount: () -> Unit,
    onLeaveCommunity: (User) -> Unit,
    onBackPress: () -> Unit
) {

    val username = currentUser?.firstName?.ifEmpty { "Guest" }
    val communityName = currentCommunity?.name ?: ""
    var communityTitle = ""
    communityTitle = if (communityName.isNotEmpty())
        "Part of: $communityName community"
    else
        "Not part of a community"
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile image placeholder
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.blank_profile),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (username != null) {
            Text(
                text = username,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = communityTitle,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Action buttons
        Button(
            enabled = currentCommunity != null,
            onClick = {
                      currentUser?.let { usr ->
                          leaveCommunity(usr, onLeaveSuccess = {
                              updatedUser -> onLeaveCommunity(updatedUser)
                          })
                      }

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Leave Community")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onLogout, modifier = Modifier.fillMaxWidth()) {
            Text("Logout")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onDeleteAccount,
//            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Delete account", color = Color.White)
        }

        Spacer(modifier = Modifier.weight(1f))

        // Back navigation button
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

fun leaveCommunity(
    currentUser: User,
    onLeaveSuccess: (User) -> Unit
) {
    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()

    db.collection("users").document(auth.currentUser!!.uid)
        .update("communityUserId", "")
        .addOnSuccessListener {
            val updatedUser = currentUser.copy(communityUserId = "")
            onLeaveSuccess(updatedUser)
        }
        .addOnFailureListener {
        }
}