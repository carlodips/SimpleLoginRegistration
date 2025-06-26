package com.carlodips.simpleloginregistration.ui.util

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import com.carlodips.simpleloginregistration.R

@Composable
fun TextFieldWithErrorMessage(
    modifier: Modifier = Modifier,
    textValue: String,
    label: String,
    errorMessage: String? = null,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        capitalization = KeyboardCapitalization.Sentences,
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Next
    ),
    onValueChange: (String) -> Unit = {}
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
