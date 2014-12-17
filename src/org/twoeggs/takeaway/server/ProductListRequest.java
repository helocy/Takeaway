package org.twoeggs.takeaway.server;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.twoeggs.takeaway.classes.Product;

import android.util.Log;

public class ProductListRequest extends Request {
	public static final String TAG = "ProductListRequest";
	public static final String URL = "http://218.244.141.142/takeaway/ApiProduct/listProduct";
	
	private ArrayList<Product> mProducts;

	public ProductListRequest(RequestListener listener, int code) {
		super(TAG, listener, URL);
		addParamPair("code", String.valueOf(code));
	}
	
	public ArrayList<Product> getProductList() {
		return mProducts;
	}

	@Override
	public void resultProcess(RequestResult result) {
		result.print(TAG);
		
		mProducts = new ArrayList<Product>();
		
		JSONTokener jsonParser = new JSONTokener(result.getData());
		
		JSONArray productArray;
		try {
			productArray = (JSONArray) jsonParser.nextValue();
			
			for (int i = 0; i < 999; i++) {
				JSONObject obj = productArray.getJSONObject(i);
				if (obj == null)
					break;
				
				Product product = new Product();
				product.setId(obj.getInt("id"));
				product.setName(obj.getString("name"));
				product.setShopCode(obj.getInt("code"));
				product.setLogoUrl(obj.getString("slogo"));
				product.setIntroductionUrl(obj.getString("blogo"));
				
				mProducts.add(product);
			}
		} catch (JSONException e) {
			Log.e(TAG, "Cannot parse product list json");
			e.printStackTrace();
		}
		
	}

}
