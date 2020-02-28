package co.com.rni.encuestadormovil.util;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import co.com.rni.encuestadormovil.model.emc_victimas;

/**
 * Created by javierperez on 4/02/16.
 */
public class asyncCargaVictimas extends AsyncTask<String, Integer, String> {

    private Context ctx;
    private responseParametros callback;

    public asyncCargaVictimas(Context ctx, responseParametros callback) {
        this.ctx = ctx;
        this.callback = callback;
    }

    @Override
    protected String doInBackground(String... params) {
        Integer contVictimas = 0;
        InputStream isCarga;


        try {
            AssetFileDescriptor descriptor = ctx.getAssets().openFd("victimas601.csv");
            isCarga = ctx.getAssets().open("victimas601.csv");

            BufferedReader reader = new BufferedReader(new InputStreamReader(isCarga));

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.replace("\"", "");
                String[] row = line.split(",");
                if(contVictimas > 0){
                    emc_victimas tempVictimas = new emc_victimas();
                    tempVictimas.setConsPersona(Integer.valueOf(row[0]));
                    //tempVictimas.setHogar(Integer.valueOf(row[1]));
                    tempVictimas.setHogar(row[1]);
                    tempVictimas.setTipoDoc(row[2]);
                    tempVictimas.setDocumento(row[3]);
                    tempVictimas.setNombre1(row[4]);
                    tempVictimas.setNombre2(row[5]);
                    tempVictimas.setApellido1(row[6]);
                    tempVictimas.setApellido2(row[7]);
                    tempVictimas.setFecNacimiento(row[8]);
                    tempVictimas.setHv1(Integer.valueOf(row[9]));
                    tempVictimas.setHv2(Integer.valueOf(row[10]));
                    tempVictimas.setHv3(Integer.valueOf(row[11]));
                    tempVictimas.setHv4(Integer.valueOf(row[12]));
                    tempVictimas.setHv5(Integer.valueOf(row[13]));
                    tempVictimas.setHv6(Integer.valueOf(row[14]));
                    tempVictimas.setHv7(Integer.valueOf(row[15]));
                    tempVictimas.setHv8(Integer.valueOf(row[16]));
                    tempVictimas.setHv9(Integer.valueOf(row[17]));
                    tempVictimas.setHv10(Integer.valueOf(row[18]));
                    tempVictimas.setHv11(Integer.valueOf(row[19]));
                    tempVictimas.setHv12(Integer.valueOf(row[20]));
                    tempVictimas.setHv13(Integer.valueOf(row[21]));
                    tempVictimas.setHv14(Integer.valueOf(row[22]));
                    tempVictimas.setEstado(row[23].toUpperCase());
                    tempVictimas.setEncuestado(Integer.valueOf(row[24]));
                    if(row.length > 25) tempVictimas.setFechaEncuesta(row[25]);
                    tempVictimas.save();
                }
                contVictimas++;
                if((contVictimas % 1000) == 0) publishProgress(contVictimas);

            }
            isCarga.close();
        }
        catch (IOException e) {
            Log.e("Main", e.getMessage());
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... progress){
        Integer registros = progress[0];
        callback.actualizaParametros("Cargando registros " + registros.toString(), registros);
    }

    @Override
    protected void onPostExecute(String result){
        callback.cargaFinalizada(true);
    }
}

