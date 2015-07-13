package com.hck.zhuanqian.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.hck.zhuanqian.ui.AboutActivity;
import com.hck.zhuanqian.ui.AppsFragmentActivity;
import com.hck.zhuanqian.ui.BaiduActivity;
import com.hck.zhuanqian.ui.BeiDuoActivity;
import com.hck.zhuanqian.ui.DLActivity;
import com.hck.zhuanqian.ui.DaTouNiaoActivity;
import com.hck.zhuanqian.ui.DianCaiActivity;
import com.hck.zhuanqian.ui.DuoMengActivity;
import com.hck.zhuanqian.ui.JiongYouActivity;
import com.hck.zhuanqian.ui.KeKeActivity;
import com.hck.zhuanqian.ui.LodingActivity;
import com.hck.zhuanqian.ui.MainActivity;
import com.hck.zhuanqian.ui.MessageActivity;
import com.hck.zhuanqian.ui.MyOrderActivity;
import com.hck.zhuanqian.ui.QiDianActivity;
import com.hck.zhuanqian.ui.SendPHOrderActivity;
import com.hck.zhuanqian.ui.SendQQOrderActivity;
import com.hck.zhuanqian.ui.SendZFBOrderActivity;
import com.hck.zhuanqian.ui.ShowTgFragment;
import com.hck.zhuanqian.ui.TGActivity;
import com.hck.zhuanqian.ui.YeGuoActivity;
import com.hck.zhuanqian.ui.YouMengActivity;
import com.hck.zhuanqian.ui.YouMiActivity;
import com.hck.zhuanqian.widget.Toasts;

/**
 * activity之间的跳转
 * 
 * @author hck
 * 
 */
public class UIHelp {
	private static Intent intent;

	public static void startMainActivity(Context context) {
		intent = new Intent();
		intent.setClass(context, MainActivity.class);
		context.startActivity(intent);
		((LodingActivity) context).finish();
		destroyIntent();
	}

	public static void startShowMyOrders(Context context, int tag) {
		intent = new Intent();
		intent.setClass(context, MyOrderActivity.class);
		intent.putExtra("tag", tag);
		context.startActivity(intent);
		destroyIntent();
	}

	public static void startGetMoney(Context context, int type, int kid,
			int num, String neirong) {
		intent = new Intent();
		intent.putExtra("kid", kid);
		intent.putExtra("num", num);
		intent.putExtra("neirong", neirong);
		switch (type) {
		case 1:
			intent.setClass(context, AppsFragmentActivity.class);
			break;
		case 2:
			intent.setClass(context, DLActivity.class);
			break;
		case 3:
			//intent.setClass(context, YGActivity.class);
			break;
		case 4:
			//intent.setClass(context, WPActivity.class);
			break;
		case 5:
			intent.setClass(context, DuoMengActivity.class);
			break;
		case 6:
			//intent.setClass(context, ZMActivity.class);
			break;
		case 7:
			intent.setClass(context, YeGuoActivity.class);
			break;
		case 8:
			intent.setClass(context, JiongYouActivity.class);
			break;
		case 9:
			intent.setClass(context, BeiDuoActivity.class);
			break;
		case 10:
			intent.setClass(context, DianCaiActivity.class);
			break;
		case 11:
			intent.setClass(context, QiDianActivity.class);
			break;
		case 12:
			intent.setClass(context, DaTouNiaoActivity.class);
			break;
		case 13:
			intent.setClass(context, BaiduActivity.class);
			break;
		case 14:
			intent.setClass(context, KeKeActivity.class);
			break;
		case 15:
			intent.setClass(context, YouMiActivity.class);
			break;
		case 16:
			intent.setClass(context, YouMengActivity.class);
			break;
		default:
			Toasts.toast(context, "该区还未开放");
			break;
		}
		try {
			context.startActivity(intent);
		} catch (Exception e) {
			Toasts.toast(context, "该区还未开放");
			Log.e("hck", "" + e.toString());
		}

	}

	public static void startSendorderActivity(Context context, int type) {
		intent = new Intent();
		intent.putExtra("type", type);
		intent.setClass(context, SendQQOrderActivity.class);
		context.startActivity(intent);
		destroyIntent();
	}

	public static void startZFBActivity(Context context, int type) {
		intent = new Intent();
		intent.putExtra("type", type);
		intent.setClass(context, SendZFBOrderActivity.class);
		context.startActivity(intent);
		destroyIntent();
	}

	public static void startPHActivity(Context context, int type) {
		intent = new Intent();
		intent.putExtra("type", type);
		intent.setClass(context, SendPHOrderActivity.class);
		context.startActivity(intent);
		destroyIntent();
	}

	public static void startMessageActivity(Context context) {
		intent = new Intent();
		intent.setClass(context, MessageActivity.class);
		context.startActivity(intent);
		destroyIntent();
	}

	public static void startTGActivity(Context context) {
		intent = new Intent();
		intent.setClass(context, TGActivity.class);
		context.startActivity(intent);
		destroyIntent();
	}

	public static void startAboutActivity(Context context) {
		intent = new Intent();
		intent.setClass(context, AboutActivity.class);
		context.startActivity(intent);
		destroyIntent();
	}

	public static void startShowTgActivity(Context context) {
		intent = new Intent();
		intent.setClass(context, ShowTgFragment.class);
		context.startActivity(intent);
		destroyIntent();
	}

	private static void destroyIntent() {
		UIHelp.intent = null;
	}
}
