package com.flzc.fanglian.ui.fragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.flzc.fanglian.R;
import com.flzc.fanglian.app.UserApplication;
import com.flzc.fanglian.base.BaseFragment;
import com.flzc.fanglian.db.UserInfoData;
import com.flzc.fanglian.http.API;
import com.flzc.fanglian.http.Constant;
import com.flzc.fanglian.http.SimpleRequest;
import com.flzc.fanglian.model.MessageListBean;
import com.flzc.fanglian.model.MessageListBean.Result;
import com.flzc.fanglian.ui.adapter.MessageListAdapter;
import com.flzc.fanglian.ui.message.MessageDetailActivity;
import com.flzc.fanglian.view.LoadListView;
import com.flzc.fanglian.view.LoadListView.OnLoaderListener;
import com.flzc.fanglian.view.dialog.CustomDialog;
import com.flzc.fanglian.view.dialog.CustomDialog.Builder;

/**
 * 
 * @ClassName: MessageFragment
 * @Description: 消息列表
 * @author: 薛东超
 * @date: 2016年3月3日 下午6:51:45
 */

// 此页面先不做任何处理，因为后台接口的字段未定，等到定时候在写model部分，只是将布局画 出来了，适配器布局是adapter_msg.xml
// 点击后跳转到的页面是 activity_msg_details_info.xml 最后的蓝色字体颜色是60a7e8 可以在java代码中动态的设置
// 写的时候感觉命名不好可以rename
public class MessageFragment extends BaseFragment implements OnLoaderListener {

