package com.flzc.fanglian.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flzc.fanglian.R;
import com.flzc.fanglian.base.BaseActivity;
import com.flzc.fanglian.http.API;

public class UserRegisterDealWebViewActivity extends BaseActivity implements
		OnClickListener {

	private RelativeLayout rl_back;
	private WebView webview;
	private TextView tv_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userregisterdeal_webview);
		init();
	}

	private void init() {
		// 标题栏
		rl_back = (RelativeLayout) findViewById(R.id.rl_back);
		rl_back.setOnClickListener(this);
		tv_title = (TextView)findViewById(R.id.tv_title);
		tv_title.setText("房链用户使用协议条款");
		webview = (WebView) findViewById(R.id.wb_rules);
		webview.setWebViewClient(webClient);
		webview.getSettings().setSupportZoom(false);
		webview.getSettings().setBuiltInZoomControls(false);
		webview.getSettings().setLayoutAlgorithm(
				WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
		webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setDomStorageEnabled(true);
		webview.setWebChromeClient(new WebChromeClientPb());
		webview.loadUrl(API.USERREGISTERDEAL);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_back:
			finish();
			break;

		default:
			break;
		}

	}
	
	private class WebChromeClientPb extends WebChromeClient {
		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			loadingDialog.showDialog();
			if (newProgress == 100) {
				loadingDialog.dismissDialog();
			}
			super.onProgressChanged(view, newProgress);
		}
	}

	private WebViewClient webClient = new WebViewClient() {

		// 在点击请求的是链接是才会调用，重写此方法返回true表明
		// 点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边。
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			/*
			 * if (!processCustomScheme(url)) { view.loadUrl(url); } return
			 * true;
			 */

			boolean et = processCustomScheme(url);
			if (!et) {
				return super.shouldOverrideUrlLoading(view, url);
			}
			return true;

		}

		private boolean processCustomScheme(String url) {
			return false;
		}

		// 在页面加载结束时调用。
		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			
		}

	};

	@Override
	protected void onDestroy() {
		webview.removeAllViews();
		webview.destroy();
		super.onDestroy();
	}


}
