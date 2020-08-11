package co.com.rni.encuestadormovil.model;

import com.orm.SugarRecord;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by javierperez on 16/12/15.
 */
/*
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
*/
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

    public emc_respuestas_instrumento() {
    }

    public emc_respuestas_instrumento(String ins_idinstrumento, String res_idrespuesta, String res_padre, String pre_validador, String pre_longcampo, String pre_validador_min, String pre_validador_max, String res_ordenrespuesta, String usu_usuariocreacion, String usu_fechacreacion, String pre_campotex, String val_idvalidador, String val_idvalidador_def, String res_obligatorio, String res_habilita, String res_finaliza, String res_autocompletar, String res_respuestashbilitar, String validador_oridrespuesta, String tipo_tabla) {
        this.ins_idinstrumento = ins_idinstrumento;
        this.res_idrespuesta = res_idrespuesta;
        this.res_padre = res_padre;
        this.pre_validador = pre_validador;
        this.pre_longcampo = pre_longcampo;
        this.pre_validador_min = pre_validador_min;
        this.pre_validador_max = pre_validador_max;
        this.res_ordenrespuesta = res_ordenrespuesta;
        this.usu_usuariocreacion = usu_usuariocreacion;
        this.usu_fechacreacion = usu_fechacreacion;
        this.pre_campotex = pre_campotex;
        this.val_idvalidador = val_idvalidador;
        this.val_idvalidador_def = val_idvalidador_def;
        this.res_obligatorio = res_obligatorio;
        this.res_habilita = res_habilita;
        this.res_finaliza = res_finaliza;
        this.res_autocompletar = res_autocompletar;
        this.res_respuestashbilitar = res_respuestashbilitar;
        this.validador_oridrespuesta = validador_oridrespuesta;
        this.tipo_tabla = tipo_tabla;
    }

    public String getIns_idinstrumento() {
        return ins_idinstrumento;
    }

    public void setIns_idinstrumento(String ins_idinstrumento) {
        this.ins_idinstrumento = ins_idinstrumento;
    }

    public String getRes_idrespuesta() {
        return res_idrespuesta;
    }

    public void setRes_idrespuesta(String res_idrespuesta) {
        this.res_idrespuesta = res_idrespuesta;
    }

    public String getRes_padre() {
        return res_padre;
    }

    public void setRes_padre(String res_padre) {
        this.res_padre = res_padre;
    }

    public String getPre_validador() {
        return pre_validador;
    }

    public void setPre_validador(String pre_validador) {
        this.pre_validador = pre_validador;
    }

    public String getPre_longcampo() {
        return pre_longcampo;
    }

    public void setPre_longcampo(String pre_longcampo) {
        this.pre_longcampo = pre_longcampo;
    }

    public String getPre_validador_min() {
        return pre_validador_min;
    }

    public void setPre_validador_min(String pre_validador_min) {
        this.pre_validador_min = pre_validador_min;
    }

    public String getPre_validador_max() {
        return pre_validador_max;
    }

    public void setPre_validador_max(String pre_validador_max) {
        this.pre_validador_max = pre_validador_max;
    }

    public String getRes_ordenrespuesta() {
        return res_ordenrespuesta;
    }

    public void setRes_ordenrespuesta(String res_ordenrespuesta) {
        this.res_ordenrespuesta = res_ordenrespuesta;
    }

    public String getUsu_usuariocreacion() {
        return usu_usuariocreacion;
    }

    public void setUsu_usuariocreacion(String usu_usuariocreacion) {
        this.usu_usuariocreacion = usu_usuariocreacion;
    }

    public String getUsu_fechacreacion() {
        return usu_fechacreacion;
    }

    public void setUsu_fechacreacion(String usu_fechacreacion) {
        this.usu_fechacreacion = usu_fechacreacion;
    }

    public String getPre_campotex() {
        return pre_campotex;
    }

    public void setPre_campotex(String pre_campotex) {
        this.pre_campotex = pre_campotex;
    }

    public String getVal_idvalidador() {
        return val_idvalidador;
    }

    public void setVal_idvalidador(String val_idvalidador) {
        this.val_idvalidador = val_idvalidador;
    }

    public String getVal_idvalidador_def() {
        return val_idvalidador_def;
    }

    public void setVal_idvalidador_def(String val_idvalidador_def) {
        this.val_idvalidador_def = val_idvalidador_def;
    }

    public String getRes_obligatorio() {
        return res_obligatorio;
    }

    public void setRes_obligatorio(String res_obligatorio) {
        this.res_obligatorio = res_obligatorio;
    }

    public String getRes_habilita() {
        return res_habilita;
    }

    public void setRes_habilita(String res_habilita) {
        this.res_habilita = res_habilita;
    }

    public String getRes_finaliza() {
        return res_finaliza;
    }

    public void setRes_finaliza(String res_finaliza) {
        this.res_finaliza = res_finaliza;
    }

    public String getRes_autocompletar() {
        return res_autocompletar;
    }

    public void setRes_autocompletar(String res_autocompletar) {
        this.res_autocompletar = res_autocompletar;
    }

    public String getRes_respuestashbilitar() {
        return res_respuestashbilitar;
    }

    public void setRes_respuestashbilitar(String res_respuestashbilitar) {
        this.res_respuestashbilitar = res_respuestashbilitar;
    }

    public String getValidador_oridrespuesta() {
        return validador_oridrespuesta;
    }

    public void setValidador_oridrespuesta(String validador_oridrespuesta) {
        this.validador_oridrespuesta = validador_oridrespuesta;
    }

    public String getTipo_tabla() {
        return tipo_tabla;
    }

    public void setTipo_tabla(String tipo_tabla) {
        this.tipo_tabla = tipo_tabla;
    }
}
