package com.aqpup.waitforit.navigation;

import org.brickred.socialauth.android.SocialAuthAdapter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.aqpup.waitforit.PresentationActivity;
import com.aqpup.waitforit.R;
import com.aqpup.waitforit.utils.CustomLoginUser;
import com.aqpup.waitforit.utils.RoundImage;

public class DashBoardActivity extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;
	private CharSequence mTitle;
	// static Activity context;
	static ActionBarActivity context;
	private SharedPreferences sh_Pref;
	private Editor toEdit;

	private CustomLoginUser loginUser;
	private SocialAuthAdapter adapter;
	private String aTitles[];
	private ImageView ivAvatar;
	private RoundImage roundedImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);

		

		context = this;
		aTitles = getResources().getStringArray(R.array.array_titles_nav_drawer);

		loginUser = CustomLoginUser.getInstanceCustomLoginUser();
		// loginUser.setContext(context);
		adapter = CustomLoginUser.getInstanceCustomLoginUser().getAdapter();

		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);

		mTitle = getTitle();
		// mNavigationDrawerFragment.setUser((User)
		// getIntent().getSerializableExtra("objUser"));

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));

		// Log.i("salida1", "salidaaaaaaaaaaaa");
		
		//Seleciona por defecto el perfil
		onNavigationDrawerItemSelected(1);
		
		//ivAvatar = (ImageView) findViewById(R.id.ivHeaderAvatar);
        //Bitmap bm = BitmapFactory.decodeResource(getResources(),R.drawable.image__splash_bg_3);
        //roundedImage = new RoundImage(bm);
        //Bitmap bmNew = roundedImage.getResizedBitmap(bm, 100, 100);
        //ivAvatar.setImageDrawable(roundedImage);
        
        
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		// FragmentManager fragmentManager = getFragmentManager()
		FragmentManager fragmentManager = getSupportFragmentManager();

		// Toast.makeText(getApplicationContext(),
		// "onNavigationDrawerItemSelected " + position,
		// Toast.LENGTH_SHORT).show();

		switch (position) {
		case 1:
			fragmentManager.beginTransaction()
					.replace(R.id.container, new MyPerfilFragment()).commit();
			// Toast.makeText(getApplicationContext(),"view 0",
			// Toast.LENGTH_SHORT).show();
			break;
		case 2:

			// Toast.makeText(context,"ta aqui", Toast.LENGTH_SHORT).show();
			fragmentManager.beginTransaction()
					.replace(R.id.container, new MyPhotosFragment()).commit();
			// Toast.makeText(getApplicationContext(),"view 1",
			// Toast.LENGTH_SHORT).show();
			break;
		case 3:
			fragmentManager.beginTransaction()
					.replace(R.id.container, new MyFriendsFragment()).commit();
			// Toast.makeText(getApplicationContext(),"view 2",
			// Toast.LENGTH_SHORT).show();
			break;
		case 4:
			closeSession();
			// Toast.makeText(getApplicationContext(),"view 3",
			// Toast.LENGTH_SHORT).show();
			break;
		}
		
		onSectionAttached(position-1);
	}

	public void onSectionAttached(int number) {
		switch (number) {
		case 0:
			mTitle = aTitles[0];
			break;
		case 1:
			mTitle = aTitles[1];
			break;
		case 2:
			mTitle = aTitles[2];
			break;
		case 3:
			mTitle = aTitles[3];
			break;

		}
	}

	public void restoreActionBar() {

		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		//actionBar.setTitle(mTitle);
		actionBar.setTitle(Html.fromHtml("<font color='#FFFFFF'>"+mTitle+"</font>"));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
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

	// ----------------------------------------------------------------
	/*
	public static class PlaceholderFragment extends Fragment {

		private static final String ARG_SECTION_NUMBER = "section_number";

		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			// Toast.makeText(context,"es" + , Toast.LENGTH_SHORT).show();

			View rootView = null;
			int number = getArguments().getInt(ARG_SECTION_NUMBER);

			switch (number) {
			case 1:
				rootView = inflater.inflate(R.layout.fragment_myperfil,
						container, false);
				break;
			case 2:
				Toast.makeText(context, "ta aqui", Toast.LENGTH_SHORT).show();
				rootView = inflater.inflate(R.layout.main, container, false);
				break;
			case 3:
				rootView = inflater.inflate(R.layout.fragment_mysocial,
						container, false);
				break;
			case 4: // Se desloguea
				break;
			}

			return rootView;
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((DashBoardActivity) activity).onSectionAttached(getArguments()
					.getInt(ARG_SECTION_NUMBER));
		}

	}
    */
	// ----------------------------------------------------------------

	public void closeSession() {

		boolean status = false;

		if (loginUser.getProviderName() != null) {

			status = adapter.signOut(context, loginUser.getProviderName());
			// status = adapter.signOut(loginUser.getContext(),
			// Provider.FACEBOOK.toString());

			// Log.i("salida", "entro salida provider"+ String.valueOf(status));
			// Log.i("salida", "entro  provider"+Provider.FACEBOOK.toString());
		}

		sh_Pref = getSharedPreferences("Login Credentials", MODE_PRIVATE);
		toEdit = sh_Pref.edit();
		toEdit.putString("email", null);
		toEdit.putString("passwordEncryt", null);
		toEdit.commit();

		// Toast.makeText(context,"Cerrar session", Toast.LENGTH_SHORT).show();

		Intent i = new Intent(DashBoardActivity.this,
				PresentationActivity.class);
		startActivity(i);

		finish();
	}

}
