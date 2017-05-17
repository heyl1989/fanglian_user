package com.flzc.fanglian.http;

public class API {
	/**
	 * API服务
	 * 支付服务
	 */
	/**正式*/
//	public static final String SERVER = "http://user.api.18fanglian.com/api";//正式
//	public static final String PAY_SERVER = "http://pay.18fanglian.com";//正式
	
	/**100服务*/
	public static final String SERVER = "http://192.168.10.100:8092/api";//100服务器
	public static final String PAY_SERVER = "http://192.168.10.100:8093";//100服务器
	
	/**106服务*/	
//	public static final String SERVER = "http://112.126.78.106:8092/api";//测试线上
//	public static final String PAY_SERVER = "http://112.126.78.106:8093";//测试线上
	
	/**齐广瑞服务*/	//http://192.168.2.121:8080/com.flzc.user.service
//	public static final String SERVER = "http://192.168.10.19:8998/flzc-user-service-war";
//	public static final String PAY_SERVER = "http://192.168.10.100:8093";
	
	/**陈琦服务*/
//	public static final String SERVER = "http://192.168.10.100:8092/api";
//	public static final String PAY_SERVER = "http://192.168.10.51:10086";
	
	
	//查询购房意向
	public static final String question = SERVER + "/api/user/intention/query";
	//保存购房意向
	public static final String INTENTION_SAVE = SERVER + "/api/user/intention/save";
	//快捷登录发送验证码
	public static final String SENDLOGINCODE = SERVER + "/api/validateCode/sendLoginCode";
	//快捷登录的登录
	public static final String USERLOGINBYVALIDATECODE = SERVER + "/api/user/userLoginByValidateCode";
	
