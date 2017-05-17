package com.flzc.fanglian.ui.me;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.flzc.fanglian.R;
import com.flzc.fanglian.app.UserApplication;
import com.flzc.fanglian.base.BaseActivity;
import com.flzc.fanglian.db.UserInfoData;
import com.flzc.fanglian.http.API;
import com.flzc.fanglian.http.Constant;
import com.flzc.fanglian.http.SimpleRequest;
import com.flzc.fanglian.model.BaseInfoBean;
import com.flzc.fanglian.model.QueryUserBean;
import com.flzc.fanglian.model.QueryUserBean.Result.User;
import com.flzc.fanglian.util.CompressPictureUtil;
import com.flzc.fanglian.util.DateUtils;
import com.flzc.fanglian.view.CircleImageView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * 
 * @ClassName: MyInfoActivity
 * @Description: 查询用户信息
 * @author: LU
 * @date: 2016-3-4 下午3:12:28
 */
public class MyInfoActivity extends BaseActivity implements OnClickListener {

	private ImageButton imgbt_ercode;
	private RelativeLayout rl_back, rl_sex, rl_birthday, rl_safe;
	private CircleImageView img_headImg_myInfo;
	private TextView tv_nickName_myInfo, tv_sex;
	private final int REQUEST_CODE_IMAGE = 100;
	private final int REQUEST_CODE_CAMERA = 200;
	private int gender;
	/**
	 * 修改信息的弹窗
	 */
	private View popView;
	private PopupWindow popupWindow;
	private RelativeLayout layout_cancelLayout_popupWindow,
			layout_albumlLayout_popupWindow, layout_cameralLayout_popupWindow,
			layout_malelLayout_changesex_popupWindow,
			layout_femaleLayout_changesex_popupWindow,
			layout_secretLayout_changesex_popupWindow;

