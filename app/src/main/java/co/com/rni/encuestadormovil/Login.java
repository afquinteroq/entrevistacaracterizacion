package co.com.rni.encuestadormovil;



import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.os.Environment;


import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import co.com.rni.encuestadormovil.model.*;
import co.com.rni.encuestadormovil.rest.*;
import co.com.rni.encuestadormovil.util.*;




import static co.com.rni.encuestadormovil.util.general.isNetworkConnected;
import static co.com.rni.encuestadormovil.util.general.isOnlineNet;


public class Login extends AppCompatActivity {

    private Button btLogin;
    private Button btLoginSAB;
    private ProgressBar pbEstado;
    private TextView tvEstado;
    private EditText edUsuario;
    private EditText edPassword;
    private Button tvDescargarBase;
    private TextView DescargarVivanto;
    private Button tvActualizarBase;
    private TextView ActualizarVivanto;
    private TextView backupVivanto;
    private TextView tvTextoVersion;
    private boolean loguear = false;
    private ProgressDialog pgMsj;
    private String versionFTPN;
    private Context ctx;
    CheckBox mCbShowPwd;

    private static final int WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 1;
    private static final int READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 1;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1;

    public String getVersionFTPN() {
        return versionFTPN;
    }

    public void setVersionFTPN(String versionFTPN) {
        this.versionFTPN = versionFTPN;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        pbEstado = (ProgressBar) findViewById(R.id.pbEstado);
        pbEstado.setMax(100000);
        tvEstado = (TextView) findViewById(R.id.tvEstado);
        tvEstado.setText("Verifica tablas parametricas");
        //tvEstado.setVisibility(View.GONE);
        edUsuario = (EditText) findViewById(R.id.edUsuario);
        edPassword = (EditText) findViewById(R.id.edPassword);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat fmValida = new SimpleDateFormat("yyyyMMdd");
        final String stFecValida = fmValida.format(c.getTime());
        tvDescargarBase = (Button)findViewById(R.id.tvDescargarBase);
        DescargarVivanto = (TextView) findViewById(R.id.DescargarVivanto);
        tvActualizarBase = (Button) findViewById(R.id.tvActualizarBase);
        ActualizarVivanto = (TextView) findViewById(R.id.ActualizarVivanto);
        backupVivanto = (TextView) findViewById(R.id.backupVivanto);
        tvTextoVersion = (TextView) findViewById(R.id.tvTextoVersion);

        mCbShowPwd = (CheckBox) findViewById(R.id.cbShowPwd);


        pgMsj = new ProgressDialog(this);
        pgMsj.setMessage("Verificando usuario");


        if (ActivityCompat.checkSelfPermission(Login.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //Si el permiso no se encuentra concedido se solicita
            ActivityCompat.requestPermissions(Login.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE);
        } else {
            //Si el permiso esá concedico prosigue con el flujo normal
            permissionGranted();
        }
        //https://stackoverflow.com/questions/38200282/android-os-fileuriexposedexception-file-storage-emulated-0-test-txt-exposed


        if (ActivityCompat.checkSelfPermission(Login.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //Si el permiso no se encuentra concedido se solicita
            ActivityCompat.requestPermissions(Login.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE);
        } else {
            //Si el permiso esá concedico prosigue con el flujo normal
            permissionGranted();
        }


        /*
        if (ActivityCompat.checkSelfPermission(Login.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //Si el permiso no se encuentra concedido se solicita
            ActivityCompat.requestPermissions(Login.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        } else {
            //Si el permiso esá concedico prosigue con el flujo normal
            permissionGranted();
        }*/



        btLogin = (Button) findViewById(R.id.btLogin);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvEstado.setVisibility(View.GONE);
                boolean prueba = false;
                //Verifica usuario en la BD local
                String[] params = {
                        edUsuario.getText().toString().toUpperCase(),
                        edPassword.getText().toString()
                };

                if(params[0].equals("") && params[1].equals("")) {
                    Toast.makeText(getBaseContext(), "Debe ingresar usuario y contraseña", Toast.LENGTH_SHORT).show();

                }else if(params[0].equals("")){
                    Toast.makeText(getBaseContext(), "Debe ingresar usuario", Toast.LENGTH_SHORT).show();
                }else if(params[1].equals("")){
                    Toast.makeText(getBaseContext(), "Debe ingresar contraseña", Toast.LENGTH_SHORT).show();
                }
                else{

                pgMsj.setMessage("Verificando usuario");
                pgMsj.show();

                List<emc_usuarios> tmUsuarios = emc_usuarios.find(emc_usuarios.class, "UPPER(nombreusuario) = ? AND password = ? ", params);
                if (tmUsuarios.size() > 0) {

                    emc_usuarios usuarioLogin = (emc_usuarios) tmUsuarios.get(0);
                    usuarioLogin.setFeclogin(stFecValida);
                    Session.SaveCookie(getApplication(), usuarioLogin);
                    Intent main = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(main);
                    finish();
                    pgMsj.hide();
                    prueba = true;

                }

                if(prueba==false) {
                    String[] paramsV = {"BASE"};
                    final List<emc_version> tmIdVersion = emc_version.find(emc_version.class, "vernombre = ?", paramsV);

                    responseValidateVersions callback = new responseValidateVersions() {

                        @Override
                        public void validacionFinalizada(boolean val, String versionFTP) {
                                int valConexion = 0;
                                setVersionFTPN(versionFTP);
                                comprobarConexionInternet objCCI = new comprobarConexionInternet();
                                valConexion = objCCI.isNetworkConnected(getBaseContext());
                                if(valConexion == 0){
                                    pgMsj.hide();
                                    Toast.makeText(getBaseContext(), "No tiene conexion a internet", Toast.LENGTH_SHORT).show();
                                }/*else if(valConexion == 2){
                                    pgMsj.hide();
                                    Toast.makeText(getBaseContext(), "Esta conectado por Datos, debe conectarse a una red WIFI", Toast.LENGTH_SHORT).show();
                                }*/
                                else if (valConexion ==1 || valConexion == 2){
                                    if(isOnlineNet()) {
                                    if(versionFTP != null) {
                                        validarVersion(val, tmIdVersion, versionFTP);
                                    }else{
                                        pgMsj.hide();
                                        tvTextoVersion.setText("No se pudo validar la versión");
                                        tvTextoVersion.setVisibility(View.VISIBLE);
                                        btLogin.setVisibility(View.VISIBLE);
                                    }
                                    }else{
                                        pgMsj.hide();
                                        Toast.makeText(getBaseContext(), "Sin acceso a internet", Toast.LENGTH_SHORT).show();

                                    }
                                }
                        }
                    };

                    asyncValidateVersions auFTP = new asyncValidateVersions(getBaseContext(), callback, "ftp.unidadvictimas.gov.co", "encuestadormovil", "Kart3g3na");
                    auFTP.execute();
                }
                }
            }
        });

        mCbShowPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // checkbox status is changed from uncheck to checked.
                if (!isChecked) {
                    // show password
                    edPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    edPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        btLoginSAB = (Button) findViewById(R.id.btLoginSAB);
        btLoginSAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvEstado.setVisibility(View.GONE);
                boolean prueba = false;
                //Verifica usuario en la BD local
                String[] params = {
                        edUsuario.getText().toString().toUpperCase(),
                        edPassword.getText().toString()

                };

                if(params[0].equals("") && params[1].equals("")) {
                    Toast.makeText(getBaseContext(), "Debe ingresar usuario y contraseña", Toast.LENGTH_SHORT).show();

                }else if(params[0].equals("")){
                    Toast.makeText(getBaseContext(), "Debe ingresar usuario", Toast.LENGTH_SHORT).show();
                }else if(params[1].equals("")){
                    Toast.makeText(getBaseContext(), "Debe ingresar contraseña", Toast.LENGTH_SHORT).show();
                }
                else{

                    pgMsj.setMessage("Verificando usuario");
                    pgMsj.show();

                    List<emc_usuarios> tmUsuarios = emc_usuarios.find(emc_usuarios.class, "UPPER(nombreusuario) = ? AND password = ? ", params);
                    if (tmUsuarios.size() > 0) {

                        emc_usuarios usuarioLogin = (emc_usuarios) tmUsuarios.get(0);
                        usuarioLogin.setFeclogin(stFecValida);
                        Session.SaveCookie(getApplication(), usuarioLogin);
                        Intent main = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(main);
                        finish();
                        pgMsj.hide();
                        prueba = true;

                    }

                    if(prueba==false) {

                                int valConexion = 0;
                                //comprobarConexionInternet objCCI = new comprobarConexionInternet();
                                //valConexion = objCCI.isNetworkConnected(getBaseContext());
                                valConexion = isNetworkConnected(getBaseContext());
                                if(valConexion == 0){
                                    pgMsj.hide();
                                    Toast.makeText(getBaseContext(), "No tiene conexion a internet", Toast.LENGTH_SHORT).show();
                                }/*else if(valConexion == 2){
                                    pgMsj.hide();
                                    Toast.makeText(getBaseContext(), "Esta conectado por Datos, debe conectarse a una red WIFI", Toast.LENGTH_SHORT).show();
                                }*/
                                else if (valConexion ==1 || valConexion == 2){
                                    //validarVersion(true, 1, 1);
                                    //Busca usuario por WS
                                    //validar conexion a internet
                                    Calendar c = Calendar.getInstance();
                                    SimpleDateFormat fmValida = new SimpleDateFormat("yyyyMMdd");
                                    final String sttFecValida = fmValida.format(c.getTime());
                                        responseCallback callbacklogin = new responseCallback() {
                                            @Override
                                            public void onLoginResponse(boolean isLoged, emc_usuarios usuarioLogin, String mensaje) {
                                                if (isLoged) {
                                                    Calendar c = Calendar.getInstance();
                                                    c.add(Calendar.DATE, 30);
                                                    SimpleDateFormat fmValida = new SimpleDateFormat("yyyyMMdd");
                                                    String stFecToken = fmValida.format(c.getTime());
                                                    usuarioLogin.setFeclogin(sttFecValida);
                                                    usuarioLogin.setFecvalidatoken(stFecToken);
                                                    usuarioLogin.save();
                                                    Session.SaveCookie(getApplication(), usuarioLogin);
                                                    Intent main = new Intent(getBaseContext(), MainActivity.class);
                                                    startActivity(main);
                                                    finish();
                                                    pgMsj.hide();
                                                } else if (mensaje.equals("Tiene mas de un perfil asociado")) {
                                                    Toast.makeText(getBaseContext(), mensaje, Toast.LENGTH_SHORT).show();
                                                    pgMsj.hide();
                                                } else if (mensaje.equals("El Usuario ya se encuentra logueado...")) {
                                                    Toast.makeText(getBaseContext(), mensaje, Toast.LENGTH_SHORT).show();
                                                    pgMsj.hide();
                                                } else {
                                                    Toast.makeText(getBaseContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                                                    pgMsj.hide();
                                                }
                                            }
                                        };
                                        emc_usuarios temUsr = new emc_usuarios();
                                        temUsr.setNombreusuario(edUsuario.getText().toString());
                                        temUsr.setPassword(edPassword.getText().toString());
                                        restclient.login(temUsr, getBaseContext(), callbacklogin);
                                }
                    }
                }
            }
        });

        DescargarVivanto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int valConexion = 0;
                comprobarConexionInternet objCCI = new comprobarConexionInternet();
                valConexion = objCCI.isNetworkConnected(getBaseContext());

                if(valConexion == 1){

                    pgMsj.setMessage("Descargando base vivanto");
                    pgMsj.show();
                    responseUpdateDBVivanto callback = new responseUpdateDBVivanto() {
                        @Override
                        public void resultado(boolean exito) {
                            pgMsj.hide();
                            if(exito){
                                tvTextoVersion.setVisibility(View.VISIBLE);
                                tvTextoVersion.setText("Base descargada");
                            }else{
                                tvTextoVersion.setVisibility(View.VISIBLE);
                                tvTextoVersion.setText("Error al descargar la base");
                            }
                        }
                    };

                    asyncDownloadDBVivantoFTP auFTP = new asyncDownloadDBVivantoFTP(getBaseContext(),callback,"ftp.unidadvictimas.gov.co", "encuestadormovil", "Kart3g3na");
                    auFTP.execute();

                }else if(valConexion == 0){
                    Toast.makeText(getBaseContext(), "No tiene conexion a internet", Toast.LENGTH_SHORT).show();
                }else if(valConexion == 2){
                    Toast.makeText(getBaseContext(), "Esta conectado por Datos, debe conectarse a una red WIFI", Toast.LENGTH_SHORT).show();
                }

            }
        });


        tvDescargarBase.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){

                    int valConexion = 0;
                    comprobarConexionInternet objCCI = new comprobarConexionInternet();
                    valConexion = objCCI.isNetworkConnected(getBaseContext());

                    if(valConexion == 1){

                        tvTextoVersion.setText("");
                        pgMsj.setMessage("Descargando base RUV");
                        pgMsj.show();
                        responseUpdateDBVivanto callback = new responseUpdateDBVivanto() {
                            @Override
                            public void resultado(boolean exito) {
                                pgMsj.hide();
                                if(exito){
                                    tvTextoVersion.setText("Base descargada");
                                    btLogin.setVisibility(View.GONE);
                                    btLoginSAB.setVisibility(View.GONE);
                                    tvDescargarBase.setVisibility(View.GONE);
                                    tvActualizarBase.setVisibility(View.VISIBLE);
                                }else{
                                    tvTextoVersion.setText("Error al descargar la base");
                                    btLogin.setVisibility(View.GONE);
                                    btLoginSAB.setVisibility(View.VISIBLE);
                                    tvDescargarBase.setVisibility(View.VISIBLE);
                                }

                            }
                        };

                        asynDownloadBDFTP auFTP = new asynDownloadBDFTP(getBaseContext(),callback,"ftp.unidadvictimas.gov.co", "encuestadormovil", "Kart3g3na");
                        auFTP.execute();

                        //tvDescargarBase.setVisibility(View.GONE);

                    }else if(valConexion == 0){
                        Toast.makeText(getBaseContext(), "No tiene conexion a internet", Toast.LENGTH_SHORT).show();
                    }else if(valConexion == 2){
                        Toast.makeText(getBaseContext(), "Esta conectado por Datos, debe conectarse a una red WIFI", Toast.LENGTH_SHORT).show();
                    }

            }
        }
);

        ActualizarVivanto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pgMsj.setMessage("Actualizando base VIVANTO");
                pgMsj.show();
                responseUpdateDBVivanto callback = new responseUpdateDBVivanto() {
                    @Override
                    public void resultado(boolean exito) {
                        if(exito == true){
                            pgMsj.hide();
                            tvTextoVersion.setVisibility(View.VISIBLE);
                            tvTextoVersion.setText("Base vivanto Actualizada");
                        }else{
                            tvTextoVersion.setVisibility(View.VISIBLE);
                            tvTextoVersion.setText("No se pudo actualziar la base");
                            pgMsj.hide();
                        }

                    }
                };

                asyncUpdateDBVivanto auFTP = new asyncUpdateDBVivanto(getBaseContext(),callback,"ftp.unidadvictimas.gov.co", "encuestadormovil", "Kart3g3na","/Parametricas/");
                auFTP.execute();

            }
        });

        backupVivanto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pgMsj.setMessage("Generando backup de la apilcación EncuestadorMovil");
                pgMsj.show();
                responseBackupBDVivanto callBack = new responseBackupBDVivanto() {
                    @Override
                    public void resultado(boolean exito) {
                        pgMsj.hide();
                        if(exito){
                            tvEstado.setText("Backup generado corretamente");
                            tvEstado.setVisibility(View.VISIBLE);
                        }else{
                            tvEstado.setText("Error al generar el backup");
                            tvEstado.setVisibility(View.VISIBLE);
                        }

                    }
                };
                ansyncBackupDBVivanto auBDBV = new ansyncBackupDBVivanto(getBaseContext(),callBack);
                auBDBV.execute();
            }
        });


        tvActualizarBase.setOnClickListener(new View.OnClickListener(){


           @Override
           public void onClick(View v){

               tvTextoVersion.setText("");
               pgMsj.setMessage("Actualizando base RUV");
               pgMsj.show();
               responseUpdateDBVivanto callback = new responseUpdateDBVivanto() {
                   @Override
                   public void resultado(boolean exito) {
                       if(exito){
                           pgMsj.hide();
                           String[] paramsV = {"BASE"};
                           final List<emc_version> tmIdVersion = emc_version.find(emc_version.class, "vernombre = ?", paramsV);
                           tvTextoVersion.setText("Base Actualizada a la versión " + Integer.parseInt(tmIdVersion.get(0).getVer_version()));
                           tvDescargarBase.setVisibility(View.GONE);
                           tvActualizarBase.setVisibility(View.GONE);
                           btLogin.setVisibility(View.VISIBLE);
                       }else{
                           pgMsj.hide();
                           tvTextoVersion.setText("No se pudo actualizar la base");
                           tvDescargarBase.setVisibility(View.GONE);
                           tvActualizarBase.setVisibility(View.VISIBLE);
                           btLogin.setVisibility(View.GONE);
                       }

                   }
               };

               asynUpdateDBVivanto auFTP = new asynUpdateDBVivanto(getBaseContext(),callback,"ftp.unidadvictimas.gov.co", "encuestadormovil", "Kart3g3na","/Parametricas/",Integer.parseInt(getVersionFTPN()));
               auFTP.execute();
           }
       }
        );


            responseParametros callback = new responseParametros() {
                @Override
                public void actualizaParametros(String mensaje, Integer registros) {
                    pbEstado.setProgress(registros);
                    tvEstado.setText(mensaje);
                    if (registros == -100) {
                        //pgMsj.dismiss();
                        // pgMsj.hide();
                    }
                }

                @Override
                public void cargaFinalizada(boolean exito) {
                    if (exito) {
                        pbEstado.setVisibility(View.GONE);
                        tvEstado.setVisibility(View.GONE);
                        btLogin.setVisibility(View.VISIBLE);
                    } else {
                        pbEstado.setVisibility(View.GONE);
                        tvEstado.setVisibility(View.VISIBLE);
                        tvEstado.setText("Ocurrio un error en la carga");
                        btLogin.setVisibility(View.GONE);
                    }
                    //pgMsj.dismiss();
                    //pgMsj.hide();
                    //exportDatabse("vivanto.db");
                }
            };


            pbEstado.setVisibility(View.VISIBLE);
            asyncPrepararParametros asPreparar = new asyncPrepararParametros(this, callback);
            asPreparar.execute();


            responsePrepararBD preparaCall = new responsePrepararBD() {
                @Override
                public void resultado(boolean exito, Integer numVictimas) {
                    pgMsj.hide();
                }
            };
            asyncPrepararBD asPrepara = new asyncPrepararBD(this, preparaCall);
            asPrepara.execute();
            pgMsj.setMessage("Preparando base de datos");
            pgMsj.show();
            pgMsj.setCancelable(false);
            pgMsj.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    pgMsj.show();
                }
            });

            TextView tvVersion = (TextView) findViewById(R.id.tvVersion);

        try {
                tvVersion.setText("Versión " + getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

        }


    public void validarVersion(boolean valv,List<emc_version> tmIdVersion, String versionFTP)
    {


        Calendar c = Calendar.getInstance();
        SimpleDateFormat fmValida = new SimpleDateFormat("yyyyMMdd");
        final String sttFecValida = fmValida.format(c.getTime());

        if (valv != true) {

            tvTextoVersion.setText("Base de datos no esta actualizada, la versión de la base de la aplicación es " + tmIdVersion.get(0).getVer_version().toString()+" "+
                    ",la última versión disponbile es " + versionFTP );
            tvTextoVersion.setVisibility(View.VISIBLE);
            pgMsj.hide();
            btLogin.setVisibility(View.GONE);
            tvDescargarBase.setVisibility(View.VISIBLE);

        } else {

                //Busca usuario por WS
                //validar conexion a internet
                comprobarConexionInternet objCCI = new comprobarConexionInternet();
                int valConexion = 0;
                valConexion = objCCI.isNetworkConnected(getBaseContext());
                if ( valConexion == 1) {
                    responseCallback callbacklogin = new responseCallback() {
                        @Override
                        public void onLoginResponse(boolean isLoged, emc_usuarios usuarioLogin, String mensaje) {
                            if (isLoged) {
                                Calendar c = Calendar.getInstance();
                                c.add(Calendar.DATE, 30);
                                SimpleDateFormat fmValida = new SimpleDateFormat("yyyyMMdd");
                                String stFecToken = fmValida.format(c.getTime());
                                usuarioLogin.setFeclogin(sttFecValida);
                                usuarioLogin.setFecvalidatoken(stFecToken);
                                usuarioLogin.save();
                                Session.SaveCookie(getApplication(), usuarioLogin);
                                Intent main = new Intent(getBaseContext(), MainActivity.class);
                                startActivity(main);
                                finish();
                                pgMsj.hide();
                            } else if (mensaje.equals("Tiene mas de un perfil asociado")) {
                                Toast.makeText(getBaseContext(), mensaje, Toast.LENGTH_SHORT).show();
                                pgMsj.hide();
                            } else if (mensaje.equals("El Usuario ya se encuentra logueado...")) {
                                Toast.makeText(getBaseContext(), mensaje, Toast.LENGTH_SHORT).show();
                                pgMsj.hide();
                            } else {
                                Toast.makeText(getBaseContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                                pgMsj.hide();
                            }
                        }
                    };
                    emc_usuarios temUsr = new emc_usuarios();
                    temUsr.setNombreusuario(edUsuario.getText().toString());
                    temUsr.setPassword(edPassword.getText().toString());
                    restclient.login(temUsr, getBaseContext(), callbacklogin);

                }else if(valConexion == 2){
                    Toast.makeText(getBaseContext(), "Esta conectado por Datos, debe conectarse a una red WIFI", Toast.LENGTH_SHORT).show();
                }
                else if (valConexion == 0){
                    Toast.makeText(getBaseContext(), "No tiene conexión a internet", Toast.LENGTH_LONG).show();
                    pgMsj.hide();
                }
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    public void exportDatabse(String databaseName) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//"+getPackageName()+"//databases//"+databaseName+"";
                String backupDBPath = "backupname.db";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
        } catch (Exception e) {

        }
    }

    private void permissionGranted() {
        Toast.makeText(Login.this, getString(R.string.permission_granted), Toast.LENGTH_SHORT).show();
    }

    private void permissionRejected() {
        Toast.makeText(Login.this, getString(R.string.permission_rejected), Toast.LENGTH_SHORT).show();
    }

    // Detectar cuando se presiona el botón de retroceso
    public void onBackPressed() {
        finish();
    }

}
