package org.twoeggs.takeaway.classes;

import java.util.ArrayList;

import org.twoeggs.takeaway.app.ProductListAdapter;
import org.twoeggs.takeaway.database.Database;
import org.twoeggs.takeaway.database.DatabaseHelper;
import org.twoeggs.takeaway.server.ProductListRequest;
import org.twoeggs.takeaway.server.Request;
import org.twoeggs.takeaway.server.RequestListener;
import org.twoeggs.takeaway.server.WebService;

import android.util.Log;

public class ShopManager implements RequestListener {
	public static final String TAG = "ShopManager";
	
	private ArrayList<Shop> mShops;
	private Database mDatabase;
	private WebService mWebService;
	
	public ShopManager(Database db, WebService service) {
		ArrayList<Shop> shops = DatabaseHelper.queryAllShops(db);
		
		if (shops == null)
			mShops = new ArrayList<Shop>();
		else
			mShops = shops;
		
		mDatabase = db;
		mWebService = service;
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
	
	public boolean loadShop(Shop shop) {
		if (mShops.contains(shop) == false) {
			Log.e(TAG, "Try to load a invalid shop");
			return false;
		}
		

		// Load all products before product list show
		if (shop.getProducts() == null)
			mWebService.requestProductList(this, shop);
		return true;
	}

	@Override
	public void onRequestComplete(Request request) {
		if (request instanceof ProductListRequest) {
		}
	}
}
