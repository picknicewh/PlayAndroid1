package com.mayi.yun.playandroid.ui.knowledgesystem.detail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mayi.yun.playandroid.R;
import com.mayi.yun.playandroid.base.BaseActivity;
import com.mayi.yun.playandroid.constant.Constant;
import com.mayi.yun.playandroid.widget.ProgressWebView;

import butterknife.BindView;

/**
 * 作者： wh
 * 时间：  2018/3/2
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class ArticleContentActivity extends BaseActivity {
    /**
     * 内容
     */
    @BindView(R.id.webview)
    ProgressWebView mWebView;

    /**
     * 链接
     */
    private String link;
    /**
     * 标题
     */
    private String title;

    @Override
    public void initInjector() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_article_content;
    }

    @Override
    public void initView() {
        link = getIntent().getStringExtra("link");
        title = getIntent().getStringExtra("title");
        setToolbarTitle( Html.fromHtml(title).toString());
        initWebView(link);

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView(final String link) {
        WebSettings webSettings = mWebView.getSettings();
        String cacheDirPath = getFilesDir().getAbsolutePath() + Constant.APP_CACAHE_DIRNAME; //缓存路径
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //缓存模式
        webSettings.setAppCachePath(cacheDirPath); //设置缓存路径
        webSettings.setAppCacheEnabled(true); //开启缓存功能
        mWebView.loadUrl(link);  //加载URL
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

    }

    @Override
    protected boolean showHomeAsUp() {
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_content, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuShare) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_article_url, getString(R.string.app_name), title, link));
            intent.setType("text/plain");
            startActivity(intent);
        } else if (item.getItemId() == R.id.menuLike) {
        } else if (item.getItemId() == R.id.menuBrowser) {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse(link));
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        }
        super.onBackPressed();
    }
}
