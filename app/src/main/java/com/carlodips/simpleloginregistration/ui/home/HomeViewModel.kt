package com.carlodips.simpleloginregistration.ui.home

import androidx.lifecycle.ViewModel
import com.carlodips.simpleloginregistration.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: UserRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUIState(repository.loggedInUser))
    val uiState: StateFlow<HomeUIState>
        get() = _uiState.asStateFlow()


    fun logoutUser() {
        repository.clearLoggedInUser()
    }

}