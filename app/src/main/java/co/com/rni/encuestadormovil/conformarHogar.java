package co.com.rni.encuestadormovil;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.github.johnkil.print.PrintView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import co.com.rni.encuestadormovil.adapter.*;
import co.com.rni.encuestadormovil.model.*;
import co.com.rni.encuestadormovil.sqlite.*;
import co.com.rni.encuestadormovil.util.*;

import static co.com.rni.encuestadormovil.util.general.fechaActual;
import static co.com.rni.encuestadormovil.util.general.fechaActualSinHora;
import static co.com.rni.encuestadormovil.util.general.fechaValida;


public class conformarHogar extends AppCompatActivity {

    private ActionBar abActivity;
    private PrintView pvBuscarVictima;
    private EditText etNumDocumento;
    private ListView lvResultadoVictimas;
    private LinearLayout llResultados;
    private conformarHogarResponse vicResponse;
    private List<emc_victimas_nosugar> lsVictimas;
    private List<emc_personas_encuestas_no_sugar> lsPersonasEncuestadas;
    private String UIDhogar;
    private ArrayList<emc_miembros_hogar> miembrosHogar;
    private LinearLayout llHogar, llPresentacion, llPresentacionP, btAceptarNoAceptar, llMensajeInicioNRC, llParametricas;
    private LinearLayout llPresentacionAcnur, llaUTORIZA, btAceptarNoAceptarAcnur, llMensajeInicioACNUR;
    private miembrosHogarAdapter adMiembros;
    private Integer hlOrigen;
    private LinearLayout llAgregar;
    private LinearLayout llBuscando;
    private TextView textoBuscando;
    private ProgressDialog pdBuscando;
    private ProgressDialog pdBuscandoGrupoHOgar;
    private ProgressDialog pdIniciandoEncuesta;
    private ListView lvMiembrosHogar;
    private List<emc_victimas_nosugar> lsVicHogar;
    private conformarHogarResponse miemResponse;
    private LinearLayout llAgregarMHogar;
    private ListView lvAgregarMHogar;
    private LinearLayout llNuevaPersona;
    private Button btAgregarPersona;
    private Spinner spTipoDoc;
    private Button btCancelarNP;
    private Button btAgregarNP;
    private Button button_filepicker;
    private EditText etNumDoc;
    private TextView valetNumDoc;
    private LinearLayout llvaletNumDoc;
    private EditText etApellido1;
    private LinearLayout llvaltApellido1;
    private TextView valtApellido1;
    private EditText etApellido2;
    private EditText etNombre1;
    private LinearLayout llvaltNombre1;
    private TextView valtNombre1;
    private EditText etNombre2;
    private EditText etFecNac;
    private LinearLayout llvaletFecNac;
    private TextView valFecNac;
    public emc_usuarios usuarioLogin;
    private DbHelper myDB;
    private Button btAceptar, btNoAceptar, btAceptarAcnur, btNoAceptarAcnur;
    ArrayList<emc_miembros_hogar> miembro = new ArrayList<emc_miembros_hogar>();
    private ProgressDialog pgMensajeCrearPersona;
    private boolean capitulo12;

