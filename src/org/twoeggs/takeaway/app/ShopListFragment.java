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
	
	private Button mAddBtn;
	private TextView mNoShopText;
	private ShopManager mShopManager;
	
	public ShopListFragment(ShopManager shopManager) {
		super(false);
		mShopManager = shopManager;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		initView();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		mShopManager.clearActive();
		
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
		LinearLayout container = (LinearLayout) getView().findViewById(R.id.id_view_bottom_container);
		container.addView(inflater.inflate(R.layout.bottom_add_shop, null));
		mAddBtn = (Button) getView().findViewById(R.id.id_btn_add_shop);
		mNoShopText = (TextView) getView().findViewById(R.id.id_text_no_shop);
		
		mAddBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Add a new shop
				getSwitcher().switchFragment(FragmentIdentity.FRAGMENT_ADD_SHOP, null);
			}
		});
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		super.onItemClick(parent, view, position, id);
		
		Shop shop = (Shop) getAdapter().getItem(position);
		if (shop != null) {
			mShopManager.setActiveShop(position);
			getSwitcher().switchFragment(FragmentIdentity.FRAGMENT_ABOUT, shop.getIntroductionUrl());
		}
	}
}
