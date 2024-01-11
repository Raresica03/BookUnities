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
import android.util.Log
import androidx.compose.runtime.*
import com.google.firebase.firestore.FirebaseFirestore

enum class Screen {
    Home, Login, Registration, JoinCreate, Profile, Join, AboutUs, Create, CommunityHome
}

data class Community(
    val name: String = "",
    val imageUrl: String = "",
    val id: String = "",
)

data class User(
    val email: String = "",
    val lastName: String = "",
    val firstName: String = "",
    val phoneNumber: String = "",
    var communityUserId: String = ""
)

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()

        setContent {
            var currentUser by remember { mutableStateOf(auth.currentUser) }
            var user by remember { mutableStateOf<User?>(null) }

            var community by remember { mutableStateOf<Community?>(null) }

            var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }

            var isUserDataFetched by remember { mutableStateOf(false) }

            val doLogout = {
                auth.signOut()
                currentUser = null
                user = null
                currentScreen = Screen.Home
            }

            val updateUser: (User, Community) -> Unit = { newUser, newCommunity ->
                user = newUser
                community = newCommunity
            }

            val updateUserAfterLeave: (User) -> Unit = { newUser ->
                user = newUser
                community = null
            }

            LaunchedEffect(currentUser, user) {
                if (currentUser != null) {
                    // Here happens the fetching of data
                    db.collection("users").document(currentUser!!.uid).get()
                        .addOnSuccessListener { document ->

                            val fetchedUser = document.toObject(User::class.java)
                            user = fetchedUser
                            // Fetch community data if communityId is available
                            fetchedUser?.communityUserId?.let { communityId ->
                                if (communityId.isNotEmpty()) {
                                    db.collection("communities").document(communityId).get()
                                        .addOnSuccessListener { communityDoc ->
                                            community = communityDoc.toObject(Community::class.java)
                                        }
                                } else {
                                    community = null
                                }
                            }
                        }
                } else {
                    user = null
                    community = null
                }
            }

            when (currentScreen) {
                Screen.Home -> {
                    HomeScreen(
                        currentUser = user,
                        onLogout = doLogout,
                        onNavigateToLogin = { currentScreen = Screen.Login },
                        onNavigateToJoinCreate = {
                            currentScreen =
                                if (user?.communityUserId.isNullOrEmpty()) Screen.JoinCreate
                                else Screen.CommunityHome
                        },
                        onNavigateToAboutUs = { currentScreen = Screen.AboutUs },
                        onNavigateToCommunity = { currentScreen = Screen.CommunityHome }
                    )
                }

                Screen.Login -> LoginScreen(
                    onLoginSuccess = {
                        currentUser = auth.currentUser
                        if (isUserDataFetched) {
                            currentScreen =
                                if (user?.communityUserId.isNullOrEmpty()) Screen.JoinCreate
                                else Screen.CommunityHome
                        } else {
                            currentScreen = Screen.Profile
                        }
                    },
                    onNavigateToRegistration = { currentScreen = Screen.Registration }
                )

                Screen.Registration -> RegistrationScreen(
                    onRegistrationSuccess = { currentScreen = Screen.Login },
                    onNavigateToLogin = {
                        currentScreen = Screen.Login
                    }
                )

                Screen.JoinCreate -> {
                    user?.let { usr ->
                        JoinCreateScreen(
                            currentUser = usr,
                            onProfileClick = { currentScreen = Screen.Profile },
                            onJoinClick = { currentScreen = Screen.Join },
                            onCreateClick = { currentScreen = Screen.Create }
                        )
                    }
                }


                Screen.Profile -> {
                    user?.let { usr ->
                        // Directly pass community, which can be null, to ProfileScreen
                        ProfileScreen(
                            currentUser = usr,
                            currentCommunity = community, // Can be null, and that's okay
                            onLogout = doLogout,
                            onDeleteAccount = doLogout,
                            onLeaveCommunity = { newUser ->
                                user = newUser
                                community = null
                                currentScreen = Screen.JoinCreate
                            },
                            onBackPress = {
                                currentScreen = if (usr.communityUserId.isEmpty())
                                    Screen.JoinCreate
                                else
                                    Screen.CommunityHome
                            }
                        )
                    }
                }

                Screen.Join ->
                    user?.let { usr ->
                        JoinScreen(
                            currentUser = usr,
                            onProfileClick = { currentScreen = Screen.Profile },
                            onBackPress = { currentScreen = Screen.JoinCreate },
                            onJoinedSuccessfully = {
                                currentScreen = Screen.CommunityHome
                            },
                            onUpdateUser = updateUser
                        )
                    }

                Screen.Create -> CreateScreen(
                    onBackPress = { currentScreen = Screen.JoinCreate }
                )

                Screen.AboutUs -> AboutPage(
                    onBackPress = { currentScreen = Screen.Home }
                )

                Screen.CommunityHome -> {
                    user?.let { usr ->
                        CommunityHomeScreen(
                            currentUser = usr,
                            community = community,
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
    }
}