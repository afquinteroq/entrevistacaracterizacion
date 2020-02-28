package co.com.rni.encuestadormovil.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by javierperez on 15/11/15.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class opcionMenu {
    private String icono;
    private String texto;

    /*
    public opcionMenu(String icono_, String texto_){
        this.icono = icono_;
        this.texto = texto_;
    }


    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
    */
}
