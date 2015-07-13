package com.hck.getmoney.util;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.Header;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hck.kedouzq.R;
import com.hck.getmoney.data.Data;
import com.hck.getmoney.data.HttpUrls;
import com.hck.getmoney.net.JsonHttpResponseHandler;
import com.hck.getmoney.net.RequestParams;
import com.hck.getmoney.widget.Toasts;
public class UpdateManager
{
	private static final int DOWNLOAD = 1;
	private static final int DOWNLOAD_FINISH = 2;
	private File mSavePath;
	private int progress;
	private boolean cancelUpdate = false;

	private Context mContext;
	private ProgressBar mProgress;
	private Dialog mDownloadDialog;
	private String pkgName;
	private int id;
	private Handler mHandler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case DOWNLOAD:
				mProgress.setProgress(progress);
				break;
			case DOWNLOAD_FINISH:
				mDownloadDialog.dismiss();
				installApk();
				break;
			case 0:
				Toast.makeText(mContext, "网络异常 或者sdcard不可用下载失败", Toast.LENGTH_LONG).show();
				AppManager.getAppManager().AppExit(mContext);
				break;
			default:
				break;
			}
		};
	};

	public UpdateManager(Context context)
	{
		this.mContext = context;
	}
	/**
	 * 下载apk文件
	 */
	public void downloadApk(String url,int id)
	{
		this.id=id;
		View view=LayoutInflater.from(mContext).inflate(R.layout.softupdate_progress, null);
		mProgress=(ProgressBar) view.findViewById(R.id.update_progress);
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle("软件下载");
		final LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.softupdate_progress, null);
		mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
		builder.setView(v);
		builder.setCancelable(false);
		mDownloadDialog=builder.create();
		mDownloadDialog.show();
		new downloadApkThread(url).start();
		
	}
	
	private class downloadApkThread extends Thread
	{
		String urls=null;
		public downloadApkThread(String url)
		{
			this.urls=url;
		}
		@Override
		public void run()
		{
			try
			{
				// 判断SD卡是否存在，并且是否具有读写权限
				if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
				{
					// 获得存储卡的路径
					File file = Environment.getExternalStorageDirectory();
					
					URL url = new URL(urls);
					// 创建连接
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.connect();
					int length = conn.getContentLength();
					InputStream is = conn.getInputStream();
					mSavePath = new File(file+"/money/");
					if(!mSavePath.exists()){
						mSavePath.mkdir();
					}
					pkgName="/money"+System.currentTimeMillis()+".apk";
					File apkFile = new File(mSavePath+pkgName);
					FileOutputStream fos = new FileOutputStream(apkFile);
					int count = 0;
					byte buf[] = new byte[1024];
					do
					{
						int numread = is.read(buf);
						count += numread;
						progress = (int) (((float) count / length) * 100);
						mHandler.sendEmptyMessage(DOWNLOAD);
						if (numread <= 0)
						{
							mDownloadDialog.dismiss();
							mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
							break;
						}
						fos.write(buf, 0, numread);
						fos.flush();
					} while (!cancelUpdate);
					fos.close();
					is.close();
				}
				else {
					mHandler.sendEmptyMessage(0);
					return;
				}
			} catch (MalformedURLException e)
			{
				 mHandler.sendEmptyMessage(0);
				e.printStackTrace();
				Log.e("hck", "eee:"+e);
			} catch (IOException e)
			{
			   mHandler.sendEmptyMessage(0);
				e.printStackTrace();
				Log.e("hck", "IOException:"+e);
			}
			
			mDownloadDialog.dismiss();
		}
		
	};
	/**
	 * 安装APK文件
	 */
	
	public void installApk()
	{
		saveApp(id);
		File apkfile = new File(mSavePath,pkgName);
		if (!apkfile.exists())
		{
			return ;
		}
		// 通过Intent安装APK文件
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
		mContext.startActivity(i);
		id=0;
		Toasts.toast(mContext,"成功获取金币");
		Data.userBean.setAllKeDouBi(Data.userBean
				.getAllKeDouBi() + 300);
		
	}
	private void saveApp(int id) {
		Log.i("hck", "发送数据");
		RequestParams params = new RequestParams();
		params.put("id", id+ "");
		params.put("uid", Data.userBean.getId() + "");
		HttpUtil.getHttpUtil().get(HttpUrls.addUserAppP, params,
				new JsonHttpResponseHandler() {
					@Override
					public void onFailure(Throwable e, JSONObject errorResponse) {
						super.onFailure(e, errorResponse);
					}
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							JSONObject response) {
						super.onSuccess(statusCode, headers, response);
					}

					@Override
					public void onFinish(String url) {
						super.onFinish(url);
					}
				});
	}
}
