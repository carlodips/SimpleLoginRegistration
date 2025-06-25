package com.carlodips.simpleloginregistration.ui.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.carlodips.simpleloginregistration.R
import com.carlodips.simpleloginregistration.ui.theme.SimpleLoginRegistrationTheme

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LoginScreenContent()
    }
}

@Composable
fun LoginScreenContent(modifier: Modifier = Modifier) {
    val temp = remember {
        mutableStateOf("")
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(R.dimen.element_spacing_x2))
            .verticalScroll(rememberScrollState()),
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.element_spacing_x2)))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.app_name),
            textAlign = TextAlign.Center
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = temp.value,
            onValueChange = {},
            label = {
                Text(stringResource(R.string.label_username_email))
            }
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.element_spacing)))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = temp.value,
            onValueChange = {},
            label = {
                Text(stringResource(R.string.label_password))
            }
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.element_spacing_x2)))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { }) {
            Text(
                modifier = Modifier.padding(dimensionResource(R.dimen.element_spacing)),
                text = stringResource(R.string.button_login)
            )
        }

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.element_spacing_half)))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.button_sign_up),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.element_spacing_x3)))
    }
}

@Preview
@Composable
fun PreviewLoginScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        SimpleLoginRegistrationTheme {
            LoginScreenContent()
        }
    }
}