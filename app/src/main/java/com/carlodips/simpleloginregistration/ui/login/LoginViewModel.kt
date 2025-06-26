package com.carlodips.simpleloginregistration.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlodips.simpleloginregistration.domain.repository.UserRepository
import com.carlodips.simpleloginregistration.domain.validation.LoginValidationUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: UserRepository,
) : ViewModel() {
    private var job: Job? = null

    private val _uiState = MutableStateFlow(LoginUIState())
    val uiState: StateFlow<LoginUIState>
        get() = _uiState.asStateFlow()

    private val _resultEventFlow = MutableSharedFlow<LoginResultEvent>()
    val resultEventFlow = _resultEventFlow.asSharedFlow()


    fun onUiEvent(event: LoginInputEvent) {
        when (event) {
            is LoginInputEvent.EnteredUserId -> {
                _uiState.update {
                    it.copy(userId = event.userId)
                }
                validateInputs()
            }

            is LoginInputEvent.EnteredPassword -> {
                _uiState.update {
                    it.copy(password = event.password)
                }
                validateInputs()
            }

            is LoginInputEvent.SignUpClicked -> {
                if (job?.isActive == true) return

                job = viewModelScope.launch {
                    _resultEventFlow.emit(LoginResultEvent.SignUpNavigate)
                }
            }

            is LoginInputEvent.LoginClicked -> {
                onLoginButtonClicked()
            }
        }
    }

    private fun validateInputs() {
        val isUserIdValid = LoginValidationUtil.validateUserId(uiState.value.userId).isValid
        val isPasswordValid = LoginValidationUtil.validatePassword(uiState.value.password).isValid

        _uiState.update {
            it.copy(isLoginButtonEnabled = isUserIdValid && isPasswordValid)
        }
    }

    private fun onLoginButtonClicked() {
        if (job?.isActive == true) return

        job = viewModelScope.launch {
            val user = withContext(Dispatchers.IO) {
                repository.getUser(
                    userInput = uiState.value.userId,
                    password = uiState.value.password
                )
            }

            withContext(Dispatchers.Main) {
                if (user != null) {
                    _resultEventFlow.emit(LoginResultEvent.LoginSuccess)
                } else {
                    _resultEventFlow.emit(LoginResultEvent.LoginFailed)
                }
            }
        }
    }
}
