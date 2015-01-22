package com.aqpup.waitforit.navigation;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.aqpup.waitforit.R;
import com.aqpup.waitforit.utils.TabPageIndicator;

public class MyPhotosFragment_ extends Fragment {

	private static final String[] CONTENT = new String[] { "Mapa", "Galeria" };

	// private FragmentTabHost mTabHost;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_myphotos_, container, false);

		/*
		 * mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		 * mTabHost.setup(this, getSupportFragmentManager(),
		 * android.R.id.tabcontent);
		 * 
		 * mTabHost.addTab( mTabHost.newTabSpec("tab1").setIndicator("Tab 1",
		 * null), FragmentTab.class, null); mTabHost.addTab(
		 * mTabHost.newTabSpec("tab2").setIndicator("Tab 2", null),
		 * FragmentTab.class, null); mTabHost.addTab(
		 * mTabHost.newTabSpec("tab3").setIndicator("Tab 3", null),
		 * FragmentTab.class, null);
		 */

		// FragmentPagerAdapter adapter = new
		// GoogleMusicAdapter(getSupportFragmentManager());
		FragmentPagerAdapter adapter = new GoogleMusicAdapter(
				getChildFragmentManager());

		ViewPager pager = (ViewPager) v.findViewById(R.id.pager);
		pager.setAdapter(adapter);

		TabPageIndicator indicator = (TabPageIndicator) v
				.findViewById(R.id.indicator);
		indicator.setViewPager(pager);

		return v;

	}


	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	class GoogleMusicAdapter extends FragmentPagerAdapter {
		public GoogleMusicAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {

			/*
			switch (position) {
			case 0:

				
				break;
			case 1:

				break;

			}
			*/

			 //return TestFragment.newInstance(CONTENT[position % CONTENT.length]);
			 return TestFragment.newInstance();

		}

		@Override
		public CharSequence getPageTitle(int position) {
			return CONTENT[position % CONTENT.length].toUpperCase();
		}

		@Override
		public int getCount() {
			return CONTENT.length;
		}
	}
}