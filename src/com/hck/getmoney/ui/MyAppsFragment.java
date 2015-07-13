package com.hck.getmoney.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hck.kedouzq.R;
import com.hck.getmoney.adpter.MyDownAppListAdpter;
import com.hck.getmoney.bean.Userapp;
import com.hck.getmoney.data.Data;
import com.hck.getmoney.data.HttpUrls;
import com.hck.getmoney.net.JsonHttpResponseHandler;
import com.hck.getmoney.net.RequestParams;
import com.hck.getmoney.util.HttpUtil;
import com.hck.getmoney.util.JsonUtil;
import com.hck.getmoney.widget.AlertDialogs;
import com.hck.getmoney.widget.PDialog;
import com.hck.getmoney.widget.Toasts;

public class MyAppsFragment extends Fragment implements BaseMethod {
	private List<Userapp> apps;
	private ListView listView;
	private int page = 1;
	private MyDownAppListAdpter adpter;
	public static int num;
	public static int kid;
	private int tag;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_my_apps, null);
		listView = (ListView) view.findViewById(R.id.my_apps_list_id);
		setListener();
		apps = new ArrayList<Userapp>();
		return view;
	}

	public void initD(int num2, int kid2) {
		num = num2;
		kid = kid2;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void initDatas() {

	}

	@Override
	public void initViews() {

	}

	@Override
	public void setListener() {

	}

	@Override
	public void getData() {
		apps.clear();
		adpter = null;
		RequestParams params = new RequestParams();
		params.put("id", Data.userBean.getId() + "");
		params.put("page", page + "");
		HttpUtil.getHttpUtil().get(HttpUrls.getUserApps, params,
				new JsonHttpResponseHandler() {
					@Override
					public void onFailure(Throwable e, JSONObject errorResponse) {
						super.onFailure(e, errorResponse);
					}

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							JSONObject response) {
						super.onSuccess(statusCode, headers, response);
						boolean b = false;
						try {
							b = response.getBoolean("isok");
						} catch (JSONException e) {
							e.printStackTrace();
						}
						if (b) {
							JsonUtil.getJsonUtil().getMyApps(response, apps);
							setDate();
						} else {
							Toasts.toast(MyAppsFragment.this.getActivity(),
									"没有数据");
						}
					}

					@Override
					public void onFinish(String url) {
						super.onFinish(url);
					}
				});
	}

	@Override
	public void setDate() {

		adpter = new MyDownAppListAdpter(this.getActivity(), apps);
		listView.setAdapter(adpter);

	}

	public void startApp(Userapp apUserapp) {
		try {
			ComponentName componentName = new ComponentName(apUserapp.getBm(),
					apUserapp.getRk());
			Intent intent = new Intent();
			intent.setComponent(componentName);
			startActivity(intent);
			if (apUserapp.getIscanqd() == 1) {
				qd(apUserapp);
			}
		} catch (Exception e) {
			AlertDialogs.alert(this.getActivity(), "",
					"您已经卸载了该应用 无法完成签到  多次不签到者会被封号处理 务必签到后 再卸载不喜歡的應用",true);
		}

	}

	private void savePoint(final Userapp aUserapp) {
		final int point = aUserapp.getPrice();
		RequestParams params = new RequestParams();
		params.put("kindid", kid + "");
		params.put("num", 10000 + "");
		params.put("type", "蝌蚪广告");
		params.put("uid", Data.userBean.getId() + "");
		params.put("money", aUserapp.getPrice() + "");
		HttpUtil.getHttpUtil().get(HttpUrls.addMoneyP, params,
				new JsonHttpResponseHandler() {
					@Override
					public void onFinish(String url) {
						super.onFinish(url);
						PDialog.hidenDialog();
					}

					@Override
					public void onFailure(Throwable error, String content) {
						super.onFailure(error, content);
						Toasts.toast(MyAppsFragment.this.getActivity(),
								"网络异常 增加蝌蚪币失败 ");
					}

					@Override
					public void onSuccess(int statusCode, JSONObject response) {
						super.onSuccess(statusCode, response);
						try {
							boolean isOk = response.getBoolean("isok");
							if (isOk) {
								Toasts.toast(MyAppsFragment.this.getActivity(),
										"获取蝌蚪币: " + point + "个");
								Data.userBean.setAllKeDouBi(Data.userBean
										.getAllKeDouBi() + point);
								adpter.beans.remove(aUserapp);
								adpter.notifyDataSetChanged();

							} else {
								Toasts.toast(MyAppsFragment.this.getActivity(),
										"网络异常 增加蝌蚪币失败 ");
							}
						} catch (Exception e) {
							Toasts.toast(MyAppsFragment.this.getActivity(),
									"网络异常 增加蝌蚪币失败 ");
						}

					}
				});
	}

	private void qd(final Userapp app) {
		RequestParams params = new RequestParams();
		params.put("appId", app.getId() + "");
		HttpUtil.getHttpUtil().get(HttpUrls.qd, params,
				new JsonHttpResponseHandler() {
					@Override
					public void onFinish(String url) {
						super.onFinish(url);
						Log.i("hck", url);
					}

					@Override
					public void onSuccess(int statusCode, JSONObject response) {
						super.onSuccess(statusCode, response);
						try {
							boolean isok = response.getBoolean("isok");
							if (isok) {
								savePoint(app);
							} else {
								Toasts.toast(MyAppsFragment.this.getActivity(),
										"签到失败 请检查网络");
							}
						} catch (JSONException e) {
							e.printStackTrace();
							Toasts.toast(MyAppsFragment.this.getActivity(),
									"签到失败 请检查网络");
						}

					}

					@Override
					public void onFailure(Throwable e, JSONObject errorResponse) {
						super.onFailure(e, errorResponse);
						Toasts.toast(MyAppsFragment.this.getActivity(),
								"签到失败 请检查网络");
					}
				});
	}

}
