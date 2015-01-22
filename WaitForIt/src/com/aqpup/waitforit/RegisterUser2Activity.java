package com.aqpup.waitforit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.aqpup.model.spotshotendpoint.model.User;
import com.aqpup.waitforit.utils.CustomDialog;
import com.aqpup.waitforit.utils.CustomFont;
import com.aqpup.waitforit.utils.CustomLoginUser;
import com.google.api.client.util.DateTime;

public class RegisterUser2Activity extends ActionBarActivity {

	private SharedPreferences sh_Pref;
	private Editor toEdit;
	private Context context;
	private Activity activity;
	private User user;
	private String username;

	private CustomLoginUser loginUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		context = this;
		activity = this;

		setContentView(R.layout.activity_register_user2);

		loginUser = CustomLoginUser.getInstanceCustomLoginUser();
		loginUser.setContext(context);
		loginUser.setActivity(activity);
		
		((TextView) findViewById(R.id.tvRegister)).setTypeface(CustomFont
				.getTfFontGeneral());
		
		((TextView) findViewById(R.id.tvEula)).setTypeface(CustomFont
				.getTfFontMessage());
	

		final EditText edUsername = (EditText) findViewById(R.id.etUserName);
		final CheckBox cbTermsConditions = (CheckBox) findViewById(R.id.cbTermsConditions);

		cbTermsConditions.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (cbTermsConditions.isChecked()) {

					CustomDialog.showDialogEula(context, getResources()
							.getString(R.string.eula), getResources()
							.getString(R.string.text_eula), cbTermsConditions);
				}
			}
		});

		Intent i = getIntent();
		String listDataUser[] = i.getExtras().getStringArray("dataUser");

		user = new User();
		user.setEmail(listDataUser[0]);
		user.setPassword(listDataUser[1]);
		user.setGender(Integer.valueOf(listDataUser[2]));
		user.setBirthday(DateTime.parseRfc3339(listDataUser[3]));

		Button bSignUp = (Button) findViewById(R.id.bSignUp);
		bSignUp.setTypeface(CustomFont.getTfFontGeneral());

		bSignUp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				username = edUsername.getText().toString();

				if (!username.equals(" ") && !username.equals("")) {
					user.setUsername(edUsername.getText().toString());

					if(cbTermsConditions.isChecked()){

						// email = edEmail.getText().toString();
						// password = edPassword.getText().toString();

						// if (!password.equals(" ") && !email.equals(" ")
						// && ValidateField.isValidEmail(edEmail.getText())) {
						// passwordEncryt = CustomEncryption.convertMD5(password);
						// Se valida en la nube
						// System.out.println("salida" +user.getUsername());
						//loginUser.setUser(user);
						
						loginUser.new TaskValideRegisterUser(user).execute();

						// } else {
						// Toast.makeText(context, "Datos vacios !!!",
						// Toast.LENGTH_LONG).show();
						// }
					}
					else{
						CustomDialog.showDialogAlert(context,
								"Términos y Condiciones", "Acepte las condiciones para registrarse",
								R.drawable.image__warning);
					}
					
				} else {
					CustomDialog.showDialogAlert(context,
							"Datos de vacios o incorrectos", "Llene los campos de forma correcta",
							R.drawable.image__warning);

				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register_user2, menu);
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
}
