package org.twoeggs.takeaway.server;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.twoeggs.takeaway.classes.Shop;

public class SubscribeShopRequest extends Request {
	public static final String TAG = "SubscribeShopRequest";
	public static final String URL = "http://218.244.141.142/takeaway/ApiCompany/searchCompany";
	
	private Shop mShop = null;

	public SubscribeShopRequest(RequestListener listener, String code, String uid, String uuid) {
		super(TAG, listener, URL);
		
		addParamPair("code", code);
		addParamPair("uid", uid);
		addParamPair("uuid", uuid);
	}
	
	public Shop getShop() {
		return mShop;
	}

	@Override
	public void resultProcess(RequestResult result) {
		result.print(TAG);
		
		if (result.getCode() == 1) {
			// Subscribe shop OK
			mShop = new Shop();
			
			JSONTokener jsonParser = new JSONTokener(result.getData());
			try {
				JSONObject shopObj = (JSONObject) jsonParser.nextValue();
				mShop.setCode(shopObj.getInt("code"));
				mShop.setName(shopObj.getString("name"));
				mShop.setLogoUrl(shopObj.getString("slogo"));
				mShop.setIntroductionUrl(shopObj.getString("blogo"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
