package com.example.chatapp.presentation.screen.main

import androidx.paging.PagingData
import com.example.chatapp.domain.model.User
import kotlinx.coroutines.flow.Flow

data class MainState(
    val fetchUsers: Flow<PagingData<User>>? = null
)
