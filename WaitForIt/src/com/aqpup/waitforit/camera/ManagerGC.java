package com.aqpup.waitforit.camera;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import android.util.Log;

import com.aqpup.model.spotshotendpoint.Spotshotendpoint;
import com.aqpup.model.spotshotendpoint.model.GeoPt;
import com.aqpup.model.spotshotendpoint.model.Photo;
import com.aqpup.model.spotshotendpoint.model.Rating;
import com.aqpup.model.spotshotendpoint.model.User;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;





public class ManagerGC {
	
	private User user;	
	public Rating rating;
	private Photo photo;
	private GeoPt geoPoint;
	private Long userId;
	
	public int result;
	public List<Photo> photos = null;
	
	public void ManagerGC(){}
	
	public void addPhoto(Long id, Photo p){
		photo = p;		
		userId = id;
		
		try {
			//Long id = 5629499534213120L;
			
			Spotshotendpoint.Builder builder = new Spotshotendpoint.Builder(
					AndroidHttp.newCompatibleTransport(),
					new JacksonFactory(), null);

			//builder = CloudEndpointUtils.updateBuilder(builder);
			Spotshotendpoint userEndpoint = builder.build();

			user = userEndpoint.addNewPhoto2User(id, p).execute();			

			if (user == null) {	
				result = 0;
				//return 0;
			} else {
				result = 1;
				//return 1;
			}
		} catch (Exception ex) {
			Log.e("SPOT SHOT - TaskAddPhoto", ex.getCause().toString());
			result = -1;
			//return -1;
		}
		
	}
	
	public void getPhotos(Long id, GeoPt geo){
		geoPoint = geo;
		userId = id;
		try {
			Spotshotendpoint.Builder builder = new Spotshotendpoint.Builder(
					AndroidHttp.newCompatibleTransport(),
					new JacksonFactory(), null);
	
			//builder = CloudEndpointUtils.updateBuilder(builder);
			Spotshotendpoint userEndpoint = builder.build();
			
			Long distance = 500L;

			Log.e("SPOT SHOT", "Buscando las fotos");
		
			photos = userEndpoint.getPhotoNeighborhood(
					geo.getLatitude().toString(),
						geo.getLongitude().toString(),  
						distance, 10).execute().getItems();
			
			//photos = userEndpoint.getBestPhoto(userId, geo.getLatitude().toString(), geo.getLongitude().toString()).execute().getItems();
					
			if(photos == null){
				Log.e("SPOT SHOT", "photos nulas");
			}else if(photos.size() > 0){
				Log.e("SPOT SHOT", "Si hay fotos");
			}else{
				Log.e("SPOT SHOT", photos.toString());
			}		
			
			
			
			
			
			result = 1;
			
		} catch (IOException e) {
			Log.e("SPOT SHOT - TaskGetPhoto", e.getCause().toString());
			photos = null;
			result = -1;
			e.printStackTrace();
		}
		//photos = null;
		
	}
	
	public void getRatingByUser(Long id, String photo){
		
		try {
			Spotshotendpoint.Builder builder = new Spotshotendpoint.Builder(
					AndroidHttp.newCompatibleTransport(),
					new JacksonFactory(), null);
	
			//builder = CloudEndpointUtils.updateBuilder(builder);
			Spotshotendpoint userEndpoint = builder.build();			
			
			Log.e("SPOT SHOT - GET_RATING", "Buscando rating de" + id.toString() + " y " + photo);
		
			rating = userEndpoint.getRatingbyUser(id, photo).execute();
			
			if(rating == null){
				result = 0;
				Log.e("SPOT SHOT - GET_RATING", "Rating nulo retornado por end point");
			}else {
				result = 1;
				
			}		
			
			
		} catch (IOException e) {
			Log.e("SPOT SHOT - GET_RATING", e.getCause().toString());
			photos = null;
			result = -1;
			e.printStackTrace();
		}
		//photos = null;
		
	}
	
	public void setRatingPhoto(Rating rat){
		
		try {
			Spotshotendpoint.Builder builder = new Spotshotendpoint.Builder(
					AndroidHttp.newCompatibleTransport(),
					new JacksonFactory(), null);
	
			//builder = CloudEndpointUtils.updateBuilder(builder);
			Spotshotendpoint userEndpoint = builder.build();			
			//rat.setDate(new DateTime());
			int rate = rat.getRate()*-1;
			rat.setRate(rate);
			rating = userEndpoint.updateRating(rat).execute();
			
			if(rating == null){
				result = 0;
				Log.e("SPOT SHOT - GET_RATING", "Rating nulo retornado por end point");
			}else {
				result = 1;
				
			}		
			
			
		} catch (IOException e) {
			Log.e("SPOT SHOT - GET_RATING", e.getCause().toString());
			photos = null;
			result = -1;
			e.printStackTrace();
		}
		//photos = null;
		
	}
	
	public int likePhoto(){
		
		
		return 1;
	}
	
	
}
