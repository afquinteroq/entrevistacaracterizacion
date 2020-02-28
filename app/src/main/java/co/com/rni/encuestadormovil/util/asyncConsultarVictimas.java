package co.com.rni.encuestadormovil.util;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import co.com.rni.encuestadormovil.model.*;
import co.com.rni.encuestadormovil.sqlite.DbHelper;

/**
 * Created by javierperez on 4/02/16.
 */
public class asyncConsultarVictimas extends AsyncTask<String, Integer, Boolean> {

    private Context ctx;
    private responseVictimas callback;
    private List<emc_victimas_nosugar> lsResultado;
    private List<emc_personas_encuestas_no_sugar> lsResultadoPersonasEncuestadas;
    private String Tipo;
    private ArrayList<emc_miembros_hogar> miembrosHogar;
    private String Documento;
    private DbHelper myDB;
    private emc_usuarios usuarioLogin;


    public asyncConsultarVictimas(Context ctx, responseVictimas callback, String Tipo, ArrayList<emc_miembros_hogar> miembrosHogar,String Documento, DbHelper myDB, emc_usuarios usuarioLogin) {
        this.ctx = ctx;
        this.callback = callback;
        this.lsResultado = new ArrayList<>();
        this.lsResultadoPersonasEncuestadas = new ArrayList<>();
        this.Tipo = Tipo;
        this.miembrosHogar = miembrosHogar;
        this.Documento = Documento;
        this.myDB = myDB;
        this.usuarioLogin = usuarioLogin;

    }

    public asyncConsultarVictimas(Context ctx, responseVictimas callback, String Tipo ,String Documento, DbHelper myDB, emc_usuarios usuarioLogin) {
        this.ctx = ctx;
        this.callback = callback;
        this.lsResultado = new ArrayList<>();
        this.lsResultadoPersonasEncuestadas = new ArrayList<>();
        this.Tipo = Tipo;
        this.Documento = Documento;
        this.myDB = myDB;
        this.usuarioLogin = usuarioLogin;

    }

    @Override
    protected Boolean doInBackground(String... params) {

        if(miembrosHogar!=null){

            if(miembrosHogar.size()<1){
                if(usuarioLogin.getIdPerfil().equals("710")){

                    if(Tipo.equals("DOCUMENTO")){
                        lsResultadoPersonasEncuestadas = myDB.getListaPersonasEncuestadas(Documento);
                        if(lsResultadoPersonasEncuestadas.size()>0){
                            lsResultado = myDB.getlistaVictimasDocumento(lsResultadoPersonasEncuestadas);
                        }else{
                            lsResultado = myDB.getlistaVictimasDocumento(Documento);
                        }

                    }
                    else{
                        lsResultado = myDB.getlistaVictimasHogar(Documento);
                    }

                }else{
                    if(Tipo.equals("DOCUMENTO")){
                        lsResultado = myDB.getlistaVictimasDocumento(Documento);
                    }

                }
            }else{
                if(usuarioLogin.getIdPerfil().equals("710")){

                    if(Tipo.equals("DOCUMENTO")){
                        lsResultadoPersonasEncuestadas = myDB.getListaPersonasEncuestadas(miembrosHogar);
                        if(lsResultadoPersonasEncuestadas.size()>0){
                            lsResultado = myDB.getlistaVictimasDocumento(lsResultadoPersonasEncuestadas);
                        }else{
                            lsResultado = myDB.getlistaVictimasDocumento(Documento);
                        }

                    }
                    else{
                        lsResultado = myDB.getlistaVictimasHogar(Documento);
                    }

                }else{
                    if(Tipo.equals("DOCUMENTO")){
                        lsResultado = myDB.getlistaVictimasDocumento(Documento);
                    }

                }
            }

        }else{
            if(usuarioLogin.getIdPerfil().equals("710")){

                if(Tipo.equals("DOCUMENTO")){
                    lsResultadoPersonasEncuestadas = myDB.getListaPersonasEncuestadas(Documento);
                    if(lsResultadoPersonasEncuestadas.size()>0){
                        lsResultado = myDB.getlistaVictimasDocumento(lsResultadoPersonasEncuestadas);
                    }else{
                        lsResultado = myDB.getlistaVictimasDocumento(Documento);
                    }

                }
                else{
                    lsResultado = myDB.getlistaVictimasHogar(Documento);
                }

            }else{
                if(Tipo.equals("DOCUMENTO")){
                    lsResultado = myDB.getlistaVictimasDocumento(Documento);
                }

            }
        }



        return true;
    }

    @Override
    protected void onProgressUpdate(Integer... progress){
    }

    @Override
    protected void onPostExecute(Boolean result){
        callback.resultadoVictimas(lsResultado,lsResultadoPersonasEncuestadas);
    }
}

