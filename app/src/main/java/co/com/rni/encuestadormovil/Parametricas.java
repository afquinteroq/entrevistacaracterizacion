package co.com.rni.encuestadormovil;





import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;

import android.os.Bundle;

import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.opencsv.stream.reader.LineReader;

import java.util.List;
import co.com.rni.encuestadormovil.model.emc_hogares;
import co.com.rni.encuestadormovil.model.emc_usuarios;
import co.com.rni.encuestadormovil.model.emc_validadores_persona;
import co.com.rni.encuestadormovil.model.emc_version;
import co.com.rni.encuestadormovil.util.Session;
import co.com.rni.encuestadormovil.util.ansyncBackupDBVivanto;
import co.com.rni.encuestadormovil.util.asynDownloadBDFTP;
import co.com.rni.encuestadormovil.util.asynUpdateDBVivanto;
import co.com.rni.encuestadormovil.util.asyncDownloadDBVivantoFTP;
import co.com.rni.encuestadormovil.util.asyncUpdateDBVivanto;
import co.com.rni.encuestadormovil.util.comprobarConexionInternet;
import co.com.rni.encuestadormovil.util.responseBackupBDVivanto;
import co.com.rni.encuestadormovil.util.responseUpdateDBVivanto;
import lombok.NonNull;




/**
 * Created by ASUS on 14/12/2018.
 */

public class Parametricas extends AppCompatActivity {

    private ActionBar abActivity;
    private LinearLayout llIrMenuPrincipal;
    private LinearLayout llDescargarBaseVivanto;
    private LinearLayout llactualizarPersonas;
    private LinearLayout llIrIndex;
    private LinearLayout llIrCardViewd;
    private ProgressDialog pgMsj;
    private TextView tvTextoMensaje;
    private TextView tvTextoTransferir;
    private TextView tvEnviarMail;
    private emc_usuarios usuarioLogin;
    private static final int WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 1;
    private static final int READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 2;


