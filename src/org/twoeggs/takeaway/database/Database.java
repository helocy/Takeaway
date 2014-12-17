package org.twoeggs.takeaway.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Database {
	public static final String TAG = "Database";
	
	public static final String DB_NAME = "takeaway_db";
	
	// tables
	public static final String TABLE_SHOP = "shop";
	
	// keys
	public static final String KEY_ID = "id";
	public static final String KEY_CODE = "code";
	public static final String KEY_NAME = "name";
	public static final String KEY_LOGO = "logo";
	public static final String KEY_INTRODUCTION = "introduction";
	
	private SQLiteDatabase mDatabase;

	public Database(Context context) {
		mDatabase = context.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
		if (mDatabase == null)
			Log.e(TAG, "Fail to create or open database: " + DB_NAME);
		
		init();
	}
	
	public void init() {
		String sql = "create table if not exists " + TABLE_SHOP + " (" +
				 KEY_ID + " integer primary key autoincrement, " +
				 KEY_CODE + " integer not null, " +
				 KEY_NAME + " text not null, " +
				 KEY_LOGO + " text not null, " +
				 KEY_INTRODUCTION + " text not null" +
				 ")";
		
		Log.d(TAG, "Try to create shop table, sql: " + sql);
		
		mDatabase.execSQL(sql);
	}
	
	public boolean insert(String table, ContentValues values) {
		mDatabase.insert(table, null, values);
		return true;
	}
	
	public boolean delete() {
		return true;
	}
	
	public boolean update() {
		return true;
	}
	
	public Cursor query(String table) {
		return mDatabase.query(table, null, null, null, null, null, null);
	}
}
