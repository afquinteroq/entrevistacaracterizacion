package co.com.rni.encuestadormovil.model;

import com.orm.SugarRecord;




public class EMC_RELACIONDTPUNTO extends SugarRecord<EMC_RELACIONDTPUNTO> {
    String hogarcodigo;
    String idpersona;
    Integer iddt;
    Integer iddeptoaten;
    Integer idpuntoaten;
    Integer idmunaten;

    public EMC_RELACIONDTPUNTO(){

    }

    public EMC_RELACIONDTPUNTO(String hogarcodigo, String idpersona, Integer iddt, Integer iddeptoaten, Integer idpuntoaten, Integer idmunaten) {
        this.hogarcodigo = hogarcodigo;
        this.idpersona = idpersona;
        this.iddt = iddt;
        this.iddeptoaten = iddeptoaten;
        this.idpuntoaten = idpuntoaten;
        this.idmunaten = idmunaten;
    }

    public String getHogarcodigo() {
        return hogarcodigo;
    }

    public void setHogarcodigo(String hogarcodigo) {
        this.hogarcodigo = hogarcodigo;
    }

    public String getIdpersona() {
        return idpersona;
    }

    public void setIdpersona(String idpersona) {
        this.idpersona = idpersona;
    }

    public Integer getIddt() {
        return iddt;
    }

    public void setIddt(Integer iddt) {
        this.iddt = iddt;
    }

    public Integer getIddeptoaten() {
        return iddeptoaten;
    }

    public void setIddeptoaten(Integer iddeptoaten) {
        this.iddeptoaten = iddeptoaten;
    }

    public Integer getIdpuntoaten() {
        return idpuntoaten;
    }

    public void setIdpuntoaten(Integer idpuntoaten) {
        this.idpuntoaten = idpuntoaten;
    }

    public Integer getIdmunaten() {
        return idmunaten;
    }

    public void setIdmunaten(Integer idmunaten) {
        this.idmunaten = idmunaten;
    }
}
