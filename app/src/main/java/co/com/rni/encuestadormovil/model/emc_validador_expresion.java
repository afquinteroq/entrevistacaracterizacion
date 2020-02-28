package co.com.rni.encuestadormovil.model;

import com.orm.SugarRecord;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by ASUS on 10/11/2016.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class emc_validador_expresion extends SugarRecord<emc_validador_expresion> {

    String valor;
    String expresion;
    String pre_idpregunta;
    /*
    public emc_validador_expresion()
    {

    }

    public emc_validador_expresion(String valor, String expresion, String pre_idpregunta) {
        this.valor = valor;
        this.expresion = expresion;
        this.pre_idpregunta = pre_idpregunta;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getExpresion() {
        return expresion;
    }

    public void setExpresion(String expresion) {
        this.expresion = expresion;
    }

    public String getPre_idpregunta() {
        return pre_idpregunta;
    }

    public void setPre_idpregunta(String pre_idpregunta) {
        this.pre_idpregunta = pre_idpregunta;
    }
    */
}
