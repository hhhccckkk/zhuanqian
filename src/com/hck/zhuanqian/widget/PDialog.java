package com.hck.zhuanqian.widget;

import android.app.ProgressDialog;
import android.content.Context;
public class PDialog {
	private static ProgressDialog dialog;

	public static void showDialog(Context context, String title, String content) {
		try {
			if (dialog!=null&&dialog.isShowing()) {
				dialog.dismiss();
			}
			else {
				if (context ==null) {
					return;
				}
				dialog=ProgressDialog.show(context, title, content);
				dialog.setCancelable(true);
			}
		} catch (Exception e) {
		}
		
	}
	public static void hidenDialog()
	{
		try {
			if (dialog!=null) {
				dialog.dismiss();
				dialog=null;
				return;
			}
		} catch (Exception e) {
		}
		
	}
}
