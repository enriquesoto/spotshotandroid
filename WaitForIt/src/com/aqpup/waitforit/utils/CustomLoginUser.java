package com.aqpup.waitforit.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.brickred.socialauth.Contact;
import org.brickred.socialauth.Feed;
import org.brickred.socialauth.Profile;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthError;
import org.brickred.socialauth.android.SocialAuthListener;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aqpup.model.spotshotendpoint.Spotshotendpoint;
import com.aqpup.model.spotshotendpoint.model.BooleanResponse;
import com.aqpup.model.spotshotendpoint.model.CollectionResponseFriendship;
import com.aqpup.model.spotshotendpoint.model.CollectionResponseUser;
import com.aqpup.model.spotshotendpoint.model.Friendship;
import com.aqpup.model.spotshotendpoint.model.User;
import com.aqpup.waitforit.R;
import com.aqpup.waitforit.RegisterUser2Activity;
import com.aqpup.waitforit.camera.CameraActivity;
import com.aqpup.waitforit.chat.Constants;
import com.aqpup.waitforit.navigation.FriendAdapter;
import com.aqpup.waitforit.navigation.Item_friend;
import com.aqpup.waitforit.navigation.Item_request_friend;
import com.aqpup.waitforit.navigation.RequestFriendAdapter;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;

public class CustomLoginUser {

	private static CustomLoginUser loginUser;
	private SocialAuthAdapter adapter;
	private String providerName;
	private SharedPreferences sh_Pref;
	private Editor toEdit;
	private Context context;
	// private User user;
	private Activity activity;

	private CustomLoginUser() {

	}

	public static CustomLoginUser getInstanceCustomLoginUser() {

		if (loginUser == null) {
			loginUser = new CustomLoginUser();

		}
		return loginUser;

	}

	public SocialAuthAdapter getAdapter() {
		return adapter;
	}

	public void setAdapter(SocialAuthAdapter adapter) {
		this.adapter = adapter;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public void saveSharedPreferences(String email, String passwordEncryt,
			Long usuarioId, String userName) {

		openSharedPreferences();

		toEdit = sh_Pref.edit();
		toEdit.putString("email", email);
		toEdit.putString("passwordEncryt", passwordEncryt);
		toEdit.putLong("usuarioId", usuarioId);
		toEdit.putString("userName", userName);

		toEdit.commit();

	}

	public void openSharedPreferences() {
		sh_Pref = context.getSharedPreferences("Login Credentials",
				context.MODE_PRIVATE);

	}

	// Method to handle events of providers
	public void Events(int position, final String provider) {

		switch (position) {
		case 0: // Code to print user profile details for all providers
		{
			// mDialog.show();
			// CustomDialog.Loading.showDialogLoading(context, "Cargando",
			// "dsads", R.drawable.ic_drawer);
			// adapter.getUserProfileAsync(new ProfileDataListener());
			break;
		}

		case 1: {
			// Share Update : Facebook, Twitter, Linkedin, Yahoo,
			// MySpace,Yammer

			// Get Contacts for FourSquare, Google, Google Plus,
			// Flickr, Instagram

			// Dismiss Dialog for Runkeeper and SalesForce

			if (provider.equalsIgnoreCase("foursquare")
					|| provider.equalsIgnoreCase("google")
					|| provider.equalsIgnoreCase("flickr")
					|| provider.equalsIgnoreCase("googleplus")
					|| provider.equalsIgnoreCase("instagram")) {
				CustomDialog.Loading.showDialogLoading(context, "Cargando",
						"dsads", R.drawable.ic_drawer);

				adapter.getContactListAsync(new ContactDataListener());

			} else if (provider.equalsIgnoreCase("runkeeper")
					|| provider.equalsIgnoreCase("salesforce")) {
				// dialog.dismiss();

			} else {

				// Code to Post Message for all providers
				final Dialog msgDialog = new Dialog(context);
				msgDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				msgDialog.setContentView(R.layout.dialog);

				TextView dialogTitle = (TextView) msgDialog
						.findViewById(R.id.dialogTitle);
				dialogTitle.setText("Share Update");
				final EditText edit = (EditText) msgDialog
						.findViewById(R.id.editTxt);
				Button update = (Button) msgDialog.findViewById(R.id.update);

				msgDialog.show();

				update.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						msgDialog.dismiss();
						adapter.updateStatus(edit.getText().toString(),
								new MessageListener(), false);
					}
				});
			}
			break;
		}

