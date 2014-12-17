package org.twoeggs.takeaway.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.util.Log;

public abstract class Request {
	public static final String TAG = "Request";
	public static final int RESULT_FAILED = 0;
	public static final int RESULT_SUCCESS = 1;
	
	private String mName;
	private List<NameValuePair> mParamList;
	private String mTarget;
	private RequestListener mListener;
	private RequestResult mResult;
	
	public Request(String name, RequestListener listener, String webServiceUrl) {
		mParamList = new ArrayList<NameValuePair>();
		mTarget = webServiceUrl;
		mListener = listener;
		mName = name;
	}
	
	public void addParamPair(String name, String value) {
		NameValuePair pair = new BasicNameValuePair(name, value);
		mParamList.add(pair);
	}
	
	public String getName() {
		return mName;
	}
	
	public RequestResult getResult() {
		return mResult;
	}
	
	public void post() {
		RequestTask task = new RequestTask(this, mParamList);
		task.execute(mTarget);
	}
	
	public void onRequestComplete(String jsonResult) {
		Log.d(TAG, mName + " result: " + jsonResult);
		
		mResult = RequestResult.parse(jsonResult);
		if (mResult != null && mResult.getCode() == RESULT_SUCCESS) {
			resultProcess(mResult);	
		} else {
			// TODO
			if (mResult != null)
				Log.e(TAG, "Error message: " + mResult.getMsg());
		}

		mListener.onRequestComplete(this);
	}
	
	public abstract void resultProcess(RequestResult result);
}
