package org.twoeggs.takeaway.app;

import org.twoeggs.takeaway.R;
import org.twoeggs.takeaway.cache.ImagePool;
import org.twoeggs.takeaway.classes.Shop;
import org.twoeggs.takeaway.classes.ShopManager;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ShopListAdapter extends BaseAdapter {
	public static final String TAG = "ShopListAdapter";
	
	private Context mContext;
	private ShopManager mShopManager;
	private ImagePool mImagePool;
	private Handler mHandler;
	
	public ShopListAdapter(Context context, ShopManager manager, ImagePool imagePool) {
		mContext = context;
		mShopManager = manager;
		mImagePool = imagePool;
		
		mHandler = new Handler(Looper.getMainLooper()){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				
				switch (msg.what) {
				case HandlerMessage.MSG_UPDATE_IMAGE:
					updateShopImage((ViewHolder)msg.obj);
					break;
					
				default:
					break;
				}
			}
		};
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
			notifyDataSetChanged();
		} else {
			Message msg = new Message();
			msg.what = HandlerMessage.MSG_UPDATE_IMAGE;
			msg.obj = viewHolder;
			mHandler.sendMessageDelayed(msg, 1000);
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
}
