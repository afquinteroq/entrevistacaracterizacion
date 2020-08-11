package co.com.rni.encuestadormovil.model;

import com.orm.SugarRecord;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by javierperez on 4/02/16.
 */
/*
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
*/
public class emc_victimas_nosugar{

    public Integer consPersona;
    public Integer hogar;
    public String tipoDoc;
    public String documento;
    public String nombre1;
    public String nombre2;
    public String apellido1;
    public String apellido2;
    public String fecNacimiento;
    public Integer hv1;
    public Integer hv2;
    public Integer hv3;
    public Integer hv4;
    public Integer hv5;
    public Integer hv6;
    public Integer hv7;
    public Integer hv8;
    public Integer hv9;
    public Integer hv10;
    public Integer hv11;
    public Integer hv12;
    public Integer hv13;
    public Integer hv14;
    public String estado;
    public Integer encuestado;
    public String fechaEncuesta;

    public emc_victimas_nosugar() {
    }

    public emc_victimas_nosugar(Integer consPersona, Integer hogar, String tipoDoc, String documento, String nombre1, String nombre2, String apellido1, String apellido2, String fecNacimiento, Integer hv1, Integer hv2, Integer hv3, Integer hv4, Integer hv5, Integer hv6, Integer hv7, Integer hv8, Integer hv9, Integer hv10, Integer hv11, Integer hv12, Integer hv13, Integer hv14, String estado, Integer encuestado, String fechaEncuesta) {
        this.consPersona = consPersona;
        this.hogar = hogar;
        this.tipoDoc = tipoDoc;
        this.documento = documento;
        this.nombre1 = nombre1;
        this.nombre2 = nombre2;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.fecNacimiento = fecNacimiento;
        this.hv1 = hv1;
        this.hv2 = hv2;
        this.hv3 = hv3;
        this.hv4 = hv4;
        this.hv5 = hv5;
        this.hv6 = hv6;
        this.hv7 = hv7;
        this.hv8 = hv8;
        this.hv9 = hv9;
        this.hv10 = hv10;
        this.hv11 = hv11;
        this.hv12 = hv12;
        this.hv13 = hv13;
        this.hv14 = hv14;
        this.estado = estado;
        this.encuestado = encuestado;
        this.fechaEncuesta = fechaEncuesta;
    }

    public Integer getConsPersona() {
        return consPersona;
    }

    public void setConsPersona(Integer consPersona) {
        this.consPersona = consPersona;
    }

    public Integer getHogar() {
        return hogar;
    }

    public void setHogar(Integer hogar) {
        this.hogar = hogar;
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

    public Integer getHv1() {
        return hv1;
    }

    public void setHv1(Integer hv1) {
        this.hv1 = hv1;
    }

    public Integer getHv2() {
        return hv2;
    }

    public void setHv2(Integer hv2) {
        this.hv2 = hv2;
    }

    public Integer getHv3() {
        return hv3;
    }

    public void setHv3(Integer hv3) {
        this.hv3 = hv3;
    }

    public Integer getHv4() {
        return hv4;
    }

    public void setHv4(Integer hv4) {
        this.hv4 = hv4;
    }

    public Integer getHv5() {
        return hv5;
    }

    public void setHv5(Integer hv5) {
        this.hv5 = hv5;
    }

    public Integer getHv6() {
        return hv6;
    }

    public void setHv6(Integer hv6) {
        this.hv6 = hv6;
    }

    public Integer getHv7() {
        return hv7;
    }

    public void setHv7(Integer hv7) {
        this.hv7 = hv7;
    }

    public Integer getHv8() {
        return hv8;
    }

    public void setHv8(Integer hv8) {
        this.hv8 = hv8;
    }

    public Integer getHv9() {
        return hv9;
    }

    public void setHv9(Integer hv9) {
        this.hv9 = hv9;
    }

    public Integer getHv10() {
        return hv10;
    }

    public void setHv10(Integer hv10) {
        this.hv10 = hv10;
    }

    public Integer getHv11() {
        return hv11;
    }

    public void setHv11(Integer hv11) {
        this.hv11 = hv11;
    }

    public Integer getHv12() {
        return hv12;
    }

    public void setHv12(Integer hv12) {
        this.hv12 = hv12;
    }

    public Integer getHv13() {
        return hv13;
    }

    public void setHv13(Integer hv13) {
        this.hv13 = hv13;
    }

    public Integer getHv14() {
        return hv14;
    }

    public void setHv14(Integer hv14) {
        this.hv14 = hv14;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getEncuestado() {
        return encuestado;
    }

    public void setEncuestado(Integer encuestado) {
        this.encuestado = encuestado;
    }

    public String getFechaEncuesta() {
        return fechaEncuesta;
    }

    public void setFechaEncuesta(String fechaEncuesta) {
        this.fechaEncuesta = fechaEncuesta;
    }
}
