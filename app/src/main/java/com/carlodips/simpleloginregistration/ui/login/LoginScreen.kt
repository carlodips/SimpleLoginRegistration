package com.carlodips.simpleloginregistration.ui.login

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.carlodips.simpleloginregistration.R
import com.carlodips.simpleloginregistration.domain.validation.RegexPatternUtil
import com.carlodips.simpleloginregistration.navigation.ScreenRoute
import com.carlodips.simpleloginregistration.ui.theme.SimpleLoginRegistrationTheme
import com.carlodips.simpleloginregistration.ui.util.PasswordTextField
import com.carlodips.simpleloginregistration.ui.util.TextFieldWithErrorMessage
import kotlinx.coroutines.flow.collectLatest

data class LoginUIState(
    val userId: String = "",
    val password: String = "",
    val isLoginButtonEnabled: Boolean = false
)

sealed class LoginInputEvent {
    data class EnteredUserId(val userId: String) : LoginInputEvent()
    data class EnteredPassword(val password: String) : LoginInputEvent()
    data object LoginClicked : LoginInputEvent()
    data object SignUpClicked : LoginInputEvent()
}

sealed class LoginResultEvent {
    data object LoginSuccess : LoginResultEvent()
    data object LoginFailed : LoginResultEvent()
    data object SignUpNavigate : LoginResultEvent()
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    navigateTo: (route: ScreenRoute) -> Unit
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    // Handles result shared flow
    LaunchedEffect(Unit) {
        viewModel.resultEventFlow.collectLatest { event ->
            when (event) {
                is LoginResultEvent.LoginSuccess -> {
                    navigateTo.invoke(ScreenRoute.Home)
                }

                is LoginResultEvent.LoginFailed -> {
                    Toast.makeText(
                        context,
                        context.getString(R.string.msg_login_failed),
                        Toast.LENGTH_LONG
                    ).show()
                }

                is LoginResultEvent.SignUpNavigate -> {
                    navigateTo.invoke(ScreenRoute.Register)
                }
            }
        }
    }

    Surface(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(insets = WindowInsets.safeDrawing),
        color = MaterialTheme.colorScheme.background
    ) {
        LoginScreenContent(
            uiState = uiState,
            onUiEvent = { viewModel.onUiEvent(it) }
        )
    }
}

@Composable
fun LoginScreenContent(
    modifier: Modifier = Modifier,
    uiState: State<LoginUIState>,
    onUiEvent: (event: LoginInputEvent) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(R.dimen.element_spacing_x2))
            .verticalScroll(rememberScrollState()),
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.element_spacing_x3)))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.app_name),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.element_spacing_x4)))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.label_login),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.element_spacing_half)))

        TextFieldWithErrorMessage(
            modifier = Modifier
                .fillMaxWidth(),
            textValue = uiState.value.userId,
            onValueChange = {
                if (it.length > 40) return@TextFieldWithErrorMessage
                onUiEvent.invoke(LoginInputEvent.EnteredUserId(it))
            },
            label = stringResource(R.string.label_username_email)
        )

        PasswordTextField(
            modifier = Modifier
                .fillMaxWidth(),
            textValue = uiState.value.password,
            onValueChange = {
                if (it.length > 32) return@PasswordTextField
                onUiEvent.invoke(LoginInputEvent.EnteredPassword(it))
            },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
                autoCorrectEnabled = false
            ),
            label = stringResource(R.string.label_password)
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.element_spacing_x2)))

        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = uiState.value.isLoginButtonEnabled,
            onClick = {
                onUiEvent.invoke(LoginInputEvent.LoginClicked)
            }) {
            Text(
                modifier = Modifier.padding(dimensionResource(R.dimen.element_spacing)),
                text = stringResource(R.string.button_login)
            )
        }

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.element_spacing_half)))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onUiEvent.invoke(LoginInputEvent.SignUpClicked)
                },
            text = stringResource(R.string.button_sign_up),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.element_spacing_x3)))
    }
}

@Preview
@Composable
fun PreviewLoginScreen() {
    val uiState = remember { mutableStateOf(LoginUIState()) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        SimpleLoginRegistrationTheme {
            LoginScreenContent(
                uiState = uiState,
                onUiEvent = {}
            )
        }
    }
}
