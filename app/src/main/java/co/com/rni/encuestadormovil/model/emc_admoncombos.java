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
public class emc_admoncombos extends SugarRecord<emc_admoncombos> {

    String gic_idcombo;
    String gic_query;

    /*
    public emc_admoncombos() {
    }

    public emc_admoncombos(String gic_idcombo, String gic_query) {
        this.gic_idcombo = gic_idcombo;
        this.gic_query = gic_query;
    }

    public String getGic_idcombo() {
        return gic_idcombo;
    }

    public void setGic_idcombo(String gic_idcombo) {
        this.gic_idcombo = gic_idcombo;
    }

    public String getGic_query() {
        return gic_query;
    }

    public void setGic_query(String gic_query) {
        this.gic_query = gic_query;
    }
    */
}
