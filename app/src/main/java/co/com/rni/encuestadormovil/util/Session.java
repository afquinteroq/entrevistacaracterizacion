package co.com.rni.encuestadormovil.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import co.com.rni.encuestadormovil.model.*;

/**
 * Created by JPerezP on 23/09/2015.
 */
public final class Session {

    public static void SaveCookie(Application app, emc_usuarios usuarioLogin){
        SharedPreferences mPrefs=app.getSharedPreferences(app.getApplicationInfo().name, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed=mPrefs.edit();
        Gson gson = new Gson();
        ed.putString("usuarioLogin", gson.toJson(usuarioLogin));
        ed.commit();
    }

    public static emc_usuarios LoadCookie(Application app){
        emc_usuarios usuarioLogin = null;
        String exchangerjson =  app.getSharedPreferences(app.getApplicationInfo().name, Context.MODE_PRIVATE).getString("usuarioLogin", null);
        Gson gson = new Gson();
        if(exchangerjson!=null)
            usuarioLogin = gson.fromJson(exchangerjson, emc_usuarios.class);
        return usuarioLogin;
    }

    public static void DeleteCookie(Application app){
        SharedPreferences mPrefs=app.getSharedPreferences(app.getApplicationInfo().name, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed=mPrefs.edit();
        ed.putString("usuarioLogin", "");
        ed.commit();
    }

    public static void SaveHogarActual(Application app, emc_hogares hogarActual){
        SharedPreferences mPrefs=app.getSharedPreferences(app.getApplicationInfo().name, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed=mPrefs.edit();
        Gson gson = new Gson();
        ed.putString("hogarActual", gson.toJson(hogarActual));
        ed.commit();
    }

    public static emc_hogares LoadHogarActual(Application app){
        emc_hogares hogarActual = null;
        String exchangerjson =  app.getSharedPreferences(app.getApplicationInfo().name, Context.MODE_PRIVATE).getString("hogarActual", null);
        Gson gson = new Gson();
        if(exchangerjson!=null)
            hogarActual = gson.fromJson(exchangerjson, emc_hogares.class);
        return hogarActual;
    }

    public static void DeleteHogarActual(Application app){
        SharedPreferences mPrefs=app.getSharedPreferences(app.getApplicationInfo().name, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed=mPrefs.edit();
        ed.putString("hogarActual", "");
        ed.commit();
    }
}
