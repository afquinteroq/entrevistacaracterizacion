package co.com.rni.encuestadormovil.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;


import co.com.rni.encuestadormovil.MainActivity;

import static co.com.rni.encuestadormovil.util.general.isOnlineNet;

/**
 * Created by ASUS on 5/10/2017.
 */

public class asyncTestNet extends AsyncTask<String, Integer, Boolean> {

    private Context ctx;
    private responseTestNet callback;


    public asyncTestNet(Context ctx, responseTestNet callback){
        this.ctx = ctx;
        this.callback = callback;
    }




    @Override
    protected Boolean doInBackground(String... params) {
        if(isOnlineNet()){
            return true;
        }else{
            return false;
        }

    }

    /*@Override protected void onProgressUpdate(Integer... porc) {
        MainActivity m = new MainActivity();
        m.pgC.setProgress(porc[0]);
    }*/

    @Override
    protected void onPostExecute(Boolean result){

        //salida.append(res + "\n");
        callback.testFinalizado(result);
    }

}
