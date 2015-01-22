package com.aqpup.waitforit.camera;

import java.io.File;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

//import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.aqpup.model.spotshotendpoint.model.GeoPt;
import com.aqpup.model.spotshotendpoint.model.Photo;
import com.aqpup.model.spotshotendpoint.model.Rating;
import com.aqpup.waitforit.R;

import com.aqpup.waitforit.camera.TaskCloudStorage.ActionRequest;
import com.aqpup.waitforit.navigation.DashBoardActivity;
import com.aqpup.waitforit.utils.CustomDialog;
import com.aqpup.waitforit.utils.CustomGoogleMap;
import com.google.android.gms.maps.model.LatLng;

public class CameraActivity extends ActionBarActivity  {

	private ResizableCameraPreview mPreview;
    private RelativeLayout mLayout;
    private int mCameraId = 0;
	
	
	
	//Preview preview;
	Button buttonClick;
	//Camera camera;
	ActionBarActivity act;
	Context ctx;

	ManagerGC manager;
	CameraActivityModel model;
	GCSParams gcsParams = new GCSParams();

	private ImageView cameraImage;
	//private SurfaceView cameraPreview;

	private String message;

	String currentPhotoPath;
	String currentPhotoName;
	String currentPhotoNameDownloaded;
	String bucketName;

	boolean savedPhoto = false;
	private ActionRequest actionRequest;

	// ///////////////////////////////////////////
	private ImageView btnTakePhoto;
	private ImageView btnFlash;
	private ImageView btnFriends;
	private ImageView btnFrontCamera;
	private EditText txtMessage;
	private LinearLayout llFriends;
	private ImageView ivLine;

	private Bitmap actualBitmap;
	
	private boolean btnFlashActive = false;
	private boolean btnFrontCameraActive = false;
	private boolean cameraPaused = false;
	private boolean inPreview = false;
	// ///////////////////////////////////////////////////

	double latitude;
	double longitude;
	
	TaskCloudStorage cloudAddPhoto = null;
	TaskCloudStorage cloudGetPhoto = null;
	TaskCloudStorage cloudGetRating = null;
	TaskCloudStorage cloudSetRating = null;
	
	private List<Photo> photos = null;
	private Rating rating = null;

	private CustomGoogleMap customGoogleMap;
	
	private boolean takingPhoto = true;
	// //////////////////////////////////////////////////////////////////////////////////////
	// EVENTOS DE LOS BOTONES
	// //////////////////////////////////////////////////////////////////////////////////////

