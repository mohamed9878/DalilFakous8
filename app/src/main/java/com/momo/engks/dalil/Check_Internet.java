package com.momo.engks.dalil;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Check_Internet {

    Context ctx;


    public Check_Internet(Context context) {

        this.ctx = context;
    }

    public boolean isPhoneConnected() {

        ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {

                return true;

            }


        }

        return false;

    }


}
