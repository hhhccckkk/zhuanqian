package com.hck.zhuanqian.db;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DB extends SQLiteOpenHelper{
    private static final String dataBaseName="hck";
    private static final int version=1;
	public DB(Context context) {
		super(context, dataBaseName, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	
		db.execSQL("create table user( id Integer primary key autoincrement,name varchar(12),password varchar(10))");
	
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
