package com.hck.getmoney.widget;


import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hck.kedouzq.R;



public class Toasts {
   public static void toast(Context context,String textString)
   {
	   View view=LayoutInflater.from(context).inflate(R.layout.toast, null);
		TextView textView=(TextView) view.findViewById(R.id.toast_text);
		textView.setText( textString);
		Toast toast = new Toast(context);    //创建一个toast
		toast.setGravity(Gravity.CENTER, 0,0);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(view);    //为toast设置一个view
		toast.show();
   }
   
}
