package org.twoeggs.takeaway.cache;

import android.graphics.Bitmap;

public class ImageCache {
	public static final String TAG  = "ImageCache";
	
	private long mTimeStamp;
	private Bitmap mImage;
	private long mUsedCnt;
	
	public ImageCache(Bitmap image, long timeStamp) {
		mImage = image;
		mTimeStamp = timeStamp;
		mUsedCnt = 0;
	}
	
	public Bitmap getImage() {
		reference();
		return mImage;
	}
	
	public long getTimeStamp() {
		return mTimeStamp;
	}
	
	public long getUsedCount() {
		return mUsedCnt;
	}
	
	public void reference() {
		mUsedCnt++;
	}
}
