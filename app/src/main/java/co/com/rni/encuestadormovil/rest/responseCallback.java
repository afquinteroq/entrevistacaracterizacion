package co.com.rni.encuestadormovil.rest;

import co.com.rni.encuestadormovil.model.*;

/**
 * Created by Javier on 29/08/2015.
 */
public interface responseCallback {
    public void onLoginResponse(boolean isLoged, emc_usuarios usuarioLogin, String mensaje);
}
