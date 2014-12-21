package org.twoeggs.takeaway.app;

import java.util.ArrayList;

import org.twoeggs.takeaway.R;
import org.twoeggs.takeaway.classes.Order;
import org.twoeggs.takeaway.classes.Product;
import org.twoeggs.takeaway.classes.Shop;
import org.twoeggs.takeaway.classes.User;
import org.twoeggs.takeaway.server.CommitOrderRequest;
import org.twoeggs.takeaway.server.ProductListRequest;
import org.twoeggs.takeaway.server.Request;
import org.twoeggs.takeaway.server.RequestListener;
import org.twoeggs.takeaway.server.WebService;
import org.twoeggs.takeaway.utils.StringChecker;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ProductListFragment extends ListFragment implements RequestListener {
	public static final String TAG = "ProductListFragment";
	
	private Shop mShop;
	private WebService mWebService;
	private User mUser;
	
	private LinearLayout mBuyLayout;
	private EditText mPhoneNumEdit;
	private Button mBuyBtn;

	public ProductListFragment(Shop shop, WebService service, User user) {
		super(true);
		mShop = shop;
		mWebService = service;
		mUser = user;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		setTitle(mShop.getName());
		
		ProductListAdapter adapter = (ProductListAdapter) getAdapter();
		adapter.setSwitcher(getSwitcher());
		
		mPhoneNumEdit.setText("");
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		initView();
	}
	
	private boolean checkOrder() {
		ArrayList<Product> products = mShop.getProducts();
		int total = 0;
		
		for (Product product : products) {
			total += product.getOrderNum();
		}
		
		if (total == 0) {
			Log.d(TAG, "Nothing add to order!");
			return false;
		}
		
		return true;
	}
	
	private void initView() {
		LayoutParams layoutParams = null;
		
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		LinearLayout container = (LinearLayout) getView().findViewById(R.id.id_view_bottom_container);
		container.addView(inflater.inflate(R.layout.bottom_add_order, null));
		
		mBuyLayout = (LinearLayout) container.findViewById(R.id.id_layout_buy);
		mBuyBtn = (Button) container.findViewById(R.id.id_btn_buy);
		mPhoneNumEdit = (EditText) container.findViewById(R.id.id_edit_phone_num);
		mBuyBtn.setTag(this);
		
		layoutParams = mBuyLayout.getLayoutParams();
		layoutParams.height = getResources().getDisplayMetrics().heightPixels / 10;
		mBuyLayout.setLayoutParams(layoutParams);
		
		layoutParams = mPhoneNumEdit.getLayoutParams();
		layoutParams.width = getResources().getDisplayMetrics().widthPixels / 2;
		layoutParams.height = getResources().getDisplayMetrics().heightPixels / 10 - 30;
		mPhoneNumEdit.setLayoutParams(layoutParams);
		
		layoutParams = mBuyBtn.getLayoutParams();
		layoutParams.width = getResources().getDisplayMetrics().widthPixels / 4;
		layoutParams.height = getResources().getDisplayMetrics().heightPixels / 10 - 10;
		mBuyBtn.setLayoutParams(layoutParams);

		mBuyBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// Check phone number
				String phoneNum = mPhoneNumEdit.getEditableText().toString();
				if (StringChecker.checkPhoneNumber(phoneNum) == false) {
					Toast.makeText(getActivity(), 
							getActivity().getString(R.string.str_warning_invalid_phone_num), Toast.LENGTH_SHORT).show();
					Log.w(TAG, "Invalid phone number");
					return;
				}
				
				// Check order
				if (checkOrder() == false) {
					Toast.makeText(getActivity(),
							getActivity().getString(R.string.str_warning_order_empty), Toast.LENGTH_SHORT).show();
					Log.w(TAG, "Empty order!");
					return;
				}
				
				// Create order
				mUser.setPhoneNum(phoneNum);
				Order order = new Order(mUser, mShop.getProducts());
				ProductListFragment fragment = (ProductListFragment)arg0.getTag();
				mWebService.requestCommitOrder(fragment, order);
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.id_btn_return:
			getSwitcher().switchFragment(FragmentIdentity.FRAGMENT_SHOP_LIST, null, null);
			break;
			
		case R.id.id_btn_about:
			getSwitcher().switchFragment(FragmentIdentity.FRAGMENT_ABOUT, mShop, mShop.getIntroductionUrl());
			break;
		}
	}

	@Override
	public void onRequestComplete(Request request) {
		if (request instanceof CommitOrderRequest) {
			if (request.getResult().getCode() == Request.RESULT_SUCCESS)
				Toast.makeText(getActivity(),
					getActivity().getString(R.string.str_hints_order_success), Toast.LENGTH_SHORT).show();
			else
				Toast.makeText(getActivity(),
					getActivity().getString(R.string.str_hints_order_fail), Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		super.onItemClick(parent, view, position, id);
	}
	
	public void setShop(Shop shop) {
		mShop = shop;
	}
}
