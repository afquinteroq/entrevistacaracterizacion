package co.com.rni.encuestadormovil.model;

import com.orm.SugarRecord;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by ASUS on 13/07/2018.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class emc_resguardosindigenas extends SugarRecord<emc_resguardosindigenas> {

    String idresguardo;
    String nombreresguardo;

    /*
    public emc_resguardosindigenas(String idresguardo, String nombreresguardo) {
        this.idresguardo = idresguardo;
        this.nombreresguardo = nombreresguardo;
    }

    public emc_resguardosindigenas() {
    }

    public String getIdresguardo() {
        return idresguardo;
    }

    public void setIdresguardo(String idresguardo) {
        this.idresguardo = idresguardo;
    }

    public String getNombreresguardo() {
        return nombreresguardo;
    }

    public void setNombreresguardo(String nombreresguardo) {
        this.nombreresguardo = nombreresguardo;
    }
    */
}
