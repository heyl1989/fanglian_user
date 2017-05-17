package com.flzc.fanglian.wxpay;

import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Xml;

import com.flzc.fanglian.http.Constant;
import com.flzc.fanglian.util.LogUtil;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * 微信支付异步类
 * 
 * @author joe
 * 
 */
public class GetPrepayIdTask extends AsyncTask<Void, Void, Map<String, String>> {

	private Context context;
	private ProgressDialog dialog;
	private PayReq req;
	private IWXAPI msgApi;
	private Map<String, String> resultunifiedorder;
	private StringBuffer sb;
	private boolean debugMode = false;
	private int type;

	private String app_id = "wxa67bc518afe91cc3";
	private String app_key = "Zmx6Yy1TRUNURVQ6MTQ2MTE0MjcyNjox";
	// 商户号
	private String mch_id = "1327846601";
	// 订单号
	private String out_trade_no = "";
	private double amount = 1;// 订单金额
	private String body;// 订单描述
	// 回调地址
	private String notify_url;

	public GetPrepayIdTask(Context context) {
		super();
		dialog = new ProgressDialog(context);
		this.context = context;
		msgApi = WXAPIFactory.createWXAPI(context, null);
		msgApi.registerApp(app_id);
		req = new PayReq();
		sb = new StringBuffer();
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	protected void onPreExecute() {
		if (dialog != null) {
			dialog.show();;
		}
	}

	@Override
	protected void onPostExecute(Map<String, String> result) {
		if (dialog != null) {
			dialog.dismiss();
		}
		sb.append("prepay_id\n" + result.get("prepay_id") + "\n\n");

		resultunifiedorder = result;

		genPayReq();
		sendPayReq();
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
	}

	@Override
	protected Map<String, String> doInBackground(Void... params) {

		// 统一下单接口
		String url = String
				.format("https://api.mch.weixin.qq.com/pay/unifiedorder");
		String entity = genProductArgs();
		LogUtil.e("WXentity", entity);

		byte[] buf = Util.httpPost(url, entity);

		String content = new String(buf);
		Map<String, String> xml = decodeXml(content);

		return xml;
	}

	/**
	 * 设置微信支付的请求参数
	 */
	private void genPayReq() {

		req.appId = app_id;
		req.partnerId = mch_id;
		req.prepayId = resultunifiedorder.get("prepay_id");
		req.packageValue = "Sign=WXPay";
		req.nonceStr = genNonceStr();
		req.timeStamp = String.valueOf(genTimeStamp());

		List<NameValuePair> signParams = new LinkedList<NameValuePair>();
		signParams.add(new BasicNameValuePair("appid", req.appId));
		signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
		signParams.add(new BasicNameValuePair("package", req.packageValue));
		signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
		signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
		signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

		req.sign = genAppSign(signParams);

		sb.append("sign\n" + req.sign + "\n\n");

	}

	/**
	 * 发送支付请求
	 */
	private void sendPayReq() {
		msgApi.registerApp(app_id);
		msgApi.sendReq(req);

	}

	/**
	 * 生成签名
	 * 
	 * @param params
	 * @return
	 */
	private String genPackageSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(app_key);

		String packageSign = MD5.getMessageDigest(sb.toString().getBytes())
				.toUpperCase();
		return packageSign;
	}

	/**
	 * 
	 * @param params
	 * @return
	 */
	private String genAppSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(app_key);

		this.sb.append("sign str\n" + sb.toString() + "\n\n");
		String appSign = MD5.getMessageDigest(sb.toString().getBytes())
				.toUpperCase();
		return appSign;
	}

	private String toXml(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		for (int i = 0; i < params.size(); i++) {
			sb.append("<" + params.get(i).getName() + ">");

			sb.append(params.get(i).getValue());
			sb.append("</" + params.get(i).getName() + ">");
		}
		sb.append("</xml>");

		return sb.toString();
	}

	public Map<String, String> decodeXml(String content) {

		try {
			Map<String, String> xml = new HashMap<String, String>();
			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(new StringReader(content));
			int event = parser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {

				String nodeName = parser.getName();
				switch (event) {
				case XmlPullParser.START_DOCUMENT:

					break;
				case XmlPullParser.START_TAG:

					if ("xml".equals(nodeName) == false) {
						// 实例化student对象
						xml.put(nodeName, parser.nextText());
					}
					break;
				case XmlPullParser.END_TAG:
					break;
				}
				event = parser.next();
			}

			return xml;
		} catch (Exception e) {
		}
		return null;

	}

	private String genNonceStr() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000))
				.getBytes());
	}

	private long genTimeStamp() {
		return System.currentTimeMillis() / 1000;
	}

	/**
	 * 支付交易编号
	 * 
	 * @return
	 */
	private String genOutTradNo() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000))
				.getBytes());
	}

	/**
	 * 生成订单的商品信息
	 * 
	 * @return
	 */
	private String genProductArgs() {
		StringBuffer xml = new StringBuffer();

		try {
			String nonceStr = genNonceStr();

			xml.append("</xml>");
			List<NameValuePair> packageParams = new LinkedList<NameValuePair>();

			// 公众账号ID
			packageParams.add(new BasicNameValuePair("appid", app_id));
			// 商品描述
			packageParams.add(new BasicNameValuePair("body", "房链活动保证金"));
			// packageParams.add(new BasicNameValuePair("body", "weixin"));
			// 设置支付的商户号
			packageParams.add(new BasicNameValuePair("mch_id", mch_id));
			// 随机字符串
			packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
			// 通知地址(接收微信支付异步通知回调地址)
			packageParams.add(new BasicNameValuePair("notify_url", Constant.WX_CALLBACK));
			// 商户订单号
			packageParams.add(new BasicNameValuePair("out_trade_no",out_trade_no));
			// 终端IP
			packageParams.add(new BasicNameValuePair("spbill_create_ip",
					"127.0.0.1"));
			String total_fee = "0";
			if (debugMode) {
				total_fee = 1 + ""; // 测试时支付一分
			} else {
				/**
				 * 获取支付金额的整数值（元及以上单位）
				 */
				int yuanAmount = (int) amount;

				int fenAmount = (int) ((amount - yuanAmount) * 100);
				total_fee = (yuanAmount * 100 + fenAmount) + "";
			}
			// 交易金额 交易金额默认为人民币交易，接口中参数支付金额单位为【分】
			packageParams.add(new BasicNameValuePair("total_fee", total_fee));
			// 支付类型（通过App支付）
			packageParams.add(new BasicNameValuePair("trade_type", "APP"));
			//禁止信用卡
			//packageParams.add(new BasicNameValuePair("limit_pay","no_credit"));
			String sign = genPackageSign(packageParams);
			// 签名
			packageParams.add(new BasicNameValuePair("sign", sign));

			String xmlstring = toXml(packageParams);

			return xmlstring;

		} catch (Exception e) {
			return null;
		}

	}
}
