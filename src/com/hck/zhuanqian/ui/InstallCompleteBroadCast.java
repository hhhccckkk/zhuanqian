package com.hck.zhuanqian.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class InstallCompleteBroadCast extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		String packageName = bundle.getString("packageName");
		String adName = bundle.getString("adName");
		String point = bundle.getString("point");
		String status = bundle.getString("status");
		//todo what you do 
		System.out.println("InstallCompleteBroadCast " + "point = " + point + "adname =" + adName);
	}

}
