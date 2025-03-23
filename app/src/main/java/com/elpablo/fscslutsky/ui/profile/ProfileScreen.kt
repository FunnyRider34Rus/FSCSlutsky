package com.elpablo.fscslutsky.ui.profile

import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.elpablo.fscslutsky.core.components.FSCSlutskyAlertDialog
import com.elpablo.fscslutsky.core.components.FSCSlutskyLoader
import com.elpablo.fscslutsky.ui.profile.components.UserProfileScreen

@Composable
fun ProfileScreen(
    modifier: Modifier,
    uiState: ProfileViewState
) {
    val activity = LocalActivity.current
    if (uiState.isLoading) {
        FSCSlutskyLoader()
    }
    if (uiState.user != null) {
        UserProfileScreen(
            modifier = modifier,
            uiState = uiState
        )
    }
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
}
