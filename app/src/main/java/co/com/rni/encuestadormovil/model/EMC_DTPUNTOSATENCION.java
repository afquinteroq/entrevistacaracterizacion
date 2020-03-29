package co.com.rni.encuestadormovil.model;

import com.orm.SugarRecord;



public class EMC_DTPUNTOSATENCION  extends SugarRecord<EMC_DTPUNTOSATENCION> {
    Integer iddt;
    String dt;
    Integer iddepartamento;
    String departamento;
    Integer idpuntoatencion;
    String  puntoatencion;
    Integer idmunicipio ;
    String municipio;

    public EMC_DTPUNTOSATENCION(){

    }

    public EMC_DTPUNTOSATENCION(Integer iddt, String dt, Integer iddepartamento, String departamento, Integer idpuntoatencion, String puntoatencion, Integer idmunicipio, String municipio) {
        this.iddt = iddt;
        this.dt = dt;
        this.iddepartamento = iddepartamento;
        this.departamento = departamento;
        this.idpuntoatencion = idpuntoatencion;
        this.puntoatencion = puntoatencion;
        this.idmunicipio = idmunicipio;
        this.municipio = municipio;
    }

    public Integer getIddt() {
        return iddt;
    }

    public void setIddt(Integer iddt) {
        this.iddt = iddt;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public Integer getIddepartamento() {
        return iddepartamento;
    }

    public void setIddepartamento(Integer iddepartamento) {
        this.iddepartamento = iddepartamento;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public Integer getIdpuntoatencion() {
        return idpuntoatencion;
    }

    public void setIdpuntoatencion(Integer idpuntoatencion) {
        this.idpuntoatencion = idpuntoatencion;
    }

    public String getPuntoatencion() {
        return puntoatencion;
    }

    public void setPuntoatencion(String puntoatencion) {
        this.puntoatencion = puntoatencion;
    }

    public Integer getIdmunicipio() {
        return idmunicipio;
    }

    public void setIdmunicipio(Integer idmunicipio) {
        this.idmunicipio = idmunicipio;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }
}
