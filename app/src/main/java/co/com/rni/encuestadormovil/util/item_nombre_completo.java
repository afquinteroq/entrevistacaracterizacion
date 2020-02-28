package co.com.rni.encuestadormovil.util;

/**
 * Created by ASUS on 01/03/2018.
 */

public class item_nombre_completo {
    String nombre1;
    String nombre2;
    String apellido1;
    String apellido2;

    public item_nombre_completo() {

    }
    public item_nombre_completo(String nombre1, String nombre2, String apellido1, String apellido2) {
        this.nombre1 = nombre1;
        this.nombre2 = nombre2;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
    }

    public String getNombre1() {
        return nombre1;
    }

    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }

    public String getNombre2() {
        return nombre2;
    }

    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }
}
