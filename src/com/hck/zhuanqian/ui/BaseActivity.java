package com.hck.zhuanqian.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.LinearLayout;

import com.hck.zhuanqian.data.Data;
import com.hck.zhuanqian.net.RequestParams;
import com.hck.zhuanqian.util.AppManager;
import com.hck.zhuanqian.widget.AlertDialogs;
import com.hck.zhuanqian.widget.CustomAlertDialog;
import com.qq.e.ads.AdListener;
import com.qq.e.ads.AdRequest;
import com.qq.e.ads.AdSize;
import com.qq.e.ads.AdView;
import com.umeng.analytics.MobclickAgent;

/**
 * 
 *  
 * 
 */
public class BaseActivity extends Activity {
	public RequestParams params; // http请求使用的参数对变量
	public boolean isOk; // 用于http请求后，返回的json数据的值的保存
	public String nt = "";
	public int money;
	private AdView bannerAD;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppManager.getAppManager().addActivity(this); // 将新创建的activity加入栈
		MobclickAgent.onResume(this); // 友盟统计

	}

	public void nt() {
		try {
			if (!getAirplaneMode() && "开".equals(Data.news)) {
				AlertDialogs.alert(this, "",
						"友情提示：试玩过程请开始手机飞行模式，需要使用手机号注册账号时候，再打开，注册后"
								+ " 请再开启飞行模式" + " 这样可以防止您自己的误操作，误点击，购买不需要的服务。",
						true);
			}
		} catch (Exception e) {
		}

	}

	public void nt2() {
		try {
			if (!getAirplaneMode()) {
				AlertDialogs.alert(this, "",
						"友情提示：该区请开启手机飞行模式，需要使用手机号注册账号时候，再打开，注册后" + " 请再开启飞行模式"
								+ " 这样可以防止您自己的误操作，误点击，购买不需要的服务。", true);
			}
		} catch (Exception e) {
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		isnetOk();
	}

	private void isnetOk() {
		ConnectivityManager con = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
		boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.isConnectedOrConnecting();
		boolean internet = con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.isConnectedOrConnecting();
		if (wifi | internet) {
		} else {
			AlertDialogs.alert(this, "", "亲爱的，没网络没法试玩应用哦，请在飞行模式下单独手动打开wifi",
					false);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this); // 友盟统计
	}

	public boolean getAirplaneMode() {
		int isAirplaneMode = Settings.System.getInt(this.getContentResolver(),
				Settings.System.AIRPLANE_MODE_ON, 0);
		return (isAirplaneMode == 1) ? true : false;
	}

	public int hashYQM(int point) {
		try {
			String tjmString = Data.userBean.getShangjia();
			if (tjmString != null && !"".equals(tjmString)) {
				point = (int) (point + point * 0.1);
				nt = "共获取金币" + point + "个 填入邀请码奖励" + (int) (point * 0.1)
						+ "个金币";
			} else {
				nt = "共获取金币" + point + "个  您尚未填入邀请码 不能获取额外10%金币加成";
			}
		} catch (Exception e) {
			return point;
		}

		return point;
	}

	public void showBannerAD(LinearLayout view) {
	
		bannerAD = new AdView(this, AdSize.BANNER, "1104666314",
				"3060505498153309");
		this.bannerAD.setAdListener(new AdListener() {
	          
	          @Override
	          public void onNoAd() {
	            Log.i("hck","Banner AD LoadFail");
	          }
	          
	          @Override
	          public void onBannerClosed() {
	            //仅在开启广点通广告关闭按钮时生效
	            Log.i("hck:","Banner AD Closed");
	          }
	          
	          @Override
	          public void onAdReceiv() {
	            Log.i("hck:","Banner AD Ready to show");
	          }
	          
	          @Override
	          public void onAdExposure() {
	            Log.i("hck:","Banner AD Exposured");
	          }
	          
	          @Override
	          public void onAdClicked() {
	          }

	          @Override
	          public void onNoAd(int arg0) {
	            
	          }
	        });
		view.removeAllViews();
		view.addView(bannerAD);
		bannerAD.fetchAd(new AdRequest());
	}
	public void showExitDialog() {
		if (isFinishing()) {
			return;
		}

		CustomAlertDialog dialog = new CustomAlertDialog(this);
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setLeftText("退出");
		dialog.setRightText("给好评");
		dialog.setTitle("提示");
		dialog.setMessage("这么好的软件，必需给个好评");
		dialog.setOnLeftListener(new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				AppManager.getAppManager().AppExit(BaseActivity.this);
				finish();
				System.gc();
			}
		});

		dialog.setOnRightListener(new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				startPinLunActivity();
			}
		});
		if (!isFinishing() && dialog != null) {
			dialog.show();
		}

	}

	public void startPinLunActivity() {
		try {
			Uri uri = Uri.parse("market://details?id=" + "com.hck.kedouzq");
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		} catch (Exception e) {
		}

	}


}
