package co.com.rni.encuestadormovil.model;

import com.orm.SugarRecord;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/*
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
*/
public class emc_gic_archivo_soportes extends SugarRecord<emc_hogares> {

    private String id_temporal;
    private String hog_codigo;
    private String arc_url;
    private String usu_usuariocreacion;
    private String usu_fechacreacion ;
    private String tipo_persona;

    public emc_gic_archivo_soportes() {
    }

    public emc_gic_archivo_soportes(String id_temporal, String hog_codigo, String arc_url, String usu_usuariocreacion, String usu_fechacreacion, String tipo_persona) {
        this.id_temporal = id_temporal;
        this.hog_codigo = hog_codigo;
        this.arc_url = arc_url;
        this.usu_usuariocreacion = usu_usuariocreacion;
        this.usu_fechacreacion = usu_fechacreacion;
        this.tipo_persona = tipo_persona;
    }

    public String getId_temporal() {
        return id_temporal;
    }

    public void setId_temporal(String id_temporal) {
        this.id_temporal = id_temporal;
    }

    public String getHog_codigo() {
        return hog_codigo;
    }

    public void setHog_codigo(String hog_codigo) {
        this.hog_codigo = hog_codigo;
    }

    public String getArc_url() {
        return arc_url;
    }

    public void setArc_url(String arc_url) {
        this.arc_url = arc_url;
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

    public String getTipo_persona() {
        return tipo_persona;
    }

    public void setTipo_persona(String tipo_persona) {
        this.tipo_persona = tipo_persona;
    }
}
