package co.com.rni.encuestadormovil.util;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.commons.net.ftp.FTP;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Random;


import co.com.rni.encuestadormovil.model.*;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferListener;

import static co.com.rni.encuestadormovil.util.general.fechaActual;


/**
 * Created by javierperez on 4/02/16.
 */
public class asyncUploadFile extends AsyncTask<String, Integer, Boolean> {

    private Context ctx;
    private responseUpload callback;
    String ftpServer;
    String user;
    String password;
    String fileName;
    String fileNameS;
    emc_usuarios usuarioLogin;

    public asyncUploadFile(Context ctx, responseUpload callback, String ftpServer, String user, String password, String fileName, emc_usuarios usuarioLogin) {
        this.ctx = ctx;
        this.callback = callback;
        this.ftpServer = ftpServer;
        this.user = user;
        this.password = password;
        this.fileName = fileName;
        this.usuarioLogin = usuarioLogin;
    }
    public asyncUploadFile() {

    }


    @Override
    protected Boolean doInBackground(String... params) {
        try {
            String ruta = backupdbVivanto();
            if(ruta != null){
                File forigsDB = new File(Environment.getExternalStorageDirectory(), "db/"+ruta);
                if(subirDB(forigsDB, usuarioLogin)==true){

                    //List<emc_hogares> lsHogares = emc_hogares.listAll(emc_hogares.class);

                    //List<emc_hogares> lsHogares = emc_hogares.find(emc_hogares.class, "USUUSUARIOCREACION = '" + usuarioLogin.getNombreusuario() + "' AND ESTADO <> 'TRANSMITIDA' AND ESTADO <> 'Incompleta' ", null);
                    List<emc_hogares> lsHogares = emc_hogares.find(emc_hogares.class, "USUUSUARIOCREACION = '" + usuarioLogin.getNombreusuario() + "' AND ESTADO <> 'TRANSMITIDA' AND ESTADO <> 'Incompleta' AND ESTADO <> 'Anulada' ", null);
                    //List<emc_hogares> lsHogares = emc_hogares.find(emc_hogares.class, "USUUSUARIOCREACION = '" + usuarioLogin.getNombreusuario() + "' AND ESTADO <> 'TRANSMITIDA' AND ESTADO <> 'Incompleta' AND ESTADO <> 'DUPLICADA' ", null);

                    if(lsHogares.size() > 0) {
                        //Crear archivo de salida
                        //File fos = new File(Environment.getExternalStorageDirectory(),"ENCUESTADORMOVIL/" + fileName);
                        //FileOutputStream fos = new FileOutputStream(new File(Environment.getExternalStorageDirectory(),"ENCUESTADORMOVIL/" + fileName));
                        //File ff = new File(Environment.getExternalStorageDirectory(),"ENCUESTADORMOVIL");
                        File ff = new File(Environment.getExternalStorageDirectory(),"ENCUESTADORMOVIL");
                        ff.mkdirs();
                        File forig = new File(Environment.getExternalStorageDirectory(),"ENCUESTADORMOVIL/" + fileName);
                        FileOutputStream fos = new FileOutputStream(forig);
                        //FileOutputStream fos = ctx.openFileOutput(fileName, Context.MODE_APPEND);
                        Writer out = new OutputStreamWriter(fos);

                        //Cargar datos
                        callback.actualizaEstado("Exportando registros");
                        Gson gson = new Gson();

                        for(Integer conHogar = 0; conHogar < lsHogares.size(); conHogar++){
                            emc_hogares tmHogar = lsHogares.get(conHogar);
                            //if(tmHogar.equals("Cerrada")){
                            String hogar = gson.toJson(tmHogar, emc_hogares.class);
                        /*tmHogar.setEstado("TRANSMITIDA");
                        tmHogar.save();*/
                            out.write(hogar);

                            List<emc_capitulos_terminados> lsCapsTer = emc_capitulos_terminados.find(emc_capitulos_terminados.class, "HOGCODIGO ='" + tmHogar.getHog_codigo() + "'", null);
                            for(Integer cont = 0; cont < lsCapsTer.size(); cont++){
                                String capter = gson.toJson(lsCapsTer.get(cont), emc_capitulos_terminados.class);
                                out.write(capter);
                            }
                            List<emc_encuestas_terminadas> lsEncsTer = emc_encuestas_terminadas.find(emc_encuestas_terminadas.class,"HOGCODIGO ='" + tmHogar.getHog_codigo() + "'",null);
                            for(Integer cont = 0; cont < lsEncsTer.size(); cont++){
                                String encter = gson.toJson(lsEncsTer.get(cont), emc_encuestas_terminadas.class);
                                out.write(encter);
                            }

                            List<emc_miembros_hogar> lsMiemHog = emc_miembros_hogar.find(emc_miembros_hogar.class,"HOGCODIGO ='" + tmHogar.getHog_codigo() + "'",null);
                            for(Integer cont = 0; cont < lsMiemHog.size(); cont++){
                                String encter = gson.toJson(lsMiemHog.get(cont), emc_miembros_hogar.class);
                                out.write(encter);
                            }

                            List<emc_respuestas_encuesta> lsRespEnc = emc_respuestas_encuesta.find(emc_respuestas_encuesta.class,"HOGCODIGO ='" + tmHogar.getHog_codigo() + "'",null);
                            for(Integer cont = 0; cont < lsRespEnc.size(); cont++){
                                String encter = gson.toJson(lsRespEnc.get(cont), emc_respuestas_encuesta.class);
                                out.write(encter);
                            }
                            //}
                        }
                        //callback.actualizaEstado("Guarda archivo");
                        out.close();
                        fos.close();

                        forig =  ModificarFichero(forig,"}{", "},{",forig);
                        //File x = new File(Environment.getExternalStorageDirectory(),"DSCIENCUESTAS/Enviadas/" + forig.getName());




                        if(uploadFile(forig) == true){

                            for(Integer conHogar = 0; conHogar < lsHogares.size(); conHogar++){
                                emc_hogares tmHogar = lsHogares.get(conHogar);
                                //if(tmHogar.equals("Cerrada")){
                                String hogar = gson.toJson(tmHogar, emc_hogares.class);
                                tmHogar.setEstado("TRANSMITIDA");
                                tmHogar.save();
                            }


                            for(Integer conSP = 1; conSP <=5; conSP ++ ) {
                                for (Integer conHogar = 0; conHogar < lsHogares.size(); conHogar++) {
                                    emc_hogares tmCHogar = lsHogares.get(conHogar);
                                    fileNameS = tmCHogar.getHog_codigo().toString();
                                    File forigs = new File(Environment.getExternalStorageDirectory(), "Soportes/" + fileNameS + ".png");
                                    if(forigs.exists()) {
                                        while(!uploadFileSoporte(forigs,usuarioLogin)) {
                                            uploadFileSoporte(forigs,usuarioLogin);
                                        }
                                    }
                                }

                            }



                        }else{
                            //callback.actualizaEstado("No se pudo exportar,intente de nuevo");
                            return false;
                        }



                    }else{
                        callback.actualizaEstado("No hay registros para exportar");
                        return false;
                    }

                }else{
                    return false;
                }

            }

        } catch (IOException e) {
            Toast.makeText(ctx, "Eradmiror al generar el archivo de salida", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected void onProgressUpdate(Integer... progress){
    }

    @Override
    protected void onPostExecute(Boolean result){
        callback.cargaFinalizada(result);
    }




    public boolean uploadFile(File fileName){
        FTPClient client = new FTPClient();
        try {

            /*client.connect(ftpServer,21);
            client.login(user, password);
            client.setType(FTPClient.TYPE_BINARY);
            client.changeDirectory("/EncuestasP/");

            client.upload(fileName, new MyTransferListener());

            client.logout();
            client.disconnect(false);*/
            if(subirArchivo(fileName)==true){
                return true;

            }else{
                return false;
            }





        } catch (Exception e) {
            e.printStackTrace();
            try {
                client.disconnect(true);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            return false;
        }

    }

    public static File ModificarFichero(File FficheroAntiguo, String Satigualinea, String Snuevalinea, File fileName) {
        /*Obtengo un numero aleatorio*/
        Random numaleatorio = new Random(3816L);
        //Random numaleatorio = new Random();
        /*Creo un nombre para el nuevo fichero apartir del
         *numero aleatorio*/
        String nombreArchivo = fileName.getName().toString().trim().replace("ENCUESTADORMOVILENCUESTAS","");
        String SnombFichNuev = FficheroAntiguo.getParent() +"/" +nombreArchivo.toString().trim().replace(".txt","") + String.valueOf(Math.abs(numaleatorio.nextInt())) + ".txt";
        /*Crea un objeto File para el fichero nuevo*/

        File FficheroNuevo = new File(SnombFichNuev);
        try {
            /*Si existe el fichero inical*/
            if (FficheroAntiguo.exists()) {
                /*Abro un flujo de lectura*/
                BufferedReader Flee = new BufferedReader(new FileReader(FficheroAntiguo));
                String Slinea;
                /*Recorro el fichero de texto linea a linea*/
                while ((Slinea = Flee.readLine()) != null) {
                    /*Si la lia obtenida es igual al la bucada
                     *para modificar*/
                    if (Slinea.toUpperCase().trim().equals(Satigualinea.toUpperCase().trim())) {
                        /*Escribo la nueva linea en vez de la que tenia*/
                        Slinea.replace("}{", "},{");
                        EcribirFichero(FficheroNuevo, Snuevalinea);
                        BorrarFichero(FficheroAntiguo);
                    } else {
                        /*Escribo la linea antigua*/
                        String replaceSlinea = Slinea.replace("}{", "},{");
                        EcribirFichero(FficheroNuevo, replaceSlinea);
                        BorrarFichero(FficheroAntiguo);
                    }
                }
                /*Cierro el flujo de lectura*/
                Flee.close();
            } else {
                System.out.println("Fichero No Existe");
            }
        } catch (Exception ex) {
            /*Captura un posible error y le imprime en pantalla*/
            System.out.println(ex.getMessage()+" error al tratar de ejecutar FficheroAntiguo.exists()");
        }


        return FficheroNuevo;
    }

    public static void EcribirFichero(File Ffichero, String SCadena) {
        try {
            //Si no Existe el fichero lo crea
            if (!Ffichero.exists()) {
                Ffichero.createNewFile();
            }
            /*Abre un Flujo de escritura,sobre el fichero con codificacion utf-8.
             *Además  en el pedazo de sentencia "FileOutputStream(Ffichero,true)",
             *true es por si existe el fichero seguir añadiendo texto y no borrar lo que tenia*/
            BufferedWriter Fescribe = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Ffichero, true), "utf-8"));
            /*Escribe en el fichero la cadena que recibe la función.
             *el string "\r\n" significa salto de linea*/
            Fescribe.write("{\"Encuestas\":[" + "\r\n");
            Fescribe.write(SCadena + "\r\n");
            Fescribe.write("]}");
            //Cierra el flujo de escritura
            Fescribe.close();

        } catch (Exception ex) {
            //Captura un posible error le imprime en pantalla
            System.out.println(ex.getMessage());
        }
    }

    public static void EcribirFicheroNuevaCarpeta(File Ffichero, String SCadena) {
        try {

            InputStream in = new FileInputStream(Ffichero);
            File ff = new File(Environment.getExternalStorageDirectory(),"ENCUESTADORMOVILENCUESTASENVIADAS");
            ff.mkdirs();

            File forig = new File(Environment.getExternalStorageDirectory(),"ENCUESTADORMOVILENCUESTASENVIADAS/" + Ffichero.getName());
            //FileOutputStream fos = new FileOutputStream(forig);
            OutputStream out = new FileOutputStream(forig);

            try {

                byte[] buf = new byte[1024];
                int len;

                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }

                out.flush();
                in.close();
                out.close();

            } catch (IOException ex) {
                //Logger.getLogger(ftp.class.getName()).log(Level.SEVERE, null, ex);

            }

            /*BufferedWriter Fescribe = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Ffichero, true), "utf-8"));
            Fescribe.close();*/

        } catch (Exception ex) {
            //Captura un posible error le imprime en pantalla
            System.out.println(ex.getMessage());
        }
    }

