package com.hck.getmoney.ui;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hck.getmoney.data.Data;
import com.hck.getmoney.data.HttpUrls;
import com.hck.getmoney.net.JsonHttpResponseHandler;
import com.hck.getmoney.net.RequestParams;
import com.hck.getmoney.util.HttpUtil;
import com.hck.getmoney.util.UIHelp;
import com.hck.getmoney.widget.AlertDialogs;
import com.hck.getmoney.widget.BadgeView;
import com.hck.getmoney.widget.PDialog;
import com.hck.getmoney.widget.Toasts;
import com.hck.kedouzq.R;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.open.SocialConstants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class UserActivity extends BaseActivity implements BaseMethod,
		OnClickListener, BaseAlert {
	private TextView titleTextView;
	private TextView nameTextView;
	private TextView yqmTextView;
	private TextView tgTextView;
	private TextView allmoneyTextView;
	private TextView kedoubiTextView;
	private TextView userIdTextView;
	private RelativeLayout inventRelativeLayout, updateLayout,
			endOrdeRelativeLayout, deaLayout, shareLayout, messageLayout,
			aboutLayout, shareQQLayout, pinglunRelativeLayout;
	private RelativeLayout yqmLayout;
	private EditText editText;
	private TextView textView;
	private BadgeView badgeView;
	private int messageSize;
	private TextView qymTextView;
	private Tencent tencent;
	private static final int jinbi = 100;
	private RelativeLayout tuiguangRLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		initViews();
		initDatas();
		setListener();
		getData();
		setDate();
	}

	@Override
	protected void onResume() {
		super.onResume();
		kedoubiTextView.setText(Data.userBean.getAllKeDouBi() + "个");
	}

	@Override
	public void initDatas() {
		tencent = Tencent.createInstance(Data.Tenxun, this);
		nameTextView.setText("用户名：" + Data.userBean.getName());
		yqmTextView.setText("我的邀请码：" + Data.userBean.getJhm());
		tgTextView.setText("推广用户:  " + Data.userBean.getTg() + "人");
		allmoneyTextView.setText(Data.userBean.getAllMoney() + "元");
		titleTextView.setText("用户中心");
		userIdTextView.setText("用户ID：" + Data.userBean.getJhm());
	}

	@Override
	public void initViews() {
		tuiguangRLayout = (RelativeLayout) findViewById(R.id.user_tuiguang);
		nameTextView = (TextView) findViewById(R.id.user_name);
		yqmTextView = (TextView) findViewById(R.id.user_yqm);
		tgTextView = (TextView) findViewById(R.id.user_tj);
		allmoneyTextView = (TextView) findViewById(R.id.user_allmoney);
		kedoubiTextView = (TextView) findViewById(R.id.user_canuse);
		inventRelativeLayout = (RelativeLayout) findViewById(R.id.user_yaoqing);
		endOrdeRelativeLayout = (RelativeLayout) findViewById(R.id.user_order_end);
		deaLayout = (RelativeLayout) findViewById(R.id.user_order_notdeal);
		shareLayout = (RelativeLayout) findViewById(R.id.user_share);
		messageLayout = (RelativeLayout) findViewById(R.id.user_messages);
		aboutLayout = (RelativeLayout) findViewById(R.id.user_about2);
		titleTextView = (TextView) findViewById(R.id.title_text);
		yqmLayout = (RelativeLayout) findViewById(R.id.user_addYqm);
		pinglunRelativeLayout = (RelativeLayout) findViewById(R.id.user_pl);
		qymTextView = (TextView) findViewById(R.id.qym_tx);
		if (Data.userBean.getShangjia() != null
				&& !"".equals(Data.userBean.getShangjia())
				&& !Data.userBean.getShangjia().equals("null")) {
			hiden();
		}
		updateLayout = (RelativeLayout) findViewById(R.id.user_updateName);
		userIdTextView = (TextView) findViewById(R.id.user_id);
		textView = (TextView) findViewById(R.id.myMessages);
		titleTextView = (TextView) findViewById(R.id.title_text);
		shareQQLayout = (RelativeLayout) findViewById(R.id.user_shareQQzn);
	}

	private void hiden() {
		yqmLayout.setEnabled(false);
		qymTextView.setEnabled(false);
		qymTextView.setText("已经填入邀请码 每次金币加10%");
		qymTextView.setTextColor(Color.RED);
		qymTextView.setTextSize(14);
	}

	private void setNotice() {
		if (messageSize <= 0) {
			return;
		}
		badgeView = new BadgeView(this, textView);
		badgeView.setText("" + messageSize); // 显示类容
		badgeView.setBadgePosition(BadgeView.POSITION_TOP_LEFT);// 显示的位置.中间，还有其他位置属性
		badgeView.setTextColor(Color.WHITE); // 文本颜色
		badgeView.setBadgeBackgroundColor(Color.RED); // 背景颜色
		badgeView.setTextSize(10); // 文本大小
		badgeView.setBadgeMargin(0, 0); // 水平和竖直方向的间距
		badgeView.toggle();
	}

	@Override
	public void setListener() {

		endOrdeRelativeLayout.setOnClickListener(this);
		deaLayout.setOnClickListener(this);
		shareLayout.setOnClickListener(this);
		messageLayout.setOnClickListener(this);
		aboutLayout.setOnClickListener(this);
		inventRelativeLayout.setOnClickListener(this);
		yqmLayout.setOnClickListener(this);
		updateLayout.setOnClickListener(this);
		shareQQLayout.setOnClickListener(this);
		tuiguangRLayout.setOnClickListener(this);
		pinglunRelativeLayout.setOnClickListener(this);
	}

	@Override
	public void getData() {
		params = null;
		params = new RequestParams();
		params.put("id", Data.userBean.getId() + "");
		HttpUtil.getHttpUtil().get(HttpUrls.getMessageNoReadP, params,
				new JsonHttpResponseHandler() {
					@Override
					public void onFailure(Throwable e, JSONArray errorResponse) {
						super.onFailure(e, errorResponse);
					}

					@Override
					public void onFinish(String url) {
						super.onFinish(url);
					}

					@Override
					public void onSuccess(int statusCode, JSONObject response) {
						super.onSuccess(statusCode, response);
						try {
							isOk = response.getBoolean("isok");
							if (isOk) {
								messageSize = response.getInt("size");
								setDate();
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});

	}

	@Override
	public void setDate() {
		setNotice();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.user_yaoqing:
			if (tencent.isReady()) {
				inviteFriend();
			} else {
				login();
			}
			break;
		case R.id.user_order_notdeal:
			UIHelp.startShowMyOrders(this, 0);
			break;
		case R.id.user_order_end:
			UIHelp.startShowMyOrders(this, 1);
			break;
		case R.id.user_share:
			share();
			break;
		case R.id.user_messages:
			if (badgeView != null) {
				badgeView.hide();
			}
			UIHelp.startMessageActivity(this);
			break;
		case R.id.user_about2:
			UIHelp.startAboutActivity(this);
			break;
		case R.id.user_addYqm:
			addYQM();
			break;
		case R.id.user_updateName:
			startActivity(new Intent(UserActivity.this,
					UpdateUserInfoActivity.class));
			break;
		case R.id.user_shareQQzn:
			shareToFriends();
			break;
		case R.id.user_tuiguang:
			startActivity(new Intent(UserActivity.this, TuiGuangActivity.class));
			break;
		case R.id.user_pl:
			startPinLunActivity();
		default:
			break;
		}
	}

	public void startPinLunActivity() {
		try {
			Uri uri = Uri.parse("market://details?id=" + "com.hck.kedouzq");
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		} catch (Exception e) {
		}
		
	}

	private void shareToFriends() {
		final Bundle params = new Bundle();
		params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE,
				QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
		params.putString(QQShare.SHARE_TO_QQ_TITLE, "可以赚钱的手机app，推荐给大家");
		params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY,
				Data.shareBean.getContent());
		params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,
				Data.shareBean.getUrlString());
		ArrayList<String> imageUrls = new ArrayList<String>();
		imageUrls.add(Data.shareBean.getImageUrl());
		params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
		doShareToQzone(params);

	}

	private void doShareToQzone(final Bundle params) {
		if (tencent != null) {
			tencent.releaseResource();
			tencent = null;
		}
		tencent = Tencent.createInstance(Data.Tenxun, this);
		tencent.shareToQzone(UserActivity.this, params, new IUiListener() {
			@Override
			public void onError(UiError arg0) {
				Toasts.toast(UserActivity.this, "分享失败");

			}

			@Override
			public void onComplete(Object arg0) {

			}

			@Override
			public void onCancel() {
				Toasts.toast(UserActivity.this, "分享取消");

			}
		});
		if (!Data.userBean.isShareQQ()) {
			sendDataToServer(Data.TENXUN_SHARE);
			addMoney(jinbi);
		}

	}

	IUiListener qZoneShareListener = new IUiListener() {

		@Override
		public void onCancel() {
		}

		@Override
		public void onError(UiError e) {

		}

		@Override
		public void onComplete(Object response) {

		}

	};

	private void login() {
		tencent = Tencent.createInstance(Data.Tenxun, this);
		if (!tencent.isSessionValid()) {
			Toasts.toast(this, "请先登录");
			tencent.login(this, "all", new IUiListener() {

				@Override
				public void onError(UiError arg0) {
					Toasts.toast(UserActivity.this, "登录失败");
				}

				@Override
				public void onComplete(Object arg0) {

					inviteFriend();

				}

				@Override
				public void onCancel() {
					Toasts.toast(UserActivity.this, "登录取消");

				}
			});

		} else {

			inviteFriend();

		}
	}

	private void inviteFriend() {
		Bundle params = new Bundle();
		params.putString(SocialConstants.PARAM_APP_ICON,
				Data.shareBean.getUrlString());
		params.putString(SocialConstants.PARAM_APP_DESC,
				Data.shareBean.getContent());
		params.putString(SocialConstants.PARAM_ACT, "进入应用");

		tencent.invite(UserActivity.this, params, new IUiListener() {
			@Override
			public void onError(UiError arg0) {

			}

			@Override
			public void onComplete(Object arg0) {
				if (!Data.userBean.isYaoQingQQ()) {
					sendDataToServer(Data.TENXUN_INVITE_APP);

				}
			}

			@Override
			public void onCancel() {
			}
		});

	}

	private void sendDataToServer(int type) {
		PDialog.showDialog(this, "请稍等", "正在为您增加金币");
		RequestParams params = new RequestParams();
		params.put("uid", Data.userBean.getId() + "");
		params.put("jinbi", jinbi + "");
		params.put("type", type + "");
		HttpUtil.getHttpUtil().post(HttpUrls.renwu, params,
				new JsonHttpResponseHandler() {
					public void onFailure(Throwable error, String content) {
						Log.d("hck", "onFailure: " + content);
					};

					public void onSuccess(int statusCode,
							org.json.JSONObject response) {
						boolean isok = false;
						try {
							isok = response.getBoolean("isok");
						} catch (JSONException e) {
							e.printStackTrace();
						}
						if (isok) {
							addMoney(jinbi);
							Toasts.toast(UserActivity.this, "增加金币成功 您获取金币: "
									+ jinbi + "个");
						} else {
							Toasts.toast(UserActivity.this, "增加失败 请检查您的网络");
						}
					};

					public void onFinish(String url) {
						PDialog.hidenDialog();
					};
				});

	}

	private void addMoney(int jinbi) {
		Data.userBean.setAllKeDouBi(Data.userBean.getAllKeDouBi() + jinbi);
	}

	public void doBackShare(int a) {
		if (a == 1) {
			handler.sendEmptyMessage(1);
		} else {
			handler.sendEmptyMessage(0);
		}
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0) {
				Toasts.toast(UserActivity.this, "分享失败");
			} else {
				Toasts.toast(UserActivity.this, "分享成功");
			}
		};
	};

	private void share() {
		// ShareUtil.share(this, Data.shareBean.getContent(), this);
	}

	private void addYQM() {
		editText = new EditText(this);
		new AlertDialog.Builder(this).setTitle("请输入邀请码")
				.setIcon(android.R.drawable.ic_dialog_info).setView(editText)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						String yqmString = editText.getText().toString();
						yqmString = yqmString.toUpperCase().trim();

						if (yqmString == null || "".equals(yqmString)) {
							Toasts.toast(UserActivity.this, "邀请码不能为空");
						} else if (Data.userBean.getJhm().equals(yqmString)) {
							Toasts.toast(UserActivity.this, "不能填入自己的邀请码！！！");
						} else {
							sendYQM(yqmString);
						}
					}
				}).setNegativeButton("取消", null).show();
	}

	private void sendYQM(final String yqm) {
		PDialog.showDialog(this, "请稍等", "处理中");
		params = new RequestParams();
		params.put("yqm", yqm);
		params.put("uid", Data.userBean.getId() + "");
		params.put("jinbi", 200 + "");
		HttpUtil.getHttpUtil().get(HttpUrls.addYQMP, params,
				new JsonHttpResponseHandler() {
					@Override
					public void onFailure(Throwable error, String content) {
						super.onFailure(error, content);
						Toasts.toast(UserActivity.this, "邀请码填入失败 请检查网络");
					}

					@Override
					public void onFinish(String url) {
						super.onFinish(url);
						PDialog.hidenDialog();
					}

					@Override
					public void onSuccess(int statusCode, JSONObject response) {
						super.onSuccess(statusCode, response);
						try {
							isOk = response.getBoolean("isok");
							if (isOk) {
								new AlertDialogs().alert(UserActivity.this,
										UserActivity.this, "填入成功",
										"您成功填入邀请码 每次获金币额外加10%");
								Data.userBean.setShangjia(yqm);
								hiden();

							} else {
								Toasts.toast(UserActivity.this,
										"邀请码填入失败 请检查邀请码是否正确");
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}

	private void updateUsername() {
		editText = new EditText(this);
		new AlertDialog.Builder(this).setTitle("请输入昵称")
				.setIcon(android.R.drawable.ic_dialog_info).setView(editText)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						String yqmString = editText.getText().toString();
						if (yqmString == null || "".equals(yqmString)) {
							Toasts.toast(UserActivity.this, "昵称不能为空");
						} else if (yqmString.length() > 5) {
							Toasts.toast(UserActivity.this, "昵称长度不能长于5个");
						} else {
							updateUserName(yqmString);
						}
					}
				}).setNegativeButton("取消", null).show();
	}

	private void updateUserName(final String name) {

		PDialog.showDialog(this, "请稍等", "处理中");
		params = new RequestParams();
		params.put("nicheng", name);
		params.put("uid", Data.userBean.getId() + "");
		HttpUtil.getHttpUtil().get(HttpUrls.updateUserNiChengP, params,
				new JsonHttpResponseHandler() {
					@Override
					public void onFailure(Throwable error, String content) {
						super.onFailure(error, content);
						Toasts.toast(UserActivity.this, "修改昵称失败 请检查网络");
					}

					@Override
					public void onFinish(String url) {
						super.onFinish(url);
						PDialog.hidenDialog();
					}

					@Override
					public void onSuccess(int statusCode, JSONObject response) {
						super.onSuccess(statusCode, response);
						try {
							isOk = response.getBoolean("isok");
							if (isOk) {
								Toasts.toast(UserActivity.this, "修改昵称成功");
								nameTextView.setText("用户名：" + name);
								Data.userBean.setName(name);
							} else {
								Toasts.toast(UserActivity.this, "修改失败 请检查网络");
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}

					}
				});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) { // 按返回键时候，提示用户是否退出
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AlertDialogs.alertDialog(this, this, "确定要退出吗？", "按错", "闪人",
					"no_exit", "exit2");
			return true;
		}
		return false;
	}

	@Override
	public void doLeftButton(String value) {

	}

	@Override
	public void doRightButton(String value) {
		finish();
		System.gc();
	}

	@Override
	public void doSometing(String s) {

	}

}
