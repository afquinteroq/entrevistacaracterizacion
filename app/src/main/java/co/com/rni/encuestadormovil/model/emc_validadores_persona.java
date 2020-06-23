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
public class emc_validadores_persona extends SugarRecord<emc_validadores_persona> {

    String ins_idinstrumento;
    String per_idpersona;
    String val_idvalidador;
    String pre_valor;
    String hog_codigo;
    String comodin;
    String fechahecho;
}
