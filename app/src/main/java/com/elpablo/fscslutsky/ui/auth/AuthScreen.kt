package com.elpablo.fscslutsky.ui.auth

import android.util.Log
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elpablo.fscslutsky.core.components.FSCSlutskyAlertDialog
import com.elpablo.fscslutsky.core.components.FSCSlutskyLoader
import com.vk.id.VKID
import com.vk.id.auth.VKIDAuthUiParams
import com.vk.id.onetap.common.OneTapStyle
import com.vk.id.onetap.common.button.style.OneTapButtonCornersStyle
import com.vk.id.onetap.common.button.style.OneTapButtonSizeStyle
import com.vk.id.onetap.compose.onetap.OneTap

@Composable
fun AuthScreen(
    modifier: Modifier,
    uiState: AuthViewState,
    onEvent: (AuthEvent) -> Unit,
    onNavigate: () -> Unit
) {
    val activity = LocalActivity.current
    if (uiState.isLoggedIn) {
        onNavigate.invoke()
    }
    if (uiState.isLoading) FSCSlutskyLoader()
    if (uiState.isError) {
        FSCSlutskyAlertDialog(
            onDismissRequest = {
                activity?.finish()
            },
            onConfirmation = {
                activity?.finish()
            },
            dialogText = uiState.error
        )
    }
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        OneTap(
            modifier = Modifier.padding(bottom = 32.dp).padding(horizontal = 32.dp),
            onAuth = { oAuth, token ->
                onEvent(AuthEvent.AuthSuccess)
                Log.d("VKID", "AuthSuccess_token: ${VKID.instance.accessToken?.token}")
                Log.d("VKID", "AuthSuccess_scope: ${VKID.instance.accessToken?.scopes}")
            },
            onFail = { oAuth, error ->
                onEvent(AuthEvent.AuthFail(error.description))
                Log.d("VKID", "AuthFailed")
            },
            signInAnotherAccountButtonEnabled = true,
            style = OneTapStyle.Light(
                cornersStyle = OneTapButtonCornersStyle.Custom(16f),
                sizeStyle = OneTapButtonSizeStyle.DEFAULT
            ),
            authParams = VKIDAuthUiParams {
                scopes = setOf("video", "wall", "photos", "phone")
            }
        )
    }
}