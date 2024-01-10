package com.example.bookunities

import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.firebase.auth.FirebaseAuth
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.runtime.*
import com.google.firebase.firestore.FirebaseFirestore

enum class Screen {
    Home, Login, Registration, JoinCreate, Profile, Join, AboutUs, Create, CommunityHome
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()
        setContent {
            var currentUser by remember { mutableStateOf(auth.currentUser) }
            var communityUserId by remember { mutableStateOf<String?>(null) }
            var initialScreen by remember { mutableStateOf<Screen>(Screen.Home) }
            var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }

            LaunchedEffect(currentUser,communityUserId) {
                if (currentUser != null) {
                    db.collection("users").document(currentUser!!.uid).get()
                        .addOnSuccessListener { document ->
                            communityUserId = document.getString("communityUserId")
                            // Determine initial screen based on communityUserId
                            initialScreen = if (communityUserId.isNullOrEmpty()) {
                                Screen.JoinCreate
                            } else {
                                Screen.CommunityHome
                            }
                        }
                        .addOnFailureListener {
                        }
                }
            }
            when (currentScreen) {
                Screen.Home -> HomeScreen(
                    onNavigateToLogin = { currentScreen = Screen.Login },
                    onNavigateToJoinCreate = { currentScreen = Screen.JoinCreate },
                    onNavigateToAboutUs = { currentScreen = Screen.AboutUs },
                    onNavigateToCommunity = {currentScreen = Screen.CommunityHome}
                )

                Screen.Login -> LoginScreen(
                    onLoginSuccess = { currentScreen = initialScreen },
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
                    onJoinClick = { currentScreen = Screen.Join },
                    onCreateClick = { currentScreen = Screen.Create }
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
                    onBackPress = { currentScreen = initialScreen })

                Screen.Join -> JoinScreen(
                    onProfileClick = { currentScreen = Screen.Profile },
                    onBackPress = { currentScreen = Screen.JoinCreate },
                    onJoinedSuccessfully = { currentScreen = Screen.CommunityHome }
                )

                Screen.Create -> CreateScreen(
                    onBackPress = { currentScreen = Screen.JoinCreate }
                )

                Screen.AboutUs -> AboutPage(
                    onBackPress = { currentScreen = Screen.Home }
                )

                Screen.CommunityHome -> CommunityHomeScreen(
                    onProfileClick = { currentScreen = Screen.Profile },
                    onMyLibraryClick = {},
                    onRentedBooksClick = {},
                    onPostBooksClick = {},
                    onFindBooksClick = {},
                    onAnnouncementsClick = {}
                )
            }
        }
    }
}