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
public class emc_preguntas_derivadas extends SugarRecord<emc_preguntas_derivadas> {

    String hog_codigo;
    String ins_idinstrumento;
    String pre_idpregunta;
    String per_idpersona;
    String guardado;
    Integer tem_idtema;
    Integer pre_idpreguntapadre;
    Integer contador;
}
