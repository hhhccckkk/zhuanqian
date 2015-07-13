package com.hck.zhuanqian.util;

import org.apache.http.client.params.HttpClientParams;

import com.hck.zhuanqian.net.BinaryHttpResponseHandler;
import com.hck.zhuanqian.net.HCKHttpClient;
import com.hck.zhuanqian.net.HCKHttpResponseHandler;
import com.hck.zhuanqian.net.JsonHttpResponseHandler;
import com.hck.zhuanqian.net.RequestParams;

public class HttpUtil {
	private HttpUtil(){}
	private static HttpUtil httpUtil;
	
	private static HCKHttpClient client;

	public static HttpUtil getHttpUtil()
	{
		if (httpUtil==null) {
			httpUtil=new HttpUtil();
			client=new HCKHttpClient();
		}
		return httpUtil;
	}

	public  void get(String urlString, HCKHttpResponseHandler res) {
		client.get(urlString, res);
	}

	public  void get(String urlString, RequestParams params,
			HCKHttpResponseHandler res) {
		client.get(urlString, params, res);
	}

	public  void get(String urlString, JsonHttpResponseHandler res) {
		client.get(urlString, res);
	}

	public  void get(String urlString, RequestParams params,
			JsonHttpResponseHandler res) {
		client.get(urlString, params, res);
	}

	public  void get(String uString, BinaryHttpResponseHandler bHandler) {
		client.get(uString, bHandler);
	}

	public  void poat(String urlString, HCKHttpResponseHandler res) {
		client.get(urlString, res);
	}

	public  void post(String urlString, RequestParams params,
			HCKHttpResponseHandler res) {
		client.post(urlString, params, res);
	}

	

	public  HCKHttpClient getClient() {
		return client;
	}

}
