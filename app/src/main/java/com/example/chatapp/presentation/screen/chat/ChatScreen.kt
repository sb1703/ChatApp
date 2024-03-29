package com.example.chatapp.presentation.screen.chat

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.chatapp.presentation.screen.main.MainViewModel

@Composable
fun ChatScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    chatViewModel: ChatViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = true) {
        chatViewModel.getChatIdArgument()
        chatViewModel.getUserInfoByUserId()
        chatViewModel.fetchChats()
    }

    val currentUser by mainViewModel.currentUser.collectAsState()
    val chatUser by chatViewModel.chatUser.collectAsState()
    val chats = chatViewModel.fetchedChat.collectAsLazyPagingItems()
    val chatText by chatViewModel.chatText.collectAsState()

    Scaffold(
        topBar = {
            ChatTopBar(
                onBackStackClicked = {
                    navController.popBackStack()
                },
                name = chatUser.name,
                profilePicture = chatUser.profilePhoto
            )
        },
        content = { paddingValue ->
            Surface(
                modifier = Modifier.padding(paddingValue)
            ) {
                currentUser?.let { ChatContent(chats = chats, currentUser = it, chatUser = chatUser) }
                    ?: Log.d("chatContentDebug","currentUser is null")
            }
        },
        bottomBar = {
            ChatBottomBar(
                text = chatText,
                onTextChange = { chatViewModel.updateChatText(it) },
                onSendClicked = {
                    mainViewModel.currentUser.value?.userId?.let { chatViewModel.addChat(it) }
                    chatViewModel.clearChatText()
                }
            )
        }
    )

}