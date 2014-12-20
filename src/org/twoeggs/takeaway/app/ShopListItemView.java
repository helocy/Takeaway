package org.twoeggs.takeaway.app;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class ShopListItemView extends LinearLayout {
	public static final String TAG = "ShopListItemView";
	public static double ITEM_ASPECT_W = 29.0;
	public static double ITEM_ASPECT_H = 10.0;
	
	public ShopListItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = getResources().getDisplayMetrics().widthPixels;
		int height = (int) (width * ITEM_ASPECT_H / ITEM_ASPECT_W);
		
		setMeasuredDimension(width, height); 
	}
}
