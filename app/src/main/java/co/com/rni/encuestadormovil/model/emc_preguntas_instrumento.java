package co.com.rni.encuestadormovil.model;

import com.orm.SugarRecord;

import java.sql.Date;

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
public class emc_preguntas_instrumento  extends SugarRecord<emc_preguntas_instrumento> {
    String ins_idinstrumento;
    String tem_idtema;
    String pre_idpregunta;
    String ixp_orden;
    String pre_activa;
    String pre_tipopregunta;
    String pre_tipocampo;
    String val_idvalidador_dato;
    String usu_usuariocreacion;
    String usu_fechacreacion;
    String pre_depende;
    String val_diferenciado;
    String val_preg_general;
    String val_pregunta_general;
    String val_respuesta_multiple;
    String val_todohogar;
    String val_diferenciadonu;
    String r_defualt;

}
