package co.com.rni.encuestadormovil.model;

import com.orm.SugarRecord;

import java.util.UUID;

public class emc_soporte_jefe_hogar extends SugarRecord<emc_soporte_jefe_hogar> {
    private String uuid;
    private String hog_codigo;
    private String cc_jefe_hogar;
    private String ARC_URL;
    private String usu_usuariocreacion;
    private String usu_fechacreacion;
    private int tipo_persona;

    public emc_soporte_jefe_hogar() {
    }

    public emc_soporte_jefe_hogar(String uuid, String hog_codigo, String cc_jefe_hogar, String ARC_URL, String usu_usuariocreacion, String usu_fechacreacion, int tipo_persona) {
        this.uuid = uuid;
        this.hog_codigo = hog_codigo;
        this.cc_jefe_hogar = cc_jefe_hogar;
        this.ARC_URL = ARC_URL;
        this.usu_usuariocreacion = usu_usuariocreacion;
        this.usu_fechacreacion = usu_fechacreacion;
        this.tipo_persona = tipo_persona;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getHog_codigo() {
        return hog_codigo;
    }

    public void setHog_codigo(String hog_codigo) {
        this.hog_codigo = hog_codigo;
    }

    public String getCc_jefe_hogar() {
        return cc_jefe_hogar;
    }

    public void setCc_jefe_hogar(String cc_jefe_hogar) {
        this.cc_jefe_hogar = cc_jefe_hogar;
    }

    public String getARC_URL() {
        return ARC_URL;
    }

    public void setARC_URL(String ARC_URL) {
        this.ARC_URL = ARC_URL;
    }

    public String getUsu_usuariocreacion() {
        return usu_usuariocreacion;
    }

    public void setUsu_usuariocreacion(String usu_usuariocreacion) {
        this.usu_usuariocreacion = usu_usuariocreacion;
    }

    public String getUsu_fechacreacion() {
        return usu_fechacreacion;
    }

    public void setUsu_fechacreacion(String usu_fechacreacion) {
        this.usu_fechacreacion = usu_fechacreacion;
    }

    public int getTipo_persona() {
        return tipo_persona;
    }

    public void setTipo_persona(int tipo_persona) {
        if(tipo_persona == 1){
            tipo_persona = 5001;
        }
        if(tipo_persona == 2){
            tipo_persona = 5002;
        }
        if(tipo_persona == 3){
            tipo_persona = 5003;
        }
        if(tipo_persona == 4){
            tipo_persona = 5004;
        }
        this.tipo_persona = tipo_persona;
    }
}
