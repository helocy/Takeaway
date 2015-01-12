package org.twoeggs.takeaway.app;

import org.twoeggs.takeaway.R;
import org.twoeggs.takeaway.classes.Shop;
import org.twoeggs.takeaway.classes.ShopManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ShopListFragment extends ListFragment {
	public static final String TAG = "ShopListFragment";
	
	private ShopManager mShopManager;
	
	private Button mAddBtn;
	private TextView mNoShopText;
	
	public ShopListFragment(ShopManager manager) {
		super(false);
		mShopManager = manager;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		initView();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		if (getAdapter().getCount() == 0) {
			mNoShopText.setVisibility(View.VISIBLE);
			mNoShopText.setText(R.string.str_hints_no_shop);
		} else {
			mNoShopText.setVisibility(View.INVISIBLE);
		}
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		setTitle(getString(R.string.str_shop_list_title));
	}
	
	private void initView() {
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		addFooterView(inflater.inflate(R.layout.bottom_add_shop, null));
		mAddBtn = (Button) getView().findViewById(R.id.id_btn_add_shop);
		mNoShopText = (TextView) getView().findViewById(R.id.id_text_no_shop);
		
		LayoutParams layoutParams = mAddBtn.getLayoutParams();
		layoutParams.height = getResources().getDisplayMetrics().heightPixels / 10;
		mAddBtn.setLayoutParams(layoutParams);
		
		mAddBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Add a new shop
				getSwitcher().switchFragment(FragmentIdentity.FRAGMENT_ADD_SHOP, null, null);
			}
		});
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		super.onItemClick(parent, view, position, id);
		
		Shop shop = (Shop) getAdapter().getItem(position);
		if (shop != null) {
			mShopManager.loadShop(shop);
			getSwitcher().switchFragment(FragmentIdentity.FRAGMENT_ABOUT, shop, shop.getIntroductionUrl());
		}
	}
}
