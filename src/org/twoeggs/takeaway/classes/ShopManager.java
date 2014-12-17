package org.twoeggs.takeaway.classes;

import java.util.ArrayList;

import org.twoeggs.takeaway.database.Database;
import org.twoeggs.takeaway.database.DatabaseHelper;

import android.util.Log;

public class ShopManager {
	public static final String TAG = "ShopManager";
	
	private ArrayList<Shop> mShops;
	private Shop mActiveShop;
	private Database mDatabase;
	
	public ShopManager(Database db) {
		ArrayList<Shop> shops = DatabaseHelper.queryAllShops(db);
		
		if (shops == null)
			mShops = new ArrayList<Shop>();
		else
			mShops = shops;
		
		mDatabase = db;
	}
	
	public boolean addShop(Shop shop) {
		if (shop == null) {
			Log.e(TAG, "Try to add a null shop");
			return false;
		}
		
		Shop s = getShop(shop.getCode());
		if (s != null) {
			Log.e(TAG, "Try to add an exist shop");
			return false;
		}
		
		Log.d(TAG, "Add a new shop, code=" + shop.getCode() + ", name=" + shop.getName());
		mShops.add(shop);
		DatabaseHelper.insertShop(mDatabase, shop);
		return true;
	}
	
	public Shop getShop(int code) {
		for (Shop shop : mShops){
			if (shop.getCode() == code) {
				return shop;
			}
		}
		
		return null;
	}
	
	public Shop getShop(String name) {
		for (Shop shop : mShops){
			if (shop.getName().equals(name)) {
				return shop;
			}
		}
		
		return null;
	}
	
	public Shop getShopByIndex(int index) {
		return mShops.get(index);
	}
	
	public int getCount() {
		return mShops.size();
	}
	
	public boolean setActiveShop(Shop shop) {
		Shop search = getShop(shop.getName());
		if (search == null) {
			Log.e(TAG, "Cannot set a invalid active shop");
			return false;
		}
		
		mActiveShop = search;
		return true;
	}
	
	public boolean setActiveShop(int index) {
		Shop search = getShopByIndex(index);
		if (search == null) {
			Log.e(TAG, "Cannot set a invalid active shop, out of index");
			return false;
		}
		
		mActiveShop = search;
		return true;
	}
	
	public Shop getActiveShop() {
		return mActiveShop;
	}
	
	public void clearActive() {
		mActiveShop = null;
	}
}
