package org.twoeggs.takeaway.cache;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

public class ImageLoader extends AsyncTask<String, Integer, Bitmap> {
	public static final String TAG = "ImageLoader";
	
	private String mUrl;
	private ImageLoaderListener mListener;
	
	public ImageLoader(ImageLoaderListener listener, String url) {
		mUrl = url;
		mListener = listener;
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

	@Override
	protected Bitmap doInBackground(String... params) {
		Bitmap image = null;
		
		try {
			image = BitmapFactory.decodeStream(getImageStream(mUrl));
		} catch (Exception e) {
			Log.e(TAG, "Cannot load image: " + mUrl);
			return null;
		} 

		return image;
	}

	@Override
	protected void onPostExecute(Bitmap image) {
		super.onPostExecute(image);
		if (image != null)
			mListener.onImageLoaded(mUrl, image);
	}
}
