package co.com.rni.encuestadormovil.model;

import com.orm.SugarRecord;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by javierperez on 15/12/15.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class emc_departamento extends SugarRecord<emc_departamento> {

    String id_depto;
    String nom_depto;
    /*
    public emc_departamento(String id_depto, String nom_depto) {
        this.id_depto = id_depto;
        this.nom_depto = nom_depto;
    }

    public emc_departamento() {

    }

    public String getId_depto() {
        return id_depto;
    }

    public void setId_depto(String id_depto) {
        this.id_depto = id_depto;
    }

    public String getNom_depto() {
        return nom_depto;
    }

    public void setNom_depto(String nom_depto) {
        this.nom_depto = nom_depto;
    }
    */

}
