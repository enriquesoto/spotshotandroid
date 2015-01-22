package com.aqpup.waitforit.utils;


import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.aqpup.waitforit.R;
import com.aqpup.waitforit.R.color;
import com.aqpup.waitforit.camera.CameraActivity;
import com.aqpup.waitforit.camera.CloudStorage;
import com.aqpup.waitforit.camera.GCSParams;
import com.aqpup.waitforit.utils.CustomFont;
import com.aqpup.waitforit.utils.CustomGoogleMap;

//import android.R;

/**
 * Simple wrapper around the Google Cloud Storage API
 */
public class TaskCreateBucket extends AsyncTask<Object, Integer, Object> {	
	public Context context;
	public Activity activity;
	private CloudStorage cloudStorage = new CloudStorage();		
	GCSParams gcsParams = new GCSParams();	
	
	protected void onPreExecute() {
		CustomDialog.Loading.showDialogLoading(context, "Configurando",
				"Configurando datos del usuario", 1234);		
	}

	protected Object doInBackground(Object... param) {
		try {
			SharedPreferences sPrefUser = context.getSharedPreferences("Login Credentials", 0);
			Long id = sPrefUser.getLong("usuarioId", 0);
			
			cloudStorage.createBucket(id.toString());
		} catch (Exception e) {
			CustomDialog.showDialogAlert(context, "ERROR", "Error creando bucket", R.drawable.image__warning);
			// TODO Auto-generated catch block
			Log.e("SPOT SHOT", e.getMessage().toString());
			e.printStackTrace();
		}
		
		Long temp = (long) 0;
		return temp;
	}

	protected void onProgressUpdate(Integer... values) {
		CustomDialog.Loading.getPbLoading().setProgress(values[0]);
	}

	protected void onPostExecute(Object result) {
		CustomDialog.Loading.getDialog().dismiss();
		
		Intent i = new Intent(context, CameraActivity.class);
		context.startActivity(i);
		activity.finish();
	}

	
}
