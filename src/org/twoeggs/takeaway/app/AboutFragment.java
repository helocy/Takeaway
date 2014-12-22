package org.twoeggs.takeaway.app;

import org.twoeggs.takeaway.R;
import org.twoeggs.takeaway.cache.ImagePool;
import org.twoeggs.takeaway.classes.Shop;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class AboutFragment extends Fragment implements Runnable {
	public static final String TAG = "AboutFragment";
	
	private ImageView mAboutImage;
	
	private Shop mShop;
	private String mImageUrl;
	private ImagePool mImagePool;
	private SwitchFragment mSwitcher;
	
	public AboutFragment(Shop shop, String imageUrl, ImagePool imagePool) {
		super();
		mImagePool = imagePool;
		mShop = shop;
		mImageUrl = imageUrl;
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
	
	private void updateUrlImage() {
		Bitmap image = mImagePool.getImage(mImageUrl);
		if (image == null) {
			mAboutImage.setImageResource(R.drawable.about_app);
			
			Handler handler = new Handler();
			handler.postDelayed(this, 1000);
		} else {
			mAboutImage.setImageBitmap(image);
		}
	}
	
	private void initView() {
		mAboutImage = (ImageView) getView().findViewById(R.id.id_image_about);
		
		if (mImageUrl == null)
			mAboutImage.setImageResource(R.drawable.about_app);
		else
			updateUrlImage();
		
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
	}

	@Override
	public void run() {
		updateUrlImage();
	}
}
