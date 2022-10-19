package com.example.chatapplication.ui.theme

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chatapplication.ui.theme.screens.LogInScreenUI
import com.example.chatapplication.ui.theme.screens.MainScreenUI
import com.example.chatapplication.ui.theme.screens.SignInScreenUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

enum class Names {
    LogIn,
    SignIn,
    Main,
    Chat
}


@Composable
fun Navigation(

    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    auth: FirebaseAuth,
    db: FirebaseFirestore
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Names.LogIn.name
    ) {
        composable(route = Names.LogIn.name) {
            LogInScreenUI(text = "Log In", auth = auth, navController = navController,db = db)
        }
        composable(route = Names.SignIn.name) {
            SignInScreenUI(text = "Sign In", auth = auth, navController = navController, db = db)
        }
        composable(route = Names.Main.name) {
            MainScreenUI(navController = navController, db = db)
        }
        composable(route = Names.Chat.name) {

        }
    }
}
