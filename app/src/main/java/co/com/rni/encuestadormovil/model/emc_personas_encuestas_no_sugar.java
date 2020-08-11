package co.com.rni.encuestadormovil.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/*
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
*/
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

    public emc_personas_encuestas_no_sugar() {
    }

    public emc_personas_encuestas_no_sugar(String dane, String municipio, String barrio, String id_entrevista, String inicio_encuesta, String fin_encuesta, String id_personas, String ESTADO_RUV, String NOMBRE_1_RUV, String NOMBRE_2_RUV, String APELLIDO_1_RUV, String APELLIDO_2_RUV, String NUM_DOCU_RUV, String NUMERO_DOCU_ENCUESTA) {
        this.dane = dane;
        this.municipio = municipio;
        this.barrio = barrio;
        this.id_entrevista = id_entrevista;
        this.inicio_encuesta = inicio_encuesta;
        this.fin_encuesta = fin_encuesta;
        this.id_personas = id_personas;
        this.ESTADO_RUV = ESTADO_RUV;
        this.NOMBRE_1_RUV = NOMBRE_1_RUV;
        this.NOMBRE_2_RUV = NOMBRE_2_RUV;
        this.APELLIDO_1_RUV = APELLIDO_1_RUV;
        this.APELLIDO_2_RUV = APELLIDO_2_RUV;
        this.NUM_DOCU_RUV = NUM_DOCU_RUV;
        this.NUMERO_DOCU_ENCUESTA = NUMERO_DOCU_ENCUESTA;
    }

    public String getDane() {
        return dane;
    }

    public void setDane(String dane) {
        this.dane = dane;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getId_entrevista() {
        return id_entrevista;
    }

    public void setId_entrevista(String id_entrevista) {
        this.id_entrevista = id_entrevista;
    }

    public String getInicio_encuesta() {
        return inicio_encuesta;
    }

    public void setInicio_encuesta(String inicio_encuesta) {
        this.inicio_encuesta = inicio_encuesta;
    }

    public String getFin_encuesta() {
        return fin_encuesta;
    }

    public void setFin_encuesta(String fin_encuesta) {
        this.fin_encuesta = fin_encuesta;
    }

    public String getId_personas() {
        return id_personas;
    }

    public void setId_personas(String id_personas) {
        this.id_personas = id_personas;
    }

    public String getESTADO_RUV() {
        return ESTADO_RUV;
    }

    public void setESTADO_RUV(String ESTADO_RUV) {
        this.ESTADO_RUV = ESTADO_RUV;
    }

    public String getNOMBRE_1_RUV() {
        return NOMBRE_1_RUV;
    }

    public void setNOMBRE_1_RUV(String NOMBRE_1_RUV) {
        this.NOMBRE_1_RUV = NOMBRE_1_RUV;
    }

    public String getNOMBRE_2_RUV() {
        return NOMBRE_2_RUV;
    }

    public void setNOMBRE_2_RUV(String NOMBRE_2_RUV) {
        this.NOMBRE_2_RUV = NOMBRE_2_RUV;
    }

    public String getAPELLIDO_1_RUV() {
        return APELLIDO_1_RUV;
    }

    public void setAPELLIDO_1_RUV(String APELLIDO_1_RUV) {
        this.APELLIDO_1_RUV = APELLIDO_1_RUV;
    }

    public String getAPELLIDO_2_RUV() {
        return APELLIDO_2_RUV;
    }

    public void setAPELLIDO_2_RUV(String APELLIDO_2_RUV) {
        this.APELLIDO_2_RUV = APELLIDO_2_RUV;
    }

    public String getNUM_DOCU_RUV() {
        return NUM_DOCU_RUV;
    }

    public void setNUM_DOCU_RUV(String NUM_DOCU_RUV) {
        this.NUM_DOCU_RUV = NUM_DOCU_RUV;
    }

    public String getNUMERO_DOCU_ENCUESTA() {
        return NUMERO_DOCU_ENCUESTA;
    }

    public void setNUMERO_DOCU_ENCUESTA(String NUMERO_DOCU_ENCUESTA) {
        this.NUMERO_DOCU_ENCUESTA = NUMERO_DOCU_ENCUESTA;
    }
}
