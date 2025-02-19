package com.hck.zhuanqian.ui;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hck.zhuanqian.R;
import com.hck.zhuanqian.adpter.HomeActivityListAdpter;
import com.hck.zhuanqian.bean.KindBean;
import com.hck.zhuanqian.data.Data;
import com.hck.zhuanqian.data.HttpUrls;
import com.hck.zhuanqian.net.JsonHttpResponseHandler;
import com.hck.zhuanqian.util.AppManager;
import com.hck.zhuanqian.util.HttpUtil;
import com.hck.zhuanqian.util.JsonUtil;
import com.hck.zhuanqian.util.MyBroadCast;
import com.hck.zhuanqian.util.MyPreferences;
import com.hck.zhuanqian.util.MyTools;
import com.hck.zhuanqian.util.UIHelp;
import com.hck.zhuanqian.util.UpdateUtil;
import com.hck.zhuanqian.widget.AlertDialogs;
import com.hck.zhuanqian.widget.AlertDialogs.OneBtOnclick;
import com.hck.zhuanqian.widget.CustomAlertDialog;
import com.hck.zhuanqian.widget.PDialog;
import com.hck.zhuanqian.widget.Toasts;

public class HomeActivity extends BaseActivity implements BaseMethod {
	private ListView listView;
	private HomeActivityListAdpter adpter;
	private List<KindBean> kindBeans;
	private boolean isFirst;
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
		LinearLayout adLayout = (LinearLayout) findViewById(R.id.tenxunAd);
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
		newTextView = (TextView) findViewById(R.id.home_new);
		newTextView.setText(Data.news);
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
	}

	@Override
	public void setDate() {
		adpter = new HomeActivityListAdpter(kindBeans, this);
		listView.setAdapter(adpter);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) { // 按返回键时候，提示用户是否退出
		showExitDialog();
		return false;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

	
}
