package com.flzc.fanglian.ui.common;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flzc.fanglian.R;
import com.flzc.fanglian.base.BaseActivity;
import com.flzc.fanglian.ui.adapter.UserListAdapter;

public class UserListActivity extends BaseActivity implements OnClickListener{
	
	private RelativeLayout rl_back;
	private TextView tv_title;
	private ListView lv_user_list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_list);
		initView();
		initLIstener();
	}

	private void initView() {
		//标题栏
		rl_back = (RelativeLayout)findViewById(R.id.rl_back);
		tv_title = (TextView)findViewById(R.id.tv_title);
		tv_title.setText("参与用户");
		
		//列表
		lv_user_list = (ListView)findViewById(R.id.lv_user_list);
		List<String> list = new ArrayList<String>();
		list.add("csjc");
		list.add("csdgjc");
		list.add("csgegjc");
		lv_user_list.setAdapter(new UserListAdapter(this,list));
	}

	private void initLIstener() {
		rl_back.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_back:
			finish();
			break;

		}
		
	}

}
