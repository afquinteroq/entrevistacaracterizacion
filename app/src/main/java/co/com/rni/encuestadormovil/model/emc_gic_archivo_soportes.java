package co.com.rni.encuestadormovil.model;

import com.orm.SugarRecord;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class emc_gic_archivo_soportes extends SugarRecord<emc_hogares> {

    private String id_temporal;
    private String hog_codigo;
    private String arc_url;
    private String usu_usuariocreacion;
    private String usu_fechacreacion ;
    private String tipo_persona;
}
