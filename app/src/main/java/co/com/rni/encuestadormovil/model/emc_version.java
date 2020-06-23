package co.com.rni.encuestadormovil.model;

import com.orm.SugarRecord;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by ASUS on 16/02/2017.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class emc_version extends SugarRecord<emc_version> {

    public Integer ver_idversion;
    public String ver_nombre;
    public String ver_version;

}
