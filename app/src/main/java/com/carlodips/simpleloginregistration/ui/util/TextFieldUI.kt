package com.carlodips.simpleloginregistration.ui.util

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun TextFieldWithErrorMessage(
    modifier: Modifier = Modifier,
    textValue: String,
    label: String,
    errorMessage: String? = null,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        capitalization = KeyboardCapitalization.None,
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Next,
        autoCorrectEnabled = false
    ),
    onValueChange: (String) -> Unit = {},
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(),
        value = textValue,
        onValueChange = {
            onValueChange(it)
        },
        label = { Text(label) },
        singleLine = singleLine,
        isError = !errorMessage.isNullOrEmpty(),
        supportingText = {
            if (!errorMessage.isNullOrEmpty()) {
                Text(
                    text = errorMessage,
                )
            }
        },
        keyboardOptions = keyboardOptions
    )
}

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    textValue: String,
    label: String,
    errorMessage: String? = null,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        capitalization = KeyboardCapitalization.None,
        keyboardType = KeyboardType.Password,
        imeAction = ImeAction.Next
    ),
    onValueChange: (String) -> Unit = {},
) {
    val showPassword = remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(),
        value = textValue,
        onValueChange = {
            onValueChange(it)
        },
        label = { Text(label) },
        singleLine = singleLine,
        isError = !errorMessage.isNullOrEmpty(),
        supportingText = {
            if (!errorMessage.isNullOrEmpty()) {
                Text(
                    text = errorMessage,
                )
            }
        },
        keyboardOptions = keyboardOptions,
        visualTransformation = if (showPassword.value) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        trailingIcon = {
            val (icon, iconColor) = if (showPassword.value) {
                Pair(
                    Icons.Filled.Visibility,
                    MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                Pair(Icons.Filled.VisibilityOff, MaterialTheme.colorScheme.onSurfaceVariant)
            }

            IconButton(onClick = { showPassword.value = !showPassword.value }) {
                Icon(
                    imageVector = icon,
                    contentDescription = "Visibility",
                    tint = iconColor
                )
            }
        },
    )
}
