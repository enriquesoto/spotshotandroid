package com.aqpup.waitforit.navigation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.aqpup.waitforit.R;
import com.aqpup.waitforit.camera.GPSTracker;
import com.aqpup.waitforit.utils.CustomGoogleMap;
import com.google.android.gms.maps.model.LatLng;

public class MyPhotos_MapFragment extends Fragment implements
		AdapterView.OnItemClickListener {

	private CustomGoogleMap customGoogleMap;
	private static View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (view != null) {
	        ViewGroup parent = (ViewGroup) view.getParent();
	        if (parent != null){
	            parent.removeView(view);
	        }
	    }
		
		view = inflater.inflate(R.layout.fragment_myphotos_map, container,
				false);
		

		customGoogleMap = CustomGoogleMap.getInstanceCGM();

		GPSTracker gps = new GPSTracker(getActivity());
		if (gps.canGetLocation()) {

			customGoogleMap.setmPosition(new LatLng(gps.getLatitude(), gps
					.getLongitude()));
			customGoogleMap.init(getActivity());
			customGoogleMap.showMapa();

			// Toast.makeText(
			// getApplicationContext(),
			// "Your Location is - \nLat: " + latitude + "\nLong: "
			// + longitude, Toast.LENGTH_LONG).show();
		} else {
			gps.showSettingsAlert();
		}

		return view;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();

		customGoogleMap.deleteFragmentMap();

	}
	
}