package co.com.rni.encuestadormovil.util;

import android.content.Context;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import co.com.rni.encuestadormovil.model.*;

/**
 * Created by javierperez on 12/12/15.
 */
public final class parametros {

    public static void cargarBD(Context context_, responseParametros callback){
        InputStream is = null;
        try {
            is = context_.getAssets().open("vivanto.db");
            // Log.v("Tag assets",is.toString());
            String outFileName = context_.getDatabasePath("vivanto.db").getPath();

            File databaseFile = new File(context_.getDatabasePath("vivanto.db").getAbsolutePath());
            // check if databases folder exists, if not create one and its subfolders
            if (!databaseFile.exists()){
                databaseFile.mkdir();
            }

            OutputStream out = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                // Log.v("Tag",out.toString());
                out.write(buffer, 0, length);
                // Log.v("Tag",out.toString());
            }
            // Log.v("Tag","Database created");
            is.close();
            out.flush();
            out.close();
            callback.cargaFinalizada(true);
        } catch (IOException e) {
            e.printStackTrace();
            callback.cargaFinalizada(false);
        }
    }

    public static void CargaInicial(Context context_/*, responseParametros callback*/)  {
        String fecha = "2015-11-11";
        String estado = "A";
        String usr = "admin";
        String[] pars = null;

        List<emc_temas> test = emc_temas.find(emc_temas.class, null, pars);
        if( test.size()== 0 ){
            //callback.actualizaParametros("Carga parametros encuesta", 0);
            ArrayList<String> listaTemas = new ArrayList<>();
            listaTemas.add("IDENTIFICACION");//1

            for(int cont = 0; cont < listaTemas.size(); cont++){
                emc_temas  temaTemp = new emc_temas(cont+1, listaTemas.get(cont), estado, usr, fecha, cont+1);
                temaTemp.save();
            }

            /*cargarEMC_ADMONCOMBOS();
            cargarEMC_Version();
            cargarEMC_TEMA_PERFILS();
            cargarEMC_PREGUNTAS_TEMAS(fecha);
            cargarEMC_PREGUNTAS_INSTRUMENTO();
            cargaEMC_VALIDADORES_INSTRUMENTO();
            cargaEMC_VALIDADORES_PERSONA();
            cargaEMC_INSTRUMENTO_VALIDADOR();
            cargaEMC_RESPUESTAS();*/
            //cargarEMC_RESPUESTAS_INSTRUMENTO();
            /*cargaEMC_VALIDADORES_RESPUESTA();
            cargarEMC_DEPARTAMENTO();
            cargarEMC_MUNICIPIO();
            cargarEMC_VEREDAS(context_);
            cargarEMC_RESGUARDOS();
            cargarEMC_ENFERMEDADES();
            cargarEMC_COMUNIDADESNEGRAS();
            cargarEMC_ADMONCOMBOS();
            cargarEMC_VALIDADORES_RESPUESTA_INSTRUMENTO();
            cargaEMC_PREGUNTA_HIJOS();
            cargarEMC_VALIDADOR_EXPRESION();
            */

            //callback.actualizaParametros("",-100);

            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = df.format(c.getTime());
            c.add(Calendar.DATE, 30);
            String formattedDate2 = df.format(c.getTime());

            emc_usuarios administrador = new emc_usuarios("0", "admin", "Admin759*", "admin", formattedDate, formattedDate2,"499","SUPERVISORMOVIL");
            administrador.save();
            emc_usuarios prueba = new emc_usuarios("99999999", "prueba", "Prueba759*", "prueba", formattedDate, formattedDate2,"658","NRC");
            prueba.save();
            emc_usuarios andagueda = new emc_usuarios("999999999", "andagueda", "Andagueda759*", "andgagueda", formattedDate, formattedDate2,"498","ANDAGUEDAMOVIL");
            andagueda.save();
            emc_usuarios sanandres = new emc_usuarios("9999999999", "sanandres", "Sanandres759*", "sanandres", formattedDate, formattedDate2,"638","MODULO_SAN_ANDRES_MOVIL");
            sanandres.save();
            emc_usuarios acnur = new emc_usuarios("7777777777", "acnur", "Acnur759*", "acnur", formattedDate, formattedDate2,"710","MODULO_ACNUR");
            acnur.save();
            emc_usuarios saah = new emc_usuarios("999999999999", "saah", "Saah", "saah", formattedDate, formattedDate2,"1190","SAAH");
            saah.save();
            emc_usuarios SaahS = new emc_usuarios("99999999999", "saahs", "SaahS", "saahsupervisor", formattedDate, formattedDate2,"1230","SAAHSUPERVISOR");
            SaahS.save();
}
    }

    private static void cargarEMC_VEREDAS(Context context_){
        //cargarVeredas0();
        InputStream isCarga;
        try {
            isCarga = context_.getAssets().open("veredas/veredas.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(isCarga));

            String line;
            while ((line = reader.readLine()) != null) {
                //line = line.replace("\"", "");
                //line = line.replace("/", "-");
                String[] row = line.split(";");

                emc_veredas tempVeredas = new emc_veredas(row[0].trim(), row[1].trim(), row[2].trim(), row[3].trim(),row[4].trim(),row[5].trim());
                tempVeredas.save();

            }
        }catch (IOException e){
            Log.e("Main", e.getMessage());
        }


        }


    private static void cargarEMC_PREGUNTAS_TEMAS(String fecha){
        emc_preguntas preguntaTem;
        preguntaTem = new emc_preguntas("1", "Lugar de la Encuesta", "", "SI", "SUPER", fecha);	preguntaTem.save();
        //cargarEMC_PREGUNTAS_TEMAS_NRC(fecha);
        //cargarEMC_PREGUNTAS_TEMAS_20062018(fecha);


    }

    private static void cargarEMC_PREGUNTAS_TEMAS_20062018(String fecha){
        emc_preguntas preguntaTem;
        preguntaTem = new emc_preguntas("909","¿Conoce el protocolo de retornos y reubicaciones?","","SI","AFQUINTEROQ",fecha);	preguntaTem.save();

    }

    private static void cargaEMC_RESPUESTAS(){
        emc_respuestas temRespuesta;
        temRespuesta = new emc_respuestas("1","1","","SI","SUPER","22/03/2016"); temRespuesta.save();
        //cargaEMC_RESPUESTAS_NRC();
        //cargaEMC_RESPUESTAS_20062018();
    }


    private static void cargaEMC_VALIDADORES_INSTRUMENTO(){
        emc_validadores_instrumento temValInstrumento;
        temValInstrumento = new emc_validadores_instrumento("90","1","10","225","225");	temValInstrumento.save();
    }

    private static void cargaEMC_VALIDADORES_PERSONA(){
        emc_validadores_persona temValPersona;

    }

    private static void cargaEMC_INSTRUMENTO_VALIDADOR(){
        emc_instrumento_validador  temInstrumentoValidador;
        temInstrumentoValidador = new emc_instrumento_validador("1","1","IN","ESTADO","8","INCLUIDO","0"); temInstrumentoValidador.save();

    }

    private static void cargarEMC_PREGUNTAS_INSTRUMENTO(){
        emc_preguntas_instrumento temPregIns;
        temPregIns = new emc_preguntas_instrumento("1","1","1","1","SI","GE","DP",null,"SUPER","01/01/14",null,null,null,null,null,null,null,null); temPregIns.save();

    }

    public static void cargaEMC_PREGUNTA_HIJOS(){
        emc_pregunta_hijos tmPregHijos;

         cargaEMC_PREGUNTA_HIJOS_NRC();

        }

    public static void cargaEMC_VALIDADORES_RESPUESTA(){
        emc_validadores_respuesta tmValRes;
        tmValRes = new emc_validadores_respuesta("100","113","INSTR('CADENA','113',1)>0"); tmValRes.save();

    }

    public static void cargarEMC_DEPARTAMENTO(){
        emc_departamento tmDepto;
        tmDepto = new emc_departamento("1","Seleccione departamento"); tmDepto.save();
    }

    public static void cargarEMC_MUNICIPIO(){
        emc_municipio tmMun;
        tmMun  = new emc_municipio("001100","1","001100","Seleccione municipio","0","0","0","0","0","0","CALIDO","No","","0"); tmMun.save();
    }

    public static void cargarEMC_RESGUARDOS(){
        emc_resguardosindigenas tmResguardos;
        tmResguardos = new emc_resguardosindigenas("897","Sin Información"); tmResguardos.save();
    }

    public static void cargarEMC_ENFERMEDADES(){
        emc_ruinosas_catastroficas tmEnfermedades;
        tmEnfermedades = new emc_ruinosas_catastroficas("115","Cáncer de cérvix"); tmEnfermedades.save();
    }

    public static void cargarEMC_COMUNIDADESNEGRAS(){
        emc_comunidadesnegras tmComunidades;
        tmComunidades = new emc_comunidadesnegras("1089","Cc De Istmina Y Parte Del Medio San Juan"); tmComunidades.save();

    }


    public static void cargarEMC_ADMONCOMBOS(){
        emc_admoncombos tmAD;
        tmAD = new emc_admoncombos("1", "select EST_IDESTADO as  id, est_nombreestado as descripcion from gic_estado");
        tmAD.save();
    }

    public static void cargarEMC_VALIDADORES_RESPUESTA_INSTRUMENTO(){
        emc_validadores_respuesta_instrumento tmVEI;

        tmVEI = new emc_validadores_respuesta_instrumento(79,1,2,5,13); tmVEI.save();

    }

    public static void cargarEMC_VALIDADOR_EXPRESION()
    {
        emc_validador_expresion tmVEX;

        tmVEX = new emc_validador_expresion("163","0","5"); tmVEX.save();

    }


    public static void cargarEMC_TEMA_PERFILS()
    {

        emc_tema_perfiles tmTMP;
        tmTMP = new emc_tema_perfiles("1","500"); tmTMP.save();


    }

    public static void cargarEMC_Version()
    {
        emc_version tmTMPV;
        tmTMPV =  new emc_version(1,"BASE", "8"); tmTMPV.save();
    }

    public static void respuestas_instrumento_NRC(){

        emc_respuestas_instrumento temResIns;
        //capitulo 24 NRC-A DATOS DEMOGRAFICOS

    }

    public static void cargarEMC_PREGUNTAS_TEMAS_NRC(String fecha){
        emc_preguntas preguntaTem;

        //CAPITULO 24 NRC DATOS DEMOGRAFICOS
        preguntaTem = new emc_preguntas("456", "¿Usted se considera víctima del conflicto armado colombiano?", "", "SI", "ANDRESQ", fecha); preguntaTem.save();

    }

    public static void cargaEMC_RESPUESTAS_NRC(){
        emc_respuestas temRespuesta;
        temRespuesta = new emc_respuestas("1515","456","Si","SI","ANDRESQ","10/08/2017"); temRespuesta.save();
    }


    public static void cargaEMC_RESPUESTAS_20062018(){
        emc_respuestas temRespuesta;
        temRespuesta = new emc_respuestas("2342","790","","SI","AFQUINTEROQ","28/05/2018");	temRespuesta.save();
    }

    public static void cargaEMC_PREGUNTA_HIJOS_NRC(){
        emc_pregunta_hijos tmPregHijos;
        tmPregHijos = new emc_pregunta_hijos("1769","536",null,"0");  tmPregHijos.save();

    }

}
