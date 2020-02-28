package co.com.rni.encuestadormovil.model;

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
public class item_resumen_encuestas {

    private String usuario;
    private String estado;
    private String total;

    /*
    public item_resumen_encuestas() {

    }

    public item_resumen_encuestas(String estado, String total) {
        this.estado = estado;
        this.total = total;
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
