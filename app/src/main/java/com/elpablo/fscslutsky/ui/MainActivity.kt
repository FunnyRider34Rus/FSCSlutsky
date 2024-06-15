package com.elpablo.fscslutsky.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.elpablo.fscslutsky.core.navigation.SetupNavGraph
import com.elpablo.fscslutsky.core.theme.FSCSlutskyTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition { viewModel.isLoading.value }
        enableEdgeToEdge()
        setContent {
            FSCSlutskyTheme {
                FSCSlutskyApp(startDestination = viewModel.startDestination.value)
            }
        }
    }
}

@Composable
fun FSCSlutskyApp(startDestination: String) {
    val navController = rememberNavController()
    SetupNavGraph(
        navController = navController,
        startDestination = startDestination
    )
}