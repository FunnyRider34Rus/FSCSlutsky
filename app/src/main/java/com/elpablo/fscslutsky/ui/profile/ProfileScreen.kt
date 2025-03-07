package com.elpablo.fscslutsky.ui.profile

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
import androidx.compose.ui.tooling.preview.Devices.PIXEL_6
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elpablo.fscslutsky.BuildConfig
import com.elpablo.fscslutsky.R
import com.vk.id.onetap.compose.onetap.sheet.OneTapBottomSheet
import com.vk.id.onetap.compose.onetap.sheet.OneTapScenario
import com.vk.id.onetap.compose.onetap.sheet.rememberOneTapBottomSheetState
import com.vk.id.onetap.compose.onetap.sheet.style.OneTapBottomSheetStyle

@Composable
fun ProfileScreen(modifier: Modifier) {
    val bottomSheetState = rememberOneTapBottomSheetState()
    OneTapBottomSheet(
        modifier = Modifier,
        state = bottomSheetState,
        onAuth = { oAuth, token ->

        },
        onFail = { oAuth, error ->

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

@Composable
@Preview(device = PIXEL_6)
fun ProfileScreenPreview() {
    ProfileScreen(modifier = Modifier)
}