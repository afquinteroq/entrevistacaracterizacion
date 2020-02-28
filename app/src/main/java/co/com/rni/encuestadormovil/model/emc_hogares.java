package co.com.rni.encuestadormovil.model;

import com.orm.SugarRecord;

import java.sql.Date;

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
public class emc_hogares extends SugarRecord<emc_hogares> {

    private String hog_codigo;
    private String usu_usuariocreacion;
    private String usu_fechacreacion;
    private String hog_tipo;
    public String estado;

    /*
    public String getHog_codigo() {
        return hog_codigo;
    }

    public void setHog_codigo(String hog_codigo) {
        this.hog_codigo = hog_codigo;
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

    public String getHog_tipo() {
        return hog_tipo;
    }

    public void setHog_tipo(String hog_tipo) {
        this.hog_tipo = hog_tipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public emc_hogares() {
    }
    */
}
