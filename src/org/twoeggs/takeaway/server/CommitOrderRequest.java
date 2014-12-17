package org.twoeggs.takeaway.server;

import org.twoeggs.takeaway.classes.Order;

public class CommitOrderRequest extends Request {
	public static final String TAG = "CommitOrderRequest";
	public static final String URL = "http://218.244.141.142/takeaway/ApiOrder/addOrder";
	
	public CommitOrderRequest(RequestListener listener, Order order) {
		super(TAG, listener, URL);
		addParamPair("data", order.getJson());
	}

	@Override
	public void resultProcess(RequestResult result) {
		
	}

}
