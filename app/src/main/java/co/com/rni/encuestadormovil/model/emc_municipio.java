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
public class emc_municipio extends SugarRecord<emc_municipio> {

    String id_muni_depto;
    String id_depto;
    String id_municipio;
    String nom_municipio;
    String no_personas;
    String hombres;
    String mujeres;
    String tipo_a;
    String tipo_b;
    String tipo_c;
    String clima;
    String operador;
    String grupo_especial;
    String categoria;

    /*

    public emc_municipio() {
    }

    public emc_municipio(String id_muni_depto, String id_depto, String id_municipio, String nom_municipio, String no_personas, String hombres, String mujeres, String tipo_a, String tipo_b, String tipo_c, String clima, String operador, String grupo_especial, String categoria) {
        this.id_muni_depto = id_muni_depto;
        this.id_depto = id_depto;
        this.id_municipio = id_municipio;
        this.nom_municipio = nom_municipio;
        this.no_personas = no_personas;
        this.hombres = hombres;
        this.mujeres = mujeres;
        this.tipo_a = tipo_a;
        this.tipo_b = tipo_b;
        this.tipo_c = tipo_c;
        this.clima = clima;
        this.operador = operador;
        this.grupo_especial = grupo_especial;
        this.categoria = categoria;
    }

    public String getId_muni_depto() {
        return id_muni_depto;
    }

    public void setId_muni_depto(String id_muni_depto) {
        this.id_muni_depto = id_muni_depto;
    }

    public String getId_depto() {
        return id_depto;
    }

    public void setId_depto(String id_depto) {
        this.id_depto = id_depto;
    }

    public String getId_municipio() {
        return id_municipio;
    }

    public void setId_municipio(String id_municipio) {
        this.id_municipio = id_municipio;
    }

    public String getNom_municipio() {
        return nom_municipio;
    }

    public void setNom_municipio(String nom_municipio) {
        this.nom_municipio = nom_municipio;
    }

    public String getNo_personas() {
        return no_personas;
    }

    public void setNo_personas(String no_personas) {
        this.no_personas = no_personas;
    }

    public String getHombres() {
        return hombres;
    }

    public void setHombres(String hombres) {
        this.hombres = hombres;
    }

    public String getMujeres() {
        return mujeres;
    }

    public void setMujeres(String mujeres) {
        this.mujeres = mujeres;
    }

    public String getTipo_a() {
        return tipo_a;
    }

    public void setTipo_a(String tipo_a) {
        this.tipo_a = tipo_a;
    }

    public String getTipo_b() {
        return tipo_b;
    }

    public void setTipo_b(String tipo_b) {
        this.tipo_b = tipo_b;
    }

    public String getTipo_c() {
        return tipo_c;
    }

    public void setTipo_c(String tipo_c) {
        this.tipo_c = tipo_c;
    }

    public String getClima() {
        return clima;
    }

    public void setClima(String clima) {
        this.clima = clima;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public String getGrupo_especial() {
        return grupo_especial;
    }

    public void setGrupo_especial(String grupo_especial) {
        this.grupo_especial = grupo_especial;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    */
}
