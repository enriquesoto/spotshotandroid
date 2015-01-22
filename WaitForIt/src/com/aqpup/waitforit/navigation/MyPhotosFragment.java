package com.aqpup.waitforit.navigation;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabWidget;
import android.widget.TextView;

import com.aqpup.waitforit.R;

public class MyPhotosFragment extends Fragment {

	private FragmentTabHost mTabHost;
	private TabWidget tabs;
	private TextView tvTitle;
	private String[] aTitles;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_myphotos, container, false);
		aTitles = getResources().getStringArray(R.array.array_titles_my_photos);

		mTabHost = (FragmentTabHost) v.findViewById(android.R.id.tabhost);
//		 = (TextView) view.findViewById(R.id.tabTitleText);
    //    tv.setText(tabText);
        
	//	tabs = (TabWidget) v.findViewById(android.R.id.tabs);

		mTabHost.setup(getActivity(), getChildFragmentManager(),
				android.R.id.tabcontent);
		
	    View tabView1 = createTabView(getActivity(), aTitles[0], 0);
	    View tabView2 = createTabView(getActivity(), aTitles[1], 1);

		mTabHost.addTab(
				mTabHost.newTabSpec("tab1").setIndicator(tabView1),
				MyPhotos_MapFragment.class, null);
		mTabHost.addTab(
				mTabHost.newTabSpec("tab2").setIndicator(tabView2),
				MyPhotos_GalleryFragment.class, null);
		
		/*
 	TabWidget tab = mTabHost.getTabWidget();
		for(int i = 0; i < tab.getChildCount(); i++) {
		    View viewTab = tab.getChildAt(i);

		    // Look for the title view to ensure this is an indicator and not a divider.
		    TextView tv = (TextView)v.findViewById(android.R.id.title);
		    
		    if(tv == null) {
		        continue;
		    }
		    viewTab.setBackgroundResource(R.drawable.xml__selector_tab_strip);
		}		
*/
		// mTabHost.setBackgroundColor(getResources().getColor(R.color.blue_dark_ss));

		return v;

	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}
	

    private static View createTabView(Context context, String tabText, int position) {
    	
        View view = null;
        
        if(position == 0){
        	view = LayoutInflater.from(context).inflate(R.layout.fragment_tab_left, null, false);	
        }
        else if (position == 1) {
        	view = LayoutInflater.from(context).inflate(R.layout.fragment_tab_right, null, false);
		}
        
        TextView tv = (TextView) view.findViewById(R.id.tabTitleText);
        tv.setText(tabText);
        return view;
    }
	
}