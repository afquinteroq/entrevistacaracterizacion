package co.com.rni.encuestadormovil.model;

import com.orm.SugarRecord;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by ASUS on 8/05/2017.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class emc_resumen_encuestas_x_usuario extends SugarRecord<emc_resumen_encuestas_x_usuario> {

    private String usuario;
    private String estado;
    private String total;
}
