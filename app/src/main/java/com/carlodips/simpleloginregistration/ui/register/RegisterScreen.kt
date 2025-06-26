package com.carlodips.simpleloginregistration.ui.register

import android.widget.Toast
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.carlodips.simpleloginregistration.R
import com.carlodips.simpleloginregistration.ui.theme.SimpleLoginRegistrationTheme
import com.carlodips.simpleloginregistration.ui.util.PasswordTextField
import com.carlodips.simpleloginregistration.ui.util.TextFieldWithErrorMessage

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

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = hiltViewModel(),
    onPopBackToLogin: () -> Unit
) {
    val context = LocalContext.current

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.value.isRegistrationSuccessful) {
        LaunchedEffect(key1 = uiState.value.isRegistrationSuccessful) {
            Toast.makeText(
                context,
                context.getString(R.string.msg_register_success), Toast.LENGTH_LONG
            ).show()
            onPopBackToLogin.invoke()
        }
    }

    Surface(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(insets = WindowInsets.safeDrawing),
        color = MaterialTheme.colorScheme.background
    ) {
        RegisterScreenContent(
            uiState = uiState,
            onInputValueChanged = viewModel::onInputValueChanged,
            onSubmitButtonClicked = viewModel::onSubmitButtonClicked
        )
    }
}

@Composable
fun RegisterScreenContent(
    modifier: Modifier = Modifier,
    uiState: State<RegisterUIState>,
    onInputValueChanged: (Int, String) -> Unit,
    onSubmitButtonClicked: () -> Unit
) {
    /*val isSubmitButtonEnabled by remember {
        derivedStateOf {
            uiState.value.let {
                it.usernameError?.isValid == true && it.emailError?.isValid == true &&
                        it.passwordError?.isValid == true &&
                        ValidationUtil.doPasswordsMatch(
                            password1 = uiState.value.password,
                            password2 = uiState.value.confirmPassword
                        ).isValid
            }
        }
    }*/

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(R.dimen.element_spacing_x2))
            .verticalScroll(rememberScrollState()),
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.element_spacing_x2)))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.label_register),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )

        TextFieldWithErrorMessage(
            modifier = Modifier
                .fillMaxWidth(),
            textValue = uiState.value.username,
            onValueChange = {
                onInputValueChanged.invoke(R.string.label_username, it)
            },
            errorMessage = uiState.value.usernameError,
            label = stringResource(R.string.label_username),
            singleLine = true
        )

        TextFieldWithErrorMessage(
            modifier = Modifier
                .fillMaxWidth(),
            textValue = uiState.value.email,
            onValueChange = {
                onInputValueChanged.invoke(R.string.label_email, it)
            },
            errorMessage = uiState.value.emailError,
            label = stringResource(R.string.label_email),
        )

        PasswordTextField(
            modifier = Modifier
                .fillMaxWidth(),
            textValue = uiState.value.password,
            onValueChange = {
                onInputValueChanged.invoke(R.string.label_password, it)
            },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            errorMessage = uiState.value.passwordError,
            label = stringResource(R.string.label_password),
        )

        PasswordTextField(
            modifier = Modifier
                .fillMaxWidth(),
            textValue = uiState.value.confirmPassword,
            onValueChange = {
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
