package com.flzc.fanglian.http;

public class Constant {
	// 超时时间
	public static final int TIME_OUT = 60 * 1000;
	// 用户是否第一次进入app
	public static final String IS_FIRST_OPEN = "isfirst";
	public static final String LOC_CITY_ID = "locatecityID";
	public static final String LOC_CITY_NAME = "locatecityName";
	// 用户信息
	public static final String TOKEN = "token";
	public static final String NICK_NAME = "nickName";
	public static final String MY_INVITE_CODE = "myInviteCode";
	public static final String DESCRIPTOR = "com.umeng.share";
	public static final String HEAD_URL = "headUrl";
	public static final String PHONE = "phone";
	// 临时参数
	public static final String HS_ID = "houseSourceId";
	public static final String ATY_ID = "activityPoolId";
	public static final String BD_ID = "buildingId";
	public static final String A_PRICE = "activityPrice";
	public static final String A_ID = "activityId";
	public static final String ST = "startTime";
	public static final String ET = "endTime";
	// 阿里支付宝
	// 商户PID
	public static final String PARTNER = "2088221221997647";
	// 商户收款账号
	public static final String SELLER = "flzc@fanglianzhongchou.com";
	// 商户私钥，pkcs8格式
	public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMydKAIPk4kofszXX4s7pRjjxeA2RmKIVCQCZdZs14hLJQEajN/WC1uBq/HqZp5SXFT6t4bbVDTuZuhRAIDRNDJ1hKodAitMSy49TvB4tsmIA5toej2niHDoL/NgQLgRE4wafdPlIO9Bq1tcjTvD2QZEQjJ8hpptgdOelpKhCkzfAgMBAAECgYB+8r6A0NMzlOlfA/0mcIIVODRr0KDIv1ac5NP0PqFW0XCLB8MBmVaAlqpstIaokebxXoCBbhEHqiY02xTZ13JvBBQzhgifRcNEhB4rxFLgtFaEK2hYJh8eC0aMuX3vNXAAOROnhPW78Sl6XVPPFCRKRk9EGx6iCxOxh+L9xlvqyQJBAOYBAe0Xcm+8vQTtEXQmeNKBpkT1oCHHZYvFm+RCa7ixMzPyp4Zm9agnjBBmYvKgiCyrDjIgxtUCOmQyzWIUzZUCQQDjvYG638PlXG6EUES1AQGn1NjTh+rNbPRaZAIMRHS7QsmqwG7yKEfj/LUu3KJIL9IFEhFX+TbD4rMM6sILngujAkBOnCOLZAshs3ErANeJSijbUaKBSS38vYiwV86rGiR1EGM+qv2kywXE/hc597Rz1ngURDZq3Zb3cHaCjnZxwkhtAkEAujCY+3st4TqscChfw8vK2re9fVNFuCU3sR7KPdVJyj9UOPGSwxADlnVoe6OOWY4fGl4nJ9Fr4MJauFjFxFG2GQJBAJIBiZikS8DiClnogwJj4cQ8UVGQCCINKJ7Me0OJOXo83wvXf9XGWj9l851OmhXJg174sWd0ykxTShxZuOselF0=";
	// 支付宝公钥
	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
	// 微信的支付
	// APP_ID 替换为你的应用从官方网站申请到的合法appId
	public static final String APP_ID = "wxa67bc518afe91cc3";
	// 微信的appSecret
	public static final String APP_SECRET = "d4218cffa9672cceb9a2bff8baf6d029";
	
	//微信支付回调地址
	public static final String WX_CALLBACK =  API.PAY_SERVER +"/com.flzc.pay/weixinpay_notify/reciveNotify";
	// 支付宝的回调地址
	public static final String ZFB_CALLBACK = API.PAY_SERVER + "/com.flzc.pay/alipay_notify/reciveNotify";
	// 连连支付的回调地址
	public static final String LL_CALLBACK = API.PAY_SERVER + "/com.flzc.pay/llpay_notify/reciveNotify";
	
}
