package com.elpablo.fscslutsky.ui.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elpablo.fscslutsky.R
import com.elpablo.fscslutsky.core.components.FSCSlutskyLoader
import com.vk.id.auth.VKIDAuthUiParams
import com.vk.id.onetap.common.OneTapStyle
import com.vk.id.onetap.common.button.style.OneTapButtonCornersStyle
import com.vk.id.onetap.common.button.style.OneTapButtonSizeStyle
import com.vk.id.onetap.compose.onetap.OneTap

@Composable
fun AuthScreen(
    snackbar: SnackbarHostState,
    uiState: AuthViewState,
    uiEvent: (AuthEvent) -> Unit,
    onNavigate: () -> Unit
) {
    if (uiState.isLoggedIn) {
        onNavigate.invoke()
    }
    if (uiState.isLoading) {
        FSCSlutskyLoader()
    }
    if (uiState.isError) {
        LaunchedEffect(null) {
            uiState.error.let { msg -> snackbar.showSnackbar(msg) }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 32.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp)
                .weight(1f),
            text = stringResource(R.string.screen_auth_title),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleLarge
        )
        OneTap(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 64.dp),
            onAuth = { oAuth, token ->
                uiEvent(AuthEvent.AuthSuccess)
            },
            onFail = { oAuth, error ->
                uiEvent(AuthEvent.AuthFail(error.description))
            },
            signInAnotherAccountButtonEnabled = true,
            style = OneTapStyle.Light(
                cornersStyle = OneTapButtonCornersStyle.Custom(16f),
                sizeStyle = OneTapButtonSizeStyle.DEFAULT
            ),
            authParams = VKIDAuthUiParams {
                scopes = setOf("wall", "video")
            }
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
                .padding(horizontal = 64.dp)
                .clickable(onClick = onNavigate),
            text = stringResource(R.string.screen_auth_button),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview
@Composable
fun AuthScreenPreview() {
    AuthScreen(
        snackbar = SnackbarHostState(),
        uiState = AuthViewState(),
        uiEvent = {  },
        onNavigate = {  }
    )
}