    private static final int ACTIVITY_CHOOSE_FILE = 3;
    private final static Logger LOGGER = Logger.getLogger("bitacora.subnivel.Control");
    private static final String CONST_AUTORIZADO = "AUTORIZADO";
    private static final String CONST_TUTOR = "TUTOR";
    private static final String CONST_CUIDADOR_PERMANENTE = "CUIDADOR PERMANENTE";
    private static final String CONST_JEFE_MAYOR_18_ANIOS = "El jefe de hogar debe ser mayor a 18 años";
    private static final String CONST_NO_RESULTADO_BUSQUEDA = "No hay resultados para la busqueda, puede agregar la persona como no incluida en el RUV";
    private static final String CONST_NO_INCLUIDO = "NO INCLUIDO";
    private static final String CONST_BUSCANDO = "Buscando...";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conformar_hogar);


        usuarioLogin = Session.LoadCookie(getApplication());
        String fch = fechaActual().replace("/", "");
        fch = fch.replace(" ", "");
        fch = fch.replace(":", "");
        UIDhogar = UUID.randomUUID().toString();
        UIDhogar = UIDhogar.substring(0, 4) + "-" + usuarioLogin.getIdusuario() + "-" + fch;
        miembrosHogar = new ArrayList<>();

        pdBuscando = new ProgressDialog(this);
        pdBuscando.setMessage("Buscando victimas...");
        pdBuscando.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.cancel();
            }
        });

        pdBuscando.setCancelable(false);
        pdBuscando.setCanceledOnTouchOutside(false);

        pdBuscandoGrupoHOgar = new ProgressDialog(this);
        pdBuscandoGrupoHOgar.setMessage("Buscando grupo familiar...");
        pdBuscandoGrupoHOgar.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.cancel();
            }
        });


        pdIniciandoEncuesta = new ProgressDialog(this);
        pdIniciandoEncuesta.setMessage("Creando hogar");

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        pdIniciandoEncuesta.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.cancel();
            }
        });

        pgMensajeCrearPersona = new ProgressDialog(this);
        pgMensajeCrearPersona.setMessage("Agregando persona...");
        pgMensajeCrearPersona.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.cancel();
            }
        });

        etNumDoc = (EditText) findViewById(R.id.etNumDoc);
        valetNumDoc = (TextView) findViewById(R.id.valetNumDoc);
        llvaletNumDoc = (LinearLayout) findViewById(R.id.llvaletNumDoc);
        etApellido1 = (EditText) findViewById(R.id.etApellido1);
        valtApellido1 = (TextView) findViewById(R.id.valtApellido1);
        llvaltApellido1 = (LinearLayout) findViewById(R.id.llvaltApellido1);
        etApellido2 = (EditText) findViewById(R.id.etApellido2);
        etNombre1 = (EditText) findViewById(R.id.etNombre1);
        valtNombre1 = (TextView) findViewById(R.id.valtNombre1);
        llvaltNombre1 = (LinearLayout) findViewById(R.id.llvaltNombre1);
        etNombre2 = (EditText) findViewById(R.id.etNombre2);
        etFecNac = (EditText) findViewById(R.id.etFecNac);
        valFecNac = (TextView) findViewById(R.id.valFecNac);
        llvaletFecNac = (LinearLayout) findViewById(R.id.llvaletFecNac);


        abActivity = getSupportActionBar();
        abActivity.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.emc_red)));
        abActivity.setHomeAsUpIndicator(R.mipmap.ic_launcher);
        abActivity.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
        abActivity.setTitle("Hogar");
        abActivity.setDisplayHomeAsUpEnabled(true);
        abActivity.setHomeButtonEnabled(true);


        myDB = new DbHelper(this);

        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.menu_hogar, null);
        abActivity.setCustomView(mCustomView);
        abActivity.setDisplayShowCustomEnabled(true);


        llBuscando = (LinearLayout) findViewById(R.id.llBuscando);
        textoBuscando = (TextView) findViewById(R.id.textoBuscando);
        lvMiembrosHogar = (ListView) findViewById(R.id.lvMiembrosHogar);
        llAgregarMHogar = (LinearLayout) findViewById(R.id.llAgregarMHogar);
        llAgregar = (LinearLayout) mCustomView.findViewById(R.id.llAgregar);
        lvAgregarMHogar = (ListView) findViewById(R.id.lvAgregarMHogar);
        llAgregar.setVisibility(View.GONE);
        llHogar = (LinearLayout) findViewById(R.id.llHogar);

        //Presentación NRC
        llPresentacion = (LinearLayout) findViewById(R.id.llPresentacion);
        llPresentacionP = (LinearLayout) findViewById(R.id.llPresentacionP);
        btAceptarNoAceptar = (LinearLayout) findViewById(R.id.btAceptarNoAceptar);
        llMensajeInicioNRC = (LinearLayout) findViewById(R.id.llMensajeInicioNRC);
        btAceptar = (Button) findViewById(R.id.btAceptar);
        btNoAceptar = (Button) findViewById(R.id.btNoAceptar);

        //Presentación acnur
        llPresentacionAcnur = (LinearLayout) findViewById(R.id.llPresentacionAcnur);
        btAceptarNoAceptarAcnur = (LinearLayout) findViewById(R.id.btAceptarNoAceptarAcnur);
        llMensajeInicioACNUR = (LinearLayout) findViewById(R.id.llMensajeInicioACNUR);
        llaUTORIZA = (LinearLayout) findViewById(R.id.llaUTORIZA);
        btAceptarAcnur = (Button) findViewById(R.id.btAceptarAcnur);
        btNoAceptarAcnur = (Button) findViewById(R.id.btNoAceptarAcnur);


        llParametricas = (LinearLayout) findViewById(R.id.llParametricas);

        //498 ANDAGUEDAMOVIL
        //if(usuarioLogin.getIdPerfil().toUpperCase().equals("498"))
        if (usuarioLogin.getNombrePerfil().toUpperCase().equals("NRC")) {

            llParametricas.setVisibility(View.GONE);
            llPresentacion.setVisibility(View.VISIBLE);
            llPresentacionP.setVisibility(View.VISIBLE);
            btAceptarNoAceptar.setVisibility(View.VISIBLE);

        }

        if (usuarioLogin.getIdPerfil().toUpperCase().equals("710")) {

            llParametricas.setVisibility(View.GONE);
            llaUTORIZA.setVisibility(View.VISIBLE);
            llPresentacionAcnur.setVisibility(View.VISIBLE);
            btAceptarNoAceptarAcnur.setVisibility(View.VISIBLE);

        }


        if (usuarioLogin.getNombrePerfil().toUpperCase().equals("ANDAGUEDAMOVIL")) {
            llHogar.setVisibility(View.VISIBLE);

        }


        btAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llPresentacion.setVisibility(View.GONE);
                llPresentacionP.setVisibility(View.GONE);
                llParametricas.setVisibility(View.VISIBLE);
                llMensajeInicioNRC.setVisibility(View.VISIBLE);
                llHogar.setVisibility(View.VISIBLE);
                btAceptarNoAceptar.setVisibility(View.GONE);
                btAgregarPersona.setText("AGREGAR MIEMBRO AL NUCLEO FAMILIAR ");
            }
        });

        btNoAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(getBaseContext(), MainActivity.class);
                startActivity(main);
                finish();

            }
        });

        btAceptarAcnur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llPresentacionAcnur.setVisibility(View.GONE);
                llParametricas.setVisibility(View.VISIBLE);
                llMensajeInicioACNUR.setVisibility(View.VISIBLE);
                llHogar.setVisibility(View.VISIBLE);
                llaUTORIZA.setVisibility(View.GONE);
                btAceptarNoAceptarAcnur.setVisibility(View.GONE);

            }
        });

        btNoAceptarAcnur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(getBaseContext(), MainActivity.class);
                startActivity(main);
                finish();

            }
        });


        llAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Crea hogar
                pdIniciandoEncuesta.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pdIniciandoEncuesta.setMessage("Procesando...");
                pdIniciandoEncuesta.setTitle("Creando Hogar");
                pdIniciandoEncuesta.setCancelable(false);
                pdIniciandoEncuesta.setCanceledOnTouchOutside(false);
                pdIniciandoEncuesta.show();

                final View vi = v;

                if (usuarioLogin.getIdPerfil().equals("710")) {

                    responseVictimas responseVi = new responseVictimas() {
                        @Override
                        public void resultadoVictimas(List<emc_victimas_nosugar> lsVictimasRP, List<emc_personas_encuestas_no_sugar> lsResultadoPersonasEncuestadas) {

                            if (lsResultadoPersonasEncuestadas.size() > 0) {
                                capitulo12 = true;
                            } else {
                                capitulo12 = false;
                            }

                            responseCrearHogar responsecrearHogar = new responseCrearHogar() {
                                @Override
                                public void resultado(boolean exito, emc_hogares hogar) {
                                    if (exito) {
                                        Session.SaveHogarActual(getApplication(), hogar);
                                        Intent dilPregunta = new Intent(vi.getContext(), DiligenciarPregunta.class);
                                        startActivity(dilPregunta);
                                        finish();

                                    } else {
                                        pdIniciandoEncuesta.hide();
                                        Toast.makeText(getBaseContext(), "Debe seleccionar el responsable del hogar", Toast.LENGTH_SHORT).show();


                                    }
                                }
                            };

                            asyncCrearHogar atCrearHogar = new asyncCrearHogar(getBaseContext(), responsecrearHogar, miembrosHogar, UIDhogar, usuarioLogin, lsVictimas, myDB, capitulo12);
                            atCrearHogar.execute();

                        }
                    };

                    asyncConsultarVictimas atBDocumento = new asyncConsultarVictimas(getBaseContext(), responseVi, "DOCUMENTO", miembrosHogar, miembrosHogar.get(0).getDocumento(), myDB, usuarioLogin);
                    atBDocumento.execute();


                } else {
                    responseCrearHogar responsecrearHogar = new responseCrearHogar() {
                        @Override
                        public void resultado(boolean exito, emc_hogares hogar) {
                            if (exito) {
                                Session.SaveHogarActual(getApplication(), hogar);
                                Intent dilPregunta = new Intent(vi.getContext(), DiligenciarPregunta.class);
                                startActivity(dilPregunta);
                                finish();

                            } else {
                                pdIniciandoEncuesta.hide();
                                Toast.makeText(getBaseContext(), "Debe seleccionar el responsable del hogar", Toast.LENGTH_SHORT).show();
                            }
                        }
                    };
                    asyncCrearHogar atCrearHogar = new asyncCrearHogar(getBaseContext(), responsecrearHogar, miembrosHogar, UIDhogar, usuarioLogin, lsVictimas, myDB, capitulo12);
                    atCrearHogar.execute();
                }


            }
        });

        vicResponse = new conformarHogarResponse() {
            @Override
            public void onClickAgregarVictimaResponse(Integer position) {
                if (lsVictimas.size() > 0) {
                    llResultados.setVisibility(View.GONE);
                    boolean agregar = true;
                    emc_victimas_nosugar vicSeleccion = lsVictimas.get(position);

                    for (Integer conMH = 0; conMH < miembrosHogar.size(); conMH++) {
                        if (miembrosHogar.get(conMH).getPer_idpersona().equals(String.valueOf(vicSeleccion.getConsPersona())))
                            agregar = false;
                    }

                    if (agregar) {
                        emc_miembros_hogar nuevoMiembro = new emc_miembros_hogar();
                        nuevoMiembro.setHog_codigo(UIDhogar);
                        nuevoMiembro.setPer_idpersona(String.valueOf(vicSeleccion.getConsPersona()));
                        nuevoMiembro.setTipoDoc(vicSeleccion.getTipoDoc());
                        nuevoMiembro.setDocumento(vicSeleccion.getDocumento());
                        nuevoMiembro.setNombre1(vicSeleccion.getNombre1());
                        nuevoMiembro.setNombre2(vicSeleccion.getNombre2());
                        nuevoMiembro.setApellido1(vicSeleccion.getApellido1());
                        nuevoMiembro.setApellido2(vicSeleccion.getApellido2());
                        String fecNac = "01/01/0001";
                        if (!vicSeleccion.getFecNacimiento().equals(""))
                            fecNac = vicSeleccion.getFecNacimiento();

                        if (fecNac.length() > 2) {
                            fecNac = fecNac.replace("ENE", "01");
                            fecNac = fecNac.replace("FEB", "02");
                            fecNac = fecNac.replace("MAR", "03");
                            fecNac = fecNac.replace("ABR", "04");
                            fecNac = fecNac.replace("MAY", "05");
                            fecNac = fecNac.replace("JUN", "06");
                            fecNac = fecNac.replace("JUL", "07");
                            fecNac = fecNac.replace("AGO", "08");
                            fecNac = fecNac.replace("SEP", "09");
                            fecNac = fecNac.replace("OCT", "10");
                            fecNac = fecNac.replace("NOV", "11");
                            fecNac = fecNac.replace("DIC", "12");
                            fecNac = fecNac.replace("-", "/");
                        }


                        nuevoMiembro.setFecNacimiento(fecNac);
                        nuevoMiembro.setEstado(vicSeleccion.getEstado());
                        nuevoMiembro.setInd_jefe("");

                        miembrosHogar.add(nuevoMiembro);
                        //
                        miembro.add(nuevoMiembro);
                        adMiembros.notifyDataSetChanged();
                        etNumDocumento.setText("");
                        llHogar.setVisibility(View.VISIBLE);
                        llAgregar.setVisibility(View.VISIBLE);
                        textoBuscando.setText(CONST_BUSCANDO);
                        llBuscando.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onDeleteMiembroHogar(Integer position) {
                if (miembrosHogar.size() > position) {

                    String[] parRes = {miembrosHogar.get(position).documento, miembrosHogar.get(position).getPer_idpersona(),
                            miembrosHogar.get(position).documento, miembrosHogar.get(position).getPer_idpersona()};
                    emc_miembros_hogar.deleteAll(emc_miembros_hogar.class,
                            "DOCUMENTO = ? " +
                                    "AND PERIDPERSONA = ? AND USUFECHACREACION = " +
                                    "(SELECT MAX(USUFECHACREACION) FROM  EMCMIEMBROSHOGAR WHERE DOCUMENTO = ? AND PERIDPERSONA = ? )", parRes);


                    String[] parP = {miembrosHogar.get(position).getPer_idpersona(), miembrosHogar.get(position).documento};
                    emc_victimas.deleteAll(emc_victimas.class, "CONS_PERSONA = ? AND DOCUMENTO = ?", parP);

                    miembrosHogar.remove(miembrosHogar.get(position));


                    if (miembro.size() > 0) {
                        try {
                            miembro.remove(miembro.get(position));
                        } catch (Exception e) {

                        }

                    }

                    adMiembros.notifyDataSetChanged();

                    if (miembrosHogar.size() == 0 && (!usuarioLogin.getIdPerfil().toUpperCase().equals("638") && !usuarioLogin.getIdPerfil().toUpperCase().equals("658") && !usuarioLogin.getIdPerfil().toUpperCase().equals("710")))

                        llHogar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onSelectTipoPersona(Integer position) {

                int mayoresedad = 0;
                int menoresedad = 0;
                String fechaHoyanio = fechaActualSinHora();

                if (miembrosHogar.size() > position) {
                    for (int conM = 0; conM < miembrosHogar.size(); conM++) {
                        if (conM == position) {

                            emc_miembros_hogar vicSeleccion = miembrosHogar.get(position);
                            String fecha = vicSeleccion.getFecNacimiento();

                            String dia = "";
                            String mes = "";
                            String anio = null;
                            if (fecha.equals("--") || fecha.equals("-")) {
                                dia = "01";
                                mes = "01";
                                anio = "1970";


                            } else {
                                dia = fecha.substring(0, 2);
                                mes = fecha.substring(3, 5);

                            }

                            if (fecha.length() == 9) {
                                String mesV = fecha.substring(3, 6);

                                switch (mesV) {
                                    case "JAN":
                                        mes = "01";
                                        break;
                                    case "FEB":
                                        mes = "02";
                                        break;
                                    case "MAR":
                                        mes = "03";
                                        break;
                                    case "APR":
                                        mes = "04";
                                        break;
                                    case "MAY":
                                        mes = "05";
                                        break;
                                    case "JUN":
                                        mes = "06";
                                        break;
                                    case "JUL":
                                        mes = "07";
                                        break;
                                    case "AUG":
                                        mes = "08";
                                        break;
                                    case "SEP":
                                        mes = "09";
                                        break;
                                    case "OCT":
                                        mes = "10";
                                        break;
                                    case "NOV":
                                        mes = "11";
                                        break;
                                    case "DEC":
                                        mes = "12";
                                        break;
                                    default:
                                        mes = "0";
                                }

                                anio = fecha.substring(7, 9);
                                fechaHoyanio = fechaHoyanio.substring(8, 10);
                                if (Integer.valueOf(anio) > Integer.parseInt(fechaHoyanio)) {
                                    anio = "19" + anio;
                                } else {
                                    anio = "20" + anio;
                                }

                            } else if (fecha.length() == 8) {
                                mes = fecha.substring(3, 5);
                                anio = fecha.substring(6, 8);
                                fechaHoyanio = fechaHoyanio.substring(8, 10);
                                if (Integer.valueOf(anio) > Integer.parseInt(fechaHoyanio)) {
                                    anio = "19" + anio;
                                } else {
                                    anio = "20" + anio;
                                }
                            } else if (fecha.length() == 10) {
                                anio = fecha.substring(6, 10);

                            }

                            boolean validadorEstado = false;

                            for (Integer cMHFv = 0; cMHFv < miembrosHogar.size(); cMHFv++) {
                                emc_miembros_hogar miembroFv = miembrosHogar.get(cMHFv);
                                String estado = miembroFv.getEstado().toUpperCase();
                                if (estado.equals("INCLUIDO")) {
                                    validadorEstado = true;
                                    break;
                                }
                            }


                            if (anio != null) {
                                Integer edad = general.CalcularEdad(Integer.valueOf(anio), Integer.valueOf(mes), Integer.valueOf(dia));
                                if (edad >= 18) {
                                    mayoresedad++;
                                }
                                if (edad < 18) {
                                    menoresedad++;
                                }

                                if (edad < 18 && (miembrosHogar.get(position).getTipoPersona().equals(CONST_AUTORIZADO) || miembrosHogar.get(position).getTipoPersona().equals(CONST_TUTOR)
                                        || miembrosHogar.get(position).getTipoPersona().equals(CONST_CUIDADOR_PERMANENTE))) {
                                    Toast.makeText(getBaseContext(), "El Autorizado, Tutor y Cuidador Permanente no puede ser menor de edad", Toast.LENGTH_SHORT).show();
                                }
                                if (miembrosHogar.get(0).getEstado().equals(CONST_NO_INCLUIDO) && miembrosHogar.get(position).getTipoPersona().equals(CONST_AUTORIZADO)) {
                                    Toast.makeText(getBaseContext(), "El Autorizado debe estar Incluido en el RUV", Toast.LENGTH_SHORT).show();
                                }
                                if (miembrosHogar.size() < 2 && miembrosHogar.get(position).getTipoPersona().equals(CONST_TUTOR)) {
                                    Toast.makeText(getBaseContext(), "Para seleccionar Tutor debe haber minimo 2 miembros de hogar en el grupo familiar", Toast.LENGTH_SHORT).show();
                                }
                                if (miembrosHogar.size() > 1 && miembrosHogar.get(position).getTipoPersona().equals(CONST_TUTOR) && menoresedad < 1) {
                                    Toast.makeText(getBaseContext(), "Para seleccionar Tutor debe haber minimo 1 menor de edad en el grupo familiar", Toast.LENGTH_SHORT).show();
                                }
                                if (miembrosHogar.get(position).getTipoPersona().equals(CONST_CUIDADOR_PERMANENTE) && mayoresedad < 2) {
                                    Toast.makeText(getBaseContext(), "Para seleccionar Cuidador Permanente debe haber minimo un miembro de hogar mayor de edad diferente del Cuidador", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (usuarioLogin.getIdPerfil().equals("710") && validadorEstado == false) {

                                        if (edad >= 18) {
                                            miembrosHogar.get(conM).setInd_jefe("SI");

                                        } else {
                                            Toast.makeText(getBaseContext(), CONST_JEFE_MAYOR_18_ANIOS, Toast.LENGTH_SHORT).show();
                                        }

                                    } else if (edad > 17) {
                                        miembrosHogar.get(conM).setInd_jefe("SI");
                                    } else {
                                        Toast.makeText(getBaseContext(), CONST_JEFE_MAYOR_18_ANIOS, Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }

                        } else {
                            miembrosHogar.get(conM).setInd_jefe("");
                        }
                    }
                    adMiembros.notifyDataSetChanged();
                }

            }

            @Override
            public void onSelectJefeHogar(Integer position, Integer tipoPersona) {

                boolean hogarvalido = true;
                int mayoresedad = 0;
                int menoresedad = 0;
                String fechaHoyanio = fechaActualSinHora();

                if (miembrosHogar.size() > position) {
                    for (int conM = 0; conM < miembrosHogar.size(); conM++) {
                        if (tipoPersona == 1 || tipoPersona == 2 || tipoPersona == 3) {
                            if (conM == position) {

                                emc_miembros_hogar vicSeleccion = miembrosHogar.get(position);
                                String fecha = vicSeleccion.getFecNacimiento();

                                String dia = "";
                                String mes = "";
                                String anio = null;
                                if (fecha.equals("--") || fecha.equals("-")) {
                                    dia = "01";
                                    mes = "01";
                                    anio = "1970";

                                } else {
                                    dia = fecha.substring(0, 2);
                                    mes = fecha.substring(3, 5);

                                }

                                if (fecha.length() == 9) {
                                    String mesV = fecha.substring(3, 6);

                                    switch (mesV) {
                                        case "JAN":
                                            mes = "01";
                                            break;
                                        case "FEB":
                                            mes = "02";
                                            break;
                                        case "MAR":
                                            mes = "03";
                                            break;
                                        case "APR":
                                            mes = "04";
                                            break;
                                        case "MAY":
                                            mes = "05";
                                            break;
                                        case "JUN":
                                            mes = "06";
                                            break;
                                        case "JUL":
                                            mes = "07";
                                            break;
                                        case "AUG":
                                            mes = "08";
                                            break;
                                        case "SEP":
                                            mes = "09";
                                            break;
                                        case "OCT":
                                            mes = "10";
                                            break;
                                        case "NOV":
                                            mes = "11";
                                            break;
                                        case "DEC":
                                            mes = "12";
                                            break;
                                        default:
                                            mes = "0";
                                    }

                                    anio = fecha.substring(7, 9);
                                    fechaHoyanio = fechaHoyanio.substring(8, 10);
                                    if (Integer.valueOf(anio) > Integer.parseInt(fechaHoyanio)) {
                                        anio = "19" + anio;
                                    } else {
                                        anio = "20" + anio;
                                    }

                                } else if (fecha.length() == 8) {
                                    mes = fecha.substring(3, 5);
                                    anio = fecha.substring(6, 8);
                                    fechaHoyanio = fechaHoyanio.substring(8, 10);
                                    if (Integer.valueOf(anio) > Integer.parseInt(fechaHoyanio)) {
                                        anio = "19" + anio;
                                    } else {
                                        anio = "20" + anio;
                                    }
                                } else if (fecha.length() == 10) {
                                    anio = fecha.substring(6, 10);

                                }

                                boolean validadorEstado = false;

                                for (Integer cMHFv = 0; cMHFv < miembrosHogar.size(); cMHFv++) {
                                    emc_miembros_hogar miembroFv = miembrosHogar.get(cMHFv);
                                    String estado = miembroFv.getEstado().toUpperCase();
                                    if (estado.equals("INCLUIDO")) {
                                        validadorEstado = true;
                                        break;
                                    }
                                }
                                //Verifica la edad por cada miembro de hogar
                                for (Integer cMHFv = 0; cMHFv < miembrosHogar.size(); cMHFv++) {
                                    emc_miembros_hogar miembroFv = miembrosHogar.get(cMHFv);
                                    String fecNacimiento = miembroFv.getFecNacimiento();
                                    Integer edadnacimiento = general.CalcularEdad(fecNacimiento);
                                    if (edadnacimiento >= 18) {
                                        mayoresedad++;
                                    }
                                    if (edadnacimiento < 18) {
                                        menoresedad++;
                                    }
                                }

                                if (anio != null) {
                                    Integer edad = general.CalcularEdad(Integer.valueOf(anio), Integer.valueOf(mes), Integer.valueOf(dia));


                                    if (edad < 18 && (tipoPersona == 1 || tipoPersona == 2 || tipoPersona == 3)) {
                                        adMiembros.notifyDataSetChanged();
                                        Toast.makeText(getBaseContext(), "El Autorizado, Tutor y Cuidador Permanente no puede ser menor de edad", Toast.LENGTH_SHORT).show();
                                    } else if (miembrosHogar.get(0).getEstado().equals(CONST_NO_INCLUIDO) && tipoPersona == 1) {
                                        adMiembros.notifyDataSetChanged();
                                        Toast.makeText(getBaseContext(), "El Autorizado debe estar Incluido en el RUV", Toast.LENGTH_SHORT).show();
                                    } else if (miembrosHogar.size() < 2 && tipoPersona == 2) {
                                        adMiembros.notifyDataSetChanged();
                                        Toast.makeText(getBaseContext(), "Para seleccionar Tutor debe haber minimo 2 miembros de hogar en el grupo familiar", Toast.LENGTH_SHORT).show();
                                    } else if (miembrosHogar.size() > 1 && tipoPersona == 2 && menoresedad < 1) {
                                        adMiembros.notifyDataSetChanged();
                                        Toast.makeText(getBaseContext(), "Para seleccionar Tutor debe haber minimo 1 menor de edad en el grupo familiar", Toast.LENGTH_SHORT).show();
                                    } else if (tipoPersona == 3 && mayoresedad < 2) {
                                        adMiembros.notifyDataSetChanged();
                                        Toast.makeText(getBaseContext(), "Para seleccionar Cuidador Permanente debe haber minimo un miembro de hogar mayor de edad diferente del Cuidador", Toast.LENGTH_SHORT).show();
                                    } else if (usuarioLogin.getIdPerfil().equals("710") && validadorEstado == false) {

                                        if (edad >= 18) {
                                            miembrosHogar.get(conM).setInd_jefe("SI");
                                        } else {
                                            hogarvalido = false;
                                            miembrosHogar.get(conM).setInd_jefe("");
                                            adMiembros.notifyDataSetChanged();
                                            Toast.makeText(getBaseContext(), CONST_JEFE_MAYOR_18_ANIOS, Toast.LENGTH_SHORT).show();
                                        }

                                    } else if (edad > 17) {
                                        Toast.makeText(getBaseContext(), "Selecciono como responsable de hogar a : " + vicSeleccion.getNombre1() + " " + vicSeleccion.getApellido1(), Toast.LENGTH_SHORT).show();

                                        miembrosHogar.get(conM).setInd_jefe("SI");
                                        miembrosHogar.get(conM).setTipoPersona(tipoPersona.toString());
                                    } else {
                                        hogarvalido = false;
                                        adMiembros.notifyDataSetChanged();
                                        miembrosHogar.get(conM).setInd_jefe("");
                                        Toast.makeText(getBaseContext(), CONST_JEFE_MAYOR_18_ANIOS, Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }
                        }


                    }

                }

                int totaljefe = 0;
                List<emc_miembros_hogar> lismi = adMiembros.getItems();

                if (hogarvalido == false) {
                    for (int i = 0; i < lismi.size(); i++) {
                        emc_miembros_hogar mi = lismi.get(i);
                        mi.setInd_jefe("");
                    }
                }

                if (hogarvalido) {
                    for (int i = 0; i < lismi.size(); i++) {
                        emc_miembros_hogar mi = lismi.get(i);
                        if (mi.getInd_jefe().equals("SI")) {
                            totaljefe = totaljefe + 1;
                        }
                    }
                    if (totaljefe > 1) {
                        for (int i = 0; i < lismi.size(); i++) {
                            emc_miembros_hogar mi = lismi.get(i);
                            mi.setInd_jefe("");
                        }
                        adMiembros.notifyDataSetChanged();
                        Toast.makeText(getBaseContext(), "Selecciono mas de un jefe de hogar", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            //GRUPO FAMILIAR, NO BORRAR
            public void onVerHogar(Integer position) {/*
                pdBuscandoGrupoHOgar.setMessage("Buscando victimas");
                pdBuscandoGrupoHOgar.setCanceledOnTouchOutside(false);
                pdBuscandoGrupoHOgar.show();
                llAgregarMHogar.setVisibility(View.VISIBLE);
                llResultados.setVisibility(View.GONE);
                llHogar.setVisibility(View.VISIBLE);
                llNuevaPersona.setVisibility(View.GONE);
                //lsVicHogar = emc_victimas.find(emc_victimas.class, "HOGAR IN (SELECT v.HOGAR FROM EMCVICTIMAS v WHERE  v.CONS_PERSONA = ? )", miembrosHogar.get(position).getPer_idpersona());

                responseVictimas rpBuscarVictimas = new responseVictimas() {
                    @Override
                    public void resultadoVictimas(List<emc_victimas_nosugar> lsVictimasRP, List<emc_personas_encuestas_no_sugar> lsResultadoPersonasEncuestadas) {
                        lsPersonasEncuestadas = lsResultadoPersonasEncuestadas;
                        lsVicHogar = lsVictimasRP;
                        if(lsVicHogar.size() > 0){
                            buscarVictimasAdapter adHogarAdd = new buscarVictimasAdapter(getBaseContext(), R.id.tvIDMiebro, lsVicHogar, miemResponse);
                            lvAgregarMHogar.setAdapter(adHogarAdd);
                        }else{
                            Toast.makeText(getBaseContext(), "La victima no tiene grupo familiar", Toast.LENGTH_SHORT).show();
                        }
                        pdBuscandoGrupoHOgar.hide();
                    }
                };
                asyncConsultarVictimas atBVictimas = new asyncConsultarVictimas(getBaseContext(), rpBuscarVictimas, "HOGAR",  miembrosHogar.get(position).getPer_idpersona(), myDB);
                atBVictimas.execute();*/
            }
        };


        miemResponse = new conformarHogarResponse() {
            @Override
            public void onClickAgregarVictimaResponse(Integer position) {
                if (lsVicHogar.size() > 0) {
                    llAgregarMHogar.setVisibility(View.GONE);
                    llResultados.setVisibility(View.GONE);
                    llHogar.setVisibility(View.VISIBLE);
                    boolean agregar = true;
                    emc_victimas_nosugar vicSeleccion = lsVicHogar.get(position);

                    for (Integer conMH = 0; conMH < miembrosHogar.size(); conMH++) {
                        if (miembrosHogar.get(conMH).getPer_idpersona().equals(String.valueOf(vicSeleccion.getConsPersona())))
                            agregar = false;
                    }

                    if (agregar) {
                        emc_miembros_hogar nuevoMiembro = new emc_miembros_hogar();
                        nuevoMiembro.setHog_codigo(UIDhogar);
                        nuevoMiembro.setPer_idpersona(String.valueOf(vicSeleccion.getConsPersona()));
                        nuevoMiembro.setTipoDoc(vicSeleccion.getTipoDoc());
                        nuevoMiembro.setDocumento(vicSeleccion.getDocumento());
                        nuevoMiembro.setNombre1(general.remoAcents(vicSeleccion.getNombre1()));
                        nuevoMiembro.setNombre2(general.remoAcents(vicSeleccion.getNombre2()));
                        nuevoMiembro.setApellido1(general.remoAcents(vicSeleccion.getApellido1()));
                        nuevoMiembro.setApellido2(general.remoAcents(vicSeleccion.getApellido2()));
                        String fecNac = "01/01/0001";
                        if (!vicSeleccion.getFecNacimiento().equals(""))
                            fecNac = vicSeleccion.getFecNacimiento();
                        nuevoMiembro.setFecNacimiento(fecNac);
                        nuevoMiembro.setEstado(vicSeleccion.getEstado());
                        nuevoMiembro.setInd_jefe("");
                        nuevoMiembro.setUsu_fechacreacion(fechaActual());
                        nuevoMiembro.setUsu_fechacreacion(usuarioLogin.getIdusuario());

                        miembrosHogar.add(nuevoMiembro);
                        adMiembros.notifyDataSetChanged();
                        llHogar.setVisibility(View.VISIBLE);
                        llAgregar.setVisibility(View.VISIBLE);
                        textoBuscando.setText(CONST_BUSCANDO);
                        llBuscando.setVisibility(View.GONE);
                    }
                }

            }

            @Override
            public void onDeleteMiembroHogar(Integer position) {

            }

            @Override
            public void onSelectJefeHogar(Integer position, Integer tipoPersona) {

            }

            @Override
            public void onVerHogar(Integer position) {

            }

            @Override
            public void onSelectTipoPersona(Integer position) {

            }

        };

        lvResultadoVictimas = (ListView) findViewById(R.id.lvResultadoVictimas);
        llResultados = (LinearLayout) findViewById(R.id.llResultados);
        hlOrigen = llResultados.getLayoutParams().height;
        etNumDocumento = (EditText) findViewById(R.id.etNumDocumento);


        pvBuscarVictima = (PrintView) findViewById(R.id.pvBuscarVictima);
        pvBuscarVictima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pdBuscando.show();
                textoBuscando.setText(CONST_BUSCANDO);

                llPresentacion.setVisibility(View.GONE);
                llPresentacionP.setVisibility(View.GONE);
                llMensajeInicioNRC.setVisibility(View.GONE);
                llNuevaPersona.setVisibility(View.GONE);
                btAceptarNoAceptar.setVisibility(View.GONE);


                int lND = etNumDocumento.length();

                if (etNumDocumento.equals("Número de documento")) {
                    llBuscando.setVisibility(View.GONE);
                    pdBuscando.hide();
                    Toast.makeText(getBaseContext(), "Debe ingresar un número de documento", Toast.LENGTH_SHORT).show();
                } else if (lND <= 0) {
                    llBuscando.setVisibility(View.GONE);
                    pdBuscando.hide();
                    Toast.makeText(getBaseContext(), "Debe ingresar un número de documento", Toast.LENGTH_SHORT).show();
                } else {

                    llBuscando.setVisibility(View.VISIBLE);
                    responseVictimas rpBDocumento = new responseVictimas() {
                        @Override
                        public void resultadoVictimas(List<emc_victimas_nosugar> lsVictimasRP, List<emc_personas_encuestas_no_sugar> lsResultadoPersonasEncuestadas) {
                            lsPersonasEncuestadas = lsResultadoPersonasEncuestadas;

                            lsVicHogar = lsVictimasRP;

                            if (lsResultadoPersonasEncuestadas != null) {

                                if (lsResultadoPersonasEncuestadas.size() > 0) {

                                    textoBuscando.setTextColor(getResources().getColor(R.color.emc_red));
                                    StringBuilder builder = new StringBuilder();
                                    builder.append("El siguiente grupo familiar registra encuesta en ");
                                    builder.append(lsResultadoPersonasEncuestadas.get(0).getMunicipio());
                                    builder.append(" en el barrio ");
                                    builder.append(lsResultadoPersonasEncuestadas.get(0).getBarrio());
                                    builder.append(", el ");
                                    builder.append(lsResultadoPersonasEncuestadas.get(0).getInicio_encuesta());
                                    builder.append(" con código de hogar ");
                                    builder.append(lsResultadoPersonasEncuestadas.get(0).getId_entrevista());
                                    textoBuscando.setText(builder);

                                    llBuscando.setVisibility(View.VISIBLE);

                                    lsVictimas = lsVictimasRP;
                                    if (!lsVictimas.isEmpty()) {
                                        llResultados.setVisibility(View.VISIBLE);

                                        if ((70 + (lsVictimas.size() * 80)) < hlOrigen) {
                                            llResultados.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 70 + (lsVictimas.size() * 80)));
                                        } else {
                                            llResultados.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, hlOrigen));
                                        }

                                        buscarVictimasAdapter bvAd = new buscarVictimasAdapter(getBaseContext(), R.id.tvIDMiebro, lsVictimas, vicResponse);
                                        lvResultadoVictimas.setAdapter(bvAd);

                                    } else {
                                        Toast.makeText(getBaseContext(), CONST_NO_RESULTADO_BUSQUEDA, Toast.LENGTH_SHORT).show();
                                        llResultados.setVisibility(View.GONE);
                                        etNumDoc.setText(etNumDocumento.getText());
                                        etNumDocumento.setText("");
                                        llvaletNumDoc.setVisibility(View.GONE);
                                        llvaltNombre1.setVisibility(View.GONE);
                                        llvaltApellido1.setVisibility(View.GONE);
                                        llvaletFecNac.setVisibility(View.GONE);
                                        llNuevaPersona.setVisibility(View.VISIBLE);
                                    }

                                } else {

                                    lsVictimas = lsVictimasRP;
                                    if (!lsVictimas.isEmpty()) {
                                        llResultados.setVisibility(View.VISIBLE);
                                        if ((70 + (lsVictimas.size() * 80)) < hlOrigen) {
                                            llResultados.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 70 + (lsVictimas.size() * 80)));
                                        } else {
                                            llResultados.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, hlOrigen));
                                        }

                                        buscarVictimasAdapter bvAd = new buscarVictimasAdapter(getBaseContext(), R.id.tvIDMiebro, lsVictimas, vicResponse);
                                        lvResultadoVictimas.setAdapter(bvAd);
                                        llBuscando.setVisibility(View.GONE);
                                    } else {
                                        Toast.makeText(getBaseContext(), CONST_NO_RESULTADO_BUSQUEDA, Toast.LENGTH_SHORT).show();
                                        llResultados.setVisibility(View.GONE);
                                        llBuscando.setVisibility(View.GONE);
                                        etNumDoc.setText(etNumDocumento.getText());
                                        etNumDocumento.setText("");
                                        llvaletNumDoc.setVisibility(View.GONE);
                                        llvaltNombre1.setVisibility(View.GONE);
                                        llvaltApellido1.setVisibility(View.GONE);
                                        llvaletFecNac.setVisibility(View.GONE);
                                        llNuevaPersona.setVisibility(View.VISIBLE);
                                    }
                                }

                            } else {

                                lsVictimas = lsVictimasRP;
                                if (!lsVictimas.isEmpty()) {
                                    llResultados.setVisibility(View.VISIBLE);
                                    if ((70 + (lsVictimas.size() * 80)) < hlOrigen) {
                                        llResultados.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 70 + (lsVictimas.size() * 80)));
                                    } else {
                                        llResultados.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, hlOrigen));
                                    }

                                    buscarVictimasAdapter bvAd = new buscarVictimasAdapter(getBaseContext(), R.id.tvIDMiebro, lsVictimas, vicResponse);
                                    lvResultadoVictimas.setAdapter(bvAd);
                                    llBuscando.setVisibility(View.GONE);
                                } else {
                                    Toast.makeText(getBaseContext(), CONST_NO_RESULTADO_BUSQUEDA, Toast.LENGTH_SHORT).show();
                                    llResultados.setVisibility(View.GONE);
                                    llBuscando.setVisibility(View.GONE);
                                    etNumDoc.setText(etNumDocumento.getText());
                                    etNumDocumento.setText("");
                                    llvaletNumDoc.setVisibility(View.GONE);
                                    llvaltNombre1.setVisibility(View.GONE);
                                    llvaltApellido1.setVisibility(View.GONE);
                                    llvaletFecNac.setVisibility(View.GONE);
                                    llNuevaPersona.setVisibility(View.VISIBLE);
                                }

                            }

                            pdBuscando.hide();

                        }
                    };

                    asyncConsultarVictimas atBDocumento = new asyncConsultarVictimas(getBaseContext(), rpBDocumento, "DOCUMENTO", etNumDocumento.getText().toString(), myDB, usuarioLogin);
                    atBDocumento.execute();


                }
            }
        });


        lvMiembrosHogar = (ListView) findViewById(R.id.lvMiembrosHogar);
        adMiembros = new miembrosHogarAdapter(this, miembrosHogar, vicResponse);
        lvMiembrosHogar.setAdapter(adMiembros);

        llNuevaPersona = (LinearLayout) findViewById(R.id.llNuevaPersona);
        btAgregarPersona = (Button) findViewById(R.id.btAgregarPersona);
        btAgregarPersona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llvaletNumDoc.setVisibility(View.GONE);
                llvaltNombre1.setVisibility(View.GONE);
                llvaltApellido1.setVisibility(View.GONE);
                llvaletFecNac.setVisibility(View.GONE);
                llNuevaPersona.setVisibility(View.VISIBLE);
                llAgregarMHogar.setVisibility(View.GONE);
            }
        });

        //Lista de tipos de documento - revisar la fuente
        ArrayList<String> lsTipoDoc = new ArrayList<>();
        lsTipoDoc.add("CEDULA CIUDADANIA");
        lsTipoDoc.add("CEDULA EXTRANJERIA");
        lsTipoDoc.add("TARJETA IDENTIDAD");
        lsTipoDoc.add("REGISTRO CIVIL DE NACIMIENTO");
        lsTipoDoc.add("CONTRASEÑA");
        lsTipoDoc.add("NUIP");
        lsTipoDoc.add("PASAPORTE");
        lsTipoDoc.add("LIBRETA MILITAR");
        lsTipoDoc.add("NIT");
        lsTipoDoc.add("INDOCUMENTADO");
        lsTipoDoc.add("NO SABE");
        lsTipoDoc.add("OTRO");


        spTipoDoc = (Spinner) findViewById(R.id.spTipoDoc);


        ArrayAdapter<String> adTipoDoc = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lsTipoDoc);
        spTipoDoc.setAdapter(adTipoDoc);


        spTipoDoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {

                if (adapterView.getItemAtPosition(pos).toString().equals("INDOCUMENTADO")) {
                    etNumDoc.setText("000");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btCancelarNP = (Button) findViewById(R.id.btCancelarNP);
        btCancelarNP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llNuevaPersona.setVisibility(View.GONE);

                etNumDoc.setText("");
                etApellido1.setText("");
                etApellido2.setText("");
                etNombre1.setText("");
                etNombre2.setText("");
                etFecNac.setText("");
            }
        });
        btAgregarNP = (Button) findViewById(R.id.btAgregarNP);

        //13/02/2020
        button_filepicker = (Button) findViewById(R.id.button_filepicker);

        button_filepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseFile;
                Intent intent;
                chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("file/*");
                intent = Intent.createChooser(chooseFile, "Choose a file");
                startActivityForResult(intent, ACTIVITY_CHOOSE_FILE);
            }
        });
        //13/02/2020

        btAgregarNP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                pgMensajeCrearPersona.setCancelable(false);
                pgMensajeCrearPersona.setCanceledOnTouchOutside(false);
                pgMensajeCrearPersona.show();


                llvaletNumDoc.setVisibility(View.GONE);
                llvaltNombre1.setVisibility(View.GONE);
                llvaltApellido1.setVisibility(View.GONE);
                llvaletFecNac.setVisibility(View.GONE);

                Integer idPersona = 0;

                idPersona = gestionEncuestas.execCalculos("SELECT MAX(CONS_PERSONA) FROM EMCVICTIMAS;");

                if (idPersona == null) {
                    idPersona = 90000000;
                }
                if (idPersona < 90000000) {
                    idPersona = 90000000;
                }

                idPersona++;
                emc_miembros_hogar nuevaPersona = new emc_miembros_hogar();
                emc_victimas nuevaPersonaNoIncluido = new emc_victimas();


                responseAgregarPNoIncluido callback = new responseAgregarPNoIncluido() {


                    @Override
                    public void resultado(boolean exito, emc_miembros_hogar nuevaPersona, String idPersona, emc_victimas nuevaPersonaNoIncluido, int reultdoconsult) {

                        if (exito) {
                            miembrosHogar.add(nuevaPersona);
                            nuevaPersona.save();

                            adMiembros.notifyDataSetChanged();
                            llNuevaPersona.setVisibility(View.GONE);

                            nuevaPersonaNoIncluido.save();
                            llAgregar.setVisibility(View.VISIBLE);

                            etNumDoc.setText("");
                            etNombre1.setText("");
                            etNombre2.setText("");
                            etApellido1.setText("");
                            etApellido2.setText("");
                            etFecNac.setText("");

                            pgMensajeCrearPersona.hide();

                        } else if (!exito) {

                            if (etNumDoc.getText().toString().equals("")) {
                                valetNumDoc.setText("Número documento no puede ser vacio");
                                llvaletNumDoc.setVisibility(View.VISIBLE);
                            }

                            int lengetNumDoc = etNumDoc.getText().toString().length();

                            if (lengetNumDoc > 11) {
                                valetNumDoc.setText("Logitud del documento no puede ser mayor a 11 digitos");
                                llvaletNumDoc.setVisibility(View.VISIBLE);

                            }
                            if (etApellido1.getText().toString().equals("")) {
                                valtApellido1.setText("Primer apellido no puede estar vacio");
                                llvaltApellido1.setVisibility(View.VISIBLE);


                            }
                            if (etNombre1.getText().toString().equals("")) {
                                valtNombre1.setText("Primer nombre no puede ser vacio");
                                llvaltNombre1.setVisibility(View.VISIBLE);
                                pgMensajeCrearPersona.hide();


                            }
                            if (etFecNac.getText().toString().equals("")) {


                                valFecNac.setText("Debe ingresar fecha de nacimiento, si no la sabe, ingresar una fecha aproximada");
                                llvaletFecNac.setVisibility(View.VISIBLE);
                                pgMensajeCrearPersona.hide();

                            }

                            boolean valFecha = fechaValida(etFecNac.getText().toString());

                            if ((!etFecNac.getText().toString().equals("") && !valFecha) || etFecNac.length() != 10) {

                                valFecNac.setText("Formato de fecha incorrecto");
                                llvaletFecNac.setVisibility(View.VISIBLE);
                                pgMensajeCrearPersona.hide();


                            }

                            String fechaHoy = fechaActualSinHora();

                            try {
                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                if (!sdf.parse(etFecNac.getText().toString()).before(sdf.parse(fechaHoy))) {


                                    valFecNac.setText("Fecha no puede ser mayor a " + fechaHoy);
                                    llvaletFecNac.setVisibility(View.VISIBLE);
                                    pgMensajeCrearPersona.hide();

                                }
                            } catch (Exception e) {
                                valFecNac.setText("Fecha no puede ser mayor a " + fechaHoy);
                                llvaletFecNac.setVisibility(View.VISIBLE);
                                pgMensajeCrearPersona.hide();
                            }

                            pgMensajeCrearPersona.hide();

                            if (reultdoconsult > 0) {

                                etNumDoc.setText("");
                                valetNumDoc.setText("Documento registrado como incluido puede consultar el documento en el campo de busqueda");
                                llvaletNumDoc.setVisibility(View.VISIBLE);
                                Toast.makeText(getBaseContext(), "Documento registrado como incluido puede consultar el documento en el campo de busqueda", Toast.LENGTH_SHORT).show();

                            }

                        }

                    }
                };

                asyncAgregarNoIncluido auANI = new asyncAgregarNoIncluido(nuevaPersona, UIDhogar, idPersona.toString(), usuarioLogin, spTipoDoc.getSelectedItem().toString(),
                        etNumDoc.getText().toString().toUpperCase(), general.remoAcents(etNombre1.getText().toString().toUpperCase()),
                        general.remoAcents(etNombre2.getText().toString().toUpperCase()),
                        etApellido1.getText().toString().toUpperCase(), etApellido2.getText().toString().toUpperCase(),
                        etFecNac.getText().toString(), callback, myDB, nuevaPersonaNoIncluido);
                auANI.execute();
            }


        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_conformar_hogar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {

        }

        return super.onOptionsItemSelected(item);
    }

    public boolean crearHogar() {

        emc_hogares hogar = new emc_hogares();

        boolean tieneJefe = false;

        for (Integer cMH = 0; cMH < miembrosHogar.size(); cMH++) {
            emc_miembros_hogar miembro = miembrosHogar.get(cMH);
            if (miembro.getInd_jefe().equals("SI")) {
                tieneJefe = true;
                break;
            }

        }

        if (tieneJefe) {

            hogar.setHog_codigo(UIDhogar);
            hogar.setUsu_usuariocreacion(usuarioLogin.getNombreusuario());
            hogar.setUsu_fechacreacion(fechaActual());
            hogar.setEstado("Incompleta");
            hogar.setHog_tipo("A");
            hogar.save();

            for (Integer cMH = 0; cMH < miembrosHogar.size(); cMH++) {
                emc_miembros_hogar miembro = miembrosHogar.get(cMH);
                miembro.save();
                if (miembro.getInd_jefe().equals("SI")) {
                    tieneJefe = true;
                }


                gestionEncuestas.GIC_INSERT_VALIDADOR_HOGAR(Integer.valueOf(miembro.getPer_idpersona()), miembro.getHog_codigo(), miembro.getEstado(), 1);


                String valJefe = "NO JEFE";
                if (miembro.getInd_jefe().equals("SI")) {
                    valJefe = "JEFE";
                }

                gestionEncuestas.GIC_INSERT_VALIDADOR_PARENT(Integer.valueOf(miembro.getPer_idpersona()), miembro.getHog_codigo(), valJefe, 1);

                gestionEncuestas.GIC_INSERT_VALIDADOR_TD(Integer.valueOf(miembro.getPer_idpersona()), miembro.getHog_codigo(), miembro.getTipoDoc(), 1);


                lsVictimas = myDB.getlistaVictimasCons_persona(miembro.getDocumento(), miembro.getPer_idpersona());
                final String hogarcodigo = miembro.getHog_codigo();
                if (lsVictimas.size() > 0) {
                    emc_victimas_nosugar tmPer = lsVictimas.get(0);
                    if (tmPer.hv1 != null) {

                        if (tmPer.getHv1() == 1) {
                            gestionEncuestas.GIC_INSERT_VALIDADOR_HECHO_AUX(tmPer.getConsPersona(), hogarcodigo, 1, 1);
                        }
                        if (tmPer.hv2 != null) {

                            if (tmPer.getHv2() == 1) {
                                gestionEncuestas.GIC_INSERT_VALIDADOR_HECHO_AUX(tmPer.getConsPersona(), hogarcodigo, 2, 1);
                            }

                        }

                        if (tmPer.hv3 != null) {

                            if (tmPer.getHv3() == 1) {
                                gestionEncuestas.GIC_INSERT_VALIDADOR_HECHO_AUX(tmPer.getConsPersona(), hogarcodigo, 3, 1);
                            }

                        }

                        if (tmPer.hv4 != null) {
                            if (tmPer.getHv4() == 1) {
                                gestionEncuestas.GIC_INSERT_VALIDADOR_HECHO_AUX(tmPer.getConsPersona(), hogarcodigo, 4, 1);
                            }

                        }

                        if (tmPer.hv5 != null) {
                            if (tmPer.getHv5() == 1) {
                                gestionEncuestas.GIC_INSERT_VALIDADOR_HECHO_AUX(tmPer.getConsPersona(), hogarcodigo, 5, 1);
                            }
                        }

                        if (tmPer.hv6 != null) {
                            if (tmPer.getHv6() == 1) {
                                gestionEncuestas.GIC_INSERT_VALIDADOR_HECHO_AUX(tmPer.getConsPersona(), hogarcodigo, 6, 1);
                            }
                        }

                        if (tmPer.hv7 != null) {
                            if (tmPer.getHv7() == 1) {
                                gestionEncuestas.GIC_INSERT_VALIDADOR_HECHO_AUX(tmPer.getConsPersona(), hogarcodigo, 7, 1);
                            }
                        }
                        if (tmPer.hv8 != null) {
                            if (tmPer.getHv8() == 1) {
                                gestionEncuestas.GIC_INSERT_VALIDADOR_HECHO_AUX(tmPer.getConsPersona(), hogarcodigo, 8, 1);
                            }
                        }
                        if (tmPer.hv9 != null) {

                            if (tmPer.getHv9() == 1) {
                                gestionEncuestas.GIC_INSERT_VALIDADOR_HECHO_AUX(tmPer.getConsPersona(), hogarcodigo, 9, 1);
                            }
                        }

                        if (tmPer.hv10 != null) {

                            if (tmPer.getHv10() == 1) {
                                gestionEncuestas.GIC_INSERT_VALIDADOR_HECHO_AUX(tmPer.getConsPersona(), hogarcodigo, 10, 1);
                            }
                        }

                        if (tmPer.hv11 != null) {

                            if (tmPer.getHv11() == 1) {
                                gestionEncuestas.GIC_INSERT_VALIDADOR_HECHO_AUX(tmPer.getConsPersona(), hogarcodigo, 11, 1);
                            }
                        }
                        if (tmPer.hv12 != null) {
                            if (tmPer.getHv12() == 1) {
                                gestionEncuestas.GIC_INSERT_VALIDADOR_HECHO_AUX(tmPer.getConsPersona(), hogarcodigo, 12, 1);
                            }
                        }
                        if (tmPer.hv13 != null) {
                            if (tmPer.getHv13() == 1) {
                                gestionEncuestas.GIC_INSERT_VALIDADOR_HECHO_AUX(tmPer.getConsPersona(), hogarcodigo, 13, 1);
                            }
                        }
                        if (tmPer.hv14 != null && tmPer.getHv14() == 1) {
                            gestionEncuestas.GIC_INSERT_VALIDADOR_HECHO_AUX(tmPer.getConsPersona(), hogarcodigo, 14, 1);
                        }

                    }

                }

            }

            Session.SaveHogarActual(getApplication(), hogar);
            return true;


        } else {

            try {
                pdIniciandoEncuesta.dismiss();
            } catch (Exception e) {
                e.getMessage();
            }

            try {
                pdIniciandoEncuesta.hide();
            } catch (Exception e) {
                e.getMessage();
            }

            return false;
        }

    }

    //13/02/2020
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_CANCELED) {
            //Cancelado por el usuario
        }
        if ((resultCode == RESULT_OK) && (requestCode == ACTIVITY_CHOOSE_FILE)) {
            //Procesar el resultado
            Uri uri = data.getData();

            String DB_PATH = Environment.getExternalStorageDirectory() + "/soportescuidadortutor/";
            Uri ruta = Uri.parse(DB_PATH);
            File directory = new File(ruta.getPath());
            if (!directory.exists()) {
                directory.mkdirs();
            }

                try {

                    File forigs = new File(uri.getPath());
                    InputStream in = new FileInputStream(forigs);
                    File forig = new File(Environment.getExternalStorageDirectory(), "soportescuidadortutor/" + forigs.getName());
                    OutputStream out = new FileOutputStream(forig);

                    byte[] buf = new byte[1024];
                    int len;

                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }

                    out.flush();
                    in.close();
                    out.close();

                } catch (IOException ex) {
                    LOGGER.log(Level.SEVERE , ex.getMessage());
                }


        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@android.support.annotation.NonNull MenuItem item) {
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
                default:
                    break;
            }
            return false;
        }
    };

    @Override
    // Detectar cuando se presiona el botón de retroceso
    public void onBackPressed() {
        Intent irMainActividy = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(irMainActividy);
        finish();
    }


}

