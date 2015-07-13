package com.hck.zhuanqian.ui;
import java.util.ArrayList;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ads8.view.AdView;
import com.hck.zhuanqian.R;
import com.hck.zhuanqian.adpter.AppsPagerAdapter;
import com.hck.zhuanqian.bean.Userapp;

public class AppsFragmentActivity extends BaseFragment implements BaseMethod {
	private TextView titleTextView;
	MyAppsFragment myAppsFragment;
	private static int num,kid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_app);
		initDatas();
		views = new ArrayList<Fragment>();
		initViews();
		InitImageView();
		setListener();
		loadBaiDuAD();
		initPagerViewer();

	}

	@Override
	public void initDatas() {
            num=getIntent().getIntExtra("num", 5);
            kid=getIntent().getIntExtra("kid", 1);
	}

	@Override
	public void initViews() {

		cursor = (ImageView) findViewById(R.id.cursor);
		textView1 = (TextView) findViewById(R.id.apps);
		textView2 = (TextView) findViewById(R.id.qd);
		titleTextView=(TextView) findViewById(R.id.title_text);
		titleTextView.setText("极品应用试玩");
		initPageView();
	}

	private void InitImageView() {
		cursor = (ImageView) findViewById(R.id.cursor);
		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.roller)
				.getWidth();
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;
		offset = (screenW / 2 - bmpW) / 2;
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		cursor.setImageMatrix(matrix);
	}

	private void initPageView() {

		AppsFragment classFragment = new AppsFragment();
		views.add(classFragment);
		myAppsFragment= new MyAppsFragment();
		views.add(myAppsFragment);
		classFragment.initD(num, kid);
		myAppsFragment.initD(num, kid);
		
	}

	@Override
	public void setListener() {
		textView1.setOnClickListener(new MyOnClickListener(0));
		textView2.setOnClickListener(new MyOnClickListener(1));
	}

	@Override
	public void getData() {

	}

	@Override
	public void setDate() {

	}

	private void initPagerViewer() {
		pager = (ViewPager) findViewById(R.id.viewpage);
		pager.setAdapter(new AppsPagerAdapter(getSupportFragmentManager(),
				views));
		pager.setCurrentItem(0);
		pager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	public class MyOnPageChangeListener implements OnPageChangeListener {
		int one = offset * 2 + bmpW;

		@Override
		public void onPageSelected(int arg0) {
			Animation animation = null;
			switch (arg0) {
			case 0:
				animation = new TranslateAnimation(one, 0, 0, 0);
				 textView1.setTextColor(getResources().getColor(R.color.whilt));
				 textView2.setTextColor(getResources().getColor(R.color.black));
				break;
			case 1:
				animation = new TranslateAnimation(offset, one, 0, 0);
				 textView1.setTextColor(getResources().getColor(R.color.black));
				 textView2.setTextColor(getResources().getColor(R.color.whilt));
				myAppsFragment.getData();
				break;
			}
			animation.setFillAfter(true);
			animation.setDuration(300);
			cursor.startAnimation(animation);
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}
	}

	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			pager.setCurrentItem(index);
		}
	};
	public void startApp(Userapp aUserapp)
	{
		myAppsFragment.startApp(aUserapp);
	}
	private void loadBaiDuAD() {
	LinearLayout	ad = (LinearLayout) findViewById(R.id.ad_lin);
		AdView adView = new AdView(this);
		ad.addView(adView);
	}
}
