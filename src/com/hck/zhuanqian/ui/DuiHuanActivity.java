package com.hck.zhuanqian.ui;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.hck.zhuanqian.R;
import com.hck.zhuanqian.adpter.DHListAdpter;
import com.hck.zhuanqian.bean.OrderBean;
import com.hck.zhuanqian.data.Data;
import com.hck.zhuanqian.data.HttpUrls;
import com.hck.zhuanqian.net.JsonHttpResponseHandler;
import com.hck.zhuanqian.util.HttpUtil;
import com.hck.zhuanqian.util.JsonUtil;
import com.hck.zhuanqian.util.UIHelp;
import com.hck.zhuanqian.widget.AlertDialogs;
import com.hck.zhuanqian.widget.PDialog;

public class DuiHuanActivity extends BaseActivity implements BaseMethod
		 {
	private TextView titleTextView;
	private DHListAdpter adpter;
	private List<OrderBean> beans;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_duihuang);
		initViews();
		initDatas();
		setListener();
		getData();
		setDate();
	}

	@Override
	public void initDatas() {
		beans = new ArrayList<OrderBean>();
		

	}

	@Override
	public void initViews() {
		titleTextView = (TextView) findViewById(R.id.title_text);
		listView = (ListView) findViewById(R.id.dh_list);
		titleTextView.setTextSize(18);

	}

	@Override
	protected void onResume() {
		titleTextView.setText("我的金币: "+Data.userBean.getAllKeDouBi()+"个");
		titleTextView.setTextSize(20);
		super.onResume();
	}

	public void startSendOrderQQActivity(View view) {

		if (!isMoneyOkQQ()) {
			AlertDialogs.alert(this, "", "兑换Q币至少需要1千个金币   下载几个应用就可以了",false,null,0);
		} else {
			UIHelp.startSendorderActivity(DuiHuanActivity.this, 1);
		}

	}

	public void startSendOrderZFBActivity(View view) {
		if (!isMoneyOkZFB()) {
			AlertDialogs.alert(this, "", "兑换支付宝 金币至少需要2千个",false);
		} else {
			UIHelp.startZFBActivity(DuiHuanActivity.this, 2);
		}
	}

	public void startSendOrderPHBActivity(View view) {
		if (!isMoneyOkPH()) {
			AlertDialogs.alert(this, "", "兑换话费 金币至少需要2万个",false);
		} else {
			UIHelp.startPHActivity(DuiHuanActivity.this, 3);
		}
	}

	private boolean isMoneyOkQQ() {
		if (Data.userBean.getAllKeDouBi() < 1000) {
			return false;
		} else {
			return true;
		}
	}

	private boolean isMoneyOkZFB() {
		if (Data.userBean.getAllKeDouBi() < 2000) {
			return false;
		} else {
			return true;
		}
	}

	private boolean isMoneyOkPH() {
		if (Data.userBean.getAllKeDouBi() < 20000) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void setListener() {
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

			}
		});
	}

	@Override
	public void getData() {
		PDialog.showDialog(this, "正在加载数据", "请稍等");
		HttpUtil.getHttpUtil().get(HttpUrls.getOrdersP,
				new JsonHttpResponseHandler() {
					@Override
					public void onFinish(String url) {
						super.onFinish(url);
						PDialog.hidenDialog();
					}

					@Override
					public void onFailure(Throwable error, String content) {
						super.onFailure(error, content);
					}

					@Override
					public void onSuccess(int statusCode, JSONObject response) {
						super.onSuccess(statusCode, response);
						JsonUtil.getJsonUtil().getNowOrders(response, beans);
						setDate();
					}
				});
	}

	@Override
	public void setDate() {
		adpter = new DHListAdpter(this, beans);
		listView.setAdapter(adpter);
	}


	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) { // 按返回键时候，提示用户是否退出
		showExitDialog();
		return false;
	}

}
