package co.com.rni.encuestadormovil;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.com.rni.encuestadormovil.adapter.listaEncuestasAdapter;
import co.com.rni.encuestadormovil.adapter.listaResumenEncuestasAdapter;
import co.com.rni.encuestadormovil.adapter.resumenEncuestasAdapter;
import co.com.rni.encuestadormovil.model.emc_hogares;
import co.com.rni.encuestadormovil.model.emc_resumen_encuestas_x_usuario;
import co.com.rni.encuestadormovil.model.emc_validadores_persona;
import co.com.rni.encuestadormovil.model.item_resumen_encuestas;


/**
 * Created by ASUS on 25/04/2017.
 */

public class resumen_encuestas_por_usuario extends AppCompatActivity {

    private ActionBar abActivity;
    private RecyclerView recyclerView;
    private resumenEncuestasAdapter resumenEncuestasAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<item_resumen_encuestas> listaHoGB = new ArrayList<>();
    private LinearLayout llVolverPaginaPrincipal;
    private ListView lvEncuestas;
    private listaResumenEncuestasAdapter leAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_resumen_encuestas);

        abActivity = getSupportActionBar();
        abActivity.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.emc_red)));
        abActivity.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
        abActivity.setTitle("Encuestas por usuario");

        abActivity.setDisplayHomeAsUpEnabled(true);
        abActivity.setHomeButtonEnabled(true);
        abActivity.setHomeAsUpIndicator(R.mipmap.ic_launcher);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        llVolverPaginaPrincipal = (LinearLayout) findViewById(R.id.llVolverPaginaPrincipal);

        recyclerView = (RecyclerView) findViewById (R.id.recyclerView);
        resumenEncuestasAdapter = new resumenEncuestasAdapter(this);
        recyclerView.setAdapter(resumenEncuestasAdapter);
        //recyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(this,1);
        //layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        emc_validadores_persona.deleteAll(emc_validadores_persona.class,
                "UPPER(hogcodigo) NOT IN (select DISTINCT UPPER(h.hogcodigo) FROM emchogares h, EMCMIEMBROSHOGAR m WHERE h.hogcodigo=m.hogcodigo )",
                null);

        emc_hogares.deleteAll(emc_hogares.class,
                "UPPER(hogcodigo) not in (select distinct UPPER(h.hogcodigo) FROM emchogares h, EMCMIEMBROSHOGAR m where h.hogcodigo=m.hogcodigo )",
                null);

        emc_resumen_encuestas_x_usuario.deleteAll(emc_resumen_encuestas_x_usuario.class);

        List<emc_hogares> lsHogares = emc_hogares.find(emc_hogares.class, "USUUSUARIOCREACION <> ''", null);

        ArrayList<emc_resumen_encuestas_x_usuario> listaEncuestas = new ArrayList<>();
        if(lsHogares.size()>0){

            String insertarEnResumenEncuestas = "INSERT INTO EMCRESUMENENCUESTASXUSUARIO (USUARIO,ESTADO,TOTAL)\n" +
                    " SELECT UPPER(HG.USUUSUARIOCREACION) USUUSUARIOCREACION, UPPER(HG.ESTADO) ESTADO, COUNT(HG.ESTADO) TOTAL FROM\n" +
                    "(SELECT * FROM EMCHOGARES HO, EMCMIEMBROSHOGAR MH WHERE HO.HOGCODIGO=MH.HOGCODIGO)  HG  WHERE HG.INDJEFE = 'SI' GROUP BY HG.USUUSUARIOCREACION, HG.ESTADO;";

            emc_resumen_encuestas_x_usuario.executeQuery(insertarEnResumenEncuestas);

            List<emc_resumen_encuestas_x_usuario> lsHogaresGB = emc_resumen_encuestas_x_usuario.find(emc_resumen_encuestas_x_usuario.class,
                    "USUARIO <> '' ORDER BY USUARIO DESC", null);


            for(int cHog = 0; cHog < lsHogaresGB.size(); cHog++){

                emc_resumen_encuestas_x_usuario tmHogar = lsHogaresGB.get(cHog);
                item_resumen_encuestas itemRE = new item_resumen_encuestas();
                itemRE.setUsuario(tmHogar.getUsuario());
                itemRE.setEstado(tmHogar.getEstado());
                itemRE.setTotal(tmHogar.getTotal());
                listaHoGB.add(itemRE);
                resumenEncuestasAdapter.adicionarListaREncuestas(listaHoGB);
                listaEncuestas.add(tmHogar);
            }

        }else{
            Toast.makeText(getBaseContext(), "No hay encuestas Creadas", Toast.LENGTH_SHORT).show();
        }

        lvEncuestas = (ListView) findViewById(R.id.lvEncuestas);
        checkScreenSize();
        leAdapter = new listaResumenEncuestasAdapter(getApplicationContext(), listaEncuestas);
        lvEncuestas.setAdapter(leAdapter);

        llVolverPaginaPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent paginaprincipal = new Intent(v.getContext(), MainActivity.class);
                startActivity(paginaprincipal);
                finish();
            }
        });



    }

    public void checkScreenSize() {



        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;

        switch (screenSize) {

            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        900 );
                lvEncuestas.setLayoutParams(lp1);
                break;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                /*LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT );
                lvEncuestas.setLayoutParams(lp);
                */
                break;
            case Configuration.SCREENLAYOUT_SIZE_UNDEFINED:
                //lvEncuestas.setLayoutParams(lp);
                break;
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        420 );
                lvEncuestas.setLayoutParams(lp3);
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                //lvEncuestas.setLayoutParams(lp);
                break;
            default:
                Toast.makeText(getBaseContext(), "Screen no definido: ", Toast.LENGTH_SHORT).show();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener  = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent irMainActividy = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(irMainActividy);
                    finish();
                    return true;
                case R.id.navigation_dashboard:

                    return true;
                case R.id.navigation_notifications:

                    return true;
            }
            return false;
        }
    };

    @Override
    // Detectar cuando se presiona el botón de retroceso
    public void onBackPressed() {
        Intent irMain = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(irMain);
        finish();
    }
}
