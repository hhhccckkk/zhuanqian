package com.hck.getmoney.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hck.getmoney.bean.UserBean;

public class DBServer {
	  Context context;
	SQLiteDatabase sDatabase;
	public DBServer(Context context)
	{
		this.context=context;
		DB db=new DB(context);
		 sDatabase=db.getWritableDatabase();
	}
	
	public void save(UserBean user)
	{
		
		//sDatabase.execSQL("insert into user(name,password) values(?,?)",new String[]{user.getName(),user.getPassword()});
	}
	public void update(UserBean user)
	{
	//sDatabase.execSQL("update user set name=?,password=? where id=1",new String[]{user.getName(),user.getPassword()});
		
	}
	public void delete(UserBean user)
	{
		sDatabase.execSQL("delete from user where name='"+user.getName()+"'");
	}
	public List<UserBean> getListUser()
	{
		List<UserBean> uList=new ArrayList<UserBean>();
		Cursor cursor=null;
		cursor=sDatabase.rawQuery("select * from user", null);
		while(cursor.moveToNext())
		{
			//UserBean user=new UserBean(cursor.getInt(0),cursor.getString(1),cursor.getString(2));
		//	uList.add(user);
		}
		return uList;
		
	}
	public UserBean getUser(String key)
	{
		UserBean u=null;
	Cursor cursor=sDatabase.rawQuery("select * from user where name=?", new String[]{key});
	if(cursor.moveToNext())
	{
		//u=new UserBean(cursor.getInt(0),cursor.getString(1),cursor.getString(2));
	}
	return u;
		
	}

}
