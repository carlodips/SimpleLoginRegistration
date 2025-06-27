package com.carlodips.simpleloginregistration.ui.register

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.carlodips.simpleloginregistration.R
import com.carlodips.simpleloginregistration.ui.theme.SimpleLoginRegistrationTheme
import com.carlodips.simpleloginregistration.ui.util.CardUI
import com.carlodips.simpleloginregistration.ui.util.PasswordTextField
import com.carlodips.simpleloginregistration.ui.util.TextFieldWithErrorMessage
import kotlinx.coroutines.flow.collectLatest

data class RegisterUIState(
    val username: String = "",
    val usernameError: String? = null,
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val confirmPassword: String = "",
    val confirmPasswordError: String? = null,
    val isRegistrationSuccessful: Boolean = false
)

sealed class RegistrationResultEvent {
    data object RegistrationSuccess : RegistrationResultEvent()
    data object RegistrationFailed : RegistrationResultEvent()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = hiltViewModel(),
    onPopBackToLogin: () -> Unit
) {
    val context = LocalContext.current

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    // Handles result shared flow
    LaunchedEffect(Unit) {
        viewModel.resultEventFlow.collectLatest { event ->
            when (event) {
                is RegistrationResultEvent.RegistrationSuccess -> {
                    Toast.makeText(
                        context,
                        context.getString(R.string.msg_register_success), Toast.LENGTH_LONG
                    ).show()
                    onPopBackToLogin.invoke()
                }

                is RegistrationResultEvent.RegistrationFailed -> {
                    Toast.makeText(
                        context,
                        context.getString(R.string.msg_failed),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    Surface(
        modifier = modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text(
                            text = stringResource(R.string.label_register),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            onPopBackToLogin.invoke()
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Navigate back"
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior,
                )
            },
        ) { innerPadding ->
            RegisterScreenContent(
                modifier = Modifier.padding(innerPadding),
                uiState = uiState,
                onInputValueChanged = viewModel::onInputValueChanged,
                onSubmitButtonClicked = viewModel::onSubmitButtonClicked
            )
        }
    }
}

@Composable
fun RegisterScreenContent(
    modifier: Modifier = Modifier,
    uiState: State<RegisterUIState>,
    onInputValueChanged: (Int, String) -> Unit,
    onSubmitButtonClicked: () -> Unit
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState()),
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.element_spacing_x3)))

        CardUI(
            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.element_spacing_x2))
        ) {
            RegisterScreenForm(
                uiState = uiState,
                onInputValueChanged = onInputValueChanged,
                onSubmitButtonClicked = onSubmitButtonClicked
            )
        }

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.element_spacing_x3)))
    }
}

@Composable
fun RegisterScreenForm(
    modifier: Modifier = Modifier,
    uiState: State<RegisterUIState>,
    onInputValueChanged: (Int, String) -> Unit,
    onSubmitButtonClicked: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.element_spacing_x2))
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.element_spacing_x2)))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.msg_register),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.element_spacing_half)))

        TextFieldWithErrorMessage(
            modifier = Modifier
                .fillMaxWidth(),
            textValue = uiState.value.username,
            onValueChange = {
                if (it.length > 40) return@TextFieldWithErrorMessage
                onInputValueChanged.invoke(R.string.label_username, it)
            },
            errorMessage = uiState.value.usernameError,
            label = stringResource(R.string.label_username)
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.element_spacing_half)))

        TextFieldWithErrorMessage(
            modifier = Modifier
                .fillMaxWidth(),
            textValue = uiState.value.email,
            onValueChange = {
                if (it.length > 40) return@TextFieldWithErrorMessage
                onInputValueChanged.invoke(R.string.label_email, it)
            },
            errorMessage = uiState.value.emailError,
            label = stringResource(R.string.label_email),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
                autoCorrectEnabled = false
            )
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.element_spacing_half)))

        PasswordTextField(
            modifier = Modifier
                .fillMaxWidth(),
            textValue = uiState.value.password,
            onValueChange = {
                if (it.length > 32) return@PasswordTextField
                onInputValueChanged.invoke(R.string.label_password, it)
            },
            errorMessage = uiState.value.passwordError,
            label = stringResource(R.string.label_password),
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.element_spacing_half)))

        PasswordTextField(
            modifier = Modifier
                .fillMaxWidth(),
            textValue = uiState.value.confirmPassword,
            onValueChange = {
                if (it.length > 32) return@PasswordTextField
                onInputValueChanged.invoke(R.string.label_confirm_password, it)
            },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            errorMessage = uiState.value.confirmPasswordError,
            label = stringResource(R.string.label_confirm_password),
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.element_spacing_x2)))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onSubmitButtonClicked.invoke() }) {
            Text(
                modifier = Modifier.padding(dimensionResource(R.dimen.element_spacing)),
                text = stringResource(R.string.button_submit)
            )
        }

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.element_spacing_x3)))
    }
}


@Preview
@Composable
fun PreviewRegisterScreen() {
    val uiState = remember { mutableStateOf(RegisterUIState()) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        SimpleLoginRegistrationTheme {
            RegisterScreenContent(
                uiState = uiState,
                onInputValueChanged = { _, _ -> },
                onSubmitButtonClicked = {}
            )
        }
    }
}
