package com.example.chatapp.presentation.screen.main

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.chatapp.navigation.Screen
import com.example.chatapp.presentation.screen.common.BottomBar

@Composable
fun MainScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel
) {

    LaunchedEffect(key1 = true) {
        mainViewModel.fetchUsers()
        mainViewModel.getCurrentUser()
    }


    Log.d("ListContent","MainScreen")
    val users = mainViewModel.fetchedUser.collectAsLazyPagingItems()
    val searchedUsers = mainViewModel.searchedUser.collectAsLazyPagingItems()
    Log.d("helloWorld","MainScreen")
    val currentUser by mainViewModel.currentUser.collectAsState()
    Log.d("helloWorld","a-MainScreen")
    val searchQuery = mainViewModel.searchQuery.collectAsState()

    Scaffold(
        topBar = {
            MainTopBar(
                text = searchQuery.value,
                onTextChange = {
                    mainViewModel.updateSearchQuery(it)
                    mainViewModel.searchUser()
                },
                onSearchClicked = {
                    mainViewModel.searchUser()
                },
                currentUser = currentUser
            )
        },
        content = { paddingValue ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValue)
            ) {
                MainContent(
                    users = users,
                    searchedUsers = searchedUsers,
                    searchQuery = searchQuery.value,
                    navigationToChatScreen = {
                        navController.navigate(Screen.Chat.passId(id = it))
                    },
                    fetchLastMessage = { userId ->
                        mainViewModel.updateChatId(userId)
                        mainViewModel.getUserInfoByUserId()
                        mainViewModel.fetchLastChat(userId)
                        Log.d("lastMessageDebug","fetchLastMessage")
                        Log.d("lastMessageDebug", mainViewModel.lastMessage.value?.messageText.toString())
                        mainViewModel.lastMessage.value
                    },
                    getAuthorName = { authorUserId ->
                        mainViewModel.chatUser.value.name
                    }
                )
            }
        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    )
}