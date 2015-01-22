package com.aqpup.waitforit.navigation;

import com.aqpup.waitforit.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;


public class MyPhotos_GalleryFragment extends Fragment implements AdapterView.OnItemClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    	View v = inflater.inflate(R.layout.fragment_myphotos_gallery, container, false);

        return v;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

    }
}