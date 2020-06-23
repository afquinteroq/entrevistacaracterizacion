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
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

}
