package co.com.rni.encuestadormovil.model;

import com.orm.SugarRecord;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by javierperez on 15/12/15.
 */
/*
@Data
@Builder
*/
/*
@NoArgsConstructor
@AllArgsConstructor
*/
public class emc_miembros_hogar extends SugarRecord<emc_miembros_hogar> {

    private String hog_codigo;
    private String per_idpersona;
    private String ind_jefe;
    private String usu_usuariocreacion;
    private String usu_fechacreacion;
    public String tipoDoc;
    public String documento;
    public String nombre1;
    public String nombre2;
    public String apellido1;
    public String apellido2;
    public String fecNacimiento;
    public String estado;
    public String tipoPersona;


    public emc_miembros_hogar() {
    }

    public emc_miembros_hogar(String hog_codigo, String per_idpersona, String ind_jefe, String usu_usuariocreacion, String usu_fechacreacion, String tipoDoc, String documento, String nombre1, String nombre2, String apellido1, String apellido2, String fecNacimiento, String estado, String tipoPersona) {
        this.hog_codigo = hog_codigo;
        this.per_idpersona = per_idpersona;
        this.ind_jefe = ind_jefe;
        this.usu_usuariocreacion = usu_usuariocreacion;
        this.usu_fechacreacion = usu_fechacreacion;
        this.tipoDoc = tipoDoc;
        this.documento = documento;
        this.nombre1 = nombre1;
        this.nombre2 = nombre2;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.fecNacimiento = fecNacimiento;
        this.estado = estado;
        this.tipoPersona = tipoPersona;
    }

    public String getHog_codigo() {
        return hog_codigo;
    }

    public void setHog_codigo(String hog_codigo) {
        this.hog_codigo = hog_codigo;
    }

    public String getPer_idpersona() {
        return per_idpersona;
    }

    public void setPer_idpersona(String per_idpersona) {
        this.per_idpersona = per_idpersona;
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

    public String getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombre1() {
        return nombre1;
    }

    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }

    public String getNombre2() {
        return nombre2;
    }

    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getFecNacimiento() {
        return fecNacimiento;
    }

    public void setFecNacimiento(String fecNacimiento) {
        this.fecNacimiento = fecNacimiento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getInd_jefe() {
        return ind_jefe;
    }

    public void setInd_jefe(String ind_jefe) {
        this.ind_jefe = ind_jefe;
    }

    public String getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(String tipoPersona) {
        if(tipoPersona.equals("1")){
            tipoPersona = "5001";
        }
        if(tipoPersona.equals("2")){
            tipoPersona = "5002";
        }
        if(tipoPersona.equals("3")){
            tipoPersona = "5003";
        }
        if(tipoPersona.equals("4")){
            tipoPersona = "5004";
        }
        this.tipoPersona = tipoPersona;
    }
}
