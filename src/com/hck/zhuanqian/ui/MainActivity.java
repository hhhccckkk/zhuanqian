package com.hck.zhuanqian.ui;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.hck.zhuanqian.R;
import com.hck.zhuanqian.util.AppManager;

public class MainActivity extends TabActivity implements
		OnCheckedChangeListener {
	private TabHost tabHost;
	private TabSpec tabSpec;
	private static final String HOME = "home";
	private static final String DH = "duihuan";
	private static final String USER = "user";
	private static final String GAME = "game";
	private static final String TG = "tuiguang";
	private RadioGroup rGroup;
	public static MainActivity mainActivity;
	private RadioButton radioButton, radioButton2, radioButton3, radioButton4,
			radioButton5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppManager.getAppManager().addActivity(this);
		setContentView(R.layout.activity_main);
		initViews();
		initDatas();
		setListener();

	}

	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.home_id:
			tabHost.setCurrentTab(0);
			break;
		case R.id.game_id:
			tabHost.setCurrentTab(1);
			break;
		case R.id.duihuan_id:
			tabHost.setCurrentTab(2);
			break;
		case R.id.tuiguang_id:
			tabHost.setCurrentTab(3);
			break;
		case R.id.user_id:
			tabHost.setCurrentTab(4);
			break;
		}
	}

	private void setListener() {
		rGroup.setOnCheckedChangeListener(this);
	}

	public void initDatas() {

		tabSpec = tabHost.newTabSpec(HOME).setIndicator(HOME)
				.setContent(new Intent(this, HomeActivity.class));
		tabHost.addTab(tabSpec);

		tabSpec = tabHost.newTabSpec(GAME).setIndicator(GAME)
				.setContent(new Intent(this, GameActivity.class));
		tabHost.addTab(tabSpec);

		tabSpec = tabHost.newTabSpec(DH).setIndicator(DH)
				.setContent(new Intent(this, DuiHuanActivity.class));
		tabHost.addTab(tabSpec);
		tabSpec = tabHost.newTabSpec(TG).setIndicator(TG)
				.setContent(new Intent(this, TuiGuangActivity.class));
		tabHost.addTab(tabSpec);

		tabSpec = tabHost.newTabSpec(USER).setIndicator(USER)
				.setContent(new Intent(this, UserActivity.class));
		tabHost.addTab(tabSpec);

	}

	@SuppressWarnings("deprecation")
	public void initViews() {
		rGroup = (RadioGroup) findViewById(R.id.RadioGroup);
		radioButton = (RadioButton) findViewById(R.id.home_id);
		radioButton2 = (RadioButton) findViewById(R.id.duihuan_id);
		radioButton3 = (RadioButton) findViewById(R.id.user_id);

		tabHost = this.getTabHost();
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onDestroy() {

		super.onDestroy();

	}

}
