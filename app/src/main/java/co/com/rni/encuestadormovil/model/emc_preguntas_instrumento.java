package co.com.rni.encuestadormovil.model;

import com.orm.SugarRecord;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by javierperez on 16/12/15.
 */
/*
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
*/
public class emc_preguntas_instrumento  extends SugarRecord<emc_preguntas_instrumento> {
    String ins_idinstrumento;
    String tem_idtema;
    String pre_idpregunta;
    String ixp_orden;
    String pre_activa;
    String pre_tipopregunta;
    String pre_tipocampo;
    String val_idvalidador_dato;
    String usu_usuariocreacion;
    String usu_fechacreacion;
    String pre_depende;
    String val_diferenciado;
    String val_preg_general;
    String val_pregunta_general;
    String val_respuesta_multiple;
    String val_todohogar;
    String val_diferenciadonu;
    String r_defualt;

    public emc_preguntas_instrumento() {
    }

    public emc_preguntas_instrumento(String ins_idinstrumento, String tem_idtema, String pre_idpregunta, String ixp_orden, String pre_activa, String pre_tipopregunta, String pre_tipocampo, String val_idvalidador_dato, String usu_usuariocreacion, String usu_fechacreacion, String pre_depende, String val_diferenciado, String val_preg_general, String val_pregunta_general, String val_respuesta_multiple, String val_todohogar, String val_diferenciadonu, String r_defualt) {
        this.ins_idinstrumento = ins_idinstrumento;
        this.tem_idtema = tem_idtema;
        this.pre_idpregunta = pre_idpregunta;
        this.ixp_orden = ixp_orden;
        this.pre_activa = pre_activa;
        this.pre_tipopregunta = pre_tipopregunta;
        this.pre_tipocampo = pre_tipocampo;
        this.val_idvalidador_dato = val_idvalidador_dato;
        this.usu_usuariocreacion = usu_usuariocreacion;
        this.usu_fechacreacion = usu_fechacreacion;
        this.pre_depende = pre_depende;
        this.val_diferenciado = val_diferenciado;
        this.val_preg_general = val_preg_general;
        this.val_pregunta_general = val_pregunta_general;
        this.val_respuesta_multiple = val_respuesta_multiple;
        this.val_todohogar = val_todohogar;
        this.val_diferenciadonu = val_diferenciadonu;
        this.r_defualt = r_defualt;
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

    public String getPre_idpregunta() {
        return pre_idpregunta;
    }

    public void setPre_idpregunta(String pre_idpregunta) {
        this.pre_idpregunta = pre_idpregunta;
    }

    public String getIxp_orden() {
        return ixp_orden;
    }

    public void setIxp_orden(String ixp_orden) {
        this.ixp_orden = ixp_orden;
    }

    public String getPre_activa() {
        return pre_activa;
    }

    public void setPre_activa(String pre_activa) {
        this.pre_activa = pre_activa;
    }

    public String getPre_tipopregunta() {
        return pre_tipopregunta;
    }

    public void setPre_tipopregunta(String pre_tipopregunta) {
        this.pre_tipopregunta = pre_tipopregunta;
    }

    public String getPre_tipocampo() {
        return pre_tipocampo;
    }

    public void setPre_tipocampo(String pre_tipocampo) {
        this.pre_tipocampo = pre_tipocampo;
    }

    public String getVal_idvalidador_dato() {
        return val_idvalidador_dato;
    }

    public void setVal_idvalidador_dato(String val_idvalidador_dato) {
        this.val_idvalidador_dato = val_idvalidador_dato;
    }

    public String getUsu_usuariocreacion() {
        return usu_usuariocreacion;
    }

    public void setUsu_usuariocreacion(String usu_usuariocreacion) {
        this.usu_usuariocreacion = usu_usuariocreacion;
    }

    public String getUsu_fechacreacion() {
        return usu_fechacreacion;
    }

    public void setUsu_fechacreacion(String usu_fechacreacion) {
        this.usu_fechacreacion = usu_fechacreacion;
    }

    public String getPre_depende() {
        return pre_depende;
    }

    public void setPre_depende(String pre_depende) {
        this.pre_depende = pre_depende;
    }

    public String getVal_diferenciado() {
        return val_diferenciado;
    }

    public void setVal_diferenciado(String val_diferenciado) {
        this.val_diferenciado = val_diferenciado;
    }

    public String getVal_preg_general() {
        return val_preg_general;
    }

    public void setVal_preg_general(String val_preg_general) {
        this.val_preg_general = val_preg_general;
    }

    public String getVal_pregunta_general() {
        return val_pregunta_general;
    }

    public void setVal_pregunta_general(String val_pregunta_general) {
        this.val_pregunta_general = val_pregunta_general;
    }

    public String getVal_respuesta_multiple() {
        return val_respuesta_multiple;
    }

    public void setVal_respuesta_multiple(String val_respuesta_multiple) {
        this.val_respuesta_multiple = val_respuesta_multiple;
    }

    public String getVal_todohogar() {
        return val_todohogar;
    }

    public void setVal_todohogar(String val_todohogar) {
        this.val_todohogar = val_todohogar;
    }

    public String getVal_diferenciadonu() {
        return val_diferenciadonu;
    }

    public void setVal_diferenciadonu(String val_diferenciadonu) {
        this.val_diferenciadonu = val_diferenciadonu;
    }

    public String getR_defualt() {
        return r_defualt;
    }

    public void setR_defualt(String r_defualt) {
        this.r_defualt = r_defualt;
    }
}
