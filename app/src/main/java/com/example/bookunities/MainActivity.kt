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
    Home, Login, Registration, JoinCreate, Profile, Join, AboutUs, Create, CommunityHome
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val auth = FirebaseAuth.getInstance()
            var currentUser by remember { mutableStateOf(auth.currentUser) }
            // Determine the initial screen based on user status and communityId
            var initialScreen = when {
                currentUser == null -> Screen.Home // User not logged in
                currentUser.communityUserId.isNullOrEmpty() -> Screen.Home // Logged in but no communityId
                else -> Screen.CommunityHome // Logged in and has communityId
            }
            var currentScreen by remember { mutableStateOf(initialScreen) }
            when (currentScreen) {
                Screen.Home -> HomeScreen(
                    onNavigateToLogin = { currentScreen = initialScreen },
                    onNavigateToJoinCreate = { currentScreen = Screen.JoinCreate },
                    onNavigateToAboutUs = { currentScreen = Screen.AboutUs }
                )

                Screen.Login -> LoginScreen(
                    onLoginSuccess = { currentScreen = Screen.JoinCreate },
                    onNavigateToRegistration = { currentScreen = Screen.Registration }
                )

                Screen.Registration -> RegistrationScreen(
                    onRegistrationSuccess = { currentScreen = initialScreen },
                    onNavigateToLogin = {
                        currentScreen = initialScreen
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
                        currentScreen = initialScreen
                    },
                    onDeleteAccount = {
                        auth.signOut()
                        currentUser = null
                        currentScreen = initialScreen
                    },
                    onLeaveCommunity = { /*TODO*/ },
                    onBackPress = { currentScreen = Screen.JoinCreate })

                Screen.Join -> JoinScreen(
                    onProfileClick = { currentScreen = Screen.Profile },
                    onBackPress = {currentScreen = initialScreen}
                )

                Screen.Create -> CreateScreen(
                )

                Screen.AboutUs -> AboutPage(
                    onBackPress = { currentScreen = initialScreen }
                )
                Screen.CommunityHome -> CommunityHomeScreen(
                    onProfileClick = {currentScreen = Screen.Profile},
                    onMyLibraryClick ={},
                    onRentedBooksClick = {},
                    onPostBooksClick = {},
                    onFindBooksClick = {},
                    onAnnouncementsClick = {}
                )
            }
        }
    }
}