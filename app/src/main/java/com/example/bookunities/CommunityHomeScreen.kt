package com.example.bookunities

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth

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
    // Same-sized buttons centered in the middle of the page
    val buttonModifier = Modifier
        .height(IntrinsicSize.Min)
        .width(200.dp) // You can adjust the width to your preference

    Column {
        Navbar(onProfileClick = onProfileClick)
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(32.dp))

//            Image(
//                painter = rememberImagePainter(communityImage),
//                contentDescription = "Community Image",
//                modifier = Modifier
//                    .size(150.dp) // Set the size as needed
//                    .padding(top = 16.dp),
//                contentScale = ContentScale.Crop
//            )

            // Name of the community
//            Text(
//                text = communityName,
//                modifier = Modifier.padding(vertical = 8.dp),
//                style = MaterialTheme.typography.titleMedium
//            )

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
