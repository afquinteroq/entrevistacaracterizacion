package co.com.rni.encuestadormovil.model;

import com.orm.SugarRecord;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by javierperez on 12/12/15.
 */
/*
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
*/
public class emc_temas  extends SugarRecord<emc_temas> {

    public Integer tem_idtema;
    public String tem_nombretema;
    public String tem_activo;
    public String usu_usuariocreacion;
    public String usu_fechacreacion;
    public Integer tem_orden;


    public emc_temas() {
    }

    public emc_temas(Integer tem_idtema, String tem_nombretema, String tem_activo, String usu_usuariocreacion, String usu_fechacreacion, Integer tem_orden) {
        this.tem_idtema = tem_idtema;
        this.tem_nombretema = tem_nombretema;
        this.tem_activo = tem_activo;
        this.usu_usuariocreacion = usu_usuariocreacion;
        this.usu_fechacreacion = usu_fechacreacion;
        this.tem_orden = tem_orden;
    }

    public Integer getTem_idtema() {
        return tem_idtema;
    }

    public void setTem_idtema(Integer tem_idtema) {
        this.tem_idtema = tem_idtema;
    }

    public String getTem_nombretema() {
        return tem_nombretema;
    }

    public void setTem_nombretema(String tem_nombretema) {
        this.tem_nombretema = tem_nombretema;
    }

    public String getTem_activo() {
        return tem_activo;
    }

    public void setTem_activo(String tem_activo) {
        this.tem_activo = tem_activo;
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

    public Integer getTem_orden() {
        return tem_orden;
    }

    public void setTem_orden(Integer tem_orden) {
        this.tem_orden = tem_orden;
    }
}
