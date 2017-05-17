package com.flzc.fanglian.view.dialog;

import com.flzc.fanglian.R;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

/**
 * 
 * @ClassName: CustomDialog 
 * @Description: 自定义弹出框
 * 
 * 		标题	
 *   这一段是内容
 *   确定   |  取消
 *   
 * @author: 薛东超 
 * @date: 2016年3月17日 下午3:17:10
 */
public class CustomDialog extends Dialog{

	public CustomDialog(Context context) {
		super(context);
	}
	
	public CustomDialog(Context context, int theme) {
		super(context, theme);
	}
	
	public static class Builder{
		private Context context; // 上下文对象
		private String title; // 对话框标题
		private String content;//对话框内容
		private String confirm_btnText; // 按钮名称“确定”
		private String cancel_btnText; // 按钮名称“取消”
		/* 按钮监听事件 */
		private DialogInterface.OnClickListener confirm_btnClickListener;
		private DialogInterface.OnClickListener cancel_btnClickListener;

		public Builder(Context context) {
			this.context = context;
		}

		public Builder setTitle(int title) {
			this.title = (String) context.getText(title);
			return this;
		}

		public Builder setTitle(String title) {
			this.title = title;
			return this;
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

		public Builder setPositiveButton(int confirm_btnText,
				DialogInterface.OnClickListener listener) {
			this.confirm_btnText = (String) context.getText(confirm_btnText);
			this.confirm_btnClickListener = listener;
			return this;
		}

		public Builder setPositiveButton(String confirm_btnText,
				DialogInterface.OnClickListener listener) {
			this.confirm_btnText = confirm_btnText;
			this.confirm_btnClickListener = listener;
			return this;
		}

		public Builder setNegativeButton(int cancel_btnText,
				DialogInterface.OnClickListener listener) {
			this.cancel_btnText = (String) context.getText(cancel_btnText);
			this.cancel_btnClickListener = listener;
			return this;
		}

		public Builder setNegativeButton(String cancel_btnText,
				DialogInterface.OnClickListener listener) {
			this.cancel_btnText = cancel_btnText;
			this.cancel_btnClickListener = listener;
			return this;
		}
		
		public CustomDialog create() {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// instantiate the dialog with the custom Theme
			final CustomDialog dialog = new CustomDialog(context,
					R.style.myalterstyle);
			View layout = inflater.inflate(R.layout.custom_dialog, null);
			dialog.addContentView(layout, new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			// set the dialog title
			((TextView) layout.findViewById(R.id.custom_diallog_title)).setText(title);
			((TextView) layout.findViewById(R.id.custom_diallog_title)).getPaint()
					.setFakeBoldText(true);
			((TextView) layout.findViewById(R.id.custom_dialog_content)).setText(content);
			((TextView) layout.findViewById(R.id.custom_dialog_content)).getPaint()
					.setFakeBoldText(false);
			
			// set the confirm button
			if (confirm_btnText != null) {
				((Button) layout.findViewById(R.id.custom_dialog_confirm_btn))
						.setText(confirm_btnText);
				if (confirm_btnClickListener != null) {
					((Button) layout.findViewById(R.id.custom_dialog_confirm_btn))
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									confirm_btnClickListener.onClick(dialog,
											DialogInterface.BUTTON_POSITIVE);
								}
							});
				}
			} else {
				// if no confirm button just set the visibility to GONE
				layout.findViewById(R.id.custom_dialog_confirm_btn).setVisibility(View.GONE);
			}
			// set the cancel button
			if (cancel_btnText != null) {
				((Button) layout.findViewById(R.id.custom_dialog_cancle_btn))
						.setText(cancel_btnText);
				if (cancel_btnClickListener != null) {
					((Button) layout.findViewById(R.id.custom_dialog_cancle_btn))
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									cancel_btnClickListener.onClick(dialog,
											DialogInterface.BUTTON_NEGATIVE);
								}
							});
				}
			} else {
				// if no confirm button just set the visibility to GONE
				layout.findViewById(R.id.custom_dialog_cancle_btn).setVisibility(View.GONE);
			}
			// set the content message
			dialog.setContentView(layout);
			return dialog;
		}
		
	}
}
