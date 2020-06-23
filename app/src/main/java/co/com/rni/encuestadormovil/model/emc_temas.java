package co.com.rni.encuestadormovil.model;

import com.orm.SugarRecord;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by javierperez on 12/12/15.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class emc_temas  extends SugarRecord<emc_temas> {

    public Integer tem_idtema;
    public String tem_nombretema;
    public String tem_activo;
    public String usu_usuariocreacion;
    public String usu_fechacreacion;
    public Integer tem_orden;



}
