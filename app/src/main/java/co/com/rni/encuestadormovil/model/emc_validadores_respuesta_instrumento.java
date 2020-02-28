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
public class emc_validadores_respuesta_instrumento extends SugarRecord<emc_validadores_respuesta_instrumento> {
    Integer RES_IDRESPUESTA;
    Integer INS_IDINSTRUMENTO;
    Integer TEM_IDTEMA;
    Integer VAL_IDVALIDADOR;
    Integer VAL_IDVALIDADOR_PERS;

    /*
    public emc_validadores_respuesta_instrumento() {
    }

    public emc_validadores_respuesta_instrumento(Integer RES_IDRESPUESTA, Integer INS_IDINSTRUMENTO, Integer TEM_IDTEMA, Integer VAL_IDVALIDADOR, Integer VAL_IDVALIDADOR_PERS) {
        this.RES_IDRESPUESTA = RES_IDRESPUESTA;
        this.INS_IDINSTRUMENTO = INS_IDINSTRUMENTO;
        this.TEM_IDTEMA = TEM_IDTEMA;
        this.VAL_IDVALIDADOR = VAL_IDVALIDADOR;
        this.VAL_IDVALIDADOR_PERS = VAL_IDVALIDADOR_PERS;
    }

    public Integer getRES_IDRESPUESTA() {
        return RES_IDRESPUESTA;
    }

    public void setRES_IDRESPUESTA(Integer RES_IDRESPUESTA) {
        this.RES_IDRESPUESTA = RES_IDRESPUESTA;
    }

    public Integer getINS_IDINSTRUMENTO() {
        return INS_IDINSTRUMENTO;
    }

    public void setINS_IDINSTRUMENTO(Integer INS_IDINSTRUMENTO) {
        this.INS_IDINSTRUMENTO = INS_IDINSTRUMENTO;
    }

    public Integer getTEM_IDTEMA() {
        return TEM_IDTEMA;
    }

    public void setTEM_IDTEMA(Integer TEM_IDTEMA) {
        this.TEM_IDTEMA = TEM_IDTEMA;
    }

    public Integer getVAL_IDVALIDADOR() {
        return VAL_IDVALIDADOR;
    }

    public void setVAL_IDVALIDADOR(Integer VAL_IDVALIDADOR) {
        this.VAL_IDVALIDADOR = VAL_IDVALIDADOR;
    }

    public Integer getVAL_IDVALIDADOR_PERS() {
        return VAL_IDVALIDADOR_PERS;
    }

    public void setVAL_IDVALIDADOR_PERS(Integer VAL_IDVALIDADOR_PERS) {
        this.VAL_IDVALIDADOR_PERS = VAL_IDVALIDADOR_PERS;
    }
    */
}
