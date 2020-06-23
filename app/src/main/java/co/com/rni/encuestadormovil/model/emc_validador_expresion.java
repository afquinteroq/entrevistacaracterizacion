package co.com.rni.encuestadormovil.model;

import com.orm.SugarRecord;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by ASUS on 10/11/2016.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class emc_validador_expresion extends SugarRecord<emc_validador_expresion> {

    String valor;
    String expresion;
    String pre_idpregunta;

}
