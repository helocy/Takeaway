package org.twoeggs.takeaway.app;

import java.util.UUID;

import org.twoeggs.takeaway.R;
import org.twoeggs.takeaway.classes.ShopManager;
import org.twoeggs.takeaway.server.Request;
import org.twoeggs.takeaway.server.RequestListener;
import org.twoeggs.takeaway.server.SubscribeShopRequest;
import org.twoeggs.takeaway.server.WebService;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class AddShopFragment extends Fragment implements OnClickListener, RequestListener {
	public static final String TAG ="AddShopFragment";
	
	private Button mAboutBtn;
	private Button mReturnBtn;
	private TextView mTitleTextView;
	private EditText mShopCodeEdit;
	private ProgressBar mLoading;
	
	private WebService mService;
	private ShopManager mShopManager;
	
	public AddShopFragment(WebService service, ShopManager shopManager) {
		super();
		mService = service;
		mShopManager = shopManager;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_add_shop, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		initView();
	}
	
	private void initView() {
		mReturnBtn = (Button) getView().findViewById(R.id.id_btn_return);
		mAboutBtn = (Button) getView().findViewById(R.id.id_btn_about);
		mTitleTextView = (TextView) getView().findViewById(R.id.id_text_title);
		mShopCodeEdit = (EditText) getView().findViewById(R.id.id_edit_shop_code);
		mLoading = (ProgressBar) getView().findViewById(R.id.id_loading_add_shop);
		
		mAboutBtn.setVisibility(View.INVISIBLE);
		mTitleTextView.setText(getActivity().getString(R.string.str_add_shop_title));
		mReturnBtn.setOnClickListener(this);
		mLoading.setVisibility(View.INVISIBLE);
		
		mShopCodeEdit.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.toString().length() == 4) {
					addShop(s.toString());
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
	}
	
	private boolean addShop(String code) {
		Log.d(TAG, "Try to add shop code=" + code);
		
		mLoading.setVisibility(View.VISIBLE);
		
		mService.requestSubscribeShop(this, code, "0", UUID.randomUUID().toString());
		
//		InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//		imm.hideSoftInputFromWindow(mShopCodeEdit.getWindowToken(), 0);
//		getActivity().getFragmentManager().popBackStack();
		
		return true;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		mShopCodeEdit.setText("");
		mLoading.setVisibility(View.INVISIBLE);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.id_btn_return:
			getActivity().getFragmentManager().popBackStack();
			break;
		}
	}

	@Override
	public void onRequestComplete(Request request) {
		if (request == null || request.getResult() == null)
			return;

		if (request.getResult().getCode() == 1 && request instanceof SubscribeShopRequest) {
			SubscribeShopRequest realRequest = (SubscribeShopRequest) request;
			if (mShopManager.addShop(realRequest.getShop())) {
				// Add shop success
				InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(mShopCodeEdit.getWindowToken(), 0); 
				getActivity().getFragmentManager().popBackStack();
			} else {
				// Shop has been added before
				mShopCodeEdit.setText("");
				Toast.makeText(getActivity(), R.string.str_error_shop_has_added, Toast.LENGTH_SHORT).show();
			}
		} else {
			// Shop is not exist
			Toast.makeText(getActivity(), R.string.str_error_no_exist_shop, Toast.LENGTH_SHORT).show();
		}
		
		mLoading.setVisibility(View.INVISIBLE);
	}
}
