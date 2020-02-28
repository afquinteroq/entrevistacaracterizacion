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
public class emc_validadores_instrumento extends SugarRecord<emc_validadores_instrumento> {

    String pre_idpregunta;
    String ins_idinstrumento;
    String tem_idtema;
    String val_idvalidador;
    String val_idvalidador_pers;
    /*
    public emc_validadores_instrumento() {
    }

    public emc_validadores_instrumento(String pre_idpregunta, String ins_idinstrumento, String tem_idtema, String val_idvalidador, String val_idvalidador_pers) {
        this.pre_idpregunta = pre_idpregunta;
        this.ins_idinstrumento = ins_idinstrumento;
        this.tem_idtema = tem_idtema;
        this.val_idvalidador = val_idvalidador;
        this.val_idvalidador_pers = val_idvalidador_pers;
    }

    public String getPre_idpregunta() {
        return pre_idpregunta;
    }

    public void setPre_idpregunta(String pre_idpregunta) {
        this.pre_idpregunta = pre_idpregunta;
    }

    public String getIns_idinstrumento() {
        return ins_idinstrumento;
    }

    public void setIns_idinstrumento(String ins_idinstrumento) {
        this.ins_idinstrumento = ins_idinstrumento;
    }

    public String getTem_idtema() {
        return tem_idtema;
    }

    public void setTem_idtema(String tem_idtema) {
        this.tem_idtema = tem_idtema;
    }

    public String getVal_idvalidador() {
        return val_idvalidador;
    }

    public void setVal_idvalidador(String val_idvalidador) {
        this.val_idvalidador = val_idvalidador;
    }

    public String getVal_idvalidador_pers() {
        return val_idvalidador_pers;
    }

    public void setVal_idvalidador_pers(String val_idvalidador_pers) {
        this.val_idvalidador_pers = val_idvalidador_pers;
    }
    */
}
