package com.elpablo.fscslutsky.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.elpablo.fscslutsky.R
import com.elpablo.fscslutsky.core.theme.FSCSlutskyTheme

@Composable
fun FSCSlutskyTitle(
    text: String = "",
    logo: @Composable () -> Unit = {  },
    startButton: @Composable () -> Unit = {  },
    endButton: @Composable () -> Unit = {  }
) {
    Column(
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = dimensionResource(R.dimen.normal_padding))
                .height(dimensionResource(R.dimen.title_height)),
            horizontalArrangement = Arrangement.Center
        ) {
            startButton.invoke()
            Spacer(modifier = Modifier.weight(1f))
            logo.invoke()
            Text(
                text = text,
                modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.small_padding)),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.weight(1f))
            endButton.invoke()
        }
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .align(Alignment.CenterHorizontally),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview(showBackground = true, device = "id:pixel_5")
@Composable
private fun FSCSlutskyTitlePreview() {
    FSCSlutskyTheme {
        FSCSlutskyTitle(text = stringResource(R.string.welcome_title_text))
    }
}