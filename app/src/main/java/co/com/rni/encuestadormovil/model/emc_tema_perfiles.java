package co.com.rni.encuestadormovil.model;

import com.orm.SugarRecord;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by ASUS on 7/10/2016.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class emc_tema_perfiles  extends SugarRecord<emc_tema_perfiles> {
    String tem_idtema;
    String per_idperfil;


}
