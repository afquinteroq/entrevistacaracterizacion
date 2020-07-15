package co.com.rni.encuestadormovil.model;

import com.orm.SugarRecord;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by javierperez on 15/12/15.
 */
/*@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
*/
public class emc_calculos extends SugarRecord<emc_calculos> {

    private Integer valor;

    public emc_calculos() {
    }

    public emc_calculos(Integer valor) {
        this.valor = valor;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }
}
