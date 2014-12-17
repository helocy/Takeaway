package org.twoeggs.takeaway.database;

import java.util.ArrayList;

import org.twoeggs.takeaway.classes.Shop;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

public class DatabaseHelper {
	public static final String TAG = "DatabaseHelper";
	
	public static boolean insertShop(Database db, Shop shop) {
		if (shop == null || shop.getCode() == Shop.INVALID_SHOP_CODE) {
			Log.e(TAG, "Cannot insert a invalid shop");
			return false;
		}
		
		ContentValues values = new ContentValues();
		values.put(Database.KEY_CODE, shop.getCode());
		values.put(Database.KEY_NAME, shop.getName());
		values.put(Database.KEY_LOGO, shop.getLogoUrl());
		values.put(Database.KEY_INTRODUCTION, shop.getIntroductionUrl());
		
		return db.insert(Database.TABLE_SHOP, values);
	}
	
	public static ArrayList<Shop> queryAllShops(Database db) {
		ArrayList<Shop> shops = new ArrayList<Shop>();
		
		Cursor cursor = db.query(Database.TABLE_SHOP);
		
		if (cursor.moveToFirst() == false) {
			Log.w(TAG, "Get nothing from database");
			return null;
		}
		
		do {
			int code = cursor.getInt(cursor.getColumnIndex(Database.KEY_CODE));
			String name = cursor.getString(cursor.getColumnIndex(Database.KEY_NAME));
			String logo = cursor.getString(cursor.getColumnIndex(Database.KEY_LOGO));
			String intro = cursor.getString(cursor.getColumnIndex(Database.KEY_INTRODUCTION));
			
			Shop shop = new Shop();
			shop.setCode(code);
			shop.setName(name);
			shop.setLogoUrl(logo);
			shop.setIntroductionUrl(intro);
			
			shops.add(shop);
		} while (cursor.moveToNext());
		
		cursor.close();
		
		return shops;
	}
}
