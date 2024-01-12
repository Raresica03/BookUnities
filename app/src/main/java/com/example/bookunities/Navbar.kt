package com.example.bookunities

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Navbar(currentUser:User,onProfileClick: () -> Unit) {

    val username = currentUser.firstName.ifEmpty { "Guest" }
    val profilePicture =  (R.drawable.blank_profile)
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
                Image(
                    painter = painterResource(id = R.drawable.bookunities_logo),
                    contentDescription = "My WebP Image",
                    modifier = Modifier.size(40.dp)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = username, style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    ))
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        painter = painterResource(id = profilePicture),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .clickable(onClick = onProfileClick)
                            .size(40.dp)
                    )
                }
            }
        }
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
