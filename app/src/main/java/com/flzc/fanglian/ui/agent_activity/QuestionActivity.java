package com.flzc.fanglian.ui.agent_activity;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
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
import com.flzc.fanglian.model.ItemQuestionModel;
import com.flzc.fanglian.model.MessageBean;
import com.flzc.fanglian.model.OptionQuestionModel;
import com.flzc.fanglian.model.QuestionListModel;
import com.flzc.fanglian.model.UserIntention;
import com.flzc.fanglian.ui.adapter.QuestionAdapter;
import com.flzc.fanglian.util.LogUtil;
import com.google.gson.Gson;

/**
 * 
 * @ClassName: QuestionActivity
 * @Description: 优惠房下面的问卷调查
 * @author: Jack
 * @date: 2016年6月1日 下午3:18:24
 */
public class QuestionActivity extends BaseActivity implements OnClickListener{

	private RelativeLayout rl_back;
	private TextView tv_title;

	private ListView questionnaire_list;
	private TextView questionnaire_submit;
	private List<ItemQuestionModel> listData = new ArrayList<ItemQuestionModel>();
	private QuestionAdapter qAdapter;
	private UserIntention userIntention;
	private String buildingId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_questionnaire);
		Intent intent = getIntent();
		if(null != intent){
			buildingId = intent.getStringExtra("buildingId");
		}
		initView();
	}

	private void initView() {
		rl_back = (RelativeLayout) findViewById(R.id.rl_back);
		rl_back.setOnClickListener(this);
		
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("购房意向");

		questionnaire_submit = (TextView) findViewById(R.id.questionnaire_submit);
		questionnaire_submit.setOnClickListener(this);
		
		questionnaire_list = (ListView) findViewById(R.id.questionnaire_list);
		qAdapter = new QuestionAdapter(QuestionActivity.this, listData);
		questionnaire_list.setAdapter(qAdapter);
		
		getData();
	}

	private void getData(){
		loadingDialog.showDialog();
		String tokenId = UserInfoData.getData(Constant.TOKEN, "");
		Map<String, String> map = new HashMap<String, String>();
		map.put("tokenId", tokenId);
		SimpleRequest<QuestionListModel> simpleRequest = new SimpleRequest<QuestionListModel>(API.question, QuestionListModel.class,
				new Listener<QuestionListModel>() {
					@Override
					public void onResponse(QuestionListModel response) {
						if(response.getStatus() == 0){
							List<ItemQuestionModel> list = response.getResult();
							listData.addAll(list);
							qAdapter.notifyDataSetChanged();
						}else{
							showTost(response.getMsg());
						}
						loadingDialog.dismissDialog();
					}
				},new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						showTost(getResources().getString(R.string.net_error));
					}
				},map);
		UserApplication.getInstance().addToRequestQueue(simpleRequest);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_back://返回
			finish();
			break;
		case R.id.questionnaire_submit://提交按钮	
			validateData();
			break;
		default:
			break;
		}
	}

	private void validateData(){
		userIntention = new UserIntention();
		List<OptionQuestionModel> listdaList = new ArrayList<OptionQuestionModel>();
		for(int i = 0; i < listData.size(); i++){
			ItemQuestionModel itemQuestionModel = listData.get(i);
			List<OptionQuestionModel> lists = itemQuestionModel.getTagsList();
			listdaList.clear();
			listdaList.addAll(lists);
			label: for (int j = 0; j < listdaList.size(); j++) {
				OptionQuestionModel optionQuestionModel = listdaList.get(j);
				boolean check = optionQuestionModel.isCheck();
				if(check){
					Class<? extends UserIntention> userIntentionClass = userIntention.getClass();					
					try {
						Method method = userIntentionClass.getDeclaredMethod("set"+itemQuestionModel.getId(), Integer.class);
						//System.out.println("------itemQuestionModel.getId()-set"+itemQuestionModel.getId());
						method.invoke(userIntention, optionQuestionModel.getCode());
					} catch (Exception e) {
						e.printStackTrace();
					}
					break label;
				}
				if(j == listdaList.size() - 1){
					if(!check){
						showTost(itemQuestionModel.getTitle() + "未选中");
						return;
					}
				}
			}
		}
		userIntention.setBuildingId(buildingId);
		LogUtil.d("question", userIntention+"");
		saveuIntention();
	}
	
	private void saveuIntention(){
		loadingDialog.showDialog();
		String tokenId = UserInfoData.getData(Constant.TOKEN, "");
		String userString = new Gson().toJson(userIntention);
		Map<String, String> map = new HashMap<String, String>();
		map.put("tokenId", tokenId);
		map.put("userIntention", userString);
		SimpleRequest<MessageBean> request = new SimpleRequest<MessageBean>(API.INTENTION_SAVE, MessageBean.class,
				new Listener<MessageBean>() {
					@Override
					public void onResponse(MessageBean response) {
						if(response.getStatus() == 0){
							finish();
						}else{
							showTost(response.getMsg());
						}
						loadingDialog.dismissDialog();
					}
				}, map);
		UserApplication.getInstance().addToRequestQueue(request);
	}
	
}
