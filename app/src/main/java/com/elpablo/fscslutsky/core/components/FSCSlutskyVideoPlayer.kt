package com.elpablo.fscslutsky.core.components

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun FSCSlutskyVideoPlayer(modifier: Modifier = Modifier, videoURI: String) {
    AndroidView(
        modifier = modifier,
        factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = false
                settings.mediaPlaybackRequiresUserGesture = true
                loadUrl(videoURI)
            }
        },
        update = { webView ->
            webView.loadUrl(videoURI)
        }
    )
}
