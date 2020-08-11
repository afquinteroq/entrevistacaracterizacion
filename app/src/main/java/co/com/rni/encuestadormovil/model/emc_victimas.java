package co.com.rni.encuestadormovil.model;

import com.orm.SugarRecord;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by javierperez on 4/02/16.
 */
/*@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
*/
public class emc_victimas extends SugarRecord<emc_victimas> {

    public Integer consPersona;
    public String hogar;
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

    public emc_victimas() {
    }

    public emc_victimas(int consPersona, String hogar, String tipoDoc, String documento, String nombre1, String nombre2, String apellido1, String apellido2, String fecNacimiento, int hv1, int hv2, int hv3, int hv4, int hv5, int hv6, int hv7, int hv8, int hv9, int hv10, int hv11, int hv12, int hv13, int hv14, String estado, int encuestado, String fechaEncuesta) {
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

    public int getConsPersona() {
        return consPersona;
    }

    public void setConsPersona(int consPersona) {
        this.consPersona = consPersona;
    }

    public String getHogar() {
        return hogar;
    }

    public void setHogar(String hogar) {
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

    public int getHv1() {
        return hv1;
    }

    public void setHv1(int hv1) {
        this.hv1 = hv1;
    }

    public int getHv2() {
        return hv2;
    }

    public void setHv2(int hv2) {
        this.hv2 = hv2;
    }

    public int getHv3() {
        return hv3;
    }

    public void setHv3(int hv3) {
        this.hv3 = hv3;
    }

    public int getHv4() {
        return hv4;
    }

    public void setHv4(int hv4) {
        this.hv4 = hv4;
    }

    public int getHv5() {
        return hv5;
    }

    public void setHv5(int hv5) {
        this.hv5 = hv5;
    }

    public int getHv6() {
        return hv6;
    }

    public void setHv6(int hv6) {
        this.hv6 = hv6;
    }

    public int getHv7() {
        return hv7;
    }

    public void setHv7(int hv7) {
        this.hv7 = hv7;
    }

    public int getHv8() {
        return hv8;
    }

    public void setHv8(int hv8) {
        this.hv8 = hv8;
    }

    public int getHv9() {
        return hv9;
    }

    public void setHv9(int hv9) {
        this.hv9 = hv9;
    }

    public int getHv10() {
        return hv10;
    }

    public void setHv10(int hv10) {
        this.hv10 = hv10;
    }

    public int getHv11() {
        return hv11;
    }

    public void setHv11(int hv11) {
        this.hv11 = hv11;
    }

    public int getHv12() {
        return hv12;
    }

    public void setHv12(int hv12) {
        this.hv12 = hv12;
    }

    public int getHv13() {
        return hv13;
    }

    public void setHv13(int hv13) {
        this.hv13 = hv13;
    }

    public int getHv14() {
        return hv14;
    }

    public void setHv14(int hv14) {
        this.hv14 = hv14;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getEncuestado() {
        return encuestado;
    }

    public void setEncuestado(int encuestado) {
        this.encuestado = encuestado;
    }

    public String getFechaEncuesta() {
        return fechaEncuesta;
    }

    public void setFechaEncuesta(String fechaEncuesta) {
        this.fechaEncuesta = fechaEncuesta;
    }

}
