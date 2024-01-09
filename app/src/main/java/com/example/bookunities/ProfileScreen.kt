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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileScreen(
    onLogout: () -> Unit,
    onDeleteAccount: () -> Unit,
    onLeaveCommunity: () -> Unit,
    onBackPress: () -> Unit
) {
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

        Spacer(modifier = Modifier.height(16.dp))

        // Profile detail placeholders
        for (i in 1..3) {
            Text(
                text = "Detail $i",
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Action buttons
        Button(onClick = onLeaveCommunity, modifier = Modifier.fillMaxWidth()) {
            Text("Leave com")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onLogout, modifier = Modifier.fillMaxWidth()) {
            Text("Logout")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onDeleteAccount,
            //colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Delete acc", color = Color.White)
        }

        Spacer(modifier = Modifier.weight(1f))

        // Back navigation button
        Button(onClick = onBackPress, modifier = Modifier.align(Alignment.Start)) {
//            Image(
//                imageVector = ImageVector.vectorResource(id = R.drawable.ic_back_arrow),
//                contentDescription = "Back",
//                modifier = Modifier.size(24.dp)
//            )
        }
    }
}