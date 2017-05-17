package com.flzc.fanglian.ui.bidding_activity;

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

public class BiddingAgreementActivity extends BaseActivity implements OnClickListener{

	private RelativeLayout rl_back;
	private TextView tv_title;
	private WebView mWebView;
	private TextView agreement_next;
	
	private String activityPoolId;
	private String activityId;
	private String buildingId;
	public static BiddingAgreementActivity biddingAgreementActivity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agreement_bidding);
		biddingAgreementActivity = this;
		initView();
	}
	
	private void initView(){
		Intent intent = getIntent();
		if(null != intent){
			activityPoolId = intent.getStringExtra("activityPoolId");
			activityId = intent.getStringExtra("activityId");
			buildingId = intent.getStringExtra("buildingId");
		}
		
		rl_back = (RelativeLayout) findViewById(R.id.rl_back);
		rl_back.setOnClickListener(this);
		
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("竞拍协议");
		
		agreement_next = (TextView) findViewById(R.id.agreement_next);
		agreement_next.setOnClickListener(this);
		
		mWebView = (WebView) findViewById(R.id.wv_webview);
		mWebView.setWebViewClient(webClient);
		mWebView.getSettings().setSupportZoom(false);
		mWebView.getSettings().setBuiltInZoomControls(false);
		mWebView.getSettings().setLayoutAlgorithm(
				WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
		mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setDomStorageEnabled(true);
		mWebView.setWebChromeClient(new WebChromeClientPb());
		mWebView.loadUrl(getUrl());
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
//			boolean et = processCustomScheme(url);
//			if (!et) {
//				return super.shouldOverrideUrlLoading(view, url);
//			}
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
	
	private String getUrl(){
		String url = API.SERVER + "/rule/gotoRulePage?basePage=auctionDeal";
		return url;
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_back://返回
			finish();
			break;
		case R.id.agreement_next:
			Intent intent = new Intent(BiddingAgreementActivity.this,AuctionBailActivity.class);
			intent.putExtra("activityPoolId", activityPoolId);
			intent.putExtra("activityId", activityId);
			intent.putExtra("buildingId", buildingId);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onDestroy() {
		mWebView.removeAllViews();
		mWebView.destroy();
		super.onDestroy();
	}
}
