package com.example.bookunities

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    currentUser: User?,
    onLogout: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToJoinCreate: () -> Unit,
    onNavigateToAboutUs: () -> Unit,
    onNavigateToCommunity: () -> Unit
) {
    val communityUserId by remember { mutableStateOf<String?>(currentUser?.communityUserId) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f, true))

        if (currentUser == null) {
            Text("Login to see the Home Page", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = onNavigateToLogin) {
                Text("Go to Login")
            }
        } else {
            Text("Welcome to the Home Page", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = onLogout
            ) {
                Text("Logout")
            }
            Spacer(modifier = Modifier.height(20.dp))
            if(communityUserId.isNullOrEmpty()){
                Button(onClick = onNavigateToJoinCreate) {
                    Text("Welcome to our App!")
                }
            } else{
                Button(onClick = onNavigateToCommunity) {
                    Text("Go to Community")
                }
            }

        }

        Spacer(modifier = Modifier.weight(1f, true))

        // About Us button at the bottom
        Button(onClick = onNavigateToAboutUs) {
            Text("About Us")
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}
