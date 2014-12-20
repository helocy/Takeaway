package org.twoeggs.takeaway.app;

import org.twoeggs.takeaway.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ShopListItemView extends LinearLayout {
	public static final String TAG = "ShopListItemView";
	public static double ITEM_ASPECT_W = 29.0;
	public static double ITEM_ASPECT_H = 10.0;
	
	public ShopListItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		LayoutInflater inflater = (LayoutInflater) context
		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.list_item_shop, this, true);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = getResources().getDisplayMetrics().widthPixels;
        int height = (int) (width * ITEM_ASPECT_H / ITEM_ASPECT_W);
	    super.onMeasure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
	}
}
