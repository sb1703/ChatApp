package com.example.chatapp.presentation.screen.chat

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.chatapp.domain.model.ApiRequest
import com.example.chatapp.domain.model.Message
import com.example.chatapp.domain.model.User
import com.example.chatapp.domain.repository.Repository
import com.example.chatapp.util.Constants.CHAT_USER_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: Repository
): ViewModel() {

    private val _chatUser = MutableStateFlow(User())
    val chatUser = _chatUser.asStateFlow()

    private val _chatId = MutableStateFlow("")
    val chatId = _chatId.asStateFlow()

    private val _fetchedChat = MutableStateFlow<PagingData<Message>>(PagingData.empty())
    val fetchedChat = _fetchedChat

    private val _chatText = MutableStateFlow("")
    val chatText = _chatText.asStateFlow()

//    init {
//        getChatIdArgument()
//        Log.d("hello",chatId.value)
//        viewModelScope.launch(Dispatchers.Main) {
//            getUserInfoByUserId()
//        }
//
//    }

    fun updateChatText(query: String) {
        _chatText.value = query
    }

    fun clearChatText() {
        _chatText.value = ""
    }

    fun getChatIdArgument() {
        _chatId.value = savedStateHandle.get<String> (
            key = CHAT_USER_ID
        ).toString()
    }

    fun fetchChats(){
        Log.d("chatContentDebug",chatId.value)
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchChats(
                request = ApiRequest(
                    userId = chatId.value
                )
            ).cachedIn(viewModelScope).collect{
                _fetchedChat.value = it
            }
        }
    }

    fun getUserInfoByUserId() {
        viewModelScope.launch(Dispatchers.IO) {
            _chatUser.value = repository.getUserInfoById(request = ApiRequest(userId = chatId.value)).user!!
        }
    }

    fun addChat(currentUserId: String) {
        if(chatText.value.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.addChats(request = ApiRequest(
                    message = Message(
                        author = currentUserId,
                        receiver = listOf(chatUser.value.userId),
                        messageText = chatText.value
                    )
                ))
            }
        }
    }

}