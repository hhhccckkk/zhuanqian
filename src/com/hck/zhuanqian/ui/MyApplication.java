package com.hck.zhuanqian.ui;

import android.app.Application;

import com.hck.zhuanqian.data.HttpUrls;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class MyApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		initImagerLoder();
		a();
	}

	private void initImagerLoder() {
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheInMemory(true) // 1.8.6包使用时候，括号里面传入参数true
				.cacheOnDisc(true) // 1.8.6包使用时候，括号里面传入参数true
				.build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this).defaultDisplayImageOptions(defaultOptions)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		ImageLoader.getInstance().init(config);
	}

//	public native String HCK();
//
//	static {
//		System.loadLibrary("hck");
//	}

	private void a() {
		//HttpUrls.mainHost = HCK();
		HttpUrls.initURLS();
	}

}
