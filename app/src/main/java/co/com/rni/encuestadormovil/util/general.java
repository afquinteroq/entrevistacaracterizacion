package co.com.rni.encuestadormovil.util;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.com.rni.encuestadormovil.model.emc_hogares;

/**
 * Created by javierperez on 16/12/15.
 */
public final class general  {
    public static boolean isNumeric(String cadena){
        try{
            Integer.parseInt(cadena);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }

    public static boolean fechaValida(String text) {

        String regexp = "\\d{1,2}/\\d{1,2}/\\d{4}";

        if(text.length()==10){

            if(Pattern.matches(regexp,text)){
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                //SimpleDateFormat df = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");

                df.setLenient(false);
                try {
                    df.parse(text);
                    return true;
                } catch (ParseException ex) {
                    return false;
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
        }

    public static boolean isValidEmail(String email)
    {
        boolean isValidEmail = false;

        String emailExpression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(emailExpression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches())
        {
            isValidEmail = true;
        }
        return isValidEmail;
    }

    public static boolean isValidPhone(String phone)
    {
        if (phone == null || !phone.replace("—","").matches("\\d{9}"))
            return false;
        return true;
    }

    public static boolean isValidMovil(String phone)
    {
        if (phone == null || !phone.replace("—","").matches("\\d{10}"))
            return false;
        return true;
    }

    public static String fechaActual(){
        /*String Datetime;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
        Datetime = dateformat.format(c.getTime());
        return Datetime;*/

        String Datetime;
        long timeInMillis = System.currentTimeMillis();
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(timeInMillis);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        //SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Datetime = dateFormat.format(cal1.getTime());
        return Datetime;

    }


    public static String fechaActualSinHora(){
        /*String Datetime;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
        Datetime = dateformat.format(c.getTime());
        return Datetime;*/

        String Datetime;
        long timeInMillis = System.currentTimeMillis();
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(timeInMillis);
        //SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Datetime = dateFormat.format(cal1.getTime());
        return Datetime;

    }

    public static int CalcularEdad(int _year, int _month, int _day) {

        try{

            GregorianCalendar cal = new GregorianCalendar();
            int y, m, d, a;

            y = cal.get(Calendar.YEAR);
            m = cal.get(Calendar.MONTH);
            d = cal.get(Calendar.DAY_OF_MONTH);
            cal.set(_year, _month, _day);
            a = y - cal.get(Calendar.YEAR);
            if ((m < cal.get(Calendar.MONTH))
                    || ((m == cal.get(Calendar.MONTH)) && (d < cal
                    .get(Calendar.DAY_OF_MONTH)))) {
                --a;
            }
            if(a < 0){
                //throw new IllegalArgumentException("Age < 0");
                return 0;
            }else{
                return a;
            }



        }catch (Exception e){
            return 0;
        }


    }

    public static int CalcularEdad(String fecNacimiento){
        int edad = 0;
        String dianacimiento = "";
        String mesnacimiento = "";
        String anionacimiento = null;
        if (fecNacimiento.equals("--") || fecNacimiento.equals("-")) {
            dianacimiento = "01";
            mesnacimiento = "01";
            anionacimiento = "1970";


        } else {
            dianacimiento = fecNacimiento.substring(0, 2);
            mesnacimiento = fecNacimiento.substring(3, 5);

        }

        if (fecNacimiento.length() == 9) {
            String mesVnacimiento = fecNacimiento.substring(3, 6);

            switch (mesVnacimiento) {
                case "JAN":
                    mesnacimiento = "01";
                    break;
                case "FEB":
                    mesnacimiento = "02";
                    break;
                case "MAR":
                    mesnacimiento = "03";
                    break;
                case "APR":
                    mesnacimiento = "04";
                    break;
                case "MAY":
                    mesnacimiento = "05";
                    break;
                case "JUN":
                    mesnacimiento = "06";
                    break;
                case "JUL":
                    mesnacimiento = "07";
                    break;
                case "AUG":
                    mesnacimiento = "08";
                    break;
                case "SEP":
                    mesnacimiento = "09";
                    break;
                case "OCT":
                    mesnacimiento = "10";
                    break;
                case "NOV":
                    mesnacimiento = "11";
                    break;
                case "DEC":
                    mesnacimiento = "12";
                    break;
                default:
                    mesnacimiento = "0";
            }

            anionacimiento = fecNacimiento.substring(7, 9);
            if (Integer.valueOf(anionacimiento) > 16) {
                anionacimiento = "19" + anionacimiento;
            } else {
                anionacimiento = "20" + anionacimiento;
            }

        } else if (fecNacimiento.length() == 8) {
            mesnacimiento = fecNacimiento.substring(3, 5);
            anionacimiento = fecNacimiento.substring(6, 8);
            if (Integer.valueOf(anionacimiento) > 16) {
                anionacimiento = "19" + anionacimiento;
            } else {
                anionacimiento = "20" + anionacimiento;
            }
        } else if (fecNacimiento.length() == 10) {
            anionacimiento = fecNacimiento.substring(6, 10);

        }
        if(anionacimiento != null){
            Integer edadnacimiento = general.CalcularEdad(Integer.valueOf(anionacimiento), Integer.valueOf(mesnacimiento), Integer.valueOf(dianacimiento));
            edad = edadnacimiento;
        }

        return edad;
    }

    public static String remoAcents(String input) {
        // Cadena de caracteres original a sustituir.
        String original = "áàäéèëíìïóòöúùuÁÀÄÉÈËÍÌÏÓÒÖÚÙÜçÇ";
        // Cadena de caracteres ASCII que reemplazarán los originales.
        String ascii = "aaaeeeiiiooouuuAAAEEEIIIOOOUUUcC";
        String output = input;
        for (int i = 0; i < original.length(); i++) {
            // Reemplazamos los caracteres especiales.
            output = output.replace(original.charAt(i), ascii.charAt(i));
        }//for i
        return output;
    }//remove1

    public static boolean borrarEncuestasRepetidas()
    {
        try {

            emc_hogares.deleteAll(emc_hogares.class,
                    "id in (select id from emchogares h where h.hogcodigo in (\n" +
                            "select h.hogcodigo  from emchogares h where  h.hogcodigo in (\n" +
                            "select t.hogcodigo from emchogares t group by t.hogcodigo HAVING COUNT(1) > 1 )\n" +
                            "and estado in ('TRANSMITIDA','Cerrada','Aplazada')\n" +
                            ") and estado NOT IN  ('TRANSMITIDA','Cerrada','Aplazada'));", null);

            emc_hogares.deleteAll(emc_hogares.class,
                    "id in (select id from (select min(id) id, min(usufechacreacion), hogcodigo from emchogares where hogcodigo  in ( \n" +
                            "select hogcodigo from emchogares h where h.hogcodigo in (\n" +
                            "select t.hogcodigo from emchogares t group by t.hogcodigo having count(1) > 1\n" +
                            ") \n" +
                            "group by estado,hogcodigo\n" +
                            "having count(1)>1\n" +
                            ") group by hogcodigo));", null);
            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }


    public static boolean borrarEncuestasDuplicadasConDiferenteEstado()
    {
        try {

            emc_hogares.deleteAll(emc_hogares.class,
                    "ID IN(SELECT ID FROM EMCHOGARES HOG WHERE HOG.HOGCODIGO IN (\n" +
                            "SELECT DISTINCT HOGCODIGO FROM EMCHOGARES HO WHERE HO.HOGCODIGO = (\n" +
                            "SELECT HOGCODIGO FROM (\n" +
                            "select HOGCODIGO, COUNT(HOGCODIGO) TOTAL from EMCHOGARES  GROUP BY HOGCODIGO)\n" +
                            "WHERE TOTAL > 1\n" +
                            ") \n" +
                            ") AND HOG.USUFECHACREACION = (\n" +
                            "SELECT MIN(USUFECHACREACION) FROM EMCHOGARES HO WHERE HO.HOGCODIGO IN (\n" +
                            "SELECT HOGCODIGO FROM (\n" +
                            "select HOGCODIGO, COUNT(HOGCODIGO) TOTAL from EMCHOGARES  GROUP BY HOGCODIGO)\n" +
                            "WHERE TOTAL > 1\n" +
                            ") \n" +
                            "))", null);


            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    public static int isNetworkConnected(Context context) {

        int val = 0;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context
                .CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info == null || !info.isConnected() || !info.isAvailable()) {
            val = 0;
        }else if(info.getType()==ConnectivityManager.TYPE_WIFI){
            val = 1;
        }else if (info.getType()==ConnectivityManager.TYPE_MOBILE){
            val = 2;
        }
        return val;
    }

    public static boolean isOnlineNet() {

        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.es");

            int val           = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public static Bitmap redimensionarImagenMaximo(Bitmap mBitmap, float newWidth, float newHeigth){
        //Redimensionamos
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeigth) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        return Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, false);
    }


}

