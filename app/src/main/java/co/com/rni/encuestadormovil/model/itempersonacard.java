package co.com.rni.encuestadormovil.model;


import com.orm.SugarRecord;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class itempersonacard extends SugarRecord<emc_hogares> {

    private String tvPersona;
    private String tvNacionalidad;
    private String textViewDatos;
    private String imageViewImagen;

}
