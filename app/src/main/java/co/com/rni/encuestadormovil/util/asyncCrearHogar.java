package co.com.rni.encuestadormovil.util;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;


import co.com.rni.encuestadormovil.model.emc_hogares;
import co.com.rni.encuestadormovil.model.emc_miembros_hogar;
import co.com.rni.encuestadormovil.model.emc_usuarios;
import co.com.rni.encuestadormovil.model.emc_version;
import co.com.rni.encuestadormovil.model.emc_victimas_nosugar;
import co.com.rni.encuestadormovil.sqlite.DbHelper;

import static co.com.rni.encuestadormovil.util.general.fechaActual;

/**
 * Created by ASUS on 09/10/2018.
 */

public class asyncCrearHogar extends AsyncTask<String, Integer, Boolean> {

    private Context ctx;
    private responseCrearHogar callback;
    private ArrayList<emc_miembros_hogar> miembrosHogar;
    private String UIDhogar;
    public emc_usuarios usuarioLogin;
    private List<emc_victimas_nosugar> lsVictimas;
    private DbHelper myDB;
    private emc_hogares hogar;
    private boolean capitulo12;




    public asyncCrearHogar(Context ctx, responseCrearHogar callback, ArrayList<emc_miembros_hogar> miembrosHogar, String UIDhogar,
                           emc_usuarios usuarioLogin, List<emc_victimas_nosugar> lsVictimas, DbHelper myDB, boolean capitulo12) {
        this.ctx = ctx;
        this.callback = callback;
        this.miembrosHogar = miembrosHogar;
        this.UIDhogar = UIDhogar;
        this.usuarioLogin = usuarioLogin;
        this.lsVictimas = lsVictimas;
        this.myDB = myDB;
        this.capitulo12 = capitulo12;

    }

    @Override
    protected Boolean doInBackground(String... params) {


        if(crearHogar()){
            return true;
        } else{
            return false;
        }

    }

