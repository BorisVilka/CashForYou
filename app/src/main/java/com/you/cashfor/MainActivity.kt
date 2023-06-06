package com.you.cashfor

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Base64
import android.util.Log
import android.view.View
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import com.you.cashfor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    fun decoderBase64(string: String): String {
        val decode = Base64.decode(string, Base64.DEFAULT)
        return String(decode)
    }

    var linkBuilderOffer = "aHR0cHM6Ly9mYXN0Y2FzaGZvcnlvdS5ydS8="

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            checkSource()
        },5000)

    }

    private fun checkSource(){

        linkBuilderOffer = decoderBase64(linkBuilderOffer)
        when {

            af_status == "Non-organic" -> {
                linkBuilderOffer += "?subid1=${sub1}&subid2=${sub2}&subid3=${sub3}"
                loadWebView()
            }

            af_status == "Organic" -> {
                linkBuilderOffer += "?subid1=$af_status"
                loadWebView()
            }

            else -> {
                linkBuilderOffer += "?subid1=$af_status"
                loadWebView()
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed(){
        if ( binding.multy.canGoBack())  binding.multy.goBack() else super.onBackPressed()
    }

    private fun loadWebView(){
        createWebView()
        binding.multy.visibility = View.VISIBLE

        Log.e("onPageFinished", linkBuilderOffer.toString())
        //запуск собранной ссылки с парамтерами.
        binding.multy.loadUrl(linkBuilderOffer)
    }

    private fun createWebView(){
        binding.multy.settings.apply {
            defaultTextEncodingName = "utf-8"
            allowFileAccess = true
            javaScriptEnabled = true
            loadWithOverviewMode = true
            domStorageEnabled = true
            databaseEnabled = true
            useWideViewPort = true

            javaScriptCanOpenWindowsAutomatically = true
            mixedContentMode = 0
        }

        binding.multy.webViewClient = object : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
            }
        }
        binding.multy.webChromeClient = object : WebChromeClient(){}
    }
}