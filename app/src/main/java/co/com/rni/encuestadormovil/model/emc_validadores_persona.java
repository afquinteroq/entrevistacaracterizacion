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
public class emc_validadores_persona extends SugarRecord<emc_validadores_persona> {

    String ins_idinstrumento;
    String per_idpersona;
    String val_idvalidador;
    String pre_valor;
    String hog_codigo;
    String comodin;
    String fechahecho;
    /*
    public emc_validadores_persona(String ins_idinstrumento, String per_idpersona, String val_idvalidador, String pre_valor, String hog_codigo) {
        this.ins_idinstrumento = ins_idinstrumento;
        this.per_idpersona = per_idpersona;
        this.val_idvalidador = val_idvalidador;
        this.pre_valor = pre_valor;
        this.hog_codigo = hog_codigo;
    }

    public emc_validadores_persona() {
    }

    public String getIns_idinstrumento() {
        return ins_idinstrumento;
    }

    public void setIns_idinstrumento(String ins_idinstrumento) {
        this.ins_idinstrumento = ins_idinstrumento;
    }

    public String getPer_idpersona() {
        return per_idpersona;
    }

    public void setPer_idpersona(String per_idpersona) {
        this.per_idpersona = per_idpersona;
    }

    public String getVal_idvalidador() {
        return val_idvalidador;
    }

    public void setVal_idvalidador(String val_idvalidador) {
        this.val_idvalidador = val_idvalidador;
    }

    public String getPre_valor() {
        return pre_valor;
    }

    public void setPre_valor(String pre_valor) {
        this.pre_valor = pre_valor;
    }

    public String getHog_codigo() {
        return hog_codigo;
    }

    public void setHog_codigo(String hog_codigo) {
        this.hog_codigo = hog_codigo;
    }
    */
}
