package com.hck.zhuanqian.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.hck.zhuanqian.R;
import com.hck.zhuanqian.ui.BaseAlert;
import com.hck.zhuanqian.util.MyTools;

public class AlertDialogs {
	private static Button leftButton;
	private static Button rightButton;
	private static TextView textView; // 提示信息
	public static AlertDialog aDialog;
	public static AlertDialog aDialog2;

	public static void alertDialog(final Context context,
			final BaseAlert activity, String title, String btsString1,
			String btString2, String leftTag, String rightTag) {
		final View view;

		view = LayoutInflater.from(context)
				.inflate(R.layout.alert_dialog, null); // 自定义布局
		leftButton = (Button) view.findViewById(R.id.bt1);
		rightButton = (Button) view.findViewById(R.id.bt2);
		textView = (TextView) view.findViewById(R.id.d_title);
		leftButton.setText(btsString1);
		rightButton.setText(btString2);
		textView.setText(title);
		if (aDialog != null && aDialog.isShowing()) {
			aDialog.dismiss();
		}
		aDialog = new AlertDialog.Builder(context).create();
		try {
			aDialog.show();
		} catch (Exception e) {
		}
		WindowManager.LayoutParams params = aDialog.getWindow().getAttributes();// 得到属性
		params.gravity = Gravity.CENTER; // 显示在中间

		params.width = (int) (MyTools.getScreenWidth() * 0.8); // 设置对话框的宽度为手机屏幕的0.8
		params.height = (int) (MyTools.getScreenHeight() * 0.25);// 设置对话框的高度为手机屏幕的0.25

		aDialog.getWindow().setAttributes(params); // 設置屬性
		aDialog.getWindow().setContentView(view); // 把自定義view加上去
		leftButton.setTag(leftTag);
		rightButton.setTag(rightTag);

		rightButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				aDialog.dismiss();
				activity.doRightButton(rightButton.getTag().toString());
			}
		});
		leftButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				aDialog.dismiss();
				activity.doLeftButton(leftButton.getTag().toString());
			}
		});
		aDialog.setCancelable(false);
	}

	public void alert(final BaseAlert alert, Context context, String title,
			String content) {
		try
		 {
		if (aDialog2 != null && aDialog2.isShowing()) {
			aDialog2.dismiss();
		}
		aDialog2 = new AlertDialog.Builder(context).create();
		aDialog2.setCancelable(false);
		final View view = LayoutInflater.from(context).inflate(R.layout.alert,
				null);
	//	TextView titleTextView = (TextView) view.findViewById(R.id.d_title);
		TextView contenTextView = (TextView) view.findViewById(R.id.d_content);
		Button button = (Button) view.findViewById(R.id.d_button);
	//	titleTextView.setText(title);
		contenTextView.setText(content);
		
			aDialog2.show();

		WindowManager.LayoutParams params = aDialog2.getWindow()
				.getAttributes();
		params.width = (int) (MyTools.getScreenWidth() * 0.8); // 设置对话框的宽度为手机屏幕的0.8
		params.height = (int) (MyTools.getScreenHeight() * 0.25);// 设置对话框的高度为手机屏幕的0.25
		aDialog2.getWindow().setAttributes(params);
		aDialog2.getWindow().setContentView(view);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				aDialog2.dismiss();
				alert.doSometing("");
			}
		});
		 }
		catch(Exception e){
			
		}

	}
	public static OneBtOnclick oneBtOnclick;
public interface OneBtOnclick{
	public void callBack(int tag);
}

	public static void alert(Context context, String title, String content,boolean big,final OneBtOnclick oneBtOnclick,final int tag) {
		if (aDialog != null && aDialog.isShowing()) {
			aDialog.dismiss();
		}
		AlertDialogs.oneBtOnclick=null;
		AlertDialogs.oneBtOnclick =oneBtOnclick;
		aDialog = new AlertDialog.Builder(context).create();
		aDialog.setCancelable(false);
		final View view = LayoutInflater.from(context).inflate(R.layout.alert,
				null);
		TextView contenTextView = (TextView) view.findViewById(R.id.d_content);
		Button button = (Button) view.findViewById(R.id.d_button);
		contenTextView.setText(content);
		try {
			aDialog.show();
		} catch (Exception e) {
		}

		WindowManager.LayoutParams params = aDialog.getWindow().getAttributes();
		params.width = (int) (MyTools.getScreenWidth() * 0.8); // 设置对话框的宽度为手机屏幕的0.8
		if (big) {
			params.height = (int) (MyTools.getScreenHeight() * 0.35);
		}
		else {
			params.height = (int) (MyTools.getScreenHeight() * 0.25);
		}
	//	;// 设置对话框的高度为手机屏幕的0.25
		aDialog.getWindow().setAttributes(params);
		aDialog.getWindow().setContentView(view);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				aDialog.dismiss();
				if (oneBtOnclick!=null) {
					oneBtOnclick.callBack(tag);
				}
				
			}
		});

	}
	public static void alert(Context context, String title, String content,boolean big)
	{
		alert(context, title, content, big,null,0);
	}

}
