package org.twoeggs.takeaway.cache;

import android.graphics.Bitmap;

public interface ImageLoaderListener {
	public void onImageLoaded(String url, Bitmap image);
}
