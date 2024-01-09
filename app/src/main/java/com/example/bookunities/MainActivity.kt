package com.example.bookunities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.firebase.auth.FirebaseAuth

enum class Screen {
    Home, Login, Registration, JoinCreate, Profile, Join, AboutUs, Create
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var currentScreen by remember { mutableStateOf(Screen.Home) }
            val auth = FirebaseAuth.getInstance()
            var currentUser by remember { mutableStateOf(auth.currentUser) }

            when (currentScreen) {
                Screen.Home -> HomeScreen(
                    onNavigateToLogin = { currentScreen = Screen.Login },
                    onNavigateToJoinCreate = { currentScreen = Screen.JoinCreate },
                    onNavigateToAboutUs = { currentScreen = Screen.AboutUs }
                )

                Screen.Login -> LoginScreen(
                    onLoginSuccess = { currentScreen = Screen.JoinCreate },
                    onNavigateToRegistration = { currentScreen = Screen.Registration }
                )

                Screen.Registration -> RegistrationScreen(
                    onRegistrationSuccess = { currentScreen = Screen.Login },
                    onNavigateToLogin = {
                        currentScreen = Screen.Login
                    }
                )

                Screen.JoinCreate -> JoinCreateScreen(
                    onProfileClick = { currentScreen = Screen.Profile },
                    onJoinClick = {currentScreen = Screen.Join},
                    onCreateClick = {currentScreen = Screen.Create}
                )

                Screen.Profile -> ProfileScreen(
                    onLogout = {
                        auth.signOut()
                        currentUser = null
                        currentScreen = Screen.Home
                    },
                    onDeleteAccount = {
                        auth.signOut()
                        currentUser = null
                        currentScreen = Screen.Home
                    },
                    onLeaveCommunity = { /*TODO*/ },
                    onBackPress = { currentScreen = Screen.JoinCreate })

                Screen.Join -> JoinScreen(
                    onProfileClick = { currentScreen = Screen.Profile },
                    onBackPress = {currentScreen = Screen.Home}
                )

                Screen.Create -> CreateScreen(
                )

                Screen.AboutUs -> AboutPage(
                    onBackPress = { currentScreen = Screen.Home }
                )
            }
        }
    }
}