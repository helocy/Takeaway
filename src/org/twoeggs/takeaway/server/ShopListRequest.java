package org.twoeggs.takeaway.server;

import android.util.Log;

public class ShopListRequest extends Request {
	public static final String TAG = "ShopListRequest";
	public static final String URL = "http://218.244.141.142/takeaway/ApiCompany/updateCompanyList";

	public ShopListRequest(RequestListener listener) {
		super(TAG, listener, URL);
		addParamPair("clist", "");
	}

	@Override
	public void resultProcess(RequestResult result) {
		result.print(TAG);
	}

}
