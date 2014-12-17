package org.twoeggs.takeaway.utils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.twoeggs.takeaway.app.HandlerMessage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ImageLoader implements Runnable {
	private static final String TAG = "ImageLoader";
	
	private Bitmap mImage = null;
	private String mUrl;
	private Handler mHandler;
	
	public ImageLoader(String url, Handler handler) {
		mUrl = url;
		mHandler = handler;
		
		Thread thread = new Thread(this);
		thread.setName(TAG);
		thread.start();
	}
	
	private static InputStream getImageStream(String path) throws Exception{  
        URL url = new URL(path);  
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
        conn.setConnectTimeout(5 * 1000);  
        conn.setRequestMethod("GET");  
        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){  
            return conn.getInputStream();  
        }  
        return null;  
    }
	
	public static Bitmap load(String url) {
		try {
			return BitmapFactory.decodeStream(getImageStream(url));
		} catch (Exception e) {
			Log.e(TAG, "Cannot load image: " + url);
			return null;
		} 
	}

	@Override
	public void run() {
		try {
			mImage = BitmapFactory.decodeStream(getImageStream(mUrl));
			
			Message msg = new Message();
			msg.what = HandlerMessage.MSG_IMAGE_LOADED;
			mHandler.sendMessage(msg);
		} catch (Exception e) {
			Log.e(TAG, "Cannot load image: " + mUrl);
			mImage = null;
		} 
	}
	
	public Bitmap getImage() {
		return mImage;
	}
}
