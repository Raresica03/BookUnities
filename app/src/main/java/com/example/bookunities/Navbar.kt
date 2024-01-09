package com.example.bookunities

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun Navbar(onProfileClick: () -> Unit) {
    // Define the parameters inside the function
    val logo = "LOGO" // Replace with your actual logo resource
    val username = "YourUsername" // Replace with actual username logic
    val profilePicture = painterResource(R.drawable.blank_profile) // Replace with actual profile picture resource

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = logo)
       // Image(painter = logo, contentDescription = "App Logo")
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