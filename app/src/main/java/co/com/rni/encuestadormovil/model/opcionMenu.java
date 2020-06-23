package co.com.rni.encuestadormovil.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by javierperez on 15/11/15.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class opcionMenu {
    private String icono;
    private String texto;

}
