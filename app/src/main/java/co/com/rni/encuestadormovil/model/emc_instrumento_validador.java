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
public class emc_instrumento_validador extends SugarRecord<emc_instrumento_validador> {

    String val_idvalidador;
    String ins_idinstrumento;
    String pre_validador;
    String pre_valor;
    String pre_longcampo;
    String pre_validador_min;
    String pre_validador_max;

}
