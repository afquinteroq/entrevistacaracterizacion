package co.com.rni.encuestadormovil.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by javierperez on 17/02/16.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class emc_preguntas_persona {

    private String pre_idpregunta;
    private String pre_tipopregunta;
    private String pre_tipocampo;
    private String hog_codigo;
    private String ixp_orden;
    private String per_idpersona;
    private String validacion_persona;
    private String ordenprioridad;

}
