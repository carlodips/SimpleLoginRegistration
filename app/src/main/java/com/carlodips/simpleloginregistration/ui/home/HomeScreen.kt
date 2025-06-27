package com.carlodips.simpleloginregistration.ui.home

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.carlodips.simpleloginregistration.R
import com.carlodips.simpleloginregistration.domain.model.UserBean
import com.carlodips.simpleloginregistration.ui.theme.SimpleLoginRegistrationTheme
import com.carlodips.simpleloginregistration.ui.util.CustomAlertDialog

data class HomeUIState(
    val loggedInUser: UserBean?,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onLogoutUser: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    val shouldShowLogoutDialog = remember { mutableStateOf(false) }

    BackHandler(enabled = true) {
        shouldShowLogoutDialog.value = true
    }

    Surface(
        modifier = modifier.fillMaxSize(),
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
                            stringResource(R.string.label_home),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    actions = {
                        IconButton(onClick = {
                            shouldShowLogoutDialog.value = true
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Logout,
                                contentDescription = stringResource(R.string.button_logout)
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior,
                )
            },
        ) { innerPadding ->
            HomeScreenContent(
                modifier = Modifier.padding(innerPadding),
                uiState = uiState
            )
        }
    }

    if (shouldShowLogoutDialog.value) {
        LogoutDialog(
            shouldShowDialog = shouldShowLogoutDialog,
            onConfirmation = {
                viewModel.logoutUser()
                onLogoutUser.invoke()
            }
        )
    }
}

@Composable
fun LogoutDialog(
    modifier: Modifier = Modifier,
    shouldShowDialog: MutableState<Boolean>,
    onConfirmation: () -> Unit
) {
    CustomAlertDialog(
        modifier = modifier,
        onDismissRequest = { shouldShowDialog.value = false },
        onConfirmation = {
            shouldShowDialog.value = false
            onConfirmation.invoke()
        },
        confirmButtonText = stringResource(R.string.button_yes),
        dismissButtonText = stringResource(R.string.button_cancel),
        dialogTitle = stringResource(R.string.title_logout),
        dialogText = stringResource(R.string.msg_logout),
        icon = Icons.AutoMirrored.Filled.Logout
    )
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(R.dimen.element_spacing_x2)),
            text = stringResource(
                R.string.msg_hello_user,
                uiState.value.loggedInUser?.username ?: ""
            ),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
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
