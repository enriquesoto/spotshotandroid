package com.aqpup.waitforit.camera;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import com.aqpup.model.spotshotendpoint.Spotshotendpoint;
import com.aqpup.model.spotshotendpoint.model.GeoPt;
import com.aqpup.model.spotshotendpoint.model.Photo;
import com.aqpup.model.spotshotendpoint.model.User;
import com.aqpup.waitforit.R;
import com.aqpup.waitforit.utils.CloudEndpointUtils;
import com.aqpup.waitforit.utils.CustomDialog;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;

class TaskAddPhoto extends AsyncTask<Void, Integer, Integer> {

	private User user;		
	private Photo photo;
	private GeoPt geoPoint;
	private Long userId;
	public Context context;
	public CameraActivity activity = null;
	
	private  ProgressBar pbLoading;
	
	public TaskAddPhoto(Photo ph, GeoPt geo, Long user) {
		photo = ph;	
		geoPoint = geo;
		userId = user;
		
		photo.setGeopt(geoPoint);
	}

	@Override
	protected void onPreExecute() {
		pbLoading = new ProgressBar(activity);
	}

	@Override
	protected Integer doInBackground(Void... params) {
		try {
			//Long id = 5629499534213120L;
			
			Spotshotendpoint.Builder builder = new Spotshotendpoint.Builder(
					AndroidHttp.newCompatibleTransport(),
					new JacksonFactory(), null);

			builder = CloudEndpointUtils.updateBuilder(builder);
			Spotshotendpoint userEndpoint = builder.build();

			user = userEndpoint.addNewPhoto2User(userId, photo).execute();

			if (user == null) {				
				return 0;
			} else {
				return 1;
			}
		} catch (Exception ex) {
			Log.e("SPOT SHOT - TaskAddPhoto", ex.getCause().toString());
			return -1;
		}

	}

	@Override
	protected void onProgressUpdate(Integer... values) {}

	@Override
	protected void onPostExecute(Integer result) {
		
		if(result == 1){
			
		}else if(result == 0){
			Log.e("SPOT SHOT", "null when addingo photo to end point");
			CustomDialog.showDialogAlert(context,
					"Agregando foto", "Error agregando foto",
					R.drawable.image__warning);			
		}else if(result == -1){			
			CustomDialog.showDialogAlert(context,
					"Datos de Usuario Vacios", "Llene los campos ",
					R.drawable.image__warning);
		}

		
	}
}
