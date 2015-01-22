package com.aqpup.waitforit;

import java.io.IOException;

import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthAdapter.Provider;
import org.brickred.socialauth.android.SocialAuthError;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.aqpup.model.spotshotendpoint.Spotshotendpoint;
import com.aqpup.model.spotshotendpoint.model.BooleanResponse;
import com.aqpup.model.spotshotendpoint.model.User;
import com.aqpup.waitforit.camera.CameraActivity;
import com.aqpup.waitforit.utils.BaseSampleActivity;
import com.aqpup.waitforit.utils.CirclePageIndicator;
import com.aqpup.waitforit.utils.CloudEndpointUtils;
import com.aqpup.waitforit.utils.CustomDialog;
import com.aqpup.waitforit.utils.CustomFont;
import com.aqpup.waitforit.utils.CustomLoginUser;
import com.aqpup.waitforit.utils.CustomLoginUser.ProfileDataListener;
import com.aqpup.waitforit.utils.PageIndicator;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;

public class PresentationActivity extends BaseSampleActivity {

	private TestFragmentAdapter mAdapter;
	private ViewPager mPager;
	private PageIndicator mIndicator;

	private int imessagesTitle[];
	private String messagesContent[];
	private int icons[];
	private Button bSignIn;
	private Button bSignUp;

	private int lastIndexOfViewPagerChildren = numberOfViewPagerChildren - 1;
	public static int numberOfViewPagerChildren = 4;

	private CustomLoginUser loginUser;
	private SocialAuthAdapter adapter;
	private Button bSignInFacebook;
	private Button bSignInTwitter;
	private Button bSignInGooglePlus;
	private String providerName;

	Context context;
	Activity activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_presentation);
		context = this;
		activity = this;

		initData();

		bSignIn = (Button) findViewById(R.id.bSignIn);
		bSignIn.setTypeface(CustomFont.getTfFontGeneral());

		bSignIn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getApplicationContext(),
						LoginActivity.class);
				startActivity(i);

				finish();
			}
		});

		bSignUp = (Button) findViewById(R.id.bSignUp);
		bSignUp.setTypeface(CustomFont.getTfFontGeneral());
		// bSignUp,setVisible(View.g);
		bSignUp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getApplicationContext(),
						RegisterUserActivity.class);
				startActivity(i);
			}
		});

		// ------- login con social network ----

		loginUser = CustomLoginUser.getInstanceCustomLoginUser();
		loginUser.setContext(context);
		loginUser.setActivity(activity);

		adapter = new SocialAuthAdapter(new DialogListener() {

			@Override
			public void onComplete(Bundle values) {
				Log.d("Custom-UI", "Successful");

				// Sacar datos de profile
				loginUser.setProviderName(values.getString(SocialAuthAdapter.PROVIDER));			
				Log.i("salida", "provider"+values.getString(SocialAuthAdapter.PROVIDER));
				ProfileDataListener profileUser = loginUser.new ProfileDataListener();
				adapter.getUserProfileAsync(profileUser);

			}

			@Override
			public void onError(SocialAuthError error) {
				Log.d("Custom-UI", "Error");
				error.printStackTrace();
			}

			@Override
			public void onCancel() {
				Log.d("Custom-UI", "Cancelled");
			}

			@Override
			public void onBack() {
				Log.d("Custom-UI", "Dialog Closed by pressing Back Key");

			}

		});

		loginUser.setAdapter(adapter);

		bSignInFacebook = (Button) findViewById(R.id.bSignInFacebook);
		bSignInTwitter = (Button) findViewById(R.id.bSignInTwitter);
		bSignInGooglePlus = (Button) findViewById(R.id.bSignInGoogle);

		bSignInFacebook.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				adapter.authorize(context, Provider.FACEBOOK);
				// loginUser.setProviderName(Provider.FACEBOOK.toString());

			}
		});

		bSignInTwitter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				adapter.addCallBack(Provider.TWITTER,
						"http://spotshotme.com/oauth2callback");
				adapter.authorize(context, Provider.TWITTER);
				// loginUser.setProviderName(Provider.TWITTER.toString());

			}
		});

		bSignInGooglePlus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				adapter.addCallBack(Provider.GOOGLEPLUS,
						"http://spotshotme.com/oauth2callback");
				adapter.authorize(context, Provider.GOOGLEPLUS);
				// loginUser.setProviderName(Provider.GOOGLEPLUS.toString());

			}
		});
		// ---------------------------------------------

		mAdapter = new TestFragmentAdapter(getSupportFragmentManager(),
				imessagesTitle, messagesContent, icons);

		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);

		CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator);
		mIndicator = indicator;
		indicator.setViewPager(mPager);

		final float density = getResources().getDisplayMetrics().density;
		// indicator.setBackgroundColor(0xFFCCCCCC);
		indicator.setRadius(3 * density);
		// indicator.setPageColor(0xFF888888);
		// indicator.setFillColor(0x880000FF);
		// indicator.setStrokeColor(0xFF000000);
		// indicator.setStrokeWidth(2 * density);

		final LayerDrawable background = (LayerDrawable) mPager.getBackground();

		background.getDrawable(0).setAlpha(0);
		background.getDrawable(1).setAlpha(255);
		background.getDrawable(2).setAlpha(255);

		mPager.setPageTransformer(true, new ViewPager.PageTransformer() {
			@Override
			public void transformPage(View view, float position) {

				int index = (Integer) view.getTag();
				Drawable currentDrawableInLayerDrawable;
				currentDrawableInLayerDrawable = background.getDrawable(index);

				if (position <= -1 || position >= 1) {
					currentDrawableInLayerDrawable.setAlpha(0);
				} else if (position == 0) {
					currentDrawableInLayerDrawable.setAlpha(255);
				} else {
					currentDrawableInLayerDrawable.setAlpha((int) (255 - Math
							.abs(position * 255)));
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);
		return true;

	}

	public void initData() {

		imessagesTitle = new int[] { R.drawable.image__logo_title_mini,
				R.drawable.image__logo_title_mini,
				R.drawable.image__logo_title_mini,
				R.drawable.image__logo_title_mini };

		messagesContent = new String[] { getString(R.string.content_splash_1),
				getString(R.string.content_splash_2),
				getString(R.string.content_splash_3),
				getString(R.string.content_splash_4) };

		icons = new int[] { R.drawable.image__logo, R.drawable.image__logo,
				R.drawable.image__logo, R.drawable.image__logo };

	}

	@Override
	public void onResume() {

		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onSaveInstanceState(Bundle savedState) {
		super.onSaveInstanceState(savedState);
	}

	
}