	// 创建订单
	public static final String CREATE_ACTIVITY_ORDER = PAY_SERVER
			+ "/com.flzc.pay/common/order/createUserOrder";
	// 取消订单
	public static final String CANCEL_ORDER = PAY_SERVER
			+ "/com.flzc.pay/common/order/cancelUserOrder";
	//查询订单
	public static final String CHECK_ORDER = PAY_SERVER
			+ "/com.flzc.pay/order/checkPayStatus";
	// 账户余额支付
	public static final String BANLANCEPAY = PAY_SERVER
			+ "/com.flzc.pay/account/banlancePay";
	// 提现
	public static final String ACCOUNT_WITH_DRAW = PAY_SERVER
			+ "/com.flzc.pay/account/withdraw";
	//易宝支付
	public static final String YIBAO_PAY = PAY_SERVER
			+ "/com.flzc.pay/instant/userInstantPay";
	// 用户密码登录
	public static final String USER_LOGIN_BY_PASSWORD = SERVER
			+ "/api/user/userLoginByPassword";
	// 找回密码
	public static final String FINDPASSWORD = SERVER + "/api/user/findPassword";
	// 用户验证码登录
	public static final String USER_LOGIN_BY_VALIDATE_CODE = SERVER
			+ "/api/user/userLoginByValidateCode";
	// 用户注册
	public static final String USER_REGISTER = SERVER
			+ "/api/user/userRegister";
	// 验证注册手机号码
	public static final String CHECK_REGISTER_PHONE = SERVER
			+ "/api/user/checkRegisterPhone";
	// 发送找回密码验证码
	public static final String SENDFINDPASSWORDCODE = SERVER
			+ "/api/validateCode/sendFindPasswordCode";
	// 发送注册验证码
	public static final String SEND_REGISTER_CODE = SERVER
			+ "/api/validateCode/sendRegisterCode";
	// 发送登录验证码
	public static final String SEND_LOGIN_CODE = SERVER
			+ "/api/validateCode/sendLoginCode";
	// 生成图片验证码
	public static final String CREATE_IMAGE_CODE = SERVER
			+ "/api/validateCode/createImageCode";
	// 验证图片验证码
	public static final String VALIDATE_IMAGE_CODE = SERVER
			+ "/api/validateCode/validateImageCode";
	// 热门楼盘
	public static final String HOT_BUILDING = SERVER
			+ "/api/search/hotBuilding";
	// 搜索提示
	public static final String HINT = SERVER + "/api/search/hint";
	// 搜索
	public static final String SEARCH = SERVER + "/api/search/search";
	// 查询开通城市
	public static final String OPENCITY = SERVER + "/api/search/openCity";
	// 查询首页推荐活动
	public static final String QUERY_ACTIVITY_RECOMMEND = SERVER
			+ "/api/home/queryActivityRecommend";
	// 查询首页Banner
	public static final String BANNER = SERVER + "/api/home/banner";
	// 查询首页房链头条
	public static final String HEADLINE = SERVER + "/api/home/headline";
	// 查询首页推荐楼盘
	public static final String SUGGESTED_BUILDING = SERVER + "/api/search/suggestedBuilding";
	// 置业顾问列表
	public static final String QUERY_BUILDER_LIST = SERVER
			+ "/api/activity/queryBuilderList";
	// 查询楼盘活动列表
	public static final String LIST_ACTIVITY = SERVER + "/api/activity/list";
	// 提醒我
	public static final String REMIND = SERVER + "/api/activity/remind";
	// 取消提醒我
	public static final String CANCEL_REMIND = SERVER
			+ "/api/activity/cancelRemind";
	// 查询提醒我
	public static final String QUERY_REMIND = SERVER
			+ "/api/activity/queryRemind";
	// 查询竞拍活动信息
	public static final String QUERY_AUCTION_ACTIVITY = SERVER
			+ "/api/auction/queryAuctionActivity";
	// 查询活动参与用户
	public static final String QUERY_AUCTION_PARTICIPANT = SERVER
			+ "/api/auction/queryAuctionParticipant";
	// 查询活房源信息
	public static final String QUERY_HOUSE_SOURCE = SERVER
			+ "/api/activity/queryHouseSource";
	// 保存用户认证信息
	public static final String SAVE_USER_IDENTITY = SERVER
			+ "/api/activity/saveUserIdentity";
	// 查询用户认证信息
	public static final String QUERY_USER_IDENTITY = SERVER
			+ "/api/activity/queryUserIdentity";
	// 保存用户出价
	public static final String SAVE_USER_BIDDING = SERVER
			+ "/api/auction/saveUserBidding";
	// 查询新房列表筛选条件
	public static final String QUERY_SCREENING_CONDITIONS = SERVER
			+ "/api/building/queryScreeningConditions";
	// 查询新房列表
	public static final String LIST_HOUSE = SERVER + "/api/building/list";
	// 查询楼盘详细信息
	public static final String DETAILS = SERVER + "/api/building/details";
	// 查询楼盘户型列表
	public static final String QUERY_HOUSE_TYPES = SERVER
			+ "/api/building/queryHouseTypes";
	// 查询楼盘图片列表
	public static final String QUERY_BXUILDING_IMGS = SERVER
			+ "/api/building/queryBuildingImgs";
	// 修改昵称
	public static final String UPDATE_NICK_NAME = SERVER
			+ "/api/userCenter/updateNickName";
	// 修改密码
	public static final String UPDATE_PASSWORD = SERVER
			+ "/api/userCenter/updatePassword";
	// 查询用户房链劵
	public static final String USERTICKETLIST = SERVER + "/api/userTicket/list";
	// 查询用户房链劵详细信息
	public static final String USERTICKETDETAILS = SERVER
			+ "/api/userTicket/details";
	// 查询用户信息
	public static final String QUERYUSERBYTOKENID = SERVER
			+ "/api/userCenter/queryUserByTokenId";
	// 查询账户余额
	public static final String QUERYBALANCE = SERVER
			+ "/api/userAccount/queryBalance";
	/**
	 * 1.1.1版本新加接口
	 */
	//查询用户银行卡号
	public static final String QUERYUSERBANKCARD = SERVER
			+ "/api/account/queryUserBankCard";
	//解绑用户银行卡
	public static final String UNBUNDLING_BANKCARD = SERVER
				+ "/api/account/unbundlingBankCard";
	//查询银行卡类型列表
	public static final String QUERYBANKCARD = SERVER
					+ "/api/account/queryBankCard";
	//保存用户银行卡
	public static final String SAVEUSERBANKCARD = SERVER
						+ "/api/account/saveUserBankCard";
	
