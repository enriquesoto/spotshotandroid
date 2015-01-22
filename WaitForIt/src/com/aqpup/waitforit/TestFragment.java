package com.aqpup.waitforit;

import com.aqpup.waitforit.utils.CustomFont;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public final class TestFragment extends Fragment {
	
	private  int imageSource;
//	private String messageTitle;
	private int imessageTitle;
	private String messageContent;
	public  int id;
	
	
	public TestFragment(int imageSource, int messageTitle, String messageContent, int  id) {
		
		this.imageSource = imageSource;
		this.imessageTitle =messageTitle;
		this.messageContent = messageContent;
		this.id = id;
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
    	ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_page,  container,false);
		ImageView ivIcon = (ImageView)root.findViewById(R.id.ivIcon);
		
		ImageView ivMessageTitle  = (ImageView)root.findViewById(R.id.ivMessageTitleSplash);
		TextView tvMessageContent  = (TextView)root.findViewById(R.id.tvMessageContentSplash);
		
		ivIcon.setImageResource(imageSource);
//		tvMessageTitle.setText(messageTitle);
//		tvMessageTitle.setTypeface(CustomFont.getTfFontApp());
		ivMessageTitle.setImageResource(imessageTitle);
		tvMessageContent.setText(messageContent);
		tvMessageContent.setTypeface(CustomFont.getTfFontMessage());
		setRetainInstance(true);

		return root;

    }

}
