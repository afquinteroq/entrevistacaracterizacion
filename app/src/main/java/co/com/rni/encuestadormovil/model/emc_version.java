package co.com.rni.encuestadormovil.model;

import com.orm.SugarRecord;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by ASUS on 16/02/2017.
 */
/*
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
*/
public class emc_version extends SugarRecord<emc_version> {

    public Integer ver_idversion;
    public String ver_nombre;
    public String ver_version;

    public emc_version() {
    }

    public emc_version(Integer ver_idversion, String ver_nombre, String ver_version) {
        this.ver_idversion = ver_idversion;
        this.ver_nombre = ver_nombre;
        this.ver_version = ver_version;
    }

    public Integer getVer_idversion() {
        return ver_idversion;
    }

    public void setVer_idversion(Integer ver_idversion) {
        this.ver_idversion = ver_idversion;
    }

    public String getVer_nombre() {
        return ver_nombre;
    }

    public void setVer_nombre(String ver_nombre) {
        this.ver_nombre = ver_nombre;
    }

    public String getVer_version() {
        return ver_version;
    }

    public void setVer_version(String ver_version) {
        this.ver_version = ver_version;
    }
}
