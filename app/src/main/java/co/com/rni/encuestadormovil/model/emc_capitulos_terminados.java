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
public class emc_capitulos_terminados extends SugarRecord<emc_capitulos_terminados> {

    String hog_codigo;
    Integer tem_idtema;
    String usu_usuariocreacion;
    String usu_fechacreacion;

    /*
    public emc_capitulos_terminados() {
    }

    public emc_capitulos_terminados(String hog_codigo, Integer tem_idtema, String usu_usuariocreacion, String usu_fechacreacion) {
        this.hog_codigo = hog_codigo;
        this.tem_idtema = tem_idtema;
        this.usu_usuariocreacion = usu_usuariocreacion;
        this.usu_fechacreacion = usu_fechacreacion;
    }

    public String getHog_codigo() {
        return hog_codigo;
    }

    public void setHog_codigo(String hog_codigo) {
        this.hog_codigo = hog_codigo;
    }

    public Integer getTem_idtema() {
        return tem_idtema;
    }

    public void setTem_idtema(Integer tem_idtema) {
        this.tem_idtema = tem_idtema;
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
    */
}
