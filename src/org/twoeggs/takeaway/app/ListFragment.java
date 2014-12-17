package org.twoeggs.takeaway.app;

import org.twoeggs.takeaway.R;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public abstract class ListFragment extends Fragment implements View.OnClickListener, OnItemClickListener {
	public static final String TAG = "ListFragment";
	
	private String mTitle;
	private boolean mHasReturnBtn;
	private SwitchFragment mSwitcher;
	private BaseAdapter mAdapter; 
	
	private Button mAboutBtn;
	private Button mReturnBtn;
	private TextView mTitleTextView;
	private ListView mListView;
	
	public ListFragment(boolean hasReturnBtn) {
		super();
		mHasReturnBtn = hasReturnBtn;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_list, container, false);
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
		mReturnBtn = (Button) getView().findViewById(R.id.id_btn_return);
		mAboutBtn = (Button) getView().findViewById(R.id.id_btn_about);
		mTitleTextView = (TextView) getView().findViewById(R.id.id_text_title);
		mListView = (ListView) getView().findViewById(R.id.id_list_view);
		
		mListView.setAdapter(mAdapter);
		mTitleTextView.setText(mTitle);
		if (mHasReturnBtn == false)
			mReturnBtn.setVisibility(View.INVISIBLE);
		
		mListView.setOnItemClickListener(this);
		
		mAboutBtn.setOnClickListener(this);
		mReturnBtn.setOnClickListener(this);
	}
	
	public void setTitle(String title) {
		mTitle = title;
		if (mTitleTextView != null) {
			mTitleTextView.setText(mTitle);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.id_btn_return:
			getActivity().getFragmentManager().popBackStack();
			break;
			
		case R.id.id_btn_about:
			mSwitcher.switchFragment(FragmentIdentity.FRAGMENT_ABOUT, null);
			break;
		}
	}
	
	public void setListAdapter(BaseAdapter adapter) {
		mAdapter = adapter;
	}
	
	public BaseAdapter getAdapter() {
		return mAdapter;
	}
	
	public SwitchFragment getSwitcher() {
		return mSwitcher;
	}
	
	public void refresh() {
		mAdapter.notifyDataSetChanged();
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		return;
	}
}
