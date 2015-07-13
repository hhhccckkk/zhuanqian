package com.hck.zhuanqian.util;

import org.json.JSONObject;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

import com.hck.zhuanqian.bean.InfoBean;
import com.hck.zhuanqian.data.HttpUrls;
import com.hck.zhuanqian.net.JsonHttpResponseHandler;
import com.hck.zhuanqian.net.RequestParams;
import com.hck.zhuanqian.ui.BaseActivity;
import com.hck.zhuanqian.ui.BaseAlert;

public class UpdateUtil {
	private static Context context;
	private static InfoBean bean;
	private int vison;
	public void isUpdate(Context context) {
		bean = new InfoBean();
		UpdateUtil.context = context;
		vison = MyTools.getVerCode(context);
		getInfo();
	}

	private void getInfo() {
		RequestParams params=new RequestParams();
		params.put("type", 1+"");
		HttpUtil.getHttpUtil().get(HttpUrls.getInfoP,params,
				new JsonHttpResponseHandler() {
                   @Override
                public void onFailure(Throwable error, String content) {
                	super.onFailure(error, content);
                }
                   @Override
                	public void onSuccess(int statusCode, JSONObject response) {
                		super.onSuccess(statusCode, response);
                		JsonUtil.getJsonUtil().getVison(response,bean);
                		update();
                	}
                   @Override
                	public void onFinish(String url) {
                		super.onFinish(url);
                		
                	}
				});
	}

	

	private void update() {
		UpdateManager manager = new UpdateManager(context);
		if (bean.getVirson() > vison) {
			new AlertDialogUpdate(context, (BaseAlert) context).showHelp(
					bean.getContent(), manager, bean.getUrl());
		}

	}
}