	private View view;
	private TextView tv_title;
	private TextView tv_right;
	private LoadListView lv_msg_mainlist;
	protected List<Result> messageList = new ArrayList<MessageListBean.Result>();
	private MessageListAdapter messageAdapter;
	private SwipeRefreshLayout swipeRefreshLayout;
	private int page; // 加载更多
	protected boolean isRefresh;
	private View headView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_message, container, false);
		initView();
		initListener();
		// initData();
		pullTORefresh();
		return view;
	}

	@Override
	public void onResume() {
		if(null != tv_right){
			tv_right.setTextColor(Color.parseColor("#999999"));
			tv_right.setEnabled(false);
		}
		isRefresh = true;
		page = 1;
		messageList.clear();
		initData(page);
		super.onResume();
	}

	private void pullTORefresh() {
		// 下拉刷新部分
		swipeRefreshLayout = (SwipeRefreshLayout) view
				.findViewById(R.id.swipeRefreshLayout);
		// 设置卷内的颜色
		swipeRefreshLayout.setColorSchemeResources(android.R.color.darker_gray,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		// 设置下拉刷新监听
		swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				isRefresh = true;
				page = 1;
				initData(page);
			}
		});
	}

	private void initView() {
		// 标题栏
		view.findViewById(R.id.rl_back).setVisibility(View.INVISIBLE);
		tv_title = (TextView) view.findViewById(R.id.tv_title);
		tv_right = (TextView) view.findViewById(R.id.tv_right);
		tv_title.setText("消息");
		tv_right.setText("标为已读");
		tv_right.setTextColor(Color.parseColor("#999999"));
		tv_right.setEnabled(false);

		// 列表
		lv_msg_mainlist = (LoadListView) view
				.findViewById(R.id.lv_msg_mainlist);
		headView = LayoutInflater.from(mActivity).inflate(
				R.layout.layout_search_none, null);
		TextView content = (TextView) headView.findViewById(R.id.tv_text);
		content.setText("这个人太低调了\n一个通知也没有╭(╯^╰)╮");
		lv_msg_mainlist.setLoaderListener(this);
		messageAdapter = new MessageListAdapter(mActivity, messageList);
		lv_msg_mainlist.setAdapter(messageAdapter);
	}

	private void initListener() {
		// 标记所有消息为已读
		tv_right.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showPromptDialog();

			}
		});
		// item跳转
		lv_msg_mainlist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (messageList.size() == 0) {
					return;
				}
				Result messageResult = messageList.get(position);
				Intent intent = new Intent(mActivity,
						MessageDetailActivity.class);
				intent.putExtra("messageResult", (Serializable) messageResult);
				startActivity(intent);
			}
		});
	}

	/**
	 * 
	 * @Title: showPromptDialog
	 * @Description: 将消息标记为已读的提示框
	 * @return: void
	 */
	protected void showPromptDialog() {
		Builder builder = new CustomDialog.Builder(mActivity);
		builder.setTitle("标记为已读");
		builder.setContent("将所有消息标记为已读状态。");
		builder.setNegativeButton("取消", new CustomDialog.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.setPositiveButton("确定", new CustomDialog.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				postReadState();
			}
		});
		builder.create().show();

	}

	/**
	 * 
	 * @Title: postReadState
	 * @Description: 提交已读状态
	 * @return: void
	 */
	private void postReadState() {
		loadingDialog.showDialog();
		Map<String, String> map = new HashMap<String, String>();
		map.put("tokenId", UserInfoData.getData(Constant.TOKEN, ""));
		map.put("inviteCode", UserInfoData.getData(Constant.MY_INVITE_CODE, ""));
		SimpleRequest<Object> req = new SimpleRequest<Object>(
				API.MESSAGE_UPDATEALL, Object.class, new Listener<Object>() {

					@Override
					public void onResponse(Object response) {
						tv_right.setTextColor(Color.parseColor("#999999"));
						tv_right.setEnabled(false);
						loadingDialog.dismissDialog();
						for (int i = 0; i < messageList.size(); i++) {
							if (messageList.get(i).getMessageRead() == 0) {
								messageList.get(i).setMessageRead(1);
							}
						}
						messageAdapter.notifyDataSetChanged();
						// isRefresh = true;
						// page = 1;
						// initData(page);
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						loadingDialog.showDialog();
					}
				}, map);
		UserApplication.getInstance().addToRequestQueue(req);

	}

	/**
	 * 
	 * @Title: initData
	 * @Description: 请求列表数据
	 * @return: void
	 */
	private void initData(int page) {
		loadingDialog.showDialog();
		Map<String, String> map = new HashMap<String, String>();
		map.put("tokenId", UserInfoData.getData(Constant.TOKEN, ""));
		map.put("page", page + "");
		map.put("pageSize", "10");
		map.put("inviteCode", UserInfoData.getData(Constant.MY_INVITE_CODE, ""));
		SimpleRequest<MessageListBean> req = new SimpleRequest<MessageListBean>(
				API.LIST_MESSAGE, MessageListBean.class,
				new Listener<MessageListBean>() {

					@Override
					public void onResponse(MessageListBean response) {
						if (response != null) {
							if (0 == response.getStatus()) {
								if (isRefresh) {
									isRefresh = false;
									messageList.clear();
								}
								List<Result> list = response.getResult();
								messageList.addAll(list);
								queryed: for (int i = 0; i < messageList.size(); i++) {
									if (messageList.get(i).getMessageRead() == 0) {
										tv_right.setTextColor(Color
												.parseColor("#333333"));
										tv_right.setEnabled(true);
										break queryed;
									}
								}
								if (null != headView) {
									lv_msg_mainlist.removeHeaderView(headView);
								}
								if (messageList.size() == 0) {
									lv_msg_mainlist.addHeaderView(headView);
								}
								messageAdapter.notifyDataSetChanged();
							} else {
								showTost(response.getMsg());
							}
						}
						loadingDialog.dismissDialog();
						// 通知listview加载完毕
						lv_msg_mainlist.loadComplete();
						swipeRefreshLayout.setRefreshing(false);
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						loadingDialog.dismissDialog();
						// 通知listview加载完毕
						lv_msg_mainlist.loadComplete();
						swipeRefreshLayout.setRefreshing(false);
					}
				}, map);
		UserApplication.getInstance().addToRequestQueue(req);
	}

	@Override
	public void onLoad() {
		initData(++page);
	}

}
