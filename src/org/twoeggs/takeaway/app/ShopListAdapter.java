package org.twoeggs.takeaway.app;

import org.twoeggs.takeaway.R;
import org.twoeggs.takeaway.classes.Shop;
import org.twoeggs.takeaway.classes.ShopManager;
import org.twoeggs.takeaway.utils.ImageLoader;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ShopListAdapter extends BaseAdapter implements Runnable {
	public static final String TAG = "ShopListAdapter";
	
	private Context mContext;
	private ShopManager mShopManager;
	private Handler mHandler;
	private Thread mImageLoader;
	private boolean mBreaker = false;
	
	public ShopListAdapter(Context context, ShopManager manager) {
		mContext = context;
		mShopManager = manager;
		
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
		
		mImageLoader = new Thread(this);
		mImageLoader.setName(TAG);
		mImageLoader.start();
	}

	@Override
	public int getCount() {
		return mShopManager.getCount();
	}

	@Override
	public Object getItem(int position) {
		return mShopManager.getShopByIndex(position);
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
			
			convertView = new ShopListItemView(mContext, null);
            
            viewHolder.mShopLogoView = (ImageView) convertView.findViewById(R.id.id_image_shop_logo);

            convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		Shop shop = mShopManager.getShopByIndex(position);
		if (shop == null) {
			Log.e(TAG, "Get a null shop");
			return null;
		}
		
		if (shop.getLogo() != null)
			viewHolder.mShopLogoView.setImageBitmap(shop.getLogo());
		
		return convertView;
	}

	private static class ViewHolder{
		ImageView mShopLogoView;
    }

	@Override
	public void run() {
		
		while (true) {
			if (mBreaker)
				break;
			
			int count = mShopManager.getCount();
			boolean changed = false;
			
			for (int i = 0; i < count; i++) {
				Shop shop = mShopManager.getShopByIndex(i);
				if (shop.getLogo() == null) {
					Bitmap logo = ImageLoader.load(shop.getLogoUrl());
					shop.setLogo(logo);
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
}
