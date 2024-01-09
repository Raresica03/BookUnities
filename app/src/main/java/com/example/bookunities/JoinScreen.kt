package com.example.bookunities

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable

@Composable
fun JoinScreen(onProfileClick: () -> Unit) {
    Column {
        Navbar(
            onProfileClick = onProfileClick
        )
    }
}