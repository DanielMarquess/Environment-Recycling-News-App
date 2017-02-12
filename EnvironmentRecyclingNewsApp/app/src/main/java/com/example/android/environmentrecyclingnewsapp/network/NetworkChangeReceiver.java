package com.example.android.environmentrecyclingnewsapp.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.android.environmentrecyclingnewsapp.R;
import com.example.android.environmentrecyclingnewsapp.utils.NetworkUtil;

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (checkInternet(context)) {
            Toast.makeText(context, R.string.network_available_toast_message, Toast.LENGTH_LONG).show();
        }
    }

    public boolean checkInternet(Context context) {
        if (NetworkUtil.isNetworkConnected(context)) {
            return true;
        } else {
            return false;
        }
    }
}
