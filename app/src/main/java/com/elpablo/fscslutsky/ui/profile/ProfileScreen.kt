package com.elpablo.fscslutsky.ui.profile

import android.content.Intent
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.elpablo.fscslutsky.R
import com.elpablo.fscslutsky.core.components.FSCSlutskyAlertDialog
import com.elpablo.fscslutsky.core.components.FSCSlutskyLoader
import com.elpablo.fscslutsky.ui.MainActivity
import com.elpablo.fscslutsky.ui.profile.components.AboutAppScreen
import com.elpablo.fscslutsky.ui.profile.components.LicensingScreen
import com.elpablo.fscslutsky.ui.profile.components.UserProfileScreen

@Composable
fun ProfileScreen(
    snackbar: SnackbarHostState,
    uiState: ProfileViewState,
    uiEvent: (ProfileEvent) -> Unit,
) {
    val activity = LocalActivity.current
    val context = LocalContext.current

    if (uiState.isLoading) {
        FSCSlutskyLoader()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiary.copy(alpha = 0.3f)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.background)
        ) {
            if (uiState.user != null) {
                UserProfileScreen(
                    modifier = Modifier.padding(16.dp),
                    uiState = uiState,
                    onEvent = uiEvent
                )
            }
            LicensingScreen(
                modifier = Modifier.padding(16.dp),
                onEvent = uiEvent
            )
            AboutAppScreen(
                modifier = Modifier.padding(16.dp),
                onEvent = uiEvent
            )
        }
    }

    if (uiState.isError) {
        LaunchedEffect(null) {
            uiState.error.let { msg -> snackbar.showSnackbar(msg) }
        }
    }
    if (uiState.isLogout) {
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
        activity?.finish()
    }
    if (uiState.isLicensing) {
        FSCSlutskyAlertDialog(
            onConfirmation = {
                uiEvent(ProfileEvent.hideLicensing)

            },
            title = stringResource(id = R.string.screen_profile_licensing_title),
            buttonText = stringResource(id = R.string.screen_profile_licensing_button),
            dialogText = stringResource(id = R.string.screen_profile_licensing_text)
        )
    }
    if (uiState.isAboutApp) {
        FSCSlutskyAlertDialog(
            onConfirmation = {
                uiEvent(ProfileEvent.hideAboutApp)
            },
            onDismissRequest = {
                uiEvent(ProfileEvent.hideAboutApp)
            },
            title = stringResource(id = R.string.screen_profile_about_app_title),
            dialogText = stringResource(id = R.string.screen_profile_about_app_text)
        )
    }
}
