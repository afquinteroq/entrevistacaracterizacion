package co.com.rni.encuestadormovil.model;

import com.orm.SugarRecord;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by javierperez on 16/12/15.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class emc_pregunta_hijos extends SugarRecord<emc_pregunta_hijos> {

    String res_idrespuesta;
    String pre_idpregunta;
    String pre_depende;
    String val_todohogar;

    /*
    public emc_pregunta_hijos() {
    }

    public emc_pregunta_hijos(String res_idrespuesta, String pre_idpregunta, String pre_depende, String val_todohogar) {
        this.res_idrespuesta = res_idrespuesta;
        this.pre_idpregunta = pre_idpregunta;
        this.pre_depende = pre_depende;
        this.val_todohogar = val_todohogar;
    }

    public String getRes_idrespuesta() {
        return res_idrespuesta;
    }

    public void setRes_idrespuesta(String res_idrespuesta) {
        this.res_idrespuesta = res_idrespuesta;
    }

    public String getPre_idpregunta() {
        return pre_idpregunta;
    }

    public void setPre_idpregunta(String pre_idpregunta) {
        this.pre_idpregunta = pre_idpregunta;
    }

    public String getPre_depende() {
        return pre_depende;
    }

    public void setPre_depende(String pre_depende) {
        this.pre_depende = pre_depende;
    }

    public String getVal_todohogar() {
        return val_todohogar;
    }

    public void setVal_todohogar(String val_todohogar) {
        this.val_todohogar = val_todohogar;
    }
    */

}
