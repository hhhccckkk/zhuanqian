package com.hck.zhuanqian.ui;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.hck.zhuanqian.R;
import com.hck.zhuanqian.adpter.AppsListAdpter;
import com.hck.zhuanqian.bean.Apps;
import com.hck.zhuanqian.data.Data;
import com.hck.zhuanqian.data.HttpUrls;
import com.hck.zhuanqian.net.JsonHttpResponseHandler;
import com.hck.zhuanqian.net.RequestParams;
import com.hck.zhuanqian.util.HttpUtil;
import com.hck.zhuanqian.util.JsonUtil;
import com.hck.zhuanqian.widget.PDialog;
import com.hck.zhuanqian.widget.Toasts;

public class AppsFragment extends Fragment implements BaseMethod {
	private int page = 1;
	private List<Apps> apps;
	private ListView listView;
	private AppsListAdpter adpter;
	private boolean isResh;
	private View pb;
	private boolean isover;
	private boolean isOnclick;
	public static int index;
	public static boolean playIsOk;
	public static int num, kid;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_apps, null);
		listView = (ListView) view.findViewById(R.id.apps_list_id);
		pb = inflater.inflate(R.layout.more_item, null);
		initDatas();
		getData();
		setListener();
		return view;
	}

	public void initD(int num2, int kid2) {
		num = num2;
		kid = kid2;
	}

	@Override
	public void onResume() {
		super.onResume();
		if (isOnclick && playIsOk) {
			adpter.beans.remove(index);
			adpter.notifyDataSetChanged();
			index = 0;
			playIsOk = false;
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		playIsOk = false;
		isOnclick = false;
	}

	@Override
	public void initDatas() {

	}

	@Override
	public void initViews() {

	}

	@Override
	public void setListener() {
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Apps apps = (Apps) listView.getItemAtPosition(arg2);
				Intent intent = new Intent();
				intent.putExtra("app", apps);
				intent.setClass(AppsFragment.this.getActivity(),
						ShowOneAppActivity.class);
				intent.putExtra("num", num);
				intent.putExtra("kid", kid);
				isOnclick = true;
				startActivity(intent);

			}
		});
		listView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {

			}

			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
				if (arg1 + arg2 == arg3 && isResh && arg3 > 0 && !isover) {
					page += 1;
					getData();
				}
			}
		});
	}

	@Override
	public void getData() {
		PDialog.showDialog(this.getActivity(), "请稍等", "数据加载中");
		RequestParams params = new RequestParams();
		params.put("page", page + "");
		params.put("id", Data.userBean.getId() + "");
		HttpUtil.getHttpUtil().get(HttpUrls.getApps, params,
				new JsonHttpResponseHandler() {
					public void onFinish(String url) {
						PDialog.hidenDialog();
					};

					@Override
					public void onFailure(Throwable error, String content) {
						super.onFailure(error, content);

						Toast.makeText(AppsFragment.this.getActivity(),
								"获取数据失败", Toast.LENGTH_LONG).show();
					}

					@Override
					public void onSuccess(int statusCode, JSONObject response) {
						super.onSuccess(statusCode, response);
						boolean isok = false;
						try {
							isok = response.getBoolean("isok");
						} catch (JSONException e) {
							e.printStackTrace();
						}
						if (isok) {
							apps = new ArrayList<Apps>();
							JsonUtil.getJsonUtil().getApps(response, apps);
							setDate();
						} else {
							Toast.makeText(AppsFragment.this.getActivity(),
									"获取数据失败 请检查网络", Toast.LENGTH_LONG).show();
						}

					}
				});
	}

	@Override
	public void setDate() {
		isResh = true;
		if (adpter == null) {
			adpter = new AppsListAdpter(this.getActivity(), apps);
			listView.addFooterView(pb);
			listView.setAdapter(adpter);
		} else {
			if (apps.isEmpty()) {
				//Toasts.toast(AppsFragment.this.getActivity(), "没有更多数据了");
				pb.setVisibility(View.GONE);
				isover = true;
			} else {
				adpter.resh(apps);
			}

		}

	}
}
