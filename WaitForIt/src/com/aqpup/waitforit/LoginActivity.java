package com.aqpup.waitforit;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aqpup.model.spotshotendpoint.Spotshotendpoint;
import com.aqpup.model.spotshotendpoint.model.User;
import com.aqpup.waitforit.camera.CameraActivity;
import com.aqpup.waitforit.navigation.DashBoardActivity;
import com.aqpup.waitforit.utils.CloudEndpointUtils;
import com.aqpup.waitforit.utils.CustomDialog;
import com.aqpup.waitforit.utils.CustomEncryption;
import com.aqpup.waitforit.utils.CustomFont;
import com.aqpup.waitforit.utils.CustomLoginUser;
import com.aqpup.waitforit.utils.VerifyHardware;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;

//import com.aqpup.waitforit.Login2Activity.TaskValideUser;

public class LoginActivity extends FragmentActivity {

	private SharedPreferences sh_Pref;
	private Editor toEdit;
	private String email;
	private String password;
	private String passwordEncryt;
	private User user;
	
	private CustomLoginUser loginUser;

	Context context;
	Activity activity;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);
		context = this;
		activity = this;
		
		loginUser = CustomLoginUser.getInstanceCustomLoginUser();
		loginUser.setContext(context);
		loginUser.setActivity(activity);
		
		TextView tvLogin= (TextView) findViewById(R.id.tvLogin);
		final EditText edEmail = (EditText) findViewById(R.id.etEmail);		
		final EditText edPassword = (EditText) findViewById(R.id.etPassword);
		TextView tvLostPassword = (TextView) findViewById(R.id.tvLostPassword);
			
		// Setear fonts
		edEmail. setTypeface(CustomFont.getTfFontGeneral());
		edPassword. setTypeface(CustomFont.getTfFontGeneral());
		tvLostPassword.setTypeface(CustomFont.getTfFontGeneral());
		tvLogin.setTypeface(CustomFont.getTfFontGeneral());

		Button bSignIn = (Button) findViewById(R.id.bSignIn);
		bSignIn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				email = edEmail.getText().toString();
				password = edPassword.getText().toString();

				if (!email.equals("") && !password.equals("") && !email.equals(" ") && !password.equals(" ")) {

					passwordEncryt = CustomEncryption.convertMD5(password);

					User user = new User();
					user.setUsername(email);
					user.setPassword(passwordEncryt);
					//loginUser.setUser(user);
					
					// Se valida en la nube
					loginUser.new TaskValideUser(user).execute();

				}
				else {
					
					CustomDialog.showDialogAlert(context,
							"Datos de Usuario Vacios", "Llene los campos ",
							R.drawable.image__warning);
				}
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		
		Intent i = new Intent(context,PresentationActivity.class);
		startActivity(i);
		finish();
	}
}