	private DatePicker datePicker;
	private String year, month, day;
	private Button btn_left_dialogBirth, btn_right_dialogBirth;
	private String fileName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myinfo);
		initView();
		initListener();
		initData();
	}

	@Override
	protected void onResume() {
		super.onResume();
		boolean isReflash = UserApplication.getInstance().isReflush();
		if (isReflash) {
			// 需要刷新
			tv_nickName_myInfo.setText(UserInfoData.getData(Constant.NICK_NAME, ""));
			UserApplication.getInstance().setReflush(false);
		} else {
			// 不需要刷新
			UserApplication.getInstance().setReflush(false);
		}
	}
	private void initView() {
		// 标题
		rl_back = (RelativeLayout) findViewById(R.id.rl_back);
		imgbt_ercode = (ImageButton) findViewById(R.id.imgbt_ercode);
		//头像
		img_headImg_myInfo = (CircleImageView) findViewById(R.id.img_headImg_myInfo);
		rl_sex = (RelativeLayout) findViewById(R.id.rl_sex);
		rl_birthday = (RelativeLayout) findViewById(R.id.rl_birthday);
		tv_nickName_myInfo = (TextView) findViewById(R.id.tv_nickName_myInfo);
		rl_safe = (RelativeLayout) findViewById(R.id.rl_safe);
		tv_sex = (TextView) findViewById(R.id.tv_sex);
	}

	private void initListener() {
		rl_back.setOnClickListener(this);
		imgbt_ercode.setOnClickListener(this);
		img_headImg_myInfo.setOnClickListener(this);
		rl_sex.setOnClickListener(this);
		rl_birthday.setOnClickListener(this);
		tv_nickName_myInfo.setOnClickListener(this);
		rl_safe.setOnClickListener(this);

	}

	private void initData() {
		loadingDialog.showDialog();
		Map<String, String> map = new HashMap<String, String>();
		map.put("tokenId", UserInfoData.getData(Constant.TOKEN, ""));

		SimpleRequest<QueryUserBean> request = new SimpleRequest<QueryUserBean>(
				API.QUERYUSERBYTOKENID, QueryUserBean.class,
				new Listener<QueryUserBean>() {
					@Override
					public void onResponse(QueryUserBean response) {
						if (response != null) {
							if (0 == response.getStatus()) {
								User user = response.getResult().getUser();
								if (user != null) {
									imageLoader.displayImage(user.getHeadUrl(),
											img_headImg_myInfo,options);
									//将用户的头像保存起来
									UserInfoData.saveData(Constant.HEAD_URL, user.getHeadUrl());
									tv_nickName_myInfo.setText(user.getNickName()+"");
									UserInfoData.saveData(Constant.NICK_NAME, user.getNickName()+"");
									// 101男，102女，103保密
									int gender = user.getGender();
									switch (gender) {
									case 101:
										tv_sex.setText("男");
										break;
									case 102:
										tv_sex.setText("女");
										break;
									case 103:
										tv_sex.setText("保密");
										break;
									default:
										break;
									}
								}
							}else{
								showTost(response.getMsg());
							}
						}
						loadingDialog.dismissDialog();
					}
				},new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						loadingDialog.dismissDialog();
					}
				}, map);
		UserApplication.getInstance().addToRequestQueue(request);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_back:
			finish();
			break;
		case R.id.imgbt_ercode:
			goOthers(MyErCodeActivity.class);
			break;
		case R.id.img_headImg_myInfo:// 点击头像
			showChangeInfoPopupWindow(1);
			break;
		case R.id.rl_birthday:// 点击更换生日
			showChangeInfoPopupWindow(2);
			break;
		case R.id.rl_sex:// 点击修改性别
			showChangeInfoPopupWindow(3);
			break;
		case R.id.btn_left_dialogBirth:// 修改生日时点击弹窗的取消按钮
			popupWindow.dismiss();
			break;
		case R.id.btn_right_dialogBirth:// 修改生日时点击弹窗的确定按钮
			// 后续为提交修改生日操作
			popupWindow.dismiss();
			break;
		case R.id.tv_nickName_myInfo:// 点击修改用户昵称
			goOthers(ChangeUserNameActivity.class);
			break;
		case R.id.rl_safe:
			goOthers(AccountSafetyActivity.class);
			break;
		// 点击修改性别 男
		case R.id.layout_malelLayout_changesex_popupWindow:
			gender = 101;
			reviseSex(gender);
			popupWindow.dismiss();
			break;
		// 点击修改性别 女
		case R.id.layout_femaleLayout_changesex_popupWindow:
			gender = 102;
			reviseSex(gender);
			popupWindow.dismiss();
			break;
		// 点击修改性别 保密
		case R.id.layout_secretLayout_changesex_popupWindow:
			gender = 103;
			reviseSex(gender);
			popupWindow.dismiss();
			break;
		case R.id.layout_cameralLayout_changeHeadImage_popupWindow:
			showCamera();
			popupWindow.dismiss();
			break;
		case R.id.layout_albumlLayout_changeHeadImage_popupWindow:
			showAlbum();
			popupWindow.dismiss();
			break;
		case R.id.layout_cancelLayout_changeHeadImage_popupWindow:
			popupWindow.dismiss();
			break;
		default:
			break;
		}

	}

	/**
	 * 
	 * @Title: showAlbum
	 * @Description: 进入手机相册选择图片
	 * @return: void
	 */
	private void showAlbum() {
		Intent intent = new Intent(Intent.ACTION_PICK,
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(intent, REQUEST_CODE_IMAGE);
	}

	/**
	 * 
	 * @Title: showCamera
	 * @Description: 进入相机选择图片
	 * @return: void
	 */
	private void showCamera() {
		fileName = DateUtils.currentTime() + "";
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// path为保存图片的路径，执行完拍照以后能保存到指定的路径下
		File file = new File("/sdcard/" + fileName + ".jpg");
		Uri imageUri = Uri.fromFile(file);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		startActivityForResult(intent, REQUEST_CODE_CAMERA);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case REQUEST_CODE_CAMERA:// 相机
				String sdStatus = Environment.getExternalStorageState();
				if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
					return;
				}
				String cameraImagePath = "/sdcard/" + fileName + ".jpg";
				postPhoto(CompressPictureUtil.saveBitmapFile(CompressPictureUtil.getSmallBitmap(cameraImagePath), "/sdcard/" + fileName+"1" + ".jpg"));
				break;
			case REQUEST_CODE_IMAGE:// 图库
				String imgpath= "";
				Uri uri = data.getData();
				Cursor cursor = getContentResolver().query(uri, null, null,null,null);
				if (cursor != null && cursor.moveToFirst()) {
					imgpath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
				}
				postPhoto(CompressPictureUtil.saveBitmapFile(CompressPictureUtil.getSmallBitmap(imgpath), "/sdcard/" + fileName+"2" + ".jpg"));
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 
	 * @Title: postPhoto 
	 * @Description: 上传头像
	 * @param cameraImagePath
	 * @return: void
	 */
	private void postPhoto(File file) {	
		loadingDialog.showDialog();
		try {
			//File file = new File(imagePath);
			AsyncHttpClient client = new AsyncHttpClient();
			RequestParams params = new RequestParams();
			params.put("file", file);
			params.put("tokenId", UserInfoData.getData(Constant.TOKEN, ""));

			client.post(API.UPLOAD_HEAD, params, new AsyncHttpResponseHandler() {

				@Override
				public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
					initData();
					loadingDialog.dismissDialog();
				}

				@Override
				public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
					initData();
					loadingDialog.dismissDialog();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	// 弹出修改信息的对话框 1为修改头像 2为修改生日 3为修改性别
	private void showChangeInfoPopupWindow(int popType) {
		LayoutInflater layoutInflater = LayoutInflater
				.from(MyInfoActivity.this);
		if (popType == 1) {// 修改头像
			popView = layoutInflater.inflate(
					R.layout.popupwindow_change_headimage, null);
			layout_cancelLayout_popupWindow = (RelativeLayout) popView
					.findViewById(R.id.layout_cancelLayout_changeHeadImage_popupWindow);
			layout_albumlLayout_popupWindow = (RelativeLayout) popView
					.findViewById(R.id.layout_albumlLayout_changeHeadImage_popupWindow);
			layout_cameralLayout_popupWindow = (RelativeLayout) popView
					.findViewById(R.id.layout_cameralLayout_changeHeadImage_popupWindow);
			layout_cancelLayout_popupWindow.setOnClickListener(this);
			layout_albumlLayout_popupWindow.setOnClickListener(this);
			layout_cameralLayout_popupWindow.setOnClickListener(this);

		} else if (popType == 2) {// 修改生日
			popView = layoutInflater.inflate(R.layout.dialog_birthday_picker,
					null);
			datePicker = (DatePicker) popView
					.findViewById(R.id.datePicker_birthday);
			datePicker.setCalendarViewShown(false);
			datePicker
					.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
			datePicker.setMaxDate(System.currentTimeMillis() - 1000);// 设置日期选择器最低日期为当前日期
			if (year != null && !"".equals(year) && month != null
					&& !"".equals(month) && day != null && !"".equals(day)) {
				datePicker.updateDate(Integer.parseInt(year),
						Integer.parseInt(month) - 1, Integer.parseInt(day));
			} else {
				datePicker.updateDate(1980, 0, 1);
			}
			btn_left_dialogBirth = (Button) popView
					.findViewById(R.id.btn_left_dialogBirth);
			btn_right_dialogBirth = (Button) popView
					.findViewById(R.id.btn_right_dialogBirth);
			btn_left_dialogBirth.setOnClickListener(this);
			btn_right_dialogBirth.setOnClickListener(this);

			popView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (null != popupWindow || popupWindow.isShowing()) {
						popupWindow.dismiss();
					}
				}
			});
		} else if (popType == 3) {// 修改性别
			popView = layoutInflater.inflate(R.layout.popupwindow_change_sex,
					null);
			layout_malelLayout_changesex_popupWindow = (RelativeLayout) popView
					.findViewById(R.id.layout_malelLayout_changesex_popupWindow);
			layout_malelLayout_changesex_popupWindow.setOnClickListener(this);
			layout_femaleLayout_changesex_popupWindow = (RelativeLayout) popView
					.findViewById(R.id.layout_femaleLayout_changesex_popupWindow);
			layout_femaleLayout_changesex_popupWindow.setOnClickListener(this);
			layout_secretLayout_changesex_popupWindow = (RelativeLayout) popView
					.findViewById(R.id.layout_secretLayout_changesex_popupWindow);
			layout_secretLayout_changesex_popupWindow.setOnClickListener(this);

		}
		if (null == popupWindow || !popupWindow.isShowing()) {
			popupWindow = new PopupWindow(getApplicationContext());
			//设置破普window的属性
			popupWindow.setWidth(LayoutParams.MATCH_PARENT);
			popupWindow.setHeight(LayoutParams.WRAP_CONTENT);
			popupWindow.setBackgroundDrawable(new BitmapDrawable());
			popupWindow.setFocusable(true);
			popupWindow.setOutsideTouchable(true);
			popupWindow.setContentView(popView);
			popupWindow.setAnimationStyle(R.style.anim_menu_bottombar);
			popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
			popupWindow.showAtLocation(findViewById(R.id.ll_mainLayout_myInfo), Gravity.BOTTOM, 0, 0);
	        WindowManager.LayoutParams lp = getWindow().getAttributes();
	        lp.alpha = 0.5f;
	        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
	        getWindow().setAttributes(lp);
	        popupWindow.setOnDismissListener(new OnDismissListener() {
	 
	            @Override
	            public void onDismiss() {
	                WindowManager.LayoutParams lp = getWindow().getAttributes();
	                lp.alpha = 1f;
	                getWindow().setAttributes(lp);
	            }
	        });
		}
	}

	/**
	 * 
	 * @Title: reviseSex
	 * @Description: 修改性别 101男，102女，103保密
	 * @return: void
	 */
	private void reviseSex(int gender) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("tokenId", UserInfoData.getData(Constant.TOKEN, ""));
		map.put("gender", gender + "");

		SimpleRequest<BaseInfoBean> request = new SimpleRequest<BaseInfoBean>(
				API.UPDATEGENDER, BaseInfoBean.class,
				new Listener<BaseInfoBean>() {
					@Override
					public void onResponse(BaseInfoBean response) {
						if (response != null) {
							if (0 == response.getStatus()) {
								// 表示修改性别修改成功
								initData();
							}
						}
					}
				}, map);
		UserApplication.getInstance().addToRequestQueue(request);
	}

}