	// 查询用户参与活动
	public static final String QUERYUSERJOINACTIVITY = SERVER
			+ "/api/userActivity/queryUserJoinActivity";
	// 修改性别
	public static final String UPDATEGENDER = SERVER
			+ "/api/userCenter/updateGender";
	// 查询天天特价活动信息
	public static final String QUERY_DAY_SPECIAL_ACTIVITY = SERVER
			+ "/api/daySpecial/queryDaySpecialActivity";
	// 查询天天特价活动参与用户信息
	public static final String QUERY_DAY_SPECIAL_PARTICIPANT = SERVER
			+ "/api/daySpecial/queryDaySpecialParticipant";
	// 查询秒杀
	public static final String SECKILL = SERVER + "/api/daySpecial/seckill";
	// 查询经纪人推荐活动
	public static final String QUERY_SALES_WAY_ACTIVITY = SERVER
			+ "/api/salesway/querySaleswayActivity";
	//提交预约经纪人时间
	public static final String APPOINTMENT = SERVER
			+ "/api/salesway/appointment/broker";
	// 参加经纪人活动用户
	public static final String QUERY_SALEWAY_PARTICIPANT = SERVER
			+ "/api/salesway/querySalewayParticipant";
	// 保存用户领取的房链劵
	public static final String RECEIVE_COUPON = SERVER
			+ "/api/salesway/receiveCoupon";
	// 查询房源列表
	public static final String QUERY_HOUSE_SOURCE_LIST = SERVER
			+ "/api/salesway/queryHouseSourceList";
	// 缴纳保证金成功的页面
	public static final String PAYSUCCESS = SERVER + "/api/activity/paySuccess";
	// 上传图片
	public static final String UPLOAD_HEAD = SERVER + "/image/upload/user/head";
	// 查询订单列表
	public static final String USER_ORDER_LIST = SERVER + "/api/userOrder/list";
	// 查询订单详情
	public static final String USER_ORDER_DETAIL = SERVER
			+ "/api/userOrder/detail";
	// 查询账户流水明细
	public static final String ACCOUNT_DETAIL = SERVER
			+ "/api/account/accountDetail";
	// 查询账户退款记录
	public static final String ACCOUNT_BACK_DETAIL = SERVER
			+ "/api/account/accountBackDetail";

	// 提现申请获取绑定id
	public static final String ACCOUNT_USER_WITH_DRAW = SERVER
			+ "/api/account/userWithDraw";
	// 提现记录
	public static final String ACCOUNT_USER_WITH_DRAW_LIST = SERVER
			+ "/api/account/userWithDrawList";
	// 消息列表
	public static final String LIST_MESSAGE = SERVER + "/api/message/list";
	// 改变消息状态
	public static final String MESSAGE_STATE = SERVER + "/api/message/detail";
	// 改变所有消息状态
	public static final String MESSAGE_UPDATEALL = SERVER
			+ "/api/message/updateAll";
	// 查询优荐经纪人
	public static final String QUERY_EXCELLENT_BROKER = SERVER
			+ "/api/salesway/queryExcellentBroker";
	// 查询优荐经纪人
	public static final String QUERYBROKER = SERVER
				+ "/api/salesway/queryBroker";
	//保存用户分享，调用红包
	public static final String SAVE_USER_SHARE = SERVER
			+ "/api/userShare/saveUserActivityShare";

	/**
	 * 以下是规则及协议
	 */
	// 竞拍规则：
	public static final String AUCTIONRULE = SERVER
			+ "/rule/gotoRulePage?basePage=auctionRule";
	// 秒杀规则：
	public static final String SECONDKILLRULE = SERVER
			+ "/rule/gotoRulePage?basePage=secondKillRule";
	// 优惠房：
	public static final String CUTHOUSERULE = SERVER
			+ "/rule/gotoRulePage?basePage=cutHouseRule";
	// 竞拍协议：
	public static final String AUCTIONDEAL = SERVER
			+ "/rule/gotoRulePage?basePage=auctionDeal";
	// 天天特价服务协议：
	public static final String DAYSPECALDEAL = SERVER
			+ "/rule/gotoRulePage?basePage=daySpecalDeal";
	// 用户注册协议：
	public static final String USERREGISTERDEAL = SERVER
			+ "/rule/gotoRulePage?basePage=userRegisterDeal";

}
