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
public class emc_respuestas_encuesta extends SugarRecord<emc_respuestas_encuesta> {

    Integer rxp_idrespuestapersona;
    String hog_codigo;
    String per_idpersona;
    Integer res_idrespuesta;
    String rxp_tipopregunta;
    String usu_usuariocreacion;
    String usu_fechacreacion;
    String ins_idinstrumento;
    String rxp_textorespuesta;

}
