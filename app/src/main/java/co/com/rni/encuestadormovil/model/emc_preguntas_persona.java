package co.com.rni.encuestadormovil.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by javierperez on 17/02/16.
 */
/*
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
*/
public class emc_preguntas_persona {

    private String pre_idpregunta;
    private String pre_tipopregunta;
    private String pre_tipocampo;
    private String hog_codigo;
    private String ixp_orden;
    private String per_idpersona;
    private String validacion_persona;
    private String ordenprioridad;

    public emc_preguntas_persona() {
    }

    public emc_preguntas_persona(String pre_idpregunta, String pre_tipopregunta, String pre_tipocampo, String hog_codigo, String ixp_orden, String per_idpersona, String validacion_persona, String ordenprioridad) {
        this.pre_idpregunta = pre_idpregunta;
        this.pre_tipopregunta = pre_tipopregunta;
        this.pre_tipocampo = pre_tipocampo;
        this.hog_codigo = hog_codigo;
        this.ixp_orden = ixp_orden;
        this.per_idpersona = per_idpersona;
        this.validacion_persona = validacion_persona;
        this.ordenprioridad = ordenprioridad;
    }

    public String getPre_idpregunta() {
        return pre_idpregunta;
    }

    public void setPre_idpregunta(String pre_idpregunta) {
        this.pre_idpregunta = pre_idpregunta;
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

    public String getHog_codigo() {
        return hog_codigo;
    }

    public void setHog_codigo(String hog_codigo) {
        this.hog_codigo = hog_codigo;
    }

    public String getIxp_orden() {
        return ixp_orden;
    }

    public void setIxp_orden(String ixp_orden) {
        this.ixp_orden = ixp_orden;
    }

    public String getPer_idpersona() {
        return per_idpersona;
    }

    public void setPer_idpersona(String per_idpersona) {
        this.per_idpersona = per_idpersona;
    }

    public String getValidacion_persona() {
        return validacion_persona;
    }

    public void setValidacion_persona(String validacion_persona) {
        this.validacion_persona = validacion_persona;
    }

    public String getOrdenprioridad() {
        return ordenprioridad;
    }

    public void setOrdenprioridad(String ordenprioridad) {
        this.ordenprioridad = ordenprioridad;
    }
}
