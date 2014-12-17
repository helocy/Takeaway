package org.twoeggs.takeaway.server;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.util.Log;

public class AppImageRequest extends Request {
	public static final String TAG ="AppImageRequest";
	public static final String URL = "http://218.244.141.142/takeaway/ApiSetting/getAppImage";
	
	private String mAppImageUrl;
	
	public AppImageRequest(RequestListener listener, String utime) {
		super(TAG, listener, URL);
		addParamPair("utime", utime);
	}

	@Override
	public void resultProcess(RequestResult result) {
		result.print(TAG);
		
		JSONTokener jsonParser = new JSONTokener(result.getData());
		try {
			JSONObject urlObj = (JSONObject) jsonParser.nextValue();
			mAppImageUrl = urlObj.getString("value");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Log.d(TAG, "AppImageUrl: " + mAppImageUrl);
	}
}
