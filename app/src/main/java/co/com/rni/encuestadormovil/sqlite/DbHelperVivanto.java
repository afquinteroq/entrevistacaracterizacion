package co.com.rni.encuestadormovil.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DbHelperVivanto extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "vivanto.db";
    private static final int DATABASE_VERSION = 1;

    public DbHelperVivanto(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    public Integer getConteoRespuesta(){
        Integer resultado = 0;

        String selectQuery = "SELECT COUNT(residrespuesta) FROM EMCRESPUESTAS";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            if (cursor.moveToFirst()) {
                resultado = cursor.getInt(0);
            }
        }finally {
            cursor.close();
            db.close();
        }
        return resultado;
    }
}
