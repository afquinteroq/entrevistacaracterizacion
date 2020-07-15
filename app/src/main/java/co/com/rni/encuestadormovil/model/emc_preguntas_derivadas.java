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
/*
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
*/
public class emc_preguntas_derivadas extends SugarRecord<emc_preguntas_derivadas> {

    String hog_codigo;
    String ins_idinstrumento;
    String pre_idpregunta;
    String per_idpersona;
    String guardado;
    Integer tem_idtema;
    Integer pre_idpreguntapadre;
    Integer contador;

    public emc_preguntas_derivadas() {
    }

    public emc_preguntas_derivadas(String hog_codigo, String ins_idinstrumento, String pre_idpregunta, String per_idpersona, String guardado, Integer tem_idtema, Integer pre_idpreguntapadre, Integer contador) {
        this.hog_codigo = hog_codigo;
        this.ins_idinstrumento = ins_idinstrumento;
        this.pre_idpregunta = pre_idpregunta;
        this.per_idpersona = per_idpersona;
        this.guardado = guardado;
        this.tem_idtema = tem_idtema;
        this.pre_idpreguntapadre = pre_idpreguntapadre;
        this.contador = contador;
    }

    public String getHog_codigo() {
        return hog_codigo;
    }

    public void setHog_codigo(String hog_codigo) {
        this.hog_codigo = hog_codigo;
    }

    public String getIns_idinstrumento() {
        return ins_idinstrumento;
    }

    public void setIns_idinstrumento(String ins_idinstrumento) {
        this.ins_idinstrumento = ins_idinstrumento;
    }

    public String getPre_idpregunta() {
        return pre_idpregunta;
    }

    public void setPre_idpregunta(String pre_idpregunta) {
        this.pre_idpregunta = pre_idpregunta;
    }

    public String getPer_idpersona() {
        return per_idpersona;
    }

    public void setPer_idpersona(String per_idpersona) {
        this.per_idpersona = per_idpersona;
    }

    public String getGuardado() {
        return guardado;
    }

    public void setGuardado(String guardado) {
        this.guardado = guardado;
    }

    public Integer getTem_idtema() {
        return tem_idtema;
    }

    public void setTem_idtema(Integer tem_idtema) {
        this.tem_idtema = tem_idtema;
    }

    public Integer getPre_idpreguntapadre() {
        return pre_idpreguntapadre;
    }

    public void setPre_idpreguntapadre(Integer pre_idpreguntapadre) {
        this.pre_idpreguntapadre = pre_idpreguntapadre;
    }

    public Integer getContador() {
        return contador;
    }

    public void setContador(Integer contador) {
        this.contador = contador;
    }
}
