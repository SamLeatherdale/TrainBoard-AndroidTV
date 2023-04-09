package com.samleatherdale.trainboard

import android.webkit.JavascriptInterface
import android.webkit.WebView

class JsInterface(
    val webView: WebView,
    val setIsReady: () -> Unit,
    val onExit: () -> Unit,
) {
    @JavascriptInterface
    fun ready() {
        setIsReady()
    }

    @JavascriptInterface
    fun reload() {
        webView.post {
            webView.reload()
        }
    }

    @JavascriptInterface
    fun exit() {
        onExit()
    }
}