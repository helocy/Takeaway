package org.twoeggs.takeaway.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

public class RequestTask extends AsyncTask<String, Integer, String> {
	public static final String TAG = "WebServiceTask";
	
	private List<NameValuePair> mParams;
	private Request mRequest;
	
	public RequestTask(Request request, List<NameValuePair> params) {
		mRequest =  request;
		mParams = params;
	}

	// Param1 - target
	@Override
	protected String doInBackground(String... params) {
		String url = params[0];
		String result = "";
		
		try {
			HttpEntity requestHttpEntity = new UrlEncodedFormEntity(mParams);
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(requestHttpEntity);
			
			HttpClient httpClient = new DefaultHttpClient();
			
			try {
				HttpResponse response = httpClient.execute(httpPost);
				
				HttpEntity httpEntity = response.getEntity();
				InputStream inputStream = httpEntity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader( inputStream));
	            String line = "";
	            
	            while (null != (line = reader.readLine()))
	            	result += line;
	            
	            Log.d(TAG, "Result: " + result);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		
		mRequest.onRequestComplete(result);
	}
}