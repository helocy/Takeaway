package org.twoeggs.takeaway.classes;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Order {
	public static final String TAG = "Order";
	
	private User mUser;
	private ArrayList<Product> mProducts;
	
	public Order(User user, ArrayList<Product> products) {
		mUser = user;
		mProducts = products;
	}
	
	public String getJson() {
		try {
			JSONObject jsonObject = new JSONObject();
			

			jsonObject.put("code", mProducts.get(0).getShopCode());
			jsonObject.put("uid", "0");
			jsonObject.put("phone", mUser.getPhoneNum());
			jsonObject.put("glat", mUser.getLatitude());
			jsonObject.put("glng", mUser.getLongitude());
			jsonObject.put("blat", mUser.getLatitude());
			jsonObject.put("blng", mUser.getLongitude());
			jsonObject.put("uuid", "ahkhakhdequiyeuiahdhahdkah");
			jsonObject.put("dt", "hdakhqoeqweqeqweqwe");
			jsonObject.put("os", 2);
			
			JSONArray array = new JSONArray();
			for (Product product : mProducts) {
				if (product.getOrderNum() > 0) {
					JSONObject obj = new JSONObject();
					obj.put("num", product.getOrderNum()); 
					obj.put("pid", product.getId());
		            array.put(obj); 
				}
			}
			
			jsonObject.put("od", array);
			
			Log.d(TAG, "Order's json: " + jsonObject.toString());
			return jsonObject.toString();
		
		} catch (JSONException e) {
			Log.d(TAG, "Cannot create order's json!");
			return null;
		}
	}
}
