package com.carlodips.simpleloginregistration.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlodips.simpleloginregistration.R
import com.carlodips.simpleloginregistration.domain.model.UserBean
import com.carlodips.simpleloginregistration.domain.repository.UserRepository
import com.carlodips.simpleloginregistration.domain.validation.RegisterValidationUtil
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
class RegisterViewModel @Inject constructor(
    private val repository: UserRepository,
) : ViewModel() {
    private var job: Job? = null

    private val _uiState = MutableStateFlow(RegisterUIState())
    val uiState: StateFlow<RegisterUIState>
        get() = _uiState.asStateFlow()

    private val _resultEventFlow = MutableSharedFlow<RegistrationResultEvent>()
    val resultEventFlow = _resultEventFlow.asSharedFlow()

    fun onInputValueChanged(id: Int, input: String) {
        when (id) {
            R.string.label_username -> {
                _uiState.update {
                    it.copy(username = input)
                }
                validateUsername()
            }

            R.string.label_email -> {
                _uiState.update {
                    it.copy(email = input)
                }
                validateEmail()
            }

            R.string.label_password -> {
                _uiState.update {
                    it.copy(password = input)
                }
                validatePassword()
                if (uiState.value.confirmPassword.isNotEmpty()) {
                    validateConfirmPassword()
                }
            }

            R.string.label_confirm_password -> {
                _uiState.update {
                    it.copy(confirmPassword = input)
                }
                validateConfirmPassword()
            }
        }
    }

    private fun validateUsername(): Boolean {
        val validationResult = RegisterValidationUtil.validateUsernameInput(uiState.value.username)

        _uiState.update {
            it.copy(usernameError = validationResult.errorMessage)
        }

        return validationResult.isValid
    }

    private fun validateEmail(): Boolean {
        val validationResult = RegisterValidationUtil.validateEmailInput(uiState.value.email)

        _uiState.update {
            it.copy(emailError = validationResult.errorMessage)
        }

        return validationResult.isValid
    }

    private fun validatePassword(): Boolean {
        val validationResult = RegisterValidationUtil.validatePassword(uiState.value.password)

        _uiState.update {
            it.copy(passwordError = validationResult.errorMessage)
        }

        return validationResult.isValid
    }

    private fun validateConfirmPassword(): Boolean {
        val validationResult = RegisterValidationUtil.doPasswordsMatch(
            password1 = uiState.value.password,
            password2 = uiState.value.confirmPassword
        )
        _uiState.update {
            it.copy(
                confirmPasswordError = validationResult.errorMessage
            )
        }

        return validationResult.isValid
    }

    private fun areInputsValid(): Boolean {
        val isUsernameValid = validateUsername()
        val isEmailValid = validateEmail()
        val isPasswordValid = validatePassword()
        val isConfirmPasswordValid = validateConfirmPassword()

        return isUsernameValid && isEmailValid && isPasswordValid && isConfirmPasswordValid
    }

    fun onSubmitButtonClicked() {
        if (!areInputsValid()) return

        if (job?.isActive == true) return

        job = viewModelScope.launch {
            withContext(Dispatchers.IO) {
                var isEmailOrUsernameTaken = false

                if (repository.checkIfEmailIsTaken(uiState.value.email)) {
                    _uiState.update {
                        it.copy(emailError = "Email is already taken")
                    }
                    isEmailOrUsernameTaken = true
                }
                if (repository.checkIfUsernameIsTaken(uiState.value.username)) {
                    _uiState.update {
                        it.copy(usernameError = "Username is already taken")
                    }
                    isEmailOrUsernameTaken = true
                }

                // If email or username is already taken, cancel inserting of user
                if (isEmailOrUsernameTaken) return@withContext

                val id = repository.insertUser(
                    user = UserBean(
                        username = uiState.value.username,
                        email = uiState.value.email,
                        password = uiState.value.password
                    )
                )

                if (id >= 0) {
                    _resultEventFlow.emit(RegistrationResultEvent.RegistrationSuccess)
                } else {
                    _resultEventFlow.emit(RegistrationResultEvent.RegistrationFailed)
                }
            }
        }
    }
}
