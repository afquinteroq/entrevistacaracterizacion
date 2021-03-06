package co.com.rni.encuestadormovil.util;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import co.com.rni.encuestadormovil.model.emc_usuarios;
import co.com.rni.encuestadormovil.model.emc_version;
import it.sauronsoftware.ftp4j.FTPClient;

/**
 * Created by ASUS on 11/05/2017.
 */

public class asyncUpdateDBVivanto extends AsyncTask<String, Integer, Boolean> {

    private Context ctx;
    private responseUpdateDBVivanto callback;
    String ftpServer;
    String user;
    String password;
    String remotePath;// "/EncuestasACargar";


    public asyncUpdateDBVivanto(Context ctx, responseUpdateDBVivanto callback, String ftpServer, String user, String password,String remotePath){
        this.ctx = ctx;
        this.callback = callback;
        this.ftpServer = ftpServer;
        this.user = user;
        this.password = password;
        this.remotePath = remotePath;

    }

    @Override
    protected Boolean doInBackground(String... params) {

       List<emc_usuarios> lsUsuario = emc_usuarios.find(emc_usuarios.class, null, null);


        FTPClient client = new FTPClient();
        try {

            deployDatabase(ctx);
            String packageName = ctx.getApplicationContext().getPackageName();
            String origen = "/data/data/" + packageName + "/databases/vivanto.zip";
            //String destino = Environment.getExternalStorageDirectory() + "/db/";
            String destino = "/data/data/" + packageName + "/databases/";
            descomprimirDB(origen,destino);

            borrarZip(origen);


            if(lsUsuario.size() > 0){

                for(int contUsu = 0; contUsu < lsUsuario.size(); contUsu++){

                    emc_usuarios tmUsuario = lsUsuario.get(contUsu);

                    List<emc_usuarios> usuario = emc_usuarios.find(emc_usuarios.class, "nombreusuario = '"+tmUsuario.getNombreusuario()+"'", null);

                    if(usuario.size() == 0)
                    {
                        emc_usuarios addUse = new emc_usuarios(tmUsuario.getIdusuario(), tmUsuario.getNombreusuario(), tmUsuario.getPassword(), tmUsuario.getTokenusuario(), tmUsuario.getFeclogin(),
                                tmUsuario.getFecvalidatoken(),tmUsuario.getIdPerfil(),tmUsuario.getNombrePerfil());
                        addUse.save();
                    }

                }

            }


            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    private void deployDatabase(Context ctx) throws IOException {
        //Open your local db as the input stream
        String packageName = ctx.getApplicationContext().getPackageName();
        String DB_PATH = "/data/data/" + packageName + "/databases/";
        //Create the directory if it does not exist
        File directory = new File(DB_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String DB_NAME = "vivanto.zip"; //The name of the source sqlite file
        //String DB_NAME = "versionbase.txt"; //The name of the source sqlite file

        //InputStream myInput = ctx.getAssets().open("cache");
        String archivo = Environment.getExternalStorageDirectory() + "/db/vivanto.zip";
        //String archivo = Environment.getExternalStorageDirectory() + "/versionbase.txt";
        InputStream myInput = new FileInputStream(archivo);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();



    }

    public boolean descomprimirDB(String ubicacion, String destino){

        String _Ubicacion_ZIP;

        String _Destino_Descompresion;

        boolean _Mantener_ZIP = true;

        _Ubicacion_ZIP = ubicacion;

        _Destino_Descompresion = destino;

        int size;

        byte[] buffer = new byte[2048];

        new File(_Destino_Descompresion).mkdirs(); //Crea la ruta de descompresion si no existe


        try {

            try {

                FileInputStream lector_archivo = new FileInputStream(_Ubicacion_ZIP);

                ZipInputStream lector_zip = new ZipInputStream(lector_archivo);

                ZipEntry item_zip = null;

                while ((item_zip = lector_zip.getNextEntry()) != null) {

                    Log.v("Descompresor", "Descomprimiendo " + item_zip.getName());


                    if (item_zip.isDirectory()) { //Si el elemento es un directorio, crearlo
                        Crea_Carpetas(item_zip.getName(), _Destino_Descompresion);

                    } else {

                        FileOutputStream outStream = new FileOutputStream(_Destino_Descompresion + item_zip.getName());

                        BufferedOutputStream bufferOut = new BufferedOutputStream(outStream, buffer.length);

                        while ((size = lector_zip.read(buffer, 0, buffer.length)) != -1) {

                            bufferOut.write(buffer, 0, size);

                        }

                        bufferOut.flush();
                        bufferOut.close();

                    }

                }

                lector_zip.close();

                lector_archivo.close();



                if(!_Mantener_ZIP)
                    new File(_Ubicacion_ZIP).delete();

                _Mantener_ZIP = true;

            } catch (Exception e) {
                Log.e("Descompresor", "Descomprimir", e);

            }

            //mProgresoDescompresion.dismiss();

        } catch (Exception e) {

            Log.e("Error", e.getMessage());
            _Mantener_ZIP = false;

        }

        return _Mantener_ZIP;
    }

    private void Crea_Carpetas(String dir, String location) {

        File f = new File(location + dir);

        if (!f.isDirectory()) {

            f.mkdirs();

        }

    }



    private void borrarZip(String zip){
        File fichero = new File(zip);
        if (fichero.delete())
            System.out.println("El fichero ha sido borrado satisfactoriamente");
        else
            System.out.println("El fichero no puede ser borrado");
    }

    @Override
    protected void onPostExecute(Boolean result){
        callback.resultado(result);

    }
}
