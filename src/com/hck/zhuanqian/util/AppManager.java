package com.hck.zhuanqian.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

/**
 * 应用程序Activity管理类：用于Activity管理和应用程序退出
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class AppManager {
	
	private static List<Activity> activityStack;
	private static AppManager instance;
	
	private AppManager(){}
	/**
	 * 单一实例
	 */
	public static AppManager getAppManager(){
		if(instance==null){
			instance=new AppManager();
		}
		return instance;
	}
	/**
	 * 添加Activity到堆栈
	 */
	public void addActivity(Activity activity){
		if(activityStack==null){
			activityStack=new ArrayList<Activity>();
		}
	     activityStack.add(activity);
	}
	public void remove(Activity activity){
		//try {
		//	activityStack.remove(activity);
		//} catch (Exception e) {
		//}
		
	}
	/**
	 * 退出应用程序
	 */
	public void AppExit(Context context) {
		try {
			
			for (int i = 0; i < activityStack.size(); i++) {
				((Activity)activityStack.get(i)).finish();
			}
			System.exit(0);
		} catch (Exception e) {	}
	}
}