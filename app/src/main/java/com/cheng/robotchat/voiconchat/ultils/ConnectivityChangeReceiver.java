package com.cheng.robotchat.voiconchat.ultils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

/**
 * Created by Welcome on 4/18/2016.
 */
public class ConnectivityChangeReceiver extends BroadcastReceiver {

    private OnConnectivityChangedListener listener;

    public ConnectivityChangeReceiver(OnConnectivityChangedListener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean val = false;
        final android.net.NetworkInfo mobile = connectivity
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobile.isAvailable() && mobile.isConnected()) {
            val = true;
        } else {
        }
        final android.net.NetworkInfo wifi = connectivity
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifi.isAvailable() && wifi.isConnected()) {
            val = true;
        } else {
        }
        listener.onConnectivityChanged(val);

    }

    public interface OnConnectivityChangedListener {
        void onConnectivityChanged(boolean isConnected);
    }
}