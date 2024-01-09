package com.example.bookunities

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
<<<<<<< HEAD
fun JoinScreen(onProfileClick: () -> Unit) {
    Column {
        Navbar(
            onProfileClick = onProfileClick
        )
=======
fun JoinScreen(onProfileClick: () -> Unit, onBackPress: () -> Unit) {
    var communityCode by remember { mutableStateOf("") }
    var showCommunityCode by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ){
        Navbar(onProfileClick = onProfileClick)
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = communityCode,
                onValueChange = {
                    communityCode = it
                    // Reset the showCommunityCode state whenever the input changes
                    showCommunityCode = false
                },
                label = { Text("Code") },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    // Set the state to true when the Find button is clicked
                    showCommunityCode = true
                }
            ) {
                Text("Find")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Conditionally display the community code based on the button click
        if (showCommunityCode && communityCode.isNotEmpty()) {
            Text(
                text = "Community Code: $communityCode",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Button(onClick = onBackPress) {
                Text("Back")
            }
            Spacer(modifier = Modifier.height(80.dp))
        }
>>>>>>> 4d584c02c31d76f94c538388c21ef67283eaf1d5
    }

}
