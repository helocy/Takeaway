package org.twoeggs.takeaway.app;

import java.util.ArrayList;

import org.twoeggs.takeaway.R;
import org.twoeggs.takeaway.classes.Product;
import org.twoeggs.takeaway.classes.Shop;
import org.twoeggs.takeaway.utils.ImageLoader;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.MotionEvent.PointerCoords;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductListAdapter extends BaseAdapter implements Runnable, OnClickListener {
	public static final String TAG = "ProductListAdapter";
	
	private Context mContext;
	private Handler mHandler;
	private SwitchFragment mSwitcher;
	
	private Shop mShop;
	private boolean mBreaker = false;
	
	public ProductListAdapter(Context context) {
		super();
		
		mContext = context;
		
		mHandler = new Handler(Looper.getMainLooper()){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				
				switch (msg.what) {
				case HandlerMessage.MSG_IMAGE_LOADED:
					notifyDataSetChanged();
					break;
					
				default:
					break;
				}
			}
		};
		
		Thread imageLoader = new Thread(this);
		imageLoader.setName(TAG);
		imageLoader.start();
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
		
		Product product = mShop.getProductByIndex(position);
		if (product == null) {
			Log.e(TAG, "Try to show null product");
			return null;
		}
		
		if (product.getLogo() != null)
			viewHolder.mProductImage.setImageBitmap(product.getLogo());
		
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

	public void setShop(Shop shop) {
		mShop = shop;
	}
	
	public Shop getShop() {
		return mShop;
	}
	
	public void setSwitcher(SwitchFragment switcher) {
		mSwitcher = switcher;
	}
	
	public void refresh() {
		notifyDataSetChanged();
	}

	@Override
	public void run() {
		while (true) {
			if (mBreaker)
				break;
			
			if (mShop == null) {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				continue;
			}
			
			ArrayList<Product> products = mShop.getProducts();
			if (products == null)
				continue;
			
			int count = products.size();
			boolean changed = false;
			
			for (int i = 0; i < count; i++) {
				Product product = products.get(i);
				if (product.getLogo() == null) {
					Bitmap logo = ImageLoader.load(product.getLogoUrl());
					product.setLogo(logo);
					changed = true;
				}
				
				if (changed) {
					Message msg = new Message();
					msg.what = HandlerMessage.MSG_IMAGE_LOADED;
					mHandler.sendMessage(msg);
				}
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
			mSwitcher.switchFragment(FragmentIdentity.FRAGMENT_ABOUT, product.getIntroductionUrl());
			break;
		}
	}
}