    public boolean crearHogar(){

        try {


            if(miembrosHogar.size() <1){
                return false;

            }else if(miembrosHogar.size() >0){
                emc_hogares hogar = new emc_hogares();

                boolean tieneJefe = false;
                int jefe = 0;

                for (Integer cMH = 0; cMH < miembrosHogar.size(); cMH++) {
                    emc_miembros_hogar miembro = miembrosHogar.get(cMH);
                    if(miembro.getInd_jefe().equals("SI")){
                        jefe = jefe + 1;
                    }
                }

                for (Integer cMH = 0; cMH < miembrosHogar.size(); cMH++) {
                    emc_miembros_hogar miembro = miembrosHogar.get(cMH);
                    if(miembro.getInd_jefe().equals("SI")){
                        tieneJefe = true;
                        break;
                    }
                }


                if(tieneJefe==true){

                    hogar.setHog_codigo(UIDhogar);
                    hogar.setUsu_usuariocreacion(usuarioLogin.getNombreusuario());
                    hogar.setUsu_fechacreacion(fechaActual());
                    hogar.setEstado("Incompleta");

                    boolean validadorEstado = false;

                    for (Integer cMHFv = 0; cMHFv < miembrosHogar.size(); cMHFv++) {
                        emc_miembros_hogar miembroFv = miembrosHogar.get(cMHFv);
                        String estado = miembroFv.getEstado().toUpperCase();
                        if(estado.equals("INCLUIDO")){
                            validadorEstado = true;
                            break;
                        }
                    }


                    if (validadorEstado==false && usuarioLogin.getIdPerfil().equals("710")){
                        hogar.setHog_tipo("B");
                    }else{
                        hogar.setHog_tipo("A");
                    }

                    hogar.save();

                    if(capitulo12==true){
                        emc_version version  = new emc_version(3,UIDhogar,"ACNUR");
                        version.save();
                    }

                    //List<emc_hogares> lsCapTer = emc_hogares.find(emc_hogares.class, "UPPER(HOGCODIGO) = ? ", UIDhogar.toUpperCase());

                    for (Integer cMH = 0; cMH < miembrosHogar.size(); cMH++) {
                        emc_miembros_hogar miembro = miembrosHogar.get(cMH);
                        miembro.save();
                        if (miembro.getInd_jefe().equals("SI")){
                            tieneJefe = true;
                        }

                        boolean valEstado = false;

                        for (Integer cMHF = 0; cMHF < miembrosHogar.size(); cMHF++) {
                            emc_miembros_hogar miembroF = miembrosHogar.get(cMHF);
                            String estado = miembroF.getEstado().toUpperCase();
                            if(estado.equals("INCLUIDO")){
                                valEstado = true;
                                break;
                            }
                        }

                        if (valEstado==false && usuarioLogin.getIdPerfil().equals("710")){
                            gestionEncuestas.GIC_INSERT_VALIDADOR_HOGAR(Integer.valueOf(miembro.getPer_idpersona()), miembro.getHog_codigo(), "INCLUIDO", 1);
                        }else{
                            gestionEncuestas.GIC_INSERT_VALIDADOR_HOGAR(Integer.valueOf(miembro.getPer_idpersona()), miembro.getHog_codigo(), miembro.getEstado(), 1);
                        }

                        String valJefe = "NO JEFE";
                        if (miembro.getInd_jefe().equals("SI"))
                        {
                            valJefe = "JEFE";
                        }


                        //24/02/2020 inserta validador tipopersona
                        gestionEncuestas.GIC_INSERT_VALIDADOR_PERFIL_USUARIO(Integer.valueOf(miembro.getPer_idpersona()), miembro.getHog_codigo(), "5005",usuarioLogin.getIdPerfil(), 1);
                        gestionEncuestas.GIC_INSERT_VALIDADOR_TIPO_PERSONA(Integer.valueOf(miembro.getPer_idpersona()), miembro.getHog_codigo(), miembro.getTipoPersona(), 1);
                        //24/02/2020

                        gestionEncuestas.GIC_INSERT_VALIDADOR_PARENT(Integer.valueOf(miembro.getPer_idpersona()), miembro.getHog_codigo(), valJefe, 1);

                        gestionEncuestas.GIC_INSERT_VALIDADOR_TD(Integer.valueOf(miembro.getPer_idpersona()), miembro.getHog_codigo(), miembro.getTipoDoc(), 1);


                        lsVictimas = myDB.getlistaVictimasCons_persona(miembro.getDocumento(),miembro.getPer_idpersona().toString());
                        final String hogarcodigo = miembro.getHog_codigo();
                        if (lsVictimas.size() > 0) {
                            emc_victimas_nosugar tmPer =  lsVictimas.get(0);
                            if (tmPer.hv1 != null) {

                                if (tmPer.getHv1() == 1) {
                                    gestionEncuestas.GIC_INSERT_VALIDADOR_HECHO_AUX(Integer.valueOf(tmPer.getConsPersona()), hogarcodigo, 1, 1);
                                }if (tmPer.hv2 != null) {

                                    if (tmPer.getHv2() == 1) {
                                        gestionEncuestas.GIC_INSERT_VALIDADOR_HECHO_AUX(Integer.valueOf(tmPer.getConsPersona()), hogarcodigo, 2, 1);
                                    }

                                }

                                if (tmPer.hv3 != null) {

                                    if (tmPer.getHv3() == 1) {
                                        gestionEncuestas.GIC_INSERT_VALIDADOR_HECHO_AUX(Integer.valueOf(tmPer.getConsPersona()), hogarcodigo, 3, 1);
                                    }

                                }

                                if (tmPer.hv4 != null) {
                                    if (tmPer.getHv4() == 1) {
                                        gestionEncuestas.GIC_INSERT_VALIDADOR_HECHO_AUX(Integer.valueOf(tmPer.getConsPersona()), hogarcodigo, 4, 1);
                                    }

                                }

                                if (tmPer.hv5 != null) {
                                    if (tmPer.getHv5() == 1) {
                                        gestionEncuestas.GIC_INSERT_VALIDADOR_HECHO_AUX(Integer.valueOf(tmPer.getConsPersona()), hogarcodigo, 5, 1);
                                    }
                                }

                                if (tmPer.hv6 != null) {
                                    if (tmPer.getHv6() == 1) {
                                        gestionEncuestas.GIC_INSERT_VALIDADOR_HECHO_AUX(Integer.valueOf(tmPer.getConsPersona()), hogarcodigo, 6, 1);
                                    }
                                }

                                if (tmPer.hv7 != null) {
                                    if (tmPer.getHv7() == 1) {
                                        gestionEncuestas.GIC_INSERT_VALIDADOR_HECHO_AUX(Integer.valueOf(tmPer.getConsPersona()), hogarcodigo, 7, 1);
                                    }
                                }
                                if (tmPer.hv8 != null) {
                                    if (tmPer.getHv8() == 1) {
                                        gestionEncuestas.GIC_INSERT_VALIDADOR_HECHO_AUX(Integer.valueOf(tmPer.getConsPersona()), hogarcodigo, 8, 1);
                                    }
                                }
                                if (tmPer.hv9 != null) {

                                    if (tmPer.getHv9() == 1) {
                                        gestionEncuestas.GIC_INSERT_VALIDADOR_HECHO_AUX(Integer.valueOf(tmPer.getConsPersona()), hogarcodigo, 9, 1);
                                    }
                                }

                                if (tmPer.hv10 != null) {

                                    if (tmPer.getHv10() == 1) {
                                        gestionEncuestas.GIC_INSERT_VALIDADOR_HECHO_AUX(Integer.valueOf(tmPer.getConsPersona()), hogarcodigo, 10, 1);
                                    }
                                }

                                if (tmPer.hv11 != null) {

                                    if (tmPer.getHv11() == 1) {
                                        gestionEncuestas.GIC_INSERT_VALIDADOR_HECHO_AUX(Integer.valueOf(tmPer.getConsPersona()), hogarcodigo, 11, 1);
                                    }
                                }
                                if (tmPer.hv12 != null) {
                                    if (tmPer.getHv12() == 1) {
                                        gestionEncuestas.GIC_INSERT_VALIDADOR_HECHO_AUX(Integer.valueOf(tmPer.getConsPersona()), hogarcodigo, 12, 1);
                                    }
                                }
                                if (tmPer.hv13 != null) {
                                    if (tmPer.getHv13() == 1) {
                                        gestionEncuestas.GIC_INSERT_VALIDADOR_HECHO_AUX(Integer.valueOf(tmPer.getConsPersona()), hogarcodigo, 13, 1);
                                    }
                                }
                                if (tmPer.hv14 != null) {
                                    if (tmPer.getHv14() == 1) {
                                        gestionEncuestas.GIC_INSERT_VALIDADOR_HECHO_AUX(Integer.valueOf(tmPer.getConsPersona()), hogarcodigo, 14, 1);
                                    }
                                }

                            }

                        }

                    }
                    this.hogar = hogar;
                    return true;


                }else{

                    return false;
                }
            }else{
                return false;
            }

        }catch (Exception e)
        {
            e.getMessage();
            return false;
        }

    }


    protected void onPostExecute(Boolean result){
        callback.resultado(result, hogar);

    }


}
