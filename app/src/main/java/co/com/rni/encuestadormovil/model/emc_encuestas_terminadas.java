package co.com.rni.encuestadormovil.model;

import com.orm.SugarRecord;

import java.sql.Date;

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
public class emc_encuestas_terminadas extends SugarRecord<emc_encuestas_terminadas> {

    private String hog_codigo;
    private String usu_usuariocreacion;
    private String usu_fechacreacion;

}
