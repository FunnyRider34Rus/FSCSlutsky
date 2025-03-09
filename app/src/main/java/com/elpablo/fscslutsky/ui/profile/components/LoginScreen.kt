package com.elpablo.fscslutsky.ui.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.elpablo.fscslutsky.BuildConfig
import com.elpablo.fscslutsky.R
import com.elpablo.fscslutsky.ui.profile.ProfileEvent
import com.vk.id.onetap.compose.onetap.sheet.OneTapBottomSheet
import com.vk.id.onetap.compose.onetap.sheet.OneTapScenario
import com.vk.id.onetap.compose.onetap.sheet.rememberOneTapBottomSheetState
import com.vk.id.onetap.compose.onetap.sheet.style.OneTapBottomSheetStyle

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onEvent: (ProfileEvent) -> Unit
) {
    val bottomSheetState = rememberOneTapBottomSheetState()
    OneTapBottomSheet(
        modifier = Modifier,
        state = bottomSheetState,
        onAuth = { oAuth, token ->
            onEvent(ProfileEvent.AuthSuccess(token.token))
        },
        onFail = { oAuth, error ->
            onEvent(ProfileEvent.AuthFail(error.description))
        },
        serviceName = BuildConfig.APPLICATION_ID,
        scenario = OneTapScenario.EnterService,
        style = OneTapBottomSheetStyle.Light()
    )
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            bottomSheetState.show()
        }) {
            Row {
                Text(text = "Войти с ")
            }
            Image(
                modifier = Modifier
                    .size(32.dp)
                    .padding(start = 4.dp),
                painter = painterResource(R.drawable.vk_logo),
                contentDescription = null
            )
        }
    }
}