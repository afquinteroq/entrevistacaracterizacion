package co.com.rni.encuestadormovil.util;

/**
 * Created by javierperez on 4/02/16.
 */
public interface responseUpload {
    public void actualizaEstado(String mensaje);

    public void cargaFinalizada(boolean exito);
}
