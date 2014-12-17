package org.twoeggs.takeaway.server;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.util.Log;

public class RequestResult {
	public static final String TAG = "RequestResult";
	
	private int mCode;
	private String mMsg;
	private String mData;
	
	public RequestResult() {
		
	}
	
	public RequestResult(int code, String msg, String data) {
		mCode = code;
		mMsg = msg;
		mData = data;
	}
	
	public static RequestResult parse(String json) {
		RequestResult result = new RequestResult();
		
		JSONTokener jsonParser = new JSONTokener(json);
		
		JSONObject jsonReslut = null;
		try {
			jsonReslut = (JSONObject) jsonParser.nextValue();
			
			result.setCode(jsonReslut.getInt("code"));
			result.setMsg( jsonReslut.getString("msg"));
			result.setData(jsonReslut.getString("data"));
		} catch (JSONException e) {
			Log.e(TAG, "Cannot parse json result: " + json);
			return null;
		}
		
		return result;
	}
	
	public int getCode() {
		return mCode;
	}
	
	public void setCode(int code) {
		mCode = code;
	}
	
	public String getMsg() {
		return mMsg;
	}
	
	public void setMsg(String msg) {
		mMsg = msg;
	}
	
	public String getData() {
		return mData;
	}
	
	public void setData(String data) {
		mData = data;
	}
	
	public void print(String tag) {
		Log.d(tag, "Code: " + getCode());
		Log.d(tag, "Data: " + getData());
		Log.d(tag, "Message: " + getMsg());
	}
}
