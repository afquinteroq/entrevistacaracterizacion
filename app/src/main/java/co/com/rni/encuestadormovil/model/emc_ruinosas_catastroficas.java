package co.com.rni.encuestadormovil.model;

import com.orm.SugarRecord;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by ASUS on 23/07/2018.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class emc_ruinosas_catastroficas extends SugarRecord<emc_ruinosas_catastroficas> {

    String idEnfermedad;
    String nombreEnfermedad;

}
