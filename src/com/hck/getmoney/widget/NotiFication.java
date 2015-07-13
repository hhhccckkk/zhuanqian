package com.hck.getmoney.widget;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;

import com.hck.kedouzq.R;



public class NotiFication {
	private static NotificationManager notificationManager;
	private static Notification notification ;
	public static Boolean isShow;
	public static void showNotification(Context context,String content)
	{
		isShow=true;
		 // 创建一个NotificationManager的引用   
		notificationManager= (NotificationManager)    
            context.getSystemService(android.content.Context.NOTIFICATION_SERVICE);   
        // 定义Notification的各种属性   
		notification=new Notification(R.drawable.ic_launcher,   
                "您有新消息", System.currentTimeMillis()); 
        //FLAG_AUTO_CANCEL   该通知能被状态栏的清除按钮给清除掉
        //FLAG_NO_CLEAR      该通知不能被状态栏的清除按钮给清除掉
        //FLAG_ONGOING_EVENT 通知放置在正在运行
        //FLAG_INSISTENT     是否一直进行，比如音乐一直播放，知道用户响应
        notification.flags |= Notification.FLAG_ONGOING_EVENT; // 将此通知放到通知栏的"Ongoing"即"正在运行"组中   
       // notification.flags |= Notification.FLAG_NO_CLEAR; // 表明在点击了通知栏中的"清除通知"后，此通知不清除，经常与FLAG_ONGOING_EVENT一起使用   
       // notification.flags |= Notification.FLAG_SHOW_LIGHTS;  
        notification.defaults = Notification.DEFAULT_SOUND;  
        //DEFAULT_ALL     使用所有默认值，比如声音，震动，闪屏等等
        //DEFAULT_LIGHTS  使用默认闪光提示
        //DEFAULT_SOUNDS  使用默认提示声音
        //DEFAULT_VIBRATE 使用默认手机震动，需加上<uses-permission android:name="android.permission.VIBRATE" />权限
       // notification.defaults = Notification.DEFAULT_LIGHTS; 
        //叠加效果常量
        //notification.defaults=Notification.DEFAULT_LIGHTS|Notification.DEFAULT_SOUND;
        notification.ledARGB = Color.BLUE;   
        notification.ledOnMS =5000; //闪光时间，毫秒
         
        // 设置通知的事件消息   
        CharSequence contentTitle ="您有新消息"; // 通知栏标题   
        CharSequence contentText ="有新消息 打开看看"; // 通知栏内容   
       // Intent notificationIntent =new Intent(context, RestActivity.class); // 点击该通知后要跳转的Activity   
       
       
       
      //  PendingIntent contentItent = PendingIntent.getActivity(context, 0, notificationIntent, 0);  
      //  notification.setLatestEventInfo(context, contentTitle, contentText, contentItent);   
        // 把Notification传递给NotificationManager   
        notificationManager.notify(0, notification);   
    }
	public static void clearNotification(Context context){
		isShow=false;
        // 启动后删除之前我们定义的通知   
        NotificationManager notificationManager = (NotificationManager) context 
                .getSystemService(Context.NOTIFICATION_SERVICE);   
        notificationManager.cancel(0);  
 
    }

}
