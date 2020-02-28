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
public class emc_respuestas extends SugarRecord<emc_respuestas> {

    String res_idrespuesta;
    String pre_idpregunta;
    String res_respuesta;
    String res_activa;
    String usu_usuariocreacion;
    String usu_fechacreacion;
    /*
    public emc_respuestas(String res_idrespuesta, String pre_idpregunta, String res_respuesta, String res_activa, String usu_usuariocreacion, String usu_fechacreacion) {
        this.res_idrespuesta = res_idrespuesta;
        this.pre_idpregunta = pre_idpregunta;
        this.res_respuesta = res_respuesta;
        this.res_activa = res_activa;
        this.usu_usuariocreacion = usu_usuariocreacion;
        this.usu_fechacreacion = usu_fechacreacion;
    }

    public emc_respuestas() {
    }

    public String getRes_idrespuesta() {
        return res_idrespuesta;
    }

    public void setRes_idrespuesta(String res_idrespuesta) {
        this.res_idrespuesta = res_idrespuesta;
    }

    public String getPre_idpregunta() {
        return pre_idpregunta;
    }

    public void setPre_idpregunta(String pre_idpregunta) {
        this.pre_idpregunta = pre_idpregunta;
    }

    public String getRes_respuesta() {
        return res_respuesta;
    }

    public void setRes_respuesta(String res_respuesta) {
        this.res_respuesta = res_respuesta;
    }

    public String getRes_activa() {
        return res_activa;
    }

    public void setRes_activa(String res_activa) {
        this.res_activa = res_activa;
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
