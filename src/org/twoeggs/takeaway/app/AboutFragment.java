package org.twoeggs.takeaway.app;

import org.twoeggs.takeaway.R;
import org.twoeggs.takeaway.classes.Shop;
import org.twoeggs.takeaway.utils.ImageLoader;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class AboutFragment extends Fragment implements Runnable {
	public static final String TAG = "AboutFragment";
	
	private ImageView mAboutImage;
	
	private Shop mShop;
	private String mImageUrl;
	private Bitmap mImage;
	private SwitchFragment mSwitcher;
	private Handler mHandler;
	
	public AboutFragment(Shop shop, String imageUrl) {
		super();
		mShop = shop;
		mImageUrl = imageUrl;
		
		mHandler = new Handler(Looper.getMainLooper()) {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				
				switch (msg.what) {
				case HandlerMessage.MSG_ABOUT_LOADED:
					mAboutImage.setImageBitmap(mImage);
					break;
				}
			}
		};
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_about, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		initView();
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		mSwitcher = (SwitchFragment)activity;
	}
	
	private void initView() {
		mAboutImage = (ImageView) getView().findViewById(R.id.id_image_about);
		
		if (mImageUrl == null) {
			mAboutImage.setImageResource(R.drawable.about_app);
		} else {
			if (mImage == null) {
				mAboutImage.setImageResource(R.drawable.about_app);
				
				Thread thread = new Thread(this);
				thread.setName(TAG);
				thread.start();
			} else {
				mAboutImage.setImageBitmap(mImage);
			}
		}
		
		mAboutImage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mImageUrl == null)
					getActivity().getFragmentManager().popBackStack();
				else {
					mSwitcher.switchFragment(FragmentIdentity.FRAGMENT_PRODUCT_LIST, mShop, null);
				}
			}
		});
	}
	
	public void update(Shop shop, String imageUrl) {
		if (imageUrl != null && imageUrl.equals(mImageUrl))
			return;

		mShop = shop;
		mImageUrl = imageUrl;
		mImage = null;
	}

	@Override
	public void run() {
		if (mImageUrl == null)
			return;
		
		mImage = ImageLoader.load(mImageUrl);
		
		Message msg = new Message();
		msg.what = HandlerMessage.MSG_ABOUT_LOADED;
		mHandler.sendMessage(msg);
	}
}
