package co.com.rni.encuestadormovil.util;

import android.content.Context;
import android.os.*;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import co.com.rni.encuestadormovil.R;
import co.com.rni.encuestadormovil.model.emc_usuarios;

import static co.com.rni.encuestadormovil.util.general.fechaActual;

/**
 * Created by ASUS on 11/04/2018.
 */

public class ansyncBackupDBVivanto extends AsyncTask<String, Integer, Boolean> {

    private Context ctx;
    private responseBackupBDVivanto callback;
    private javax.mail.Session session;


    public ansyncBackupDBVivanto (Context ctx,responseBackupBDVivanto callback){
        this.ctx = ctx;
        this.callback = callback;


    }



    @Override
    protected Boolean doInBackground(String... params) {
        try {
            if(backupdbVivanto()){
                /*
                if(senMail()){
                    return true;
                }else{
                    return false;
                }
                */
                return true;
            }else{
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean backupdbVivanto()throws IOException {
        try{

            String packageName = ctx.getApplicationContext().getPackageName();
            final String inFileName = "/data/data/"+packageName+"/databases/vivanto.db";
            File dbFile = new File(inFileName);
            FileInputStream fis = new FileInputStream(dbFile);


            String fch = fechaActual().replace("/", "");
            fch = fch.replace(" ","");
            fch = fch.replace(":","");

            String outFileName = Environment.getExternalStorageDirectory()+"/vivanto_copy.db";

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

            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }



    }



    public boolean senMail(){

        //Creating properties
        Properties props = new Properties();

        //Configuring properties for gmail
        //If you are not using gmail you may need to change the values
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");






        try {


            //Creating a new session
            session = javax.mail.Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        //Authenticating the password
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("afquinteroq@gmail.com", "Af80857397*+");
                        }
                    });

            //javax.mail.Session session = javax.mail.Session.getInstance(props, null);
            session.setDebug(true);

            // Se compone la parte del texto
            BodyPart texto = new MimeBodyPart();
            texto.setText("Texto del mensaje");

            // Se compone el adjunto con la imagen
            MimeBodyPart adjunto = new MimeBodyPart();
            //Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/vivanto_copy.db" ));
            //adjunto.setDataHandler(new DataHandler(new FileDataSource(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/vivanto_copy.db"))));
            adjunto.setDataHandler(new DataHandler(new FileDataSource(new File(Environment.getExternalStorageDirectory().getAbsolutePath() +"/DSCIENCUESTASENVIADAS" +"/prueba_22_06_2018_14_01_23949259166.txt"))));

            // Una MultiParte para agrupar texto e imagen.
            MimeMultipart multiParte = new MimeMultipart();
            multiParte.addBodyPart(texto);
            //multiParte.addBodyPart(adjunto);

            // Se compone el correo, dando to, from, subject y el
            // contenido.
            MimeMessage message = new MimeMessage(session);
            //message.setHeader("Content-Type", "text/plain; charset=\"us-ascii\"; name=\"mail.txt\"");
            message.setFrom(new InternetAddress("afquinteroq@gmail.com"));
            message.addRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress("afquinteroq@hotmail.com"));
            message.setSubject("Prueba");
            message.setContent(multiParte);

            // Se envia el correo.
            Transport t = session.getTransport("smtp");
            t.connect("afquinteroq@gmail.com", "Af80857397*+");
            Thread.currentThread().setContextClassLoader( getClass().getClassLoader() );
            t.sendMessage(message, message.getAllRecipients());
            t.close();


            return true;

        } catch (MessagingException e) {

                       e.printStackTrace();
            e.getMessage();
            return false;

        }
    }

    public boolean enviarMail(){
        final String correo = "afquinteroq@gmail.com";
        final String contrasena = "Af80857397*+";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Properties properties = new Properties();
        properties.put("mail.smtp.host","smtp.googlemail.com");
        properties.put("mail.smtp.socketFactory.port","465");
        properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.port","465");

        try {



            //Creating a new session
            session = javax.mail.Session.getInstance(properties,
                    new javax.mail.Authenticator() {
                        //Authenticating the password
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("afquinteroq@gmail.com", "Af80857397*+");
                        }
                    });

            if(session!=null){
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(correo));
                message.setSubject("asunto");
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("afquinteroq@hotmail.com"));
                message.setContent("hola","text/html; charset=utf-8");

                Transport.send(message);
            }



        }catch (Exception e){
                e.printStackTrace();
        }

        return true;
    }

    @Override
    protected void onPostExecute(Boolean result){
        callback.resultado(result);

    }
}
