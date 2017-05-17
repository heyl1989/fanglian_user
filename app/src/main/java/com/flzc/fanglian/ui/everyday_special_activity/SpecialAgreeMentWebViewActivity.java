package com.flzc.fanglian.ui.everyday_special_activity;

import android.content.Intent;
import android.net.Uri;
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

public class SpecialAgreeMentWebViewActivity extends BaseActivity implements
		OnClickListener {

	private RelativeLayout rl_back;
	private WebView webview;
	private TextView agreement_next;
	private String activityPoolId;
	private String houseSourceId;
	private String buildingId;
	private String activityId;
	public static SpecialAgreeMentWebViewActivity specialAgreeMentWebViewActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agreement_bidding);
		specialAgreeMentWebViewActivity = this;
		Intent intent = getIntent();
		if(null != intent){
			houseSourceId = intent.getStringExtra("houseSourceId");
			buildingId = intent.getStringExtra("buildingId");
			activityPoolId = intent.getStringExtra("activityPoolId");
			activityId = intent.getStringExtra("activityId");
		}
		
		init();
	}

	private void init() {
		// 标题栏
		rl_back = (RelativeLayout) findViewById(R.id.rl_back);
		rl_back.setOnClickListener(this);
		TextView tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("天天特价协议");
		
		agreement_next = (TextView) findViewById(R.id.agreement_next);
		agreement_next.setOnClickListener(this);
		
		webview = (WebView) findViewById(R.id.wv_webview);
		webview.setWebViewClient(webClient);
		webview.getSettings().setSupportZoom(false);
		webview.getSettings().setBuiltInZoomControls(false);
		webview.getSettings().setLayoutAlgorithm(
				WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
		webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setDomStorageEnabled(true);
		webview.setWebChromeClient(new WebChromeClientPb());
		webview.loadUrl(API.DAYSPECALDEAL);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_back:
			finish();
			break;
		case R.id.agreement_next:
			Intent agreementIntent = new Intent(this,
					ScaleBuyingCashActivity.class);
			agreementIntent.putExtra("houseSourceId", houseSourceId + "");
			agreementIntent.putExtra("buildingId", buildingId + "");
			agreementIntent.putExtra("activityPoolId", activityPoolId + "");
			agreementIntent.putExtra("activityId", activityId);
			startActivity(agreementIntent);
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
			if(url.startsWith("tel:")){
				 Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(url));
                startActivity(intent);
			}else if(url.startsWith("http:") || url.startsWith("https:")){
				view.loadUrl(url);
			}

			/*boolean et = processCustomScheme(url);
			if (!et) {
				return super.shouldOverrideUrlLoading(view, url);
			}*/
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
