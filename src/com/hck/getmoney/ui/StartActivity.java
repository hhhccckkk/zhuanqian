package com.hck.getmoney.ui;

import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

import com.hck.kedouzq.R;
import com.hck.getmoney.util.UIHelp;

public class StartActivity extends BaseActivity {
	private ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_activity);
		AlphaAnimation animation = new AlphaAnimation(0.1f, 1.0f);
		animation.setDuration(5000);
		imageView=(ImageView) findViewById(R.id.start_img);
		imageView.startAnimation(animation);
		setListener(animation);

	}

	private void setListener(AlphaAnimation animation) {
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				UIHelp.startMainActivity(StartActivity.this);

			}
		});

	}

}
