package com.aqpup.waitforit.utils;

import android.content.Context;
import android.graphics.Typeface;

public class CustomFont {

	private static  Typeface tfGeneralApp;
	private static  Typeface tMessageApp;

	private CustomFont() {
		
	}

	public static Typeface getTfFontGeneral(){
		return tfGeneralApp;
	}
	
	public static void setTfFontGeneralApp(String typeFont, Context context) {
		
		tfGeneralApp = Typeface.createFromAsset(context.getAssets(), "fonts/"+typeFont);

	}
	
	public static Typeface getTfFontMessage() {
		return tMessageApp;
	}
	
	public static void setTfFontMessageApp(String typeFont, Context context) {
		
		tMessageApp = Typeface.createFromAsset(context.getAssets(), "fonts/"+typeFont);
	}

}
