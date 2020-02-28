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
public class emc_preguntas extends SugarRecord<emc_preguntas> {

    String pre_idpregunta;
    String pre_pregunta;
    String pre_observacion;
    String pre_activa;
    String usu_usuariocreacion;
    String usu_fechacreacion;

    /*
    public emc_preguntas() {
    }

    public emc_preguntas(String pre_idpregunta, String pre_pregunta, String pre_observacion,String pre_activa, String usu_usuariocreacion, String usu_fechacreacion) {
        this.pre_idpregunta = pre_idpregunta;
        this.pre_pregunta = pre_pregunta;
        this.pre_observacion = pre_observacion;
        this.pre_activa = pre_activa;
        this.usu_usuariocreacion = usu_usuariocreacion;
        this.usu_fechacreacion = usu_fechacreacion;
    }

    public String getPre_idpregunta() {
        return pre_idpregunta;
    }

    public void setPre_idpregunta(String pre_idpregunta) {
        this.pre_idpregunta = pre_idpregunta;
    }

    public String getPre_pregunta() {
        return pre_pregunta;
    }

    public void setPre_pregunta(String pre_pregunta) {
        this.pre_pregunta = pre_pregunta;
    }

    public String getPre_activa() {
        return pre_activa;
    }

    public void setPre_activa(String pre_activa) {
        this.pre_activa = pre_activa;
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


    public String getPre_observacion() {
        return pre_observacion;
    }

    public void setPre_observacion(String pre_observacion) {
        this.pre_observacion = pre_observacion;
    }
    */
}
