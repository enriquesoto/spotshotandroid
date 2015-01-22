package com.aqpup.waitforit.utils;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.aqpup.model.spotshotendpoint.model.Photo;
import com.aqpup.waitforit.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class CustomGoogleMap {

	private ArrayList<LatLng> listMarkersNeighbors;
	private GoogleMap map;
	private LatLng mPosition;
	private ActionBarActivity activity;
	private FragmentActivity activityFragment;
	private SupportMapFragment fragmentMap;
	private FragmentManager fragmentManager;

	static private CustomGoogleMap customGoogleMap;

	static public CustomGoogleMap getInstanceCGM() {

		if (customGoogleMap == null) {
			customGoogleMap = new CustomGoogleMap();

		}

		return customGoogleMap;
	}

	private CustomGoogleMap() {

	}

	public void init(Object object) {

		if (object instanceof ActionBarActivity) {

			this.activity = (ActionBarActivity) object;

			if (activity != null) {
				fragmentMap = ((SupportMapFragment) activity
						.getSupportFragmentManager().findFragmentById(R.id.map));
				map = fragmentMap.getMap();
				fragmentManager = activity.getSupportFragmentManager();
			}

		}

		else if (object instanceof FragmentActivity) {

			this.activityFragment = (FragmentActivity) object;

			if (activityFragment != null) {
				fragmentMap = ((SupportMapFragment) activityFragment
						.getSupportFragmentManager().findFragmentById(R.id.map));
				map = fragmentMap.getMap();
				fragmentManager = activityFragment.getSupportFragmentManager();

			}
		}

		listMarkersNeighbors = new ArrayList<LatLng>();

	}

	/*
	 * public void initFragment(FragmentActivity activity) {
	 * 
	 * this.activityFragment = activity; listMarkersNeighbors = new
	 * ArrayList<LatLng>();
	 * 
	 * 
	 * if (activity != null) { map = ((SupportMapFragment)
	 * activityFragment.getSupportFragmentManager()
	 * .findFragmentById(R.id.map)).getMap();
	 * 
	 * } }
	 */

	public void loadData() {

		listMarkersNeighbors.add(new LatLng(-16.400093, -71.538315));
		listMarkersNeighbors.add(new LatLng(-16.400097, -71.523806));
		listMarkersNeighbors.add(new LatLng(-16.419234, -71.551022));
		listMarkersNeighbors.add(new LatLng(-16.397205, -71.519147));
		listMarkersNeighbors.add(new LatLng(-16.429644, -71.482008));
		listMarkersNeighbors.add(new LatLng(-16.456541, -71.521704));

	}

	public void showMapa() {

		loadData();
	//	loadMarkers();

		//fragmentMap.getView().setVisibility(View.VISIBLE);
		
		Marker hamburg = map.addMarker(new MarkerOptions().position(
				mPosition).title("Yo"));
		
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(mPosition, 15));
		fragmentMap.getView().setVisibility(View.VISIBLE);

		// ((SupportMapFragment) activity.getSupportFragmentManager()
		// .findFragmentById(R.id.map)).getView().setVisibility(
		// View.VISIBLE);

	}

	public void hideMap() {

		// Eliminamos views
		// ((ViewManager) pbLoading.getParent()).removeView(pbLoading);
		// ((ViewManager) tvMessage.getParent()).removeView(tvMessage);

		// ((SupportMapFragment) activity.getSupportFragmentManager()
		// .findFragmentById(R.id.map)).getView().setVisibility(View.GONE);

		fragmentMap.getView().setVisibility(View.GONE);
	}

	public void loadMarkers(List<Photo> photos) {

		// Agregar mi posision
		//Marker hamburg = map.addMarker(new MarkerOptions().position(mPosition)
		//		.title("Yo"));

		// Agrega marcadores de mis amigos

		if(photos != null){
			for (int i = 0; i < photos.size(); i++) {
				
				Marker kiel = map.addMarker(new MarkerOptions()
				.position(new LatLng(photos.get(i).getGeopt().getLatitude(), photos.get(i).getGeopt().getLongitude()))
				.title("SpotShotter")
				.snippet("hola")
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.ic_launcher)));
				
			}
		}
		
		// Move the camera instantly to hamburg with a zoom of 15.
		//map.moveCamera(CameraUpdateFactory.newLatLngZoom(mPosition, 15));

		// Zoom in, animating the camera.
		// map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

	}

	public LatLng getmPosition() {
		return mPosition;
	}

	public void setmPosition(LatLng mPosition) {
		this.mPosition = mPosition;
	}

	public SupportMapFragment getFragmentMap() {
		return fragmentMap;
	}

	public void setFragmentMap(SupportMapFragment fragmentMap) {
		this.fragmentMap = fragmentMap;
	}

	public void deleteFragmentMap() {

		if (customGoogleMap.getFragmentMap() != null)
			fragmentManager.beginTransaction()
					.remove(customGoogleMap.getFragmentMap()).commit();

	}

}