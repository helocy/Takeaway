package org.twoeggs.takeaway.classes;

import java.util.ArrayList;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class User implements LocationListener {
	public static final String TAG = "User";
	public static final int LOCATION_UPDATE_DURATION = 30000;
	
	private String mId;
	private String mUUID;
	private String mName;
	private String mPhoneNum;
	private double mLatitude;
	private double mLongitude;
	private ArrayList<Shop> mFavoriteShops;
	
	public User(Context context) {
		LocationManager locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_UPDATE_DURATION, 0, this);
		
		Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (location != null) {
			mLatitude = location.getLatitude();
			mLongitude = location.getLongitude();
		}
	}
	
	public void setId(String id) {
		mId = id;
	}
	
	public String getId() {
		return mId;
	}
	
	public String getUUID() {
		return mUUID;
	}
	
	public boolean setUUID(String uuid) {
		mUUID = uuid;
		return true;
	}
	
	public double getLatitude() {
		return mLatitude;
	}
	
	public double getLongitude() {
		return mLongitude;
	}
	
	public void setPhoneNum(String phoneNum) {
		mPhoneNum = phoneNum;
	}
	
	public String getPhoneNum() {
		return mPhoneNum;
	}

	@Override
	public void onLocationChanged(Location location) {
		if (location != null) {
			Log.d(TAG, "User locaiton update, mLatitude=" + location.getLatitude() +
					", mLongitude" + location.getLongitude());
			
			mLatitude = location.getLatitude();
			mLongitude = location.getLongitude();
		}
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}
}
