package com.hck.getmoney.ui;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ListView;
import android.widget.TextView;

import com.hck.kedouzq.R;
import com.hck.getmoney.adpter.PHListAdpter;
import com.hck.getmoney.bean.PaiHangBean;
import com.hck.getmoney.data.HttpUrls;
import com.hck.getmoney.net.JsonHttpResponseHandler;
import com.hck.getmoney.util.AppManager;
import com.hck.getmoney.util.HttpUtil;
import com.hck.getmoney.util.JsonUtil;
import com.hck.getmoney.widget.AlertDialogs;
import com.hck.getmoney.widget.PDialog;
import com.hck.getmoney.widget.Toasts;

public class MoreActivity extends BaseActivity implements BaseMethod, BaseAlert {

	private List<PaiHangBean> beans;
	private PHListAdpter adpter;
	private ListView listView;
	private TextView titleTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_other);
		initViews();
		initDatas();
		getData();
		
	}

	@Override
	public void initDatas() {
		beans = new ArrayList<PaiHangBean>();
	}

	@Override
	public void initViews() {
		listView = (ListView) findViewById(R.id.ph_list);
		titleTextView = (TextView) findViewById(R.id.title_text);
	}

	@Override
	public void setListener() {

	}

	@Override
	public void getData() {
		PDialog.showDialog(this, "正在获取数据", "请稍等");
		HttpUtil.getHttpUtil().get(HttpUrls.getMoneyRankP,
				new JsonHttpResponseHandler() {
					public void onFailure(Throwable e,
							org.json.JSONObject errorResponse) {
						Toasts.toast(MoreActivity.this, "获取数据失败 ,请检查网络");
					};

					@Override
					public void onSuccess(int statusCode, JSONObject response) {
						super.onSuccess(statusCode, response);
						try {
							isOk = response.getBoolean("isok");
							if (isOk) {
								JsonUtil.getJsonUtil().getPH(response, beans);
								setDate();
							} else {
								Toasts.toast(MoreActivity.this, "获取数据失败 ,请检查网络");
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onFinish(String url) {
						super.onFinish(url);
						
						PDialog.hidenDialog();

					}
				});
	}

	@Override
	public void setDate() {
		adpter = new PHListAdpter(beans, this);
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
		finish();
		System.gc();
	}

	@Override
	public void doSometing(String s) {

	}

}
