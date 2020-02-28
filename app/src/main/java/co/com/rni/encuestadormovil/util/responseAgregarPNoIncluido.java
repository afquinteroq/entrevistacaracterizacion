package co.com.rni.encuestadormovil.util;


import co.com.rni.encuestadormovil.adapter.miembrosHogarAdapter;
import co.com.rni.encuestadormovil.model.emc_miembros_hogar;
import co.com.rni.encuestadormovil.model.emc_victimas;

/**
 * Created by ASUS on 19/12/2018.
 */

public interface responseAgregarPNoIncluido {

    public void resultado(boolean exito,emc_miembros_hogar nuevaPersona, String idPersona, emc_victimas nuevaPersonaNoIncluido, int reultdoconsult);


}
