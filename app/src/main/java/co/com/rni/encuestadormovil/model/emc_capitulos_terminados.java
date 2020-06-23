package co.com.rni.encuestadormovil.model;

import com.orm.SugarRecord;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by javierperez on 15/12/15.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class emc_capitulos_terminados extends SugarRecord<emc_capitulos_terminados> {

    String hog_codigo;
    Integer tem_idtema;
    String usu_usuariocreacion;
    String usu_fechacreacion;
}
