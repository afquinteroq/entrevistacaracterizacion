package co.com.rni.encuestadormovil.model;

import com.orm.SugarRecord;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by ASUS on 8/05/2017.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class emc_resumen_encuestas_x_usuario extends SugarRecord<emc_resumen_encuestas_x_usuario> {

    private String usuario;
    private String estado;
    private String total;

    /*
    public emc_resumen_encuestas_x_usuario() {
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
    */
}
