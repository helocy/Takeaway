package org.twoeggs.takeaway.app;

import org.twoeggs.takeaway.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class ProductListItemView extends LinearLayout {
	public static final String TAG = "ProductListItemView";
	public static double ITEM_ASPECT_W = 3.0;
	public static double ITEM_ASPECT_H = 1.0;
	public static int IMG_W = 441;
	public static int IMG_H = 221;
	public static int BTN_W = 220;
	public static int BTN_H = 220;
	
	private RelativeLayout mBtnLayout;
	private ImageView mOrderAddImage;
	private ImageView mOrderCancelImage;
	private ImageView mProductImage;

	public ProductListItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		LayoutInflater inflater = (LayoutInflater) context
		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.list_item_product, this, true);
		
		mBtnLayout = (RelativeLayout) view.findViewById(R.id.id_layout_order_btn);
		mOrderAddImage = (ImageView) view.findViewById(R.id.id_image_order_add);
		mOrderCancelImage = (ImageView) view.findViewById(R.id.id_image_order_cancel);
		mProductImage = (ImageView) view.findViewById(R.id.id_image_product);
		
		measureChilds();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = getResources().getDisplayMetrics().widthPixels;
		int height = (int)(width * ITEM_ASPECT_H / ITEM_ASPECT_W);
		
		super.onMeasure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
	}
	
	private void measureChilds() {
		ViewGroup.LayoutParams layoutParams = null;

		int width = getResources().getDisplayMetrics().widthPixels;
		int height = (int)(width * ITEM_ASPECT_H / ITEM_ASPECT_W);
		
		layoutParams = mBtnLayout.getLayoutParams();
		layoutParams.width = layoutParams.height = height;
		mBtnLayout.setLayoutParams(layoutParams);
		
		layoutParams = mOrderAddImage.getLayoutParams();
		layoutParams.width = layoutParams.height = height;
		mOrderAddImage.setLayoutParams(layoutParams);
		
		layoutParams = mOrderCancelImage.getLayoutParams();
		layoutParams.width = layoutParams.height = height / 2;
		mOrderCancelImage.setLayoutParams(layoutParams);
		
		layoutParams = mProductImage.getLayoutParams();
		layoutParams.width = width - height;
		layoutParams.height = height;
		mProductImage.setLayoutParams(layoutParams);
	}
}
