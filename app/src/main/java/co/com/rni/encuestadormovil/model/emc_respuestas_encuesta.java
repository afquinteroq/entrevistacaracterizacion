package co.com.rni.encuestadormovil.model;

import com.orm.SugarRecord;

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
public class emc_respuestas_encuesta extends SugarRecord<emc_respuestas_encuesta> {

    Integer rxp_idrespuestapersona;
    String hog_codigo;
    String per_idpersona;
    Integer res_idrespuesta;
    String rxp_tipopregunta;
    String usu_usuariocreacion;
    String usu_fechacreacion;
    String ins_idinstrumento;
    String rxp_textorespuesta;

    public emc_respuestas_encuesta() {
    }

    public emc_respuestas_encuesta(Integer rxp_idrespuestapersona, String hog_codigo, String per_idpersona, Integer res_idrespuesta, String rxp_tipopregunta, String usu_usuariocreacion, String usu_fechacreacion, String ins_idinstrumento, String rxp_textorespuesta) {
        this.rxp_idrespuestapersona = rxp_idrespuestapersona;
        this.hog_codigo = hog_codigo;
        this.per_idpersona = per_idpersona;
        this.res_idrespuesta = res_idrespuesta;
        this.rxp_tipopregunta = rxp_tipopregunta;
        this.usu_usuariocreacion = usu_usuariocreacion;
        this.usu_fechacreacion = usu_fechacreacion;
        this.ins_idinstrumento = ins_idinstrumento;
        this.rxp_textorespuesta = rxp_textorespuesta;
    }

    public Integer getRxp_idrespuestapersona() {
        return rxp_idrespuestapersona;
    }

    public void setRxp_idrespuestapersona(Integer rxp_idrespuestapersona) {
        this.rxp_idrespuestapersona = rxp_idrespuestapersona;
    }

    public String getHog_codigo() {
        return hog_codigo;
    }

    public void setHog_codigo(String hog_codigo) {
        this.hog_codigo = hog_codigo;
    }

    public String getPer_idpersona() {
        return per_idpersona;
    }

    public void setPer_idpersona(String per_idpersona) {
        this.per_idpersona = per_idpersona;
    }

    public Integer getRes_idrespuesta() {
        return res_idrespuesta;
    }

    public void setRes_idrespuesta(Integer res_idrespuesta) {
        this.res_idrespuesta = res_idrespuesta;
    }

    public String getRxp_tipopregunta() {
        return rxp_tipopregunta;
    }

    public void setRxp_tipopregunta(String rxp_tipopregunta) {
        this.rxp_tipopregunta = rxp_tipopregunta;
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

    public String getIns_idinstrumento() {
        return ins_idinstrumento;
    }

    public void setIns_idinstrumento(String ins_idinstrumento) {
        this.ins_idinstrumento = ins_idinstrumento;
    }

    public String getRxp_textorespuesta() {
        return rxp_textorespuesta;
    }

    public void setRxp_textorespuesta(String rxp_textorespuesta) {
        this.rxp_textorespuesta = rxp_textorespuesta;
    }
}
