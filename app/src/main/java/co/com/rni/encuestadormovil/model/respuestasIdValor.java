package co.com.rni.encuestadormovil.model;



/**
 * Created by javierperez on 22/02/16.
 */

public class respuestasIdValor {
    String id;
    String valor;
    boolean selected;
    Integer position;

    public respuestasIdValor() {
    }

    public respuestasIdValor(String id, String valor, boolean selected, Integer position) {
        this.id = id;
        this.valor = valor;
        this.selected = selected;
        this.position = position;
    }

    public respuestasIdValor(String id, String valor, boolean selected) {
        this.id = id;
        this.valor = valor;
        this.selected = selected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

}
