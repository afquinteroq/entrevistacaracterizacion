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
public class emc_instrumento_validador extends SugarRecord<emc_instrumento_validador> {

    String val_idvalidador;
    String ins_idinstrumento;
    String pre_validador;
    String pre_valor;
    String pre_longcampo;
    String pre_validador_min;
    String pre_validador_max;

    /*
    public emc_instrumento_validador() {
    }

    public emc_instrumento_validador(String val_idvalidador, String ins_idinstrumento, String pre_validador, String pre_valor, String pre_longcampo, String pre_validador_min, String pre_validador_max) {
        this.val_idvalidador = val_idvalidador;
        this.ins_idinstrumento = ins_idinstrumento;
        this.pre_validador = pre_validador;
        this.pre_valor = pre_valor;
        this.pre_longcampo = pre_longcampo;
        this.pre_validador_min = pre_validador_min;
        this.pre_validador_max = pre_validador_max;
    }


    public String getVal_idvalidador() {
        return val_idvalidador;
    }

    public void setVal_idvalidador(String val_idvalidador) {
        this.val_idvalidador = val_idvalidador;
    }

    public String getIns_idinstrumento() {
        return ins_idinstrumento;
    }

    public void setIns_idinstrumento(String ins_idinstrumento) {
        this.ins_idinstrumento = ins_idinstrumento;
    }

    public String getPre_validador() {
        return pre_validador;
    }

    public void setPre_validador(String pre_validador) {
        this.pre_validador = pre_validador;
    }

    public String getPre_valor() {
        return pre_valor;
    }

    public void setPre_valor(String pre_valor) {
        this.pre_valor = pre_valor;
    }

    public String getPre_longcampo() {
        return pre_longcampo;
    }

    public void setPre_longcampo(String pre_longcampo) {
        this.pre_longcampo = pre_longcampo;
    }

    public String getPre_validador_min() {
        return pre_validador_min;
    }

    public void setPre_validador_min(String pre_validador_min) {
        this.pre_validador_min = pre_validador_min;
    }

    public String getPre_validador_max() {
        return pre_validador_max;
    }

    public void setPre_validador_max(String pre_validador_max) {
        this.pre_validador_max = pre_validador_max;
    }
    */

}
