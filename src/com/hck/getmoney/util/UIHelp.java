package com.hck.getmoney.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.hck.getmoney.ui.AboutActivity;
import com.hck.getmoney.ui.AppsFragmentActivity;
import com.hck.getmoney.ui.BaiduActivity;
import com.hck.getmoney.ui.BeiDuoActivity;
import com.hck.getmoney.ui.DLActivity;
import com.hck.getmoney.ui.DaTouNiaoActivity;
import com.hck.getmoney.ui.DianCaiActivity;
import com.hck.getmoney.ui.DuoMengActivity;
import com.hck.getmoney.ui.JiongYouActivity;
import com.hck.getmoney.ui.KeKeActivity;
import com.hck.getmoney.ui.LodingActivity;
import com.hck.getmoney.ui.MainActivity;
import com.hck.getmoney.ui.MessageActivity;
import com.hck.getmoney.ui.MyOrderActivity;
import com.hck.getmoney.ui.QiDianActivity;
import com.hck.getmoney.ui.SendPHOrderActivity;
import com.hck.getmoney.ui.SendQQOrderActivity;
import com.hck.getmoney.ui.SendZFBOrderActivity;
import com.hck.getmoney.ui.ShowTgFragment;
import com.hck.getmoney.ui.TGActivity;
import com.hck.getmoney.ui.YeGuoActivity;
import com.hck.getmoney.ui.YouMengActivity;
import com.hck.getmoney.ui.YouMiActivity;
import com.hck.getmoney.widget.Toasts;

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
