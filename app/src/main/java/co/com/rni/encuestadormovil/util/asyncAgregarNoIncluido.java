package co.com.rni.encuestadormovil.util;

import android.os.AsyncTask;
import android.view.View;


import java.text.SimpleDateFormat;
import java.util.List;

import co.com.rni.encuestadormovil.model.emc_miembros_hogar;
import co.com.rni.encuestadormovil.model.emc_usuarios;
import co.com.rni.encuestadormovil.model.emc_victimas;
import co.com.rni.encuestadormovil.model.emc_victimas_nosugar;
import co.com.rni.encuestadormovil.sqlite.DbHelper;

import static co.com.rni.encuestadormovil.util.general.fechaActual;
import static co.com.rni.encuestadormovil.util.general.fechaActualSinHora;
import static co.com.rni.encuestadormovil.util.general.fechaValida;

public class asyncAgregarNoIncluido extends AsyncTask<String, Integer, Boolean> {

    private emc_miembros_hogar nuevaPersona;
    private String UIDhogar;
    private String idPersona;
    private emc_usuarios usuarioLogin;
    private String spTipoDoc;
    private String etNumDoc;
    private String etNombre1;
    private String etNombre2;
    private String etApellido1;
    private String etApellido2;
    private String etFecNac;
    private responseAgregarPNoIncluido callback;

    emc_victimas nuevaPersonaNoIncluido;
    private DbHelper myDB;
    private int reultdoconsult;



    public asyncAgregarNoIncluido(emc_miembros_hogar nuevaPersona, String UIDhogar, String idPersona, emc_usuarios usuarioLogin, String spTipoDoc, String etNumDoc, String etNombre1, String etNombre2, String etApellido1, String etApellido2, String etFecNac, responseAgregarPNoIncluido callback, DbHelper myDB,emc_victimas nuevaPersonaNoIncluido) {
        this.nuevaPersona = nuevaPersona;
        this.UIDhogar = UIDhogar;
        this.idPersona = idPersona;
        this.usuarioLogin = usuarioLogin;
        this.spTipoDoc = spTipoDoc;
        this.etNumDoc = etNumDoc;
        this.etNombre1 = etNombre1;
        this.etNombre2 = etNombre2;
        this.etApellido1 = etApellido1;
        this.etApellido2 = etApellido2;
        this.etFecNac = etFecNac;
        this.callback = callback;
        this.myDB = myDB;
        this.nuevaPersonaNoIncluido = nuevaPersonaNoIncluido;
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        boolean valForNP = true;
        if (etNumDoc.equals("")) {

            valForNP = false;

        }

        int lengetNumDoc = etNumDoc.length();

        if (lengetNumDoc > 11) {

            valForNP = false;

        }
        if (etApellido1.equals("")) {

            valForNP = false;


        }
        if (etNombre1.equals("")) {

            valForNP = false;

        }
        if (etFecNac.equals("")) {

            valForNP = false;

        }

        boolean valFecha = fechaValida(etFecNac);

        if ((!etFecNac.equals("") && valFecha == false)
                || etFecNac.length() != 10) {

            valForNP = false;

        }



        String fechaHoy  = fechaActualSinHora();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            if(sdf.parse(etFecNac).before(sdf.parse(fechaHoy))== false) {
                return false;
            }
        }catch (Exception e){
            return false;
        }


        if (valForNP == true) {
            reultdoconsult = comprobarNDocumento();
            if ( reultdoconsult > 0) {
                return false;
            } else {
                if (agregarNoIncluido()) {
                    return true;
                } else {
                    return false;
                }
            }
        }else{
            return false;
        }


    }

    private boolean agregarNoIncluido(){
        try {
            nuevaPersona.setHog_codigo(UIDhogar);
            nuevaPersona.setPer_idpersona(idPersona);
            nuevaPersona.setUsu_usuariocreacion(usuarioLogin.getNombreusuario());
            nuevaPersona.setUsu_fechacreacion(fechaActual());
            nuevaPersona.setTipoDoc(spTipoDoc);
            nuevaPersona.setDocumento(etNumDoc.toUpperCase());
            nuevaPersona.setNombre1(general.remoAcents(etNombre1.toUpperCase()));
            nuevaPersona.setNombre2(general.remoAcents(etNombre2.toUpperCase()));
            nuevaPersona.setApellido1(general.remoAcents(etApellido1.toUpperCase()));
            nuevaPersona.setApellido2(general.remoAcents(etApellido2.toUpperCase()));
            nuevaPersona.setFecNacimiento(etFecNac);
            nuevaPersona.setInd_jefe("No");
            nuevaPersona.setEstado("NO INCLUIDO");
            //miembrosHogar.add(nuevaPersona);
            nuevaPersona.save();


            nuevaPersonaNoIncluido.setConsPersona(Integer.valueOf(idPersona));
            nuevaPersonaNoIncluido.setDocumento(etNumDoc);
            nuevaPersonaNoIncluido.setApellido1(etApellido1.toUpperCase());
            nuevaPersonaNoIncluido.setApellido2(etApellido2.toUpperCase());
            nuevaPersonaNoIncluido.setNombre1(etNombre1.toUpperCase());
            nuevaPersonaNoIncluido.setNombre2(etNombre2.toUpperCase());
            nuevaPersonaNoIncluido.setFecNacimiento(etFecNac);
            nuevaPersonaNoIncluido.setFechaEncuesta(general.fechaActualSinHora());
            nuevaPersonaNoIncluido.setHogar(UIDhogar);

            return true;
        }catch (Exception e){
            e.getMessage();
            return false;
        }
    }

    private int comprobarNDocumento(){

        List<emc_victimas_nosugar> lsResultado = null;
        if(!etNumDoc.equals("") && !etNumDoc.equals("0") && etNumDoc.length()>0) {
            lsResultado = myDB.getlistaVictimasDocumento(etNumDoc);
            if (lsResultado != null) {
                return lsResultado.size();
            } else {
                return 0;
            }

        }
        return 0;
    }

    @Override
    protected void onPostExecute(Boolean result){
        callback.resultado(result,nuevaPersona,idPersona,nuevaPersonaNoIncluido, reultdoconsult);
    }

}
