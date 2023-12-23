package com.example.chatapp.data.remote

import com.example.chatapp.domain.model.Message
import com.example.chatapp.util.RequestState
import kotlinx.coroutines.flow.Flow

class ChatSocketServiceImpl: ChatSocketService {

    override suspend fun initSession(): RequestState<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun sendMessage(message: String) {
        TODO("Not yet implemented")
    }

    override fun observeMessage(): Flow<Message> {
        TODO("Not yet implemented")
    }

    override suspend fun closeSession() {
        TODO("Not yet implemented")
    }

}