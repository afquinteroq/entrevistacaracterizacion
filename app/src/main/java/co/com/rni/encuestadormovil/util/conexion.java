package co.com.rni.encuestadormovil.util;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import it.sauronsoftware.ftp4j.FTPClient;

/**
 * Created by ASUS on 13/12/2016.
 */

public class conexion {

    private int version;

    public conexion(){}

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public boolean readVersionFTP(Context ctx, String ftpServer, String user, String password){


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
                setVersion(Integer.parseInt(cadena));
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
            return false;
        }


    }


}
