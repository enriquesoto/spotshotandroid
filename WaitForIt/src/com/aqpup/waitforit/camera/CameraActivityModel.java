package com.aqpup.waitforit.camera;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.util.Log;

public class CameraActivityModel {	
	private Context context;
	private GCSParams  gcsParams = new GCSParams();
	
	public CameraActivityModel(Context ctx){
		context = ctx;
	}

	public boolean checkStatusStorage() {
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;

		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			//Toast.makeText(context,		"La memoria externa solo tiene permisos de lectura",Toast.LENGTH_SHORT).show();
			return false;

		} else {
			//Toast.makeText(context, "No se tiene acceso a la memoria",	Toast.LENGTH_SHORT).show();
			return false;
		}
	}
	
	public boolean CheckFolderAndKey(AssetManager assetManager){
		
		File folder = new File(Environment.getExternalStorageDirectory()
				+ gcsParams.TEMP_PATH);
		boolean success = true;
		if (!folder.exists()) {
			success = folder.mkdir();
			
			if (success) {			
				return copyKey(assetManager);				
			} else {
				Log.e("SPOTSHOT", "Error creando directorio");	
				return false;
			}	
		}
		else{
			return copyKey(assetManager);
		}		
	}
	
	private boolean copyKey(AssetManager assetManager){
		try {			
			File key = new File(Environment.getExternalStorageDirectory()
					+ gcsParams.TEMP_PATH + gcsParams.PRIVATE_KEY);
			if(!key.exists()){
				InputStream in = assetManager.open(gcsParams.PRIVATE_KEY);
				File outFile = new File(Environment.getExternalStorageDirectory()
						+ gcsParams.TEMP_PATH + gcsParams.PRIVATE_KEY);
				OutputStream out = new FileOutputStream(outFile);
				copyFile(in, out);
				in.close();
				in = null;
				out.flush();
				out.close();
				out = null;
			}	
			
			return true;

		} catch (Exception e) {
			Log.e("SPOTSHOT", e.getMessage());	
			return false;
		}
	}
	
	private void copyFile(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[1024];
		int read;
		while ((read = in.read(buffer)) != -1) {
			out.write(buffer, 0, read);
		}
	}
	
	public String saveImage(BitmapDrawable image, String currentPhotoPath) {
		try {

			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
					.format(new Date());
			String imageFileName = gcsParams.PREFIX_NAME_IMAGE + timeStamp;

			OutputStream fOut = null;
			File file = new File(currentPhotoPath, imageFileName + ".jpg");
			String currentPhotoName = imageFileName + ".jpg";
			fOut = new FileOutputStream(file);

			//Bitmap bitmap = ((BitmapDrawable) cameraImage.getDrawable())	.getBitmap();
			Bitmap bitmap = image.getBitmap();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fOut);

			fOut.flush();
			fOut.close();
			
			return currentPhotoName;

		} catch (IOException ex) {
			Log.e("ERROR", ex.getMessage());
			return "";
		}
	}

}
