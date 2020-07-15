package co.com.rni.encuestadormovil.util;

import android.content.Context;
import android.os.AsyncTask;

import co.com.rni.encuestadormovil.sqlite.DbHelper;
import co.com.rni.encuestadormovil.sqlite.DbHelperVivanto;

/**
 * Created by javierperez on 4/02/16.
 */
public class asyncPrepararBD extends AsyncTask<String, Integer, Boolean> {

    private Context ctx;
    private responsePrepararBD callback;
    private DbHelper myDB;
    //private DbHelperVivanto myDBVI;
    private Integer conteo;

    public asyncPrepararBD(Context ctx, responsePrepararBD callback) {
        this.ctx = ctx;
        this.callback = callback;
        myDB = new DbHelper(ctx);
        //myDBVI = new DbHelperVivanto(ctx);
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try{
            conteo = myDB.getConteoVictimas();
            //conteo = myDBVI.getConteoRespuesta();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage() + "   calse asyncPrepraBF");
            return false;
        }
    }

    @Override
    protected void onProgressUpdate(Integer... progress){
    }

    @Override
    protected void onPostExecute(Boolean result){
        callback.resultado(result, conteo);
    }
}

