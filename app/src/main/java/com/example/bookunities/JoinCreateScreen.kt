 package com.example.bookunities

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun JoinCreateScreen(onProfileClick: () -> Unit, onJoinClick:() -> Unit) {
    Column {
        Navbar(
            onProfileClick = onProfileClick
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(
                    1.dp,
                    Color.Black,
                    RoundedCornerShape(4.dp)
                ) // Border for the overall layout if needed
                .shadow(1.dp, RoundedCornerShape(4.dp)), // Shadow for some depth
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // JOIN button and description
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = onJoinClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Join", fontSize = 18.sp)
                }
                Text(
                    "Join an existing community",
                    modifier = Modifier.padding(top = 8.dp),
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.width(16.dp)) // Spacer between buttons

            // CREATE button and description
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { /* Handle create action */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Create", fontSize = 18.sp)
                }
                Text(
                    "Create a new community",
                    modifier = Modifier.padding(top = 8.dp),
                    fontSize = 14.sp
                )
            }
        }
    }
}