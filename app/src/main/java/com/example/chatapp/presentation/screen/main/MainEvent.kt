package com.example.chatapp.presentation.screen.main

sealed interface MainEvent {
    data object UpdateFetchUsers: MainEvent
}