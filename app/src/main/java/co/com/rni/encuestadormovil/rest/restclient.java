package co.com.rni.encuestadormovil.rest;

/**
 * Created by Javier on 28/08/2015.
 */

import android.content.Context;
import android.widget.ListView;

import com.loopj.android.http.*;

import org.apache.http.Header;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import co.com.rni.encuestadormovil.model.*;
import co.com.rni.encuestadormovil.util.*;


public final class restclient {

    //public static String WS_URL = "http://190.60.70.149:82";
    public static String AppID = "228";
	/*67*/

    public static void login(final emc_usuarios loginUsuario, Context ctx, final responseCallback callback){
        try{

            //Conectarse parabuscar el usuario por WS

            AsyncHttpClient client = new AsyncHttpClient();
            String URL;
            String PATH_WS = "http://190.60.70.149:82/LoginRest/Autentica.svc/LoginPerfil/";
            //String PATH_WS = "http://190.60.70.149:82/LoginRest/Autentica.svc/Login/";
            //String PATH_WS = "http://190.60.70.187/LoginRestProd/Autentica.svc/LoginPerfil/";
            URL = PATH_WS +  AppID + "," + loginUsuario.getNombreusuario() + "," + loginUsuario.getPassword();
            client.setResponseTimeout(20000);
            client.get(URL, null, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    callback.onLoginResponse(false, loginUsuario, "Ocurrio un error en el login");
                    throwable.printStackTrace();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    Document doc;
                    DocumentBuilderFactory dbf;
                    DocumentBuilder db;
                    InputSource is;
                    NodeList nodes;
                    Element element;
                    String cadRespuesta;
                    try {
                        dbf = DocumentBuilderFactory.newInstance();
                        db = dbf.newDocumentBuilder();
                        is = new InputSource();
                        is.setCharacterStream(new StringReader(responseString));
                        doc = db.parse(is);
                        if(doc.getElementsByTagName("Autorizado").item(0).getChildNodes().getLength() > 0){
                            cadRespuesta = doc.getElementsByTagName("IdUsuario").item(0).getChildNodes().item(0).getNodeValue().toString();
                            if(general.isNumeric(cadRespuesta)){
                                if(doc.getElementsByTagName("UPerfiles").item(0).getChildNodes().getLength() == 1 ){
                                    if(doc.getElementsByTagName("dtaPerfil").item(0).getChildNodes().getLength() > 0 ){
                                        loginUsuario.setIdusuario(cadRespuesta);
                                        String cadToken = doc.getElementsByTagName("Token").item(0).getChildNodes().item(0).getNodeValue().toString();
                                        loginUsuario.setTokenusuario(cadToken);
                                        String cadIdPerfil = doc.getElementsByTagName("IdPerfil").item(0).getChildNodes().item(0).getNodeValue().toString();
                                        loginUsuario.setIdPerfil(cadIdPerfil);
                                        String cadNombrePerfil = doc.getElementsByTagName("NombrePerfil").item(0).getChildNodes().item(0).getNodeValue().toString();
                                        loginUsuario.setNombrePerfil(cadNombrePerfil);
                                        callback.onLoginResponse(true, loginUsuario, "Usuario logueado");
                                    }

                                }else
                                {
                                    callback.onLoginResponse(false, loginUsuario, "Tiene mas de un perfil asociado");
                                }


                            }else if (cadRespuesta.trim().equals("El Usuario ya se encuentra logueado..."))
                            {
                                callback.onLoginResponse(false, loginUsuario, "El Usuario ya se encuentra logueado...");
                            }
                            else{
                                callback.onLoginResponse(false, loginUsuario, "Ocurrio un error en el login");
                            }

                        }else{
                            callback.onLoginResponse(false,loginUsuario, "Ocurrio un error en el login");
                        }
                    } catch (Exception e) {
                        callback.onLoginResponse(false, loginUsuario, "Ocurrio un error en el login");
                        e.printStackTrace();
                    }
                }
            });

        }catch (Exception e){
            callback.onLoginResponse(false, loginUsuario, "Ocurrio un error en el login");
        }
    }

    public static void forceLogout(final emc_usuarios loginUsuario, Context ctx, final responseCallback callback){
        try{
            AsyncHttpClient client = new AsyncHttpClient();
            String URL;
            String PATH_WS;
            PATH_WS = "http://190.60.70.149:82/LoginRest/LoginRest/Autentica.svc/LogoutTemp/";
            URL = PATH_WS +  "67," + loginUsuario.getIdusuario();
            client.setResponseTimeout(20000);
            client.get(URL, null, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    callback.onLoginResponse(false,loginUsuario,"Error en el logout");
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    callback.onLoginResponse(true,loginUsuario,"Logout ok");
                }
            });

        }catch (Exception e){
            callback.onLoginResponse(false,loginUsuario,"Error en el logout");
        }
    }


    /*
    public static void logout(final usuario loginUsuario, Context ctx, final responseCallbackLogout callback){
        try{
            AsyncHttpClient client = new AsyncHttpClient();
            String URL;
            String PATH_WS;
            PATH_WS = "/LoginRest/Autentica.svc/Logout/";
            URL = loginUsuario.getWS_URL() + PATH_WS + loginUsuario.getAppID() + "," + loginUsuario.getUserID() + "," + loginUsuario.getToken();
            client.setResponseTimeout(20000);
            client.get(URL, null, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    callback.onResponse(false);
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    Document doc;
                    DocumentBuilderFactory dbf;
                    DocumentBuilder db;
                    InputSource is;
                    NodeList nodes;
                    Element element;
                    String cadRespuesta;
                    try {
                        dbf = DocumentBuilderFactory.newInstance();
                        db = dbf.newDocumentBuilder();
                        is = new InputSource();
                        is.setCharacterStream(new StringReader(responseString));
                        doc = db.parse(is);
                        if(doc.getElementsByTagName("string").item(0).getChildNodes().getLength() > 0){
                            cadRespuesta = doc.getElementsByTagName("string").item(0).getChildNodes().item(0).getNodeValue().toString();
                            if(cadRespuesta.equals("Se ha CERRADO sesión de este usuario....")){
                                callback.onResponse(true);
                            }else{
                                callback.onResponse(false);
                            }

                        }else{
                            callback.onResponse(false);
                        }
                        callback.onResponse(false);
                    } catch (Exception e) {
                        callback.onResponse(false);
                        e.printStackTrace();
                    }
                }
            });

        }catch (Exception e){
            callback.onResponse(false);
        }
    }


    */
}
