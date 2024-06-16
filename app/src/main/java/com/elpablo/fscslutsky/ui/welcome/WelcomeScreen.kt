package com.elpablo.fscslutsky.ui.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elpablo.fscslutsky.R
import com.elpablo.fscslutsky.core.components.FSCSlutskyTitle
import com.elpablo.fscslutsky.core.theme.FSCSlutskyTheme

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    navigateToNextScreen: () -> Unit
) {

    var phone by rememberSaveable { mutableStateOf("") }

    FSCSlutskyTitle(
        text = stringResource(R.string.welcome_title_text),
        logo = {
            Image(
                painter = painterResource(R.drawable.original),
                contentDescription = null,
                modifier = Modifier.fillMaxHeight(),
                contentScale = ContentScale.Fit
            )
        }
    )
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(R.dimen.huge_padding)),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.welcome_edittext_label),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.padding(vertical = 16.dp))
        TextField(
            value = phone,
            onValueChange = {
                if (it.length <= 10) {
                    phone = it
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(shape = RoundedCornerShape(16.dp)),
            prefix = { Text(text = stringResource(R.string.welcome_countrycode_text)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.tertiary,
                unfocusedContainerColor = MaterialTheme.colorScheme.tertiary,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
        Spacer(modifier = Modifier.padding(vertical = 16.dp))
        Button(
            onClick = navigateToNextScreen,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = stringResource(R.string.welcome_button_text),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview(showBackground = true, device = "id:pixel_5")
@Composable
private fun WelcomeScreenPreview() {
    FSCSlutskyTheme {
        WelcomeScreen { }
    }
}