package co.com.rni.encuestadormovil.adapter;

/**
 * Created by javierperez on 9/02/16.
 */
public interface conformarHogarResponse {
    public void onClickAgregarVictimaResponse(Integer position);

    public void onDeleteMiembroHogar(Integer position);

    public void onSelectJefeHogar(Integer position, Integer tipopersona);

    public void onVerHogar(Integer position);

    public void onSelectTipoPersona(Integer position);
}
