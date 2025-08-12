package com.elpablo.fscslutsky.ui.profile

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.elpablo.fscslutsky.core.components.FSCSlutskyAlertDialog
import com.elpablo.fscslutsky.core.components.FSCSlutskyLoader
import com.elpablo.fscslutsky.ui.profile.components.UserProfileScreen

@Composable
fun ProfileScreen(
    uiState: ProfileViewState
) {
    val activity = LocalActivity.current
    if (uiState.isLoading) {
        FSCSlutskyLoader()
    }
    if (uiState.user != null) {
        UserProfileScreen(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.tertiary.copy(alpha = 0.3f)),
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
