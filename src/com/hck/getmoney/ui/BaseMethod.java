package com.hck.getmoney.ui;
/**
 * 
 * @author 黄成科
 *activity的公共使用方法接口
 */
public interface BaseMethod {
	void initDatas();  //初始化数据

	void initViews();  //初始化view

	void setListener(); //绑定事件

	void getData(); //请求数据

	void setDate();  //绑定数据到view
}
