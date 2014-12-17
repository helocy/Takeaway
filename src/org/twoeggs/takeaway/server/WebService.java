package org.twoeggs.takeaway.server;

import org.twoeggs.takeaway.classes.Order;

public class WebService {
	public static final String TAG = "WebService";
	
	public WebService() {
		
	}
	
	public void requestAppImage(RequestListener listener) {
		Request request = new AppImageRequest(listener, "00");
		request.post();
	}
	
	public void requestShopList(RequestListener listener) {
		Request request = new ShopListRequest(listener);
		request.post();
	}
	
	public void requestSubscribeShop(RequestListener listener, String code, String uid, String uuid) {
		Request request = new SubscribeShopRequest(listener, code, uid, uuid);
		request.post();
	}
	
	public void requestProductList(RequestListener listener, int code) {
		Request request = new ProductListRequest(listener, code);
		request.post();
	}
	
	public void requestCommitOrder(RequestListener listener, Order order) {
		Request request = new CommitOrderRequest(listener, order);
		request.post();
	}
}
