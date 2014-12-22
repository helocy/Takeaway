package org.twoeggs.takeaway.app;

import org.twoeggs.takeaway.R;
import org.twoeggs.takeaway.cache.ImagePool;
import org.twoeggs.takeaway.classes.Shop;
import org.twoeggs.takeaway.classes.ShopManager;
import org.twoeggs.takeaway.classes.User;
import org.twoeggs.takeaway.database.Database;
import org.twoeggs.takeaway.server.Request;
import org.twoeggs.takeaway.server.RequestListener;
import org.twoeggs.takeaway.server.WebService;
import org.twoeggs.takeaway.utils.NetworkManager;

import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener, SwitchFragment, RequestListener  {
	public static final String TAG = "TakeAwayMainActivity";
	
	private ShopListFragment mShopListFragment;
	private AddShopFragment mAddShopFragment;
	private AboutFragment mAboutFragment;
	private ProductListFragment mProductListFragment;
	
	private WebService mWebService;
	private ShopManager mShopManager;
	private Database mDatabase;
	private ImagePool mImagePool;
	
	private User mUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		mUser = new User(this);
		
		mImagePool = new ImagePool();
		mWebService = new WebService();
		mDatabase = new Database(this);
		mShopManager = new ShopManager(mDatabase, mWebService);
		
		initView();
		loadAppImage();                                  
		loadShopList();
		
		showShopListFragment();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		if (NetworkManager.isNetworkConnected(this) == false)
			Toast.makeText(this, this.getString(R.string.str_error_no_network), Toast.LENGTH_SHORT).show();
	}
	
	private void loadAppImage() {
		mWebService.requestAppImage(this);
	}
	
	private void loadShopList() {
		mWebService.requestShopList(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void initView() {
		
	}
	
	public void hideFragments(FragmentTransaction transaction) {
		if (mShopListFragment != null)
			transaction.hide(mShopListFragment);
	}
	
	private void showShopListFragment() {
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		
		//hideFragments(transaction);
		
		if (mShopListFragment == null) {
			mShopListFragment = new ShopListFragment(mShopManager);
			ShopListAdapter adapter = new ShopListAdapter(this, mShopManager, mImagePool);
			mShopListFragment.setListAdapter(adapter);
			transaction.add(R.id.id_frame_container, mShopListFragment);
		}
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		transaction.replace(R.id.id_frame_container, mShopListFragment);
		
		transaction.commit();
	}
	
	private void showAddShopFragment() {
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		
		if (mAddShopFragment == null) {
			mAddShopFragment = new AddShopFragment(mWebService, mShopManager);
		}
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		transaction.replace(R.id.id_frame_container, mAddShopFragment);
		
		transaction.addToBackStack("ShopList");
		
		transaction.commit();
	}
	
	private void showAboutFragment(Shop shop, String url) {
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		
		if (mAboutFragment == null) {
			mAboutFragment = new AboutFragment(shop, url, mImagePool);
		} else {
			mAboutFragment.update(shop, url);
		}
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		transaction.replace(R.id.id_frame_container, mAboutFragment);
		
		transaction.addToBackStack("ShopList");
		
		transaction.commit();
	}
	
	private void showProductListFragment(Shop shop) {
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		
		if (mProductListFragment == null) {
			mProductListFragment = new ProductListFragment(shop, mWebService, mUser);
			ProductListAdapter adapter = new ProductListAdapter(this, shop, mImagePool);
			mProductListFragment.setListAdapter(adapter);
		} else {
			mProductListFragment.setShop(shop);
			ProductListAdapter adapter = (ProductListAdapter)mProductListFragment.getAdapter();
			adapter.setShop(shop);
		}
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		transaction.replace(R.id.id_frame_container, mProductListFragment);
		
		transaction.addToBackStack("ProductList");
		
		transaction.commit();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		}
	}

	@Override
	public void switchFragment(int id, Object arg1, Object arg2) {
		Shop shop = null;

		switch (id) {
		case FragmentIdentity.FRAGMENT_SHOP_LIST:
			showShopListFragment();
			break;
			
		case FragmentIdentity.FRAGMENT_ADD_SHOP:
			showAddShopFragment();
			break;
			
		case FragmentIdentity.FRAGMENT_ABOUT:
			shop = (Shop) arg1;
			String url = (String) arg2;
			showAboutFragment(shop, url);
			break;
			
		case FragmentIdentity.FRAGMENT_PRODUCT_LIST:
			shop = (Shop) arg1;
			if (shop == null) {
				Log.e(TAG, "Cannot show product fragment without shop");
				break;
			}

			showProductListFragment(shop);
			break;
		}
	}

	@Override
	public void onRequestComplete(Request request) {
		
	}
}
