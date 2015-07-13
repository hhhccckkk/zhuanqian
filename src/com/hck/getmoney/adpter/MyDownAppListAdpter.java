package com.hck.getmoney.adpter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hck.kedouzq.R;
import com.hck.getmoney.bean.Userapp;
import com.hck.getmoney.ui.AppsFragmentActivity;
import com.hck.getmoney.widget.Toasts;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MyDownAppListAdpter extends BaseAdapter {
	private Context context;
	public List<Userapp> beans;
	private long time;

	public MyDownAppListAdpter(Context context, List<Userapp> beans) {
		this.context = context;
		this.beans = beans;
		time = System.currentTimeMillis();
	}

	@Override
	public int getCount() {
		return beans.size();
	}

	@Override
	public Object getItem(int arg0) {
		return beans.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		Holder holder = null;
	//	if (arg1 == null) {
			arg1 = LayoutInflater.from(context).inflate(
					R.layout.list_my_apps_item, null);
			holder = new Holder();
			holder.nameTextView = (TextView) arg1
					.findViewById(R.id.list_app_name);
			holder.desc = (TextView) arg1.findViewById(R.id.list_app_desc);
			holder.price = (TextView) arg1.findViewById(R.id.list_app_price);
			holder.imageView = (ImageView) arg1.findViewById(R.id.list_app_img);
			holder.button = (Button) arg1.findViewById(R.id.qiandao);
			holder.button.setId(arg0 + 1);
			arg1.setTag(holder);
		//} else {
		//	holder = (Holder) arg1.getTag();
		//}
		if (beans.get(arg0).getIscanqd() == 0) {
			holder.button.setText("还不能签到");
		} else {
			holder.button.setText("签到");
		}
		holder.button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				int post = arg0.getId() - 1;
				if (beans.get(post).getIscanqd()==1) {
					((AppsFragmentActivity) context).startApp(beans.get(post));
				}
				else {
					Toasts.toast(context, "还不能签到");
				}
			}
		});
		holder.nameTextView.setText(beans.get(arg0).getName());
		holder.price.setText("签到+" + beans.get(arg0).getPrice() + "蝌蚪币");
		ImageLoader.getInstance().displayImage(beans.get(arg0).getImage1(),
				holder.imageView);
		return arg1;
	}

	static class Holder {
		TextView nameTextView, desc, price;
		ImageView imageView;
		Button button;

	}

	public void resh(List<Userapp> bApps) {
		beans.addAll(bApps);
		this.notifyDataSetChanged();
	}

}
