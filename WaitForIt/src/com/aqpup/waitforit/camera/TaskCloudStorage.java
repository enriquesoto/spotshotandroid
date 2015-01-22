package com.aqpup.waitforit.camera;

import java.util.List;
import android.os.AsyncTask;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aqpup.model.spotshotendpoint.model.GeoPt;
import com.aqpup.model.spotshotendpoint.model.Photo;
import com.aqpup.model.spotshotendpoint.model.Rating;
import com.aqpup.waitforit.R;
import com.aqpup.waitforit.R.color;
import com.aqpup.waitforit.utils.CustomFont;
import com.aqpup.waitforit.utils.CustomGoogleMap;

//import android.R;

/**
 * Simple wrapper around the Google Cloud Storage API
 */
public class TaskCloudStorage extends AsyncTask<Object, Integer, Object> {

	public enum ActionRequest {
		CREATE_BUCKET, DELETE_BUCKET, UPLOAD_OBJECT, DOWNLOAD_OBJECT, DELETE_OBJECT, 
		LIST_BUCKETS, LIST_BUCKET, ADD_PHOTO, GET_PHOTO, GET_RATING, SET_RATING
	}

	static public boolean isMapLoad = false;
	
	 public ActionRequest actionRequest = null;
	
	public List<String> listResult = null;
	public boolean finished = false;
	public CameraActivity activity = null;
	
	//private Storage storage = null;
	
	//ProgressDialog progDailog = null;

	private  ProgressBar pbLoading;
	private  TextView tvMessage;
	private LinearLayout llLoadingMap;
	private LinearLayout llCamera;
	
	private CloudStorage cloudStorage = new CloudStorage();	
	public ManagerGC manager = new ManagerGC();
	
	public Photo photo;
	public GeoPt geoPoint;
	public Long userId;
	
	GCSParams gcsParams = new GCSParams();	
	
