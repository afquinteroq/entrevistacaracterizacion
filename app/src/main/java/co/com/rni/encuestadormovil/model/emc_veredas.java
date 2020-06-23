package co.com.rni.encuestadormovil.model;

import com.orm.SugarRecord;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by ASUS on 20/08/2018.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class emc_veredas extends SugarRecord<emc_veredas> {

    String cod_dpto;
    String dane_mpio;
    String coddane_ver;
    String nom_dep;
    String nom_mpio;
    String nom_ver;

}
