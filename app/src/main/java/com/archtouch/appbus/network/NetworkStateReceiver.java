package com.archtouch.appbus.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.archtouch.appbus.application.AppBusApplication;

public class NetworkStateReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

        boolean isWifiConnected = false;
        boolean isMobileConnected = false;

        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (networkInfo != null)
            isWifiConnected = networkInfo.isConnected();

        networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (networkInfo != null)
            isMobileConnected = networkInfo.isConnected();

        if(isWifiConnected || isMobileConnected) {
            Toast.makeText(context, "Connection OK", Toast.LENGTH_SHORT).show();
//            ((AppBusApplication) context.getApplicationContext()).networkConnection = true;
        } else {
            Toast.makeText(context,"Connection NOK",Toast.LENGTH_SHORT).show();
//            ((AppBusApplication) context.getApplicationContext()).networkConnection = false;
        }
//        Log.d("network status", "wifi == " + isWifiConnected + " and mobile == " + isMobileConnected);
    }
}
