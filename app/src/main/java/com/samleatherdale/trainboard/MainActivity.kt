package com.samleatherdale.trainboard

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.WindowManager.LayoutParams
import android.webkit.WebView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentActivity

class MainActivity : FragmentActivity() {
    private lateinit var webView: WebView
    private var isReady = false

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.addFlags(LayoutParams.FLAG_KEEP_SCREEN_ON)

        webView = WebView(this@MainActivity)
        webView.settings.domStorageEnabled = true
        webView.settings.javaScriptEnabled = true
        webView.addJavascriptInterface(JsInterface(
            webView = webView,
            setIsReady = {
                isReady = true
            },
            onExit = {
                this@MainActivity.finish()
            }
        ), "android")

        val headers = mutableMapOf<String, String>()
        if (BuildConfig.DEBUG_WEBVIEW) {
            WebView.setWebContentsDebuggingEnabled(true)
            webView.settings.userAgentString = "ngrok tunnel client"
            headers["ngrok-skip-browser-warning"] = "true"
        }
        webView.loadUrl(BuildConfig.APP_URL, headers)
        setContentView(webView)

        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (webView.canGoBack()) {
                    webView.goBack()
                } else {
                    this@MainActivity.finish()
                }
            }
        })
    }

}