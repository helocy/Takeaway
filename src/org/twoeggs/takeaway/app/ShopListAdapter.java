package org.twoeggs.takeaway.app;

import org.twoeggs.takeaway.R;
import org.twoeggs.takeaway.cache.ImagePool;
import org.twoeggs.takeaway.classes.Shop;
import org.twoeggs.takeaway.classes.ShopManager;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ShopListAdapter extends BaseAdapter implements Runnable {
	public static final String TAG = "ShopListAdapter";
	
	private Context mContext;
	private ShopManager mShopManager;
	private ImagePool mImagePool;
	
	public ShopListAdapter(Context context, ShopManager manager, ImagePool imagePool) {
		mContext = context;
		mShopManager = manager;
		mImagePool = imagePool;
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
	
	private void updateShopImage(ViewHolder viewHolder) {
		Shop shop = mShopManager.getShopByIndex(viewHolder.mPosition);
		if (shop == null) {
			Log.e(TAG, "Get a null shop");
			return;
		}
		
		if (mImagePool.getImage(shop.getLogoUrl()) != null) {
			viewHolder.mShopLogoView.setImageBitmap(mImagePool.getImage(shop.getLogoUrl()));
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
			
			convertView = new ShopListItemView(mContext, null);
            
            viewHolder.mShopLogoView = (ImageView) convertView.findViewById(R.id.id_image_shop_logo);
            viewHolder.mPosition = position;

            convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		updateShopImage(viewHolder);
		
		return convertView;
	}

	private static class ViewHolder{
		ImageView mShopLogoView;
		int mPosition;
    }

	@Override
	public void run() {
		//updateShopImage();
	}
}