	protected void onPreExecute() {
		
		pbLoading = new ProgressBar(activity);
		tvMessage = new TextView(activity);
		llLoadingMap = new LinearLayout(activity);
		llCamera = (LinearLayout) activity.findViewById(R.id.llCamera);
		
		
		llLoadingMap.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		llLoadingMap.setGravity(17);
		llLoadingMap.setOrientation(1);
		llLoadingMap.setBackgroundColor(activity.getResources().getColor(color.blue_dark_ss));
		
		 //LinearLayout.LayoutParams llparams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 100);
		//llparams.gravity = 17;
		//tvMessage.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, 200));
		tvMessage.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,50));
		tvMessage.setTypeface(CustomFont.getTfFontMessage());
		tvMessage.setGravity(17);
		tvMessage.setTextColor(activity.getResources().getColor(android.R.color.white));
		tvMessage.setTextSize(18.0f);
		
		pbLoading.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,200));
			
		switch (actionRequest) {
		
		case CREATE_BUCKET:
			//progDailog.setMessage("Preparando datos...");
			//tvMessage.setText("Preparando datos..");
		   //CustomGoogleMap.getInstanceCGM().showMapa();			
			break;		
		case UPLOAD_OBJECT:
			//progDailog.setMessage("Compartiendo la foto...");
			tvMessage.setText("Compartiendo la foto...");
			//CustomGoogleMap.getInstanceCGM().showMapa();
			break;
		case DOWNLOAD_OBJECT:
			//progDailog.setMessage("Descargando la foto...");
			tvMessage.setText("Descargando la foto...");
			break;		
		case ADD_PHOTO:
			//progDailog.setMessage("Descargando la foto...");
			tvMessage.setText("Enlazando la foto...");
			break;	
		case GET_PHOTO:
			//progDailog.setMessage("Descargando la foto...");
			CustomGoogleMap.getInstanceCGM().showMapa();
			tvMessage.setText("Buscando la mejor foto...");
			break;	
		case GET_RATING:
			tvMessage.setText("Obteniendo rating...");
			break;	
		case SET_RATING:
			tvMessage.setText("Seteando rating...");
			break;
		default:
			//progDailog.setMessage("Loading...");
			tvMessage.setText("Loading...");
			break;
		}
		
        //progDailog.setIndeterminate(false);
        //progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //progDailog.setCancelable(true);
        //progDailog.show();
		
		pbLoading.setProgress(0);

		llLoadingMap.addView(tvMessage);
		llLoadingMap.addView(pbLoading);
		llCamera.addView(llLoadingMap);
	}

	protected Object doInBackground(Object... param) {
		Long temp = (long) 0;

		List<String> resultList = null;

		try {
			switch (actionRequest) {
			case LIST_BUCKET:
				resultList = cloudStorage.listBucket((String) param[0]);
				break;
			case LIST_BUCKETS:
				resultList = cloudStorage.listBuckets();
				break;
			case CREATE_BUCKET:
				cloudStorage.createBucket((String) param[0]);
				break;
			case DELETE_BUCKET:
				cloudStorage.deleteBucket((String) param[0]);
				break;
			case UPLOAD_OBJECT:
				cloudStorage.uploadFile((String) param[0], (String) param[1]);
				break;
			case DOWNLOAD_OBJECT:
				cloudStorage.downloadFile((String) param[0], (String) param[1],
						(String) param[2]);
				break;
			case DELETE_OBJECT:
				cloudStorage.deleteFile((String) param[0], (String) param[1]);
				break;
				
			case ADD_PHOTO:
				manager.addPhoto((Long)param[0], (Photo)param[1]);
				break;	
			case GET_PHOTO:
				//manager.getPhotos(geoPoint, userId);
				manager.getPhotos((Long)param[0], (GeoPt)param[1]);
			case GET_RATING:
				manager.getRatingByUser((Long)param[0], (String)param[1]);
			case SET_RATING:
				manager.setRatingPhoto((Rating)param[0]);			
				break;		
				
			default:
				break;
			}

			return (Object) resultList;

		} catch (Exception e) {

			Log.e("SPOTSHOT " + actionRequest, "error");
			Log.e("SPOTSHOT " + actionRequest, e.getMessage().toString());

		}

		return temp;
	}

	protected void onProgressUpdate(Integer... progress) {
		
		pbLoading.setProgress(progress[0]);

	}

	protected void onPostExecute(Object result) {

		//pbLoading.setVisibility(View.INVISIBLE);
		//tvMessage .setVisibility(View.INVISIBLE);

		//Eliminamos views
		((ViewManager) llLoadingMap.getParent()).removeView(llLoadingMap);
	//	((ViewManager) pbLoading.getParent()).removeView(pbLoading);
	//	((ViewManager) tvMessage.getParent()).removeView(tvMessage);
		
		switch (actionRequest) {
		case LIST_BUCKET:
			listResult = (List<String>) result;
			Log.e("SPOTSHOT", ((List<String>) result).toString());
			break;
		case LIST_BUCKETS:
			listResult = (List<String>) result;
			Log.e("SPOTSHOT", ((List<String>) result).toString());
			break;
		case CREATE_BUCKET:

			break;
		case DELETE_BUCKET:

			break;
		case UPLOAD_OBJECT:

			break;
		case DOWNLOAD_OBJECT:

			break;
		case DELETE_OBJECT:

			break;
			
		case ADD_PHOTO:			
			Log.e("SPOTSHOT", "Se agrego la foto en el end point");
			break;
		case GET_PHOTO:			
			Log.e("SPOTSHOT", "Se obtuvieron las fotos de los end poinst");
			break;
		case GET_RATING:			
			Log.e("SPOTSHOT", "Se obtuvo el rating");
			break;
		case SET_RATING:			
			Log.e("SPOTSHOT", "Se seteo el rating");
			break;
		default:
			break;
		}

//		progDailog.dismiss();
		
		finished = true;
		activity.checkStatusTask(true);
	}

	
}
