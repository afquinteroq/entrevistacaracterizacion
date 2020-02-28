package co.com.rni.encuestadormovil.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;

import it.sauronsoftware.ftp4j.FTPClient;

/**
 * Created by ASUS on 15/03/2017.
 */

public class asynDownloadBDFTP extends AsyncTask<String, Integer, Boolean> {

    private Context ctx;
    private responseUpdateDBVivanto callback;
    String ftpServer;
    String user;
    String password;


    public asynDownloadBDFTP(Context ctx, responseUpdateDBVivanto callback, String ftpServer, String user, String password){
        this.ctx = ctx;
        this.callback = callback;
        this.ftpServer = ftpServer;
        this.user = user;
        this.password = password;


    }

    @Override
    protected Boolean doInBackground(String... params) {
        FTPClient client = new FTPClient();
        try {



            AssetManager assetManager = ctx.getAssets();
            client.connect(ftpServer,21);
            client.login(user, password);
            client.setType(FTPClient.TYPE_BINARY);
            client.changeDirectory("/Parametricas/");
            File root = Environment.getExternalStorageDirectory();
            String DB_PATH = Environment.getExternalStorageDirectory() + "/db/";
            Uri ruta = Uri.parse(DB_PATH);
            File directory = new File(ruta.getPath());
            if (!directory.exists()) {
                directory.mkdirs();
            }
            File file = new File(root, "/db/dbencuestadormovil.db.zip");
            client.download("dbencuestadormovil.db.zip", file);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    protected void onPostExecute(Boolean result){
        callback.resultado(result);

    }
}
