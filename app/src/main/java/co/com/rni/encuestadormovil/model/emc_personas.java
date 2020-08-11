package co.com.rni.encuestadormovil.model;

import com.orm.SugarRecord;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by javierperez on 4/02/16.
 */

/*
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
*/
public class emc_personas extends SugarRecord<emc_personas> {

    String per_idpersona;
    String per_primernombre;
    String per_segundonombre;
    String per_primerapellido;
    String per_segundoapellido;
    String per_fechanacimiento;
    String per_tipodoc;
    String usu_usuariocreacion;
    String usu_fechacreacion;
    String per_numerodoc;
    String per_relacion;
    String per_iddeclaracion;
    String per_idepersonafuente;
    String per_tipovictima;
    String per_idsiniestro;
    String per_fuente;
    String per_estado;

    public emc_personas() {
    }

    public emc_personas(String per_idpersona, String per_primernombre, String per_segundonombre, String per_primerapellido, String per_segundoapellido, String per_fechanacimiento, String per_tipodoc, String usu_usuariocreacion, String usu_fechacreacion, String per_numerodoc, String per_relacion, String per_iddeclaracion, String per_idepersonafuente, String per_tipovictima, String per_idsiniestro, String per_fuente, String per_estado) {
        this.per_idpersona = per_idpersona;
        this.per_primernombre = per_primernombre;
        this.per_segundonombre = per_segundonombre;
        this.per_primerapellido = per_primerapellido;
        this.per_segundoapellido = per_segundoapellido;
        this.per_fechanacimiento = per_fechanacimiento;
        this.per_tipodoc = per_tipodoc;
        this.usu_usuariocreacion = usu_usuariocreacion;
        this.usu_fechacreacion = usu_fechacreacion;
        this.per_numerodoc = per_numerodoc;
        this.per_relacion = per_relacion;
        this.per_iddeclaracion = per_iddeclaracion;
        this.per_idepersonafuente = per_idepersonafuente;
        this.per_tipovictima = per_tipovictima;
        this.per_idsiniestro = per_idsiniestro;
        this.per_fuente = per_fuente;
        this.per_estado = per_estado;
    }

    public String getPer_idpersona() {
        return per_idpersona;
    }

    public void setPer_idpersona(String per_idpersona) {
        this.per_idpersona = per_idpersona;
    }

    public String getPer_primernombre() {
        return per_primernombre;
    }

    public void setPer_primernombre(String per_primernombre) {
        this.per_primernombre = per_primernombre;
    }

    public String getPer_segundonombre() {
        return per_segundonombre;
    }

    public void setPer_segundonombre(String per_segundonombre) {
        this.per_segundonombre = per_segundonombre;
    }

    public String getPer_primerapellido() {
        return per_primerapellido;
    }

    public void setPer_primerapellido(String per_primerapellido) {
        this.per_primerapellido = per_primerapellido;
    }

    public String getPer_segundoapellido() {
        return per_segundoapellido;
    }

    public void setPer_segundoapellido(String per_segundoapellido) {
        this.per_segundoapellido = per_segundoapellido;
    }

    public String getPer_fechanacimiento() {
        return per_fechanacimiento;
    }

    public void setPer_fechanacimiento(String per_fechanacimiento) {
        this.per_fechanacimiento = per_fechanacimiento;
    }

    public String getPer_tipodoc() {
        return per_tipodoc;
    }

    public void setPer_tipodoc(String per_tipodoc) {
        this.per_tipodoc = per_tipodoc;
    }

    public String getUsu_usuariocreacion() {
        return usu_usuariocreacion;
    }

    public void setUsu_usuariocreacion(String usu_usuariocreacion) {
        this.usu_usuariocreacion = usu_usuariocreacion;
    }

    public String getUsu_fechacreacion() {
        return usu_fechacreacion;
    }

    public void setUsu_fechacreacion(String usu_fechacreacion) {
        this.usu_fechacreacion = usu_fechacreacion;
    }

    public String getPer_numerodoc() {
        return per_numerodoc;
    }

    public void setPer_numerodoc(String per_numerodoc) {
        this.per_numerodoc = per_numerodoc;
    }

    public String getPer_relacion() {
        return per_relacion;
    }

    public void setPer_relacion(String per_relacion) {
        this.per_relacion = per_relacion;
    }

    public String getPer_iddeclaracion() {
        return per_iddeclaracion;
    }

    public void setPer_iddeclaracion(String per_iddeclaracion) {
        this.per_iddeclaracion = per_iddeclaracion;
    }

    public String getPer_idepersonafuente() {
        return per_idepersonafuente;
    }

    public void setPer_idepersonafuente(String per_idepersonafuente) {
        this.per_idepersonafuente = per_idepersonafuente;
    }

    public String getPer_tipovictima() {
        return per_tipovictima;
    }

    public void setPer_tipovictima(String per_tipovictima) {
        this.per_tipovictima = per_tipovictima;
    }

    public String getPer_idsiniestro() {
        return per_idsiniestro;
    }

    public void setPer_idsiniestro(String per_idsiniestro) {
        this.per_idsiniestro = per_idsiniestro;
    }

    public String getPer_fuente() {
        return per_fuente;
    }

    public void setPer_fuente(String per_fuente) {
        this.per_fuente = per_fuente;
    }

    public String getPer_estado() {
        return per_estado;
    }

    public void setPer_estado(String per_estado) {
        this.per_estado = per_estado;
    }
}
