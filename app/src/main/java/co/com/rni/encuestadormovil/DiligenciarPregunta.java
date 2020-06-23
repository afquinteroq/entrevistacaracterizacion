package co.com.rni.encuestadormovil;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import co.com.rni.encuestadormovil.adapter.*;
import co.com.rni.encuestadormovil.model.*;
import co.com.rni.encuestadormovil.util.*;


import static co.com.rni.encuestadormovil.util.general.fechaActualSinHora;
import static co.com.rni.encuestadormovil.util.general.isNumeric;
import static co.com.rni.encuestadormovil.util.general.redimensionarImagenMaximo;

public class DiligenciarPregunta extends AppCompatActivity /* implements View.OnClickListener*/{

    private Context TheThis;
    private ActionBar abActivity;
    private ListView lvDrawer;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private drawerTemasAdapter aDrawer;
    private LinearLayout lyInicial;
    private LinearLayout lyContent;
    private LinearLayout lyFinTema;
    private List<emc_temas> listaTemas;
    private TextView tvIdTema;
    private TextView tvTextoPregunta;
    private TextView tvObservacion;
    private TextView tituloVereda;
    private String hogCodigo;
    private ArrayList<LinearLayout> listaPreguntas;
    private LayoutInflater mInflater;
    private Integer idPreguntaActual;
    private emc_temas temSeleccionado;
    private LinearLayout llMostrarPregunta;
    private LinearLayout llPreguntaTexto;
    private emc_usuarios usuarioLogin;
    private LinearLayout llDeptoMun;
    private LinearLayout llDTDireccionTerritorial, llDTdepartamento, llDTPuntoAtencion, llDTMunicipio;
    private TextView tituloDireccionTerritorial, tituloDTdepartamento, tituloDTPuntoAtencion, tituloDTMunicipio;
    private LinearLayout llResguardo;
    private EditText etNom1, etNom2, etApl1, etApl2;
    private LinearLayout llNomsApls;
    private Spinner spDepto;
    private Spinner spMunicipio;
    private Spinner spResguardos;
    private Spinner spDTDireccionTerritorial, spDTdepartamento, spDTPuntoAtencion, spDTMunicipio;
    private List<emc_municipio> lsMunicipios;
    private municipiosAdapter adMunicipio;
    private String pIDJEFE = null;
    private List<emc_miembros_hogar> lsMiembrosHogar;
    private ArrayList<respuestasRatioAdapter> lsRatioAdapterGen;
    private ArrayList<respuestasCheckAdapter> lsCheckAdapterGen;
    private ArrayList<ArrayList<respuestasIdValor>> lsRespuestasGen;
    private ArrayList<ArrayList<String>> lsCheckOtro;
    private ArrayList<respuestasIdValor> alRespuestas;
    private ArrayList<String> valIdRespuestasRatio, valOtroRespuestasRatio;
    private LinearLayout llSiguiente;
    private LinearLayout llAnterior;
    private LinearLayout llReiniciarCapitulo;
    private LinearLayout llFinalizarEncuesta;
    private LinearLayout llRespuestas;
    private Integer contadorRespuestasRatio;
    private Integer contadorRespuestasCheck;
    private List<emc_departamento> lsDeptos;
    private List<EMC_DTPUNTOSATENCION> lsDts;
    private List<EMC_DTPUNTOSATENCION> lsdeptoDts, lsPuntosAtencion, lsmunsDts;
    private List<emc_resguardosindigenas> lsResguardos;
    private List<emc_ruinosas_catastroficas> lsEnfermedades;
    private List<emc_veredas> lsVeredas;
    private veredasAdapter adVeredas;
    private List<emc_comunidadesnegras> lsComunidadesNegras;
    private deptosAdapter adDepto;
    private dtsAdapter adDTS;
    private deptosDTAdapter adDeptosDT;
    private puntosDTAdapter adPuntosDT;
    private municipiosDTAdapter adMunsDT;
    private resguardosAdapter adResguardo;
    private enfermedadesAdapter adEnfermedades;
    private comunidadesnegrasAdapter adComunidadesNegras;
    private RadioGroup rgEstado;
    private LinearLayout llSalirEntrevista;
    private LinearLayout llConfirmarSalirEncuesta;
    private LinearLayout llConfirmarReiniciarCapitulo;
    private Button btnSalir, btnCancelar;
    private Button btnConfirmarSalirEncuesta, btnCancelarSalirEncuesta;
    private Button btnTomarImagenSoporte, btnSalvarImagenSoporte, btnCancelarTomarImagenSoporte;
    private LinearLayout llTomarImagenSoporte;
    private ImageView ivCamera = null;
    emc_respuestas_instrumento emcrespuestainstrumento = null;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Uri imageUri;
    private EditText textOtro;
    private item_nombre_completo nombreC = null;
    public ProgressDialog pgDMensaje;
    private LinearLayout llMensajeInicioCapituloAcrnur;
    private TextView tvMensajeInicioCapituloAcrnur;

    private Button btnbtnSalirReinicio, btnCancelarReinicio;
    private emc_hogares hogarActual;

