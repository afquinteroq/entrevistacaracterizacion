package co.com.rni.encuestadormovil.model;

import com.orm.SugarRecord;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by ASUS on 03/08/2018.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class emc_comunidadesnegras  extends SugarRecord<emc_comunidadesnegras> {

    String idcomunidad;
    String nombrecomunidad;

}
