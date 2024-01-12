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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun CommunityHomeScreen(
    currentUser: User,
    community: Community?,
    onProfileClick: () -> Unit,
    onAnnouncementsClick: () -> Unit,
    onFindBooksClick: () -> Unit,
    onPostBooksClick: () -> Unit,
    onRentedBooksClick: () -> Unit,
    onMyLibraryClick: () -> Unit
) {

    var communityName by remember { mutableStateOf(community?.name) }
    var communityImage by remember { mutableStateOf(community?.imageUrl) }

    val buttonModifier = Modifier
        .height(IntrinsicSize.Min)
        .width(200.dp)

    Column {
        Navbar(currentUser = currentUser, onProfileClick = onProfileClick)
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            AsyncImage(
                model = communityImage,
                contentDescription = "Community Image",
                modifier = Modifier
                    .size(150.dp)
                    .padding(top = 16.dp),
                contentScale = ContentScale.Crop
            )

            communityName?.let {
                Text(
                    text = it,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            }

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
