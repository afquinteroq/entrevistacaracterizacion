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

}
