package co.com.rni.encuestadormovil;



import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.com.rni.encuestadormovil.adapter.*;
import co.com.rni.encuestadormovil.model.*;
import co.com.rni.encuestadormovil.sqlite.DbHelper;
import co.com.rni.encuestadormovil.util.*;
import co.com.rni.encuestadormovil.configuracionencuestas.*;


import static co.com.rni.encuestadormovil.util.general.borrarEncuestasDuplicadasConDiferenteEstado;
import static co.com.rni.encuestadormovil.util.general.borrarEncuestasRepetidas;
import static co.com.rni.encuestadormovil.util.general.isNetworkConnected;



public class MainActivity extends AppCompatActivity {

    private ActionBar abActivity;
    private ListView lvDrawerOpciones;
    private List<opcionMenu> opciones;
    private drawerAdapter aDrawer;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mTitle;
    private CharSequence mDrawerTitle;
    private ListView lvEncuestas;
    private LinearLayout llAgregar;
    private TextView tvUsuario;
    private TextView tvFecLogin;
    private TextView tvFecToken;
    private TextView tvCerrarSesion;
    private TextView tvVersionBD;

    private emc_usuarios usuarioLogin;
    private LinearLayout llTransfer;
    private LinearLayout llTransferidas;
    private LinearLayout llMensajePrimerUso;
    private ProgressDialog pg;
    public  ProgressDialog pgC;
    private listaEncuestasAdapter leAdapter;
    private LinearLayout llverAdminEncuestas;
    private LinearLayout llOpciones;
    private DbHelper myDB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        abActivity = getSupportActionBar();
        abActivity.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.emc_red)));
        abActivity.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
        abActivity.setTitle("Gestor de encuestas");

        myDB = new DbHelper(this);
        abActivity.setDisplayHomeAsUpEnabled(true);
        abActivity.setHomeButtonEnabled(true);
        abActivity.setHomeAsUpIndicator(R.mipmap.ic_launcher);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.menu_main, null);
        abActivity.setCustomView(mCustomView);
        abActivity.setDisplayShowCustomEnabled(true);

        mTitle = mDrawerTitle = abActivity.getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.MainActivity);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.mipmap.ic_launcher, R.string.open_drawer, R.string.close_drawer)
        {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();

            }
        };


        mDrawerLayout.setDrawerListener(mDrawerToggle);

        lvDrawerOpciones = (ListView) findViewById(R.id.lvDrawerOpciones);
        opciones = cargarListaOpciones();
        aDrawer = new drawerAdapter(this, opciones);
        lvDrawerOpciones.setAdapter(aDrawer);


        lvDrawerOpciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                opcionMenu idOpcionmenu =  opciones.get(position);
                if(idOpcionmenu.getTexto().equals("Tablas parametricas")){
                    if(usuarioLogin.getIdPerfil().equals("499")){
                        Intent renpconfe = new Intent(view.getContext(), Agregartema.class);
                        startActivity(renpconfe);
                        finish();
                    }else{
                        Toast.makeText(getBaseContext(), "Opción activada solo para el administrador", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getBaseContext(), "No hay evento asignado para "+idOpcionmenu.getTexto(), Toast.LENGTH_SHORT).show();
                }

                mDrawerLayout.closeDrawer(Gravity.LEFT);

            }
        });


        usuarioLogin = Session.LoadCookie(getApplication());
        pg = new ProgressDialog(this);
        pgC = new ProgressDialog(this);
        llverAdminEncuestas = (LinearLayout) findViewById(R.id.llverAdminEncuestas) ;
        llverAdminEncuestas.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent renpusu = new Intent(v.getContext(), resumen_encuestas_por_usuario.class);
                startActivity(renpusu);
                finish();


            }
        });
        llAgregar = (LinearLayout) mCustomView.findViewById(R.id.llAgregar);
        llAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent creaHogar = new Intent(v.getContext(), conformarHogar.class);
                startActivity(creaHogar);
                finish();


            }
        });

        borrarEncuestasRepetidas();
        borrarEncuestasDuplicadasConDiferenteEstado();


        ArrayList<itemEncuesta> listaEncuestas = new ArrayList<>();
        List<emc_hogares> lsHogares = emc_hogares.find(emc_hogares.class, "USUUSUARIOCREACION = '" + usuarioLogin.getNombreusuario() + "' AND ESTADO <> 'TRANSMITIDA' AND ESTADO <> 'DUPLICADA'", null);
        for(int cHog = 0; cHog < lsHogares.size(); cHog++){

            emc_hogares tmHogar = lsHogares.get(cHog);
            itemEncuesta tmEnc = new itemEncuesta();
            List<emc_miembros_hogar> lsResponsableHogar = emc_miembros_hogar.find(emc_miembros_hogar.class, "hogcodigo = '" + tmHogar.getHog_codigo() + "'" + "and indjefe = 'SI'", null);
            if(lsResponsableHogar!=null && lsResponsableHogar.size() > 0){
                emc_miembros_hogar tmHogarP = lsResponsableHogar.get(0);
                tmEnc.setIdPersona(tmHogarP.getNombre1()+" "+tmHogarP.getNombre2()+" "+tmHogarP.getApellido1()+" "+tmHogarP.getApellido2());
                tmEnc.setIdHogar(tmHogar.getHog_codigo());
                tmEnc.setFecha(tmHogar.getUsu_fechacreacion());
                tmEnc.setEstado(tmHogar.getEstado());
                listaEncuestas.add(tmEnc);
            }

        }

        listaEncuestasResponse callback = new listaEncuestasResponse() {
            @Override
            public void onHogarSelected(String hogar) {
                List<emc_hogares> lsHogares = emc_hogares.find(emc_hogares.class, "HOGCODIGO = '" + hogar + "';", null);
                if(lsHogares.size() == 1){
                    Session.SaveHogarActual(getApplication(), lsHogares.get(0));
                    Intent dilPregunta = new Intent(getBaseContext(), DiligenciarPregunta.class);
                    startActivity(dilPregunta);
                }
            }
        };

        LinearLayout llParametricas = (LinearLayout) findViewById(R.id.llParametricas);


        llParametricas.setBackground(getDrawable(R.drawable.border_radius_red));


        llTransfer = (LinearLayout) findViewById(R.id.llTransfer);
        llTransferidas = (LinearLayout) findViewById(R.id.llTransferidas);
        llMensajePrimerUso = (LinearLayout) findViewById(R.id.llMensajePrimerUso);

        List<emc_respuestas> lsRespuetas = emc_respuestas.find(emc_respuestas.class, null, null);
        if(lsRespuetas.size() >= 1000)
        {
            llTransferidas.setVisibility(View.VISIBLE);
            llTransfer.setVisibility(View.VISIBLE);
            llverAdminEncuestas.setVisibility(View.VISIBLE);
            llAgregar.setVisibility(View.VISIBLE);
            llMensajePrimerUso.setVisibility(View.GONE);
            llParametricas.setVisibility(View.GONE);
        }
        else{
            llTransferidas.setVisibility(View.GONE);
            llTransfer.setVisibility(View.GONE);
            llverAdminEncuestas.setVisibility(View.GONE);
            llMensajePrimerUso.setVisibility(View.VISIBLE);
            llAgregar.setVisibility(View.GONE);
        }

        llMensajePrimerUso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent parametricas = new Intent(v.getContext(), Parametricas.class);
                startActivity(parametricas);
                finish();

            }
        });

        llParametricas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent parametricas = new Intent(v.getContext(), Parametricas.class);
                startActivity(parametricas);
                finish();

            }
        });
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT );


        lvEncuestas = (ListView) findViewById(R.id.lvEncuestas);

        //checkScreenSize(lp);
        checkScreenSize();

        leAdapter = new listaEncuestasAdapter(this, listaEncuestas, callback);
        lvEncuestas.setAdapter(leAdapter);




        tvUsuario = (TextView) findViewById(R.id.tvUsuario);
        tvUsuario.setText(usuarioLogin.getNombreusuario());
        tvFecLogin = (TextView) findViewById(R.id.tvFecLogin);
        tvFecLogin.setText("Ingreso: " + usuarioLogin.getFeclogin());
        tvFecToken = (TextView) findViewById(R.id.tvFecToken);
        tvFecToken.setText("Expira: " + usuarioLogin.getFecvalidatoken());
        tvCerrarSesion = (TextView) findViewById(R.id.tvCerrarSesion);
        tvVersionBD = (TextView) findViewById(R.id.tvVersionBD);
        String[] paramsV = {"BASE"};
        //final List<emc_version> tmIdVersion = emc_version.find(emc_version.class, "vernombre = ?", paramsV);

        final int tmIdVersion = myDB.getlistaVesionBaseRuv("BASE");
        tvVersionBD.setText("Versión base : " + tmIdVersion);

        tvCerrarSesion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
        {

            Intent ListSong = new Intent(getApplicationContext(), Login.class);
            startActivity(ListSong);
            finish();

        }
        });



        llTransferidas.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                encuestasTransmitidas();
            }
        });

        llTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //pgC.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                int con = isNetworkConnected(getBaseContext());
                if(con == 1){
                    pgC.setTitle("Transfiriendo encuesta");
                    pgC.setMessage("Procesando...");
                    pgC.setCancelable(false);
                    /*pgC.setMax(100);
                    pgC.setProgress(0);*/
                    pgC.show();
                    responseTestNet callbackT = new responseTestNet() {
                        @Override
                        public void testFinalizado(boolean exito) {

                            if(exito) {
                                //pgC.dismiss();
                                int val = 0;
                                val = isNetworkConnected(getBaseContext());
                                if (val == 1) {
                                    responseUpload callback = new responseUpload() {
                                        @Override
                                        public void actualizaEstado(String mensaje) {
                                            //pg.setMessage(mensaje);
                                        }

                                        @Override
                                        public void cargaFinalizada(boolean exito) {
                                            finalizarCarga(exito);
                                        }
                                    };
                                    String filename = usuarioLogin.getNombreusuario() + "_" + general.fechaActual().replace("/", "-") + ".txt";
                                    filename = filename.replace("-", "_");
                                    filename = filename.replace(" ", "_");
                                    filename = filename.replace(":", "_");
                                    asyncUploadFile auFTP = new asyncUploadFile(getBaseContext(), callback, "ftp.unidadvictimas.gov.co", "encuestadormovil", "Kart3g3na", filename, usuarioLogin);
                                    auFTP.execute();
                                } else if (val == 2) {
                                    Toast.makeText(getBaseContext(), "Esta conectado por Datos, debe conectarse a una red WIFI", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getBaseContext(), "Sin conexión a internet, favor verificar", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                try{
                                    pgC.dismiss();
                                }catch (Exception e){
                                    e.getMessage();
                                }

                                Toast.makeText(getBaseContext(), "Sin conexión a internet, no fue posible transferir las encuestas", Toast.LENGTH_SHORT).show();
                            }

                        }
                    };

                    asyncTestNet auTN = new asyncTestNet(getBaseContext(),callbackT);
                    auTN.execute();

                }
                else if (con == 2){
                    Toast.makeText(getBaseContext(), "Esta conectado por Datos, debe conectarse a una red WIFI", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getBaseContext(), "Sin conexión a internet, favor verificar", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    private void finalizarCarga(boolean exito){

        try{
            pgC.dismiss();
            pg.hide();
            pg.dismiss();
        }catch (Exception e){
            e.getMessage();
        }

        if(exito){
            Toast.makeText(getBaseContext(), "Archivo transferido exitosamente", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getBaseContext(), "Ocurrio un error en la transmisión o no hay archivos para transmitir o no tiene conexión a internet", Toast.LENGTH_SHORT).show();
        }

        ArrayList<itemEncuesta> listaEncuestas = new ArrayList<>();
        List<emc_hogares> lsHogares = emc_hogares.find(emc_hogares.class, "USUUSUARIOCREACION = '" + usuarioLogin.getNombreusuario() + "' AND ESTADO <> 'TRANSMITIDA'", null);
        //List<emc_hogares> lsHogares = emc_hogares.find(emc_hogares.class, "USUUSUARIOCREACION = '" + usuarioLogin.getNombreusuario() + "'", null);
        for(int cHog = 0; cHog < lsHogares.size(); cHog++){

            emc_hogares tmHogar = lsHogares.get(cHog);
            itemEncuesta tmEnc = new itemEncuesta();
            List<emc_miembros_hogar> lsResponsableHogar = emc_miembros_hogar.find(emc_miembros_hogar.class, "hogcodigo = '" + tmHogar.getHog_codigo() + "'" + "and indjefe = 'SI'", null);
            if(lsResponsableHogar.size()>0){
                emc_miembros_hogar tmHogarP = lsResponsableHogar.get(0);
                tmEnc.setIdPersona(tmHogarP.getNombre1()+" "+tmHogarP.getNombre2()+" "+tmHogarP.getApellido1()+" "+tmHogarP.getApellido2());
                tmEnc.setIdHogar(tmHogar.getHog_codigo());
                tmEnc.setFecha(tmHogar.getUsu_fechacreacion());
                //tmEnc.setIdPersona(tmHogar.getUsu_usuariocreacion());
                tmEnc.setEstado(tmHogar.getEstado());
            }else{
                tmEnc.setIdPersona("NO SE HA REGISTRADO JEFE DE HOGAR");
                tmEnc.setIdHogar(tmHogar.getHog_codigo());
                tmEnc.setFecha(tmHogar.getUsu_fechacreacion());
                //tmEnc.setIdPersona(tmHogar.getUsu_usuariocreacion());
                tmEnc.setEstado(tmHogar.getEstado());
            }
            listaEncuestas.add(tmEnc);
        }
        leAdapter.actualizarRegistros(listaEncuestas);
        leAdapter.notifyDataSetChanged();
    }

    private void encuestasTransmitidas(){

        ArrayList<itemEncuesta> listaEncuestas = new ArrayList<>();
        List<emc_hogares> lsHogares = emc_hogares.find(emc_hogares.class, "USUUSUARIOCREACION = '" + usuarioLogin.getNombreusuario() + "' AND ESTADO = 'TRANSMITIDA'", null);
        //List<emc_hogares> lsHogares = emc_hogares.find(emc_hogares.class, "USUUSUARIOCREACION = '" + usuarioLogin.getNombreusuario() + "'", null);
        if(lsHogares.size()==0)
        {
            Toast.makeText(getBaseContext(), "No se encontraron encuestas trasmitidas para el usuario " + usuarioLogin.getNombreusuario().toUpperCase(), Toast.LENGTH_SHORT).show();
        }
        for(int cHog = 0; cHog < lsHogares.size(); cHog++){

            emc_hogares tmHogar = lsHogares.get(cHog);
            itemEncuesta tmEnc = new itemEncuesta();
            List<emc_miembros_hogar> lsResponsableHogar = emc_miembros_hogar.find(emc_miembros_hogar.class, "hogcodigo = '" + tmHogar.getHog_codigo() + "'" + "and indjefe = 'SI'", null);
            emc_miembros_hogar tmHogarP = lsResponsableHogar.get(0);
            tmEnc.setIdPersona(tmHogarP.getNombre1()+" "+tmHogarP.getNombre2()+" "+tmHogarP.getApellido1()+" "+tmHogarP.getApellido2());
            tmEnc.setIdHogar(tmHogar.getHog_codigo());
            tmEnc.setFecha(tmHogar.getUsu_fechacreacion());
            //tmEnc.setIdPersona(tmHogar.getUsu_usuariocreacion());
            tmEnc.setEstado(tmHogar.getEstado());
            //tmEnc.setPdf("Generar PDF");
            listaEncuestas.add(tmEnc);
        }
        leAdapter.actualizarRegistros(listaEncuestas);
        leAdapter.notifyDataSetChanged();
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

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...
        return super.onOptionsItemSelected(item);
    }



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener  = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

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

        Intent irLogin = new Intent(getApplicationContext(), Login.class);
        startActivity(irLogin);
        finish();

    }

    public List<opcionMenu> cargarListaOpciones(){
        List<opcionMenu> listadoOpciones = new ArrayList<>();
        listadoOpciones.add(new opcionMenu(getResources().getString(R.string.f_transfer), "Transmitir encuestas"));
        listadoOpciones.add(new opcionMenu(getResources().getString(R.string.f_parametricas), "Tablas parametricas"));
        listadoOpciones.add(new opcionMenu(getResources().getString(R.string.f_config), "Configuración"));
        return listadoOpciones;
    }

}
