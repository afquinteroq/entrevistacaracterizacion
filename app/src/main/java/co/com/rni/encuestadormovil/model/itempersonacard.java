package co.com.rni.encuestadormovil.model;


import com.orm.SugarRecord;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
*/
public class itempersonacard extends SugarRecord<emc_hogares> {

    private String tvPersona;
    private String tvNacionalidad;
    private String textViewDatos;
    private String imageViewImagen;

    public itempersonacard() {
    }

    public itempersonacard(String tvPersona, String tvNacionalidad, String textViewDatos, String imageViewImagen) {
        this.tvPersona = tvPersona;
        this.tvNacionalidad = tvNacionalidad;
        this.textViewDatos = textViewDatos;
        this.imageViewImagen = imageViewImagen;
    }

    public String getTvPersona() {
        return tvPersona;
    }

    public void setTvPersona(String tvPersona) {
        this.tvPersona = tvPersona;
    }

    public String getTvNacionalidad() {
        return tvNacionalidad;
    }

    public void setTvNacionalidad(String tvNacionalidad) {
        this.tvNacionalidad = tvNacionalidad;
    }

    public String getTextViewDatos() {
        return textViewDatos;
    }

    public void setTextViewDatos(String textViewDatos) {
        this.textViewDatos = textViewDatos;
    }

    public String getImageViewImagen() {
        return imageViewImagen;
    }

    public void setImageViewImagen(String imageViewImagen) {
        this.imageViewImagen = imageViewImagen;
    }
}
