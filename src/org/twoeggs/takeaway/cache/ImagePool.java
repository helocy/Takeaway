package org.twoeggs.takeaway.cache;

import java.util.HashMap;

import android.graphics.Bitmap;

public class ImagePool implements ImageLoaderListener {
	public static final String TAG  = "ImagePool";
	public static final int CAPACITY = 36;
	
	private HashMap<String, ImageCache> mPool;
	private long mCurrentTime;
	
	public ImagePool() {
		mPool = new HashMap<String, ImageCache>();
		mCurrentTime = 0;
	}
	
	private void loadImage(String url) {
		ImageLoader loader = new ImageLoader(this, url);
		loader.execute();
	}
	
	public Bitmap getImage(String url) {
		ImageCache imageCache = mPool.get(url);
		if (imageCache == null) {
			loadImage(url);
			return null;
		}

		return imageCache.getImage();
	}

	@Override
	public void onImageLoaded(String url, Bitmap image) {
		mCurrentTime++;
		ImageCache imageCache = new ImageCache(image, mCurrentTime);
		mPool.put(url, imageCache);
	}
}
