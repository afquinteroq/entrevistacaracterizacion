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

    /*
    public emc_usuarios() {
    }



    public emc_usuarios(String idusuario, String nombreusuario, String password, String tokenusuario, String feclogin, String fecvalidatoken, String IdPerfil, String nombrePerfil) {
        this.idusuario = idusuario;
        this.nombreusuario = nombreusuario;
        this.password = password;
        this.tokenusuario = tokenusuario;
        this.feclogin = feclogin;
        this.fecvalidatoken = fecvalidatoken;
        this.IdPerfil = IdPerfil;
        this.nombrePerfil = nombrePerfil;


    }
    public String getIdPerfil() {
        return IdPerfil;
    }

    public void setIdPerfil(String idPerfil) {
        IdPerfil = idPerfil;
    }

    public String getNombrePerfil() {
        return nombrePerfil;
    }

    public void setNombrePerfil(String nombrePerfil) {
        this.nombrePerfil = nombrePerfil;
    }


    public String getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(String idusuario) {
        this.idusuario = idusuario;
    }

    public String getNombreusuario() {
        return nombreusuario;
    }

    public void setNombreusuario(String nombreusuario) {
        //this.nombreusuario = nombreusuario.replace("Ñ","N").replace("ñ","n");
        this.nombreusuario = nombreusuario;
    }

    public String getTokenusuario() {
        return tokenusuario;
    }

    public void setTokenusuario(String tokenusuario) {
        this.tokenusuario = tokenusuario;
    }

    public String getFeclogin() {
        return feclogin;
    }

    public void setFeclogin(String feclogin) {
        this.feclogin = feclogin;
    }

    public String getFecvalidatoken() {
        return fecvalidatoken;
    }

    public void setFecvalidatoken(String fecvalidatoken) {
        this.fecvalidatoken = fecvalidatoken;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    */
}
