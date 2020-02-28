package co.com.rni.encuestadormovil.configuracionencuestas;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import co.com.rni.encuestadormovil.MainActivity;
import co.com.rni.encuestadormovil.R;
import co.com.rni.encuestadormovil.model.emc_preguntas;
import co.com.rni.encuestadormovil.model.emc_preguntas_instrumento;
import co.com.rni.encuestadormovil.model.emc_temas;
import co.com.rni.encuestadormovil.model.emc_usuarios;
import co.com.rni.encuestadormovil.util.Session;

import static co.com.rni.encuestadormovil.util.general.fechaActualSinHora;
import static co.com.rni.encuestadormovil.util.gestionEncuestas.execCalculos;

public class Agregartema extends AppCompatActivity {


    private LinearLayout llIrInicio,llNuevoTema, llNuevaPregunta, llNuevaRespuesta;
    private emc_usuarios usuarioLogin;
    private List<emc_temas> listaTemas;
    private List<emc_preguntas> listaPreguntas;
    private EditText etTema, etPregunta, etObservacionPregunta, etRespuesta;
    private Button btAgregarTema,btCancelarTema, btAgregarPregunta, btCancelarPregunta;
    private Spinner spTipoTema, spTipoPregunta, spTipoTemaPre;
    private RadioGroup rgTipoPregunta, rgTipoCampo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregartema);

        usuarioLogin = Session.LoadCookie(getApplication());
        llNuevoTema = (LinearLayout) findViewById(R.id.llNuevoTema);
        etTema = (EditText) findViewById(R.id.etTema);
        etPregunta = (EditText) findViewById(R.id.etPregunta);
        etObservacionPregunta = (EditText) findViewById(R.id.etObservacionPregunta);

        btAgregarTema = (Button) findViewById(R.id.btAgregarTema);
        btCancelarTema = (Button) findViewById(R.id.btCancelarTema);

        rgTipoPregunta = (RadioGroup) findViewById(R.id.rgTipoPregunta);
        rgTipoCampo = (RadioGroup) findViewById(R.id.rgTipoCampo);

        btAgregarPregunta = (Button) findViewById(R.id.btAgregarPregunta);
        btCancelarPregunta = (Button) findViewById(R.id.btCancelarPregunta);

        llNuevaPregunta = (LinearLayout) findViewById(R.id.llNuevaPregunta);
        llNuevaRespuesta = (LinearLayout) findViewById(R.id.llNuevaRespuesta);

        etRespuesta = (EditText) findViewById(R.id.etRespuesta);


        listaTemas = emc_temas.find(emc_temas.class, null,null);

        final List<String> lsTipoTem = new ArrayList<>();
        //lsTipoTem.add("Seleccione tema");
        if (listaTemas.size() >= 1) {
            for(int conM = 0; conM < listaTemas.size(); conM++){
                emc_temas temas = listaTemas.get(conM);
                lsTipoTem.add(temas.getTem_nombretema());
            }
        }

        spTipoTemaPre = (Spinner) findViewById(R.id.spTipoTemaPre);
        spTipoTema = (Spinner) findViewById(R.id.spTipoTema);
        ArrayAdapter<String> adTipoTem=  new ArrayAdapter<String>(this,  android.R.layout.simple_spinner_item, lsTipoTem);
        spTipoTema.setAdapter(adTipoTem);



        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        llIrInicio = (LinearLayout) findViewById(R.id.llIrInicio);
        llIrInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent irMainActividy = new Intent(v.getContext(), MainActivity.class);
                startActivity(irMainActividy);

            }
        });

        btAgregarTema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!etTema.getText().toString().isEmpty()){

                    String fecha = fechaActualSinHora();
                    String estado = "A";
                    String usr = usuarioLogin.getNombreusuario().toUpperCase();
                    String nombreTema = etTema.getText().toString();
                    listaTemas = emc_temas.find(emc_temas.class, null,null);

                    emc_temas temaTemp = new emc_temas(listaTemas.size()+1, nombreTema, estado, usr, fecha, listaTemas.size()+1);
                    temaTemp.save();


                    listaTemas = emc_temas.find(emc_temas.class, null,null);

                    List<String> lsTipoTem = new ArrayList<>();
                    //lsTipoTem.add("Seleccione tema");
                    if (listaTemas.size() >= 1) {
                        for(int conM = 0; conM < listaTemas.size(); conM++){
                            emc_temas temas = listaTemas.get(conM);
                            lsTipoTem.add(temas.getTem_nombretema());
                        }
                    }

                    ArrayAdapter<String> adTipoTem=  new ArrayAdapter<String>(getBaseContext(),  android.R.layout.simple_spinner_item, lsTipoTem);
                    spTipoTema.setAdapter(adTipoTem);

                    llNuevoTema.setVisibility(View.GONE);
                    etTema.setText("");
                    llNuevaPregunta.setVisibility(View.VISIBLE);


                }else {
                    Toast.makeText(getBaseContext(), "Campo no puede estar vacio", Toast.LENGTH_LONG).show();
                }

            }
        });

        btCancelarTema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llNuevoTema.setVisibility(View.GONE);
                etTema.setText("");
            }
        });


        btAgregarPregunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tipoPreegunta = vlaidarTipoPregunta();
                String tipoCampo = vlaidarTipoCampo();


                String text = spTipoTema.getSelectedItem().toString();
                if(text.equals("Seleccione tema")){

                    Toast.makeText(getBaseContext(), text, Toast.LENGTH_LONG).show();

                }else{
                    if(tipoPreegunta.equals("GE") || tipoPreegunta.equals("IN")){

                        if(tipoCampo.equals("")){
                            Toast.makeText(getBaseContext(), "Debe seleccionar tipo de campo de la pregunta", Toast.LENGTH_LONG).show();
                        }else{

                            String fecha = fechaActualSinHora();
                            String usr = usuarioLogin.getNombreusuario().toUpperCase();
                            String nombrePregunta = etPregunta.getText().toString();
                            String preguntaObservacion = etObservacionPregunta.getText().toString();

                            String pQ = "SELECT MAX(CAST(PR.PREIDPREGUNTA AS INTEGER)) FROM EMCPREGUNTAS PR ";
                            Integer MAX_pCont_Pre = execCalculos(pQ);

                            //Insertar pregunta
                            emc_preguntas temaTemp = new emc_preguntas(Integer.valueOf(MAX_pCont_Pre+1).toString(), nombrePregunta, preguntaObservacion, "SI", usr, fecha);
                            temaTemp.save();

                            //ingresar acá EMCPREGUNTASINSTRUMENTO
                            emc_temas temSelecionado = listaTemas.get(spTipoTema.getSelectedItemPosition());


                            String QueryultimimoIdpregunta = "SELECT MAX(CAST(PR.PREIDPREGUNTA AS INTEGER)) FROM EMCPREGUNTAS PR ";
                            Integer ultimimoIdpregunta = execCalculos(QueryultimimoIdpregunta);

                            String QueryultimimoIdOrdenPregunta = "SELECT MAX(CAST(PR.IXPORDEN AS INTEGER)) FROM EMCPREGUNTASINSTRUMENTO PR WHERE TEMIDTEMA = '"+temSelecionado.getTem_idtema().toString()+"'";
                            Integer ultimimoOrdenpregunta = execCalculos(QueryultimimoIdOrdenPregunta);

                            if(ultimimoOrdenpregunta == null){
                                ultimimoOrdenpregunta = 0;
                            }

                            emc_preguntas_instrumento temPregIns;
                            temPregIns = new emc_preguntas_instrumento("1",temSelecionado.getTem_idtema().toString(),ultimimoIdpregunta.toString(),Integer.valueOf(ultimimoOrdenpregunta+1).toString(),"SI",tipoPreegunta,tipoCampo,null,"SUPER","01/01/14",null,null,null,null,null,null,null,null); temPregIns.save();

                            ///
                            listaTemas = emc_temas.find(emc_temas.class, null,null);

                            List<String> lsTipoTem = new ArrayList<>();
                            //lsTipoTem.add("Seleccione tema");
                            if (listaTemas.size() >= 1) {
                                for(int conM = 0; conM < listaTemas.size(); conM++){
                                    emc_temas temas = listaTemas.get(conM);
                                    lsTipoTem.add(temas.getTem_nombretema());
                                }
                            }

                            ArrayAdapter<String> adTipoTem=  new ArrayAdapter<String>(getBaseContext(),  android.R.layout.simple_spinner_item, lsTipoTem);
                            spTipoTemaPre.setAdapter(adTipoTem);


                            emc_temas temSel = listaTemas.get(spTipoTemaPre.getSelectedItemPosition());
                            listaPreguntas = emc_preguntas.find(emc_preguntas.class, " PREIDPREGUNTA IN \n" +
                                    "(SELECT PREIDPREGUNTA FROM EMCPREGUNTASINSTRUMENTO WHERE TEMIDTEMA = ? )",temSel.getTem_idtema().toString());

                            List<String> lsTipoPre = new ArrayList<>();

                            if (listaPreguntas.size() >= 1) {
                                for(int conM = 0; conM < listaPreguntas.size(); conM++){
                                    emc_preguntas pregunta = listaPreguntas.get(conM);
                                    lsTipoPre.add(pregunta.getPre_pregunta());
                                }
                            }

                            spTipoPregunta = (Spinner) findViewById(R.id.spTipoPregunta);
                            ArrayAdapter<String> adTipoPre=  new ArrayAdapter<String>(getBaseContext(),  android.R.layout.simple_spinner_item, lsTipoPre);
                            spTipoPregunta.setAdapter(adTipoPre);

                            llNuevaPregunta.setVisibility(View.GONE);
                            llNuevaRespuesta.setVisibility(View.VISIBLE);

                        }

                    }else{
                        Toast.makeText(getBaseContext(), "Debe seleccionar tipo de pregunta General o Individual", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

        btCancelarPregunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etTema.setText("");
                etPregunta.setText("");
                llNuevaPregunta.setVisibility(View.GONE);
            }
        });

        spTipoTemaPre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                emc_temas selTem = listaTemas.get(position);
                listaPreguntas = emc_preguntas.find(emc_preguntas.class, " PREIDPREGUNTA IN \n" +
                        "(SELECT PREIDPREGUNTA from EMCPREGUNTASINSTRUMENTO WHERE TEMIDTEMA = ? )",selTem.getTem_idtema().toString());
                List<String> lsTipoPre = new ArrayList<>();

                if (listaPreguntas.size() >= 1) {
                    for(int conM = 0; conM < listaPreguntas.size(); conM++){
                        emc_preguntas pregunta = listaPreguntas.get(conM);
                        lsTipoPre.add(pregunta.getPre_pregunta());
                    }
                }
                ArrayAdapter<String> adTipoPre=  new ArrayAdapter<String>(getBaseContext(),  android.R.layout.simple_spinner_item, lsTipoPre);
                spTipoPregunta.setAdapter(adTipoPre);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public String vlaidarTipoPregunta(){

        String tipoPreegunta = "";

        if (rgTipoPregunta.getCheckedRadioButtonId() == R.id.rdGeneral){
            tipoPreegunta = "GE";

        }
        if (rgTipoPregunta.getCheckedRadioButtonId() == R.id.rdIndividual){
            tipoPreegunta = "IN";

        }
        return tipoPreegunta;

    }

    public String vlaidarTipoCampo(){

        String tipoCampo = "";


        if (rgTipoCampo.getCheckedRadioButtonId() == R.id.rdDeptoMuni){
            tipoCampo = "DP";

        }
        if (rgTipoCampo.getCheckedRadioButtonId() == R.id.rdVereda){
            tipoCampo = "VERD";

        }
        if (rgTipoCampo.getCheckedRadioButtonId() == R.id.rdCampoAbierto){
            tipoCampo = "CT";

        }
        if (rgTipoCampo.getCheckedRadioButtonId() == R.id.rdSeleccionUnica){
            tipoCampo = "LT";

        }
        if (rgTipoCampo.getCheckedRadioButtonId() == R.id.rdSeleccionMultiple){
            tipoCampo = "LT";

        }
        if (rgTipoCampo.getCheckedRadioButtonId() == R.id.rdObservacionCapitulo){
            tipoCampo = "TA";

        }

        return tipoCampo;
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

    // Detectar cuando se presiona el botón de retroceso
    @Override
    public void onBackPressed() {
        finish();
        Intent irmain = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(irmain);
    }

}
