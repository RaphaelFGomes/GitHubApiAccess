package com.gomes.ferreira.raphael.githubapiaccess.Network;

import android.content.Context;
import android.net.ConnectivityManager;

public class Internet {

    public Internet(){}

    public boolean isInternetAvailable(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getActiveNetworkInfo() != null){
            if (connectivityManager.getActiveNetworkInfo().isConnected()){
                return true;
            }
        }
        return false;
    }
}
