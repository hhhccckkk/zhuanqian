package com.hck.zhuanqian.adpter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hck.zhuanqian.R;
import com.hck.zhuanqian.bean.Apps;
import com.nostra13.universalimageloader.core.ImageLoader;

public class AppsListAdpter extends BaseAdapter{
	private Context context;
	public List<Apps> beans;
    public AppsListAdpter(Context context,List<Apps> beans)
    {
    	this.context=context;
    	this.beans=beans;
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
		Holder holder=null;
		if (arg1==null) {
			arg1=LayoutInflater.from(context).inflate(R.layout.list_apps_item, null);
			holder=new Holder();
			holder.nameTextView=(TextView) arg1.findViewById(R.id.list_app_name);
			holder.desc=(TextView) arg1.findViewById(R.id.list_app_desc);
			holder.price=(TextView) arg1.findViewById(R.id.list_app_price);
			holder.imageView=(ImageView) arg1.findViewById(R.id.list_app_img);
		    arg1.setTag(holder);
		}
		else {
			holder=(Holder) arg1.getTag();
		}
		holder.nameTextView.setText(beans.get(arg0).getName());
		String desString=beans.get(arg0).getNeirong();
		if (desString.length()>12) {
			desString=desString.substring(0, 12);
		}
		holder.desc.setText(desString);
		int point=beans.get(arg0).getPrice1()+beans.get(arg0).getPrice2();
		holder.price.setText("+"+point+"");
		ImageLoader.getInstance().displayImage(beans.get(arg0).getImage1(), holder.imageView);
		return arg1;
	}
	
	static class Holder
	{
		TextView nameTextView,desc,price;
		ImageView imageView;
		
	}
	public void resh(List<Apps> bApps)
	{
		beans.addAll(bApps);
		this.notifyDataSetChanged();
	}

}
