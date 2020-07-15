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
public class emc_validadores_respuesta extends SugarRecord<emc_validadores_respuesta> {
    String idpregunta;
    String idrespuesta;
    String condicion;

    public emc_validadores_respuesta() {
    }

    public emc_validadores_respuesta(String idpregunta, String idrespuesta, String condicion) {
        this.idpregunta = idpregunta;
        this.idrespuesta = idrespuesta;
        this.condicion = condicion;
    }

    public String getIdpregunta() {
        return idpregunta;
    }

    public void setIdpregunta(String idpregunta) {
        this.idpregunta = idpregunta;
    }

    public String getIdrespuesta() {
        return idrespuesta;
    }

    public void setIdrespuesta(String idrespuesta) {
        this.idrespuesta = idrespuesta;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }
}
