package co.com.rni.encuestadormovil.util;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by javierperez on 4/02/16.
 */
public class asyncPrepararParametros extends AsyncTask<String, Integer, Boolean> {

    private Context ctx;
    private responseParametros callback;
    private static final String TAG = "asyncPrepararParametros";

    public asyncPrepararParametros(Context ctx, responseParametros callback) {
        this.ctx = ctx;
        this.callback = callback;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try{
            //parametros.CargaInicial(ctx);
            Log.i(TAG, "MyClass.getView() — "+"ok") ;
            return true;
        }catch (Exception e){
            e.printStackTrace();
            Log.e(TAG, "MyClass.getView() —  "+e.getMessage()) ;
            return false;
        }
    }

    @Override
    protected void onProgressUpdate(Integer... progress){
    }

    @Override
    protected void onPostExecute(Boolean result){
        callback.cargaFinalizada(result);
    }
}