    private static final String CONS_CAPITULO_YA_FUE_DILIGENCIADO = "El capítulo ya fue diligenciado";
    private static final String CONS_ESTADO_CERRADA = "Cerrada";
    private static final String CONS_ESTADO_INCOMPLETA = "Incompleta";
    private static final String CONS_ESTADO_ANULADA = "Anulada";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diligenciar_pregunta);

        usuarioLogin = Session.LoadCookie(getApplication());
        hogarActual = Session.LoadHogarActual(getApplication());
        hogCodigo = hogarActual.getHog_codigo();
        abActivity = getSupportActionBar();
        abActivity.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.emc_red)));
        abActivity.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
        abActivity.setTitle("Pregunta");

        abActivity.setDisplayHomeAsUpEnabled(true);
        abActivity.setHomeButtonEnabled(true);
        abActivity.setHomeAsUpIndicator(R.drawable.ic_drawer);
        pgDMensaje = new ProgressDialog(this);
        llMensajeInicioCapituloAcrnur = (LinearLayout) findViewById(R.id.llMensajeInicioCapituloAcrnur);
        tvMensajeInicioCapituloAcrnur = (TextView) findViewById(R.id.tvMensajeInicioCapituloAcrnur);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.DiligenciarPregunta);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.mipmap.ic_launcher, R.string.open_drawer, R.string.close_drawer)
        {
            public void onDrawerClosed(View view) {

                /*String[] parCap = {hogCodigo, temSeleccionado.getTem_idtema().toString()};
                List<emc_capitulos_terminados> lsCapTer = emc_capitulos_terminados.find(emc_capitulos_terminados.class, "HOGCODIGO = ? AND TEMIDTEMA = ?", parCap);
                if(lsCapTer.size() > 0)
                {
                    gestionEncuestas.reiniciarCapitulo(hogCodigo, temSeleccionado.getTem_idtema(), 1);
                }*/

                invalidateOptionsMenu();
                abActivity.setTitle("Pregunta");
                llFinalizarEncuesta.setVisibility(View.GONE);

                if(llSiguiente != null)
                    llSiguiente.setVisibility(View.VISIBLE);
                    llReiniciarCapitulo.setVisibility(View.VISIBLE);
            }

            public void onDrawerOpened(View drawerView) {
                abActivity.setTitle("Temas  ");
                llFinalizarEncuesta.setVisibility(View.VISIBLE);

                llConfirmarReiniciarCapitulo.setVisibility(View.GONE);
                invalidateOptionsMenu();
                if(llSiguiente != null)
                    llSiguiente.setVisibility(View.GONE);
                    llAnterior.setVisibility(View.GONE);
                    llReiniciarCapitulo.setVisibility(View.GONE);

            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        lvDrawer = (ListView) findViewById(R.id.lvDrawer);


        List<emc_version> lsHo= emc_version.find(emc_version.class,"UPPER(VERNOMBRE) = ?",hogCodigo.toUpperCase());

        if(lsHo.size()>0){
            if(lsHo.get(0).getVer_version().equals("ACNUR")){
                listaTemas = emc_temas.find(emc_temas.class, "TEMIDTEMA IN ('34','35','36','37','38','39','40','41')",null);
            }else{
                listaTemas = emc_temas.find(emc_temas.class, "TEMIDTEMA IN (SELECT P.TEMIDTEMA FROM EMCTEMAPERFILES P WHERE P.PERIDPERFIL = ?)", usuarioLogin.getIdPerfil());
            }
        }else{
            listaTemas = emc_temas.find(emc_temas.class, "TEMIDTEMA IN (SELECT P.TEMIDTEMA FROM EMCTEMAPERFILES P WHERE P.PERIDPERFIL = ?)", usuarioLogin.getIdPerfil());
        }

        /*if(lsHogar.size()>0){

            listaTemas = emc_temas.find(emc_temas.class, "TEMIDTEMA IN (SELECT P.TEMIDTEMA FROM EMCTEMAPERFILES P WHERE P.PERIDPERFIL = ?)", usuarioLogin.getIdPerfil());

        }*/

        /*if(lsHogar.size()>0){
            if(lsHogar.get(0).getHog_tipo().equals("B")){
                listaTemas = emc_temas.find(emc_temas.class, "TEMIDTEMA IN (SELECT P.TEMIDTEMA FROM EMCTEMAPERFILES P WHERE P.PERIDPERFIL = ?)", usuarioLogin.getIdPerfil());
            }else if (lsHogar.get(0).getHog_tipo().equals("A") && usuarioLogin.getIdPerfil().equals("710")){
                listaTemas = emc_temas.find(emc_temas.class, "TEMIDTEMA IN (SELECT P.TEMIDTEMA FROM EMCTEMAPERFILES P WHERE P.PERIDPERFIL = ?)", "500");
            }
            else{
                listaTemas = emc_temas.find(emc_temas.class, "TEMIDTEMA IN (SELECT P.TEMIDTEMA FROM EMCTEMAPERFILES P WHERE P.PERIDPERFIL = ?)", usuarioLogin.getIdPerfil());
            }
        }else{
            listaTemas = emc_temas.find(emc_temas.class, "TEMIDTEMA IN (SELECT P.TEMIDTEMA FROM EMCTEMAPERFILES P WHERE P.PERIDPERFIL = ?)", usuarioLogin.getIdPerfil());
        }
        */


        //listaTemas = emc_temas.find(emc_temas.class, "TEMIDTEMA IN (SELECT P.TEMIDTEMA FROM EMCTEMAPERFILES P WHERE P.PERIDPERFIL = ?)", usuarioLogin.getIdPerfil());
        aDrawer = new drawerTemasAdapter(this, listaTemas, hogCodigo, usuarioLogin.getNombreusuario());

        lvDrawer.setAdapter(aDrawer);


        llSalirEntrevista = (LinearLayout) findViewById(R.id.llSalirEntrevista);
        llConfirmarSalirEncuesta = (LinearLayout) findViewById(R.id.llConfirmarSalirEncuesta);
        llConfirmarReiniciarCapitulo =  (LinearLayout) findViewById(R.id.llConfirmarReiniciarCapitulo);
        llTomarImagenSoporte = (LinearLayout) findViewById(R.id.llTomarImagenSoporte);
        rgEstado = (RadioGroup) findViewById(R.id.rgEstado);
        btnSalir = (Button) findViewById(R.id.btnSalir);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        btnConfirmarSalirEncuesta = (Button) findViewById(R.id.btnConfirmarSalirEncuesta);
        btnCancelarSalirEncuesta = (Button) findViewById(R.id.btnCancelarSalirEncuesta);
        btnbtnSalirReinicio = (Button) findViewById(R.id.btnSalirReinicio);
        btnCancelarReinicio = (Button) findViewById(R.id.btnCancelarReinicio);
        btnTomarImagenSoporte = (Button) findViewById(R.id.btnTomarImagenSoporte);
        btnSalvarImagenSoporte = (Button) findViewById(R.id.btnSalvarImagenSoporte);
        btnCancelarTomarImagenSoporte = (Button) findViewById(R.id.btnCancelarTomarImagenSoporte);
        ivCamera = (ImageView) findViewById(R.id.ivCamera);


        lvDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                boolean val = true;
                idPreguntaActual = 1;
                temSeleccionado = (emc_temas) listaTemas.get(position);

                int idtema = temSeleccionado.getTem_idtema();


                if(usuarioLogin.getIdPerfil().equals("710") && idtema==34 ){
                //if( idtema==1 ){
                    List<emc_miembros_hogar> lmhT = null;
                    lmhT = emc_miembros_hogar.find(emc_miembros_hogar.class,"INDJEFE = 'SI' and UPPER(HOGCODIGO) = '"+hogCodigo.toUpperCase()+"';",null);

                    StringBuilder builder = new StringBuilder();
                    builder.append("Sr/ Sra ");
                    builder.append(lmhT.get(0).nombre1);
                    builder.append(" ");
                    builder.append(lmhT.get(0).nombre2);
                    builder.append(" ");
                    builder.append(lmhT.get(0).apellido1);
                    builder.append(" ");
                    builder.append(lmhT.get(0).apellido2);
                    builder.append(", ahora pasaremos al módulo final de la encuesta, en donde las preguntas se enfocarán en sus opiniones personales,");

                    tvMensajeInicioCapituloAcrnur.setText(builder);
                    /*
                    tvMensajeInicioCapituloAcrnur.setText("Sr/ Sra "+lmhT.get(0).nombre1+" "+lmhT.get(0).nombre2+" "+lmhT.get(0).apellido1+" "+lmhT.get(0).apellido2+
                            ", ahora pasaremos al módulo final de la encuesta, en donde las preguntas se enfocarán en sus opiniones personales,");
                    */
                    llMensajeInicioCapituloAcrnur.setVisibility(View.VISIBLE);
                }

                llMostrarPregunta.setVisibility(View.VISIBLE);
                llPreguntaTexto.setVisibility(View.VISIBLE);
                llRespuestas.setVisibility(View.VISIBLE);
                llReiniciarCapitulo.setVisibility(View.GONE);
                llFinalizarEncuesta.setVisibility(View.GONE);
                llSalirEntrevista.setVisibility(View.GONE);


                /*
                String[] parametros = {usuarioLogin.getIdPerfil(), temSeleccionado.getTem_idtema().toString()};
                List<emc_tema_perfiles> tmP = emc_tema_perfiles.find(emc_tema_perfiles.class, "PERIDPERFIL = ? AND TEMIDTEMA = ?", parametros);
                if (tmP.size() < 1) {
                    Toast.makeText(getBaseContext(), "Seleccione un capítulo", Toast.LENGTH_LONG).show();
                }else{*/
                if(idtema ==2){
                    String[] parCapT = {hogCodigo};
                    List<emc_capitulos_terminados> lsCapTerT = emc_capitulos_terminados.find(emc_capitulos_terminados.class, "HOGCODIGO = ? ", parCapT);

                    if (lsCapTerT.size() >= 1) {
                        String[] parCap = {hogCodigo, temSeleccionado.getTem_idtema().toString()};
                        List<emc_capitulos_terminados> lsCapTer = emc_capitulos_terminados.find(emc_capitulos_terminados.class, "HOGCODIGO = ? AND TEMIDTEMA = ?", parCap);
                        if (lsCapTer.size() > 0) {
                            //gestionEncuestas.reiniciarCapitulo(hogCodigo, temSeleccionado.getTem_idtema(), 1);
                            Toast.makeText(getBaseContext(), CONS_CAPITULO_YA_FUE_DILIGENCIADO, Toast.LENGTH_LONG).show();
                            llFinalizarEncuesta.setVisibility(View.VISIBLE);
                            val = false;

                            List<emc_preguntas_persona> lsPreguntasPersona = gestionEncuestas.SP_BUSCAR_SIGUIENTE_PREGUNTA(hogCodigo, temSeleccionado.getTem_idtema(), 1, idPreguntaActual);
                            if (lsPreguntasPersona.size() > 0 && val == true) {
                                llSiguiente.setVisibility(View.VISIBLE);
                                llAnterior.setVisibility(View.GONE);
                                llFinalizarEncuesta.setVisibility(View.GONE);

                                llReiniciarCapitulo.setVisibility(View.VISIBLE);
                                mostrarPregunta(temSeleccionado, lsPreguntasPersona);
                            }
                        }

                        List<emc_preguntas_persona> lsPreguntasPersona = gestionEncuestas.SP_BUSCAR_SIGUIENTE_PREGUNTA(hogCodigo, temSeleccionado.getTem_idtema(), 1, idPreguntaActual);
                        if (lsPreguntasPersona.size() > 0 && val == true) {
                            llSiguiente.setVisibility(View.VISIBLE);
                            llAnterior.setVisibility(View.GONE);
                            llFinalizarEncuesta.setVisibility(View.GONE);
                            llReiniciarCapitulo.setVisibility(View.VISIBLE);
                            mostrarPregunta(temSeleccionado, lsPreguntasPersona);
                        }
                    }else {
                        Toast.makeText(getBaseContext(), "Primero debe diligenciar el primer capitulo", Toast.LENGTH_LONG).show();
                    }

                }else if(idtema == 3){
                    String[] parCapT = {hogCodigo};
                    List<emc_capitulos_terminados> lsCapTerT = emc_capitulos_terminados.find(emc_capitulos_terminados.class, "HOGCODIGO = ? ", parCapT);

                    if (lsCapTerT.size() >= 2) {
                        String[] parCap = {hogCodigo, temSeleccionado.getTem_idtema().toString()};
                        List<emc_capitulos_terminados> lsCapTer = emc_capitulos_terminados.find(emc_capitulos_terminados.class, "HOGCODIGO = ? AND TEMIDTEMA = ?", parCap);
                        if (lsCapTer.size() > 0) {
                            //gestionEncuestas.reiniciarCapitulo(hogCodigo, temSeleccionado.getTem_idtema(), 1);
                            Toast.makeText(getBaseContext(), CONS_CAPITULO_YA_FUE_DILIGENCIADO, Toast.LENGTH_LONG).show();
                            llFinalizarEncuesta.setVisibility(View.VISIBLE);
                            val = false;

                            List<emc_preguntas_persona> lsPreguntasPersona = gestionEncuestas.SP_BUSCAR_SIGUIENTE_PREGUNTA(hogCodigo, temSeleccionado.getTem_idtema(), 1, idPreguntaActual);
                            if (lsPreguntasPersona.size() > 0 && val == true) {
                                llSiguiente.setVisibility(View.VISIBLE);
                                llAnterior.setVisibility(View.GONE);
                                llFinalizarEncuesta.setVisibility(View.GONE);

                                llReiniciarCapitulo.setVisibility(View.VISIBLE);
                                mostrarPregunta(temSeleccionado, lsPreguntasPersona);
                            }
                        }

                        List<emc_preguntas_persona> lsPreguntasPersona = gestionEncuestas.SP_BUSCAR_SIGUIENTE_PREGUNTA(hogCodigo, temSeleccionado.getTem_idtema(), 1, idPreguntaActual);
                        if (lsPreguntasPersona.size() > 0 && val == true) {
                            llSiguiente.setVisibility(View.VISIBLE);
                            llAnterior.setVisibility(View.GONE);
                            llFinalizarEncuesta.setVisibility(View.GONE);
                            llReiniciarCapitulo.setVisibility(View.VISIBLE);
                            mostrarPregunta(temSeleccionado, lsPreguntasPersona);
                        }
                    }else {
                        Toast.makeText(getBaseContext(), "Primero debe diligenciar los dos primeros capítulos", Toast.LENGTH_LONG).show();
                    }
                }
                else if ((idtema > 3 && idtema < 24) || (idtema > 30 && idtema < 34) ) {
                        String[] parCapT = {hogCodigo};
                        List<emc_capitulos_terminados> lsCapTerT = emc_capitulos_terminados.find(emc_capitulos_terminados.class, "HOGCODIGO = ? ", parCapT);

                        if (lsCapTerT.size() >= 3) {
                            String[] parCap = {hogCodigo, temSeleccionado.getTem_idtema().toString()};
                            List<emc_capitulos_terminados> lsCapTer = emc_capitulos_terminados.find(emc_capitulos_terminados.class, "HOGCODIGO = ? AND TEMIDTEMA = ?", parCap);
                            if (lsCapTer.size() > 0) {
                                //gestionEncuestas.reiniciarCapitulo(hogCodigo, temSeleccionado.getTem_idtema(), 1);
                                Toast.makeText(getBaseContext(), CONS_CAPITULO_YA_FUE_DILIGENCIADO, Toast.LENGTH_LONG).show();
                                llFinalizarEncuesta.setVisibility(View.VISIBLE);
                                val = false;

                                List<emc_preguntas_persona> lsPreguntasPersona = gestionEncuestas.SP_BUSCAR_SIGUIENTE_PREGUNTA(hogCodigo, temSeleccionado.getTem_idtema(), 1, idPreguntaActual);
                                if (lsPreguntasPersona.size() > 0 && val == true) {
                                    llSiguiente.setVisibility(View.VISIBLE);

                                    llAnterior.setVisibility(View.GONE);
                                    llFinalizarEncuesta.setVisibility(View.GONE);

                                    llReiniciarCapitulo.setVisibility(View.VISIBLE);
                                    mostrarPregunta(temSeleccionado, lsPreguntasPersona);
                                }
                            }

                            List<emc_preguntas_persona> lsPreguntasPersona = gestionEncuestas.SP_BUSCAR_SIGUIENTE_PREGUNTA(hogCodigo, temSeleccionado.getTem_idtema(), 1, idPreguntaActual);
                            if (lsPreguntasPersona.size() > 0 && val == true) {
                                llSiguiente.setVisibility(View.VISIBLE);
                                llAnterior.setVisibility(View.GONE);
                                llFinalizarEncuesta.setVisibility(View.GONE);
                                llReiniciarCapitulo.setVisibility(View.VISIBLE);
                                mostrarPregunta(temSeleccionado, lsPreguntasPersona);
                            }


                        } else {
                            Toast.makeText(getBaseContext(), "Primero debe diligenciar los tres primeros capítulos", Toast.LENGTH_LONG).show();
                        }


                    }else {

                        String[] parCap = {hogCodigo, temSeleccionado.getTem_idtema().toString()};
                        List<emc_capitulos_terminados> lsCapTer = emc_capitulos_terminados.find(emc_capitulos_terminados.class, "HOGCODIGO = ? AND TEMIDTEMA = ?", parCap);
                        if (lsCapTer.size() > 0) {
                            //gestionEncuestas.reiniciarCapitulo(hogCodigo, temSeleccionado.getTem_idtema(), 1);
                            Toast.makeText(getBaseContext(), CONS_CAPITULO_YA_FUE_DILIGENCIADO, Toast.LENGTH_LONG).show();
                            llFinalizarEncuesta.setVisibility(View.VISIBLE);
                            val = false;

                        }

                        List<emc_preguntas_persona> lsPreguntasPersona = gestionEncuestas.SP_BUSCAR_SIGUIENTE_PREGUNTA(hogCodigo, temSeleccionado.getTem_idtema(), 1, idPreguntaActual);
                        if (lsPreguntasPersona.size() > 0 && val == true) {
                            llSiguiente.setVisibility(View.VISIBLE);
                            llAnterior.setVisibility(View.GONE);
                            llFinalizarEncuesta.setVisibility(View.GONE);
                            llReiniciarCapitulo.setVisibility(View.VISIBLE);
                            mostrarPregunta(temSeleccionado, lsPreguntasPersona);
                        }

                    }
            //}

            }
        });

        mDrawerLayout.openDrawer(Gravity.LEFT);
        abActivity.setTitle("Encuesta: " + hogarActual.getHog_codigo());

        lyInicial = (LinearLayout) findViewById(R.id.lyInicial);
        lyContent = (LinearLayout) findViewById(R.id.lyContent);
        lyFinTema = (LinearLayout) findViewById(R.id.lyFinTema);
        llMostrarPregunta = (LinearLayout) findViewById(R.id.llMostrarPregunta);

        mDrawerLayout.closeDrawer(Gravity.LEFT);
        mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.menu_pregunta, null);
        abActivity.setCustomView(mCustomView);
        abActivity.setDisplayShowCustomEnabled(true);
        tvIdTema = (TextView) findViewById(R.id.tvIdTema);
        llPreguntaTexto = (LinearLayout) findViewById(R.id.llPreguntaTexto);

        lsMiembrosHogar = emc_miembros_hogar.find(emc_miembros_hogar.class, "HOGCODIGO = ?", hogCodigo);
        for(int conM = 0; conM < lsMiembrosHogar.size(); conM++){
            emc_miembros_hogar mm = lsMiembrosHogar.get(conM);
            if(mm.getInd_jefe().equals("SI"))
                pIDJEFE = mm.getPer_idpersona();
        }
        llAnterior = (LinearLayout) mCustomView.findViewById(R.id.llAnterior);
        llAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mostrarPregunta(listaPreguntas, position-1, temActual);
            }
        });

        llReiniciarCapitulo = (LinearLayout) mCustomView.findViewById(R.id.llReiniciarCapitulo);
        llFinalizarEncuesta = (LinearLayout) mCustomView.findViewById(R.id.llFinalizarEncuesta);
        //checkScreenSize();
        llReiniciarCapitulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(temSeleccionado != null){
                    llMensajeInicioCapituloAcrnur.setVisibility(View.GONE);
                    llConfirmarReiniciarCapitulo.setVisibility(View.VISIBLE);
                    llSalirEntrevista.setVisibility(View.GONE);

                    llMostrarPregunta.setVisibility(View.GONE);
                    llPreguntaTexto.setVisibility(View.GONE);
                    llRespuestas.setVisibility(View.GONE);
                }else{
                    Toast.makeText(getBaseContext(), "Debe seleccionar un capítulo a reiniciar", Toast.LENGTH_LONG).show();
                }



                btnbtnSalirReinicio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        llConfirmarReiniciarCapitulo.setVisibility(View.GONE);
                        if(temSeleccionado != null){


                        gestionEncuestas.reiniciarCapitulo(hogCodigo, temSeleccionado.getTem_idtema(), 1);

                        Toast.makeText(getBaseContext(), "Reinicio el capítulo de "+temSeleccionado.getTem_nombretema()+" , recuerde diligenciarlo nuevamente" , Toast.LENGTH_LONG).show();
                        llRespuestas.removeAllViews();
                        if(listaPreguntas != null){
                                listaPreguntas.clear();
                          }
                        llSiguiente.setVisibility(View.GONE);
                        llAnterior.setVisibility(View.GONE);
                        llReiniciarCapitulo.setVisibility(View.GONE);
                        lyContent.setVisibility(View.GONE);
                        lyInicial.setVisibility(View.GONE);
                        lyFinTema.setVisibility(View.VISIBLE);
                        llMostrarPregunta.setVisibility(View.GONE);
                        llFinalizarEncuesta.setVisibility(View.VISIBLE);
                        mDrawerLayout.openDrawer(Gravity.LEFT);
                        llPreguntaTexto.setVisibility(View.GONE);
                        aDrawer.notifyDataSetChanged();

                        }else{
                            Toast.makeText(getBaseContext(), "Debe seleccionar un capítulo a reiniciar", Toast.LENGTH_LONG).show();
                        }

                    }
                });


                btnCancelarReinicio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        llMostrarPregunta.setVisibility(View.VISIBLE);
                        llPreguntaTexto.setVisibility(View.VISIBLE);
                        llRespuestas.setVisibility(View.VISIBLE);
                        llConfirmarReiniciarCapitulo.setVisibility(View.GONE);

                    }
                });

            }
        });


        llFinalizarEncuesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                llMensajeInicioCapituloAcrnur.setVisibility(View.GONE);
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                lyContent.setVisibility(View.VISIBLE);
                llConfirmarReiniciarCapitulo.setVisibility(View.GONE);
                llMostrarPregunta.setVisibility(View.GONE);
                llPreguntaTexto.setVisibility(View.GONE);
                llRespuestas.setVisibility(View.GONE);
                llSiguiente.setVisibility(View.GONE);
                llTomarImagenSoporte.setVisibility(View.GONE);
                llSalirEntrevista.setVisibility(View.VISIBLE);



                btnSalir.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String est = "";
                        if (rgEstado.getCheckedRadioButtonId() == R.id.estIncompleta)
                            est = CONS_ESTADO_INCOMPLETA;
                        if (rgEstado.getCheckedRadioButtonId() == R.id.estAnulada || rgEstado.getCheckedRadioButtonId() == R.id.estAnulaHogar)
                            est = CONS_ESTADO_ANULADA;
                        if (rgEstado.getCheckedRadioButtonId() == R.id.estCerrada)
                            est = CONS_ESTADO_CERRADA;

                        if (est.equals("")) {
                            Toast.makeText(getBaseContext(), "Debe seleccionar un estado", Toast.LENGTH_SHORT).show();
                        } else if (est.equals("CONS_ESTADO_CERRADA"))
                        {
                            String[] parCapT = {hogCodigo};
                            List<emc_capitulos_terminados> lsCapTerT = emc_capitulos_terminados.find(emc_capitulos_terminados.class, "HOGCODIGO = ? ", parCapT);

                            int totalCapitulosTerminado = lsCapTerT.size();

                            String idTem = "0";

                            if(totalCapitulosTerminado<1){

                                idTem = "0";

                            }else{

                             idTem = lsCapTerT.get(0).getTem_idtema().toString().toUpperCase();

                            }


                            if(totalCapitulosTerminado>0){

                                if (totalCapitulosTerminado>3/*lsCapTerT.size() > 3*/ || (idTem.equals("34".toUpperCase()) || idTem.equals("35".toUpperCase())
                                        || idTem.equals("36".toUpperCase()) || idTem.equals("37".toUpperCase())
                                        || idTem.equals("38".toUpperCase()) || idTem.equals("39".toUpperCase())
                                        || idTem.equals("40".toUpperCase()) || idTem.equals("41".toUpperCase())
                                        ))
                                {

                                    llSalirEntrevista.setVisibility(View.GONE);

                                    llConfirmarSalirEncuesta.setVisibility(View.VISIBLE);
                                    final String esT = est;

                                    btnConfirmarSalirEncuesta.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {


                                            //btnConfirmarSalirEncuesta.setOnClickListener(null);
                                            llConfirmarSalirEncuesta.setVisibility(View.GONE);
                                            llTomarImagenSoporte.setVisibility(View.VISIBLE);
                                            llSiguiente.setVisibility(View.GONE);




                                            btnTomarImagenSoporte.setOnClickListener(new View.OnClickListener(){
                                                @Override
                                                public void onClick(View V){
                                                /*Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                                                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                                                }*/

                                                    File dir = new File(Environment.getExternalStorageDirectory(), "Soportes");

                                                    if (!dir.exists()) {
                                                        dir.mkdirs();
                                                    }

                                                    String filename = Environment.getExternalStorageDirectory().getPath() + "/Soportes/testfile.png";
                                                    //imageUri = Uri.fromFile(new File(filename));
                                                    imageUri = FileProvider.getUriForFile(DiligenciarPregunta.this, DiligenciarPregunta.this.getApplicationContext().getPackageName() + ".co.com.rni.encuestadormovil.GenericFileProvider", new File(filename));

                                                    // start default camera
                                                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                                    cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
                                                            imageUri);
                                                    startActivityForResult (cameraIntent, REQUEST_IMAGE_CAPTURE);
                                                }
                                            });

                                            btnSalvarImagenSoporte.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                    BitmapDrawable drawable = (BitmapDrawable) ivCamera.getDrawable();
                                                    if(drawable == null)
                                                    {
                                                        Toast.makeText(getApplicationContext(), "No ha tomado la imagen de soporte", Toast.LENGTH_LONG).show();
                                                    }else{

                                                        Bitmap bitmap = drawable.getBitmap();
                                                        bitmap = redimensionarImagenMaximo(bitmap, 1440,2560);
                                                        pgDMensaje.setMessage("Guardando Soporte... " +hogCodigo);
                                                        pgDMensaje.show();
                                                        //String ruta = guardarImagen(getApplicationContext(), hogCodigo, bitmap);
                                                        //Toast.makeText(getApplicationContext(), ruta, Toast.LENGTH_LONG).show();
                                                        hiloGuardarImagen(bitmap,hogCodigo.trim() ,/*hogCodigo.trim() ,*/esT.trim());

                                                        /*saveImageToGallery();*/
                                                        hogarActual.setEstado(esT);
                                                        hogarActual.save();
                                                        llTomarImagenSoporte.setVisibility(View.GONE);
                                                        ivCamera.setImageDrawable(null);
                                                        Intent mainI = new Intent(getBaseContext(), MainActivity.class);
                                                        startActivity(mainI);
                                                        finish();

                                                    }

                                                }
                                            });

                                            btnCancelarTomarImagenSoporte.setOnClickListener(new View.OnClickListener(){
                                                @Override
                                                public void onClick(View v){
                                                    llConfirmarSalirEncuesta.setVisibility(View.VISIBLE);
                                                    llTomarImagenSoporte.setVisibility(View.GONE);
                                                    Toast.makeText(getBaseContext(), "Se cancelo la toma de soporte", Toast.LENGTH_SHORT).show();
                                                }
                                            });



                                        }});


                                    btnCancelarSalirEncuesta.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            llConfirmarSalirEncuesta.setVisibility(View.GONE);
                                            llSalirEntrevista.setVisibility(View.VISIBLE);

                                        }});

                                }else
                                {
                                    Toast.makeText(getBaseContext(), "Debe diligencia los tres primeros capítulos y un capítulo adicional", Toast.LENGTH_SHORT).show();
                                }

                            }else{
                                Toast.makeText(getBaseContext(), "No ha diligenciado ninguno capítulo", Toast.LENGTH_SHORT).show();
                            }


                        }
                        else {

                           llSalirEntrevista.setVisibility(View.GONE);
                           llConfirmarSalirEncuesta.setVisibility(View.VISIBLE);
                           final String esT = est;

                           btnConfirmarSalirEncuesta.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {


                                    hogarActual.setEstado(esT);
                                    hogarActual.save();
                                    //modificacion javier
                                    Intent mainI = new Intent(getBaseContext(), MainActivity.class);
                                    startActivity(mainI);
                                    finish();


                                }
                            });


                            btnCancelarSalirEncuesta.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    llConfirmarSalirEncuesta.setVisibility(View.GONE);
                                    llTomarImagenSoporte.setVisibility(View.GONE);
                                    llSalirEntrevista.setVisibility(View.VISIBLE);

                                }
                            });


                        }
                    }
                });

                btnCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lyContent.setVisibility(View.GONE);
                        llSalirEntrevista.setVisibility(View.GONE);

                        mDrawerLayout.openDrawer(Gravity.LEFT);

                    }
                });

            }
        });


        llSiguiente = (LinearLayout) mCustomView.findViewById(R.id.llSiguiente);
        llSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(respuestaValida()){
                    llMensajeInicioCapituloAcrnur.setVisibility(View.GONE);
                    insertaRespuestaXencuesta();
                    List<emc_preguntas_persona> lsPreguntasPersona = gestionEncuestas.SP_BUSCAR_SIGUIENTE_PREGUNTA(hogCodigo, temSeleccionado.getTem_idtema(), 1, idPreguntaActual);
                    if(emcrespuestainstrumento == null) {

                        if(lsPreguntasPersona.size() > 0){
                            mostrarPregunta(temSeleccionado, lsPreguntasPersona);
                        }
                        else{
                            //Capitulo terminado

                            Toast.makeText(getBaseContext(),"FINALIZO EL CAPITULO", Toast.LENGTH_LONG).show();
                            llRespuestas.removeAllViews();
                            listaPreguntas.clear();
                            llSiguiente.setVisibility(View.GONE);
                            llAnterior.setVisibility(View.GONE);
                            llReiniciarCapitulo.setVisibility(View.GONE);
                            lyContent.setVisibility(View.GONE);
                            lyInicial.setVisibility(View.GONE);
                            lyFinTema.setVisibility(View.VISIBLE);
                            llMostrarPregunta.setVisibility(View.GONE);
                            mDrawerLayout.openDrawer(Gravity.LEFT);
                            llPreguntaTexto.setVisibility(View.GONE);

                            gestionEncuestas.SP_FINALIZARCAPITULO(hogCodigo, temSeleccionado.getTem_idtema(), usuarioLogin.getNombreusuario());
                            aDrawer.notifyDataSetChanged();
                        }
                    }else
                        if (emcrespuestainstrumento.getPre_campotex() != null &&  emcrespuestainstrumento.getPre_campotex().equals("TR")){
                            //Capitulo terminado

                            Toast.makeText(getBaseContext(),"FINALIZO LA ENCUESTA", Toast.LENGTH_LONG).show();


                            llRespuestas.removeAllViews();
                            listaPreguntas.clear();
                            llSiguiente.setVisibility(View.GONE);
                            llAnterior.setVisibility(View.GONE);
                            llReiniciarCapitulo.setVisibility(View.GONE);
                            lyContent.setVisibility(View.GONE);
                            lyInicial.setVisibility(View.GONE);
                            lyFinTema.setVisibility(View.VISIBLE);
                            llMostrarPregunta.setVisibility(View.GONE);
                            mDrawerLayout.openDrawer(Gravity.LEFT);
                            llPreguntaTexto.setVisibility(View.GONE);
                            for(int conM = 24; conM <= 30; conM++){
                                gestionEncuestas.SP_FINALIZARCAPITULO(hogCodigo, conM/*temSeleccionado.getTem_idtema()*/, usuarioLogin.getNombreusuario());
                            }

                            aDrawer.notifyDataSetChanged();

                        }else{
                            if(lsPreguntasPersona.size() > 0){
                                mostrarPregunta(temSeleccionado, lsPreguntasPersona);
                            }
                            else{
                                //Capitulo terminado

                                Toast.makeText(getBaseContext(),"FINALIZO EL CAPITULO", Toast.LENGTH_LONG).show();
                                llRespuestas.removeAllViews();
                                listaPreguntas.clear();
                                llSiguiente.setVisibility(View.GONE);
                                llAnterior.setVisibility(View.GONE);
                                llReiniciarCapitulo.setVisibility(View.GONE);
                                lyContent.setVisibility(View.GONE);
                                lyInicial.setVisibility(View.GONE);
                                lyFinTema.setVisibility(View.VISIBLE);
                                llMostrarPregunta.setVisibility(View.GONE);
                                mDrawerLayout.openDrawer(Gravity.LEFT);
                                llPreguntaTexto.setVisibility(View.GONE);

                                gestionEncuestas.SP_FINALIZARCAPITULO(hogCodigo, temSeleccionado.getTem_idtema(), usuarioLogin.getNombreusuario());
                                aDrawer.notifyDataSetChanged();
                            }
                        }

                }
            }
        });
        mDrawerLayout.openDrawer(Gravity.LEFT);

        llRespuestas = (LinearLayout) findViewById(R.id.llRespuestas);
        lsRatioAdapterGen = new ArrayList<>();
        lsCheckAdapterGen = new ArrayList<>();
        lsRespuestasGen = new ArrayList<>();
        lsCheckOtro = new ArrayList<>();
        valIdRespuestasRatio = new ArrayList<>();
        valOtroRespuestasRatio = new ArrayList<>();

        lsDeptos = emc_departamento.find(emc_departamento.class, null, null);
        adDepto = new deptosAdapter(getBaseContext(), R.id.valID, lsDeptos);

        lsResguardos = emc_resguardosindigenas.find(emc_resguardosindigenas.class, null, null);
        adResguardo = new resguardosAdapter(getBaseContext(), R.id.valID, lsResguardos);

        lsEnfermedades = emc_ruinosas_catastroficas.find(emc_ruinosas_catastroficas.class, null, null);
        adEnfermedades = new enfermedadesAdapter(getBaseContext(), R.id.valID, lsEnfermedades );

        lsComunidadesNegras = emc_comunidadesnegras.find(emc_comunidadesnegras.class,null,null);
        adComunidadesNegras = new comunidadesnegrasAdapter(getBaseContext(),R.id.valID,lsComunidadesNegras);

        lsDts = EMC_DTPUNTOSATENCION.findWithQuery(EMC_DTPUNTOSATENCION.class, "SELECT DISTINCT IDDT,DT FROM EMCDTPUNTOSATENCION ORDER BY IDDT");
        adDTS = new dtsAdapter(getBaseContext(),R.id.valID, lsDts);

    }

    @Override
    /*protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ivCamera.setImageBitmap(imageBitmap);
        }
    }*/
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            ImageView img = (ImageView) findViewById(R.id.ivCamera);
            img.setImageURI(imageUri);
        }
    }


    private void saveImageToGallery(){
        BitmapDrawable drawable = (BitmapDrawable) ivCamera.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        Save objSave = new Save();
        objSave.SaveImage(TheThis,hogCodigo,bitmap);
        /*try {
            FileOutputStream fos = openFileOutput("prueba.png", Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
            Toast.makeText(this, "Imagen salvada", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("saveToInternalStorage()", e.getMessage());
        }*/
    }
    private String guardarImagen (Context context, String nombre, Bitmap imagen){
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Soportes";

        File dir = new File(file_path);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        File myPath = new File(dir, nombre + ".png");

        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(myPath);
            imagen.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
        }catch (FileNotFoundException ex){
            ex.printStackTrace();
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return myPath.getAbsolutePath();
    }

    private void mostrarPregunta(emc_temas temaActual, List<emc_preguntas_persona> lsPreguntasPersona){
        if(listaPreguntas != null ){
            llRespuestas.removeAllViews();
            listaPreguntas.clear();
        }
        if(lsPreguntasPersona != null){
            llSiguiente.setVisibility(View.VISIBLE);
            //llAnterior.setVisibility(View.VISIBLE);
            llAnterior.setVisibility(View.GONE);
            llReiniciarCapitulo.setVisibility(View.VISIBLE);
        }else{
            llSiguiente.setVisibility(View.GONE);
            llAnterior.setVisibility(View.GONE);
            llReiniciarCapitulo.setVisibility(View.GONE);
        }
        contadorRespuestasRatio = 0;
        contadorRespuestasCheck = 0;
        lsCheckAdapterGen.clear();
        lsRatioAdapterGen.clear();
        lsRespuestasGen.clear();
        lsCheckOtro.clear();
        valIdRespuestasRatio.clear();
        valOtroRespuestasRatio.clear();

        if(lsPreguntasPersona.size() == 0){
            lyContent.setVisibility(View.GONE);
            lyInicial.setVisibility(View.GONE);
            lyFinTema.setVisibility(View.VISIBLE);
            llMostrarPregunta.setVisibility(View.GONE);
            mDrawerLayout.openDrawer(Gravity.LEFT);
            llPreguntaTexto.setVisibility(View.GONE);
        }else{
            tvIdTema.setText(temaActual.getTem_nombretema());
            lyInicial.setVisibility(View.GONE);
            lyFinTema.setVisibility(View.GONE);
            llMostrarPregunta.setVisibility(View.VISIBLE);
            listaPreguntas = new ArrayList<>();
            mDrawerLayout.closeDrawers();


            //gestionEncuestas.reiniciarCapitulo(hogCodigo, temSeleccionado.getTem_idtema(), 1);

            for(int contPregunta = 0; contPregunta < lsPreguntasPersona.size(); contPregunta++){
                emc_preguntas_persona tmPregPersona = lsPreguntasPersona.get(contPregunta);
                if(tmPregPersona.getPre_tipopregunta().equals("GE")){
                    List<emc_preguntas> lsTemPregunta = emc_preguntas.find(emc_preguntas.class, "PREIDPREGUNTA = ?", tmPregPersona.getPre_idpregunta());
                    emc_preguntas tmPregunta = lsTemPregunta.get(0);

                    TextView tvTextoPregunta = (TextView) llPreguntaTexto.findViewById(R.id.tvTextoPregunta);
                    tvTextoPregunta.setText(tmPregPersona.getIxp_orden() + ". " + tmPregunta.getPre_pregunta());
                    TextView tvObservacion = (TextView) llPreguntaTexto.findViewById(R.id.tvObservacion);
                    tvObservacion.setText(tmPregunta.getPre_observacion());

                    LinearLayout llRespuesta;
                    switch (tmPregPersona.getPre_tipocampo()){
                        case "DP":
                        case "DT":
                        case "TE":
                        case "AT":
                        case "TA":
                        case "RES":
                        case "ENFR":
                        case "COMN":
                        case "VERD":
                        default:
                            llRespuesta = cargarPreguntaTexto(tmPregPersona, pIDJEFE);
                            break;

                        case "CT":
                        case "CH":
                            llRespuesta = cargarPreguntaRadio(tmPregPersona, pIDJEFE);
                            contadorRespuestasRatio++;
                            break;


                        case "LT":
                        case "CL":
                            llRespuesta = cargarPreguntaCheck(tmPregPersona, pIDJEFE);
                            contadorRespuestasCheck++;
                            break;

                        case "ATC":
                            llRespuesta = cargarPreguntaTextoAutocompletar(tmPregPersona, /*tmPregPersona.getPer_idpersona()*/pIDJEFE);
                            contadorRespuestasCheck++;
                            break;
                        case "ATCP":
                            llRespuesta = cargarPreguntaTextoAutocompletarp(tmPregPersona, /*tmPregPersona.getPer_idpersona()*/pIDJEFE);
                            contadorRespuestasCheck++;
                            break;
                        case "ATCR":
                            llRespuesta = cargarPreguntaTextoAutocompletarResguardos(tmPregPersona, /*tmPregPersona.getPer_idpersona()*/pIDJEFE);
                            contadorRespuestasCheck++;
                            break;
                    }
                    llRespuestas.addView(llRespuesta);
                    listaPreguntas.add(llRespuesta);
                    idPreguntaActual = Integer.valueOf(tmPregPersona.getPre_idpregunta());
                }else{
                    //for(int contPersona = 0; contPersona < lsMiembrosHogar.size(); contPersona++){
                    //    emc_miembros_hogar miembroHogar = lsMiembrosHogar.get(contPersona);
                        List<emc_preguntas> lsTemPregunta = emc_preguntas.find(emc_preguntas.class, "PREIDPREGUNTA = ?", tmPregPersona.getPre_idpregunta());
                        emc_preguntas tmPregunta = lsTemPregunta.get(0);

                        TextView tvTextoPregunta = (TextView) llPreguntaTexto.findViewById(R.id.tvTextoPregunta);
                        tvTextoPregunta.setText(tmPregPersona.getIxp_orden() + ". " +tmPregunta.getPre_pregunta());
                        TextView tvObservacion = (TextView) llPreguntaTexto.findViewById(R.id.tvObservacion);
                        tvObservacion.setText(tmPregunta.getPre_observacion());

                        LinearLayout llRespuesta;
                        switch (tmPregPersona.getPre_tipocampo()){
                            case "DP":
                            case "DT":
                            case "TE":
                            case "AT":
                            case "TA":
                            case "RES":
                            case "ENFR":
                            case "COMN":
                            case "VERD":
                            default:
                                llRespuesta = cargarPreguntaTexto(tmPregPersona, tmPregPersona.getPer_idpersona());
                                break;

                            case "CT":
                            case "CH":
                                llRespuesta = cargarPreguntaRadio(tmPregPersona, tmPregPersona.getPer_idpersona());
                                    contadorRespuestasRatio++;
                                break;


                            case "LT":
                            case "CL":
                                llRespuesta = cargarPreguntaCheck(tmPregPersona, tmPregPersona.getPer_idpersona());
                                contadorRespuestasCheck++;
                                break;
                            case "ATC":
                                llRespuesta = cargarPreguntaTextoAutocompletar(tmPregPersona, tmPregPersona.getPer_idpersona());
                                contadorRespuestasCheck++;
                                break;
                            case "ATCP":
                                llRespuesta = cargarPreguntaTextoAutocompletarp(tmPregPersona, tmPregPersona.getPer_idpersona());
                                contadorRespuestasCheck++;
                                break;
                            case "ATCR":
                                llRespuesta = cargarPreguntaTextoAutocompletarResguardos(tmPregPersona, tmPregPersona.getPer_idpersona());
                                contadorRespuestasCheck++;
                                break;
                        }
                        llRespuestas.addView(llRespuesta);
                        listaPreguntas.add(llRespuesta);
                        idPreguntaActual = Integer.valueOf(tmPregPersona.getPre_idpregunta());
                    }
                //}
            }
            llPreguntaTexto.setVisibility(View.VISIBLE);
            lyContent.setVisibility(View.VISIBLE);
        }
    }

    private LinearLayout cargarPreguntaTexto(emc_preguntas_persona tmPregPersona, String perIdMiembro){
        LinearLayout llRespuestaTexto= new LinearLayout(this);
        LayoutInflater liPregunta = getLayoutInflater();
        liPregunta.inflate(R.layout.item_respuesta_texto, llRespuestaTexto);
        emc_miembros_hogar mhPer = null;
        TextView tvNombrePersonaRespuesta = (TextView) llRespuestaTexto.findViewById(R.id.tvNombrePersonaRespuesta);
        final EditText etTextoPregunta = (EditText) llRespuestaTexto.findViewById(R.id.etTextoPregunta);

        TextView tvTipoCampo = (TextView) llRespuestaTexto.findViewById(R.id.tvTipoCampo);
        tvTipoCampo.setText(tmPregPersona.getPre_tipocampo());
        llNomsApls = (LinearLayout) llRespuestaTexto.findViewById(R.id.llNomsApls);

        String[] parCon = {perIdMiembro,hogCodigo};
        List<emc_miembros_hogar> tmMiembroHogar = emc_miembros_hogar.find(emc_miembros_hogar.class, "PERIDPERSONA = ? AND HOGCODIGO = ? ", parCon);
        mhPer = tmMiembroHogar.get(0);

        if(!tmPregPersona.getPre_tipopregunta().equals("IN") && !tmPregPersona.getPre_tipocampo().equals("RES") &&
                !tmPregPersona.getPre_tipocampo().equals("ENFR") && !tmPregPersona.getPre_tipocampo().equals("COMN")
                && !tmPregPersona.getPre_tipocampo().equals("VERD") ){

            tvNombrePersonaRespuesta.setVisibility(View.GONE);

        }else{
            //String[] parCon = {perIdMiembro,hogCodigo};
            tvNombrePersonaRespuesta.setVisibility(View.VISIBLE);
            //List<emc_miembros_hogar> tmMiembroHogar = emc_miembros_hogar.find(emc_miembros_hogar.class, "PERIDPERSONA = ?", perIdMiembro);
            /*List<emc_miembros_hogar> tmMiembroHogar = emc_miembros_hogar.find(emc_miembros_hogar.class, "PERIDPERSONA = ? AND HOGCODIGO = ? ", parCon);
            mhPer = tmMiembroHogar.get(0);*/
            tvNombrePersonaRespuesta.setText(mhPer.getNombre1() + " " + mhPer.getNombre2() + " " + mhPer.getApellido1() + " " + mhPer.getApellido2());

            etNom1 = (EditText) llRespuestaTexto.findViewById(R.id.etNom1);
            etNom1.setText(mhPer.getNombre1());
            etNom2 = (EditText) llRespuestaTexto.findViewById(R.id.etNom2);
            etNom2.setText(mhPer.getNombre2());
            etApl1 = (EditText) llRespuestaTexto.findViewById(R.id.etApl1);
            etApl1.setText(mhPer.getApellido1());
            etApl2 = (EditText) llRespuestaTexto.findViewById(R.id.etApl2);
            etApl2.setText(mhPer.getApellido2());

        }

        llDeptoMun = (LinearLayout) llRespuestaTexto.findViewById(R.id.llDeptoMun);
        llResguardo = (LinearLayout) llRespuestaTexto.findViewById(R.id.llResguardo);
        tituloVereda = (TextView) llRespuestaTexto.findViewById(R.id.tituloVereda);


        llDTDireccionTerritorial = (LinearLayout) llRespuestaTexto.findViewById(R.id.llDTDireccionTerritorial);
        llDTdepartamento = (LinearLayout) llRespuestaTexto.findViewById(R.id.llDTdepartamento);
        llDTPuntoAtencion = (LinearLayout) llRespuestaTexto.findViewById(R.id.llDTPuntoAtencion);
        llDTMunicipio = (LinearLayout) llRespuestaTexto.findViewById(R.id.llDTMunicipio);

        tituloDireccionTerritorial = (TextView) llRespuestaTexto.findViewById(R.id.tituloDireccionTerritorial);
        tituloDTdepartamento = (TextView) llRespuestaTexto.findViewById(R.id.tituloDTdepartamento);
        tituloDTPuntoAtencion = (TextView) llRespuestaTexto.findViewById(R.id.tituloDTPuntoAtencion);
        tituloDTMunicipio = (TextView) llRespuestaTexto.findViewById(R.id.tituloDTMunicipio);


        spDepto = (Spinner) llRespuestaTexto.findViewById(R.id.spDepto);
        spMunicipio = (Spinner) llRespuestaTexto.findViewById(R.id.spMunicipio);
        spResguardos = (Spinner) llRespuestaTexto.findViewById(R.id.spResguardos);

        spDTDireccionTerritorial = (Spinner) llRespuestaTexto.findViewById(R.id.spDTDireccionTerritorial);
        spDTdepartamento = (Spinner) llRespuestaTexto.findViewById(R.id.spDTdepartamento);
        spDTPuntoAtencion = (Spinner) llRespuestaTexto.findViewById(R.id.spDTPuntoAtencion);
        spDTMunicipio = (Spinner) llRespuestaTexto.findViewById(R.id.spDTMunicipio);


        if(tmPregPersona.getPre_tipocampo().equals("DP")){
            llResguardo.setVisibility(View.GONE);
            tituloVereda.setVisibility(View.GONE);
            llDeptoMun.setVisibility(View.VISIBLE);
            etTextoPregunta.setVisibility(View.GONE);

            tvNombrePersonaRespuesta.setVisibility(View.GONE);

            spDepto.setAdapter(adDepto);
            emc_departamento depSel = lsDeptos.get(spDepto.getSelectedItemPosition());
            lsMunicipios = emc_municipio.find(emc_municipio.class, "IDDEPTO = ?", depSel.getId_depto().toString());
            adMunicipio = new municipiosAdapter(getBaseContext(), R.id.valID, lsMunicipios);
            spMunicipio.setAdapter(adMunicipio);

            spDepto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    emc_departamento selDepto = lsDeptos.get(position);
                    lsMunicipios = emc_municipio.find(emc_municipio.class, "IDDEPTO = ?", selDepto.getId_depto().toString());
                    adMunicipio = new municipiosAdapter(getBaseContext(), R.id.valID, lsMunicipios);
                    spMunicipio.setAdapter(adMunicipio);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            spMunicipio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    emc_municipio munSel = lsMunicipios.get(position);
                    etTextoPregunta.setText(munSel.getId_muni_depto());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
        /////
        else if (tmPregPersona.getPre_tipocampo().equals("DT")){
            //List<EMC_DTPUNTOSATENCION> lcount = EMC_DTPUNTOSATENCION.find(EMC_DTPUNTOSATENCION.class,"IDDT = ?","7");
            llResguardo.setVisibility(View.GONE);
            llDeptoMun.setVisibility(View.GONE);
            tituloVereda.setVisibility(View.GONE);
            etTextoPregunta.setVisibility(View.GONE);

            tituloDireccionTerritorial.setVisibility(View.VISIBLE);
            tituloDTdepartamento.setVisibility(View.VISIBLE);
            tituloDTPuntoAtencion.setVisibility(View.VISIBLE);
            tituloDTMunicipio.setVisibility(View.VISIBLE);

            llDTDireccionTerritorial.setVisibility(View.VISIBLE);
            llDTdepartamento.setVisibility(View.VISIBLE);
            llDTPuntoAtencion.setVisibility(View.VISIBLE);
            llDTMunicipio.setVisibility(View.VISIBLE);
            tvNombrePersonaRespuesta.setVisibility(View.GONE);

            spDTDireccionTerritorial.setAdapter(adDTS);
            spDTDireccionTerritorial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    final EMC_DTPUNTOSATENCION selDTs = lsDts.get(position);
                    lsdeptoDts  = EMC_DTPUNTOSATENCION.findWithQuery(EMC_DTPUNTOSATENCION.class,
                            "SELECT DISTINCT IDDEPARTAMENTO, DEPARTAMENTO FROM EMCDTPUNTOSATENCION WHERE IDDT = ? ORDER BY 2",selDTs.getIddt().toString());
                    adDeptosDT = new deptosDTAdapter(getBaseContext(), R.id.valID, lsdeptoDts);
                    spDTdepartamento.setAdapter(adDeptosDT);

                    spDTdepartamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            final EMC_DTPUNTOSATENCION seldeptosDTs = lsdeptoDts.get(position);
                            lsPuntosAtencion = EMC_DTPUNTOSATENCION.findWithQuery(EMC_DTPUNTOSATENCION.class,
                                    "SELECT DISTINCT IDPUNTOATENCION, PUNTOATENCION FROM EMCDTPUNTOSATENCION WHERE IDDT = ?  AND IDDEPARTAMENTO = ? ORDER BY 1 ",
                                    selDTs.getIddt().toString(),seldeptosDTs.getIddepartamento().toString());
                            adPuntosDT = new puntosDTAdapter(getBaseContext(), R.id.valID, lsPuntosAtencion);
                            spDTPuntoAtencion.setAdapter(adPuntosDT);

                            spDTPuntoAtencion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    final EMC_DTPUNTOSATENCION selpuntosADTs = lsPuntosAtencion.get(position);
                                    String[] params = {selDTs.getIddt().toString(), seldeptosDTs.getIddepartamento().toString(), selpuntosADTs.getIdpuntoatencion().toString()};
                                    lsmunsDts = EMC_DTPUNTOSATENCION.findWithQuery(EMC_DTPUNTOSATENCION.class,
                                            "SELECT DISTINCT  IDMUNICIPIO, MUNICIPIO FROM EMCDTPUNTOSATENCION " +
                                                    "WHERE IDDT = ? AND IDDEPARTAMENTO = ? AND IDPUNTOATENCION = ?  ORDER BY 2",params );
                                    adMunsDT = new municipiosDTAdapter(getBaseContext(), R.id.valID, lsmunsDts);
                                    spDTMunicipio.setAdapter(adMunsDT);

                                    //12/04/2020
                                    spDTMunicipio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            if(selDTs.getIddt() > 0){
                                                EMC_RELACIONDTPUNTO.deleteAll(EMC_RELACIONDTPUNTO.class,
                                                        "HOGARCODIGO = '"+hogCodigo+"'");
                                                EMC_DTPUNTOSATENCION mundtSel = lsmunsDts.get(position);

                                                EMC_RELACIONDTPUNTO emc_relaciondtpunto
                                                        = new EMC_RELACIONDTPUNTO(hogCodigo,"1",selDTs.getIddt(),seldeptosDTs.getIddepartamento(),selpuntosADTs.getIdpuntoatencion(),mundtSel.getIdmunicipio());
                                                emc_relaciondtpunto.save();

                                                etTextoPregunta.setText(mundtSel.getIdmunicipio().toString());
                                            }

                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });



        }
        //////////////
        else if (tmPregPersona.getPre_tipocampo().equals("RES")){
            llResguardo.setVisibility(View.VISIBLE);
            tituloVereda.setVisibility(View.GONE);
            etTextoPregunta.setVisibility(View.GONE);

            //tvNombrePersonaRespuesta.setVisibility(View.GONE);
            spResguardos.setAdapter(adResguardo);

            spResguardos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    emc_resguardosindigenas resSel = lsResguardos.get(position);
                    etTextoPregunta.setText(resSel.getNombreresguardo().toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });



        }else if (tmPregPersona.getPre_tipocampo().equals("ENFR")){

            llResguardo.setVisibility(View.VISIBLE);
            tituloVereda.setVisibility(View.GONE);
            etTextoPregunta.setVisibility(View.GONE);

            //tvNombrePersonaRespuesta.setVisibility(View.GONE);

            spResguardos.setAdapter(adEnfermedades);

            spResguardos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    emc_ruinosas_catastroficas resSel = lsEnfermedades.get(position);
                    etTextoPregunta.setText(resSel.getNombreEnfermedad().toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        } else if (tmPregPersona.getPre_tipocampo().equals("COMN")){

            llResguardo.setVisibility(View.VISIBLE);
            tituloVereda.setVisibility(View.GONE);
            etTextoPregunta.setVisibility(View.GONE);

            spResguardos.setAdapter(adComunidadesNegras);

            spResguardos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    emc_comunidadesnegras resSel = lsComunidadesNegras.get(position);
                    etTextoPregunta.setText(resSel.getNombrecomunidad().toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }else if (tmPregPersona.getPre_tipocampo().equals("VERD")){

            llResguardo.setVisibility(View.VISIBLE);
            llDeptoMun.setVisibility(View.VISIBLE);
            tituloVereda.setVisibility(View.VISIBLE);
            etTextoPregunta.setVisibility(View.GONE);


            tvNombrePersonaRespuesta.setVisibility(View.GONE);

            spDepto.setAdapter(adDepto);
            emc_departamento depSel = lsDeptos.get(spDepto.getSelectedItemPosition());
            lsMunicipios = emc_municipio.find(emc_municipio.class, "IDDEPTO = ?", depSel.getId_depto().toString());
            adMunicipio = new municipiosAdapter(getBaseContext(), R.id.valID, lsMunicipios);
            spMunicipio.setAdapter(adMunicipio);

            emc_municipio munSel = lsMunicipios.get(spMunicipio.getSelectedItemPosition());
            lsVeredas = emc_veredas.find(emc_veredas.class, "DANEMPIO = ?", munSel.getId_muni_depto());
            adVeredas = new veredasAdapter(getBaseContext(), R.id.valID, lsVeredas);
            spResguardos.setAdapter(adVeredas);



            spDepto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    emc_departamento selDepto = lsDeptos.get(position);
                    lsMunicipios = emc_municipio.find(emc_municipio.class, "IDDEPTO = ?", selDepto.getId_depto().toString());
                    adMunicipio = new municipiosAdapter(getBaseContext(), R.id.valID, lsMunicipios);
                    spMunicipio.setAdapter(adMunicipio);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            spMunicipio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        emc_municipio selMun = lsMunicipios.get(position);
                        lsVeredas = emc_veredas.find(emc_veredas.class, "DANEMPIO = ?", selMun.getId_muni_depto().toString());
                        adVeredas = new veredasAdapter(getBaseContext(), R.id.valID, lsVeredas);
                        spResguardos.setAdapter(adVeredas);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            spResguardos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    emc_veredas verSel = lsVeredas.get(position);
                    etTextoPregunta.setText(verSel.getNom_ver().toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


        }
        else{
            llDeptoMun.setVisibility(View.GONE);
            llResguardo.setVisibility(View.GONE);
            tituloVereda.setVisibility(View.GONE);

            if(tmPregPersona.getPre_idpregunta().equals("13") || tmPregPersona.getPre_idpregunta().equals("457")){
                llNomsApls.setVisibility(View.VISIBLE);
                etTextoPregunta.setVisibility(View.GONE);
            }else{
                llNomsApls.setVisibility(View.GONE);
                etTextoPregunta.setVisibility(View.VISIBLE);
            }
            if(tmPregPersona.getPre_idpregunta().equals("14") || tmPregPersona.getPre_idpregunta().equals("461") ||  tmPregPersona.getPre_idpregunta().equals("912") ) {

                if(mhPer != null){

                if (mhPer.getFecNacimiento().length() >= 8) {
                    String dia = mhPer.getFecNacimiento().substring(0, 2);
                    String mes = mhPer.getFecNacimiento().substring(3, 5);
                    String anio = null;
                    if (mhPer.getFecNacimiento().length() == 8) {
                        anio = mhPer.getFecNacimiento().substring(6, 8);
                        if (Integer.valueOf(anio) > 16) {
                            anio = "19" + anio;
                        } else {
                            anio = "20" + anio;
                        }
                    } else if (mhPer.getFecNacimiento().length() == 10) {
                        anio = mhPer.getFecNacimiento().substring(6, 10);
                    }

                    if (anio != null) {
                        Integer edad = general.CalcularEdad(Integer.valueOf(anio), Integer.valueOf(mes), Integer.valueOf(dia));
                        etTextoPregunta.setText(edad.toString());
                    }
                }

                }

            }

            if(tmPregPersona.getPre_idpregunta().equals("31") || tmPregPersona.getPre_idpregunta().equals("459"))
                etTextoPregunta.setText(mhPer.getDocumento());
            if(tmPregPersona.getPre_idpregunta().equals("27") || tmPregPersona.getPre_idpregunta().equals("475") /*|| tmPregPersona.getPre_idpregunta().equals("911")*/)

                //if(mhPer.getFecNacimiento().toString() != null) {
                try {
                    etTextoPregunta.setText(mhPer.getFecNacimiento());
                }catch (Exception e){

                }

                //}
            if(tmPregPersona.getPre_idpregunta().equals("11"))

            etTextoPregunta.setText(general.fechaActualSinHora());
        }



        //Carga los datos adicionales a la respuesta
        TextView cod_Hogar = (TextView) llRespuestaTexto.findViewById(R.id.cod_hogar);
        cod_Hogar.setText(tmPregPersona.getHog_codigo());
        TextView per_IdPersona = (TextView) llRespuestaTexto.findViewById(R.id.per_IdPersona);
        per_IdPersona.setText(perIdMiembro);
        TextView res_IdRespuesta = (TextView) llRespuestaTexto.findViewById(R.id.res_IdRespuesta);
        List<emc_respuestas> lsResPre = emc_respuestas.find(emc_respuestas.class, "PREIDPREGUNTA = ?", tmPregPersona.getPre_idpregunta());
        res_IdRespuesta.setText(lsResPre.get(0).getRes_idrespuesta());
        TextView rxp_TipoPreguntaRespuesta = (TextView) llRespuestaTexto.findViewById(R.id.rxp_TipoPreguntaRespuesta);
        rxp_TipoPreguntaRespuesta.setText(tmPregPersona.getPre_tipopregunta());
        TextView ins_IdInstrumento = (TextView) llRespuestaTexto.findViewById(R.id.ins_IdInstrumento);
        ins_IdInstrumento.setText("1");
        TextView idPregunta = (TextView) llRespuestaTexto.findViewById(R.id.idPregunta);
        idPregunta.setText(tmPregPersona.getPre_idpregunta());

        return llRespuestaTexto;
    }

    private LinearLayout cargarPreguntaTextoAutocompletarp(emc_preguntas_persona tmPregPersona, String perIdMiembro){
        LinearLayout llRespuestaTexto= new LinearLayout(this);
        LayoutInflater liPregunta = getLayoutInflater();
        liPregunta.inflate(R.layout.item_respuesta_texto_autocompletar, llRespuestaTexto);
        emc_miembros_hogar mhPer = null;
        TextView tvNombrePersonaRespuesta = (TextView) llRespuestaTexto.findViewById(R.id.tvNombrePersonaRespuesta);
        final AutoCompleteTextView etTextoPregunta = (AutoCompleteTextView) llRespuestaTexto.findViewById(R.id.etTextoPregunta);

        String[] paises = getResources().getStringArray(R.array.paises);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, paises);

        etTextoPregunta.setThreshold(3);
        etTextoPregunta.setAdapter(adapter);

        TextView tvTipoCampo = (TextView) llRespuestaTexto.findViewById(R.id.tvTipoCampo);
        tvTipoCampo.setText(tmPregPersona.getPre_tipocampo());
        llNomsApls = (LinearLayout) llRespuestaTexto.findViewById(R.id.llNomsApls);
        if(!tmPregPersona.getPre_tipopregunta().equals("IN")){
            tvNombrePersonaRespuesta.setVisibility(View.GONE);
        }else{
            String[] parCon = {perIdMiembro,hogCodigo};
            tvNombrePersonaRespuesta.setVisibility(View.VISIBLE);
            //List<emc_miembros_hogar> tmMiembroHogar = emc_miembros_hogar.find(emc_miembros_hogar.class, "PERIDPERSONA = ?", perIdMiembro);
            List<emc_miembros_hogar> tmMiembroHogar = emc_miembros_hogar.find(emc_miembros_hogar.class, "PERIDPERSONA = ? AND HOGCODIGO = ? ", parCon);
            mhPer = tmMiembroHogar.get(0);
            tvNombrePersonaRespuesta.setText(mhPer.getNombre1() + " " + mhPer.getNombre2() + " " + mhPer.getApellido1() + " " + mhPer.getApellido2());

            etNom1 = (EditText) llRespuestaTexto.findViewById(R.id.etNom1);
            etNom1.setText(mhPer.getNombre1());
            etNom2 = (EditText) llRespuestaTexto.findViewById(R.id.etNom2);
            etNom2.setText(mhPer.getNombre2());
            etApl1 = (EditText) llRespuestaTexto.findViewById(R.id.etApl1);
            etApl1.setText(mhPer.getApellido1());
            etApl2 = (EditText) llRespuestaTexto.findViewById(R.id.etApl2);
            etApl2.setText(mhPer.getApellido2());



        }
        tituloVereda = (TextView) llRespuestaTexto.findViewById(R.id.tituloVereda);
        llDeptoMun = (LinearLayout) llRespuestaTexto.findViewById(R.id.llDeptoMun);
        spDepto = (Spinner) llRespuestaTexto.findViewById(R.id.spDepto);
        spMunicipio = (Spinner) llRespuestaTexto.findViewById(R.id.spMunicipio);
        if(tmPregPersona.getPre_tipocampo().equals("DP")){
            llResguardo.setVisibility(View.GONE);
            llDeptoMun.setVisibility(View.VISIBLE);
            etTextoPregunta.setVisibility(View.GONE);

            tvNombrePersonaRespuesta.setVisibility(View.GONE);

            spDepto.setAdapter(adDepto);
            emc_departamento depSel = lsDeptos.get(spDepto.getSelectedItemPosition());
            lsMunicipios = emc_municipio.find(emc_municipio.class, "IDDEPTO = ?", depSel.getId_depto().toString());
            adMunicipio = new municipiosAdapter(getBaseContext(), R.id.valID, lsMunicipios);
            spMunicipio.setAdapter(adMunicipio);

            spDepto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    emc_departamento selDepto = lsDeptos.get(position);
                    lsMunicipios = emc_municipio.find(emc_municipio.class, "IDDEPTO = ?", selDepto.getId_depto().toString());
                    adMunicipio = new municipiosAdapter(getBaseContext(), R.id.valID, lsMunicipios);
                    spMunicipio.setAdapter(adMunicipio);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            spMunicipio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    emc_municipio munSel = lsMunicipios.get(position);
                    etTextoPregunta.setText(munSel.getId_muni_depto().toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
        /////
        else if (tmPregPersona.getPre_tipocampo().equals("DT")){
            //List<EMC_DTPUNTOSATENCION> lcount = EMC_DTPUNTOSATENCION.find(EMC_DTPUNTOSATENCION.class,"IDDT = ?","7");
            llResguardo.setVisibility(View.GONE);
            llDeptoMun.setVisibility(View.GONE);
            tituloVereda.setVisibility(View.GONE);
            etTextoPregunta.setVisibility(View.GONE);

            tituloDireccionTerritorial.setVisibility(View.VISIBLE);
            tituloDTdepartamento.setVisibility(View.VISIBLE);
            tituloDTPuntoAtencion.setVisibility(View.VISIBLE);
            tituloDTMunicipio.setVisibility(View.VISIBLE);

            llDTDireccionTerritorial.setVisibility(View.VISIBLE);
            llDTdepartamento.setVisibility(View.VISIBLE);
            llDTPuntoAtencion.setVisibility(View.VISIBLE);
            llDTMunicipio.setVisibility(View.VISIBLE);
            tvNombrePersonaRespuesta.setVisibility(View.GONE);

            spDTDireccionTerritorial.setAdapter(adDTS);
            spDTDireccionTerritorial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    final EMC_DTPUNTOSATENCION selDTs = lsDts.get(position);
                    lsdeptoDts  = EMC_DTPUNTOSATENCION.findWithQuery(EMC_DTPUNTOSATENCION.class,
                            "SELECT DISTINCT IDDEPARTAMENTO, DEPARTAMENTO FROM EMCDTPUNTOSATENCION WHERE IDDT = ? ORDER BY 2",selDTs.getIddt().toString());
                    adDeptosDT = new deptosDTAdapter(getBaseContext(), R.id.valID, lsdeptoDts);
                    spDTdepartamento.setAdapter(adDeptosDT);

                    spDTdepartamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            final EMC_DTPUNTOSATENCION seldeptosDTs = lsdeptoDts.get(position);
                            lsPuntosAtencion = EMC_DTPUNTOSATENCION.findWithQuery(EMC_DTPUNTOSATENCION.class,
                                    "SELECT DISTINCT IDPUNTOATENCION, PUNTOATENCION FROM EMCDTPUNTOSATENCION WHERE IDDT = ?  AND IDDEPARTAMENTO = ? ORDER BY 1 ",
                                    selDTs.getIddt().toString(),seldeptosDTs.getIddepartamento().toString());
                            adPuntosDT = new puntosDTAdapter(getBaseContext(), R.id.valID, lsPuntosAtencion);
                            spDTPuntoAtencion.setAdapter(adPuntosDT);

                            spDTPuntoAtencion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    final EMC_DTPUNTOSATENCION selpuntosADTs = lsPuntosAtencion.get(position);
                                    String[] params = {selDTs.getIddt().toString(), seldeptosDTs.getIddepartamento().toString(), selpuntosADTs.getIdpuntoatencion().toString()};
                                    lsmunsDts = EMC_DTPUNTOSATENCION.findWithQuery(EMC_DTPUNTOSATENCION.class,
                                            "SELECT DISTINCT  IDMUNICIPIO, MUNICIPIO FROM EMCDTPUNTOSATENCION " +
                                                    "WHERE IDDT = ? AND IDDEPARTAMENTO = ? AND IDPUNTOATENCION = ?  ORDER BY 2",params );
                                    adMunsDT = new municipiosDTAdapter(getBaseContext(), R.id.valID, lsmunsDts);
                                    spDTMunicipio.setAdapter(adMunsDT);
                                    //12/04/2020
                                    spDTMunicipio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            if(selDTs.getIddt() > 0){
                                                EMC_RELACIONDTPUNTO.deleteAll(EMC_RELACIONDTPUNTO.class,
                                                        "HOGARCODIGO = '"+hogCodigo+"'");
                                                EMC_DTPUNTOSATENCION mundtSel = lsmunsDts.get(position);

                                                EMC_RELACIONDTPUNTO emc_relaciondtpunto
                                                        = new EMC_RELACIONDTPUNTO(hogCodigo,"1",selDTs.getIddt(),seldeptosDTs.getIddepartamento(),selpuntosADTs.getIdpuntoatencion(),mundtSel.getIdmunicipio());
                                                emc_relaciondtpunto.save();

                                                etTextoPregunta.setText(mundtSel.getIdmunicipio().toString());
                                            }
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


        }else if (tmPregPersona.getPre_tipocampo().equals("RES")){
            llResguardo.setVisibility(View.VISIBLE);
            tituloVereda.setVisibility(View.GONE);
            etTextoPregunta.setVisibility(View.GONE);

            tvNombrePersonaRespuesta.setVisibility(View.GONE);


            lsResguardos = emc_resguardosindigenas.find(emc_resguardosindigenas.class, null, null);
            adResguardo = new resguardosAdapter(getBaseContext(), R.id.valID, lsResguardos);


            spResguardos.setAdapter(adResguardo);

            spResguardos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    emc_resguardosindigenas resSel = lsResguardos.get(position);
                    etTextoPregunta.setText(resSel.getNombreresguardo().toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }else if (tmPregPersona.getPre_tipocampo().equals("ENFR")){

            llResguardo.setVisibility(View.VISIBLE);
            tituloVereda.setVisibility(View.GONE);
            etTextoPregunta.setVisibility(View.GONE);

            tvNombrePersonaRespuesta.setVisibility(View.GONE);


            spResguardos.setAdapter(adEnfermedades);

            spResguardos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    emc_ruinosas_catastroficas resSel = lsEnfermedades.get(position);
                    etTextoPregunta.setText(resSel.getNombreEnfermedad().toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }else if (tmPregPersona.getPre_tipocampo().equals("COMN")){

            llResguardo.setVisibility(View.VISIBLE);
            tituloVereda.setVisibility(View.GONE);
            etTextoPregunta.setVisibility(View.GONE);


            spResguardos.setAdapter(adComunidadesNegras);

            spResguardos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    emc_comunidadesnegras resSel = lsComunidadesNegras.get(position);
                    etTextoPregunta.setText(resSel.getNombrecomunidad().toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }else if (tmPregPersona.getPre_tipocampo().equals("VERD")){

            llResguardo.setVisibility(View.VISIBLE);
            llDeptoMun.setVisibility(View.VISIBLE);
            tituloVereda.setVisibility(View.VISIBLE);
            etTextoPregunta.setVisibility(View.GONE);


            tvNombrePersonaRespuesta.setVisibility(View.GONE);

            spDepto.setAdapter(adDepto);
            emc_departamento depSel = lsDeptos.get(spDepto.getSelectedItemPosition());
            lsMunicipios = emc_municipio.find(emc_municipio.class, "IDDEPTO = ?", depSel.getId_depto().toString());
            adMunicipio = new municipiosAdapter(getBaseContext(), R.id.valID, lsMunicipios);
            spMunicipio.setAdapter(adMunicipio);

            emc_municipio munSel = lsMunicipios.get(spMunicipio.getSelectedItemPosition());
            lsVeredas = emc_veredas.find(emc_veredas.class, "DANEMPIO = ?", munSel.getId_muni_depto());
            adVeredas = new veredasAdapter(getBaseContext(), R.id.valID, lsVeredas);
            spResguardos.setAdapter(adVeredas);



            spDepto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    emc_departamento selDepto = lsDeptos.get(position);
                    lsMunicipios = emc_municipio.find(emc_municipio.class, "IDDEPTO = ?", selDepto.getId_depto().toString());
                    adMunicipio = new municipiosAdapter(getBaseContext(), R.id.valID, lsMunicipios);
                    spMunicipio.setAdapter(adMunicipio);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            spMunicipio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    emc_municipio selMun = lsMunicipios.get(position);
                    lsVeredas = emc_veredas.find(emc_veredas.class, "DANEMPIO = ?", selMun.getId_muni_depto().toString());
                    adVeredas = new veredasAdapter(getBaseContext(), R.id.valID, lsVeredas);
                    spResguardos.setAdapter(adVeredas);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spResguardos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    emc_veredas verSel = lsVeredas.get(position);
                    etTextoPregunta.setText(verSel.getNom_ver().toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        else{
            llDeptoMun.setVisibility(View.GONE);
            llResguardo.setVisibility(View.GONE);
            tituloVereda.setVisibility(View.GONE);

            if(tmPregPersona.getPre_idpregunta().equals("13") || tmPregPersona.getPre_idpregunta().equals("457")){
                llNomsApls.setVisibility(View.VISIBLE);
                etTextoPregunta.setVisibility(View.GONE);
            }else{
                llNomsApls.setVisibility(View.GONE);
                etTextoPregunta.setVisibility(View.VISIBLE);
            }
            if(tmPregPersona.getPre_idpregunta().equals("14") || tmPregPersona.getPre_idpregunta().equals("461") ||  tmPregPersona.getPre_idpregunta().equals("912")) {
                if (mhPer.getFecNacimiento().length() >= 8) {
                    String dia = mhPer.getFecNacimiento().substring(0, 2);
                    String mes = mhPer.getFecNacimiento().substring(3, 5);
                    String anio = null;
                    if (mhPer.getFecNacimiento().length() == 8) {
                        anio = mhPer.getFecNacimiento().substring(6, 8);
                        if (Integer.valueOf(anio) > 16) {
                            anio = "19" + anio;
                        } else {
                            anio = "20" + anio;
                        }
                    } else if (mhPer.getFecNacimiento().length() == 10) {
                        anio = mhPer.getFecNacimiento().substring(6, 10);
                    }

                    if (anio != null) {
                        Integer edad = general.CalcularEdad(Integer.valueOf(anio), Integer.valueOf(mes), Integer.valueOf(dia));
                        etTextoPregunta.setText(edad.toString());
                    }
                }
            }

            if(tmPregPersona.getPre_idpregunta().equals("31") || tmPregPersona.getPre_idpregunta().equals("459"))
                etTextoPregunta.setText(mhPer.getDocumento());
            if(tmPregPersona.getPre_idpregunta().equals("27") || tmPregPersona.getPre_idpregunta().equals("475") /*|| tmPregPersona.getPre_idpregunta().equals("911")*/)
                etTextoPregunta.setText(mhPer.getFecNacimiento());
            if(tmPregPersona.getPre_idpregunta().equals("11"))
                etTextoPregunta.setText(general.fechaActual());
        }

        //Carga los datos adicionales a la respuesta
        TextView cod_Hogar = (TextView) llRespuestaTexto.findViewById(R.id.cod_hogar);
        cod_Hogar.setText(tmPregPersona.getHog_codigo());
        TextView per_IdPersona = (TextView) llRespuestaTexto.findViewById(R.id.per_IdPersona);
        per_IdPersona.setText(perIdMiembro);
        TextView res_IdRespuesta = (TextView) llRespuestaTexto.findViewById(R.id.res_IdRespuesta);
        List<emc_respuestas> lsResPre = emc_respuestas.find(emc_respuestas.class, "PREIDPREGUNTA = ?", tmPregPersona.getPre_idpregunta());
        res_IdRespuesta.setText(lsResPre.get(0).getRes_idrespuesta());
        TextView rxp_TipoPreguntaRespuesta = (TextView) llRespuestaTexto.findViewById(R.id.rxp_TipoPreguntaRespuesta);
        rxp_TipoPreguntaRespuesta.setText(tmPregPersona.getPre_tipopregunta());
        TextView ins_IdInstrumento = (TextView) llRespuestaTexto.findViewById(R.id.ins_IdInstrumento);
        ins_IdInstrumento.setText("1");
        TextView idPregunta = (TextView) llRespuestaTexto.findViewById(R.id.idPregunta);
        idPregunta.setText(tmPregPersona.getPre_idpregunta());

        return llRespuestaTexto;
    }

    private LinearLayout cargarPreguntaTextoAutocompletar(emc_preguntas_persona tmPregPersona, String perIdMiembro){
        LinearLayout llRespuestaTexto= new LinearLayout(this);
        LayoutInflater liPregunta = getLayoutInflater();
        liPregunta.inflate(R.layout.item_respuesta_texto_autocompletar, llRespuestaTexto);
        emc_miembros_hogar mhPer = null;
        TextView tvNombrePersonaRespuesta = (TextView) llRespuestaTexto.findViewById(R.id.tvNombrePersonaRespuesta);
        //final EditText etTextoPregunta = (EditText) llRespuestaTexto.findViewById(R.id.etTextoPregunta);
        final AutoCompleteTextView etTextoPregunta = (AutoCompleteTextView) llRespuestaTexto.findViewById(R.id.etTextoPregunta);

        String[] profesiones = getResources().getStringArray(R.array.profesiones);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, profesiones/*pROFESIONES()*/);

        etTextoPregunta.setThreshold(3);
        etTextoPregunta.setAdapter(adapter);

        TextView tvTipoCampo = (TextView) llRespuestaTexto.findViewById(R.id.tvTipoCampo);
        tvTipoCampo.setText(tmPregPersona.getPre_tipocampo());
        llNomsApls = (LinearLayout) llRespuestaTexto.findViewById(R.id.llNomsApls);
        if(!tmPregPersona.getPre_tipopregunta().equals("IN")){
            tvNombrePersonaRespuesta.setVisibility(View.GONE);
        }else{
            String[] parCon = {perIdMiembro,hogCodigo};
            tvNombrePersonaRespuesta.setVisibility(View.VISIBLE);
            //List<emc_miembros_hogar> tmMiembroHogar = emc_miembros_hogar.find(emc_miembros_hogar.class, "PERIDPERSONA = ?", perIdMiembro);
            List<emc_miembros_hogar> tmMiembroHogar = emc_miembros_hogar.find(emc_miembros_hogar.class, "PERIDPERSONA = ? AND HOGCODIGO = ? ", parCon);
            mhPer = tmMiembroHogar.get(0);
            tvNombrePersonaRespuesta.setText(mhPer.getNombre1() + " " + mhPer.getNombre2() + " " + mhPer.getApellido1() + " " + mhPer.getApellido2());

            etNom1 = (EditText) llRespuestaTexto.findViewById(R.id.etNom1);
            etNom1.setText(mhPer.getNombre1());
            etNom2 = (EditText) llRespuestaTexto.findViewById(R.id.etNom2);
            etNom2.setText(mhPer.getNombre2());
            etApl1 = (EditText) llRespuestaTexto.findViewById(R.id.etApl1);
            etApl1.setText(mhPer.getApellido1());
            etApl2 = (EditText) llRespuestaTexto.findViewById(R.id.etApl2);
            etApl2.setText(mhPer.getApellido2());

        }

        tituloVereda = (TextView) llRespuestaTexto.findViewById(R.id.tituloVereda);
        llDeptoMun = (LinearLayout) llRespuestaTexto.findViewById(R.id.llDeptoMun);
        spDepto = (Spinner) llRespuestaTexto.findViewById(R.id.spDepto);
        spMunicipio = (Spinner) llRespuestaTexto.findViewById(R.id.spMunicipio);


        if(tmPregPersona.getPre_tipocampo().equals("DP")){
            llResguardo.setVisibility(View.GONE);
            llDeptoMun.setVisibility(View.VISIBLE);
            tituloVereda.setVisibility(View.GONE);
            etTextoPregunta.setVisibility(View.GONE);


            tvNombrePersonaRespuesta.setVisibility(View.GONE);

            spDepto.setAdapter(adDepto);
            emc_departamento depSel = lsDeptos.get(spDepto.getSelectedItemPosition());
            lsMunicipios = emc_municipio.find(emc_municipio.class, "IDDEPTO = ?", depSel.getId_depto().toString());
            adMunicipio = new municipiosAdapter(getBaseContext(), R.id.valID, lsMunicipios);
            spMunicipio.setAdapter(adMunicipio);

            spDepto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    emc_departamento selDepto = lsDeptos.get(position);
                    lsMunicipios = emc_municipio.find(emc_municipio.class, "IDDEPTO = ?", selDepto.getId_depto().toString());
                    adMunicipio = new municipiosAdapter(getBaseContext(), R.id.valID, lsMunicipios);
                    spMunicipio.setAdapter(adMunicipio);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            spMunicipio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    emc_municipio munSel = lsMunicipios.get(position);
                    etTextoPregunta.setText(munSel.getId_muni_depto().toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
        /////
        else if (tmPregPersona.getPre_tipocampo().equals("DT")){
            //List<EMC_DTPUNTOSATENCION> lcount = EMC_DTPUNTOSATENCION.find(EMC_DTPUNTOSATENCION.class,"IDDT = ?","7");
            llResguardo.setVisibility(View.GONE);
            llDeptoMun.setVisibility(View.GONE);
            tituloVereda.setVisibility(View.GONE);
            etTextoPregunta.setVisibility(View.GONE);

            tituloDireccionTerritorial.setVisibility(View.VISIBLE);
            tituloDTdepartamento.setVisibility(View.VISIBLE);
            tituloDTPuntoAtencion.setVisibility(View.VISIBLE);
            tituloDTMunicipio.setVisibility(View.VISIBLE);

            llDTDireccionTerritorial.setVisibility(View.VISIBLE);
            llDTdepartamento.setVisibility(View.VISIBLE);
            llDTPuntoAtencion.setVisibility(View.VISIBLE);
            llDTMunicipio.setVisibility(View.VISIBLE);
            tvNombrePersonaRespuesta.setVisibility(View.GONE);

            spDTDireccionTerritorial.setAdapter(adDTS);
            spDTDireccionTerritorial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    final EMC_DTPUNTOSATENCION selDTs = lsDts.get(position);
                    lsdeptoDts  = EMC_DTPUNTOSATENCION.findWithQuery(EMC_DTPUNTOSATENCION.class,
                            "SELECT DISTINCT IDDEPARTAMENTO, DEPARTAMENTO FROM EMCDTPUNTOSATENCION WHERE IDDT = ? ORDER BY 2",selDTs.getIddt().toString());
                    adDeptosDT = new deptosDTAdapter(getBaseContext(), R.id.valID, lsdeptoDts);
                    spDTdepartamento.setAdapter(adDeptosDT);

                    spDTdepartamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            final EMC_DTPUNTOSATENCION seldeptosDTs = lsdeptoDts.get(position);
                            lsPuntosAtencion = EMC_DTPUNTOSATENCION.findWithQuery(EMC_DTPUNTOSATENCION.class,
                                    "SELECT DISTINCT IDPUNTOATENCION, PUNTOATENCION FROM EMCDTPUNTOSATENCION WHERE IDDT = ?  AND IDDEPARTAMENTO = ? ORDER BY 1 ",
                                    selDTs.getIddt().toString(),seldeptosDTs.getIddepartamento().toString());
                            adPuntosDT = new puntosDTAdapter(getBaseContext(), R.id.valID, lsPuntosAtencion);
                            spDTPuntoAtencion.setAdapter(adPuntosDT);

                            spDTPuntoAtencion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    final EMC_DTPUNTOSATENCION selpuntosADTs = lsPuntosAtencion.get(position);
                                    String[] params = {selDTs.getIddt().toString(), seldeptosDTs.getIddepartamento().toString(), selpuntosADTs.getIdpuntoatencion().toString()};
                                    lsmunsDts = EMC_DTPUNTOSATENCION.findWithQuery(EMC_DTPUNTOSATENCION.class,
                                            "SELECT DISTINCT  IDMUNICIPIO, MUNICIPIO FROM EMCDTPUNTOSATENCION " +
                                                    "WHERE IDDT = ? AND IDDEPARTAMENTO = ? AND IDPUNTOATENCION = ?  ORDER BY 2",params );
                                    adMunsDT = new municipiosDTAdapter(getBaseContext(), R.id.valID, lsmunsDts);
                                    spDTMunicipio.setAdapter(adMunsDT);
                                    //12/04/2020
                                    spDTMunicipio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            if(selDTs.getIddt() > 0){
                                                EMC_RELACIONDTPUNTO.deleteAll(EMC_RELACIONDTPUNTO.class,
                                                        "HOGARCODIGO = '"+hogCodigo+"'");
                                                EMC_DTPUNTOSATENCION mundtSel = lsmunsDts.get(position);

                                                EMC_RELACIONDTPUNTO emc_relaciondtpunto
                                                        = new EMC_RELACIONDTPUNTO(hogCodigo,"1",selDTs.getIddt(),seldeptosDTs.getIddepartamento(),selpuntosADTs.getIdpuntoatencion(),mundtSel.getIdmunicipio());
                                                emc_relaciondtpunto.save();

                                                etTextoPregunta.setText(mundtSel.getIdmunicipio().toString());
                                            }
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        ///
        else if (tmPregPersona.getPre_tipocampo().equals("RES")){
            llResguardo.setVisibility(View.VISIBLE);
            tituloVereda.setVisibility(View.GONE);
            etTextoPregunta.setVisibility(View.GONE);

            tvNombrePersonaRespuesta.setVisibility(View.GONE);


            lsResguardos = emc_resguardosindigenas.find(emc_resguardosindigenas.class, null, null);
            adResguardo = new resguardosAdapter(getBaseContext(), R.id.valID, lsResguardos);

            spResguardos.setAdapter(adResguardo);

            spResguardos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    emc_resguardosindigenas resSel = lsResguardos.get(position);
                    etTextoPregunta.setText(resSel.getNombreresguardo().toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }else if (tmPregPersona.getPre_tipocampo().equals("ENFR")){

            llResguardo.setVisibility(View.VISIBLE);
            tituloVereda.setVisibility(View.GONE);
            etTextoPregunta.setVisibility(View.GONE);

            tvNombrePersonaRespuesta.setVisibility(View.GONE);



            spResguardos.setAdapter(adEnfermedades);

            spResguardos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    emc_ruinosas_catastroficas resSel = lsEnfermedades.get(position);
                    etTextoPregunta.setText(resSel.getNombreEnfermedad().toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });



        }else if (tmPregPersona.getPre_tipocampo().equals("COMN")){

            llResguardo.setVisibility(View.GONE);
            tituloVereda.setVisibility(View.GONE);
            llDeptoMun.setVisibility(View.VISIBLE);
            etTextoPregunta.setVisibility(View.GONE);


            tvNombrePersonaRespuesta.setVisibility(View.GONE);

            spDepto.setAdapter(adDepto);
            emc_departamento depSel = lsDeptos.get(spDepto.getSelectedItemPosition());
            lsMunicipios = emc_municipio.find(emc_municipio.class, "IDDEPTO = ?", depSel.getId_depto().toString());
            adMunicipio = new municipiosAdapter(getBaseContext(), R.id.valID, lsMunicipios);
            spMunicipio.setAdapter(adMunicipio);

            spDepto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    emc_departamento selDepto = lsDeptos.get(position);
                    lsMunicipios = emc_municipio.find(emc_municipio.class, "IDDEPTO = ?", selDepto.getId_depto().toString());
                    adMunicipio = new municipiosAdapter(getBaseContext(), R.id.valID, lsMunicipios);
                    spMunicipio.setAdapter(adMunicipio);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            spMunicipio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    emc_municipio munSel = lsMunicipios.get(position);
                    etTextoPregunta.setText(munSel.getId_muni_depto().toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        else{
            llDeptoMun.setVisibility(View.GONE);
            llResguardo.setVisibility(View.GONE);
            tituloVereda.setVisibility(View.GONE);

            if(tmPregPersona.getPre_idpregunta().equals("13") || tmPregPersona.getPre_idpregunta().equals("457")){
                llNomsApls.setVisibility(View.VISIBLE);
                etTextoPregunta.setVisibility(View.GONE);
            }else{
                llNomsApls.setVisibility(View.GONE);
                etTextoPregunta.setVisibility(View.VISIBLE);
            }
            if(tmPregPersona.getPre_idpregunta().equals("14") || tmPregPersona.getPre_idpregunta().equals("461") ||  tmPregPersona.getPre_idpregunta().equals("912") ) {
                if (mhPer.getFecNacimiento().length() >= 8) {
                    String dia = mhPer.getFecNacimiento().substring(0, 2);
                    String mes = mhPer.getFecNacimiento().substring(3, 5);
                    String anio = null;
                    if (mhPer.getFecNacimiento().length() == 8) {
                        anio = mhPer.getFecNacimiento().substring(6, 8);
                        if (Integer.valueOf(anio) > 16) {
                            anio = "19" + anio;
                        } else {
                            anio = "20" + anio;
                        }
                    } else if (mhPer.getFecNacimiento().length() == 10) {
                        anio = mhPer.getFecNacimiento().substring(6, 10);
                    }

                    if (anio != null) {
                        Integer edad = general.CalcularEdad(Integer.valueOf(anio), Integer.valueOf(mes), Integer.valueOf(dia));
                        etTextoPregunta.setText(edad.toString());
                    }
                }
            }

            if(tmPregPersona.getPre_idpregunta().equals("31") || tmPregPersona.getPre_idpregunta().equals("459"))
                etTextoPregunta.setText(mhPer.getDocumento());
            if(tmPregPersona.getPre_idpregunta().equals("27") || tmPregPersona.getPre_idpregunta().equals("475") /*|| tmPregPersona.getPre_idpregunta().equals("911")*/)
                etTextoPregunta.setText(mhPer.getFecNacimiento());
            if(tmPregPersona.getPre_idpregunta().equals("11"))
                etTextoPregunta.setText(general.fechaActual());
        }

        //Carga los datos adicionales a la respuesta
        TextView cod_Hogar = (TextView) llRespuestaTexto.findViewById(R.id.cod_hogar);
        cod_Hogar.setText(tmPregPersona.getHog_codigo());
        TextView per_IdPersona = (TextView) llRespuestaTexto.findViewById(R.id.per_IdPersona);
        per_IdPersona.setText(perIdMiembro);
        TextView res_IdRespuesta = (TextView) llRespuestaTexto.findViewById(R.id.res_IdRespuesta);
        List<emc_respuestas> lsResPre = emc_respuestas.find(emc_respuestas.class, "PREIDPREGUNTA = ?", tmPregPersona.getPre_idpregunta());
        res_IdRespuesta.setText(lsResPre.get(0).getRes_idrespuesta());
        TextView rxp_TipoPreguntaRespuesta = (TextView) llRespuestaTexto.findViewById(R.id.rxp_TipoPreguntaRespuesta);
        rxp_TipoPreguntaRespuesta.setText(tmPregPersona.getPre_tipopregunta());
        TextView ins_IdInstrumento = (TextView) llRespuestaTexto.findViewById(R.id.ins_IdInstrumento);
        ins_IdInstrumento.setText("1");
        TextView idPregunta = (TextView) llRespuestaTexto.findViewById(R.id.idPregunta);
        idPregunta.setText(tmPregPersona.getPre_idpregunta());

        return llRespuestaTexto;
    }

    private LinearLayout cargarPreguntaTextoAutocompletarResguardos(emc_preguntas_persona tmPregPersona, String perIdMiembro){
        LinearLayout llRespuestaTexto= new LinearLayout(this);
        LayoutInflater liPregunta = getLayoutInflater();
        liPregunta.inflate(R.layout.item_respuesta_texto_autocompletar, llRespuestaTexto);
        emc_miembros_hogar mhPer = null;
        TextView tvNombrePersonaRespuesta = (TextView) llRespuestaTexto.findViewById(R.id.tvNombrePersonaRespuesta);
        final AutoCompleteTextView etTextoPregunta = (AutoCompleteTextView) llRespuestaTexto.findViewById(R.id.etTextoPregunta);

        String[] paises = getResources().getStringArray(R.array.resguardos);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, paises);

        etTextoPregunta.setThreshold(3);
        etTextoPregunta.setAdapter(adapter);

        TextView tvTipoCampo = (TextView) llRespuestaTexto.findViewById(R.id.tvTipoCampo);
        tvTipoCampo.setText(tmPregPersona.getPre_tipocampo());
        llNomsApls = (LinearLayout) llRespuestaTexto.findViewById(R.id.llNomsApls);
        if(!tmPregPersona.getPre_tipopregunta().equals("IN")){
            tvNombrePersonaRespuesta.setVisibility(View.GONE);
        }else{
            String[] parCon = {perIdMiembro,hogCodigo};
            tvNombrePersonaRespuesta.setVisibility(View.VISIBLE);
            //List<emc_miembros_hogar> tmMiembroHogar = emc_miembros_hogar.find(emc_miembros_hogar.class, "PERIDPERSONA = ?", perIdMiembro);
            List<emc_miembros_hogar> tmMiembroHogar = emc_miembros_hogar.find(emc_miembros_hogar.class, "PERIDPERSONA = ? AND HOGCODIGO = ? ", parCon);
            mhPer = tmMiembroHogar.get(0);
            tvNombrePersonaRespuesta.setText(mhPer.getNombre1() + " " + mhPer.getNombre2() + " " + mhPer.getApellido1() + " " + mhPer.getApellido2());

            etNom1 = (EditText) llRespuestaTexto.findViewById(R.id.etNom1);
            etNom1.setText(mhPer.getNombre1());
            etNom2 = (EditText) llRespuestaTexto.findViewById(R.id.etNom2);
            etNom2.setText(mhPer.getNombre2());
            etApl1 = (EditText) llRespuestaTexto.findViewById(R.id.etApl1);
            etApl1.setText(mhPer.getApellido1());
            etApl2 = (EditText) llRespuestaTexto.findViewById(R.id.etApl2);
            etApl2.setText(mhPer.getApellido2());



        }

        tituloVereda = (TextView) llRespuestaTexto.findViewById(R.id.tituloVereda);
        llDeptoMun = (LinearLayout) llRespuestaTexto.findViewById(R.id.llDeptoMun);
        spDepto = (Spinner) llRespuestaTexto.findViewById(R.id.spDepto);
        spMunicipio = (Spinner) llRespuestaTexto.findViewById(R.id.spMunicipio);
        if(tmPregPersona.getPre_tipocampo().equals("DP")){
            llResguardo.setVisibility(View.GONE);
            tituloVereda.setVisibility(View.GONE);
            llDeptoMun.setVisibility(View.VISIBLE);
            etTextoPregunta.setVisibility(View.GONE);

            tvNombrePersonaRespuesta.setVisibility(View.GONE);

            spDepto.setAdapter(adDepto);
            emc_departamento depSel = lsDeptos.get(spDepto.getSelectedItemPosition());
            lsMunicipios = emc_municipio.find(emc_municipio.class, "IDDEPTO = ?", depSel.getId_depto().toString());
            adMunicipio = new municipiosAdapter(getBaseContext(), R.id.valID, lsMunicipios);
            spMunicipio.setAdapter(adMunicipio);

            spDepto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    emc_departamento selDepto = lsDeptos.get(position);
                    lsMunicipios = emc_municipio.find(emc_municipio.class, "IDDEPTO = ?", selDepto.getId_depto().toString());
                    adMunicipio = new municipiosAdapter(getBaseContext(), R.id.valID, lsMunicipios);
                    spMunicipio.setAdapter(adMunicipio);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            spMunicipio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    emc_municipio munSel = lsMunicipios.get(position);
                    etTextoPregunta.setText(munSel.getId_muni_depto().toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }/////
        else if (tmPregPersona.getPre_tipocampo().equals("DT")){
            //List<EMC_DTPUNTOSATENCION> lcount = EMC_DTPUNTOSATENCION.find(EMC_DTPUNTOSATENCION.class,"IDDT = ?","7");
            llResguardo.setVisibility(View.GONE);
            llDeptoMun.setVisibility(View.GONE);
            tituloVereda.setVisibility(View.GONE);
            etTextoPregunta.setVisibility(View.GONE);

            tituloDireccionTerritorial.setVisibility(View.VISIBLE);
            tituloDTdepartamento.setVisibility(View.VISIBLE);
            tituloDTPuntoAtencion.setVisibility(View.VISIBLE);
            tituloDTMunicipio.setVisibility(View.VISIBLE);

            llDTDireccionTerritorial.setVisibility(View.VISIBLE);
            llDTdepartamento.setVisibility(View.VISIBLE);
            llDTPuntoAtencion.setVisibility(View.VISIBLE);
            llDTMunicipio.setVisibility(View.VISIBLE);
            tvNombrePersonaRespuesta.setVisibility(View.GONE);

            spDTDireccionTerritorial.setAdapter(adDTS);
            spDTDireccionTerritorial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    final EMC_DTPUNTOSATENCION selDTs = lsDts.get(position);
                    lsdeptoDts  = EMC_DTPUNTOSATENCION.findWithQuery(EMC_DTPUNTOSATENCION.class,
                            "SELECT DISTINCT IDDEPARTAMENTO, DEPARTAMENTO FROM EMCDTPUNTOSATENCION WHERE IDDT = ? ORDER BY 2",selDTs.getIddt().toString());
                    adDeptosDT = new deptosDTAdapter(getBaseContext(), R.id.valID, lsdeptoDts);
                    spDTdepartamento.setAdapter(adDeptosDT);

                    spDTdepartamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            final EMC_DTPUNTOSATENCION seldeptosDTs = lsdeptoDts.get(position);
                            lsPuntosAtencion = EMC_DTPUNTOSATENCION.findWithQuery(EMC_DTPUNTOSATENCION.class,
                                    "SELECT DISTINCT IDPUNTOATENCION, PUNTOATENCION FROM EMCDTPUNTOSATENCION WHERE IDDT = ?  AND IDDEPARTAMENTO = ? ORDER BY 1 ",
                                    selDTs.getIddt().toString(),seldeptosDTs.getIddepartamento().toString());
                            adPuntosDT = new puntosDTAdapter(getBaseContext(), R.id.valID, lsPuntosAtencion);
                            spDTPuntoAtencion.setAdapter(adPuntosDT);

                            spDTPuntoAtencion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    final EMC_DTPUNTOSATENCION selpuntosADTs = lsPuntosAtencion.get(position);
                                    String[] params = {selDTs.getIddt().toString(), seldeptosDTs.getIddepartamento().toString(), selpuntosADTs.getIdpuntoatencion().toString()};
                                    lsmunsDts = EMC_DTPUNTOSATENCION.findWithQuery(EMC_DTPUNTOSATENCION.class,
                                            "SELECT DISTINCT  IDMUNICIPIO, MUNICIPIO FROM EMCDTPUNTOSATENCION " +
                                                    "WHERE IDDT = ? AND IDDEPARTAMENTO = ? AND IDPUNTOATENCION = ?  ORDER BY 2",params );
                                    adMunsDT = new municipiosDTAdapter(getBaseContext(), R.id.valID, lsmunsDts);
                                    spDTMunicipio.setAdapter(adMunsDT);
                                    //12/04/2020
                                    spDTMunicipio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            if(selDTs.getIddt() > 0){
                                                EMC_RELACIONDTPUNTO.deleteAll(EMC_RELACIONDTPUNTO.class,
                                                        "HOGARCODIGO = '"+hogCodigo+"'");
                                                EMC_DTPUNTOSATENCION mundtSel = lsmunsDts.get(position);

                                                EMC_RELACIONDTPUNTO emc_relaciondtpunto
                                                        = new EMC_RELACIONDTPUNTO(hogCodigo,"1",selDTs.getIddt(),seldeptosDTs.getIddepartamento(),selpuntosADTs.getIdpuntoatencion(),mundtSel.getIdmunicipio());
                                                emc_relaciondtpunto.save();

                                                etTextoPregunta.setText(mundtSel.getIdmunicipio().toString());
                                            }
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


        }
        ///////////
        else if (tmPregPersona.getPre_tipocampo().equals("RES")){
            llResguardo.setVisibility(View.VISIBLE);
            tituloVereda.setVisibility(View.GONE);
            etTextoPregunta.setVisibility(View.GONE);

            tvNombrePersonaRespuesta.setVisibility(View.GONE);


            lsResguardos = emc_resguardosindigenas.find(emc_resguardosindigenas.class, null, null);
            adResguardo = new resguardosAdapter(getBaseContext(), R.id.valID, lsResguardos);

            spResguardos.setAdapter(adResguardo);

            spResguardos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    emc_resguardosindigenas resSel = lsResguardos.get(position);
                    etTextoPregunta.setText(resSel.getNombreresguardo().toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }else if (tmPregPersona.getPre_tipocampo().equals("ENFR")){

            llResguardo.setVisibility(View.VISIBLE);
            tituloVereda.setVisibility(View.GONE);
            etTextoPregunta.setVisibility(View.GONE);

            tvNombrePersonaRespuesta.setVisibility(View.GONE);


            spResguardos.setAdapter(adEnfermedades);

            spResguardos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    emc_ruinosas_catastroficas resSel = lsEnfermedades.get(position);
                    etTextoPregunta.setText(resSel.getNombreEnfermedad().toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }else if (tmPregPersona.getPre_tipocampo().equals("COMN")){

            llResguardo.setVisibility(View.VISIBLE);
            tituloVereda.setVisibility(View.GONE);
            etTextoPregunta.setVisibility(View.GONE);

            spResguardos.setAdapter(adComunidadesNegras);

            spResguardos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    emc_comunidadesnegras resSel = lsComunidadesNegras.get(position);
                    etTextoPregunta.setText(resSel.getNombrecomunidad().toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }else if (tmPregPersona.getPre_tipocampo().equals("VERD")){

            llResguardo.setVisibility(View.VISIBLE);
            llDeptoMun.setVisibility(View.VISIBLE);
            tituloVereda.setVisibility(View.VISIBLE);
            etTextoPregunta.setVisibility(View.GONE);


            tvNombrePersonaRespuesta.setVisibility(View.GONE);

            spDepto.setAdapter(adDepto);
            emc_departamento depSel = lsDeptos.get(spDepto.getSelectedItemPosition());
            lsMunicipios = emc_municipio.find(emc_municipio.class, "IDDEPTO = ?", depSel.getId_depto().toString());
            adMunicipio = new municipiosAdapter(getBaseContext(), R.id.valID, lsMunicipios);
            spMunicipio.setAdapter(adMunicipio);

            emc_municipio munSel = lsMunicipios.get(spMunicipio.getSelectedItemPosition());
            lsVeredas = emc_veredas.find(emc_veredas.class, "DANEMPIO = ?", munSel.getId_muni_depto());
            adVeredas = new veredasAdapter(getBaseContext(), R.id.valID, lsVeredas);
            spResguardos.setAdapter(adVeredas);



            spDepto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    emc_departamento selDepto = lsDeptos.get(position);
                    lsMunicipios = emc_municipio.find(emc_municipio.class, "IDDEPTO = ?", selDepto.getId_depto());
                    adMunicipio = new municipiosAdapter(getBaseContext(), R.id.valID, lsMunicipios);
                    spMunicipio.setAdapter(adMunicipio);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            spMunicipio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    emc_municipio selMun = lsMunicipios.get(position);
                    lsVeredas = emc_veredas.find(emc_veredas.class, "DANEMPIO = ?", selMun.getId_muni_depto().toString());
                    adVeredas = new veredasAdapter(getBaseContext(), R.id.valID, lsVeredas);
                    spResguardos.setAdapter(adVeredas);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spResguardos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    emc_veredas verSel = lsVeredas.get(position);
                    etTextoPregunta.setText(verSel.getNom_ver().toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        else{
            llDeptoMun.setVisibility(View.GONE);
            llResguardo.setVisibility(View.GONE);
            tituloVereda.setVisibility(View.GONE);

            if(tmPregPersona.getPre_idpregunta().equals("13") || tmPregPersona.getPre_idpregunta().equals("457")){
                llNomsApls.setVisibility(View.VISIBLE);
                etTextoPregunta.setVisibility(View.GONE);
            }else{
                llNomsApls.setVisibility(View.GONE);
                etTextoPregunta.setVisibility(View.VISIBLE);
            }
            if(tmPregPersona.getPre_idpregunta().equals("14") || tmPregPersona.getPre_idpregunta().equals("461") ||  tmPregPersona.getPre_idpregunta().equals("912") ) {
                if (mhPer.getFecNacimiento().length() >= 8) {
                    String dia = mhPer.getFecNacimiento().substring(0, 2);
                    String mes = mhPer.getFecNacimiento().substring(3, 5);
                    String anio = null;
                    if (mhPer.getFecNacimiento().length() == 8) {
                        anio = mhPer.getFecNacimiento().substring(6, 8);
                        if (Integer.valueOf(anio) > 16) {
                            anio = "19" + anio;
                        } else {
                            anio = "20" + anio;
                        }
                    } else if (mhPer.getFecNacimiento().length() == 10) {
                        anio = mhPer.getFecNacimiento().substring(6, 10);
                    }

                    if (anio != null) {
                        Integer edad = general.CalcularEdad(Integer.valueOf(anio), Integer.valueOf(mes), Integer.valueOf(dia));
                        etTextoPregunta.setText(edad.toString());
                    }
                }
            }

            if(tmPregPersona.getPre_idpregunta().equals("31") || tmPregPersona.getPre_idpregunta().equals("459"))
                etTextoPregunta.setText(mhPer.getDocumento());
            if(tmPregPersona.getPre_idpregunta().equals("27") || tmPregPersona.getPre_idpregunta().equals("475") /*|| tmPregPersona.getPre_idpregunta().equals("911")*/)
                etTextoPregunta.setText(mhPer.getFecNacimiento());
            if(tmPregPersona.getPre_idpregunta().equals("11"))
                etTextoPregunta.setText(general.fechaActual());
        }

        //Carga los datos adicionales a la respuesta
        TextView cod_Hogar = (TextView) llRespuestaTexto.findViewById(R.id.cod_hogar);
        cod_Hogar.setText(tmPregPersona.getHog_codigo());
        TextView per_IdPersona = (TextView) llRespuestaTexto.findViewById(R.id.per_IdPersona);
        per_IdPersona.setText(perIdMiembro);
        TextView res_IdRespuesta = (TextView) llRespuestaTexto.findViewById(R.id.res_IdRespuesta);
        List<emc_respuestas> lsResPre = emc_respuestas.find(emc_respuestas.class, "PREIDPREGUNTA = ?", tmPregPersona.getPre_idpregunta());
        res_IdRespuesta.setText(lsResPre.get(0).getRes_idrespuesta());
        TextView rxp_TipoPreguntaRespuesta = (TextView) llRespuestaTexto.findViewById(R.id.rxp_TipoPreguntaRespuesta);
        rxp_TipoPreguntaRespuesta.setText(tmPregPersona.getPre_tipopregunta());
        TextView ins_IdInstrumento = (TextView) llRespuestaTexto.findViewById(R.id.ins_IdInstrumento);
        ins_IdInstrumento.setText("1");
        TextView idPregunta = (TextView) llRespuestaTexto.findViewById(R.id.idPregunta);
        idPregunta.setText(tmPregPersona.getPre_idpregunta());

        return llRespuestaTexto;
    }

     public   String[] pROFESIONES()
    {

        String[] profesiones = getResources().getStringArray(R.array.profesiones);


        return profesiones;
    }



    private LinearLayout cargarPreguntaRadio(emc_preguntas_persona tmPregPersona, String perIdMiembro){
        LinearLayout llRespuestaRatio = new LinearLayout(this);
        LayoutInflater liPregunta = getLayoutInflater();
        liPregunta.inflate(R.layout.item_respuesta_ratio, llRespuestaRatio);
        Integer p = null;

        TextView tvNombrePersonaRespuesta = (TextView) llRespuestaRatio.findViewById(R.id.tvNombrePersonaRespuesta);
        final EditText etTextoPregunta = (EditText) llRespuestaRatio.findViewById(R.id.etTextoPregunta);

        TextView tvTipoCampo = (TextView) llRespuestaRatio.findViewById(R.id.tvTipoCampo);
        tvTipoCampo.setText(tmPregPersona.getPre_tipocampo());

        ListView lvRespuestas = (ListView) llRespuestaRatio.findViewById(R.id.lvRespuestas);

        if(!tmPregPersona.getPre_tipopregunta().equals("IN")){
            tvNombrePersonaRespuesta.setVisibility(View.GONE);
        }else{
            String[] parCon = {perIdMiembro,hogCodigo};
            tvNombrePersonaRespuesta.setVisibility(View.VISIBLE);
            //List<emc_miembros_hogar> tmMiembroHogar = emc_miembros_hogar.find(emc_miembros_hogar.class, "PERIDPERSONA = ?", perIdMiembro);
            List<emc_miembros_hogar> tmMiembroHogar = emc_miembros_hogar.find(emc_miembros_hogar.class, "PERIDPERSONA = ? AND HOGCODIGO = ? ", parCon);
            emc_miembros_hogar mhPer = tmMiembroHogar.get(0);
            tvNombrePersonaRespuesta.setText(mhPer.getNombre1() + " " + mhPer.getNombre2() + " " + mhPer.getApellido1() + " " + mhPer.getApellido2());
        }

        //Carga los datos adicionales a la respuesta
        TextView cod_Hogar = (TextView) llRespuestaRatio.findViewById(R.id.cod_hogar);
        cod_Hogar.setText(tmPregPersona.getHog_codigo());
        TextView per_IdPersona = (TextView) llRespuestaRatio.findViewById(R.id.per_IdPersona);
        per_IdPersona.setText(perIdMiembro);
        final TextView res_IdRespuesta = (TextView) llRespuestaRatio.findViewById(R.id.res_IdRespuesta);
        //List<emc_respuestas> lsResPre = emc_respuestas.find(emc_respuestas.class, "PREIDPREGUNTA = ?", tmPregPersona.getPre_idpregunta());
        //res_IdRespuesta.setText(lsResPre.get(0).getRes_idrespuesta());
        TextView rxp_TipoPreguntaRespuesta = (TextView) llRespuestaRatio.findViewById(R.id.rxp_TipoPreguntaRespuesta);
        rxp_TipoPreguntaRespuesta.setText(tmPregPersona.getPre_tipopregunta());
        TextView ins_IdInstrumento = (TextView) llRespuestaRatio.findViewById(R.id.ins_IdInstrumento);
        ins_IdInstrumento.setText("1");
        TextView idPregunta = (TextView) llRespuestaRatio.findViewById(R.id.idPregunta);
        idPregunta.setText(tmPregPersona.getPre_idpregunta());

        //Cargar las respuestas

        if(tmPregPersona.getPer_idpersona() != null) {
            p = Integer.valueOf(tmPregPersona.getPer_idpersona().toString());
        }else{
            p = Integer.valueOf(perIdMiembro);
        }
        List<emc_respuestas_instrumento> lsRespuestas = gestionEncuestas.SP_GET_RESPUESTASXPREMOD(Integer.valueOf(tmPregPersona.getPre_idpregunta().toString()), 1, tmPregPersona.getHog_codigo(), p, temSeleccionado.getTem_idtema(), Integer.valueOf(pIDJEFE));
        alRespuestas = new ArrayList<>();

        for(int conRI = 0; conRI < lsRespuestas.size(); conRI++){
            emc_respuestas_instrumento tmRI = lsRespuestas.get(conRI);
            List<emc_respuestas> listaRespuestas = emc_respuestas.find(emc_respuestas.class, "RESIDRESPUESTA = ? AND RESACTIVA = 'SI'", tmRI.getRes_idrespuesta());
            if(listaRespuestas.size() > 0){
                emc_respuestas tmRes = listaRespuestas.get(0);
                respuestasIdValor val = new respuestasIdValor(tmRes.getRes_idrespuesta(), tmRes.getRes_respuesta(), false, conRI);
                alRespuestas.add(val);
            }
        }
        lsRespuestasGen.add(alRespuestas);

        ratiosResponse callback = new ratiosResponse() {
            @Override
            public void onItemSelected(Integer position, Integer positionRespuesta) {
                ArrayList<respuestasIdValor> respEsp = lsRespuestasGen.get(positionRespuesta);
                for(int conR = 0; conR < respEsp.size(); conR++){
                    respuestasIdValor tmR = respEsp.get(conR);
                    if(conR == position){
                        tmR.setSelected(true);
                        valIdRespuestasRatio.set(positionRespuesta, lsRespuestasGen.get(positionRespuesta).get(position).getId());
                        //res_IdRespuesta.setText(tmR.getId());
                        //etTextoPregunta.setText(tmR.getId());
                    }else{
                        tmR.setSelected(false);
                    }
                 lsRatioAdapterGen.get(positionRespuesta).notifyDataSetChanged();
              }

            }

            @Override
            public void onOtroChanged(Integer position, Integer positionRespuesta, String textoOtro) {
                valOtroRespuestasRatio.set(positionRespuesta, textoOtro);
            }
        };
        valIdRespuestasRatio.add("-1");
        valOtroRespuestasRatio.add("-1");
        lsRatioAdapterGen.add(new respuestasRatioAdapter(this, R.id.valID, lsRespuestasGen.get(contadorRespuestasRatio), callback, contadorRespuestasRatio, lsRespuestas));
        lvRespuestas.setAdapter(lsRatioAdapterGen.get(contadorRespuestasRatio));

        return llRespuestaRatio;
    }


    private LinearLayout cargarPreguntaCheck(emc_preguntas_persona tmPregPersona, String perIdMiembro){
        LinearLayout llRespuestaCheck = new LinearLayout(this);
        LayoutInflater liPregunta = getLayoutInflater();
        liPregunta.inflate(R.layout.item_respuesta_check, llRespuestaCheck);

        TextView tvNombrePersonaRespuesta = (TextView) llRespuestaCheck.findViewById(R.id.tvNombrePersonaRespuesta);
        final EditText etTextoPregunta = (EditText) llRespuestaCheck.findViewById(R.id.etTextoPregunta);

        TextView tvTipoCampo = (TextView) llRespuestaCheck.findViewById(R.id.tvTipoCampo);
        tvTipoCampo.setText(tmPregPersona.getPre_tipocampo());

        ListView lvRespuestas = (ListView) llRespuestaCheck.findViewById(R.id.lvRespuestas);

        if(!tmPregPersona.getPre_tipopregunta().equals("IN")){
            tvNombrePersonaRespuesta.setVisibility(View.GONE);
        }else{
            String[] parCon = {perIdMiembro,hogCodigo};
            tvNombrePersonaRespuesta.setVisibility(View.VISIBLE);
            //List<emc_miembros_hogar> tmMiembroHogar = emc_miembros_hogar.find(emc_miembros_hogar.class, "PERIDPERSONA = ?", perIdMiembro);
            List<emc_miembros_hogar> tmMiembroHogar = emc_miembros_hogar.find(emc_miembros_hogar.class, "PERIDPERSONA = ? AND HOGCODIGO = ? ", parCon);
            emc_miembros_hogar mhPer = tmMiembroHogar.get(0);
            tvNombrePersonaRespuesta.setText(mhPer.getNombre1() + " " + mhPer.getNombre2() + " " + mhPer.getApellido1() + " " + mhPer.getApellido2());
        }

        //Carga los datos adicionales a la respuesta
        TextView cod_Hogar = (TextView) llRespuestaCheck.findViewById(R.id.cod_hogar);
        cod_Hogar.setText(tmPregPersona.getHog_codigo());
        TextView per_IdPersona = (TextView) llRespuestaCheck.findViewById(R.id.per_IdPersona);
        per_IdPersona.setText(perIdMiembro);
        final TextView res_IdRespuesta = (TextView) llRespuestaCheck.findViewById(R.id.res_IdRespuesta);
        //List<emc_respuestas> lsResPre = emc_respuestas.find(emc_respuestas.class, "PREIDPREGUNTA = ?", tmPregPersona.getPre_idpregunta());
        //res_IdRespuesta.setText(lsResPre.get(0).getRes_idrespuesta());
        TextView rxp_TipoPreguntaRespuesta = (TextView) llRespuestaCheck.findViewById(R.id.rxp_TipoPreguntaRespuesta);
        rxp_TipoPreguntaRespuesta.setText(tmPregPersona.getPre_tipopregunta());
        TextView ins_IdInstrumento = (TextView) llRespuestaCheck.findViewById(R.id.ins_IdInstrumento);
        ins_IdInstrumento.setText("1");
        TextView idPregunta = (TextView) llRespuestaCheck.findViewById(R.id.idPregunta);
        idPregunta.setText(tmPregPersona.getPre_idpregunta());

        //Cargar las respuestas
        Integer p = null;
        //lsRespuestasGen.clear();
        //lsCheckOtro.clear();
        if(tmPregPersona.getPer_idpersona() != null) {
            p = Integer.valueOf(tmPregPersona.getPer_idpersona().toString());
        }else{

        }

        List<emc_respuestas_instrumento> lsRespuestas = gestionEncuestas.SP_GET_RESPUESTASXPREMOD(Integer.valueOf(tmPregPersona.getPre_idpregunta().toString()), 1, tmPregPersona.getHog_codigo(), p, temSeleccionado.getTem_idtema(), Integer.valueOf(pIDJEFE));
        alRespuestas = new ArrayList<>();
        final ArrayList<String> tmRespOtro = new ArrayList<>();
        for(int conRI = 0; conRI < lsRespuestas.size(); conRI++){
            emc_respuestas_instrumento tmRI = lsRespuestas.get(conRI);
            List<emc_respuestas> listaRespuestas = emc_respuestas.find(emc_respuestas.class, "RESIDRESPUESTA = ? AND RESACTIVA = 'SI'", tmRI.getRes_idrespuesta());
            if(listaRespuestas.size() > 0){
                emc_respuestas tmRes = listaRespuestas.get(0);
                respuestasIdValor val = new respuestasIdValor(tmRes.getRes_idrespuesta(), tmRes.getRes_respuesta(), false);
                alRespuestas.add(val);
            }
            tmRespOtro.add("");
        }
        lsRespuestasGen.add(alRespuestas);
        lsCheckOtro.add(tmRespOtro);

        ratiosResponse callback = new ratiosResponse() {
            @Override
            public void onItemSelected(Integer position, Integer positionRespuesta) {
                ArrayList<respuestasIdValor> respEsp = lsRespuestasGen.get(positionRespuesta);
                if(lsRespuestasGen.get(positionRespuesta).get(position).isSelected()){
                    lsRespuestasGen.get(positionRespuesta).get(position).setSelected(false);
                }else{
                    if(lsRespuestasGen.get(positionRespuesta).get(position).getValor().toUpperCase().equals("NINGUNO") ||
                            lsRespuestasGen.get(positionRespuesta).get(position).getValor().toUpperCase().equals("NINGUNA") ||
                            lsRespuestasGen.get(positionRespuesta).get(position).getValor().toUpperCase().equals("NO SABE") ||
                            lsRespuestasGen.get(positionRespuesta).get(position).getValor().toUpperCase().equals("NS/NR") ||
                            lsRespuestasGen.get(positionRespuesta).get(position).getValor().toUpperCase().equals("NO SABE/NO RESPONDE") ||
                            lsRespuestasGen.get(positionRespuesta).get(position).getValor().toUpperCase().equals("NINGUNA DE LAS ANTERIORES") ||
                            lsRespuestasGen.get(positionRespuesta).get(position).getValor().toUpperCase().equals("A NADIE") ||
                            lsRespuestasGen.get(positionRespuesta).get(position).getValor().toUpperCase().equals("NO SÉ A QUIÉN ACUDIR")
                            ){
                        for(int conRI = 0; conRI < lsRespuestasGen.get(positionRespuesta).size(); conRI++){
                            if(conRI!=position){

                                lsRespuestasGen.get(positionRespuesta).get(conRI).setSelected(false);
                            }
                        }
                    }else if(!lsRespuestasGen.get(positionRespuesta).get(position).getValor().toUpperCase().equals("NINGUNO") &&
                            !lsRespuestasGen.get(positionRespuesta).get(position).getValor().toUpperCase().equals("NINGUNA") &&
                            !lsRespuestasGen.get(positionRespuesta).get(position).getValor().toUpperCase().equals("NO SABE") &&
                            !lsRespuestasGen.get(positionRespuesta).get(position).getValor().toUpperCase().equals("NS/NR") &&
                            !lsRespuestasGen.get(positionRespuesta).get(position).getValor().toUpperCase().equals("NO SABE/NO RESPONDE") &&
                            !lsRespuestasGen.get(positionRespuesta).get(position).getValor().toUpperCase().equals("NINGUNA DE LAS ANTERIORES") &&
                            !lsRespuestasGen.get(positionRespuesta).get(position).getValor().toUpperCase().equals("A NADIE") &&
                            !lsRespuestasGen.get(positionRespuesta).get(position).getValor().toUpperCase().equals("NO SÉ A QUIÉN ACUDIR")
                            ){


                        for(int conRI = 0; conRI < lsRespuestasGen.get(positionRespuesta).size(); conRI++){
                            if(lsRespuestasGen.get(positionRespuesta).get(conRI).getValor().toUpperCase().equals("NINGUNO") ||
                                    lsRespuestasGen.get(positionRespuesta).get(conRI).getValor().toUpperCase().equals("NINGUNA") ||
                                    lsRespuestasGen.get(positionRespuesta).get(conRI).getValor().toUpperCase().equals("NO SABE") ||
                                    lsRespuestasGen.get(positionRespuesta).get(conRI).getValor().toUpperCase().equals("NS/NR") ||
                                    lsRespuestasGen.get(positionRespuesta).get(conRI).getValor().toUpperCase().equals("NO SABE/NO RESPONDE") ||
                                    lsRespuestasGen.get(positionRespuesta).get(conRI).getValor().toUpperCase().equals("NINGUNA DE LAS ANTERIORES") ||
                                    lsRespuestasGen.get(positionRespuesta).get(conRI).getValor().toUpperCase().equals("A NADIE") ||
                                    lsRespuestasGen.get(positionRespuesta).get(conRI).getValor().toUpperCase().equals("NO SÉ A QUIÉN ACUDIR")
                                    ){

                                lsRespuestasGen.get(positionRespuesta).get(conRI).setSelected(false);
                            }
                        }

                    }

                    lsRespuestasGen.get(positionRespuesta).get(position).setSelected(true);
                }
                lsCheckAdapterGen.get(positionRespuesta).notifyDataSetChanged();



            }

            @Override
            public void onOtroChanged(Integer position, Integer positionRespuesta, String textoOtro) {
                ArrayList<String> temporalOtro = lsCheckOtro.get(positionRespuesta);
                temporalOtro.set(position,textoOtro);
                lsCheckOtro.set(positionRespuesta, temporalOtro);
            }
        };
        //aqui se totea cuando respondo no en la pregunta 10 del capitulo 6
        lsCheckAdapterGen.add(new respuestasCheckAdapter(this, R.id.valID, lsRespuestasGen.get(contadorRespuestasCheck), callback, contadorRespuestasCheck, lsRespuestas));
        lvRespuestas.setAdapter(lsCheckAdapterGen.get(contadorRespuestasCheck));

        return llRespuestaCheck;
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

    public boolean respuestaValida(){
        boolean valida = true;
        String nomCompuesto;
        emc_miembros_hogar mhPer = null;
        emc_respuestas_instrumento tmRes = null;
        if(listaPreguntas==null){
            Toast.makeText(this, "Debe seleccionar un captítulo", Toast.LENGTH_SHORT).show();
            valida = false;
        }else
        for (int conLP = 0; conLP < listaPreguntas.size(); conLP++) {
            Integer idRespuesta = null;
            LinearLayout llRespuestaTexto = listaPreguntas.get(conLP);

            EditText etTextoPregunta = (EditText) llRespuestaTexto.findViewById(R.id.etTextoPregunta);
            etTextoPregunta.requestFocus();
            //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);*/
            TextView cod_Hogar = (TextView) llRespuestaTexto.findViewById(R.id.cod_hogar);
            TextView per_IdPersona = (TextView) llRespuestaTexto.findViewById(R.id.per_IdPersona);
            TextView rxp_TipoPreguntaRespuesta = (TextView) llRespuestaTexto.findViewById(R.id.rxp_TipoPreguntaRespuesta);
            TextView ins_IdInstrumento = (TextView) llRespuestaTexto.findViewById(R.id.ins_IdInstrumento);
            TextView idPregunta = (TextView) llRespuestaTexto.findViewById(R.id.idPregunta);
            Integer p;
            if(per_IdPersona.getText().equals("")){
                p = null;
            }else{
                p = Integer.valueOf(per_IdPersona.getText().toString());
                //List<emc_miembros_hogar> tmMiembroHogar = emc_miembros_hogar.find(emc_miembros_hogar.class, "PERIDPERSONA = " + p, null);
                List<emc_miembros_hogar> tmMiembroHogar = emc_miembros_hogar.find(emc_miembros_hogar.class, "PERIDPERSONA = " + p + " AND HOGCODIGO = '"+ hogCodigo+"'", null);
                mhPer = tmMiembroHogar.get(0);

                if(idPregunta.getText().toString().equals("13") || idPregunta.getText().toString().equals("457")){
                    etNom1 = (EditText) llRespuestaTexto.findViewById(R.id.etNom1);
                    etNom2 = (EditText) llRespuestaTexto.findViewById(R.id.etNom2);
                    etApl1 = (EditText) llRespuestaTexto.findViewById(R.id.etApl1);
                    etApl2 = (EditText) llRespuestaTexto.findViewById(R.id.etApl2);

                    nomCompuesto = etNom1.getText().toString() + " " + etNom2.getText().toString() + " " + etApl1.getText().toString() + " " + etApl2.getText().toString();
                    nombreC = new item_nombre_completo(etNom1.getText().toString(), etNom2.getText().toString(), etApl1.getText().toString(),etApl2.getText().toString());

                    etTextoPregunta.setText(nomCompuesto);
                }
            }
            TextView res_IdRespuesta = (TextView) llRespuestaTexto.findViewById(R.id.res_IdRespuesta);
            List<emc_respuestas_instrumento> lsResp = emc_respuestas_instrumento.find(emc_respuestas_instrumento.class,
                    "RESIDRESPUESTA = ?", res_IdRespuesta.getText().toString());
            if(lsResp.size() > 0){
                tmRes = lsResp.get(0);
            }

            TextView tvTipoCampo = (TextView) llRespuestaTexto.findViewById(R.id.tvTipoCampo);
            switch (tvTipoCampo.getText().toString()){
                case "DP":
                case "DT":
                case "TE":
                case "AT":
                case "TA":
                case "RES":
                case "ENFR":
                case "COMN":
                case "VERD":
                default:

                    if(etTextoPregunta.getText().toString().equals("") && !tmRes.getRes_obligatorio().equals("NO")){
                        Toast.makeText(this, "Debe diligenciar una respuesta", Toast.LENGTH_SHORT).show();
                        valida = false;
                    }else{
                        if(tmRes != null){
                            if(tmRes.getPre_validador() != null){
                                switch(tmRes.getPre_validador()){
                                    case "TE":
                                        if(tmRes.getPre_longcampo() != null){
                                            if(etTextoPregunta.getText().length() > Integer.valueOf(tmRes.getPre_longcampo())){
                                                Toast.makeText(this, "Logintud incorrecta", Toast.LENGTH_SHORT).show();
                                                valida = false;
                                            }
                                        }
                                        break;

                                    case "NU":


                                        etTextoPregunta.requestFocus();
                                        etTextoPregunta.setInputType(InputType.TYPE_CLASS_NUMBER);
                                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

                                        if(!etTextoPregunta.getText().toString().matches("[0-9]*")){
                                            Toast.makeText(this, "Número incorrecto", Toast.LENGTH_SHORT).show();
                                            valida = false;
                                        }else{

                                            int t = etTextoPregunta.getText().length();
                                            double d = Double.parseDouble(tmRes.getPre_longcampo());
                                            if(etTextoPregunta.getText().length() > Double.parseDouble(tmRes.getPre_longcampo())){
                                                Toast.makeText(this, "Logintud incorrecta", Toast.LENGTH_SHORT).show();
                                                valida = false;
                                            }else{
                                                if(t > 0){

                                                    Double valor = Double.parseDouble(etTextoPregunta.getText().toString());
                                                    if(tmRes.getPre_validador_min() != null){
                                                        if(valor < Double.parseDouble(tmRes.getPre_validador_min())){
                                                            Toast.makeText(this, "Valor debe ser superior a " + tmRes.getPre_validador_min(), Toast.LENGTH_SHORT).show();
                                                            valida = false;
                                                        }
                                                    }


                                                    if(tmRes.getPre_validador_max() != null){
                                                        if(valor > Double.parseDouble(tmRes.getPre_validador_max())){
                                                            Toast.makeText(this, "Valor debe ser inferior o igual a " + tmRes.getPre_validador_max(), Toast.LENGTH_SHORT).show();
                                                            valida = false;
                                                        }
                                                    }
                                                }else if(t == 0){
                                                    Toast.makeText(this, "Campo esta vacio debe diligenciarlo ", Toast.LENGTH_SHORT).show();
                                                    valida = false;
                                                }

                                            }

                                        }

                                        break;
                                    case "DE":

                                        if(etTextoPregunta.getText().length() > Integer.valueOf(tmRes.getPre_longcampo())){
                                            Toast.makeText(this, "Logintud incorrecta", Toast.LENGTH_SHORT).show();
                                            valida = false;
                                        }

                                        if(!esDecimal(etTextoPregunta.getText().toString()))
                                        {
                                            if(etTextoPregunta.getText().length() == 2 || etTextoPregunta.getText().length() == 1){
                                            try {
                                                Double.parseDouble(etTextoPregunta.getText().toString());

                                            } catch (NumberFormatException nfe) {
                                                Toast.makeText(this, "No es un valor valido " , Toast.LENGTH_SHORT).show();
                                                valida = false;
                                            }

                                            }else
                                            {
                                                Toast.makeText(this, "No es un valor valido " , Toast.LENGTH_SHORT).show();
                                                valida = false;
                                            }


                                        }

                                        break;

                                    case "FE":

                                        if(!general.fechaValida(etTextoPregunta.getText().toString())){
                                            Toast.makeText(this, "Fecha incorrecta (DD/MM/AAAA)", Toast.LENGTH_SHORT).show();
                                            valida = false;
                                            break;
                                        }else{
                                            if(tmRes.getRes_idrespuesta().equals("78")  || tmRes.getRes_idrespuesta().equals("2820") ){
                                                try {
                                                    String fechaHoy  = fechaActualSinHora();
                                                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                                                    if (sdf.parse(etTextoPregunta.getText().toString()).before(sdf.parse(fechaHoy)) == false) {
                                                        Toast.makeText(this, "Fecha no puede ser mayor a "+fechaHoy, Toast.LENGTH_SHORT).show();
                                                        valida = false;
                                                        break;
                                                    }
                                                    break;
                                                }catch (Exception e){
                                                    valida = false;
                                                    break;
                                                }
                                            }
                                            break;

                                        }

                                    case "EM":

                                        if(etTextoPregunta.getText().toString().equals("") && !tmRes.getRes_obligatorio().equals("NO")){
                                            Toast.makeText(this, "Debe registar un E-mail", Toast.LENGTH_SHORT).show();
                                            valida = false;
                                        }else if (!etTextoPregunta.getText().toString().equals("")){//if(!general.isValidEmail(etTextoPregunta.getText().toString())){
                                            if(!general.isValidEmail(etTextoPregunta.getText().toString())){
                                                Toast.makeText(this, "E-mail incorrecto", Toast.LENGTH_SHORT).show();
                                                valida = false;
                                            }

                                        }
                                        break;

                                    case "TI":
                                        if(!general.isValidPhone(etTextoPregunta.getText().toString())){
                                            Toast.makeText(this, "Teléfono incorrecto (#########)", Toast.LENGTH_SHORT).show();
                                            valida = false;
                                        }
                                        break;

                                    case "MO":
                                        if(!general.isValidMovil(etTextoPregunta.getText().toString())){
                                            Toast.makeText(this, "Número incorrecto", Toast.LENGTH_SHORT).show();
                                            valida = false;
                                        }
                                        break;

                                }
                            }
                        }
                    }

                    break;

                case "CT":
                case "CH":

                    String valOtro = "";

                    if(valIdRespuestasRatio.size() >= conLP){
                        idRespuesta = Integer.valueOf(valIdRespuestasRatio.get(conLP));

                    }

                    if(idRespuesta == null || idRespuesta == -1){
                        Toast.makeText(this, "Debe seleccionar una respuesta valida", Toast.LENGTH_SHORT).show();
                        valida = false;
                    }else
                    {
                        valOtro = valOtroRespuestasRatio.get(conLP);
                        List<emc_respuestas_instrumento> lsRespT = emc_respuestas_instrumento.find(emc_respuestas_instrumento.class,
                                "RESIDRESPUESTA = ?", idRespuesta.toString());
                        if (lsRespT.size() > 0) {
                            tmRes = lsRespT.get(0);
                            emcrespuestainstrumento = tmRes;
                        }

                        if (tmRes.getPre_validador() != null) {
                            if (tmRes.getPre_validador().equals("NU")) {

                                etTextoPregunta.requestFocus();
                                etTextoPregunta.setInputType(InputType.TYPE_CLASS_NUMBER);
                                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                                if(valOtro.length() > 0 && !valOtro.equals("-1") ){

                                    try {

                                        int x = Integer.parseInt(valOtro.toString().trim());

                                        if(valOtro.equals("-1"))
                                        {
                                            Toast.makeText(this, "Campo esta vacio, debe diligenciarlo", Toast.LENGTH_SHORT).show();
                                            valida = false;
                                        }else  if (x > Integer.valueOf(tmRes.getPre_validador_max())) {
                                            Toast.makeText(this, "Valor debe ser inferior o igual a " + tmRes.getPre_validador_max(), Toast.LENGTH_SHORT).show();
                                            valida = false;
                                        }else  if (x < Integer.valueOf(tmRes.getPre_validador_min())) {
                                            Toast.makeText(this, "Valor debe ser mayor o igual a " + tmRes.getPre_validador_min(), Toast.LENGTH_SHORT).show();
                                            valida = false;
                                        } else  if (valOtro.length() > Integer.valueOf(tmRes.getPre_longcampo())) {
                                            Toast.makeText(this, "Logitud incorrecta", Toast.LENGTH_SHORT).show();
                                            valida = false;
                                        }


                                    } catch (NumberFormatException nfe) {
                                        Toast.makeText(this, "Debe ingresar un número valido", Toast.LENGTH_SHORT).show();
                                        valida = false;

                                    }

                                }else
                                {
                                    Toast.makeText(this, "Campo esta vacio, debe diligenciarlo ", Toast.LENGTH_SHORT).show();
                                    valida = false;

                                }

                            }else if(tmRes.getPre_validador().equals("TE"))
                            {
                                if(valOtro.length() > 0 && !valOtro.equals("-1") )
                                {

                                    if(valOtro.length() > Integer.valueOf(tmRes.getPre_longcampo()))
                                    {
                                    Toast.makeText(this, "Logitud incorrecta", Toast.LENGTH_SHORT).show();
                                    valida = false;
                                    }
                                    else
                                    {
                                        valida = true;
                                    }
                                }
                                else if(tmRes.getRes_obligatorio().equals("NO")){
                                    valida = true;
                                }else
                                {
                                    Toast.makeText(this, "Campo esta vacio, debe diligenciarlo ", Toast.LENGTH_SHORT).show();
                                    valida = false;
                                }

                            }
                            else
                            {
                                valida = true;
                            }

                        }
                    }


                    break;

                case "LT":
                case "CL":
                    boolean seleccionado = false;
                    String valOtroC = "";
                    for(int contR = 0; contR < lsRespuestasGen.get(conLP).size(); contR++){

                        if(lsRespuestasGen.get(conLP).get(contR).isSelected()){
                            String idrespuesta = lsRespuestasGen.get(conLP).get(contR).getId();
                            String respuesta = lsRespuestasGen.get(conLP).get(contR).getValor().toUpperCase();
                            valOtroC = lsCheckOtro.get(conLP).get(contR);

                                if(idrespuesta.equals("2258") && valOtroC.equals("")) {
                                    Toast.makeText(this, "Campo esta vacio, debe diligenciarlo", Toast.LENGTH_SHORT).show();
                                    seleccionado = false;
                                }else if (idrespuesta.equals("1619") && valOtroC.equals("") ){
                                    Toast.makeText(this, "Campo esta vacio, debe diligenciarlo", Toast.LENGTH_SHORT).show();
                                    seleccionado = false;

                                }else if (idrespuesta.equals("1619") && !isNumeric(valOtroC) ){
                                    Toast.makeText(this, "Debe registrar número de 4 digitos", Toast.LENGTH_SHORT).show();
                                    seleccionado = false;

                                } else if (idrespuesta.equals("2292") && valOtroC.equals("")){
                                    Toast.makeText(this, "Campo esta vacio, debe diligenciarlo", Toast.LENGTH_SHORT).show();
                                    seleccionado = false;

                                }else if (idrespuesta.equals("2292") && !isNumeric(valOtroC)){
                                    Toast.makeText(this, "Debe registrar número de 4 digitos", Toast.LENGTH_SHORT).show();
                                    seleccionado = false;
                                }else if (idrespuesta.equals("2293") && valOtroC.equals("")){
                                    Toast.makeText(this, "Campo esta vacio, debe diligenciarlo", Toast.LENGTH_SHORT).show();
                                    seleccionado = false;

                                }else if (idrespuesta.equals("2293") && !isNumeric(valOtroC)){
                                    Toast.makeText(this, "Debe registrar número de 4 digitos", Toast.LENGTH_SHORT).show();
                                    seleccionado = false;
                                }else if (idrespuesta.equals("2294") && valOtroC.equals("")){
                                    Toast.makeText(this, "Campo esta vacio, debe diligenciarlo", Toast.LENGTH_SHORT).show();
                                    seleccionado = false;

                                }else if (idrespuesta.equals("2294") && !isNumeric(valOtroC)){
                                    Toast.makeText(this, "Debe registrar número de 4 digitos", Toast.LENGTH_SHORT).show();
                                    seleccionado = false;
                                }else if (idrespuesta.equals("2295") && valOtroC.equals("")){
                                    Toast.makeText(this, "Campo esta vacio, debe diligenciarlo", Toast.LENGTH_SHORT).show();
                                    seleccionado = false;

                                }else if (idrespuesta.equals("2295") && !isNumeric(valOtroC)){
                                    Toast.makeText(this, "Debe registrar número de 4 digitos", Toast.LENGTH_SHORT).show();
                                    seleccionado = false;
                                }
                                else {
                                    seleccionado = true;
                                }


                        }
                    }
                    if(!seleccionado){
                        Toast.makeText(this, "Debe seleccionar mínimo una respuesta", Toast.LENGTH_SHORT).show();
                        valida = false;
                    }
                    break;
            }

            if(valida){



                if(idPregunta.getText().toString().equals("13") ||  idPregunta.getText().toString().equals("457")){
                    if(!etTextoPregunta.getText().toString().equals(mhPer.getNombre1() + " " + mhPer.getNombre2() + " " + mhPer.getApellido1() + " " + mhPer.getApellido2())){
                        //Actualizar los nombres
                        mhPer.setNombre1(etNom1.getText().toString());
                        mhPer.setNombre2(etNom2.getText().toString());
                        mhPer.setApellido1(etApl1.getText().toString());
                        mhPer.setApellido2(etApl2.getText().toString());
                        mhPer.setUsu_fechacreacion(general.fechaActual());
                        mhPer.setUsu_usuariocreacion(usuarioLogin.getIdusuario());
                        mhPer.save();
                    }
                }
                if(idPregunta.getText().toString().equals("31") || idPregunta.getText().toString().equals("459") ) {
                    if(!mhPer.getDocumento().equals(etTextoPregunta.getText().toString())){
                        //Actualiza el numero de documento
                        mhPer.setDocumento(etTextoPregunta.getText().toString());
                        mhPer.save();
                    }
                }
                /*
                if(idPregunta.getText().toString().equals("27") || idPregunta.getText().toString().equals("475")) {
                    if (!mhPer.getFecNacimiento().trim().equals(etTextoPregunta.getText().toString().trim())){
                        //Actualiza la fecha de nacimiento
                        mhPer.setFecNacimiento(etTextoPregunta.getText().toString().trim());
                        mhPer.save();
                    }
                }*/
                if(idPregunta.getText().toString().equals("27") || idPregunta.getText().toString().equals("475") /*|| idPregunta.getText().toString().equals("911")*/) {
                    if (!mhPer.getFecNacimiento().trim().equals(etTextoPregunta.getText().toString().trim())){
                        //Actualiza la fecha de nacimiento
                        if(!etTextoPregunta.getText().toString().equals("")) {
                            if(!general.fechaValida(etTextoPregunta.getText().toString().trim())){
                                Toast.makeText(this, "Fecha Nacimiento incorrecta (DD/MM/AAAA)", Toast.LENGTH_SHORT).show();
                                valida = false;
                            }else{
                                mhPer.setFecNacimiento(etTextoPregunta.getText().toString().trim());
                                mhPer.save();
                            }
                        }
                    }
                }
                //Valida municipio
                if(!etTextoPregunta.getText().toString().equals("")) {
                            if(etTextoPregunta.getText().toString().trim().equals("001100") ||
                                    etTextoPregunta.getText().toString().trim().equals("Seleccione vereda")){
                                Toast.makeText(this, "Debe seleccionar Departamento y municipio", Toast.LENGTH_SHORT).show();
                                valida = false;
                            }
                }

            }

        }

        return valida;
    }

    public boolean esDecimal(String cad) {
        boolean hayPunto = false;
        StringBuffer parteEntera = new StringBuffer();
        StringBuffer parteDecimal = new StringBuffer();
        int i = 0, posicionDelPunto;

        for (i = 0; i < cad.length(); i++) {
            if (cad.charAt(i) == '.') //Detectar si hay un punto decimal en la cadena
            {
                hayPunto = true;
            }
        }
        if (hayPunto) //Si hay punto guardar la posicion donde se encuentra el carater punto
        {
            posicionDelPunto = cad.indexOf('.');                  //(si la cadena tiene varios puntos, detecta donde esta el primero).
        } else {

            return false;                                 //Si no hay punto; no es decimal
        }
        if (posicionDelPunto == cad.length() - 1 || posicionDelPunto == 0) //Si el punto esta al final o al principio no es un decimal
        {
            return false;
        }

        for (i = 0; i < posicionDelPunto; i++) {
            parteEntera.append(cad.charAt(i));                 //Guardar la parte entera en una variable
        }
        for (i = 0; i < parteEntera.length(); i++) {
            if (!Character.isDigit(parteEntera.charAt(i))) //Si alguno de los caracteres de la parte entera no son digitos no es decimal
            {
                return false;
            }
        }

        for (i = posicionDelPunto + 1; i < cad.length(); i++) {
            parteDecimal.append(cad.charAt(i));                 //Guardar la parte decimal en una variable
        }
        for (i = 0; i < parteDecimal.length(); i++) {
            if (!Character.isDigit(parteDecimal.charAt(i))) //Si alguno de los caracteres de la parte decimal no es un digito no es decimal
            {
                return false;                                   //Incluye el caso en el que la cadena tenga dos o mas puntos
            }
        }
        return true;                                            //Si paso todas las pruebas anteriores, la cadena es un Numero decimal
    }


    public void insertaRespuestaXencuesta() {


        for (int conLP = 0; conLP < listaPreguntas.size(); conLP++) {
            Integer idRespuesta = null;
            String valOtro = "";
            String valOtroC = "";
            LinearLayout llRespuestaTexto = listaPreguntas.get(conLP);



            EditText etTextoPregunta = (EditText) llRespuestaTexto.findViewById(R.id.etTextoPregunta);


            TextView cod_Hogar = (TextView) llRespuestaTexto.findViewById(R.id.cod_hogar);
            TextView per_IdPersona = (TextView) llRespuestaTexto.findViewById(R.id.per_IdPersona);
            TextView rxp_TipoPreguntaRespuesta = (TextView) llRespuestaTexto.findViewById(R.id.rxp_TipoPreguntaRespuesta);
            TextView ins_IdInstrumento = (TextView) llRespuestaTexto.findViewById(R.id.ins_IdInstrumento);
            TextView idPregunta = (TextView) llRespuestaTexto.findViewById(R.id.idPregunta);
            Integer p;
            if(per_IdPersona.getText().equals("")){
                p = null;

            }else{
                p = Integer.valueOf(per_IdPersona.getText().toString());
            }
            TextView res_IdRespuesta = (TextView) llRespuestaTexto.findViewById(R.id.res_IdRespuesta);

            Integer pbandera = 0;

            TextView tvTipoCampo = (TextView) llRespuestaTexto.findViewById(R.id.tvTipoCampo);
            //alRespuestas.getPre_campotex().toString();

            switch (tvTipoCampo.getText().toString()){
                case "DP":
                case "DT":
                case "TE":
                case "AT":
                case "TA":
                case "RES":
                case "ENFR":
                case "COMN":
                case "VERD":
                default:

                    if(res_IdRespuesta.getText() != null) {
                        if (!res_IdRespuesta.getText().equals("")) {
                            idRespuesta = Integer.valueOf(res_IdRespuesta.getText().toString());
                        }
                    }
                    if(idPregunta.getText().toString().equals("11"))
                    {
                        gestionEncuestas.SP_SET_RESPUESTAS_DE_ENCUESTA(cod_Hogar.getText().toString(), p, idRespuesta, general.fechaActual().toString() , rxp_TipoPreguntaRespuesta.getText().toString(), Integer.valueOf(ins_IdInstrumento.getText().toString()), usuarioLogin.getNombreusuario()/*usuarioLogin.getIdusuario()*/, Integer.valueOf(idPregunta.getText().toString()), pbandera);
                    }else
                    {
                        //aqui poner codigo para validar tildes
                        String textValido= general.remoAcents(etTextoPregunta.getText().toString());
                        gestionEncuestas.SP_SET_RESPUESTAS_DE_ENCUESTA(cod_Hogar.getText().toString(), p, idRespuesta, textValido/*etTextoPregunta.getText().toString()*/, rxp_TipoPreguntaRespuesta.getText().toString(), Integer.valueOf(ins_IdInstrumento.getText().toString()), usuarioLogin.getNombreusuario()/*usuarioLogin.getIdusuario()*/, Integer.valueOf(idPregunta.getText().toString()), pbandera);
                    }

                    break;

                case "CT":
                case "CH":
                    ListView lvRespuestas = (ListView) llRespuestaTexto.findViewById(R.id.lvRespuestas);
                    //AutoCompleteTextView textOtro = null;
                    emc_respuestas_instrumento tmRes = null;
                    boolean val = true;
                    String textValidovalOtro = "";

                    if(valIdRespuestasRatio.size() >= conLP) {

                        idRespuesta = Integer.valueOf(valIdRespuestasRatio.get(conLP));
                        valOtro = valOtroRespuestasRatio.get(conLP);
                        List<emc_respuestas_instrumento> lsResp = emc_respuestas_instrumento.find(emc_respuestas_instrumento.class,
                                "RESIDRESPUESTA = ?", idRespuesta.toString());
                        if (lsResp.size() > 0) {
                            tmRes = lsResp.get(0);
                        }

                        if (tmRes.getPre_validador() != null) {
                            if (tmRes.getPre_validador().equals("NU")) {

                                etTextoPregunta.requestFocus();
                                etTextoPregunta.setInputType(InputType.TYPE_CLASS_NUMBER);
                                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

                                if(valOtro.length() > 0 && !valOtro.equals("-1") ){


                                    try {

                                        int x = Integer.parseInt(valOtro.toString().trim());

                                        if(valOtro.equals("-1"))
                                        {
                                            Toast.makeText(this, "Campo esta vacio, debe diligenciarlo", Toast.LENGTH_SHORT).show();
                                            val = false;
                                        }else  if (x > Integer.valueOf(tmRes.getPre_validador_max())) {
                                            Toast.makeText(this, "Valor debe ser inferior o igual a " + tmRes.getPre_validador_max(), Toast.LENGTH_SHORT).show();
                                            val = false;
                                        }else  if (x < Integer.valueOf(tmRes.getPre_validador_min())) {
                                            Toast.makeText(this, "Valor debe ser mayor o igual a " + tmRes.getPre_validador_min(), Toast.LENGTH_SHORT).show();
                                            val = false;
                                        } else  if (valOtro.length() > Integer.valueOf(tmRes.getPre_longcampo())) {
                                            Toast.makeText(this, "Logitud incorrecta", Toast.LENGTH_SHORT).show();
                                            val = false;
                                        }

                                        if (val) {

                                            textValidovalOtro= general.remoAcents(valOtro);
                                            gestionEncuestas.SP_SET_RESPUESTAS_DE_ENCUESTA(cod_Hogar.getText().toString(), p, idRespuesta, textValidovalOtro, rxp_TipoPreguntaRespuesta.getText().toString(), Integer.valueOf(ins_IdInstrumento.getText().toString()), usuarioLogin.getNombreusuario(), Integer.valueOf(idPregunta.getText().toString()), pbandera);

                                        }


                                    } catch (NumberFormatException nfe) {
                                        Toast.makeText(this, "Debe ingresar un número valido", Toast.LENGTH_SHORT).show();
                                        val = false;

                                    }

                                }else
                                {
                                    Toast.makeText(this, "Campo esta vacio, debe diligenciarlo ", Toast.LENGTH_SHORT).show();
                                    val = false;
                                }

                            } else {
                                if (valOtro.equals("-1")) {
                                    Toast.makeText(this, "Campo esta vacio, debe diligenciarlo", Toast.LENGTH_SHORT).show();
                                    val = false;
                                }
                                if (val) {
                                    textValidovalOtro = general.remoAcents(valOtro);
                                    gestionEncuestas.SP_SET_RESPUESTAS_DE_ENCUESTA(cod_Hogar.getText().toString(), p, idRespuesta, textValidovalOtro/*valOtro*/, rxp_TipoPreguntaRespuesta.getText().toString(), Integer.valueOf(ins_IdInstrumento.getText().toString()), usuarioLogin.getNombreusuario()/*usuarioLogin.getIdusuario()*/, Integer.valueOf(idPregunta.getText().toString()), pbandera);
                                }

                            }
                        }else if (val) {

                            if (valOtro.equals("-1")) {
                                valOtro = "";
                            }
                            textValidovalOtro = general.remoAcents(valOtro);

                                gestionEncuestas.SP_SET_RESPUESTAS_DE_ENCUESTA(cod_Hogar.getText().toString(), p, idRespuesta, textValidovalOtro/*valOtro*/, rxp_TipoPreguntaRespuesta.getText().toString(), Integer.valueOf(ins_IdInstrumento.getText().toString()), usuarioLogin.getNombreusuario()/*usuarioLogin.getIdusuario()*/, Integer.valueOf(idPregunta.getText().toString()), pbandera);

                        }

                    }

                    break;

                case "LT":
                case "CL":

                    boolean valC = true;
                    for(int contR = 0; contR < lsRespuestasGen.get(conLP).size(); contR++){
                        if(lsRespuestasGen.get(conLP).get(contR).isSelected()){
                            idRespuesta = Integer.valueOf(lsRespuestasGen.get(conLP).get(contR).getId());
                            valOtroC = lsCheckOtro.get(conLP).get(contR);
                            String textValidovalOtroC = "";

                            List<emc_respuestas_instrumento> lsResp = emc_respuestas_instrumento.find(emc_respuestas_instrumento.class,
                                    "RESIDRESPUESTA = ?", idRespuesta.toString());

                            emc_respuestas_instrumento tmResC = null;
                            if (lsResp.size() > 0) {
                                tmResC = lsResp.get(0);
                            }

                            if (tmResC.getPre_validador() != null) {
                                if (tmResC.getPre_validador().equals("NU")) {

                                    etTextoPregunta.requestFocus();
                                    etTextoPregunta.setInputType(InputType.TYPE_CLASS_NUMBER);
                                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

                                    try {

                                        int x = Integer.parseInt(valOtroC.toString().trim());

                                        if (x > Integer.valueOf(tmResC.getPre_validador_max())) {
                                            Toast.makeText(this, "Valor debe ser inferior o igual a " + tmResC.getPre_validador_max(), Toast.LENGTH_SHORT).show();
                                            valC = false;
                                        }
                                        if (x < Integer.valueOf(tmResC.getPre_validador_min())) {
                                            Toast.makeText(this, "Valor debe ser mayor o igual a " + tmResC.getPre_validador_min(), Toast.LENGTH_SHORT).show();
                                            valC = false;
                                        }
                                        if (valOtro.length() > Integer.valueOf(tmResC.getPre_longcampo())) {
                                            Toast.makeText(this, "Logintud incorrecta", Toast.LENGTH_SHORT).show();
                                            valC = false;
                                        }

                                        if (valC) {

                                            //valOtro = lsCheckOtro.get(conLP).get(contR);
                                            if (valOtroC.equals("")) {
                                                valOtroC = "";
                                            }
                                            textValidovalOtroC = general.remoAcents(valOtroC);
                                            gestionEncuestas.SP_SET_RESPUESTAS_DE_ENCUESTA(cod_Hogar.getText().toString(), p, idRespuesta, textValidovalOtroC/*valOtroC*/, rxp_TipoPreguntaRespuesta.getText().toString(), Integer.valueOf(ins_IdInstrumento.getText().toString()), usuarioLogin.getNombreusuario()/*usuarioLogin.getIdusuario()*/, Integer.valueOf(idPregunta.getText().toString()), pbandera);
                                        }


                                    } catch (NumberFormatException nfe) {
                                        Toast.makeText(this, "Debe ingresar un número valido", Toast.LENGTH_SHORT).show();
                                        valC = false;
                                        val = false;
                                    }


                                }else{

                                    if (valOtroC.equals("")) {
                                        Toast.makeText(this, "Campo esta vacio, debe diligenciarlo", Toast.LENGTH_SHORT).show();
                                        valC = false;
                                    }
                                    if (valC) {
                                        textValidovalOtroC = general.remoAcents(valOtroC);
                                        gestionEncuestas.SP_SET_RESPUESTAS_DE_ENCUESTA(cod_Hogar.getText().toString(), p, idRespuesta, textValidovalOtroC/*valOtroC*/, rxp_TipoPreguntaRespuesta.getText().toString(), Integer.valueOf(ins_IdInstrumento.getText().toString()), usuarioLogin.getNombreusuario()/*usuarioLogin.getIdusuario()*/, Integer.valueOf(idPregunta.getText().toString()), pbandera);
                                    }
                                }
                            }else if (valC){

                                //valOtro = lsCheckOtro.get(conLP).get(contR);
                                if (valOtroC.equals("")) {
                                    valOtroC = "";
                                }

                                textValidovalOtroC = general.remoAcents(valOtroC);
                                gestionEncuestas.SP_SET_RESPUESTAS_DE_ENCUESTA(cod_Hogar.getText().toString(), p, idRespuesta, textValidovalOtroC/*valOtroC*/, rxp_TipoPreguntaRespuesta.getText().toString(), Integer.valueOf(ins_IdInstrumento.getText().toString()), usuarioLogin.getNombreusuario()/*usuarioLogin.getIdusuario()*/, Integer.valueOf(idPregunta.getText().toString()), pbandera);

                            }

                        }
                    }
                    break;

            }
        }
    }

    @Override
    public void onBackPressed() {

        if (!mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {


            llSalirEntrevista.setVisibility(View.VISIBLE);
            llConfirmarReiniciarCapitulo.setVisibility(View.GONE);

            llMostrarPregunta.setVisibility(View.GONE);
            llPreguntaTexto.setVisibility(View.GONE);
            llRespuestas.setVisibility(View.GONE);

            btnSalir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String est = "";
                    if (rgEstado.getCheckedRadioButtonId() == R.id.estIncompleta)
                        est = CONS_ESTADO_INCOMPLETA;
                    if (rgEstado.getCheckedRadioButtonId() == R.id.estAnulada || rgEstado.getCheckedRadioButtonId() == R.id.estAnulaHogar)
                        est = CONS_ESTADO_ANULADA;
                    if (rgEstado.getCheckedRadioButtonId() == R.id.estCerrada)
                        est = CONS_ESTADO_CERRADA;

                    if (est.equals("")) {
                        Toast.makeText(getBaseContext(), "Debe seleccionar un estado", Toast.LENGTH_SHORT).show();
                    } else if (est.equals(CONS_ESTADO_CERRADA)) {
                        String[] parCapT = {hogCodigo};
                        List<emc_capitulos_terminados> lsCapTerT = emc_capitulos_terminados.find(emc_capitulos_terminados.class, "HOGCODIGO = ? ", parCapT);
                        if (lsCapTerT.size() > 3) {


                            llSalirEntrevista.setVisibility(View.GONE);
                            llConfirmarSalirEncuesta.setVisibility(View.VISIBLE);
                            final String esT = est;



                            btnConfirmarSalirEncuesta.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    hogarActual.setEstado(esT);
                                    hogarActual.save();
                                    //modificacion javier
                                    Intent mainI = new Intent(getBaseContext(), MainActivity.class);
                                    startActivity(mainI);
                                    finish();


                                }});


                            btnCancelarSalirEncuesta.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    llConfirmarSalirEncuesta.setVisibility(View.GONE);
                                    llTomarImagenSoporte.setVisibility(View.GONE);
                                    llSalirEntrevista.setVisibility(View.VISIBLE);

                                }});



                        } else {
                            Toast.makeText(getBaseContext(), "Debe diligencia los tres primeros capítulos y un capítulo adicional", Toast.LENGTH_SHORT).show();
                        }
                    } else {

                        llSalirEntrevista.setVisibility(View.GONE);
                        llTomarImagenSoporte.setVisibility(View.GONE);
                        llConfirmarSalirEncuesta.setVisibility(View.VISIBLE);
                        final String esT = est;


                        btnConfirmarSalirEncuesta.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                hogarActual.setEstado(esT);
                                hogarActual.save();
                                //modificacion javier
                                Intent mainI = new Intent(getBaseContext(), MainActivity.class);
                                startActivity(mainI);
                                finish();


                            }});


                        btnCancelarSalirEncuesta.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                llConfirmarSalirEncuesta.setVisibility(View.GONE);
                                llSalirEntrevista.setVisibility(View.VISIBLE);

                            }});
                    }
                }
            });

            btnCancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    llSalirEntrevista.setVisibility(View.GONE);
                    llTomarImagenSoporte.setVisibility(View.GONE);
                    llMostrarPregunta.setVisibility(View.VISIBLE);
                    llPreguntaTexto.setVisibility(View.VISIBLE);
                    llRespuestas.setVisibility(View.VISIBLE);
                }
            });
        }
   }

   /* @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btnConfirmarSalirEncuesta:
                break;
            case R.id.btnCancelarSalirEncuesta:
                break;
        }

    }*/

    public item_nombre_completo getNombreC() {
        return nombreC;
    }

    public void setNombreC(item_nombre_completo nombreC) {
        this.nombreC = nombreC;
    }

    public void hiloGuardarImagen(Bitmap bitmap,String nombreFoto ,/*String hogCodigo ,*/String  esT){



        responseGuardarSoporte callback = new responseGuardarSoporte() {


            @Override
            public void resultado(boolean exito, String ruta) {
                ///26092018
                try{
                    pgDMensaje.dismiss();
                }catch (Exception e){
                    e.getMessage();
                }

                if(exito){
                    //https://leandrotemperoni.wordpress.com/2013/05/17/como-implementar-progress-dialog/
                    Toast.makeText(getApplicationContext(), ruta.trim(), Toast.LENGTH_LONG).show();


                }else{
                    Toast.makeText(getApplicationContext(), "No se pudo guardar la imagen", Toast.LENGTH_LONG).show();

                }
            }
        };

        ansycGuardarSoporte auFTP = new ansycGuardarSoporte(getApplication(),callback,bitmap,hogCodigo,nombreFoto);
        auFTP.execute();
        pgDMensaje.setCancelable(false);
        pgDMensaje.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                pgDMensaje.show();
            }
        });


    }

    public void checkScreenSize() {



        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;

        switch (screenSize) {

            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
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

                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:

                break;
            default:
                Toast.makeText(getBaseContext(), "Screen no definido: ", Toast.LENGTH_SHORT).show();
        }
    }


}
