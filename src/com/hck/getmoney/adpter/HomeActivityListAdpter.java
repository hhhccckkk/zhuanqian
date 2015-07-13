package com.hck.getmoney.adpter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hck.getmoney.bean.KindBean;
import com.hck.kedouzq.R;

public class HomeActivityListAdpter extends BaseAdapter {
	private Context context;
	private List<KindBean> bean;
	private int[] images={R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d
			,R.drawable.e,R.drawable.f,R.drawable.g,R.drawable.h,R.drawable.k,R.drawable.j};
	private int lincolors[]={R.color.a,R.color.b,R.color.c,R.color.d,R.color.e,R.color.f,R.color.g,R.color.h
			,R.drawable.jiu,R.color.i,R.color.j};
	public HomeActivityListAdpter(List<KindBean> bean,Context context) {
		this.bean = bean;
		this.context=context;
	}
	@Override
	public int getCount() {
		return bean.size();
	}

	@Override
	public Object getItem(int position) {
		return bean.get(position);
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Holder holder=null;
		holder=new Holder();
		
		if (position<=9) {
			convertView=LayoutInflater.from(context).inflate(R.layout.listview_home_item, null);
		}
		else {
			convertView=LayoutInflater.from(context).inflate(R.layout.listview_home_item2, null);
		}
		holder.contentTextView=(TextView) convertView.findViewById(R.id.home_list_item_content);
		holder.layout=(LinearLayout) convertView.findViewById(R.id.home_list_lin_bg);
		holder.kindNamTextView=(TextView) convertView.findViewById(R.id.kind_name);
		if (position<10) {
			holder.layout.setBackgroundResource(images[position]);
		}
		int tag=position+1;
		holder.kindNamTextView.setText("赚钱"+tag+"区");
		String content=bean.get(position).getContent();
		holder.contentTextView.setText(content);
		return convertView;
	}
	 class Holder
	{
		TextView contentTextView;
		LinearLayout layout;
		TextView kindNamTextView;
	}

}