    String correo = "afquinteroq@gmail.com";
    String contrasena = "Af80857397*+";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametriscas);


        abActivity = getSupportActionBar();
        abActivity.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.emc_red)));
        abActivity.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
        abActivity.setTitle("Parametricas");
        abActivity.setDisplayHomeAsUpEnabled(true);
        abActivity.setHomeButtonEnabled(true);
        abActivity.setHomeAsUpIndicator(R.mipmap.ic_launcher);

        llDescargarBaseVivanto = (LinearLayout) findViewById(R.id.llDescargarBaseVivanto);
        llactualizarPersonas = (LinearLayout) findViewById(R.id.llactualizarPersonas);

        usuarioLogin = Session.LoadCookie(getApplication());

        if(usuarioLogin.getIdPerfil().equals("499")){
            llactualizarPersonas.setVisibility(View.VISIBLE);
        }

        llIrIndex = (LinearLayout) findViewById(R.id.llIrIndex);
        llIrCardViewd = (LinearLayout) findViewById(R.id.llIrCardViewd);

        if(usuarioLogin.getIdusuario().equals("50214")  || usuarioLogin.getIdusuario().equals("0")){
            llactualizarPersonas.setVisibility(View.VISIBLE);
            llIrIndex.setVisibility(View.VISIBLE);
            llIrCardViewd.setVisibility(View.VISIBLE);
        }

        llIrMenuPrincipal = (LinearLayout) findViewById(R.id.llIrMenuPrincipal);

        tvTextoMensaje = (TextView) findViewById(R.id.tvTextoMensaje);
        tvTextoTransferir = (TextView) findViewById(R.id.tvTextoTransferir);
        tvEnviarMail = (TextView) findViewById(R.id.tvEnviarMail);

        pgMsj = new ProgressDialog(this);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);



        if (ActivityCompat.checkSelfPermission(Parametricas.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //Si el permiso no se encuentra concedido se solicita
            ActivityCompat.requestPermissions(Parametricas.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE);
        } else {
            //Si el permiso esá concedico prosigue con el flujo normal
            //permissionGranted();
            tvTextoMensaje.setText("Permiso de descarga concedido");
            tvTextoMensaje.setVisibility(View.VISIBLE);
        }


        if (ActivityCompat.checkSelfPermission(Parametricas.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //Si el permiso no se encuentra concedido se solicita
            ActivityCompat.requestPermissions(Parametricas.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE);
        } else {
            //Si el permiso esá concedico prosigue con el flujo normal

            //permissionGranted();
            tvTextoMensaje.setText("Permiso de descarga concedido");
            tvTextoMensaje.setVisibility(View.VISIBLE);
        }


        llDescargarBaseVivanto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int valConexion = 0;
                boolean val = true;
                tvTextoMensaje.setText("");
                tvTextoTransferir.setText("");
                comprobarConexionInternet objCCI = new comprobarConexionInternet();
                valConexion = objCCI.isNetworkConnected(getBaseContext());

                emc_validadores_persona.deleteAll(emc_validadores_persona.class,
                        "UPPER(hogcodigo) NOT IN (select DISTINCT UPPER(h.hogcodigo) FROM emchogares h, EMCMIEMBROSHOGAR m WHERE h.hogcodigo=m.hogcodigo )",
                        null);

                emc_hogares.deleteAll(emc_hogares.class,
                        "UPPER(hogcodigo) not in (select distinct UPPER(h.hogcodigo) FROM emchogares h, EMCMIEMBROSHOGAR m where h.hogcodigo=m.hogcodigo )",
                        null);

                //List<emc_hogares> lsHogares = emc_hogares.find(emc_hogares.class, "USUUSUARIOCREACION = '" + usuarioLogin.getNombreusuario() + "' AND ESTADO <> 'TRANSMITIDA' AND ESTADO <> 'Incompleta' AND ESTADO <> 'Anulada' ", null);
                List<emc_hogares> lsHogares = emc_hogares.find(emc_hogares.class, "ESTADO <> 'TRANSMITIDA'  AND ESTADO <> 'Anulada' ", null);

                if(lsHogares.size() > 0) {
                    tvTextoMensaje.setVisibility(View.VISIBLE);
                    tvTextoTransferir.setVisibility(View.VISIBLE);
                    tvTextoMensaje.setText("Tiene encuestas pendientes por transferir");
                    tvTextoTransferir.setText("Se debe transferir o anular las entrevistas para poder actualizar la base");

                }else{


                    if(valConexion == 1){
                    pgMsj.setMessage("Generando backup de la apilcación EncuestadorMovil");
                    pgMsj.setCancelable(false);
                    pgMsj.setCanceledOnTouchOutside(false);
                    pgMsj.show();
                    responseBackupBDVivanto callBack = new responseBackupBDVivanto() {
                        @Override
                        public void resultado(boolean exito) {
                            if(exito){
                                //tvEnviarMail.setVisibility(View.VISIBLE);
                                //tvEnviarMail.setText("Se envió una copia de seguridad al correo andres.quintero@unidadvictimas.gov.co");
                            }else{
                                //tvEnviarMail.setVisibility(View.VISIBLE);
                                //tvEnviarMail.setText("Error al enviar copia de seguridad");
                            }

                        }
                    };
                    ansyncBackupDBVivanto auBDBV = new ansyncBackupDBVivanto(getBaseContext(),callBack);
                    auBDBV.execute();

                    tvTextoTransferir.setVisibility(View.GONE);
                    //if(valConexion == 1){

                        pgMsj.setMessage("Descargando Encuesta vivanto");


                        responseUpdateDBVivanto callback = new responseUpdateDBVivanto() {
                            @Override
                            public void resultado(boolean exito) {
                                //pgMsj.hide();
                                if(exito){
                                    tvTextoMensaje.setVisibility(View.VISIBLE);
                                    tvTextoMensaje.setText("Encuesta descargada");
                                    pgMsj.setMessage("Actualizando preguntas y respuestas");
                                    responseUpdateDBVivanto callback = new responseUpdateDBVivanto() {
                                        @Override
                                        public void resultado(boolean exito) {
                                            if(exito == true){
                                                pgMsj.hide();
                                                tvTextoMensaje.setVisibility(View.VISIBLE);
                                                tvTextoMensaje.setText("Encuesta Actualizada, puede volver a la página principal e iniciar una encuesta.");
                                            }else{
                                                tvTextoMensaje.setVisibility(View.VISIBLE);
                                                tvTextoMensaje.setText("No se pudo actualziar la Encuesta");
                                                pgMsj.hide();
                                            }

                                        }
                                    };

                                    asyncUpdateDBVivanto auFTP = new asyncUpdateDBVivanto(getBaseContext(),callback,"ftp.unidadvictimas.gov.co", "encuestadormovil", "Kart3g3na","/Parametricas/");
                                    auFTP.execute();

                                }else{
                                    pgMsj.hide();
                                    tvTextoMensaje.setVisibility(View.VISIBLE);
                                    tvTextoMensaje.setText("Error al descargar la base encuesta");

                                }
                            }
                        };

                        asyncDownloadDBVivantoFTP auFTP = new asyncDownloadDBVivantoFTP(getBaseContext(),callback,"ftp.unidadvictimas.gov.co", "encuestadormovil", "Kart3g3na");
                        auFTP.execute();

                    }else if(valConexion == 0){
                        tvTextoMensaje.setText("No tiene conexión a internet");
                        tvTextoMensaje.setVisibility(View.VISIBLE);
                        Toast.makeText(getBaseContext(), "No tiene conexión a internet", Toast.LENGTH_SHORT).show();
                    }else if(valConexion == 2){
                        tvTextoMensaje.setText("Esta conectado por Datos, debe conectarse a una red WIFI");
                        tvTextoMensaje.setVisibility(View.VISIBLE);
                        Toast.makeText(getBaseContext(), "Esta conectado por Datos, debe conectarse a una red WIFI", Toast.LENGTH_SHORT).show();
                    }

                }



            }

        });

        llactualizarPersonas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int valConexion = 0;
                comprobarConexionInternet objCCI = new comprobarConexionInternet();
                valConexion = objCCI.isNetworkConnected(getBaseContext());
                tvTextoMensaje.setText("");
                tvTextoTransferir.setText("");

                if(valConexion == 1){


                    pgMsj.setMessage("Descargando Base RUV");
                    pgMsj.setCancelable(false);
                    pgMsj.setCanceledOnTouchOutside(false);
                    pgMsj.show();
                    responseUpdateDBVivanto callback = new responseUpdateDBVivanto() {
                        @Override
                        public void resultado(boolean exito) {
                            //pgMsj.hide();
                            if(exito){
                                tvTextoMensaje.setText("Base RUV descargada");
                                tvTextoMensaje.setVisibility(View.VISIBLE);
                                pgMsj.setMessage("Actualizando Base RUV");

                                    responseUpdateDBVivanto callback = new responseUpdateDBVivanto() {
                                       @Override
                                       public void resultado(boolean exito) {
                                           if(exito){
                                               pgMsj.hide();
                                               String[] paramsV = {"BASE"};
                                               final List<emc_version> tmIdVersion = emc_version.find(emc_version.class, "vernombre = ?", paramsV);
                                               tvTextoMensaje.setText("Base Actualizada a la versión " + Integer.parseInt(tmIdVersion.get(0).getVer_version()));

                                           }else{
                                               pgMsj.hide();
                                               tvTextoMensaje.setText("No se pudo actualizar la base");

                                           }

                                       }
                                   };

                                    asynUpdateDBVivanto auFTP = new asynUpdateDBVivanto(getBaseContext(),callback,"ftp.unidadvictimas.gov.co", "encuestadormovil", "Kart3g3na","/Parametricas/",8/*Integer.parseInt(getVersionFTPN())*/);
                                    auFTP.execute();


                            }else{
                                pgMsj.hide();
                                tvTextoMensaje.setText("Error al descargar la base RUV");
                                tvTextoMensaje.setVisibility(View.VISIBLE);
                            }

                        }
                    };

                    asynDownloadBDFTP auFTP = new asynDownloadBDFTP(getBaseContext(),callback,"ftp.unidadvictimas.gov.co", "encuestadormovil", "Kart3g3na");
                    auFTP.execute();


                }else if(valConexion == 0){
                    tvTextoMensaje.setText("No tiene conexión a internet");
                    tvTextoMensaje.setVisibility(View.VISIBLE);
                    Toast.makeText(getBaseContext(), "No tiene conexión a internet", Toast.LENGTH_SHORT).show();
                }else if(valConexion == 2){
                    tvTextoMensaje.setText("Esta conectado por Datos, debe conectarse a una red WIFI");
                    tvTextoMensaje.setVisibility(View.VISIBLE);
                    Toast.makeText(getBaseContext(), "Esta conectado por Datos, debe conectarse a una red WIFI", Toast.LENGTH_SHORT).show();
                }
            }
        });

        llIrMenuPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent irMainActividy = new Intent(v.getContext(), MainActivity.class);
                startActivity(irMainActividy);
                finish();


            }
        });

        llIrIndex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent irServicios = new Intent(v.getContext(), Servicios.class);
                startActivity(irServicios);
                finish();

            }
        });

        llIrCardViewd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent irCardViewd = new Intent(v.getContext(), PersonasCardViewd.class);
                startActivity(irCardViewd);
                finish();

            }
        });


    }

    private void permissionGranted() {
        Toast.makeText(Parametricas.this, getString(R.string.permission_granted), Toast.LENGTH_SHORT).show();
    }

    private void permissionRejected() {
        Toast.makeText(Parametricas.this, getString(R.string.permission_rejected), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //Se obtiene el resultado de los permisos con base en la clave
        switch (requestCode) {
            case WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE:
                //Se verifica si existen resultados y se valida si el permiso fue concedido o no
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Si el permiso está concedido prosigue con el flujo normal
                    //permissionGranted();
                    tvTextoMensaje.setText(R.string.permission_granted);
                    tvTextoMensaje.setVisibility(View.VISIBLE);

                } else {
                    //Si el permiso no fue concedido no se puede continuar
                    //permissionRejected();
                    tvTextoMensaje.setText(R.string.permission_rejected);
                    tvTextoMensaje.setVisibility(View.VISIBLE);
                }
                break;
            case READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE:
                //Se verifica si existen resultados y se valida si el permiso fue concedido o no
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Si el permiso está concedido prosigue con el flujo normal
                    //permissionGranted();
                    tvTextoMensaje.setText(R.string.permission_granted);
                    tvTextoMensaje.setVisibility(View.VISIBLE);
                } else {
                    //Si el permiso no fue concedido no se puede continuar
                    //permissionRejected();
                    tvTextoMensaje.setText(R.string.permission_rejected);
                    tvTextoMensaje.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener  = new BottomNavigationView.OnNavigationItemSelectedListener() {

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
