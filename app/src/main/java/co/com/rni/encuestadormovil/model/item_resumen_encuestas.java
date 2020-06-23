package co.com.rni.encuestadormovil.model;

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
public class item_resumen_encuestas {

    private String usuario;
    private String estado;
    private String total;
}
