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
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class emc_hogares extends SugarRecord<emc_hogares> {

    private String hog_codigo;
    private String usu_usuariocreacion;
    private String usu_fechacreacion;
    private String hog_tipo;
    public String estado;
    public String id_perfil_usuario;

}
