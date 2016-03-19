package cn.qianlicao.gank.ui.activity

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.*
import cn.qianlicao.gank.R
import com.daimajia.numberprogressbar.NumberProgressBar
import org.jetbrains.anko.toast

class WebViewActivity : BaseActivity() {

    val webview: WebView by lazy { findViewById(R.id.web_content) as  WebView }

    val numberProgress: NumberProgressBar by lazy { findViewById(R.id.number_progress_bar) as NumberProgressBar }

    var currentUrl: String = ""

    override fun myContentViewId() = R.layout.activity_web_view

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        //supportActionBar?.hide()
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true);
        supportActionBar?.setDisplayHomeAsUpEnabled(true); // 决定左上角图标的右侧是否有向左的小箭头, true
        // 有小箭头，并且图标可以点击
        supportActionBar?.setDisplayShowHomeEnabled(false);

        numberProgress.max = 100

        initWebView()

        webview.loadUrl(intent?.getStringExtra("url"))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add("通过浏览器打开")
        menu?.add("关闭")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        return super.onPrepareOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        toast(item.toString() + "show" + "id = " + item?.itemId)

        when (item?.title) {
            "通过浏览器打开" -> startBrowser()
            "关闭" -> finish()
            else -> backOrOut()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startBrowser() {
        val uri: Uri = Uri.parse(currentUrl);
        val intent1: Intent = Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(intent1);
        } catch (e: Exception) {
            toast("没装浏览器?")
        }

    }

    private fun backOrOut() {
        if (webview.canGoBack())
            webview.goBack()
        else
            finish()
    }

    private fun initWebView() {
        webview.setWebViewClient(MyWebViewClient())
        webview.setWebChromeClient(myWebClient())
    }

    override fun onBackPressed() {
        super.onBackPressed()
        backOrOut()
    }

    inner class MyWebViewClient : WebViewClient() {

        override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest?): WebResourceResponse? {
            return super.shouldInterceptRequest(view, request)
        }

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            currentUrl = url!!
            return super.shouldOverrideUrlLoading(view, url)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            currentUrl = url!!
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            currentUrl = url!!
        }
    }

    inner class myWebClient : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)

            numberProgress.progress = newProgress
            if (newProgress >= 100) {
                numberProgress.visibility = View.GONE
            } else {
                if (!numberProgress.isShown)
                    numberProgress.visibility = View.VISIBLE
                numberProgress.progress = newProgress
            }
        }

        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
            supportActionBar?.title = title
        }
    }


}
