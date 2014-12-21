package org.twoeggs.takeaway.classes;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.util.Log;

public class Shop {
	public static final String TAG = "Shop";
	public static final int INVALID_SHOP_CODE = -1;
	
	private Bitmap mLogo = null;
	private Bitmap mIntroductionImage = null;
	
	private int mCode = INVALID_SHOP_CODE;
	private String mUserId;
	private String mName;
	private String mLogoUrl;
	private String mIntroductionUrl;
	private String mUtime;
	private String mCtime;
	
	private ArrayList<Product> mProducts;
	
	public Shop() {
		
	}
	
	public int getCode() {
		return mCode;
	}
	
	public boolean setCode(int code) {
		mCode = code;
		return true;
	}
	
	public String getName() {
		return mName;
	}
	
	public boolean setName(String name) {
		mName = name;
		return true;
	}
	
	public String getLogoUrl() {
		return mLogoUrl;
	}
	
	public boolean setLogoUrl(String url) {
		mLogoUrl = url;
		return true;
	}
	
	public String getIntroductionUrl() {
		return mIntroductionUrl;
	}
	
	public boolean setIntroductionUrl(String url) {
		mIntroductionUrl = url;
		return true;
	}
	
	public Bitmap getLogo() {
		return mLogo;
	}
	
	public void setLogo(Bitmap bm) {
		mLogo = bm;
	}
	
	public Bitmap getIntroductionImage() {
		return mIntroductionImage;
	}
	
	public void setIntroductionImage(Bitmap bm) {
		mIntroductionImage = bm;
	}
	
	public ArrayList<Product> getProducts() {
		return mProducts;
	}
	
	public void setProducts(ArrayList<Product> products) {
		mProducts = products;
	}
	
	public Product getProductByIndex(int index) {
		if (mProducts == null) {
			Log.w(TAG, "Try to get product while mProducts==null");
			return null;
		}
		
		return mProducts.get(index);
	}
}
