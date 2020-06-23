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
public class emc_pregunta_hijos extends SugarRecord<emc_pregunta_hijos> {

    String res_idrespuesta;
    String pre_idpregunta;
    String pre_depende;
    String val_todohogar;

}
