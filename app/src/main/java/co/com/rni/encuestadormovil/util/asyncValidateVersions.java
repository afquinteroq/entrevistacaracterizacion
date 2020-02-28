package co.com.rni.encuestadormovil.util;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;

import java.io.FileReader;
import java.util.List;


import co.com.rni.encuestadormovil.model.emc_usuarios;
import co.com.rni.encuestadormovil.model.emc_version;
import it.sauronsoftware.ftp4j.FTPClient;



/**
 * Created by ASUS on 19/02/2017.
 */

public class asyncValidateVersions   extends AsyncTask<String, Integer, Boolean> {

    private Context ctx;
    private responseValidateVersions callback;
    String ftpServer;
    String user;
    String password;
    private String version;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public asyncValidateVersions(Context ctx, responseValidateVersions callback, String ftpServer, String user, String password) {
        this.ctx = ctx;
        this.callback = callback;
        this.ftpServer = ftpServer;
        this.user = user;
        this.password = password;

    }
    public asyncValidateVersions(){}

    @Override
    protected Boolean doInBackground(String... params) {
        //boolean v = readVersionFTP();

        FTPClient client = new FTPClient();
        try {

            String DB_PATH = Environment.getExternalStorageDirectory() + "/db/";
            Uri ruta = Uri.parse(DB_PATH);
            File directory = new File(ruta.getPath());
            if (!directory.exists()) {
                directory.mkdirs();
            }

            client.connect(ftpServer,21);
            client.login(user, password);
            client.setType(FTPClient.TYPE_BINARY);
            client.changeDirectory("/Parametricas/");
            File root = Environment.getExternalStorageDirectory();
            //http://www.sgoliver.net/blog/ficheros-en-android-ii-memoria-externa-tarjeta-sd/
            //http://www.sgoliver.net/blog/ficheros-en-android-i-memoria-interna/
            //http://www.programcreek.com/java-api-examples/android.net.NetworkInfo
            File file = new File(root, "/db/versionbase.txt");
            client.download("versionbase.txt", file);


            String cadena;
            File forigs = new File(Environment.getExternalStorageDirectory(), "/db/versionbase" + ".txt");
            FileReader f = new FileReader(forigs);
            BufferedReader b = new BufferedReader(f);
            while((cadena = b.readLine())!=null) {
                System.out.println(cadena);
                setVersion(cadena);
            }
            b.close();

            String[] paramsV = {"BASE"};
            final List<emc_version> tmIdVersion = emc_version.find(emc_version.class, "vernombre = ?", paramsV);

            if(Integer.parseInt(tmIdVersion.get(0).getVer_version()) >= Integer.parseInt(getVersion()))
            {
                return true;
            }else{
                return false;

            }


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }
    public boolean readVersionFTP(){


        FTPClient client = new FTPClient();
        try {

            client.connect(ftpServer,21);
            client.login(user, password);
            client.setType(FTPClient.TYPE_BINARY);
            client.changeDirectory("/Parametricas/");
            File root = Environment.getExternalStorageDirectory();
            File file = new File(root, "localFile.txt");
            client.download("versionbase.txt", file);

            String cadena;
            File forigs = new File(Environment.getExternalStorageDirectory(), "localFile" + ".txt");
            FileReader f = new FileReader(forigs);
            BufferedReader b = new BufferedReader(f);
            while((cadena = b.readLine())!=null) {
                System.out.println(cadena);
                setVersion(cadena);
            }
            b.close();


            return true;

        } catch (Exception e) {
            e.printStackTrace();
            try {
                //client.disconnect(true);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
           // callback.actualizaEstado("Error al validar la versión");
            return false;
        }


    }
    @Override
    protected void onPostExecute(Boolean result){
        callback.validacionFinalizada(result, getVersion());

    }

}
