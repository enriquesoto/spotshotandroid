package com.aqpup.waitforit;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aqpup.model.spotshotendpoint.Spotshotendpoint;
import com.aqpup.model.spotshotendpoint.model.BooleanResponse;
import com.aqpup.model.spotshotendpoint.model.User;
import com.aqpup.waitforit.utils.CloudEndpointUtils;
import com.aqpup.waitforit.utils.CustomDialog;
import com.aqpup.waitforit.utils.CustomEncryption;
import com.aqpup.waitforit.utils.CustomFont;
import com.aqpup.waitforit.utils.CustomLoginUser;
import com.aqpup.waitforit.utils.DateTimePicker;
import com.aqpup.waitforit.utils.DateTimePicker.DateWatcher;
import com.aqpup.waitforit.utils.ValidateField;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;

public class RegisterUserActivity extends Activity {

	private String email;
	private String password;
	private String gender;
	private String birthday;
	private EditText etBirthday;
	private String[] aGender;
	private String passwordEncryt;
	private CustomLoginUser loginUser;

	Context context;
	Activity activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		activity = this;

		setContentView(R.layout.activity_register_user);

		initData();

		loginUser = CustomLoginUser.getInstanceCustomLoginUser();
		loginUser.setContext(context);
		loginUser.setActivity(activity);
		
		((TextView) findViewById(R.id.tvRegister)).setTypeface(CustomFont
				.getTfFontGeneral());
		final EditText edEmail = (EditText) findViewById(R.id.etEmail);
		final EditText edPassword = (EditText) findViewById(R.id.etPassword);
		final TextView tvGender = (TextView) findViewById(R.id.tvGender);

		System.out.println("salida 1" + edEmail.getText().toString());
		System.out.println("salida 2" + edPassword.getText().toString());

		tvGender.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				CustomDialog.showDialogList(context, "Seleccione su Genero",
						aGender, tvGender);
			}
		});

		etBirthday = (EditText) findViewById(R.id.etBirthday);
		// etBirthday.addTextChangedListener(ValidateField
		// .getTextWatcherBirthday(etBirthday));
		etBirthday.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Create the dialog
				final Dialog mDateTimeDialog = new Dialog(context);

				// Inflate the root layout
				final LinearLayout mDateTimeDialogView = (LinearLayout) getLayoutInflater()
						.inflate(R.layout.date_time_dialog, null);
				// Grab widget instance
				final DateTimePicker mDateTimePicker = (DateTimePicker) mDateTimeDialogView
						.findViewById(R.id.DateTimePicker);
				mDateTimePicker.setDateChangedListener(new DateWatcher() {
					@Override
					public void onDateChanged(Calendar c) {
						Log.e("",
								"" + c.get(Calendar.MONTH) + " "
										+ c.get(Calendar.DAY_OF_MONTH) + " "
										+ c.get(Calendar.YEAR));

					}
				});

				((Button) mDateTimeDialogView.findViewById(R.id.SetDateTime))
						.setOnClickListener(new OnClickListener() {

							public void onClick(View v) {
								mDateTimePicker.clearFocus();

								String result_string = mDateTimePicker
										.getYear()
										+ "-"
										+ String.valueOf(mDateTimePicker
												.getMonthNumber())
										+ "-"
										+ String.valueOf(mDateTimePicker
												.getDay())
								// + "  "
								// + String.valueOf(mDateTimePicker
								// .getHour())
								// + ":"
								// + String.valueOf(mDateTimePicker
								// .getMinute())
								;
								// if(mDateTimePicker.getHour() > 12)
								// result_string = result_string + "PM";
								// else result_string = result_string + "AM";

								etBirthday.setText(result_string);
								mDateTimeDialog.dismiss();
							}
						});

				((Button) mDateTimeDialogView.findViewById(R.id.CancelDialog))
						.setOnClickListener(new OnClickListener() {

							public void onClick(View v) {
								// TODO Auto-generated method stub
								mDateTimeDialog.cancel();
							}
						});

				((Button) mDateTimeDialogView.findViewById(R.id.ResetDateTime))
						.setOnClickListener(new OnClickListener() {

							public void onClick(View v) {
								// TODO Auto-generated method stub
								mDateTimePicker.reset();
							}
						});

				mDateTimeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				mDateTimeDialog.getWindow().setBackgroundDrawable(
						new ColorDrawable(android.graphics.Color.TRANSPARENT));
				mDateTimeDialog.getWindow().setLayout(400,
						LayoutParams.WRAP_CONTENT);
				mDateTimeDialog.setContentView(mDateTimeDialogView);
				mDateTimeDialog.show();

			}
		});

		// Se setea tipos de fuentes
		edEmail.setTypeface(CustomFont.getTfFontGeneral());
		edPassword.setTypeface(CustomFont.getTfFontGeneral());
		tvGender.setTypeface(CustomFont.getTfFontGeneral());
		etBirthday.setTypeface(CustomFont.getTfFontGeneral());

		Button bSignUp = (Button) findViewById(R.id.bSignUp);
		bSignUp.setTypeface(CustomFont.getTfFontGeneral());

		bSignUp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				email = edEmail.getText().toString();
				password = edPassword.getText().toString();
				gender = String.valueOf(CustomDialog.answerDialogList());
				birthday = etBirthday.getText().toString();

				// validacion de campos vacios
				if ((!email.equals("") && !password.equals("")
						&& !birthday.equals("") && !gender.equals("")) &&  (!email.equals(" ") && !password.equals(" ")
								&& !birthday.equals(" ") && !gender.equals(" "))) {

					//Para corregir el formato de la fecha
					SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");					
					Date d;
					try {
						d = ft.parse(birthday);
						
						ft.applyPattern("yyyy-MM-dd");
						birthday = ft.format(d);
						
						Log.e("SPOT SHOTTT D", d.toString());
						Log.e("SPOT SHOTTT D", birthday);
					} catch (ParseException e) {
						Log.e("SPOT SHOTTT", e.getMessage());
						// TODO Auto-generated catch block
						e.printStackTrace();
						CustomDialog.showDialogAlert(context,
								"Problemas con el servidor",
								"Error inesperado con el formato de la fechas", R.drawable.image__warning);
					}
					
					
					
					User user = new User();
					user.setEmail(email);
					user.setPassword(password);
					user.setGender(CustomDialog.answerDialogList());
					//user.setBirthday(new DateTime(etBirthday.getText().toString()));
					user.setBirthday(new DateTime(birthday));

					//loginUser.setUser(user);
					loginUser. new TaskValideRegisterUserFirst(user).execute();

				} else {

					CustomDialog.showDialogAlert(context,
							"Datos de Usuario Vacios", "Llene los campos ",
							R.drawable.image__warning);
				}

			}
		});

	}

	public void initData() {
		aGender = new String[] { "Masculino", "Femenino" };
	}

}