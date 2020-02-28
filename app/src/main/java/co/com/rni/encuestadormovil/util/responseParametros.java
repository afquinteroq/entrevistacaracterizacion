package co.com.rni.encuestadormovil.util;

/**
 * Created by javierperez on 4/02/16.
 */
public interface responseParametros {
    public void actualizaParametros(String mensaje, Integer registros);

    public void cargaFinalizada(boolean exito);
}
