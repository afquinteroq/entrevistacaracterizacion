package co.com.rni.encuestadormovil.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static co.com.rni.encuestadormovil.util.general.redimensionarImagenMaximo;

/**
 * Created by ASUS on 13/07/2018.
 */

public class ansycGuardarSoporte extends AsyncTask<String, Integer, Boolean> {

    private Context ctx;
    private responseGuardarSoporte callback;
    private Bitmap bitmap;
    private String hogCodigo;
    private String nombreFoto;
    private String ruta;
    private String folder;

    public ansycGuardarSoporte(Context ctx, responseGuardarSoporte callback, Bitmap bitmap, String hogcodigo, String nombreFoto, String folder){
        this.ctx = ctx;
        this.callback = callback;
        this.bitmap = bitmap;
        this.hogCodigo = hogcodigo;
        this.nombreFoto = nombreFoto;
        this.folder = folder;
        //this.pgDMensaje = pgDMensaje;


    }

    @Override
    protected Boolean doInBackground(String... params) {
        bitmap = redimensionarImagenMaximo(bitmap, 1440,1500);
        ruta = guardarImagen(ctx.getApplicationContext(), /*hogCodigo+*/nombreFoto, bitmap);
        return true;
    }

    private String guardarImagen (Context context, String nombre, Bitmap imagen){


        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + folder;

        File dir = new File(file_path);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        File myPath = new File(dir, nombre + ".png");

        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(myPath);
            imagen.compress(Bitmap.CompressFormat.PNG, 100, fos);

            fos.flush();
        }catch (FileNotFoundException ex){
            ex.printStackTrace();
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return myPath.getAbsolutePath();
    }
    protected void onPostExecute(Boolean result){
        //pgDMensaje.hide();
        //pgDMensaje.dismiss();
        callback.resultado(result, ruta);
    }
}
