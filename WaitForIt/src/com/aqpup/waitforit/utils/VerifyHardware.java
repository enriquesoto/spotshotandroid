package com.aqpup.waitforit.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;

public class VerifyHardware {
	
	
	public static int checkNetworkStatus(final Activity activity) {

        //String networkStatus = "";
		int iNetworkStatus;
		
        final ConnectivityManager connMgr = (ConnectivityManager)  
                activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        final android.net.NetworkInfo wifi =  connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        
        final android.net.NetworkInfo mobile =  connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        
        if( wifi.isAvailable() ){

            //networkStatus = "wifi";
            iNetworkStatus = 0;

        }  else if( mobile.isAvailable() ){

            //networkStatus = "mobileData";
        	iNetworkStatus = 1;
        }  else  {

            //networkStatus = "noNetwork";
        	iNetworkStatus = 2;
        } 

        return iNetworkStatus;

    } 

}
