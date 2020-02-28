package co.com.rni.encuestadormovil.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class emc_personas_encuestas_no_sugar {
    private String dane;
    private String municipio;
    private String barrio;
    private String id_entrevista;
    private String inicio_encuesta;
    private String fin_encuesta;
    private String id_personas;
    private String ESTADO_RUV;
    private String NOMBRE_1_RUV;
    private String NOMBRE_2_RUV;
    private String APELLIDO_1_RUV;
    private String APELLIDO_2_RUV;
    private String NUM_DOCU_RUV;
    private String NUMERO_DOCU_ENCUESTA;

}
