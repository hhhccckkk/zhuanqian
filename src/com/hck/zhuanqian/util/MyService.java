package com.hck.zhuanqian.util;

import com.hck.zhuanqian.ui.ShowOneAppActivity;
import com.hck.zhuanqian.widget.Toasts;

import android.R.string;
import android.app.ActivityManager;
import android.app.Service;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
	public static MyService myService;
	public static String pkg;
	public static String zhuce;
	public static int time;
	private appListener th;
	public static boolean isFish;
	public static boolean isOK;
	public static int nowTime;
	private final int waitTime = 1000;
	public static int isZhuce;

	@Override
	public IBinder onBind(Intent intent) {
		isOK = false;
		myService = this;
		isFish = false;
		th = new appListener();
		handler.post(th);
		Log.i("hck", "绑定service");
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		return super.onStartCommand(intent, flags, startId);
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (!isOK) {
				getInfo();
			}
			if (!isFish) {
				handler.postDelayed(th, waitTime);
			}

		};
	};

	class appListener implements Runnable {

		@Override
		public void run() {
			if (!isFish && handler != null) {
				handler.sendEmptyMessage(0);
			}
		}
	}

	public void getInfo() {
		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		RunningTaskInfo info = manager.getRunningTasks(1).get(0);
		String shortClassName = info.topActivity.getShortClassName(); // 类名
		String className = info.topActivity.getClassName(); // 完整类名
		String packageName = info.topActivity.getPackageName(); // 包名
		
		if (isZhuce == 1) {
			if (className.equals(zhuce)) {
				if (nowTime < time) {
					nowTime += waitTime;
				} else {
					isPlayOk();
					isOK=true;
					fish();
					isFish = true;
					handler = null;
					nowTime = 0;
					isZhuce = 0;
				}
			}
		} else {
			if (packageName.equals(pkg)) {
				if (nowTime < time) {
					nowTime += waitTime;
				} else {
					isPlayOk();
					isOK=true;
					fish();
					isFish = true;
					handler = null;
					nowTime = 0;
					isZhuce = 0;
				}
			}
		}

	}

	private void isPlayOk() {
		isOK = true;
		ShowOneAppActivity.shActivity.playAppOk();
		pkg = null;
		stop();
		onDestroy();
		
		
	}

	@Override
	public void onDestroy() {
		stop();
		stopSelf();
		super.onDestroy();

	}

	public void stop() {
		if (handler != null && th != null) {
			handler.removeCallbacks(th);
			Log.i("hck", "removeCallbacks");
			th = null;
		}
	}

	public void fish() {
		if (handler != null && th != null) {
			handler.removeCallbacks(th);
			th = null;
		}
		stopSelf();
	}

}
