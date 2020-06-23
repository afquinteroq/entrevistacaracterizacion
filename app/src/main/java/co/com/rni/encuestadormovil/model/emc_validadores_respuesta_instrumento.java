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
public class emc_validadores_respuesta_instrumento extends SugarRecord<emc_validadores_respuesta_instrumento> {
    Integer RES_IDRESPUESTA;
    Integer INS_IDINSTRUMENTO;
    Integer TEM_IDTEMA;
    Integer VAL_IDVALIDADOR;
    Integer VAL_IDVALIDADOR_PERS;

}
