package org.twoeggs.takeaway.classes;

import android.graphics.Bitmap;

public class Product {
	public static final String TAG = "Product";
	
	private int mId;
	private String mName;
	private int mShopCode;
	private String mLogoUrl;
	private String mIntroductionUrl;
	private String mUtime;
	private boolean mDelFlag;
	private int mOrderNum;
	
	private Bitmap mLogoImage;
	private Bitmap mIntroductionImage;
	
	public int getId() {
		return mId;
	}
	
	public void setId(int id) {
		mId = id;
	}
	
	public String getName() {
		return mName;
	}
	
	public void setName(String name) {
		mName = name;
	}
	
	public int getShopCode() {
		return mShopCode;
	}
	
	public void setShopCode(int code) {
		mShopCode = code;
	}
	
	public String getLogoUrl() {
		return mLogoUrl;
	}
	
	public void setLogoUrl(String url) {
		mLogoUrl = url;
	}
	
	public String getIntroductionUrl() {
		return mIntroductionUrl;
	}
	
	public void setIntroductionUrl(String url) {
		mIntroductionUrl = url;
	}
	
	public Bitmap getLogo() {
		return mLogoImage;
	}
	
	public void setLogo(Bitmap image) {
		mLogoImage = image;
	}
	
	public void setOrderNum(int num) {
		mOrderNum = num;
	}
	
	public int getOrderNum() {
		return mOrderNum;
	}
}
