package co.com.rni.encuestadormovil.model;

/**
 * Created by javierperez on 26/11/15.
 */
public class itemEncuesta {

    String fecha;
    String idHogar;
    String idPersona;
    String Estado;

    public itemEncuesta() {
    }


    public itemEncuesta(String fecha, String idHogar, String idPersona, String estado/*, String pdf*/) {
        this.fecha = fecha;
        this.idHogar = idHogar;
        this.idPersona = idPersona;
        this.Estado = estado;
        //this.pdf = pdf;
    }


    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getIdHogar() {
        return idHogar;
    }

    public void setIdHogar(String idHogar) {
        this.idHogar = idHogar;
    }

    public String getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }
}