		case 2: {

			// Get Contacts : Facebook, Twitter, Linkedin, Yahoo,
			// MySpace,Yammer

			// Get Feeds : Google Plus, Instagram
			// Dismiss Dialog for FourSquare , Google, Flickr

			if (provider.equalsIgnoreCase("foursquare")
					|| provider.equalsIgnoreCase("google")
					|| provider.equalsIgnoreCase("flickr")) {
				// Close Dialog
				// dialog.dismiss();
				CustomDialog.Loading.getDialog().dismiss();
			} else if (provider.equalsIgnoreCase("instagram")
					|| provider.equalsIgnoreCase("googleplus")) {
				// mDialog.show();
				CustomDialog.Loading.showDialogLoading(context, "Cargando",
						"dsads", R.drawable.ic_drawer);
				adapter.getFeedsAsync(new FeedDataListener());
			} else {
				// Get Contacts for Remaining Providers
				// mDialog.show();
				CustomDialog.Loading.showDialogLoading(context, "Cargando",
						"dsads", R.drawable.ic_drawer);
				adapter.getContactListAsync(new ContactDataListener());
			}
			break;
		}
		/*
		 * case 3: { // Get Feeds : For Facebook , Twitter, Linkedin // Get
		 * Albums : Google Plus // Dismiss Dialog: Rest
		 * 
		 * if (provider.equalsIgnoreCase("facebook") ||
		 * provider.equalsIgnoreCase("twitter") ||
		 * provider.equalsIgnoreCase("linkedin")) { //mDialog.show();
		 * CustomDialog.Loading.showDialogLoading(context, "Cargando", "dsads",
		 * R.drawable.ic_drawer);
		 * 
		 * adapter.getFeedsAsync(new FeedDataListener()); } else if
		 * (provider.equalsIgnoreCase("googleplus")) { //mDialog.show();
		 * CustomDialog.Loading.showDialogLoading(context, "Cargando", "dsads",
		 * R.drawable.ic_drawer);
		 * 
		 * adapter.getAlbumsAsync(new AlbumDataListener()); } else {
		 * dialog.dismiss(); } break; }
		 * 
		 * case 4: { // Upload Image for Facebook and Twitter
		 * 
		 * if (provider.equalsIgnoreCase("facebook") ||
		 * provider.equalsIgnoreCase("twitter")) {
		 * 
		 * // Code to Post Message for all providers final Dialog imgDialog =
		 * new Dialog(context.this);
		 * imgDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		 * imgDialog.setContentView(R.layout.dialog);
		 * imgDialog.setCancelable(true);
		 * 
		 * TextView dialogTitle = (TextView) imgDialog
		 * .findViewById(R.id.dialogTitle); dialogTitle.setText("Share Image");
		 * final EditText edit = (EditText) imgDialog
		 * .findViewById(R.id.editTxt); Button update = (Button)
		 * imgDialog.findViewById(R.id.update);
		 * update.setVisibility(View.INVISIBLE); Button getImage = (Button)
		 * imgDialog .findViewById(R.id.loadImage);
		 * getImage.setVisibility(View.VISIBLE); imgDialog.show();
		 * 
		 * getImage.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) {
		 * 
		 * if (isEmpty(edit)) { Toast.makeText(context, "Please fill message",
		 * Toast.LENGTH_SHORT) .show(); } else { imgDialog.dismiss(); msg_value
		 * = edit.getText().toString(); // Taking image from phone gallery
		 * Intent photoPickerIntent = new Intent( Intent.ACTION_PICK);
		 * photoPickerIntent.setType("image/*");
		 * startActivityForResult(photoPickerIntent, SELECT_PHOTO); } } });
		 * 
		 * } else if (provider.equalsIgnoreCase("linkedin")) {
		 * 
		 * // get Job and Education information mDialog.show();
		 * adapter.getCareerAsync(new CareerListener());
		 * 
		 * } else { dialog.dismiss(); } break; }
		 * 
		 * case 5: { // Get Albums for Facebook and Twitter
		 * 
		 * if (provider.equalsIgnoreCase("facebook") ||
		 * provider.equalsIgnoreCase("twitter")) { mDialog.show();
		 * adapter.getAlbumsAsync(new AlbumDataListener()); } else {
		 * dialog.dismiss(); } break; }
		 * 
		 * case 6: { // For share text with link preview if
		 * (provider.equalsIgnoreCase("facebook")) { try { adapter.updateStory(
		 * "Hello SocialAuth Android" + System.currentTimeMillis(),
		 * "Google SDK for Android",
		 * "Build great social apps and get more installs.",
		 * "The Facebook SDK for Android makes it easier and faster to develop Facebook integrated Android apps."
		 * , "https://www.facebook.com",
		 * "http://carbonfreepress.gr/images/facebook.png", new
		 * MessageListener()); } catch (UnsupportedEncodingException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); }
		 * 
		 * } else { dialog.dismiss(); } break; }
		 */
		case 7: {
			// Dismiss Dialog
			// dialog.dismiss();
			CustomDialog.Loading.getDialog().dismiss();
			break;
		}

		}

	}

	public class ProfileDataListener implements SocialAuthListener<Profile> {

		private User userProfileData;

		public ProfileDataListener() {

			userProfileData = new User();
		}

		@Override
		public void onError(SocialAuthError arg0) {

		}

		@Override
		public void onExecute(String arg0, Profile profile) {

			// CustomDialog.Loading.getDialog().dismiss();
			// CustomDialog.Loading.showDialogLoading(context, "Cargando",
			// "Cargando", 1234);

			Log.d("Custom-UI", "Receiving Data");
			Profile profileMap = profile;

			Log.d("Custom-UI",
					"Validate ID         = " + profileMap.getValidatedId());
			Log.d("Custom-UI",
					"First Name          = " + profileMap.getFirstName());
			Log.d("Custom-UI",
					"Last Name           = " + profileMap.getLastName());
			Log.d("Custom-UI", "Email               = " + profileMap.getEmail());
			Log.d("Custom-UI",
					"Gender                   = " + profileMap.getGender());
			Log.d("Custom-UI",
					"Country                  = " + profileMap.getCountry());
			Log.d("Custom-UI",
					"Language                 = " + profileMap.getLanguage());
			Log.d("Custom-UI",
					"Location                 = " + profileMap.getLocation());
			Log.d("Custom-UI",
					"Profile Image URL  = " + profileMap.getProfileImageURL());

			System.out.println("salida" + profileMap.toString());
			userProfileData.setEmail(profileMap.getEmail());
			userProfileData.setPassword(CustomEncryption
					.convertMD5("@SpotShot_2014"));

			userProfileData.setGender(profileMap.getGender().equals("Male") ? 0
					: 1);
			userProfileData.setBirthday(new DateTime(profileMap.getDob()
					.getYear()
					+ "-"
					+ profileMap.getDob().getMonth()
					+ "-"
					+ profileMap.getDob().getDay()));
			userProfileData.setUsername(profileMap.getFirstName());

			// sube a la nube
			CustomLoginUser loginUser = CustomLoginUser
					.getInstanceCustomLoginUser();
			loginUser.new TaskValideRegisterUserRedSocial(userProfileData)
					.execute();

		}
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	// To receive the contacts response after authentication
	public final class ContactDataListener implements
			SocialAuthListener<List<Contact>> {

		@Override
		public void onExecute(String provider, List<Contact> t) {

			Log.d("Custom-UI", "Receiving Data");
			// mDialog.dismiss();
			CustomDialog.Loading.showDialogLoading(context, "Cargando",
					"dsads", R.drawable.ic_drawer);
			List<Contact> contactsList = t;

			if (contactsList != null && contactsList.size() > 0) {
				// Intent intent = new Intent(CustomUI.this,
				// ContactActivity.class);
				// intent.putExtra("provider", provider);
				// intent.putExtra("contact", (Serializable) contactsList);
				// startActivity(intent);
			} else {
				Log.d("Custom-UI", "Contact List Empty");
			}
		}

		@Override
		public void onError(SocialAuthError e) {

		}
	}

	// To get status of image upload after authentication
	public final class UploadImageListener implements
			SocialAuthListener<Integer> {

		@Override
		public void onExecute(String provider, Integer t) {
			// mDialog.dismiss();
			CustomDialog.Loading.showDialogLoading(context, "Cargando",
					"dsads", R.drawable.ic_drawer);
			Integer status = t;
			Log.d("Custom-UI", String.valueOf(status));
			if (status.intValue() == 200 || status.intValue() == 201
					|| status.intValue() == 204)
				Toast.makeText(context, "Image Uploaded", Toast.LENGTH_SHORT)
						.show();
			else
				Toast.makeText(context, "Image not Uploaded",
						Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onError(SocialAuthError e) {

		}
	}

	// To receive the feed response after authentication
	public final class FeedDataListener implements
			SocialAuthListener<List<Feed>> {

		@Override
		public void onExecute(String provider, List<Feed> t) {

			Log.d("Custom-UI", "Receiving Data");
			// mDialog.dismiss();

			CustomDialog.Loading.showDialogLoading(context, "Cargando",
					"dsads", R.drawable.ic_drawer);

			List<Feed> feedList = t;

			if (feedList != null && feedList.size() > 0) {
				// Intent intent = new Intent(CustomUI.this,
				// FeedActivity.class);
				// intent.putExtra("feed", (Serializable) feedList);
				// startActivity(intent);
			} else {
				Log.d("Custom-UI", "Feed List Empty");
			}
		}

		@Override
		public void onError(SocialAuthError e) {
		}
	}

	// To get status of message after authentication
	public final class MessageListener implements SocialAuthListener<Integer> {
		@Override
		public void onExecute(String provider, Integer t) {
			Integer status = t;
			if (status.intValue() == 200 || status.intValue() == 201
					|| status.intValue() == 204)
				Toast.makeText(context, "Message posted on" + provider,
						Toast.LENGTH_LONG).show();
			else
				Toast.makeText(context, "Message not posted" + provider,
						Toast.LENGTH_LONG).show();
		}

		@Override
		public void onError(SocialAuthError e) {

		}
	}

	public class TaskValideRegisterUserRedSocial extends

	AsyncTask<Void, Integer, Integer> {

		private int typeResponse = -1;
		private String sEmail = null;
		private User userTemp;
		private User userRegisterRS;

		public TaskValideRegisterUserRedSocial(User userRegisterRS) {
			this.userRegisterRS = userRegisterRS;
		}

		@Override
		protected void onPreExecute() {
			CustomDialog.Loading.showDialogLoading(context, "Cargando",
					"Cargando", 1234);
		}

		@Override
		protected Integer doInBackground(Void... params) {

			// Verificar username
			Spotshotendpoint.Builder builder = new Spotshotendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
					null);

			builder = CloudEndpointUtils.updateBuilder(builder);
			Spotshotendpoint userEndpoint = builder.build();

			try {
				System.out.println("salida" + userRegisterRS.getUsername());

				CollectionResponseUser cResponseUser = userEndpoint
						.getUserByEmailEndpoint(userRegisterRS.getEmail())
						.execute();
				sEmail = cResponseUser.getItems().get(0).getEmail();
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (sEmail == null) {
				try {
					// Registrar usuario
					userTemp = userEndpoint.insertUser(userRegisterRS)
							.execute();
					if (userTemp == null) {
						typeResponse = 1; // incorrecto
					} else {
						typeResponse = 0;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {

				typeResponse = 2;
			}
			return typeResponse;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			CustomDialog.Loading.getPbLoading().setProgress(values[0]);
		}

		@Override
		protected void onPostExecute(Integer result) {
			CustomDialog.Loading.getDialog().dismiss();

			System.out.println("salida" + result);
			switch (result) {
			case 0:

				loginUser.saveSharedPreferences(userTemp.getEmail(),
						userTemp.getPassword(), userTemp.getId(),
						userTemp.getUsername());

				// Creamos el Bucket en google clous storage
				createStorageBucket();

				// Intent i = new Intent(context, CameraActivity.class);
				// context.startActivity(i);
				// activity.finish();

				break;
			case 1:

				CustomDialog.showDialogAlert(context,
						"No se pudo registrar un usuario",
						"Problemas con el servidor", R.drawable.image__warning);

				break;
			case 2:

				Intent i = new Intent(context, CameraActivity.class);
				context.startActivity(i);

				activity.finish();

				break;
			}
		}
	}

	public class TaskValideUser extends AsyncTask<Void, Integer, Integer> {

		private int typeResponse = -1;
		private User userTemp = null;
		private User userIn = null;
		private GoogleCloudMessaging gcm;

		public TaskValideUser(User userIn) {
			this.userIn = userIn;
			this.gcm = GoogleCloudMessaging.getInstance(context);
		}

		@Override
		protected void onPreExecute() {
			CustomDialog.Loading.showDialogLoading(context, "Cargando",
					"Cargando", 1234);
		}

		@Override
		protected Integer doInBackground(Void... params) {

			int iResponse = VerifyHardware.checkNetworkStatus(activity);

			switch (iResponse) {
			case 0:
			case 1:

				Spotshotendpoint.Builder builder = new Spotshotendpoint.Builder(
						AndroidHttp.newCompatibleTransport(),
						new JacksonFactory(), null);
				builder = CloudEndpointUtils.updateBuilder(builder);
				Spotshotendpoint userEndpoint = builder.build();

				int noOfAttemptsAllowed = 5; // Number of Retries allowed
				int noOfAttempts = 0; // Number of tries done
				boolean stopFetching = false; // Flag to denote if it has to be
												// retried or not
				String regId = "";

				while (!stopFetching) {
					noOfAttempts++;
					try {
						// Leave some time here for the register to be
						// registered before going to the next line
						Thread.sleep(2000); // Set this timing based on trial.
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					try {
						// Get the registration ID
						// regId =
						// GCMRegistrar.getRegistrationId(LoginActivity.this);
						regId = gcm.register(Constants.SENDER_ID);
					} catch (Exception e) {
					}
					if (!regId.isEmpty() || noOfAttempts > noOfAttemptsAllowed) {
						// If registration ID obtained or No Of tries exceeded,
						// stop fetching
						stopFetching = true;
					}
				}
				userIn.setRegId(regId);

				try {

					userTemp = userEndpoint.validateUser(userIn.getUsername(),
							userIn.getPassword(), userIn.getRegId()).execute();

					if (userTemp == null) {
						typeResponse = 0;
						Log.i("LOG", "Es nullo");
					} else {
						typeResponse = 1;
						Log.i("LOG", "No es nullo");

					}

				} catch (IOException e) {
					e.printStackTrace();
				}

				break;
			case 2:
				typeResponse = 2;
				break;
			}

			return typeResponse;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {

			CustomDialog.Loading.getPbLoading().setProgress(values[0]);
		}

		@Override
		protected void onPostExecute(Integer result) {

			Log.i("LOG", "Es " + result);

			CustomDialog.Loading.getDialog().dismiss();

			switch (result) {
			case 0:
				CustomDialog
						.showDialogAlert(
								context,
								"Datos Incorrectos",
								"El correo o password son incorrectos, vuelva a intentar  ",
								R.drawable.image__warning);

				break;

			case 1:

				loginUser.saveSharedPreferences(userTemp.getEmail(),
						userTemp.getPassword(), userTemp.getId(),
						userTemp.getUsername());

				Log.e("salida ", userTemp.toString());
				// loginUser.setUser(userTemp);

				Intent i = new Intent(context, CameraActivity.class);

				context.startActivity(i);
				activity.finish();

				break;

			case 2:
				CustomDialog.showDialogAlert(context, "Error de Conexion ",
						"Problema de Red o Internet ",
						R.drawable.image__warning);

				break;

			case 3:
				break;
			}
		}
	}

	public class TaskValideRegisterUser extends
			AsyncTask<Void, Integer, Integer> {

		private int typeResponse = -1;
		private User userTemp;
		private User userRegister;
		private GoogleCloudMessaging gcm;

		public TaskValideRegisterUser(User userRegister) {
			this.userRegister = userRegister;
			this.gcm = GoogleCloudMessaging.getInstance(context);


		}

		@Override
		protected void onPreExecute() {
			CustomDialog.Loading.showDialogLoading(context, "Cargando",
					"Cargando", 1234);
		}

		@Override
		protected Integer doInBackground(Void... params) {

			// Verificar username

			Spotshotendpoint.Builder builder = new Spotshotendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
					null);

			builder = CloudEndpointUtils.updateBuilder(builder);
			Spotshotendpoint userEndpoint = builder.build();
			BooleanResponse boolResponse = null;

			try {
				System.out.println("salida" + userRegister.getUsername());
				boolResponse = userEndpoint.existsUsername(
						userRegister.getUsername()).execute();
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (!boolResponse.getResponse()) {

				int noOfAttemptsAllowed = 5; // Number of Retries allowed
				int noOfAttempts = 0; // Number of tries done
				boolean stopFetching = false; // Flag to denote if it has to be
												// retried or not
				String regId = "";

				while (!stopFetching) {
					noOfAttempts++;
					try {
						// Leave some time here for the register to be
						// registered before going to the next line
						Thread.sleep(2000); // Set this timing based on trial.
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					try {
						// Get the registration ID
						// regId =
						// GCMRegistrar.getRegistrationId(LoginActivity.this);
						regId = gcm.register(Constants.SENDER_ID);
					} catch (Exception e) {
					}
					if (!regId.isEmpty() || noOfAttempts > noOfAttemptsAllowed) {
						// If registration ID obtained or No Of tries exceeded,
						// stop fetching
						stopFetching = true;
					}
				}
				userRegister.setRegId(regId);

				try {

					// Registrar usuario
					userTemp = userEndpoint.insertUser(userRegister).execute();

					// Log.e("PROBANDOOOO", userTemp.getId().toString());

					if (userTemp == null) {
						typeResponse = 1; // incorrecto
					} else {
						typeResponse = 0;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {

				typeResponse = 2;

			}
			return typeResponse;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			CustomDialog.Loading.getPbLoading().setProgress(values[0]);
		}

		@Override
		protected void onPostExecute(Integer result) {
			CustomDialog.Loading.getDialog().dismiss();

			switch (result) {
			case 0:

				Long id = userTemp.getId();
				if (id == null)
					Log.e("SPOT SHOT ID", ":(");

				Log.e("SPOT SHOT ID", userTemp.getId().toString());

				loginUser.saveSharedPreferences(userTemp.getEmail(),
						userTemp.getPassword(), userTemp.getId(),
						userTemp.getUsername());

				// Creamos el Bucket en google clous storage
				createStorageBucket();

				// Intent i = new Intent(context, CameraActivity.class);
				// context.startActivity(i);
				// activity.finish();

				break;
			case 1:

				CustomDialog.showDialogAlert(context,
						"No se pudo registrar un usuario",
						"Problemas con el servidor", R.drawable.image__warning);

				break;
			case 2:

				CustomDialog.showDialogAlert(context,
						"Error de Nombre del Usuario",
						"El nombre del usuario  ya existe",
						R.drawable.image__warning);

				break;
			}
		}
	}

	public class TaskValideRegisterUserFirst extends
			AsyncTask<Void, Integer, Integer> {

		private int typeResponse = -1;
		private String sEmail = null;
		private User userRegisterFirst;

		public TaskValideRegisterUserFirst(User userRegisterFirst) {
			this.userRegisterFirst = userRegisterFirst;

		}

		@Override
		protected void onPreExecute() {
			CustomDialog.Loading.showDialogLoading(context, "Cargando",
					"Cargando", 1234);
		}

		@Override
		protected Integer doInBackground(Void... params) {

			if (ValidateField.isValidEmail(userRegisterFirst.getEmail())) {

				String passwordEncryt = CustomEncryption
						.convertMD5(userRegisterFirst.getPassword());
				userRegisterFirst.setPassword(passwordEncryt);

				Spotshotendpoint.Builder builder = new Spotshotendpoint.Builder(
						AndroidHttp.newCompatibleTransport(),
						new JacksonFactory(), null);

				builder = CloudEndpointUtils.updateBuilder(builder);
				Spotshotendpoint userEndpoint = builder.build();
				// BooleanResponse boolResponse = null;

				try {

					CollectionResponseUser cResponseUser = userEndpoint
							.getUserByEmailEndpoint(
									userRegisterFirst.getEmail()).execute();
					sEmail = cResponseUser.getItems().get(0).getEmail();

				} catch (IOException e) {
					e.printStackTrace();
				}

				if (sEmail == null) {
					// User user = new User();
					// user.setEmail(email);
					// user.setPassword(passwordEncryt);
					// user.setBirthday(new DateTime(etBirthday.getText()
					// .toString()));
					// user.setBirthday(new DateTime("1989-12-31"));
					// user.setGender(CustomDialog.answerDialogList());
					// i.putExtra("dataUser", new String[] {
					// user.getEmail(),
					// user.getPassword(),
					// user.getGender().toString(),
					// user.getBirthday().toString() });
					// i.putExtra("password", value)

					typeResponse = 0;
				} else {
					typeResponse = 1;
				}
			} else {
				typeResponse = 2;
			}

			return typeResponse;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			CustomDialog.Loading.getPbLoading().setProgress(values[0]);
		}

		@Override
		protected void onPostExecute(Integer result) {

			CustomDialog.Loading.getDialog().dismiss();

			switch (result) {
			case 0:
				Intent i = new Intent(context, RegisterUser2Activity.class);

				// i.putExtra("UserSpotShop", user);
				// User user = new User();
				// user.setEmail(email);
				// user.setPassword(passwordEncryt);
				// user.setBirthday(new
				// DateTime(etBirthday.getText().toString()));
				// user.setBirthday(new DateTime("1989-12-31"));
				// user.setGender(CustomDialog.answerDialogList());

				i.putExtra("dataUser",
						new String[] { userRegisterFirst.getEmail(),
								userRegisterFirst.getPassword(),
								userRegisterFirst.getGender().toString(),
								userRegisterFirst.getBirthday().toString() });

				context.startActivity(i);
				activity.finish();
				break;
			case 1:
				CustomDialog.showDialogAlert(context, "Cuenta ya existente",
						"El correo ingresado ya existe",
						R.drawable.image__warning);

				break;
			case 2:
				CustomDialog.showDialogAlert(context, "Correo Incorrecto",
						"El correcto ingresado esta mal escrito",
						R.drawable.image__warning);
				break;
			}
		}
	}

	public class TaskSearchNeighBors extends AsyncTask<Void, Void, Integer> {

		private int typeResponse = -1;
		private User userSearchN;

		public TaskSearchNeighBors(User userSearchN) {
			this.userSearchN = userSearchN;
		}

		@Override
		protected void onPreExecute() {

		}

		@Override
		protected Integer doInBackground(Void... params) {

			int iResponse = VerifyHardware.checkNetworkStatus(activity);

			switch (iResponse) {
			case 0:
			case 1:

				Spotshotendpoint.Builder builder = new Spotshotendpoint.Builder(
						AndroidHttp.newCompatibleTransport(),
						new JacksonFactory(), null);
				builder = CloudEndpointUtils.updateBuilder(builder);
				Spotshotendpoint photoEndpoint = builder.build();

				try {
					User userResponse = photoEndpoint.validateUser(
							userSearchN.getEmail(), userSearchN.getPassword(),
							"0").execute();

					if (userResponse == null) {
						typeResponse = 0;
						Log.i("LOG", "Es nullo");
					} else {
						typeResponse = 1;
						Log.i("LOG", "No es nullo");

					}

				} catch (IOException e) {
					e.printStackTrace();
				}

				break;
			case 2:
				typeResponse = 2;
				break;
			}

			return typeResponse;
		}

		@Override
		protected void onProgressUpdate(Void... values) {

		}

		@Override
		protected void onPostExecute(Integer result) {

			Log.i("LOG", "Es " + result);

			switch (result) {
			case 0:
				CustomDialog
						.showDialogAlert(
								context,
								"Datos Incorrectos",
								"El correo o password son incorrectos, vuelva a intentar  ",
								R.drawable.image__warning);

				break;

			case 1:

				loginUser.saveSharedPreferences(userSearchN.getEmail(),
						userSearchN.getPassword(), userSearchN.getId(),
						userSearchN.getUsername());

				Intent i = new Intent(context, CameraActivity.class);

				context.startActivity(i);
				activity.finish();

				break;

			case 2:
				CustomDialog.showDialogAlert(context, "Error de Conexion ",
						"Problema de Red o Internet ",
						R.drawable.image__warning);

				break;

			case 3:
				break;
			}
		}
	}

	public class TaskSearchFriends extends AsyncTask<Void, Integer, Integer> {
		private int typeResponse = -1;
		private List<Friendship> alFriends;
		private List<Item_friend> alItemFriends;
		private Long idUser;
		private ListView lvFriends;

		public TaskSearchFriends(ListView lvFriends, Long idUser) {
			this.lvFriends = lvFriends;
			this.idUser = idUser;

			alItemFriends = new ArrayList<Item_friend>();
		}

		@Override
		protected void onPreExecute() {
			CustomDialog.Loading.showDialogLoading(context, "Cargando",
					"Cargando", 1234);
		}

		@Override
		protected Integer doInBackground(Void... params) {

			int iResponse = VerifyHardware.checkNetworkStatus(activity);

			switch (iResponse) {
			case 0:
			case 1:

				Spotshotendpoint.Builder builder = new Spotshotendpoint.Builder(
						AndroidHttp.newCompatibleTransport(),
						new JacksonFactory(), null);
				builder = CloudEndpointUtils.updateBuilder(builder);
				Spotshotendpoint userEndpoint = builder.build();

				try {

					CollectionResponseFriendship collectionFriends = userEndpoint
							.getFriendships(idUser).execute();

					alFriends = collectionFriends.getItems();
					typeResponse = 0;

				} catch (IOException e) {
					e.printStackTrace();
				}

				break;
			case 2:
				typeResponse = 1;
				break;
			}

			return typeResponse;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {

			CustomDialog.Loading.getPbLoading().setProgress(values[0]);
		}

		@Override
		protected void onPostExecute(Integer result) {

			Log.i("LOG", "Es " + result);
			CustomDialog.Loading.getDialog().dismiss();

			switch (result) {

			case 0:

				if (alFriends != null) {

					for (int i = 0; i < alFriends.size(); i++) {

						Friendship frienship = alFriends.get(i);
						alItemFriends.add(new Item_friend(frienship
								.getTargetFriendUsername(), "AQP UP",
								R.drawable.image__profile_avatar_mini, frienship.getTargetFriendId()));

					}

					lvFriends.setAdapter(new FriendAdapter(activity, context,
							
							alItemFriends));

				}

				break;

			case 1:
				CustomDialog.showDialogAlert(context, "Error de Conexion ",
						"Problema de Red o Internet ",
						R.drawable.image__warning);

				break;
			}
		}
	}

	public class TaskAddFriend extends AsyncTask<Void, Integer, Integer> {
		private int typeResponse = -1;
		private String usernameTarget;
		private Long idUser;

		public TaskAddFriend(String usernameTarget, Long idUser) {
			this.usernameTarget = usernameTarget;
			this.idUser = idUser;
		}

		@Override
		protected void onPreExecute() {
			CustomDialog.Loading.showDialogLoading(context, "Cargando",
					"Cargando", 1234);
		}

		@Override
		protected Integer doInBackground(Void... params) {

			int iResponse = VerifyHardware.checkNetworkStatus(activity);

			switch (iResponse) {
			case 0:
			case 1:

				Spotshotendpoint.Builder builder = new Spotshotendpoint.Builder(
						AndroidHttp.newCompatibleTransport(),
						new JacksonFactory(), null);
				builder = CloudEndpointUtils.updateBuilder(builder);
				Spotshotendpoint endpoint = builder.build();

				try {
					// falta modificar
					endpoint.sendFriendshipRequestbyUsername(idUser,
							usernameTarget);
					typeResponse = 0;

				} catch (IOException e) {
					e.printStackTrace();
				}

				break;
			case 2:
				typeResponse = 1;
				break;
			}

			return typeResponse;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {

			CustomDialog.Loading.getPbLoading().setProgress(values[0]);
		}

		@Override
		protected void onPostExecute(Integer result) {

			Log.i("LOG", "Es " + result);
			CustomDialog.Loading.getDialog().dismiss();

			switch (result) {

			case 0:

				CustomDialog.showDialogAlert(context, "Solicitud enviada ",
						"Espere la confirmacion de su amigo ",
						R.drawable.image__warning);

				break;

			case 1:
				CustomDialog.showDialogAlert(context, "Error de Conexion ",
						"Problema de Red o Internet ",
						R.drawable.image__warning);

				break;
			}
		}
	}

	public class TaskRequestFriend extends AsyncTask<Void, Integer, Integer> {

		private int typeResponse = -1;
		private ListView lvRequestFriend;
		private List<Friendship> lFriendShipRequest;
		private List<Item_request_friend> alItemFriendsRequest;
		private Long idUser;

		public TaskRequestFriend(ListView lvRequestFriend, Long idUser) {

			this.lvRequestFriend = lvRequestFriend;
			this.idUser = idUser;

			alItemFriendsRequest = new ArrayList<Item_request_friend>();
		}

		@Override
		protected void onPreExecute() {
			CustomDialog.Loading.showDialogLoading(context, "Cargando",
					"Cargando", 1234);
		}

		@Override
		protected Integer doInBackground(Void... params) {

			int iResponse = VerifyHardware.checkNetworkStatus(activity);

			switch (iResponse) {
			case 0:
			case 1:

				Spotshotendpoint.Builder builder = new Spotshotendpoint.Builder(
						AndroidHttp.newCompatibleTransport(),
						new JacksonFactory(), null);
				builder = CloudEndpointUtils.updateBuilder(builder);
				Spotshotendpoint endpoint = builder.build();

				try {

					CollectionResponseFriendship collectionRFriendShip = endpoint
							.getPendingFriendships(idUser).execute();
					lFriendShipRequest = collectionRFriendShip.getItems();

					typeResponse = 0;

				} catch (IOException e) {
					e.printStackTrace();
				}

				break;
			case 2:
				typeResponse = 1;
				break;
			}

			return typeResponse;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {

			CustomDialog.Loading.getPbLoading().setProgress(values[0]);
		}

		@Override
		protected void onPostExecute(Integer result) {

			Log.i("LOG", "Es " + result);
			CustomDialog.Loading.getDialog().dismiss();

			switch (result) {

			case 0:

				if (lFriendShipRequest != null) {

					for (int i = 0; i < lFriendShipRequest.size(); i++) {

						Friendship frienship = lFriendShipRequest.get(i);
						alItemFriendsRequest.add(new Item_request_friend(
								frienship.getTargetFriendUsername(),
								R.drawable.image__profile_avatar_mini));

					}

					lvRequestFriend.setAdapter(new RequestFriendAdapter(
							activity, alItemFriendsRequest));

				}

				break;

			case 1:
				CustomDialog.showDialogAlert(context, "Error de Conexion ",
						"Problema de Red o Internet ",
						R.drawable.image__warning);

				break;
			}
		}
	}

	private void createStorageBucket() {
		TaskCreateBucket task = new TaskCreateBucket();
		task.context = context;
		task.activity = activity;
		task.execute();
	}

	public SharedPreferences getSh_Pref() {
		return sh_Pref;
	}

}