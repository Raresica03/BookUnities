package com.example.bookunities

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Navbar(onProfileClick: () -> Unit) {
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    val logo = "LOGO" // Replace with your actual logo resource
    val username =
        if (currentUser != null)
            currentUser.email ?: "Unknown"
        else "Guest"
    val profilePicture =  (R.drawable.blank_profile) // Replace with actual profile picture resource

    // Define the colors for the background and shadow
    val backgroundColor = Color.LightGray.copy(alpha = 0.95f) // Slightly gray and translucent
    val shadowColor = Color.Black.copy(alpha = 0.2f) // Soft black shadow

    Column {
        Box(
            modifier = Modifier
                .background(backgroundColor)
                .fillMaxWidth()
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(bottomStart = 4.dp, bottomEnd = 4.dp)
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = logo)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = username)
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        painter = profilePicture,
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .clickable(onClick = onProfileClick)
                            .size(40.dp) // Set the size of the profile picture if needed
                    )
                }
            }
        }
        // Shadowed Divider
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(shadowColor, Color.Transparent)
                    )
                )
        )
    }
}
