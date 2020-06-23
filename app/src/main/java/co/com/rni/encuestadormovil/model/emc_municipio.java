package co.com.rni.encuestadormovil.model;

import com.orm.SugarRecord;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by javierperez on 15/12/15.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class emc_municipio extends SugarRecord<emc_municipio> {

    String id_muni_depto;
    String id_depto;
    String id_municipio;
    String nom_municipio;
    String no_personas;
    String hombres;
    String mujeres;
    String tipo_a;
    String tipo_b;
    String tipo_c;
    String clima;
    String operador;
    String grupo_especial;
    String categoria;

}
