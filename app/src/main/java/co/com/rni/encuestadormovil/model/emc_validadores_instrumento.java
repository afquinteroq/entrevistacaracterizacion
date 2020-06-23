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
public class emc_validadores_instrumento extends SugarRecord<emc_validadores_instrumento> {

    String pre_idpregunta;
    String ins_idinstrumento;
    String tem_idtema;
    String val_idvalidador;
    String val_idvalidador_pers;

}
