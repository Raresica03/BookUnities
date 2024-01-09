package com.example.bookunities

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AboutPage(onBackPress: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.blank_profile), // Replace with your logo resource ID
                    contentDescription = "Logo",
                    modifier = Modifier.size(100.dp) // Adjust size as needed
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "About BookUnities",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(16.dp))

                Divider(color = Color.Gray, thickness = 1.dp)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "BookUnities connects readers and encourages sharing books. Discover new stories and join a community of diverse readers.",
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Our platform is user-friendly, perfect for avid readers and newcomers alike. Share, discover, and enjoy books effortlessly.",
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "We promote sustainability through book exchanges, reducing waste and supporting eco-friendly reading practices.",
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Join BookUnities today! Be part of our book-loving community and explore a world of books like never before.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
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