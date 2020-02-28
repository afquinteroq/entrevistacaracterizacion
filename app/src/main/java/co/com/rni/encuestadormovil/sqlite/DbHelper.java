package co.com.rni.encuestadormovil.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;


import java.util.ArrayList;
import java.util.List;

import co.com.rni.encuestadormovil.model.*;

/**
 * Created by JPerezP on 27/05/2016.
 */
public class DbHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "dbencuestadormovil.db";
    private static final int DATABASE_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    public Integer getConteoVictimas(){
        Integer resultado = 0;

        String selectQuery = "SELECT COUNT(documento) FROM personas0";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            if (cursor.moveToFirst()) {
                resultado = cursor.getInt(0);
            }
        }finally {
            cursor.close();
            db.close();
        }
        return resultado;
    }


    public List<emc_victimas_nosugar> getlistaVictimasDocumento(String documento){
        List<emc_victimas_nosugar> lsResultado = new ArrayList<>();
        String selectQuery = null;
        char  ind;

        if(documento.equals(""))
        {
            ind = '0';
        }else
        {
            ind = documento.charAt(0);
        }

        switch (ind)
        {
            case '0':
                selectQuery = "SELECT * FROM personas0 WHERE documento = '" + documento + "';";
                break;
            case '1':
                selectQuery = "SELECT * FROM personas1 WHERE documento = '" + documento + "';";
                break;
            case '2':
                selectQuery = "SELECT * FROM personas2 WHERE documento = '" + documento + "';";
                break;
            case '3':
                selectQuery = "SELECT * FROM personas3 WHERE documento = '" + documento + "';";
                break;
            case '4':
                selectQuery = "SELECT * FROM personas4 WHERE documento = '" + documento + "';";
                break;
            case '5':
                selectQuery = "SELECT * FROM personas5 WHERE documento = '" + documento + "';";
                break;
            case '6':
                selectQuery = "SELECT * FROM personas6 WHERE documento = '" + documento + "';";
                break;
            case '7':
                selectQuery = "SELECT * FROM personas7 WHERE documento = '" + documento + "';";
                break;
            case '8':
                selectQuery = "SELECT * FROM personas8 WHERE documento = '" + documento + "';";
                break;
            case '9':
                selectQuery = "SELECT * FROM personas9 WHERE documento = '" + documento + "';";
                break;
            default:
                selectQuery = "SELECT * FROM personasa WHERE documento = '" + documento + "';";
                break;
        }


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    emc_victimas_nosugar tmVictimas = new emc_victimas_nosugar();
                    tmVictimas.setConsPersona(cursor.getInt(0));
                    tmVictimas.setHogar(cursor.getInt(1));
                    tmVictimas.setTipoDoc(cursor.getString(2));
                    tmVictimas.setDocumento(cursor.getString(3));
                    tmVictimas.setNombre1(cursor.getString(4));
                    tmVictimas.setNombre2(cursor.getString(5));
                    tmVictimas.setApellido1(cursor.getString(6));
                    tmVictimas.setApellido2(cursor.getString(7));
                    tmVictimas.setFecNacimiento(cursor.getString(8));
                    tmVictimas.setHv1(cursor.getInt(9));
                    tmVictimas.setHv2(cursor.getInt(10));
                    tmVictimas.setHv3(cursor.getInt(11));
                    tmVictimas.setHv4(cursor.getInt(12));
                    tmVictimas.setHv5(cursor.getInt(13));
                    tmVictimas.setHv6(cursor.getInt(14));
                    tmVictimas.setHv7(cursor.getInt(15));
                    tmVictimas.setHv8(cursor.getInt(16));
                    tmVictimas.setHv9(cursor.getInt(17));
                    tmVictimas.setHv10(cursor.getInt(18));
                    tmVictimas.setHv11(cursor.getInt(19));
                    tmVictimas.setHv12(cursor.getInt(20));
                    tmVictimas.setHv13(cursor.getInt(21));
                    tmVictimas.setHv14(cursor.getInt(22));
                    tmVictimas.setEstado(cursor.getString(23));
                    tmVictimas.setEncuestado(cursor.getInt(24));
                    tmVictimas.setFechaEncuesta(cursor.getString(25));
                    lsResultado.add(tmVictimas);
                } while (cursor.moveToNext());
            }
        }finally {
            cursor.close();
            db.close();
        }
        return lsResultado;
    }


    public List<emc_victimas_nosugar> getlistaVictimasDocumento(List<emc_personas_encuestas_no_sugar> Listdocumento){
        List<emc_victimas_nosugar> lsResultado = new ArrayList<>();
        String selectQuery = null;

        for(Integer conMH = 0; conMH < Listdocumento.size(); conMH++){

            char  ind;

            if(Listdocumento.get(conMH).getNUM_DOCU_RUV().equals(""))
            {
                ind = 'a';
            }else
            {
                ind = Listdocumento.get(conMH).getNUM_DOCU_RUV().charAt(0);
            }

            switch (ind)
            {
                case '0':
                    selectQuery = "SELECT * FROM personas0 WHERE documento = '" + Listdocumento.get(conMH).getNUM_DOCU_RUV() + "';";
                    break;
                case '1':
                    selectQuery = "SELECT * FROM personas1 WHERE documento = '" + Listdocumento.get(conMH).getNUM_DOCU_RUV() + "';";
                    break;
                case '2':
                    selectQuery = "SELECT * FROM personas2 WHERE documento = '" + Listdocumento.get(conMH).getNUM_DOCU_RUV() + "';";
                    break;
                case '3':
                    selectQuery = "SELECT * FROM personas3 WHERE documento = '" + Listdocumento.get(conMH).getNUM_DOCU_RUV() + "';";
                    break;
                case '4':
                    selectQuery = "SELECT * FROM personas4 WHERE documento = '" + Listdocumento.get(conMH).getNUM_DOCU_RUV() + "';";
                    break;
                case '5':
                    selectQuery = "SELECT * FROM personas5 WHERE documento = '" + Listdocumento.get(conMH).getNUM_DOCU_RUV() + "';";
                    break;
                case '6':
                    selectQuery = "SELECT * FROM personas6 WHERE documento = '" + Listdocumento.get(conMH).getNUM_DOCU_RUV() + "';";
                    break;
                case '7':
                    selectQuery = "SELECT * FROM personas7 WHERE documento = '" + Listdocumento.get(conMH).getNUM_DOCU_RUV() + "';";
                    break;
                case '8':
                    selectQuery = "SELECT * FROM personas8 WHERE documento = '" + Listdocumento.get(conMH).getNUM_DOCU_RUV() + "';";
                    break;
                case '9':
                    selectQuery = "SELECT * FROM personas9 WHERE documento = '" + Listdocumento.get(conMH).getNUM_DOCU_RUV() + "';";
                    break;
                default:
                    selectQuery = "SELECT * FROM personasa WHERE documento = '" + Listdocumento.get(conMH).getNUM_DOCU_RUV() + "';";
                    break;
            }


            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            try {
                if (cursor.moveToFirst()) {
                    do {
                        emc_victimas_nosugar tmVictimas = new emc_victimas_nosugar();
                        tmVictimas.setConsPersona(cursor.getInt(0));
                        tmVictimas.setHogar(cursor.getInt(1));
                        tmVictimas.setTipoDoc(cursor.getString(2));
                        tmVictimas.setDocumento(cursor.getString(3));
                        tmVictimas.setNombre1(cursor.getString(4));
                        tmVictimas.setNombre2(cursor.getString(5));
                        tmVictimas.setApellido1(cursor.getString(6));
                        tmVictimas.setApellido2(cursor.getString(7));
                        tmVictimas.setFecNacimiento(cursor.getString(8));
                        tmVictimas.setHv1(cursor.getInt(9));
                        tmVictimas.setHv2(cursor.getInt(10));
                        tmVictimas.setHv3(cursor.getInt(11));
                        tmVictimas.setHv4(cursor.getInt(12));
                        tmVictimas.setHv5(cursor.getInt(13));
                        tmVictimas.setHv6(cursor.getInt(14));
                        tmVictimas.setHv7(cursor.getInt(15));
                        tmVictimas.setHv8(cursor.getInt(16));
                        tmVictimas.setHv9(cursor.getInt(17));
                        tmVictimas.setHv10(cursor.getInt(18));
                        tmVictimas.setHv11(cursor.getInt(19));
                        tmVictimas.setHv12(cursor.getInt(20));
                        tmVictimas.setHv13(cursor.getInt(21));
                        tmVictimas.setHv14(cursor.getInt(22));
                        tmVictimas.setEstado(cursor.getString(23));
                        tmVictimas.setEncuestado(cursor.getInt(24));
                        tmVictimas.setFechaEncuesta(cursor.getString(25));
                        lsResultado.add(tmVictimas);
                    } while (cursor.moveToNext());
                }
            }finally {
                cursor.close();
                db.close();
            }

        }


        return lsResultado;
    }

    public List<emc_personas_encuestas_no_sugar> getListaPersonasEncuestadas(ArrayList<emc_miembros_hogar> miembrosHogar){


        List<emc_personas_encuestas_no_sugar> lsResultado = new ArrayList<>();
        String selectQuery = null;
        for(Integer conMH = 0; conMH < miembrosHogar.size(); conMH++){

            selectQuery = "SELECT * FROM PERSONASYAENCUESTADAS WHERE ID_ENTREVISTA IN (SELECT DISTINCT ID_ENTREVISTA FROM PERSONASYAENCUESTADAS WHERE num_docu_ruv = '" + miembrosHogar.get(conMH).getDocumento() + "');";

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            try {
                if (cursor.moveToFirst()) {
                    do {
                        emc_personas_encuestas_no_sugar tmVictimas = new emc_personas_encuestas_no_sugar();
                        tmVictimas.setMunicipio(cursor.getString(1));
                        tmVictimas.setDane(cursor.getString(2));
                        tmVictimas.setId_entrevista(cursor.getString(3));
                        tmVictimas.setInicio_encuesta(cursor.getString(4));
                        tmVictimas.setFin_encuesta(cursor.getString(5));
                        tmVictimas.setId_personas(cursor.getString(6));
                        tmVictimas.setESTADO_RUV(cursor.getString(7));
                        tmVictimas.setNOMBRE_1_RUV(cursor.getString(8));
                        tmVictimas.setNOMBRE_2_RUV(cursor.getString(9));
                        tmVictimas.setAPELLIDO_1_RUV(cursor.getString(10));
                        tmVictimas.setAPELLIDO_2_RUV(cursor.getString(11));
                        tmVictimas.setNUM_DOCU_RUV(cursor.getString(12));
                        tmVictimas.setNUMERO_DOCU_ENCUESTA(cursor.getString(13));

                        lsResultado.add(tmVictimas);
                    } while (cursor.moveToNext());
                }
            }finally {
                cursor.close();
                db.close();
            }

        }


        return lsResultado;
    }

    public List<emc_personas_encuestas_no_sugar> getListaPersonasEncuestadas(String documento){


        List<emc_personas_encuestas_no_sugar> lsResultado = new ArrayList<>();
        String selectQuery = null;

            selectQuery = "SELECT * FROM PERSONASYAENCUESTADAS WHERE ID_ENTREVISTA IN (SELECT DISTINCT ID_ENTREVISTA FROM PERSONASYAENCUESTADAS WHERE num_docu_ruv = '" + documento+ "');";

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            try {
                if (cursor.moveToFirst()) {
                    do {
                        emc_personas_encuestas_no_sugar tmVictimas = new emc_personas_encuestas_no_sugar();
                        tmVictimas.setMunicipio(cursor.getString(1));
                        tmVictimas.setBarrio(cursor.getString(2));
                        tmVictimas.setId_entrevista(cursor.getString(3));
                        tmVictimas.setInicio_encuesta(cursor.getString(4));
                        tmVictimas.setFin_encuesta(cursor.getString(5));
                        tmVictimas.setId_personas(cursor.getString(6));
                        tmVictimas.setESTADO_RUV(cursor.getString(7));
                        tmVictimas.setNOMBRE_1_RUV(cursor.getString(8));
                        tmVictimas.setNOMBRE_2_RUV(cursor.getString(9));
                        tmVictimas.setAPELLIDO_1_RUV(cursor.getString(10));
                        tmVictimas.setAPELLIDO_2_RUV(cursor.getString(11));
                        tmVictimas.setNUM_DOCU_RUV(cursor.getString(12));
                        tmVictimas.setNUMERO_DOCU_ENCUESTA(cursor.getString(13));

                        lsResultado.add(tmVictimas);
                    } while (cursor.moveToNext());
                }
            }finally {
                cursor.close();
                db.close();
            }




        return lsResultado;
    }

    public List<emc_victimas_nosugar> getlistaVictimasCons_persona(String documento,String cons_persona){
        List<emc_victimas_nosugar> lsResultado = new ArrayList<>();
        String selectQuery = null;
        char  ind;

        if(documento.equals(""))
        {
            ind = '0';
        }else
        {
            ind = documento.charAt(0);
        }

        switch (ind)
        {
            case '0':
                selectQuery = "SELECT * FROM personas0 WHERE cons_persona = '" + cons_persona + "';";
                break;
            case '1':
                selectQuery = "SELECT * FROM personas1 WHERE cons_persona = '" + cons_persona + "';";
                break;
            case '2':
                selectQuery = "SELECT * FROM personas2 WHERE cons_persona = '" + cons_persona + "';";
                break;
            case '3':
                selectQuery = "SELECT * FROM personas3 WHERE cons_persona = '" + cons_persona + "';";
                break;
            case '4':
                selectQuery = "SELECT * FROM personas4 WHERE cons_persona = '" + cons_persona + "';";
                break;
            case '5':
                selectQuery = "SELECT * FROM personas5 WHERE cons_persona = '" + cons_persona + "';";
                break;
            case '6':
                selectQuery = "SELECT * FROM personas6 WHERE cons_persona = '" + cons_persona + "';";
                break;
            case '7':
                selectQuery = "SELECT * FROM personas7 WHERE cons_persona = '" + cons_persona + "';";
                break;
            case '8':
                selectQuery = "SELECT * FROM personas8 WHERE cons_persona = '" + cons_persona + "';";
                break;
            case '9':
                selectQuery = "SELECT * FROM personas9 WHERE cons_persona = '" + cons_persona + "';";
                break;
            default:
                selectQuery = "SELECT * FROM personasa WHERE cons_persona = '" + cons_persona + "';";
                break;
        }


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    emc_victimas_nosugar tmVictimas = new emc_victimas_nosugar();
                    tmVictimas.setConsPersona(cursor.getInt(0));
                    tmVictimas.setHogar(cursor.getInt(1));
                    tmVictimas.setTipoDoc(cursor.getString(2));
                    tmVictimas.setDocumento(cursor.getString(3));
                    tmVictimas.setNombre1(cursor.getString(4));
                    tmVictimas.setNombre2(cursor.getString(5));
                    tmVictimas.setApellido1(cursor.getString(6));
                    tmVictimas.setApellido2(cursor.getString(7));
                    tmVictimas.setFecNacimiento(cursor.getString(8));
                    tmVictimas.setHv1(cursor.getInt(9));
                    tmVictimas.setHv2(cursor.getInt(10));
                    tmVictimas.setHv3(cursor.getInt(11));
                    tmVictimas.setHv4(cursor.getInt(12));
                    tmVictimas.setHv5(cursor.getInt(13));
                    tmVictimas.setHv6(cursor.getInt(14));
                    tmVictimas.setHv7(cursor.getInt(15));
                    tmVictimas.setHv8(cursor.getInt(16));
                    tmVictimas.setHv9(cursor.getInt(17));
                    tmVictimas.setHv10(cursor.getInt(18));
                    tmVictimas.setHv11(cursor.getInt(19));
                    tmVictimas.setHv12(cursor.getInt(20));
                    tmVictimas.setHv13(cursor.getInt(21));
                    tmVictimas.setHv14(cursor.getInt(22));
                    tmVictimas.setEstado(cursor.getString(23));
                    tmVictimas.setEncuestado(cursor.getInt(24));
                    tmVictimas.setFechaEncuesta(cursor.getString(25));
                    lsResultado.add(tmVictimas);
                } while (cursor.moveToNext());
            }
        }finally {
            cursor.close();
            db.close();
        }
        return lsResultado;
    }


   public List<emc_victimas_nosugar> getlistaVictimasHogar(String documento){
        List<emc_victimas_nosugar> lsResultado = new ArrayList<>();

        String selectQuery = "SELECT * FROM personas WHERE HOGAR IN (SELECT v.HOGAR FROM personas v WHERE  v.CONS_PERSONA = '" + documento + "' );";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    emc_victimas_nosugar tmVictimas = new emc_victimas_nosugar();
                    tmVictimas.setConsPersona(cursor.getInt(0));
                    tmVictimas.setHogar(cursor.getInt(1));
                    tmVictimas.setTipoDoc(cursor.getString(2));
                    tmVictimas.setDocumento(cursor.getString(3));
                    tmVictimas.setNombre1(cursor.getString(4));
                    tmVictimas.setNombre2(cursor.getString(5));
                    tmVictimas.setApellido1(cursor.getString(6));
                    tmVictimas.setApellido2(cursor.getString(7));
                    tmVictimas.setFecNacimiento(cursor.getString(8));
                    tmVictimas.setHv1(cursor.getInt(9));
                    tmVictimas.setHv2(cursor.getInt(10));
                    tmVictimas.setHv3(cursor.getInt(11));
                    tmVictimas.setHv4(cursor.getInt(12));
                    tmVictimas.setHv5(cursor.getInt(13));
                    tmVictimas.setHv6(cursor.getInt(14));
                    tmVictimas.setHv7(cursor.getInt(15));
                    tmVictimas.setHv8(cursor.getInt(16));
                    tmVictimas.setHv9(cursor.getInt(17));
                    tmVictimas.setHv10(cursor.getInt(18));
                    tmVictimas.setHv11(cursor.getInt(19));
                    tmVictimas.setHv12(cursor.getInt(20));
                    tmVictimas.setHv13(cursor.getInt(21));
                    tmVictimas.setHv14(cursor.getInt(22));
                    tmVictimas.setEstado(cursor.getString(23));
                    tmVictimas.setEncuestado(cursor.getInt(24));
                    tmVictimas.setFechaEncuesta(cursor.getString(25));
                    lsResultado.add(tmVictimas);
                } while (cursor.moveToNext());
            }
        }finally {
            cursor.close();
            db.close();
        }
        return lsResultado;
    }


    public List<emc_victimas_nosugar> getlistaHechosVictimasHogar(String conspersona){
        List<emc_victimas_nosugar> lsResultado = new ArrayList<>();

        String selectQuery = "SELECT * FROM personas WHERE HOGAR IN (SELECT v.HOGAR FROM personas v WHERE  v.CONS_PERSONA = '" + conspersona + "' );";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    emc_victimas_nosugar tmVictimas = new emc_victimas_nosugar();
                    tmVictimas.setConsPersona(cursor.getInt(0));
                    tmVictimas.setHogar(cursor.getInt(1));
                    tmVictimas.setTipoDoc(cursor.getString(2));
                    tmVictimas.setDocumento(cursor.getString(3));
                    tmVictimas.setNombre1(cursor.getString(4));
                    tmVictimas.setNombre2(cursor.getString(5));
                    tmVictimas.setApellido1(cursor.getString(6));
                    tmVictimas.setApellido2(cursor.getString(7));
                    tmVictimas.setFecNacimiento(cursor.getString(8));
                    tmVictimas.setHv1(cursor.getInt(9));
                    tmVictimas.setHv2(cursor.getInt(10));
                    tmVictimas.setHv3(cursor.getInt(11));
                    tmVictimas.setHv4(cursor.getInt(12));
                    tmVictimas.setHv5(cursor.getInt(13));
                    tmVictimas.setHv6(cursor.getInt(14));
                    tmVictimas.setHv7(cursor.getInt(15));
                    tmVictimas.setHv8(cursor.getInt(16));
                    tmVictimas.setHv9(cursor.getInt(17));
                    tmVictimas.setHv10(cursor.getInt(18));
                    tmVictimas.setHv11(cursor.getInt(19));
                    tmVictimas.setHv12(cursor.getInt(20));
                    tmVictimas.setHv13(cursor.getInt(21));
                    tmVictimas.setHv14(cursor.getInt(22));
                    tmVictimas.setEstado(cursor.getString(23));
                    tmVictimas.setEncuestado(cursor.getInt(24));
                    tmVictimas.setFechaEncuesta(cursor.getString(25));
                    lsResultado.add(tmVictimas);
                } while (cursor.moveToNext());
            }
        }finally {
            cursor.close();
            db.close();
        }
        return lsResultado;
    }
}