package org.twoeggs.takeaway.app;

import java.util.ArrayList;

import org.twoeggs.takeaway.R;
import org.twoeggs.takeaway.cache.ImagePool;
import org.twoeggs.takeaway.classes.Product;
import org.twoeggs.takeaway.classes.Shop;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductListAdapter extends BaseAdapter implements Runnable, OnClickListener {
	public static final String TAG = "ProductListAdapter";
	
	private Context mContext;
	private ImagePool mImagePool;
	private SwitchFragment mSwitcher;
	
	private Shop mShop;
	
	public ProductListAdapter(Context context, Shop shop, ImagePool imagePool) {
		super();
		
		mContext = context;
		mShop = shop;
		mImagePool = imagePool;
	}

	@Override
	public int getCount() {
		if (mShop == null) {
			Log.w(TAG, "No shop to display in product list");
			return 0;
		}
		
		if (mShop.getProducts() == null) {
			Log.w(TAG, "No products to display in product list");
			return 0;
		}

		return mShop.getProducts().size();
	}

	@Override
	public Object getItem(int position) {
		return mShop.getProducts().get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	private void updateProductImage(ViewHolder viewHolder) {
		Product product = mShop.getProductByIndex(viewHolder.mPosition);
		if (product == null) {
			Log.e(TAG, "Try to show null product");
			return;
		}	
		
		if (mImagePool.getImage(product.getLogoUrl()) != null) {
			viewHolder.mProductImage.setImageBitmap(mImagePool.getImage(product.getLogoUrl()));
		} else {
			Handler handler = new Handler();
			handler.postDelayed(this, 1000);
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = new ProductListItemView(mContext, null);
			
			viewHolder.mProductImage = (ImageView) convertView.findViewById(R.id.id_image_product);
			viewHolder.mAddBtn = (ImageView) convertView.findViewById(R.id.id_image_order_add);
			viewHolder.mCancelBtn = (ImageView) convertView.findViewById(R.id.id_image_order_cancel);
			viewHolder.mOrderNumText = (TextView) convertView.findViewById(R.id.id_text_order_num);
			viewHolder.mPosition = position;
			
			convertView.setTag(viewHolder);
			viewHolder.mAddBtn.setTag(viewHolder);
			viewHolder.mCancelBtn.setTag(viewHolder);
			viewHolder.mProductImage.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		updateProductImage(viewHolder);
		
		viewHolder.mAddBtn.setOnClickListener(this);
		viewHolder.mCancelBtn.setOnClickListener(this);
		viewHolder.mProductImage.setOnClickListener(this);
		
		return convertView;
	}
	
	private static class ViewHolder {
		ImageView mProductImage;
		ImageView mAddBtn;
		ImageView mCancelBtn;
		TextView mOrderNumText;
		int mOrderNumber = 0;
		int mPosition;
    }
	
	public Shop getShop() {
		return mShop;
	}
	
	public void setShop(Shop shop) {
		mShop = shop;
	}
	
	public void setSwitcher(SwitchFragment switcher) {
		mSwitcher = switcher;
	}
	
	public void refresh() {
		notifyDataSetChanged();
	}

	@Override
	public void run() {
		//updateProductImage();
	}

	@Override
	public void onClick(View v) {
		ViewHolder viewHolder = (ViewHolder) v.getTag();
		Product product = null;
		
		if (viewHolder == null)
			return;
		
		switch (v.getId()) {
		case R.id.id_image_order_add:
			viewHolder.mOrderNumber++;
			viewHolder.mOrderNumText.setText(String.valueOf(viewHolder.mOrderNumber));
			product = (Product)getItem(viewHolder.mPosition);
			product.setOrderNum(viewHolder.mOrderNumber);
			break;
			
		case R.id.id_image_order_cancel:
			viewHolder.mOrderNumber = 0;
			viewHolder.mOrderNumText.setText("");
			product = (Product)getItem(viewHolder.mPosition);
			product.setOrderNum(0);
			break;
			
		case R.id.id_image_product:
			// Show product image
			product = (Product)getItem(viewHolder.mPosition);
			mSwitcher.switchFragment(FragmentIdentity.FRAGMENT_ABOUT, mShop, product.getIntroductionUrl());
			break;
		}
	}
}
