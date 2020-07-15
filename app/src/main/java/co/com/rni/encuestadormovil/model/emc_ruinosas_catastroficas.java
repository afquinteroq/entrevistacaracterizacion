package co.com.rni.encuestadormovil.model;

import com.orm.SugarRecord;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by ASUS on 23/07/2018.
 */
/*
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
*/
public class emc_ruinosas_catastroficas extends SugarRecord<emc_ruinosas_catastroficas> {

    String idEnfermedad;
    String nombreEnfermedad;

    public emc_ruinosas_catastroficas() {
    }

    public emc_ruinosas_catastroficas(String idEnfermedad, String nombreEnfermedad) {
        this.idEnfermedad = idEnfermedad;
        this.nombreEnfermedad = nombreEnfermedad;
    }

    public String getIdEnfermedad() {
        return idEnfermedad;
    }

    public void setIdEnfermedad(String idEnfermedad) {
        this.idEnfermedad = idEnfermedad;
    }

    public String getNombreEnfermedad() {
        return nombreEnfermedad;
    }

    public void setNombreEnfermedad(String nombreEnfermedad) {
        this.nombreEnfermedad = nombreEnfermedad;
    }
}
