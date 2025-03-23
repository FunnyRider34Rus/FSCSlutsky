package com.elpablo.fscslutsky.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.elpablo.fscslutsky.core.navigation.Screen
import com.elpablo.fscslutsky.core.navigation.SetupNavGraph
import com.elpablo.fscslutsky.core.theme.FSCSlutskyTheme
import com.vk.api.sdk.VK
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition { viewModel.isLoading.value }
        enableEdgeToEdge()
        setContent {
            FSCSlutskyTheme {
                if (!VK.isLoggedIn()) {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    FSCSlutskyApp(startDestination = Screen.WALL.route)
                }
            }
        }
    }
}

@Composable
fun FSCSlutskyApp(startDestination: String) {
    val navController = rememberNavController()
    SetupNavGraph(
        navController = navController,
        startDestination = startDestination,
    )
}