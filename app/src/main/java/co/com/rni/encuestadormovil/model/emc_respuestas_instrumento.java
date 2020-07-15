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
public class emc_respuestas_instrumento extends SugarRecord<emc_respuestas_instrumento> {

    String ins_idinstrumento;
    String res_idrespuesta;
    String res_padre;
    String pre_validador;
    String pre_longcampo;
    String pre_validador_min;
    String pre_validador_max;
    String res_ordenrespuesta;
    String usu_usuariocreacion;
    String usu_fechacreacion;
    String pre_campotex;
    String val_idvalidador;
    String val_idvalidador_def;
    String res_obligatorio;
    String res_habilita;
    String res_finaliza;
    String res_autocompletar;
    String res_respuestashbilitar;
    String validador_oridrespuesta;
    String tipo_tabla;

}
