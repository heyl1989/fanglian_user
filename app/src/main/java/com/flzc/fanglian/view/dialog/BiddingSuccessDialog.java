package com.flzc.fanglian.view.dialog;

import com.flzc.fanglian.R;
import com.flzc.fanglian.view.dialog.CustomDialog.Builder;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BiddingSuccessDialog extends Dialog{

	public BiddingSuccessDialog(Context context) {
		super(context);
	}
	
	public BiddingSuccessDialog(Context context, int theme) {
		super(context, theme);
	}
	
	public static class Builder{
		private Context context; // 上下文对象
		private String content;//对话框内容
		/* 按钮监听事件 */
		private DialogInterface.OnClickListener confirm_btnClickListener;

		public Builder(Context context) {
			this.context = context;
		}
		
		public Builder setContent(String  content){
			this.content = content;
			return this;
		}
		
		/**
		 * 设置对话框界面
		 * 
		 * @param v
		 *            View
		 * @return
		 */
		public Builder setPositiveButton(DialogInterface.OnClickListener listener) {
			this.confirm_btnClickListener = listener;
			return this;
		}
		
		
		public CustomDialog create() {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// instantiate the dialog with the custom Theme
			final CustomDialog dialog = new CustomDialog(context,
					R.style.myalterstyle);
			View layout = inflater.inflate(R.layout.popupwindow_bidding_success, null);
			dialog.addContentView(layout, new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			// set the dialog title
			((TextView) layout.findViewById(R.id.tv_contentHint_biddingSuccess)).setText(content);
			((TextView) layout.findViewById(R.id.tv_contentHint_biddingSuccess)).getPaint()
					.setFakeBoldText(true);
			
			// set the confirm button
			
			RelativeLayout relativeLayout = (RelativeLayout)layout.findViewById(R.id.rl_backLayout_biddingSuccess);
			if(confirm_btnClickListener != null){
				relativeLayout.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						confirm_btnClickListener.onClick(dialog,
								DialogInterface.BUTTON_POSITIVE);
					}
				});
			}
			// set the content message
			dialog.setContentView(layout);
			return dialog;
		}
		
	}
}
