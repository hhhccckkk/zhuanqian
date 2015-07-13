package com.hck.getmoney.ui;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 
 * @author Administrator
 *FragmentActivity的基类，定义了一些公用数据
 */
public class BaseFragment extends FragmentActivity{
	public Button backButton;   //title左边按钮
	public TextView contentTextView;  //title中间文本
	public  Button rightButton;  ////title右边按钮
	public TextView textView1,textView2,textView3,textView4;  //title部分
	
	public ViewPager pager; 
	public int offset = 0;  //偏移量
	public int bmpW;  //横线图片长度
	public ImageView cursor;
	public ArrayList<Fragment> views; // 保存view的数组，用来显示在页卡
	public int currIndex = 0;

}
