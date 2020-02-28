package co.com.rni.encuestadormovil.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by ASUS on 13/12/2016.
 */

public class comprobarConexionInternet {


    public int isNetworkConnected(Context context) {

        int val = 0;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context
                .CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info == null || !info.isConnected() || !info.isAvailable()) {
            val = 0;
        }else if(info.getType()==ConnectivityManager.TYPE_WIFI){
            val = 1;
        }else if (info.getType()==ConnectivityManager.TYPE_MOBILE){
            val = 2;
        }
        return val;
    }
}
