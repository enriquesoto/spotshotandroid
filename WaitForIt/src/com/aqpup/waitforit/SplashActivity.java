package com.aqpup.waitforit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aqpup.waitforit.camera.CameraActivity;
import com.aqpup.waitforit.navigation.DashBoardActivity;
import com.aqpup.waitforit.utils.CustomFont;

public class SplashActivity extends Activity {

	private boolean isNetwork = false;

	private SharedPreferences sPrefUser;
	private ProgressBar pBar;
	private TaskLoadSplash loadSplash;
	
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		context = this;
		
		// Set Fonts
		CustomFont.setTfFontMessageApp("font_message_splash.ttf", context);
		CustomFont.setTfFontGeneralApp("font_general.ttf", context);
		
		init();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void init() {

		pBar = (ProgressBar) findViewById(R.id.pbarLoading);
		loadSplash = new TaskLoadSplash(pBar);
		loadSplash.execute();

	}

	private class TaskLoadSplash extends AsyncTask<Void, Integer, Boolean> {

		private ProgressBar pBar;
		private Intent i;
		private boolean isLogged = true;
		private int valueDeley  = 1500;
		
		public TaskLoadSplash(ProgressBar pBar) {
			this.pBar = pBar;
		}		
		
		@Override
		protected Boolean doInBackground(Void... params) {
		
			System.out.println("login"+sPrefUser.getString("email", null)+"-"+sPrefUser.getString("passwordEncryt", null));

			if (sPrefUser.getString("email", null) == null && sPrefUser.getString("passwordEncryt", null) == null) {
	            isLogged = false;  
			}
			
            Log.d("status", "Es :" + String.valueOf(isLogged));

			return isLogged;
		}

		@Override
		protected void onPostExecute(final Boolean result) {

			new Handler().postDelayed(new Runnable() {
				
				@Override
				public void run() {
					
					if(result == true){

						requestUpdate();

						i = new Intent(getApplicationContext(),
								CameraActivity.class);
						
						startActivity(i);
			            finish();
					}
					else{
						
						i = new Intent(getApplicationContext(),
								PresentationActivity.class);
						
						startActivity(i);
			            finish();
					}
				}
			}, valueDeley);
		}

		@Override
		protected void onPreExecute() {

			pBar.setProgress(0);
			sPrefUser = getSharedPreferences("Login Credentials", MODE_PRIVATE);

		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			//super.onProgressUpdate(values);
			pBar.setProgress(values[0]);
		}
	}

	private boolean requestUpdate() {

		return true;
	}
}
