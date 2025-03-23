package com.elpablo.fscslutsky.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elpablo.fscslutsky.core.theme.FSCSlutskyTheme
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope

class LoginActivity : ComponentActivity() {

    val authLauncher = VK.login(this) { result : VKAuthenticationResult ->
        when (result) {
            is VKAuthenticationResult.Success -> {
                Log.d("VKID", "Success ${result.token.accessToken}")
            }
            is VKAuthenticationResult.Failed -> {
                Log.d("VKID", "Failed ${result.exception}")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FSCSlutskyTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { paddingValues ->
                    Box(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize()
                            .padding(32.dp),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        Row {
                            Button(onClick = {
                                authLauncher.launch(listOf(VKScope.WALL, VKScope.VIDEO))
                            }) {
                                Text(text = "Войти с VK ID")
                            }
                        }
                    }
                }
            }
        }
    }
}