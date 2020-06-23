package co.com.rni.encuestadormovil.model;

import com.orm.SugarRecord;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by javierperez on 4/02/16.
 */


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class emc_personas extends SugarRecord<emc_personas> {

    String per_idpersona;
    String per_primernombre;
    String per_segundonombre;
    String per_primerapellido;
    String per_segundoapellido;
    String per_fechanacimiento;
    String per_tipodoc;
    String usu_usuariocreacion;
    String usu_fechacreacion;
    String per_numerodoc;
    String per_relacion;
    String per_iddeclaracion;
    String per_idepersonafuente;
    String per_tipovictima;
    String per_idsiniestro;
    String per_fuente;
    String per_estado;


}