	private OnClickListener likeImageButtonClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			//Toast.makeText(ctx, "Te gusta", Toast.LENGTH_LONG).show();
			setRating();
		}
	};

	private OnClickListener chatImageButtonClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Toast.makeText(ctx, "En construccion", Toast.LENGTH_LONG).show();
		}
	};

	private OnClickListener captureImageButtonClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {			
			
			btnTakePhoto.setClickable(false);
			//cameraPaused = true;
			
			//if(takingPhoto ){
			if(inPreview ){
				Log.e("SPOT SHOT CAMARA", "taking foto true");
				System.gc();
				mPreview.mCamera.takePicture(null, null, jpegCallback);
				inPreview=false;
			}						
			else
				Log.e("SPOT SHOT CAMARA", "taking foto false");
		}
	};

	private OnClickListener escribirButtonClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {

			//Toast.makeText(ctx, "A escribir", Toast.LENGTH_LONG).show();

			txtMessage.setVisibility(View.VISIBLE);
			txtMessage.setFocusable(true);
			txtMessage.requestFocus();
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.showSoftInput(txtMessage, InputMethodManager.SHOW_IMPLICIT);
		}
	};

	private OnClickListener flashImageButtonClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			btnFlashActive = !btnFlashActive;
 			PackageManager pm=ctx.getPackageManager();
			  final Parameters p = mPreview.mCamera.getParameters();
			  if(isFlashSupported(pm, p)){		
			   if (btnFlashActive) {
			    Log.i("info", "torch is turn on!");
			    p.setFlashMode(Parameters.FLASH_MODE_TORCH);
			   
			   } else {
			    Log.i("info", "torch is turn off!");
			    p.setFlashMode(Parameters.FLASH_MODE_OFF);
			   
			   }
			   mPreview.mCamera.setParameters(p);
			  }
		}
	};
	
	private boolean isFlashSupported(PackageManager packageManager, Parameters p){ 
	  	  // if device support camera flash?
	  	  if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
	  		  
	  		List<String> flashModes = p.getSupportedFlashModes();
	  		//Log.e("SPOTT", flashModes.toString());
	  		
	  		if(flashModes == null) {
	  	        return false;
	  	    }

	  	    for(String flashMode : flashModes) {
	  	        if(Parameters.FLASH_MODE_ON.equals(flashMode)) {
	  	            return true;
	  	        }
	  	    }
	  	    return false;	  	   
	  	  } 
	  	  return false;
	  	 }

	private OnClickListener frontCameraImageButtonClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			//setCameraFrontBack();
			mPreview.stop();
            mLayout.removeView(mPreview);
            
            btnFrontCameraActive = !btnFrontCameraActive;
            
            if(btnFrontCameraActive){
            	mCameraId = 1;
            }else
            	mCameraId = 0;
            
            createCameraPreview();
		}
	};

	private OnClickListener recaptureImageButtonClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			//cameraPaused = false;
			startCamera();
			resetMessage();

			cameraImage.setVisibility(View.INVISIBLE);
			mLayout.setVisibility(View.VISIBLE);			

			btnTakePhoto.setImageResource(R.drawable.image__take_photo);
			btnTakePhoto.setOnClickListener(captureImageButtonClickListener);

			btnFlash.setImageResource(R.drawable.image_btn_flash);
			btnFlash.setOnClickListener(flashImageButtonClickListener);

			btnFrontCamera
					.setImageResource(R.drawable.image_btn_cambiar_camara);
			btnFrontCamera
					.setOnClickListener(frontCameraImageButtonClickListener);

			// btnFriends.setVisibility(View.VISIBLE);
			llFriends.setVisibility(View.VISIBLE);
			ivLine.setVisibility(View.VISIBLE);
			
		}
	};

	private OnClickListener uploadImageButtonClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			sendPhoto();
		}
	};

	
	// //////////////////////////////////////////////////////////////////////////////////////
	// FIN EVENTOS DE LOS BOTONES
	// //////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		boolean isOptedOut = GoogleAnalytics.getInstance(this).getAppOptOut();
		Log.e("SPOT SHOT", ""+isOptedOut );
		
		//Get a Tracker (should auto-report)
		Tracker t = ((SpotShot) getApplication()).getTracker(SpotShot.TrackerName.APP_TRACKER);
		t.setScreenName("Camera");
        t.send(new HitBuilders.AppViewBuilder().build());
        
        //t.setScreenName("Register");
        //t.send(new HitBuilders.AppViewBuilder().build());
        
        //t.setScreenName("Login");
        //t.send(new HitBuilders.AppViewBuilder().build());
		
		ctx = this;
		act = this;

		manager = new ManagerGC();
		model = new CameraActivityModel(ctx);

		Toast.makeText(ctx, "Empiece a compartir sus mejores momentos",
				Toast.LENGTH_LONG).show();

		if (!model.checkStatusStorage()) {
			Toast.makeText(ctx, "Problema con la memoria externa",
					Toast.LENGTH_LONG).show();
			return;
		}
		if (!model.CheckFolderAndKey(getAssets())) {
			Toast.makeText(ctx, "Problema interno 0001", Toast.LENGTH_LONG)
					.show();
			return;
		}

		currentPhotoPath = Environment.getExternalStorageDirectory()
				+ gcsParams.TEMP_PATH;
		
		setContentView(R.layout.activity_camera);
		
		mLayout = (RelativeLayout) findViewById(R.id.layout);
		
		customGoogleMap = CustomGoogleMap.getInstanceCGM();
		customGoogleMap.init(act);
		customGoogleMap.hideMap();
		
		txtMessage = (EditText) findViewById(R.id.txt_message);
		txtMessage.setVisibility(View.INVISIBLE);

		cameraImage = (ImageView) findViewById(R.id.camera_image_view);
		cameraImage.setVisibility(View.INVISIBLE);
		
		btnTakePhoto = (ImageView) findViewById(R.id.btn_camera_take_photo);
		btnTakePhoto.setOnClickListener(captureImageButtonClickListener);

		btnFlash = (ImageView) findViewById(R.id.btn_camera_flash);
		btnFlash.setOnClickListener(flashImageButtonClickListener);

		btnFrontCamera = (ImageView) findViewById(R.id.btn_camera_front);
		btnFrontCamera.setOnClickListener(frontCameraImageButtonClickListener);

		llFriends = (LinearLayout) findViewById(R.id.llFriends);
		ivLine = (ImageView) findViewById(R.id.ivLine_1);

		btnFriends = (ImageView) findViewById(R.id.btn_friends);
		btnFriends.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(ctx, DashBoardActivity.class);
				startActivity(i);
			}
		});

	}
	
	@Override
	protected void onStart() {
		super.onResume();		
		//Get an Analytics tracker to report app starts & uncaught exceptions etc.
		GoogleAnalytics.getInstance(this).reportActivityStart(this);	
	}	
	
	@Override
	protected void onStop() {
		super.onResume();		
		//Stop the analytics tracking
		GoogleAnalytics.getInstance(this).reportActivityStop(this);	
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		GoogleAnalytics.getInstance(this).reportActivityStart(this);		
		
		startCamera();		
	}	

	@Override
	protected void onPause() {
		super.onPause();	
		
		stopCamera(); 
	}

	private void startCamera(){
		Log.e("SPOT SHOT CAMARA", "onResume");		
		try{
			createCameraPreview();
		}catch(Exception e){
			Log.e("SPOT SHOT CAMARA onResume CATCH ", e.getMessage());
		}		
	}
	private void stopCamera(){
		
		Log.e("SPOT SHOT CAMARA", "onPause");		
		try{
			
			//if(cameraPaused) {
				Log.e("STOPING...", "1");
				mPreview.stop();
				
				Log.e("STOPING...", "2");
				
				if(mPreview != null)
					mLayout.removeView(mPreview);
		        
		        
		        
		        Log.e("STOPING...", "3");
		        mPreview = null;
			//}
		        
		        inPreview=false;
			
		}catch(Exception e){
			Log.e("SPOT SHOT CAMARA onPause CATCH", "");
		}		
	}
	
	private void createCameraPreview() {
        // Set the second argument by your choice.
        // Usually, 0 for back-facing camera, 1 for front-facing camera.
        // If the OS is pre-gingerbreak, this does not have any effect.
        mPreview = new ResizableCameraPreview(this, mCameraId, CameraPreview.LayoutMode.FitToParent, false);
        LayoutParams previewLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mLayout.addView(mPreview, 0, previewLayoutParams);  
        
        inPreview=true;
    }	

	private void resetMessage() {
		message = txtMessage.getText().toString();
		txtMessage.setVisibility(View.INVISIBLE);
		txtMessage.setText("");
	}	

	private void refreshGallery(File file) {
		Intent mediaScanIntent = new Intent(
				Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		mediaScanIntent.setData(Uri.fromFile(file));
		sendBroadcast(mediaScanIntent);
	}
	
	PictureCallback jpegCallback = new PictureCallback() {

		public void onPictureTaken(byte[] data, Camera camera) {
			cameraPaused = true;
			stopCamera();
			inPreview=true;
			
			//Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);			
			//Log.e("SPOTTTT", "altura foto"+bitmap.getHeight() + "");
			//Log.e("SPOTTTT", "anchura foto "+bitmap.getWidth() + "");
			//int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));
			//Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
			
			
			BitmapFactory.Options options=new BitmapFactory.Options();// Create object of bitmapfactory's option method for further option use
            options.inPurgeable = true; // inPurgeable is used to free up memory while required
            Bitmap bitmap = BitmapFactory.decodeByteArray(data,0, data.length,options);//Decode image, "thumbnail" is the object of image file
            int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));
            Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512 , nh , true);// convert decoded bitmap into well scalled Bitmap format.

			

			Matrix matrix = new Matrix();		
			
			if (btnFrontCameraActive) {
				matrix.postRotate(270);
				Bitmap rotated = Bitmap.createBitmap(scaled, 0, 0,
						scaled.getWidth(), scaled.getHeight(), matrix, true);
				
				actualBitmap = flip(rotated);
				cameraImage.setImageBitmap(actualBitmap);
				
			} else {
				matrix.postRotate(90);
				Bitmap rotated = Bitmap.createBitmap(scaled, 0, 0,
						scaled.getWidth(), scaled.getHeight(), matrix, true);			
				cameraImage.setImageBitmap(rotated);
				actualBitmap = rotated;
			}

			//cameraImage.setImageBitmap(scaled);
			cameraImage.setVisibility(View.VISIBLE);
			mLayout.setVisibility(View.INVISIBLE);

			// btnFriends.setVisibility(View.GONE);
			llFriends.setVisibility(View.GONE);
			ivLine.setVisibility(View.GONE);

			btnFlash.setImageResource(R.drawable.image_btn_subir);
			btnFlash.setOnClickListener(uploadImageButtonClickListener);

			btnFrontCamera.setImageResource(R.drawable.image_btn_escribir);
			btnFrontCamera.setOnClickListener(escribirButtonClickListener);

			btnTakePhoto.setImageResource(R.drawable.image__take_photo);
			btnTakePhoto.setOnClickListener(recaptureImageButtonClickListener);
			
			
			btnTakePhoto.setClickable(true);
			cameraPaused = false;
			
		}
	};

	Bitmap flip(Bitmap d)
	{
	    Matrix m = new Matrix();
	    m.preScale(-1, 1);
	    Bitmap src = d;
	    Bitmap dst = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), m, false);
	    dst.setDensity(DisplayMetrics.DENSITY_DEFAULT);
	    return dst;
	}
	
	public void sendPhoto() {
		resetMessage();

		currentPhotoName = model.saveImage(
				(BitmapDrawable) cameraImage.getDrawable(), currentPhotoPath);

		/*GPSTracker gps = new GPSTracker(this);
		if (gps.canGetLocation()) {
			
			latitude = gps.getLatitude();
			longitude = gps.getLongitude();
	
			customGoogleMap.setmPosition(new LatLng(latitude, longitude));
			//Toast.makeText(
				//	getApplicationContext(),
					//"Your Location is - \nLat: " + latitude + "\nLong: "
						//	+ longitude, Toast.LENGTH_LONG).show();
		} else {
			gps.showSettingsAlert();
		}*/
		//createBucket();
		
		getPhoto();
		//uploadPhoto();
	}	

	public void checkStatusTask(boolean r) {
		switch (actionRequest) {
		case CREATE_BUCKET:
			uploadPhoto();
			break;
		case UPLOAD_OBJECT:			 
			addPhoto();			
			break;
		case ADD_PHOTO:
			if(cloudAddPhoto.manager.result == 0){
				CustomDialog.showDialogAlert(ctx,"Agregando foto", "Error agregando foto", R.drawable.image__warning);
				showCameraAndButtons();	
			}					
			if(cloudAddPhoto.manager.result == -1){
				CustomDialog.showDialogAlert(ctx,"Error", "Error catch", R.drawable.image__warning);
				showCameraAndButtons();	
			}else{
				//getPhoto();
				downloadPhoto();
			}		
			
			
			break;
			
		case GET_PHOTO:
			if(cloudGetPhoto.manager.result == 0){
				CustomDialog.showDialogAlert(ctx,"End Point", "Escogiendo foto", R.drawable.image__warning);
				showCameraAndButtons();	
			}					
			if(cloudGetPhoto.manager.result == -1){
				CustomDialog.showDialogAlert(ctx,"Error", "Error catch", R.drawable.image__warning);
				showCameraAndButtons();	
			}else{
				photos = cloudGetPhoto.manager.photos;				
				//downloadPhoto();
				
				//mostramos las fotos cercanas
				customGoogleMap.loadMarkers(photos);
				
				//uploadPhoto();
				getRating();
			}			
			break;
		case GET_RATING:
			if(cloudGetRating.manager.result == 0){
				CustomDialog.showDialogAlert(ctx,"End Point", "Obteniendo rating", R.drawable.image__warning);
				showCameraAndButtons();	
			}					
			if(cloudGetRating.manager.result == -1){
				CustomDialog.showDialogAlert(ctx,"Error", "Error catch - obteniendo rating", R.drawable.image__warning);
				showCameraAndButtons();	
			}else{
				rating = cloudGetRating.manager.rating;	
				setLikeButton();
				uploadPhoto();				
			}			
			break;
		case SET_RATING:
			//showCameraAndButtons();
			((LinearLayout) findViewById(R.id.llbuttonCamera)).setVisibility(View.VISIBLE);
			((LinearLayout) findViewById(R.id.lloptionsCamera)).setVisibility(View.VISIBLE);
			
			if(cloudSetRating.manager.result == 0){
				CustomDialog.showDialogAlert(ctx,"End Point", "Seteando rating", R.drawable.image__warning);
				showCameraAndButtons();	
			}					
			if(cloudSetRating.manager.result == -1){
				CustomDialog.showDialogAlert(ctx,"Error", "Error catch - seteando rating", R.drawable.image__warning);
				showCameraAndButtons();	
			}else{
				rating = cloudSetRating.manager.rating;	
				//CustomDialog.showDialogAlert(ctx,"Spot Shot", "Te gusta :)", R.drawable.image__warning);
				
				if(rating.getRate() == -1)
					Toast.makeText(ctx, "Ya no te gusta :(", Toast.LENGTH_LONG).show();
				else
					Toast.makeText(ctx, "Te gusta :)", Toast.LENGTH_LONG).show();
				
				setLikeButton();
			}			
			break;
		case DOWNLOAD_OBJECT:
			// Quita el map
			showCameraAndButtons();			

			File imgFile = new File(currentPhotoPath
					+ currentPhotoNameDownloaded);
			Bitmap myBitmap = BitmapFactory.decodeFile(imgFile
					.getAbsolutePath());
			
			cameraImage.setImageBitmap(myBitmap);

			//btnFlash.setImageResource(R.drawable.image_btn_like);
			btnFlash.setOnClickListener(likeImageButtonClickListener);

			btnFrontCamera.setImageResource(R.drawable.image_btn_chat);
			btnFrontCamera.setOnClickListener(chatImageButtonClickListener);

			btnTakePhoto.setImageResource(R.drawable.image__take_photo);
			btnTakePhoto.setOnClickListener(recaptureImageButtonClickListener);
			
			
			break;

		default:
			break;
		}
	}
	
	private void showCameraAndButtons(){
		CustomGoogleMap.getInstanceCGM().hideMap();

		((LinearLayout) findViewById(R.id.llbuttonCamera))
				.setVisibility(View.VISIBLE);
		((LinearLayout) findViewById(R.id.lloptionsCamera))
				.setVisibility(View.VISIBLE);
		
		((ImageView) findViewById(R.id.camera_image_view))
		.setVisibility(View.VISIBLE);
	}
	
	private void hideCameraAndButtons(){
		((LinearLayout) findViewById(R.id.llbuttonCamera))
		.setVisibility(View.GONE);
		((LinearLayout) findViewById(R.id.lloptionsCamera))
				.setVisibility(View.GONE);
		
		((ImageView) findViewById(R.id.camera_image_view))
		.setVisibility(View.GONE);
	}

	private void setLikeButton(){
		if(rating == null){
			btnFlash.setImageResource(R.drawable.image_btn_no_like);
		}else{
			if(rating.getRate() == -1)
				btnFlash.setImageResource(R.drawable.image_btn_no_like);
			else
				btnFlash.setImageResource(R.drawable.image_btn_like);
		}
	}
	// debe obtener la sesion del usuario, sale del login
	private Long getIdUser() {
		SharedPreferences sPrefUser = ctx.getSharedPreferences("Login Credentials", 0);
		Long id = sPrefUser.getLong("usuarioId", 0);
		return id;
	}
	
	public void uploadPhoto() {
		/*((LinearLayout) findViewById(R.id.llbuttonCamera))
		.setVisibility(View.GONE);
		((LinearLayout) findViewById(R.id.lloptionsCamera))
				.setVisibility(View.GONE);
		
		((ImageView) findViewById(R.id.camera_image_view))
		.setVisibility(View.GONE);*/
		
		// ((LinearLayout)
		// findViewById(R.id.llCamera)).setVisibility(View.GONE);
		
		// Pedimos el id de usuario
		Long idUser = getIdUser();
		bucketName = idUser.toString();
		
		TaskCloudStorage cloud3 = new TaskCloudStorage();
		cloud3.actionRequest = ActionRequest.UPLOAD_OBJECT;
		this.actionRequest = ActionRequest.UPLOAD_OBJECT;
		cloud3.activity = this;
		cloud3.execute((Object) bucketName, (Object) currentPhotoPath
				+ currentPhotoName);
	}
	
	public void addPhoto() {
		
		GeoPt geopoint = new GeoPt();
		geopoint.setLatitude((float)latitude);
		geopoint.setLongitude((float)longitude);
		 
		Photo aPhoto = new Photo();
		aPhoto.setGeopt(geopoint);		 
		aPhoto.setDescription(message);
		aPhoto.setPath2photo(bucketName + "/" + currentPhotoName);	
		
		cloudAddPhoto = null;
		
		cloudAddPhoto = new TaskCloudStorage();
		cloudAddPhoto.actionRequest = ActionRequest.ADD_PHOTO;
		this.actionRequest = ActionRequest.ADD_PHOTO;
		//cloudAddPhoto.photo = aPhoto;
		//cloudAddPhoto.geoPoint = geopoint;
		//cloudAddPhoto.userId = getIdUser();
		cloudAddPhoto.activity = this;
		cloudAddPhoto.execute((Object)getIdUser(), (Object)aPhoto );	
		
	}

	private void getPhoto() {		

		/*((LinearLayout) findViewById(R.id.llbuttonCamera))
		.setVisibility(View.GONE);
		((LinearLayout) findViewById(R.id.lloptionsCamera))
				.setVisibility(View.GONE);
		
		((ImageView) findViewById(R.id.camera_image_view))
		.setVisibility(View.GONE);*/
		hideCameraAndButtons();
		
		GPSTracker gps = new GPSTracker(this);
		if (gps.canGetLocation()) {
			
			latitude = gps.getLatitude();
			longitude = gps.getLongitude();
	
			customGoogleMap.setmPosition(new LatLng(latitude, longitude));
			//Toast.makeText(
				//	getApplicationContext(),
					//"Your Location is - \nLat: " + latitude + "\nLong: "
						//	+ longitude, Toast.LENGTH_LONG).show();
		} else {
			gps.showSettingsAlert();
		}
		
		GeoPt geopoint = new GeoPt();
		geopoint.setLatitude((float)latitude);
		geopoint.setLongitude((float)longitude);
		
		cloudGetPhoto = null;
		
		cloudGetPhoto = new TaskCloudStorage();
		cloudGetPhoto.actionRequest = ActionRequest.GET_PHOTO;
		this.actionRequest = ActionRequest.GET_PHOTO;
		//cloudGetPhoto.userId = getIdUser();
		//cloudGetPhoto.geoPoint = geopoint;
		cloudGetPhoto.activity = this;
		cloudGetPhoto.execute((Object)getIdUser(), (Object)geopoint);
	}
	
	public void getRating() {		
		if(photos != null && photos.size() > 0){
			Iterator<Photo> iterator = photos.iterator();			
			Photo pho = iterator.next();
			
			cloudGetRating = new TaskCloudStorage();
			cloudGetRating.actionRequest = ActionRequest.GET_RATING;
			this.actionRequest = ActionRequest.GET_RATING;
			cloudGetRating.activity = this;
			cloudGetRating.execute((Object)getIdUser(), (Object)pho.getId() );	
		}else{ //si no ay fotos, ya no obtenemos el rating
			rating = null;
			setLikeButton();
			uploadPhoto();			
		}
	}
	
	public void setRating() {		
		if(rating == null){
			
		}else{ //siempre devuelve un object rating al menos que entre al catch 
			//hideCameraAndButtons();
			((LinearLayout) findViewById(R.id.llbuttonCamera)).setVisibility(View.GONE);
			((LinearLayout) findViewById(R.id.lloptionsCamera)).setVisibility(View.GONE);
			
			cloudSetRating = new TaskCloudStorage();
			cloudSetRating.actionRequest = ActionRequest.SET_RATING;
			this.actionRequest = ActionRequest.SET_RATING;
			cloudSetRating.activity = this;
			cloudSetRating.execute((Object)rating );		
		}
	}
	
	private void downloadPhoto() {	
		
		if(photos != null && photos.size() > 0){
			Iterator<Photo> iterator = photos.iterator();
			
			Photo pho = iterator.next();
			
			Log.e("SPOT SHOT DOWLOAD", pho.getPath2photo());
			Log.e("SPOT SHOT DOWLOAD", pho.getGeopt().getLatitude().toString());
			
			String path = pho.getPath2photo();
			
			//Log.e("SPOT SHOT", path );
			
			String[] temp = path.split("/");
			
			String bucket = temp[0];
			String name = temp[1];
			
			//Log.e("SPOT SHOT", temp.toString() );
			
			currentPhotoNameDownloaded = name;
			
			TaskCloudStorage cloud4 = new TaskCloudStorage();
			cloud4.actionRequest = ActionRequest.DOWNLOAD_OBJECT;
			this.actionRequest = ActionRequest.DOWNLOAD_OBJECT;
			cloud4.activity = this;
			cloud4.execute((Object) bucket, (Object) name,
					(Object) currentPhotoPath);
		}else{
			CustomDialog.showDialogAlert(ctx,"Wooooo", "Es la primera foto de tu zona", R.drawable.image__warning);
			
			showCameraAndButtons();		
			
			cameraImage.setImageBitmap(actualBitmap);

			//btnFlash.setImageResource(R.drawable.image_btn_like);
			btnFlash.setOnClickListener(likeImageButtonClickListener);

			btnFrontCamera.setImageResource(R.drawable.image_btn_chat);
			btnFrontCamera.setOnClickListener(chatImageButtonClickListener);

			btnTakePhoto.setImageResource(R.drawable.image__take_photo);
			btnTakePhoto.setOnClickListener(recaptureImageButtonClickListener);
		}
		
		Log.e("wwww", "dddd");
	}

	@Override
	public void onBackPressed() {

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

	}

}