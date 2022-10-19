package com.example.chatapplication.ui.theme

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TutViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TutUIState())
    val uiState: StateFlow<TutUIState> = _uiState.asStateFlow()

    fun updateName(name: String) {
        _uiState.update { TutUIState ->
            TutUIState.copy(
                name = name
            )
        }
    }

    fun updatePwd(pwd: String) {
        _uiState.update { TutUIState ->
            TutUIState.copy(
                pwd = pwd
            )
        }
    }

    fun updateUser(username: String) {
        _uiState.update { TutUIState ->
            TutUIState.copy(
                username = username
            )
        }
    }

    fun updateSearch(search: String) {
        _uiState.update { TutUIState ->
            TutUIState.copy(
                search = search
            )
        }
    }
}