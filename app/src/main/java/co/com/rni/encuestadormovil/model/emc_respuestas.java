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

}
