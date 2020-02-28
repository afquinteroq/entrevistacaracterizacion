package co.com.rni.encuestadormovil.model;

import com.orm.SugarRecord;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by ASUS on 20/08/2018.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class emc_veredas extends SugarRecord<emc_veredas> {

    String cod_dpto;
    String dane_mpio;
    String coddane_ver;
    String nom_dep;
    String nom_mpio;
    String nom_ver;

    /*
    public emc_veredas() {
    }

    public emc_veredas(String cod_dpto, String dane_mpio, String coddane_ver, String nom_dep, String nom_mpio, String nom_ver) {
        this.cod_dpto = cod_dpto;
        this.dane_mpio = dane_mpio;
        this.coddane_ver = coddane_ver;
        this.nom_dep = nom_dep;
        this.nom_mpio = nom_mpio;
        this.nom_ver = nom_ver;
    }

    public String getCod_dpto() {
        return cod_dpto;
    }

    public void setCod_dpto(String cod_dpto) {
        this.cod_dpto = cod_dpto;
    }

    public String getNom_ver() {
        return nom_ver;
    }

    public void setNom_ver(String nom_ver) {
        this.nom_ver = nom_ver;
    }

    public String getDane_mpio() {
        return dane_mpio;
    }

    public void setDane_mpio(String dane_mpio) {
        this.dane_mpio = dane_mpio;
    }

    public String getCoddane_ver() {
        return coddane_ver;
    }

    public void setCoddane_ver(String coddane_ver) {
        this.coddane_ver = coddane_ver;
    }

    public String getNom_dep() {
        return nom_dep;
    }

    public void setNom_dep(String nom_dep) {
        this.nom_dep = nom_dep;
    }

    public String getNom_mpio() {
        return nom_mpio;
    }

    public void setNom_mpio(String nom_mpio) {
        this.nom_mpio = nom_mpio;
    }
    */
}
