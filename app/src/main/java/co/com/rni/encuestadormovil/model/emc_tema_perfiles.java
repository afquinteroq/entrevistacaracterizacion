package co.com.rni.encuestadormovil.model;

import com.orm.SugarRecord;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by ASUS on 7/10/2016.
 */
/*
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
*/
public class emc_tema_perfiles  extends SugarRecord<emc_tema_perfiles> {
    String tem_idtema;
    String per_idperfil;

    public emc_tema_perfiles() {
    }

    public emc_tema_perfiles(String tem_idtema, String per_idperfil) {
        this.tem_idtema = tem_idtema;
        this.per_idperfil = per_idperfil;
    }

    public String getTem_idtema() {
        return tem_idtema;
    }

    public void setTem_idtema(String tem_idtema) {
        this.tem_idtema = tem_idtema;
    }

    public String getPer_idperfil() {
        return per_idperfil;
    }

    public void setPer_idperfil(String per_idperfil) {
        this.per_idperfil = per_idperfil;
    }
}
