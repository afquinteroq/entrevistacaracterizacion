package co.com.rni.encuestadormovil.adapter;

/**
 * Created by javierperez on 9/02/16.
 */
public interface ratiosResponse {
    public void onItemSelected(Integer position, Integer positionRespuesta);

    public void onOtroChanged(Integer position, Integer positionRespuesta, String textoOtro);
}
