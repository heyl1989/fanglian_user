package com.flzc.fanglian.lianlianpay;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.flzc.fanglian.db.UserInfoData;
import com.flzc.fanglian.http.Constant;
import com.flzc.fanglian.util.DateUtils;

public class LianLianUtils {

	// 生成订单
	public static PayOrder constructGesturePayOrder(String orderNo,String needPay) {
		SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String timeString = dataFormat.format(date);

		// 订单信息均为测试数据 需替换为真实数据
		PayOrder order = new PayOrder();
		order.setBusi_partner("101001");
		order.setNo_order(orderNo);
		order.setDt_order(timeString);
		order.setName_goods("房链活动保证金");
		//回调地址
		order.setNotify_url(Constant.LL_CALLBACK);
		// TODO MD5 签名方式
		// order.setSign_type(PayOrder.SIGN_TYPE_MD5);
		// TODO RSA 签名方式
		order.setSign_type(PayOrder.SIGN_TYPE_RSA);
		order.setValid_order("100");

		// 用户 id 身份证 姓名 支付金额
//		 order.setUser_id("330");
//		 order.setId_no("430421199308184972");
//		 order.setAcct_name("王大锤");
		order.setMoney_order(needPay);

		// 身份证 和姓名信息是否可修改

		// int id = ((RadioGroup) findViewById(R.id.flag_modify_group))
		// .getCheckedRadioButtonId();
		// if (id == R.id.flag_modify_0) {
		order.setFlag_modify("0");
		// } else if (id == R.id.flag_modify_1) {
		// order.setFlag_modify("1");
		// }
		// 风险控制参数
		order.setRisk_item(constructRiskItem());

		String sign = "";

		// 是否预授权
		// if (is_preauth) {
		// order.setOid_partner(EnvConstants.PARTNER_PREAUTH);
		// } else {
		order.setOid_partner(EnvConstants.PARTNER);
		// }
		String content = BaseHelper.sortParam(order);
		// TODO MD5 签名方式, 签名方式包括两种，一种是MD5，一种是RSA 这个在商户站管理里有对验签方式和签名Key的配置。
		// if (is_preauth) {
		// sign = Md5Algorithm.getInstance().sign(content,
		// EnvConstants.MD5_KEY_PREAUTH);
		// } else {
		// sign = Md5Algorithm.getInstance().sign(content,
		// EnvConstants.MD5_KEY);
		// }
		// RSA 签名方式
		sign = Rsa.sign(content, EnvConstants.RSA_PRIVATE);
		System.out.println("//////////sign/////////////" + sign);
		order.setSign(sign);
		return order;
	}

	//风险控制参数
	public static String constructRiskItem() {
		JSONObject mRiskItem = new JSONObject();
		try {
			mRiskItem.put("user_info_bind_phone", UserInfoData.getData(Constant.PHONE, "")+"5");
			mRiskItem.put("user_info_dt_register", DateUtils.getTime("yyyyMMddHHmmss", System.currentTimeMillis()));
			mRiskItem.put("frms_ware_category", "1005");
			mRiskItem.put("request_imei", "1122111221");
			mRiskItem.put("user_info_mercht_userno", "330");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return mRiskItem.toString();
	}
}
