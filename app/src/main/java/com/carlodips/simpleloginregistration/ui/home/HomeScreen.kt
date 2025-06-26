package com.carlodips.simpleloginregistration.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.carlodips.simpleloginregistration.R
import com.carlodips.simpleloginregistration.domain.model.UserBean
import com.carlodips.simpleloginregistration.ui.theme.SimpleLoginRegistrationTheme

data class HomeUIState(
    val loggedInUser: UserBean?,
)

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()


    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        HomeScreenContent(uiState = uiState)
    }
}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    uiState: State<HomeUIState>,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .windowInsetsPadding(insets = WindowInsets.safeDrawing),
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.element_spacing_x3)))

        Text(
            text = stringResource(
                R.string.msg_hello_user,
                uiState.value.loggedInUser?.username ?: ""
            )
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.element_spacing_x3)))
    }
}

@Preview
@Composable
fun PreviewHomeScreen() {
    val uiState = remember {
        mutableStateOf(
            HomeUIState(
                loggedInUser = UserBean(
                    username = "carlo123",
                    email = "carlo123@gmail.com",
                    password = "abc12345",
                )
            )
        )
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        SimpleLoginRegistrationTheme {
            HomeScreenContent(
                uiState = uiState
            )
        }
    }
}
