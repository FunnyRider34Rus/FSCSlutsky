package com.elpablo.fscslutsky.ui.profile

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices.PIXEL_6
import androidx.compose.ui.tooling.preview.Preview
import com.elpablo.fscslutsky.ui.profile.components.LoginScreen
import com.elpablo.fscslutsky.ui.profile.components.UserProfileScreen

@Composable
fun ProfileScreen(
    modifier: Modifier,
    uiState: ProfileViewState,
    onEvent: (ProfileEvent) -> Unit
) {
    if (!uiState.isLoggedIn) {
        LoginScreen(
            modifier = modifier,
            onEvent = onEvent
        )
    } else {
        UserProfileScreen(
            modifier = modifier,
            uiState = uiState
        )
    }
}

@Composable
@Preview(device = PIXEL_6)
fun ProfileScreenPreview() {
    ProfileScreen(modifier = Modifier, uiState = ProfileViewState(), onEvent = {  })
}