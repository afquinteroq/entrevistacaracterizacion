package co.com.rni.encuestadormovil.model;

import com.orm.SugarRecord;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by javierperez on 16/12/15.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class emc_usuarios  extends SugarRecord<emc_usuarios> {

    String idusuario;
    String nombreusuario;
    String password;
    String tokenusuario;
    String feclogin;
    String fecvalidatoken;
    String IdPerfil;
    String nombrePerfil;


}
