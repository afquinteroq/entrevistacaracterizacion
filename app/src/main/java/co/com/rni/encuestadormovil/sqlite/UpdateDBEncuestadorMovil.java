package co.com.rni.encuestadormovil.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

import co.com.rni.encuestadormovil.model.emc_version;

/**
 * Created by ASUS on 8/03/2017.
 */

public class UpdateDBEncuestadorMovil extends SQLiteOpenHelper {



    public UpdateDBEncuestadorMovil(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*Hacer versión 2*/
        int y = newVersion;
        emc_version.executeQuery("UPDATE EMCVERSION SET VERVERSION ="+String.valueOf(newVersion)+" WHERE VERIDVERSION = 1");
        String[] paramsV = {"BASE"};
        final List<emc_version> tmIdVersion = emc_version.find(emc_version.class, "vernombre = ?", paramsV);
        y = Integer.parseInt(tmIdVersion.get(0).getVer_version());


    }
}