    public static void EcribirFicheroNuevaCarpetaSoporte(File Ffichero, String SCadena) {
        try {

            InputStream in = new FileInputStream(Ffichero);
            File ff = new File(Environment.getExternalStorageDirectory(),"SOPORTESENVIADOS");
            ff.mkdirs();
            File forig = new File(Environment.getExternalStorageDirectory(),"SOPORTESENVIADOS/" + Ffichero.getName());
            OutputStream out = new FileOutputStream(forig);

            try {

                byte[] buf = new byte[1024];
                int len;

                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }

                out.flush();
                in.close();
                out.close();

            } catch (IOException ex) {
                //Logger.getLogger(ftp.class.getName()).log(Level.SEVERE, null, ex);

            }

        } catch (Exception ex) {
            //Captura un posible error le imprime en pantalla
            System.out.println(ex.getMessage());
        }
    }



    public static void BorrarFichero(File Ffichero) {
        try {
            /*Si existe el fichero*/
            if (Ffichero.exists()) {
                /*Borra el fichero*/
                Ffichero.delete();
                System.out.println("Fichero Borrado con Exito");
            }
        } catch (Exception ex) {
            /*Captura un posible error y le imprime en pantalla*/
            System.out.println(ex.getMessage());
        }
    }

    public static boolean subirArchivo(File filename) throws SocketException, UnknownHostException, IOException {

        try {

            org.apache.commons.net.ftp.FTPClient ftpClient = new org.apache.commons.net.ftp.FTPClient();
            ftpClient.connect(InetAddress.getByName("ftp.unidadvictimas.gov.co"));
            ftpClient.login("encuestadormovil", "Kart3g3na");

            ftpClient.changeWorkingDirectory("/Encuestas/");
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            //File rutaSd = Environment.getExternalStorageDirectory();
            BufferedInputStream buffIn=null;
            //File rutaCompleta = new File(rutaSd.getAbsolutePath(), "ENCUESTADORMOVIL/"+filename);
            //File rutaCompleta = new File(rutaSd.getAbsolutePath(), "DSCIENCUESTAS/prueba.txt");
            buffIn=new BufferedInputStream(new FileInputStream(filename/*rutaCompleta*/));
            ftpClient.enterLocalPassiveMode();
            if(ftpClient.storeFile(filename.getName().replace("ENCUESTADORMOVILENCUESTAS",""), buffIn)){
                buffIn.close();
                ftpClient.logout();
                ftpClient.disconnect();
                EcribirFicheroNuevaCarpeta(filename,"},{");
                BorrarFichero(filename);

                return true;


            }else{
                buffIn.close();
                ftpClient.logout();
                ftpClient.disconnect();
                return false;
            }



        } catch (Exception e){
            Log.i("consola","Ups...");
            return false;
        }

    }

    public static boolean subirSoportes(File filename, emc_usuarios usuarioLogin) throws SocketException, UnknownHostException, IOException {

        try {

            org.apache.commons.net.ftp.FTPClient ftpClient = new org.apache.commons.net.ftp.FTPClient();
            BufferedInputStream buffIn=null;

            if(usuarioLogin.getIdPerfil().equals("710")){


                ftpClient.connect(InetAddress.getByName("ftp.isegoria.co"));
                ftpClient.login("victimas@isegoria.co", "isegoFTPvictimas2019");


                ftpClient.changeWorkingDirectory("/SoportesEncuestas/");
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);


                buffIn=new BufferedInputStream(new FileInputStream(filename/*rutaCompleta*/));
                ftpClient.enterLocalPassiveMode();
                if(ftpClient.storeFile(filename.getName().replace("EcribirFicheroNuevaCarpetaSoporte",""), buffIn)){
                    buffIn.close();
                    ftpClient.logout();
                    ftpClient.disconnect();
                    //EcribirFicheroNuevaCarpetaSoporte(filename,"},{");
                    //BorrarFichero(filename);

                    //return true;

                }

                ftpClient.connect(InetAddress.getByName("ftp.unidadvictimas.gov.co"));
                ftpClient.login("encuestadormovil", "Kart3g3na");

                ftpClient.changeWorkingDirectory("/Soportes/");
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);


                buffIn=new BufferedInputStream(new FileInputStream(filename/*rutaCompleta*/));
                ftpClient.enterLocalPassiveMode();
                if(ftpClient.storeFile(filename.getName().replace("EcribirFicheroNuevaCarpetaSoporte",""), buffIn)){
                    buffIn.close();
                    ftpClient.logout();
                    ftpClient.disconnect();
                    EcribirFicheroNuevaCarpetaSoporte(filename,"},{");
                    BorrarFichero(filename);

                    return true;


                }else{
                    buffIn.close();
                    ftpClient.logout();
                    ftpClient.disconnect();
                    return false;
                }



            }else{


                ftpClient.connect(InetAddress.getByName("ftp.unidadvictimas.gov.co"));
                ftpClient.login("encuestadormovil", "Kart3g3na");

                ftpClient.changeWorkingDirectory("/Soportes/");
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);


                buffIn=new BufferedInputStream(new FileInputStream(filename/*rutaCompleta*/));
                ftpClient.enterLocalPassiveMode();
                if(ftpClient.storeFile(filename.getName().replace("EcribirFicheroNuevaCarpetaSoporte",""), buffIn)){
                    buffIn.close();
                    ftpClient.logout();
                    ftpClient.disconnect();
                    EcribirFicheroNuevaCarpetaSoporte(filename,"},{");
                    BorrarFichero(filename);

                    return true;


                }else{
                    buffIn.close();
                    ftpClient.logout();
                    ftpClient.disconnect();
                    return false;
                }

            }

        } catch (Exception e){
            Log.i("consola","Ups...");
            return false;
        }

    }

    public static boolean subirDB(File filename, emc_usuarios usuarioLogin) throws SocketException, UnknownHostException, IOException {

        try {

            if(usuarioLogin.getIdPerfil().equals("710")){

                org.apache.commons.net.ftp.FTPClient ftpClient = new org.apache.commons.net.ftp.FTPClient();
                ftpClient.connect(InetAddress.getByName("ftp.isegoria.co"));
                ftpClient.login("victimas@isegoria.co", "isegoFTPvictimas2019");

                ftpClient.changeWorkingDirectory("/DBEncuestasAcnur/");
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                BufferedInputStream buffIn=null;

                buffIn=new BufferedInputStream(new FileInputStream(filename/*rutaCompleta*/));
                ftpClient.enterLocalPassiveMode();
                if(ftpClient.storeFile(filename.getName().replace("EcribirFicheroNuevaCarpetaSoporte",""), buffIn)){
                    buffIn.close();
                    ftpClient.logout();
                    ftpClient.disconnect();
                    //EcribirFicheroNuevaCarpetaSoporte(filename,"},{");
                    //BorrarFichero(filename);

                    return true;


                }else{
                    buffIn.close();
                    ftpClient.logout();
                    ftpClient.disconnect();
                    return false;
                }

            }else{

                org.apache.commons.net.ftp.FTPClient ftpClient = new org.apache.commons.net.ftp.FTPClient();
                ftpClient.connect(InetAddress.getByName("ftp.unidadvictimas.gov.co"));
                ftpClient.login("encuestadormovil", "Kart3g3na");

                ftpClient.changeWorkingDirectory("/EncuestasP/");
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                BufferedInputStream buffIn=null;

                buffIn=new BufferedInputStream(new FileInputStream(filename/*rutaCompleta*/));
                ftpClient.enterLocalPassiveMode();
                if(ftpClient.storeFile(filename.getName().replace("EcribirFicheroNuevaCarpetaSoporte",""), buffIn)){
                    buffIn.close();
                    ftpClient.logout();
                    ftpClient.disconnect();
                    //EcribirFicheroNuevaCarpetaSoporte(filename,"},{");
                    //BorrarFichero(filename);

                    return true;


                }else{
                    buffIn.close();
                    ftpClient.logout();
                    ftpClient.disconnect();
                    return false;
                }

            }





        } catch (Exception e){
            Log.i("consola","Ups...");
            return false;
        }

    }





    public boolean uploadFileSoporte(File fileName, emc_usuarios usuarioLogin ){
        FTPClient client = new FTPClient();
        try {

            if(subirSoportes(fileName,usuarioLogin)==true){
                return true;

            }else{
                return false;
            }


        } catch (Exception e) {
            e.printStackTrace();
            try {
                client.disconnect(true);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            return false;
        }

    }

    public boolean uploadFileBackupDb(File fileName){
        FTPClient client = new FTPClient();
        try {

            if(subirSoportes(fileName,usuarioLogin)==true){
                return true;

            }else{
                return false;
            }


        } catch (Exception e) {
            e.printStackTrace();
            try {
                client.disconnect(true);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            return false;
        }

    }

    private String backupdbVivanto()throws IOException {
        try{

            String packageName = ctx.getApplicationContext().getPackageName();
            final String inFileName = "/data/data/"+packageName+"/databases/vivanto.db";
            File dbFile = new File(inFileName);
            FileInputStream fis = new FileInputStream(dbFile);


            String fch = fechaActual().replace("/", "");
            fch = fch.replace(" ","");
            fch = fch.replace(":","");

            String nombreArchivo = "vivanto_"+usuarioLogin.getIdusuario()+"_"+fch+".db";
            String outFileName = Environment.getExternalStorageDirectory()+"/db/"+nombreArchivo;

            // Open the empty db as the output stream
            OutputStream output = new FileOutputStream(outFileName);

            // Transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer))>0){
                output.write(buffer, 0, length);
            }

            // Close the streams
            output.flush();
            output.close();
            fis.close();

            return nombreArchivo;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }



    }

    /*******  Used to file upload and show progress  **********/

    public class MyTransferListener implements FTPDataTransferListener {

        public void started() {
            // Transfer started
            System.out.println(" Upload Started ...");
        }

        public void transferred(int length) {
            // Yet other length bytes has been transferred since the last time this
            // method was called
            System.out.println(" transferred ..." + length);
        }

        public void completed() {
            // Transfer completed
            System.out.println(" completed ..." );
        }

        public void aborted() {
            // Transfer aborted
            System.out.println(" aborted ...");
        }

        public void failed() {
            // Transfer failed
            System.out.println(" failed ..." );
        }
    }
}

