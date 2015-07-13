package com.hck.getmoney.ui;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hck.getmoney.adpter.HomeActivityListAdpter;
import com.hck.getmoney.bean.KindBean;
import com.hck.getmoney.data.Data;
import com.hck.getmoney.data.HttpUrls;
import com.hck.getmoney.net.JsonHttpResponseHandler;
import com.hck.getmoney.util.AppManager;
import com.hck.getmoney.util.HttpUtil;
import com.hck.getmoney.util.JsonUtil;
import com.hck.getmoney.util.MyBroadCast;
import com.hck.getmoney.util.MyPreferences;
import com.hck.getmoney.util.MyTools;
import com.hck.getmoney.util.UIHelp;
import com.hck.getmoney.util.UpdateUtil;
import com.hck.getmoney.widget.AlertDialogs;
import com.hck.getmoney.widget.AlertDialogs.OneBtOnclick;
import com.hck.getmoney.widget.PDialog;
import com.hck.getmoney.widget.Toasts;
import com.hck.kedouzq.R;

public class HomeActivity extends BaseActivity implements BaseMethod, BaseAlert {
	private ListView listView;
	private HomeActivityListAdpter adpter;
	private int point;
	private TextView textView;
	private List<KindBean> kindBeans;
	private boolean isFirst;
	private MyBroadCast cast;
	private LinearLayout ad;
	private View view; // 每个页面的view
	private int postion;
	private TextView newTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		AppManager.getAppManager().addActivity(this);
		isFinishd();
		initViews();
		initDatas();
		setListener();
		getData();
		new UpdateUtil().isUpdate(this);
		isFirst = MyPreferences.getBoolean("isfirst", true);
		if (isFirst) {
			showXinShou();
		}
		MyPreferences.saveBoolean("isfirst", false);
		LinearLayout adLayout =(LinearLayout) findViewById(R.id.tenxunAd);
		showBannerAD(adLayout);

	}

	private void isFinishd() {
		if (Data.userBean == null || Data.userBean.getName() == null) {
			Toasts.toast(this, "系统异常 请重试");
			AppManager.getAppManager().addActivity(this);
			finish();
		}
	}

	public int dip2px(float dpValue) {
		final float scale = getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	// 获取ApiKey
	public static String getMetaValue(Context context, String metaKey) {
		Bundle metaData = null;
		String apiKey = null;
		if (context == null || metaKey == null) {
			return null;
		}
		try {
			ApplicationInfo ai = context.getPackageManager()
					.getApplicationInfo(context.getPackageName(),
							PackageManager.GET_META_DATA);
			if (null != ai) {
				metaData = ai.metaData;
			}
			if (null != metaData) {
				apiKey = metaData.getString(metaKey);
			}
		} catch (NameNotFoundException e) {

		}
		return apiKey;
	}

	private void zhuce() {
		cast = new MyBroadCast();
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.intent.action.PACKAGE_ADDED");
		filter.addAction("android.intent.action.PACKAGE_REMOVED");
		filter.addDataScheme("package");
		this.registerReceiver(cast, filter);

	}

	@Override
	public void initDatas() {
		kindBeans = new ArrayList<KindBean>();

	}

	@Override
	protected void onResume() {
		super.onResume();

		if (MyTools.getVerCode(this) < Data.infoBean.getVirson()) {
			new UpdateUtil().isUpdate(this);
		}

	}

	private void showXinShou() {
		if (Data.userBean.isXinShou()) {
			AlertDialogs.alert(this, "欢迎您的到来，新手请先看 新手帮助哦",
					"欢迎您的到来，新手请先看 新手帮助哦，1000金币=1元", false, new OneBtOnclick() {

						@Override
						public void callBack(int tag) {
							startActivity(new Intent(HomeActivity.this,
									XinShouBiKanActivity.class));
						}
					}, 1);
		}
	}

	@Override
	public void initViews() {
		listView = (ListView) findViewById(R.id.home_list);
		textView = (TextView) findViewById(R.id.title_text);
		ad = (LinearLayout) findViewById(R.id.ad_lin);
		view = getLayoutInflater().inflate(R.layout.home_item1, null);
		view = getLayoutInflater().inflate(R.layout.home_item2, null);
		newTextView = (TextView) findViewById(R.id.home_new);
		newTextView.setText(Data.news);
		// handler.post(thread);
	}

	@Override
	public void setListener() {
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (kindBeans.get(arg2).getIsOk() == 0) {
					Toasts.toast(HomeActivity.this, "该区暂未开放");
				} else {
					UIHelp.startGetMoney(HomeActivity.this, kindBeans.get(arg2)
							.getAid(), kindBeans.get(arg2).getId(), kindBeans
							.get(arg2).getNum(), kindBeans.get(arg2)
							.getNeirong());
				}
			}
		});

	}

	@Override
	public void getData() {
		if (isFinishing()) {
			return;

		}
		PDialog.showDialog(this, "加载数据", "请稍等");
		HttpUtil.getHttpUtil().get(HttpUrls.getKind,
				new JsonHttpResponseHandler() {
					public void onFinish(String url) {
						PDialog.hidenDialog();
					};

					@Override
					public void onFailure(Throwable error, String content) {
						super.onFailure(error, content);

						Toast.makeText(HomeActivity.this, "获取数据失败",
								Toast.LENGTH_LONG).show();
					}

					@Override
					public void onSuccess(int statusCode, JSONObject response) {
						super.onSuccess(statusCode, response);
						JsonUtil.getJsonUtil().getKindData(kindBeans, response);
						setDate();
					}
				});
		HttpUtil.getHttpUtil().get(HttpUrls.getNewsP,
				new JsonHttpResponseHandler() {
					@Override
					public void onFinish(String url) {
						super.onFinish(url);
					}

					@Override
					public void onFailure(Throwable error, String content) {
						super.onFailure(error, content);
					}

					@Override
					public void onSuccess(int statusCode, JSONObject response) {
						super.onSuccess(statusCode, response);
						try {
							isOk = response.getBoolean("isok");
							if (isOk) {
								JsonUtil.getJsonUtil().getNews(response);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}

					}
				});
	}

	@Override
	public void setDate() {
		adpter = new HomeActivityListAdpter(kindBeans, this);
		listView.setAdapter(adpter);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) { // 按返回键时候，提示用户是否退出
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AlertDialogs.alertDialog(this, this, "确定要退出吗？", "按错", "闪人",
					"no_exit", "exit2");
			return true;
		}
		return false;
	}

	@Override
	public void doLeftButton(String value) {

	}

	@Override
	public void doRightButton(String value) {

		AppManager.getAppManager().AppExit(this);
		finish();
		System.gc();

	}

	@Override
	public void doSometing(String s) {
		finish();
		System.gc();
	}

	@Override
	protected void onDestroy() {
		if (cast != null) {
			this.unregisterReceiver(cast);
		}
		super.onDestroy();

	}

}
