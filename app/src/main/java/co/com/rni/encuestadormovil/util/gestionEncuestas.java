package co.com.rni.encuestadormovil.util;




import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


import co.com.rni.encuestadormovil.model.*;



/**
 * Created by javierperez on 13/02/16.
 */
public final class gestionEncuestas {

    public static List<emc_preguntas_persona> SP_BUSCAR_SIGUIENTE_PREGUNTA(String pHOG_CODIGO,Integer pID_TEMA,Integer pINS_IDINSTRUMENTO,Integer pID_PREGUNTA) {
        Integer pCont_Preg;
        Integer MAX_pCont_Pre = 0;
        String[] pars = {pHOG_CODIGO};

        List<emc_encuestas_terminadas> lsEncter = emc_encuestas_terminadas.find(emc_encuestas_terminadas.class, "HOGCODIGO = ?", pars);
        Integer finCapitulo = lsEncter.size();

        if (finCapitulo > 0) {
            pCont_Preg = pID_PREGUNTA;
        } else {
            if (pID_PREGUNTA == 1) {

                String sQ = "SELECT COALESCE(MIN(CAST(PR.IXPORDEN AS INTEGER)),1) FROM EMCPREGUNTASDERIVADAS TD " +
                        " JOIN EMCPREGUNTASINSTRUMENTO PR " +
                        " ON PR.PREIDPREGUNTA = TD.PREIDPREGUNTA " +
                        " AND TD.GUARDADO = 0 AND TD.HOGCODIGO = '" + pHOG_CODIGO + "' " +
                        " AND PR.TEMIDTEMA = " + pID_TEMA;
                pCont_Preg = execCalculos(sQ);
                if (pCont_Preg == null)
                    pCont_Preg = 1;

                //se carga el orden de la ultima pregunta del tema
                String pQ = "SELECT MAX(CAST(PR.IXPORDEN AS INTEGER)) FROM EMCPREGUNTASINSTRUMENTO PR " +
                        " WHERE INSIDINSTRUMENTO = " + pINS_IDINSTRUMENTO +
                        " AND PR.TEMIDTEMA = " + pID_TEMA + " AND PREACTIVA='SI'";
                MAX_pCont_Pre = execCalculos(pQ);
            } else {

                String pConQ = "SELECT COALESCE(MIN(CAST(PR.IXPORDEN AS INTEGER)),0) FROM EMCPREGUNTASDERIVADAS TD " +
                        "JOIN EMCPREGUNTASINSTRUMENTO PR " +
                        "ON PR.PREIDPREGUNTA = TD.PREIDPREGUNTA " +
                        "AND TD.GUARDADO = 0 AND TD.HOGCODIGO = '" + pHOG_CODIGO + "' " +
                        "AND PR.TEMIDTEMA = " + pID_TEMA;
                pCont_Preg = execCalculos(pConQ);
                if (pCont_Preg == null)
                    pCont_Preg = 0;

                //se carga la maxima pregunta en preguntas derivadas
                String MAXCq = "SELECT MAX(CAST(IXPORDEN AS INTEGER)) FROM ( " +
                        "SELECT u.IXPORDEN " +
                        "FROM EMCPREGUNTASDERIVADAS t  " +
                        "LEFT JOIN EMCPREGUNTASINSTRUMENTO u ON (t.PREIDPREGUNTA = u.PREIDPREGUNTA)  " +
                        "WHERE HOGCODIGO = '" + pHOG_CODIGO + "' " +
                        " AND T.TEMIDTEMA = " + pID_TEMA +
                        " AND T.INSIDINSTRUMENTO = " + pINS_IDINSTRUMENTO +
                        " AND PREACTIVA = 'SI' AND T.GUARDADO=0 " +
                        "ORDER BY CAST(t.PREIDPREGUNTA AS INTEGER))";


                MAX_pCont_Pre = execCalculos(MAXCq);

            }
        }

        do {
            String[] parPreg = {pCont_Preg.toString(), pINS_IDINSTRUMENTO.toString(), pID_TEMA.toString()};
            List<emc_preguntas_instrumento> lsInstrumentoPreg = emc_preguntas_instrumento.find(emc_preguntas_instrumento.class,
                    " IXPORDEN = ?" +
                            "AND PREACTIVA = 'SI' " +
                            "AND INSIDINSTRUMENTO = ? " +
                            "AND TEMIDTEMA = ? ", parPreg);
            List<emc_miembros_hogar> lsMiembrosHogar = emc_miembros_hogar.find(emc_miembros_hogar.class, "HOGCODIGO = ?", pHOG_CODIGO);
            Integer pTotalValidaciones = 0;
            Integer pConteoValidaciones = 0;

            for (int conM = 0; conM < lsMiembrosHogar.size(); conM++) {
                emc_miembros_hogar miembro = lsMiembrosHogar.get(conM);
                for (int conP = 0; conP < lsInstrumentoPreg.size(); conP++) {
                    emc_preguntas_instrumento pregunta = lsInstrumentoPreg.get(conP);

                    String pre = pregunta.getPre_idpregunta();

                    Integer res = FN_COMPROBARVALIDACIONES(miembro.getPer_idpersona(), pregunta.getPre_idpregunta(), pHOG_CODIGO, pINS_IDINSTRUMENTO.toString());
                    pTotalValidaciones += res;
                    pConteoValidaciones++;
                }
            }

            if (pTotalValidaciones == 0 && pConteoValidaciones > 0) {
                String[] parUpdate = {pHOG_CODIGO, pCont_Preg.toString(), pID_TEMA.toString(), pINS_IDINSTRUMENTO.toString()};
                emc_preguntas_derivadas.executeQuery(
                        "UPDATE EMCPREGUNTASDERIVADAS " +
                                "SET GUARDADO = '1' " +
                                "WHERE HOGCODIGO = ? " +
                                "AND PREIDPREGUNTA = (SELECT PREIDPREGUNTA " +
                                "                     FROM EMCPREGUNTASINSTRUMENTO t " +
                                "                     WHERE T.IXPORDEN = ? " +
                                "                     AND T.TEMIDTEMA = ? " +
                                "                     AND T.PREACTIVA = 'SI' " +
                                "                     AND T.INSIDINSTRUMENTO = ?)", parUpdate);

                if (pID_PREGUNTA == 1) {
                    pCont_Preg++;
                } else {

                    String pContQ = "SELECT MIN(CAST(IXPORDEN AS INTEGER)) FROM " +
                            "(  SELECT T.* , U.IXPORDEN " +
                            " FROM EMCPREGUNTASDERIVADAS T " +
                            " LEFT JOIN EMCPREGUNTASINSTRUMENTO U " +
                            " ON T.PREIDPREGUNTA = U.PREIDPREGUNTA " +
                            " WHERE HOGCODIGO = '" + pHOG_CODIGO + "' " +
                            " AND T.TEMIDTEMA = " + pID_TEMA +
                            " AND T.INSIDINSTRUMENTO = " + pINS_IDINSTRUMENTO +
                            " AND PREACTIVA = 'SI' AND GUARDADO=0 )" +
                            " WHERE CAST(IXPORDEN AS INTEGER)  > " + pCont_Preg.toString();

                    //aqui genera error
                    pCont_Preg = execCalculos(pContQ);

                    if(pCont_Preg != null)
                    {
                        if (pCont_Preg > MAX_pCont_Pre) break;
                    }
                    //código prueba
                    else if (pCont_Preg == null)
                    {
                        pCont_Preg = MAX_pCont_Pre;
                    }

                }
            } else {
                break;
            }
        } while (pCont_Preg < MAX_pCont_Pre);

        SP_SET_ELIMINAR_RESP_ENCUESTA(pHOG_CODIGO, pINS_IDINSTRUMENTO, pID_TEMA);

        //borra las respuestas de las personas que no validan
        String[] parPreIns = {pCont_Preg.toString(), pINS_IDINSTRUMENTO.toString(), pID_TEMA.toString()};
        List<emc_preguntas_instrumento> lsPregIns = emc_preguntas_instrumento.find(emc_preguntas_instrumento.class,
                "IXPORDEN = ? " +
                        "AND PREACTIVA='SI' " +
                        "AND INSIDINSTRUMENTO = ? " +
                        "AND TEMIDTEMA = ?", parPreIns);

        List<emc_miembros_hogar> lsMiembrosHog = emc_miembros_hogar.find(emc_miembros_hogar.class, "HOGCODIGO = ?", pHOG_CODIGO.toString());
        for (int conM = 0; conM < lsMiembrosHog.size(); conM++) {
            emc_miembros_hogar miembro = lsMiembrosHog.get(conM);
            for (int conP = 0; conP < lsPregIns.size(); conP++) {
                emc_preguntas_instrumento pregInstrumento = lsPregIns.get(conP);



                Integer validacion_persona = FN_COMPROBARVALIDACIONES(miembro.getPer_idpersona(), pregInstrumento.getPre_idpregunta(), pHOG_CODIGO.toString(), pINS_IDINSTRUMENTO.toString());
                if (validacion_persona == 0) {
                    String[] parRes = {pHOG_CODIGO, miembro.getPer_idpersona(), pregInstrumento.getPre_idpregunta()};
                    emc_respuestas_encuesta.deleteAll(emc_respuestas_encuesta.class,
                            "HOGCODIGO = ? " +
                                    "AND PERIDPERSONA = ? " +
                                    "AND RESIDRESPUESTA IN (" +
                                    "SELECT r.RESIDRESPUESTA FROM EMCRESPUESTAS r " +
                                    "WHERE r.PREIDPREGUNTA = ? " +
                                    "AND RESACTIVA = 'SI')", parRes);
                }
            }
        }

        //devuelve siguiente pregunta
        String[] parPreInsR = {pCont_Preg.toString(), pINS_IDINSTRUMENTO.toString(), pID_TEMA.toString()};
        List<emc_preguntas_instrumento> lsPregInsR = emc_preguntas_instrumento.find(emc_preguntas_instrumento.class,
                "IXPORDEN = ? " +
                        "AND PREACTIVA='SI' " +
                        "AND INSIDINSTRUMENTO = ? " +
                        "AND TEMIDTEMA = ?", parPreInsR);

        List<emc_preguntas_persona> lsPreguntasPersona = new ArrayList<>();
        for (int conP = 0; conP < lsPregInsR.size(); conP++) {
            emc_preguntas_instrumento tmPregIns = lsPregInsR.get(conP);

            if (tmPregIns.getPre_tipopregunta().equals("IN")) {
                //case when  GIC_N_CARACTERIZACION.FN_GET_MAXPRENTASXTEMA(pID_TEMA) = f.PRE_IDPREGUNTA then 'U' else case when f.PRE_IDPREGUNTA = pID_PREGUNTA then 'P' else 'I' end end ORDENPRIORIDAD,

                for(int Cmiem = 0; Cmiem < lsMiembrosHog.size(); Cmiem++){
                    emc_miembros_hogar tmMiembro = lsMiembrosHog.get(Cmiem);
                    if(FN_COMPROBARVALIDACIONES(tmMiembro.getPer_idpersona(),tmPregIns.getPre_idpregunta(), pHOG_CODIGO, pINS_IDINSTRUMENTO.toString()).toString().equals("1")){
                        emc_preguntas_persona tmPreguntaPersona = new emc_preguntas_persona();
                        tmPreguntaPersona.setPer_idpersona(tmMiembro.getPer_idpersona());
                        tmPreguntaPersona.setPre_idpregunta(tmPregIns.getPre_idpregunta());
                        tmPreguntaPersona.setPre_tipopregunta(tmPregIns.getPre_tipopregunta());
                        tmPreguntaPersona.setPre_tipocampo(tmPregIns.getPre_tipocampo());
                        tmPreguntaPersona.setHog_codigo(pHOG_CODIGO);
                        tmPreguntaPersona.setIxp_orden(tmPregIns.getIxp_orden());
                        tmPreguntaPersona.setValidacion_persona("1");
                        //tmPreguntaPersona.setOrdenprioridad();
                        lsPreguntasPersona.add(tmPreguntaPersona);
                    }
                }
            }else {
                emc_preguntas_persona tmPreguntaPersona = new emc_preguntas_persona();
                tmPreguntaPersona.setPre_idpregunta(tmPregIns.getPre_idpregunta());
                tmPreguntaPersona.setPre_tipopregunta(tmPregIns.getPre_tipopregunta());
                tmPreguntaPersona.setPre_tipocampo(tmPregIns.getPre_tipocampo());
                tmPreguntaPersona.setHog_codigo(pHOG_CODIGO);
                tmPreguntaPersona.setIxp_orden(tmPregIns.getIxp_orden());
                lsPreguntasPersona.add(tmPreguntaPersona);
            }
        }

        String[] parUpd = {pCont_Preg.toString(), pINS_IDINSTRUMENTO.toString(), pID_TEMA.toString(), pHOG_CODIGO, pINS_IDINSTRUMENTO.toString(), pID_TEMA.toString()};
        emc_preguntas_derivadas.executeQuery("UPDATE EMCPREGUNTASDERIVADAS " +
                "SET CONTADOR = 0 " +
                "WHERE PREIDPREGUNTA = ( " +
                "SELECT IP.PREIDPREGUNTA   " +
                "FROM EMCPREGUNTASINSTRUMENTO IP  " +
                "WHERE IP.IXPORDEN = ? " +
                "AND IP.PREACTIVA = 'SI' " +
                "AND IP.INSIDINSTRUMENTO = ? " +
                "AND IP.TEMIDTEMA = ?) " +
                "AND HOGCODIGO = ? " +
                "AND INSIDINSTRUMENTO = ? " +
                "AND TEMIDTEMA = ?", parUpd);

        return lsPreguntasPersona;
    }


    /*public static List<emc_preguntas_persona> SP_BUSCAR_ANTERIOR_PREGUNTA( String pHOG_CODIGO,Integer pID_TEMA,Integer pINS_IDINSTRUMENTO,Integer pID_PREGUNTA){

        List<emc_preguntas_persona> lsPreguntasPersona = new ArrayList<>();
        String pCont_Preg;
        String pID_PREGUNTA_act;

        if (pID_PREGUNTA == 0 )
        {
            String siPregunta0 = "SELECT MAX(IXPORDEN)+1  pCont_Preg FROM EMCRESPUESTASENCUESTA G" +
                    "        JOIN EMCRESPUESTAS A" +
                    "        ON a.RESIDRESPUESTA=G.RESIDRESPUESTA" +
                    "        LEFT JOIN  EMCPREGUNTASINSTRUMENTO C" +
                    "        ON A.PREIDPREGUNTA=C.PREIDPREGUNTA" +
                    "        WHERE TEMIDTEMA= "+ pID_TEMA +
                    "        AND G.HOGCODIGO= '" + pHOG_CODIGO +"'"+
                    "        AND G.INSIDINSTRUMENTO= "+pINS_IDINSTRUMENTO+";";



        }else
        {
            String sino = "SELECT PR.IXPORDEN  pCont_Preg" +
                    "        FROM  EMCPREGUNTASINSTRUMENTO PR" +
                    "        WHERE PR.PREIDPREGUNTA= "+pID_PREGUNTA+
                    "        AND PR.TEM_IDTEMA= "+pID_TEMA+";";
        }


        return lsPreguntasPersona;

    }*/


    public static Integer FN_GET_MAXPRENTASXTEMA(List<emc_preguntas_instrumento> lsPreIns) {
        //DEVUELVE EL MAXIMO PRE_IDPREGUNTA DE UN TEMA
        Integer Result = 0;

        for (int conI = 0; conI < lsPreIns.size(); conI++) {
            emc_preguntas_instrumento tmPreIns = lsPreIns.get(conI);
            if (Integer.valueOf(tmPreIns.getPre_idpregunta()) > Result)
                Result = Integer.valueOf(tmPreIns.getPre_idpregunta());
        }
        return Result;
    }

    public static void SP_SET_ELIMINAR_RESP_ENCUESTA(String pHOG_CODIGO, Integer pID_TEMA, Integer pINS_IDINSTRUMENTO) {
        String[] parDelete = {pINS_IDINSTRUMENTO.toString(), pID_TEMA.toString(), pHOG_CODIGO.toString(), pID_TEMA.toString(), pHOG_CODIGO.toString(), pINS_IDINSTRUMENTO.toString()};
        emc_respuestas_encuesta.deleteAll(emc_respuestas_encuesta.class,
                "RESIDRESPUESTA IN ( " +
                        "SELECT DISTINCT B.RESIDRESPUESTA FROM (" +
                        "SELECT * FROM EMCPREGUNTASINSTRUMENTO U " +
                        "WHERE U.INSIDINSTRUMENTO = ? " +
                        "AND U.TEMIDTEMA = ? " +
                        "AND U.PREIDPREGUNTA NOT IN (" +
                        "SELECT DISTINCT t.PREIDPREGUNTA FROM EMCPREGUNTASDERIVADAS t " +
                        "WHERE t.HOGCODIGO = ? AND t.TEMIDTEMA = ?) " +
                        "AND U.IXPORDEN > 1) A " +
                        "LEFT JOIN EMCRESPUESTAS B " +
                        "ON A.PREIDPREGUNTA = B.PREIDPREGUNTA) " +
                        "AND HOGCODIGO = ? " +
                        "AND INSIDINSTRUMENTO = ?", parDelete);
    }

    public static Integer FN_VALIDARPERSONA(String VALOR, String pVAL_IDVALIDADOR) {
        Integer Result = 0;
        String Val;
        Integer Existe;

        List<emc_instrumento_validador> lsValInstrumento = emc_instrumento_validador.find(emc_instrumento_validador.class,
                "VALIDVALIDADOR = ?", pVAL_IDVALIDADOR);
        if (lsValInstrumento.size() > 0) {
            emc_instrumento_validador tmValIns = lsValInstrumento.get(0);
            Val = tmValIns.getPre_validador();


            if (Val.equals("NU")) {
                Integer valEv = Integer.valueOf(VALOR);
                Integer valMin = Integer.valueOf(tmValIns.getPre_validador_min());
                Integer valMax = Integer.valueOf(tmValIns.getPre_validador_max());

                if (valEv >= valMin && valEv <= valMax) {
                    Result = 1;
                } else {
                    Result = 0;
                }
            } else if (Val.equals("FE")) {
                Date feEv = Date.valueOf(VALOR);
                Date feMin = Date.valueOf(tmValIns.getPre_validador_min());
                Date feMax = Date.valueOf(tmValIns.getPre_validador_max());

                if ((feEv.after(feMin) || feEv.equals(feMin)) && (feEv.before(feMax) || feEv.equals(feMax))) {
                    Result = 1;
                } else {
                    Result = 0;
                }

            } else if (Val.equals("TE") || Val.equals("IN")) {
                if (tmValIns.getPre_validador_min().equals(VALOR)) {
                    Result = 1;
                } else {
                    Result = 0;
                }
            }
        }
        return Result;
    }


    public static void SP_BORRARPREGUNTASANTERIORES(String pHOG_CODIGO, String  pPER_IDPERSONA,String  pINS_IDINSTRUMENTO,String pID_PREGUNTA)
    {
        String[] parDeletePD = {pHOG_CODIGO, pPER_IDPERSONA.toString(), pINS_IDINSTRUMENTO.toString(), pID_PREGUNTA.toString()};
        emc_preguntas_derivadas.deleteAll(emc_preguntas_derivadas.class,
                "HOGCODIGO = ? AND PERIDPERSONA= ?" +
                        "  AND INSIDINSTRUMENTO= ? AND PREIDPREGUNTA= ?", parDeletePD);


    }

    public static Integer FN_COMPROBARVALIDACIONES(String pPER_IDPERSONA, String pPRE_IDPREGUNTA, String pHOG_CODIGO, String pINS_IDINSTRUMENTO) {
        Integer EXISTE;
        Integer CONT = 0;
        Integer TOTAL = 0;
        Integer IXP_ORDEN = 0;
        Integer TIENE_VAL;
        String valor = "";
        String Val_Diferenciado;
        String VAL_PREG_GENERAL;
        String Val_Diferenciado_Res = "";
        String Val_Encontrados;
        Integer Inic_cadena;
        Integer Fin_cadena;
        Integer Pos_cadena;
        String Val_cadena;
        String CONDICION;
        String RESULTADO;
        Integer BUSRESPUESTAS;
        Integer Result = 0;
        int Val_DiferenciadoLeng;

        // BUSCAR EL ORDEN DE LA PREGUNTA
        String[] params = {pPRE_IDPREGUNTA.toString(), pINS_IDINSTRUMENTO.toString()};
        List<emc_preguntas_instrumento> lsPreguntaIns = emc_preguntas_instrumento.find(emc_preguntas_instrumento.class,
                "PREIDPREGUNTA = ? AND INSIDINSTRUMENTO = ?", params);
        emc_preguntas_instrumento preguntaInstrumento = lsPreguntaIns.get(0);


        IXP_ORDEN = Integer.valueOf(preguntaInstrumento.getIxp_orden());
        //VAL_PREG_GENERAL = preguntaInstrumento.getVal_preg_general();

        if (preguntaInstrumento.getVal_diferenciado() == null && preguntaInstrumento.getVal_preg_general() == null) {

            if (Integer.valueOf(preguntaInstrumento.getIxp_orden()) > 0) {

                //Busca los validadores para la pregunta
                List<emc_validadores_instrumento> lsValInstrumento = emc_validadores_instrumento.find(emc_validadores_instrumento.class, "PREIDPREGUNTA = ?", pPRE_IDPREGUNTA);
                TIENE_VAL = lsValInstrumento.size();

                //Busca a la persona en preguntas derivadas
                String[] paramsDV = {pHOG_CODIGO, pPER_IDPERSONA, pPRE_IDPREGUNTA};
                List<emc_preguntas_derivadas> lsPreguntasDerivadas = emc_preguntas_derivadas.find(emc_preguntas_derivadas.class, "HOGCODIGO = ? AND PERIDPERSONA = ? AND PREIDPREGUNTA = ?", paramsDV);
                TOTAL = lsPreguntasDerivadas.size();

                if (TOTAL > 0) TOTAL = 1;

                if ((IXP_ORDEN == 1 && TIENE_VAL >= 1) || (IXP_ORDEN != 1 && TIENE_VAL >= 1 && TOTAL == 1)) {
                    String[] parVal = {pPRE_IDPREGUNTA, pPER_IDPERSONA, pPRE_IDPREGUNTA};
                    List<emc_validadores_instrumento> curDatos = emc_validadores_instrumento.find(emc_validadores_instrumento.class,
                            "PREIDPREGUNTA = ? " +
                                    "OR PREIDPREGUNTA IN (SELECT DISTINCT PREIDPREGUNTA FROM EMCVALIDADORESPERSONA VP " +
                                    "JOIN EMCPREGUNTASDERIVADAS PD ON PD.PERIDPERSONA = VP.PERIDPERSONA " +
                                    "WHERE PD.PERIDPERSONA= ?" +
                                    "AND PD.PREIDPREGUNTA = ?)", parVal);


                    for (Integer conVal = 0; conVal < curDatos.size(); conVal++) {
                        emc_validadores_instrumento valInstrumento = curDatos.get(conVal);
                        String[] parValPer = {pPER_IDPERSONA, pHOG_CODIGO, valInstrumento.getVal_idvalidador_pers()};
                        List<emc_validadores_persona> lsValPersona = emc_validadores_persona.find(emc_validadores_persona.class,
                                "PERIDPERSONA = ? AND HOGCODIGO = ? AND VALIDVALIDADOR = ?", parValPer);
                        Integer VALOR = lsValPersona.size();


                        if (VALOR > 0) {
                            emc_validadores_persona tmValPersona = lsValPersona.get(0);
                            //verifica si la persona cumple con la validacion 1 si cumple, 0 no cumple
                            EXISTE = FN_VALIDARPERSONA(tmValPersona.getPre_valor().toString(), valInstrumento.getVal_idvalidador());
                        } else {
                            EXISTE = 0;
                        }

                        if (EXISTE == 1) CONT++;
                        if (TIENE_VAL == CONT) {
                            Result = 1;
                        } else {
                            Result = 0;
                        }
                    }

                } else if ((IXP_ORDEN == 1 && TIENE_VAL == 0) || (IXP_ORDEN != 1 && TIENE_VAL == 0 && TOTAL == 1)) {
                    Result = 1;
                } else if ((IXP_ORDEN != 1 && TIENE_VAL >= 1 && TOTAL == 0) || (IXP_ORDEN != 1 && TIENE_VAL == 0 && TOTAL == 0)) {
                    Result = 0;
                }

            } else if (preguntaInstrumento.getVal_preg_general().equals("")) {
                Result = 0;
                TIENE_VAL = 1;


                if (IXP_ORDEN > 0) {

                    String[] parPDev = {pHOG_CODIGO, pPER_IDPERSONA, pPRE_IDPREGUNTA};
                    List<emc_preguntas_derivadas> lsPregDerivadas = emc_preguntas_derivadas.find(emc_preguntas_derivadas.class,
                            "HOGCODIGO = ? " +
                                    "AND PERIDPERSONA = ? " +
                                    "AND PREIDPREGUNTA = ?", parPDev);
                    TOTAL = lsPregDerivadas.size();
                    if (TOTAL > 0) {
                        TOTAL = 1;
                    }

                    if ((IXP_ORDEN == 1 && TIENE_VAL >= 1) || (IXP_ORDEN != 1 && TIENE_VAL >= 1 && TOTAL == 1)) {
                        Pos_cadena = 1;
                        Val_Diferenciado = preguntaInstrumento.getVal_diferenciado();
                        do {
                            Inic_cadena = Val_Diferenciado.indexOf('(', Pos_cadena);
                            Fin_cadena = Val_Diferenciado.indexOf(')', Pos_cadena);
                            Pos_cadena = Fin_cadena + 1;

                            //pRESPUESTASN = "(" + pRESPUESTASN.substring(0, pRESPUESTASN.length()-1) + ")";
                            Val_cadena = Val_Diferenciado.substring(Inic_cadena + 1, Fin_cadena - Inic_cadena - 1);
                            Inic_cadena = Val_cadena.indexOf(',', 1);
                            //validador de la persona
                            String Val_idValidador = Val_cadena.substring(Inic_cadena + 1);
                            //validador del instrumento
                            String Val_idPersona = Val_cadena.substring(1, Inic_cadena - 1);

                            String[] parValPer = {pPER_IDPERSONA, pHOG_CODIGO, Val_idValidador};
                            List<emc_validadores_persona> lsValidadoresPersona = emc_validadores_persona.find(emc_validadores_persona.class,
                                    "PERIDPERSONA = ? AND HOGCODIGO = ? AND VALIDVALIDADOR = ?", parValPer);

                            if (lsValidadoresPersona.size() > 0) {
                                EXISTE = FN_VALIDARPERSONA(lsValidadoresPersona.get(0).getPre_valor(), Val_idValidador);
                            } else {
                                EXISTE = 0;
                            }

                            if (EXISTE == 1) {
                                Val_Diferenciado_Res = Val_Diferenciado_Res + "(" + Val_cadena + ")";
                            } else {
                                Val_Diferenciado_Res = Val_Diferenciado_Res + "(x,x)";
                            }

                            Val_cadena = Val_Diferenciado.substring(Pos_cadena, 3);

                            if (Val_cadena.toUpperCase().equals(" OR") || Pos_cadena > Val_Diferenciado.length()) {
                                if (Val_Diferenciado.indexOf(Val_Diferenciado_Res, 1) > 0) {
                                    BUSRESPUESTAS = 1;
                                } else {
                                    BUSRESPUESTAS = 0;
                                }

                                if (BUSRESPUESTAS == 0) {
                                    Val_Diferenciado_Res = "";
                                    Result = 0;
                                } else {
                                    Result = 1;
                                    return Result;
                                }
                            }


                        } while (Pos_cadena > Val_Diferenciado.length());
                    }

                } else if ((IXP_ORDEN == 1 && TIENE_VAL == 0) || (IXP_ORDEN != 1 && TIENE_VAL == 0 && TOTAL == 1)) {
                    Result = 1;
                } else if ((IXP_ORDEN != 1 && TIENE_VAL >= 1 && TOTAL == 0) || (IXP_ORDEN != 1 && TIENE_VAL == 0 && TOTAL == 0)) {
                    Result = 0;
                }
            } else {
                Result = 0;
            }

        }
        else if (preguntaInstrumento.getVal_preg_general() == null)
        {
            Result =0;
            //validadores diferenciados
            TIENE_VAL =1;
            if (IXP_ORDEN > 0)
            {
                //Busca a la persona en preguntas derivadas
                String pPD = "SELECT COUNT(*)  FROM EMCPREGUNTASDERIVADAS T " +
                        " WHERE HOGCODIGO = '" +pHOG_CODIGO+ "' AND PERIDPERSONA= "+ pPER_IDPERSONA +
                        " AND PREIDPREGUNTA = "+pPRE_IDPREGUNTA;

                TOTAL =  execCalculos(pPD);
                if (TOTAL>0){TOTAL=1;}


                if ((IXP_ORDEN == 1 &&  TIENE_VAL >=1) || (IXP_ORDEN != 1 && TIENE_VAL>=1 && TOTAL ==1))
                {
                    //Val_Diferenciado
                    Pos_cadena = 0;
                    Val_Diferenciado = preguntaInstrumento.getVal_diferenciado();
                    do {

                        Inic_cadena = Val_Diferenciado.indexOf('(', Pos_cadena);
                        Fin_cadena = Val_Diferenciado.indexOf(')', Pos_cadena);
                        Pos_cadena = Fin_cadena + 1;

                        if(Fin_cadena<0)
                        {
                            if(Fin_cadena<0)
                            {
                                Val_cadena = Val_Diferenciado.substring(Inic_cadena + 1, Fin_cadena + 1 );
                            }
                            else
                            {
                                Val_cadena = Val_Diferenciado.substring(Inic_cadena + 1, Fin_cadena - Inic_cadena );
                            }

                        }else
                        {
                            Val_cadena = Val_Diferenciado.substring(Inic_cadena + 1, Fin_cadena/* - Inic_cadena*/ );
                        }

                        Inic_cadena = Val_cadena.indexOf(',', 0);
                        //validador de la persona
                        String Val_idValidador = Val_cadena.substring(Inic_cadena + 1);
                        //validador del instrumento
                        String Val_idPersona;
                        if(Inic_cadena<0)
                        {
                            Val_idPersona = Val_cadena.substring(0, Inic_cadena+1);
                        }else
                        {
                            Val_idPersona = Val_cadena.substring(0, Inic_cadena);
                        }

                        //VERIFICA SI LA PERSONA TIENE VALOR PARA ESTE VALIDADOR y
                        //BUSCA EL VALOR DEL VALIDADOR DE LA PERSONA
                        String[] parValPer = {pPER_IDPERSONA, pHOG_CODIGO, Val_idValidador};
                        List<emc_validadores_persona> lsValidadoresPersona = emc_validadores_persona.find(emc_validadores_persona.class,
                                "PERIDPERSONA = ? AND HOGCODIGO = ? AND VALIDVALIDADOR = ?", parValPer);

                        if (lsValidadoresPersona.size() > 0)
                        {
                            //verifica si la persona cumple con la validacion 1 si cumple, 0 no cumple
                            EXISTE = FN_VALIDARPERSONA(lsValidadoresPersona.get(0).getPre_valor(), Val_idPersona/*Val_idValidador*/);
                        } else {
                            EXISTE = 0;
                        }
                        if (EXISTE == 1) {
                            Val_Diferenciado_Res = Val_Diferenciado_Res + "(" + Val_cadena + ")";
                        } else {
                            Val_Diferenciado_Res = Val_Diferenciado_Res + "(x,x)";
                        }

                        if(Val_Diferenciado.length() <= Pos_cadena+3)
                        {
                            Val_cadena = Val_Diferenciado.substring(Pos_cadena, Pos_cadena);
                        }else
                        {
                            Val_cadena = Val_Diferenciado.substring(Pos_cadena,Pos_cadena+3);
                        }

                        if (Val_cadena.toUpperCase().equals(" OR") || Pos_cadena >/*=*/ Val_Diferenciado.length()
                                || Val_cadena.equals("")) {

                            if (Val_Diferenciado.indexOf(Val_Diferenciado_Res, 0) >= 0) {

                                BUSRESPUESTAS = 1;
                            } else {
                                BUSRESPUESTAS = 0;
                            }

                            if (BUSRESPUESTAS == 0) {
                                Val_Diferenciado_Res = "";
                                Result = 0;
                            } else {
                                Result = 1;

                                return Result;

                            }

                        }
                        if(Pos_cadena <= 0){Pos_cadena=Val_Diferenciado.length()+1;}


                    }while(Pos_cadena <= Val_Diferenciado.length());

                }else
                {
                    Result = 0;
                }


            }else
            {
                Result =0;
            }

        }
        else {
            Result = 0;
            //validadores diferenciados
            TIENE_VAL = 1;

            if (IXP_ORDEN > 0) {

                List<emc_miembros_hogar> lsMiembrosHogar = emc_miembros_hogar.find(emc_miembros_hogar.class, "HOGCODIGO = ?", pHOG_CODIGO);

                //Busca a la persona en preguntas derivadas
                String[] paramsDV = {pHOG_CODIGO, pPER_IDPERSONA, pPRE_IDPREGUNTA};
                List<emc_preguntas_derivadas> lsPreguntasDerivadas = emc_preguntas_derivadas.find(emc_preguntas_derivadas.class, "HOGCODIGO = ? AND PERIDPERSONA = ? AND PREIDPREGUNTA = ?", paramsDV);
                TOTAL = lsPreguntasDerivadas.size();

                if (TOTAL > 0)
                {
                    TOTAL = 1;
                }

                for (int conM = 0; conM < lsMiembrosHogar.size(); conM++) {
                    TOTAL = 1;
                    emc_miembros_hogar mHogar = lsMiembrosHogar.get(conM);

                    if ((IXP_ORDEN == 1 && TIENE_VAL >= 1) || (IXP_ORDEN != 1 && TIENE_VAL >= 1 && TOTAL == 1)) {
                        Pos_cadena = 0;
                        //Val_Diferenciado = preguntaInstrumento.getVal_preg_general();
                        VAL_PREG_GENERAL = preguntaInstrumento.getVal_preg_general();
                        do {
                            Inic_cadena = VAL_PREG_GENERAL.indexOf('(', Pos_cadena);
                            Fin_cadena = VAL_PREG_GENERAL.indexOf(')', Pos_cadena);
                            Pos_cadena = Fin_cadena + 1;

                            if(Fin_cadena<0)
                            {
                                if(Fin_cadena<0)
                                {
                                    Val_cadena = VAL_PREG_GENERAL.substring(Inic_cadena + 1, Fin_cadena + 1 );
                                }
                                else
                                {
                                    Val_cadena = VAL_PREG_GENERAL.substring(Inic_cadena + 1, Fin_cadena - Inic_cadena );
                                }

                            }else
                            {
                                Val_cadena = VAL_PREG_GENERAL.substring(Inic_cadena + 1, Fin_cadena/* - Inic_cadena*/ );
                            }

                            Inic_cadena = Val_cadena.indexOf(',', 0);
                            //validador de la persona
                            String Val_idValidador = Val_cadena.substring(Inic_cadena + 1);
                            //validador del instrumento
                            String Val_idPersona;
                            if(Inic_cadena<0)
                            {
                                Val_idPersona = Val_cadena.substring(0, Inic_cadena+1);
                            }else
                            {
                                Val_idPersona = Val_cadena.substring(0, Inic_cadena);
                            }

                            //VERIFICA SI LA PERSONA TIENE VALOR PARA ESTE VALIDADOR y
                            //BUSCA EL VALOR DEL VALIDADOR DE LA PERSONA
                            String[] parValPer = {mHogar.getPer_idpersona(), pHOG_CODIGO, Val_idValidador};
                            List<emc_validadores_persona> lsValidadoresPersona = emc_validadores_persona.find(emc_validadores_persona.class,
                                    "PERIDPERSONA = ? AND HOGCODIGO = ? AND VALIDVALIDADOR = ?", parValPer);

                            if (lsValidadoresPersona.size() > 0) {
                                //verifica si la persona cumple con la validacion 1 si cumple, 0 no cumple
                                EXISTE = FN_VALIDARPERSONA(lsValidadoresPersona.get(0).getPre_valor(), Val_idPersona/*Val_idValidador*/);
                            } else {
                                EXISTE = 0;
                            }

                            if (EXISTE == 1) {
                                Val_Diferenciado_Res = Val_Diferenciado_Res + "(" + Val_cadena + ")";
                            } else {
                                Val_Diferenciado_Res = Val_Diferenciado_Res + "(x,x)";
                            }

                            if(VAL_PREG_GENERAL.length() <= Pos_cadena+3)
                            {
                                Val_cadena = VAL_PREG_GENERAL.substring(Pos_cadena,Pos_cadena);
                            }else
                            {
                                Val_cadena = VAL_PREG_GENERAL.substring(Pos_cadena,Pos_cadena+3);
                            }



                            if (Val_cadena.toUpperCase().equals(" OR") || Pos_cadena >/*=*/ VAL_PREG_GENERAL.length()
                                    || Val_cadena.equals("")) {

                                if (VAL_PREG_GENERAL.indexOf(VAL_PREG_GENERAL, 0) >= 0) {

                                    BUSRESPUESTAS = 1;
                                } else {
                                    BUSRESPUESTAS = 0;
                                }

                                if (BUSRESPUESTAS == 0) {
                                    Val_Diferenciado_Res = "";
                                    Result = 0;
                                } else {
                                    Result = 1;

                                    return Result;

                                }

                            }
                            if(Pos_cadena <= 0){Pos_cadena=VAL_PREG_GENERAL.length()+1;}

                        } while ( Pos_cadena <= VAL_PREG_GENERAL.length());
                    }
                }
            } else {
                Result = 0;
            }
        }
        return Result;
    }

    public static String FN_TRAETIPOCAMPO(Integer pID_RESPUESTA) {
        String Resultado = "";

        List<emc_preguntas_instrumento> lsPregIns = emc_preguntas_instrumento.find(emc_preguntas_instrumento.class, "PREIDPREGUNTA IN (SELECT PREIDPREGUNTA FROM EMCRESPUESTAS WHERE RESIDRESPUESTA = ?)", pID_RESPUESTA.toString());
        if (lsPregIns.size() > 0) {
            emc_preguntas_instrumento tmPregIns = lsPregIns.get(0);
            switch (tmPregIns.getPre_tipocampo()) {
                case "TE":
                case "DP":
                case "CT":
                case "LT":
                case "TA":
                case "AT":
                    Resultado = "UN";
                    break;

                default:
                    Resultado = "MU";
                    break;
            }
        }

        return Resultado;
    }


    public static boolean FN_VERIFICARRESPUESTA(String pHOG_CODIGO,Integer pINS_IDINSTRUMENTO,Integer pID_RESPUESTA,Integer pID_PERSONA,String PDepartamento) {
        boolean Resultado;
        Integer Existe;

        if (FN_TRAETIPOCAMPO(pID_RESPUESTA).equals("MU")) {
            String pPER = null;
            if (pID_PERSONA != null)
                pPER = pID_PERSONA.toString();
            String[] parResEnc = {pPER, pINS_IDINSTRUMENTO.toString(), pID_RESPUESTA.toString(), pHOG_CODIGO};
            List<emc_respuestas_encuesta> lsResEnc = emc_respuestas_encuesta.find(emc_respuestas_encuesta.class,
                    "PERIDPERSONA = ? AND INSIDINSTRUMENTO = ? AND RESIDRESPUESTA IN (SELECT r.RESIDRESPUESTA FROM EMCRESPUESTAS r WHERE r.RESIDRESPUESTA = ?) AND HOGCODIGO = ?", parResEnc);
            Existe = lsResEnc.size();
        } else {
            String p = "null";
            if (pID_PERSONA != null) {
                p = pID_PERSONA.toString();
            }

            String[] parResEnc = {pHOG_CODIGO, p, pINS_IDINSTRUMENTO.toString(), pID_RESPUESTA.toString(), PDepartamento};
            List<emc_respuestas_encuesta> lsResEnc = emc_respuestas_encuesta.find(emc_respuestas_encuesta.class,
                    "HOGCODIGO = ? AND PERIDPERSONA = ? AND INSIDINSTRUMENTO = ? AND RESIDRESPUESTA = ? AND RXPTEXTORESPUESTA = ?", parResEnc);
            Existe = lsResEnc.size();
        }

        if (Existe > 0) {
            Resultado = true;
        } else {
            Resultado = false;
        }
        return Resultado;
    }

    public static void SP_BORRADORESPUESTAS(String pcod_hogar, Integer pins_IdInstrumento, Integer pId_Respuesta, Integer pper_idPersona) {
        //borra las respuestas de la pregunta
        String[] parDelete = {pcod_hogar, pper_idPersona.toString(), pins_IdInstrumento.toString(), pcod_hogar, pper_idPersona.toString(), pins_IdInstrumento.toString(), pId_Respuesta.toString()};
        String delQ = "DELETE FROM EMCRESPUESTASENCUESTA " +
                " WHERE HOGCODIGO = '" + pcod_hogar + "', +" +
                " AND PERIDPERSONA = " + pper_idPersona.toString() +
                " AND INSIDINSTRUMENTO = " + pins_IdInstrumento +
                " AND RESIDRESPUESTA = " + pId_Respuesta;
        emc_respuestas_encuesta.executeQuery(delQ);

    }

    public static void SP_BORRADOVALIDADORES(String pcod_hogar, Integer pins_IdInstrumento, Integer pId_Pregunta, Integer pper_idPersona) {

        String[] parRespuestas = {pId_Pregunta.toString()};
        List<emc_respuestas_instrumento> lsRespuestas = emc_respuestas_instrumento.find(emc_respuestas_instrumento.class,
                "RESIDRESPUESTA IN (SELECT res.RESIDRESPUESTA FROM EMCRESPUESTAS res WHERE PREIDPREGUNTA = ?) AND VALIDVALIDADOR IS NOT NULL ", parRespuestas);

        for (int conR = 0; conR < lsRespuestas.size(); conR++) {
            //borra las respuestas de la pregunta
            emc_respuestas_instrumento tmRespuesta = lsRespuestas.get(conR);
            if (tmRespuesta.getVal_idvalidador() != null) {
                String[] parDel = {pcod_hogar, tmRespuesta.getVal_idvalidador(), pper_idPersona.toString()};
                emc_validadores_persona.deleteAll(emc_validadores_persona.class,
                        "HOGCODIGO = ? AND VALIDVALIDADOR = ? AND PERIDPERSONA = ?", parDel);
            }
        }
    }

    public static Integer FN_COMPROBARVALIDACION(Integer pIDRESPUESTA) {
        Integer Result = 0;

        String pValQ = "SELECT VALIDVALIDADORDATO " +
                "     FROM EMCPREGUNTASINSTRUMENTO PR " +
                "     JOIN EMCRESPUESTAS RE ON RE.PREIDPREGUNTA=PR.PREIDPREGUNTA " +
                "     WHERE RE.RESIDRESPUESTA= " + pIDRESPUESTA;
        Result = execCalculos(pValQ);
        /*List<emc_preguntas_instrumento> lsPregIns = emc_preguntas_instrumento.find(emc_preguntas_instrumento.class,
                "PREIDPREGUNTA = (SELECT RE.PREIDPREGUNTA FROM EMCRESPUESTAS RE WHERE RE.RESIDRESPUESTA = ?)", pIDRESPUESTA.toString());
        if (lsPregIns.size() > 0) {
            if (lsPregIns.get(0).getVal_idvalidador_dato() != null)
                Result = Integer.valueOf(lsPregIns.get(0).getVal_idvalidador_dato());
        }*/
        return Result;
    }

    public static void SP_CAMBIAR_ESTADOGUARDADO(String pHOG_CODIGO, Integer pINS_IDINSTRUMENTO, Integer pPER_IDPERSONA, Integer pRES_IDRESPUESTA) {
        String[] parUpd = {pHOG_CODIGO, pINS_IDINSTRUMENTO.toString(), pRES_IDRESPUESTA.toString()};
        emc_preguntas_derivadas.executeQuery("UPDATE EMCPREGUNTASDERIVADAS SET GUARDADO = 1 " +
                "WHERE HOGCODIGO = ? " +
                "AND INSIDINSTRUMENTO = ? " +
                "AND PREIDPREGUNTA = ?", parUpd);
    }

    public static Integer FN_VALIDARXRESPUESTA(String pHOG_CODIGO, Integer pID_PREGUNTA, Integer pID_RESPUESTA, String pID_PERSONA, Integer pINS_IDINSTRUMENTO) {
        String Val_RES;
        String CONDICION;
        String BUSRESPUESTAS;
        String RESPCONTESTADAS;
        String RESULTADO;
        Integer CONTEO;
        Integer Result = 0;

        List<emc_validadores_respuesta> lsValRespuesta = emc_validadores_respuesta.find(emc_validadores_respuesta.class, "IDPREGUNTA = ?", pID_PREGUNTA.toString());
        CONTEO = lsValRespuesta.size();

        if (CONTEO == 1) {
            CONDICION = lsValRespuesta.get(0).getCondicion();
            BUSRESPUESTAS = lsValRespuesta.get(0).getIdrespuesta();

            String[] parRes = {pHOG_CODIGO, pID_PERSONA.toString()};
            String qR = "RESIDRESPUESTA IN ( " + BUSRESPUESTAS + " ) ";
            List<emc_respuestas_encuesta> lsRespEncuesta = emc_respuestas_encuesta.find(emc_respuestas_encuesta.class, qR + "AND HOGCODIGO = ? AND PERIDPERSONA = ?", parRes);
            RESPCONTESTADAS = "";
            for (int conRE = 0; conRE < lsRespEncuesta.size(); conRE++) {
                emc_respuestas_encuesta tmRespuesta = lsRespEncuesta.get(conRE);
                RESPCONTESTADAS += tmRespuesta.getRes_idrespuesta();
                if (conRE < lsRespEncuesta.size() - 1)
                    RESPCONTESTADAS += ",";
            }
            CONDICION = CONDICION.replace("CADENA", RESPCONTESTADAS);
            CONDICION = CONDICION.replace(",1)",")");
            emc_calculos.executeQuery("INSERT INTO EMCCALCULOS(VALOR) SELECT CASE WHEN " + CONDICION + " THEN 1 ELSE 0 END RSULTADO ;");
            List<emc_calculos> lsCalculos = emc_calculos.find(emc_calculos.class, null, null);
            Result = lsCalculos.get(0).getValor();
            emc_calculos.deleteAll(emc_calculos.class);
        }
        return Result;
    }

    public static Integer execCalculos(String query) {
        Integer Result = 0;
        emc_calculos.executeQuery("INSERT INTO EMCCALCULOS(VALOR) " + query);
        List<emc_calculos> lsCalculos = emc_calculos.find(emc_calculos.class, null, null);
        if (lsCalculos.size() > 0)
            Result = lsCalculos.get(0).getValor();
        emc_calculos.deleteAll(emc_calculos.class);
        return Result;
    }



    public static void SP_SET_PREGUNTAS_DERIVADAS(String pHOG_CODIGO, Integer pPER_IDPERSONA, Integer pINS_IDINSTRUMENTO, Integer pId_RespuestaEncuesta, Integer pId_PreguntaPadre) {

        String pId_Tema;
        String pres_finaliza;
        String pTipo_Preg;
        Integer pTipo_valres;
        Integer pTipo_valfun = 0;
        String pTipo_Camp;
        Integer pTodoHogar;
        Integer pValidador;
        Integer pConteoHogar;
        Integer pConteo;

        List<emc_preguntas_instrumento> lsPregIns = emc_preguntas_instrumento.find(emc_preguntas_instrumento.class, "PREIDPREGUNTA = ?", pId_PreguntaPadre.toString());
        pId_Tema = lsPregIns.get(0).getTem_idtema();

        List<emc_miembros_hogar> lsMiembrosHogar = emc_miembros_hogar.find(emc_miembros_hogar.class, "HOGCODIGO = ?", pHOG_CODIGO);

        //BUSCAR EL TIPO DE PREGUNTA
        String[] parIns = {pINS_IDINSTRUMENTO.toString(), pId_Tema, pId_PreguntaPadre.toString()};
        List<emc_preguntas_instrumento> lsPregInsTipo = emc_preguntas_instrumento.find(emc_preguntas_instrumento.class,
                "INSIDINSTRUMENTO = ? AND TEMIDTEMA = ? AND PREIDPREGUNTA = ?", parIns);
        pTipo_Preg = lsPregInsTipo.get(0).getPre_tipopregunta();
        pTipo_Camp = lsPregInsTipo.get(0).getPre_tipocampo();

        //borra todas las preguntas derivadas de la pregunta padre
        if (!pTipo_Camp.equals("CL")) {
            String[] parDelPD = {pHOG_CODIGO, pINS_IDINSTRUMENTO.toString(), pId_PreguntaPadre.toString(), pPER_IDPERSONA.toString(), pId_Tema};
            emc_preguntas_derivadas.deleteAll(emc_preguntas_derivadas.class,
                    "HOGCODIGO = ? " +
                            "AND INSIDINSTRUMENTO = ? " +
                            "AND PREIDPREGUNTAPADRE = ? " +
                            "AND PERIDPERSONA = ? " +
                            "AND TEMIDTEMA = ?", parDelPD);
        }

        List<emc_respuestas_instrumento> lsResIns = emc_respuestas_instrumento.find(emc_respuestas_instrumento.class, "RESIDRESPUESTA = ?", pId_RespuestaEncuesta.toString());
        if (lsResIns.size() > 0) {
            pres_finaliza = lsResIns.get(0).getRes_finaliza();
            if (pres_finaliza != null) {
                if (pres_finaliza.equals("SI")) {
                    emc_preguntas_derivadas.deleteAll(emc_preguntas_derivadas.class, "PERIDPERSONA = ? AND GUARDADO = 0", pPER_IDPERSONA.toString());
                }
            }
        }

        if(pTipo_Preg.equals("IN")){

            String inSQL = "INSERT INTO EMCPREGUNTASDERIVADAS(HOGCODIGO, PREIDPREGUNTA, PERIDPERSONA, GUARDADO, INSIDINSTRUMENTO, TEMIDTEMA,PREIDPREGUNTAPADRE) " +
                    " SELECT HOGCODIGO,PREIDPREGUNTA, PERIDPERSONA,GUARDADO, Instrumento,idtema,prepadre FROM " +
                    " (SELECT '" + pHOG_CODIGO + "' AS HOGCODIGO, PH.PREIDPREGUNTA, " + pPER_IDPERSONA + " AS PERIDPERSONA,0 AS GUARDADO, " + pINS_IDINSTRUMENTO + "  as Instrumento," +
                    "(SELECT TEMIDTEMA  FROM EMCPREGUNTASINSTRUMENTO " +
                    "WHERE PREIDPREGUNTA = ph.PREIDPREGUNTA ) as idtema, " + pId_PreguntaPadre + " as prepadre, " +
                    "(SELECT DISTINCT INDJEFE FROM EMCMIEMBROSHOGAR WHERE HOGCODIGO = '" + pHOG_CODIGO + "' and PERIDPERSONA = " + pPER_IDPERSONA + ") as encuestada, " +
                    "PR.PRETIPOPREGUNTA " +
                    "FROM EMCPREGUNTAHIJOS PH " +
                    "left JOIN EMCPREGUNTASINSTRUMENTO PR ON PR.PREIDPREGUNTA = PH.PREIDPREGUNTA AND PR.PREACTIVA='SI' " +
                    "WHERE ph.RESIDRESPUESTA = " + pId_RespuestaEncuesta + ") " +
                    "where PRETIPOPREGUNTA = 'IN' OR (PRETIPOPREGUNTA = 'GE' AND encuEstada='SI')";
            emc_preguntas_derivadas.executeQuery(inSQL);
        }else if(pTipo_Preg.equals("GE")){
            for(int conM = 0; conM < lsMiembrosHogar.size(); conM++){
                emc_miembros_hogar tmMiembro = lsMiembrosHogar.get(conM);
                String inSQL = "INSERT INTO EMCPREGUNTASDERIVADAS(HOGCODIGO, PREIDPREGUNTA, PERIDPERSONA, GUARDADO, INSIDINSTRUMENTO, TEMIDTEMA,PREIDPREGUNTAPADRE) " +
                        " select HOGCODIGO,PREIDPREGUNTA, PERIDPERSONA,GUARDADO, Instrumento,idtema,prepadre FROM " +
                        " (SELECT '" + pHOG_CODIGO + "' AS HOGCODIGO, PH.PREIDPREGUNTA, " + tmMiembro.getPer_idpersona() + " AS PERIDPERSONA,0 AS GUARDADO, " + pINS_IDINSTRUMENTO + "  as Instrumento," +
                        "(SELECT TEMIDTEMA  FROM EMCPREGUNTASINSTRUMENTO " +
                        "WHERE PREIDPREGUNTA = ph.PREIDPREGUNTA ) as idtema, " + pId_PreguntaPadre + " as prepadre, " +
                        "(SELECT DISTINCT INDJEFE FROM EMCMIEMBROSHOGAR WHERE HOGCODIGO = '" + pHOG_CODIGO + "' and PERIDPERSONA = " + tmMiembro.getPer_idpersona() + ") as encuestada, " +
                        "PR.PRETIPOPREGUNTA " +
                        "FROM EMCPREGUNTAHIJOS PH " +
                        "left JOIN EMCPREGUNTASINSTRUMENTO PR ON PR.PREIDPREGUNTA = PH.PREIDPREGUNTA AND PR.PREACTIVA='SI' " +
                        "WHERE ph.RESIDRESPUESTA = " + pId_RespuestaEncuesta + ") " +
                        "where PRETIPOPREGUNTA = 'IN' OR (PRETIPOPREGUNTA = 'GE' AND encuEstada='SI')";
                emc_preguntas_derivadas.executeQuery(inSQL);
            }


        }

        /*String[] upPD = {pHOG_CODIGO, pId_PreguntaPadre.toString(), pINS_IDINSTRUMENTO.toString()};
        emc_preguntas_derivadas.executeQuery("UPDATE EMCPREGUNTASDERIVADAS SET CONTADOR = CONTADOR + 1 " +
                "WHERE HOGCODIGO = ? AND PREIDPREGUNTA = ? AND INSIDINSTRUMENTO = ?", upPD);*/

        //--1. VALIDA QUE EL CAMPO PRE_DEPENDE DE LA TABLA GIC_N_PREGUNTASHIJOS ESTE MARCADO CON LA ETIQUETA 'SI', PARA REALIZAR
        //--LAS VALIDACIONES QUE HABILITAN LAS PREGUNTAS CONFIGURADAS EN EL CAMPO PRE_IDPREGUNTA DE LA TABLA GIC_N_PREGUNTASHIJOS
        List<emc_pregunta_hijos> lsPregHijos = emc_pregunta_hijos.find(emc_pregunta_hijos.class, "RESIDRESPUESTA IN (?) and PREDEPENDE='SI'", pId_RespuestaEncuesta.toString());
        pTipo_valres = lsPregHijos.size();

        if(pTipo_valres > 0)
        {
            SP_GET_PREGUNTAS(pINS_IDINSTRUMENTO,pHOG_CODIGO,Integer.parseInt(pId_Tema),
                    pPER_IDPERSONA,pId_RespuestaEncuesta.toString(),pId_PreguntaPadre,pTipo_valres);
        }
        //--SI LA  VARIABLE pTipo_valres ES  0 ENTONCES TERMINA EL PROCEDIMIENTO

       /* if (pTipo_valres == 1) {
            List<emc_pregunta_hijos> lsPregHijHog = emc_pregunta_hijos.find(emc_pregunta_hijos.class, "RESIDRESPUESTA IN (?) AND PREDEPENDE='SI'", pId_RespuestaEncuesta.toString());
            pTipo_valres = Integer.valueOf(lsPregHijHog.get(0).getPre_idpregunta());
            pTodoHogar = Integer.valueOf(lsPregHijHog.get(0).getVal_todohogar());*/

            /*if (pTodoHogar > 0) {
                for (int conM = 0; conM < lsMiembrosHogar.size(); conM++) {
                    emc_miembros_hogar miembro = lsMiembrosHogar.get(conM);
                    pValidador = FN_VALIDARXRESPUESTA(pHOG_CODIGO, pTipo_valres, 0, miembro.getPer_idpersona(), pINS_IDINSTRUMENTO);
                    if (pTodoHogar == 1) {
                        pConteoHogar = lsMiembrosHogar.size();


                        String pConQ = "SELECT MAX(CAST(PD.CONTADOR AS INTEGER)) " +
                                "FROM EMCPREGUNTASDERIVADAS PD " +
                                "WHERE PD.HOGCODIGO = '" + pHOG_CODIGO + "' " +
                                " AND PD.PREIDPREGUNTA = " + pId_PreguntaPadre +
                                " AND PD.INSIDINSTRUMENTO = " + pINS_IDINSTRUMENTO +
                                " AND PD.TEMIDTEMA = " + pId_Tema;
                        pConteo = execCalculos(pConQ);*/

                        /*
                        String[] parCalc = {pHOG_CODIGO, pId_PreguntaPadre.toString(), pINS_IDINSTRUMENTO.toString(), pId_Tema};
                        emc_calculos.executeQuery("INSERT INTO EMCCALCULOS(VALOR) SELECT MAX(PD.CONTADOR) " +
                                "FROM EMCPREGUNTASDERIVADAS PD WHERE PD.HOGCODIGO = ? AND PD.PREIDPREGUNTA = ? " +
                                "AND PD.INSIDINSTRUMENTO = ? AND PD.TEMIDTEMA = ?", parCalc);
                        List<emc_calculos> lsCalculos = emc_calculos.find(emc_calculos.class, null, null);
                        pConteo = lsCalculos.get(0).getValor();
                        emc_calculos.deleteAll(emc_calculos.class);
                        */

                    /*    if (pValidador == 1 && (pConteoHogar == pConteo)) {
                            pTipo_valfun = 1;
                        } else {
                            pTipo_valfun = 0;
                            break;
                        }
                    } else if (pTodoHogar == 2) {
                        if (pValidador == 1) {
                            pTipo_valfun = 1;
                            break;
                        } else {
                            pTipo_valfun = 0;
                        }
                    }
                }
            } else {
                pTipo_valfun = FN_VALIDARXRESPUESTA(pHOG_CODIGO, pTipo_valres, 0, pPER_IDPERSONA.toString(), pINS_IDINSTRUMENTO);
            }*/

            //if (pTipo_valfun == 1) {
                //Borrar las preguntas derivadas de la pregunta padre
                /*String[] parDel = {pHOG_CODIGO, pId_PreguntaPadre.toString(), pPER_IDPERSONA.toString(), pINS_IDINSTRUMENTO.toString()};
                emc_preguntas_derivadas.deleteAll(emc_preguntas_derivadas.class, "HOGCODIGO = ? " +
                        "AND PREIDPREGUNTAPADRE = ? " +
                        "AND PERIDPERSONA = ? " +
                        "AND INSIDINSTRUMENTO = ?", parDel);*/

                //Insertar en preguntas derivadas la pregunta
                /*emc_preguntas_derivadas insPD = new emc_preguntas_derivadas();
                insPD.setHog_codigo(pHOG_CODIGO);
                insPD.setPre_idpregunta(pTipo_valres.toString());
                insPD.setPer_idpersona(pPER_IDPERSONA.toString());
                insPD.setGuardado("0");
                insPD.setIns_idinstrumento(pINS_IDINSTRUMENTO.toString());
                insPD.setTem_idtema(Integer.valueOf(pId_Tema));
                insPD.setPre_idpreguntapadre(pId_PreguntaPadre);
                insPD.save();*/
            //} else {
                //Borrar las preguntas derivadas QUE NO CUMPLE CONDICION
                /*String[] parDel = {pHOG_CODIGO, pTipo_valres.toString(), pId_PreguntaPadre.toString(), pPER_IDPERSONA.toString(), pINS_IDINSTRUMENTO.toString()};
                emc_preguntas_derivadas.deleteAll(emc_preguntas_derivadas.class, "HOGCODIGO = ? " +
                        "AND PREIDPREGUNTA = ? " +
                        "AND PREIDPREGUNTAPADRE = ? " +
                        "AND PERIDPERSONA = ? " +
                        "AND INSIDINSTRUMENTO = ?", parDel);
            }*/
        //}
    }

    public static void SP_SET_RESPUESTAS_DE_ENCUESTA
            (
                    String pcod_hogar,
                    Integer pper_IdPersona,
                    Integer pres_IdRespuesta,
                    String prxp_TextoRespuesta,
                    String prxp_TipoPreguntaRespuesta,
                    Integer pins_IdInstrumento,
                    String pusu_UsuarioCreacion,
                    Integer pper_idPreguntaPadre,
                    Integer pbandera
            ) {
        Integer pValidador;
        String textVal;
        Integer pOrden = 0;

        List<emc_preguntas_instrumento> lsPregIns = emc_preguntas_instrumento.find(emc_preguntas_instrumento.class,
                "PREIDPREGUNTA = (SELECT r.PREIDPREGUNTA FROM EMCRESPUESTAS r WHERE r.RESIDRESPUESTA = ?)", pres_IdRespuesta.toString());
        if (lsPregIns.get(0).getIxp_orden().equals("")) {
            pOrden = null;
        } else {
            pOrden = Integer.valueOf(lsPregIns.get(0).getIxp_orden());
        }

        //Comprueba el validador y lo carga
        List<emc_respuestas_instrumento> lsResIns = emc_respuestas_instrumento.find(emc_respuestas_instrumento.class,
                "RESIDRESPUESTA = ?", pres_IdRespuesta.toString());
        if (lsResIns.size() == 0) {
            pValidador = null;
            textVal = null;
        } else {
            pValidador = null;
            textVal = null;

            if (lsResIns.get(0).getVal_idvalidador() != null)
                pValidador = Integer.valueOf(lsResIns.get(0).getVal_idvalidador());
            if (lsResIns.get(0).getVal_idvalidador_def() != null)
                textVal = lsResIns.get(0).getVal_idvalidador_def();
        }

        boolean resVerificarRespuesta = FN_VERIFICARRESPUESTA(pcod_hogar, pins_IdInstrumento, pres_IdRespuesta, pper_IdPersona, prxp_TextoRespuesta);
        if (resVerificarRespuesta == false || pOrden == 1) {

            String p = "null";
            if (pper_IdPersona != null) {
                p = pper_IdPersona.toString();
            }
            emc_respuestas_encuesta insRespuesta = new emc_respuestas_encuesta();
            insRespuesta.setHog_codigo(pcod_hogar);
            insRespuesta.setPer_idpersona(p);
            insRespuesta.setRes_idrespuesta(pres_IdRespuesta);
            insRespuesta.setRxp_tipopregunta(prxp_TipoPreguntaRespuesta);
            insRespuesta.setUsu_usuariocreacion(pusu_UsuarioCreacion);
            //se agrego la insersión de fecha
            insRespuesta.setUsu_fechacreacion(general.fechaActual());
            insRespuesta.setIns_idinstrumento(pins_IdInstrumento.toString());
            insRespuesta.setRxp_textorespuesta(prxp_TextoRespuesta);
            if(insRespuesta.getRes_idrespuesta() == 19){

                List<emc_miembros_hogar> lsMiem = emc_miembros_hogar.find(emc_miembros_hogar.class,
                        "PERIDPERSONA = ? AND HOGCODIGO = ?", p,pcod_hogar);


                emc_respuestas_encuesta insRespuestaN1 = new emc_respuestas_encuesta();
                insRespuestaN1.setHog_codigo(pcod_hogar);
                insRespuestaN1.setPer_idpersona(p);
                insRespuestaN1.setRes_idrespuesta(pres_IdRespuesta);
                insRespuestaN1.setRxp_tipopregunta(prxp_TipoPreguntaRespuesta);
                insRespuestaN1.setUsu_usuariocreacion(pusu_UsuarioCreacion);
                //se agrego la insersión de fecha
                insRespuestaN1.setUsu_fechacreacion(general.fechaActual());
                insRespuestaN1.setIns_idinstrumento(pins_IdInstrumento.toString());
                insRespuestaN1.setRxp_textorespuesta(prxp_TextoRespuesta);
                insRespuestaN1.setRes_idrespuesta(19);
                insRespuestaN1.setRxp_textorespuesta(lsMiem.get(0).getNombre1().trim());
                insRespuestaN1.save();

                emc_respuestas_encuesta insRespuestaN2 = new emc_respuestas_encuesta();
                insRespuestaN2.setHog_codigo(pcod_hogar);
                insRespuestaN2.setPer_idpersona(p);
                insRespuestaN2.setRes_idrespuesta(pres_IdRespuesta);
                insRespuestaN2.setRxp_tipopregunta(prxp_TipoPreguntaRespuesta);
                insRespuestaN2.setUsu_usuariocreacion(pusu_UsuarioCreacion);
                //se agrego la insersión de fecha
                insRespuestaN2.setUsu_fechacreacion(general.fechaActual());
                insRespuestaN2.setIns_idinstrumento(pins_IdInstrumento.toString());
                insRespuestaN2.setRxp_textorespuesta(prxp_TextoRespuesta);
                insRespuestaN2.setRes_idrespuesta(20);
                insRespuestaN2.setRxp_textorespuesta(lsMiem.get(0).getNombre2().trim());
                insRespuestaN2.save();

                emc_respuestas_encuesta insRespuestaA1 = new emc_respuestas_encuesta();
                insRespuestaA1.setHog_codigo(pcod_hogar);
                insRespuestaA1.setPer_idpersona(p);
                insRespuestaA1.setRes_idrespuesta(pres_IdRespuesta);
                insRespuestaA1.setRxp_tipopregunta(prxp_TipoPreguntaRespuesta);
                insRespuestaA1.setUsu_usuariocreacion(pusu_UsuarioCreacion);
                //se agrego la insersión de fecha
                insRespuestaA1.setUsu_fechacreacion(general.fechaActual());
                insRespuestaA1.setIns_idinstrumento(pins_IdInstrumento.toString());
                insRespuestaA1.setRxp_textorespuesta(prxp_TextoRespuesta);
                insRespuestaA1.setRes_idrespuesta(21);
                insRespuestaA1.setRxp_textorespuesta(lsMiem.get(0).getApellido1().trim());
                insRespuestaA1.save();

                emc_respuestas_encuesta insRespuestaA2 = new emc_respuestas_encuesta();
                insRespuestaA2.setHog_codigo(pcod_hogar);
                insRespuestaA2.setPer_idpersona(p);
                insRespuestaA2.setRes_idrespuesta(pres_IdRespuesta);
                insRespuestaA2.setRxp_tipopregunta(prxp_TipoPreguntaRespuesta);
                insRespuestaA2.setUsu_usuariocreacion(pusu_UsuarioCreacion);
                //se agrego la insersión de fecha
                insRespuestaA2.setUsu_fechacreacion(general.fechaActual());
                insRespuestaA2.setIns_idinstrumento(pins_IdInstrumento.toString());
                insRespuestaA2.setRxp_textorespuesta(prxp_TextoRespuesta);
                insRespuestaA2.setRes_idrespuesta(22);
                insRespuestaA2.setRxp_textorespuesta(lsMiem.get(0).getApellido2().trim());
                insRespuestaA2.save();

            }else{
                insRespuesta.save();
            }


        }

        if (pbandera == 1) {
            SP_BORRADOVALIDADORES(pcod_hogar, pins_IdInstrumento, pper_idPreguntaPadre, pper_IdPersona);
        }
        //pValidador = FN_COMPROBARVALIDACION(pres_IdRespuesta);

        if (pValidador != null) {
            if (Integer.valueOf(pValidador) != 0) {


                String[] parDelVal = {pins_IdInstrumento.toString(), pper_IdPersona.toString(), pValidador.toString(), pcod_hogar};
                emc_validadores_persona.deleteAll(emc_validadores_persona.class, "INSIDINSTRUMENTO = ? AND PERIDPERSONA = ? AND VALIDVALIDADOR = ? AND HOGCODIGO = ?", parDelVal);

                emc_validadores_persona tmValPer = new emc_validadores_persona();
                tmValPer.setIns_idinstrumento(pins_IdInstrumento.toString());
                tmValPer.setPer_idpersona(pper_IdPersona.toString());
                tmValPer.setVal_idvalidador(pValidador.toString());
                tmValPer.setHog_codigo(pcod_hogar);
                if (textVal != null) {
                    if (textVal.equals("")) {
                        if (prxp_TextoRespuesta != null)
                            tmValPer.setPre_valor(prxp_TextoRespuesta);
                    } else {
                        if (prxp_TextoRespuesta != null)
                            tmValPer.setPre_valor(textVal);
                    }
                } else {
                    tmValPer.setPre_valor(prxp_TextoRespuesta);
                }

                tmValPer.save();
            }
        }

        //CAMBIA EL ESTADO EN LA TABLA GIC_N_PREGUNTASDERIVADAS A 1
        SP_CAMBIAR_ESTADOGUARDADO(pcod_hogar, pins_IdInstrumento, pper_IdPersona, pper_idPreguntaPadre);

        //INSERTA LAS PREGUNTAS DE LA PREGUNTA QUE INGRESA
        SP_SET_PREGUNTAS_DERIVADAS(pcod_hogar, pper_IdPersona, pins_IdInstrumento, pres_IdRespuesta, pper_idPreguntaPadre);
    }


    public static void GIC_INSERT_VALIDADOR_PERFIL_USUARIO(Integer IDPERSONA, String CODHOGAR, String VAL_IDVALIDADOR,String PREVALOR, Integer IDINSTRUMENTO){

        String p = null;
        if (IDPERSONA != null)
            p = IDPERSONA.toString();
        emc_validadores_persona tmValTD = new emc_validadores_persona(IDINSTRUMENTO.toString(), p,VAL_IDVALIDADOR, PREVALOR.trim(), CODHOGAR, "0");
        tmValTD.save();
    }


    public static void GIC_INSERT_VALIDADOR_TIPO_PERSONA(Integer IDPERSONA, String CODHOGAR, String VALIDADOR, Integer IDINSTRUMENTO){

        Integer VALIDADOR_P = 0;
        String prevalor = "";

        if(VALIDADOR != null){
            if (VALIDADOR.trim().equals("5001")) {
                VALIDADOR_P = 5001;
                prevalor = "AUTORIZADO";
            } else if (VALIDADOR.trim().equals("5002")) {
                VALIDADOR_P = 5002;
                prevalor = "TUTOR";
            }else if(VALIDADOR.trim().equals("5003")){
                VALIDADOR_P = 5003;
                prevalor = "CUIDADOR PERMANENTE";
            }else if (VALIDADOR.trim().equals("")){
                VALIDADOR_P = 5004;
                prevalor = "MIEMBRO HOGAR";
            }
        }else if (VALIDADOR == null){
            VALIDADOR_P = 5004;
            prevalor = "MIEMBRO HOGAR";
        }

        String p = null;
        if (IDPERSONA != null)
            p = IDPERSONA.toString();
        emc_validadores_persona tmValTD = new emc_validadores_persona(IDINSTRUMENTO.toString(), p, VALIDADOR_P.toString(), prevalor.trim(), CODHOGAR, "0");
        tmValTD.save();
    }

    public static void GIC_INSERT_VALIDADOR_TD(Integer IDPERSONA, String CODHOGAR, String VALIDADOR, Integer IDINSTRUMENTO){

        Integer VALIDADOR_P = 0;

        if (VALIDADOR.trim().equals("TARJETA IDENTIDAD") || VALIDADOR.trim().equals("TI")) {
            VALIDADOR_P = 209;
        } else if (VALIDADOR.trim().equals("CEDULA EXTRANJERIA") || VALIDADOR.trim().equals("CE")) {
            VALIDADOR_P = 210;
        }else if(VALIDADOR.trim().equals("REGISTRO CIVIL DE NACIMIENTO") || VALIDADOR.trim().equals("RC") || VALIDADOR.trim().equals("RCN") ){
            VALIDADOR_P = 211;
        }else if (VALIDADOR.trim().equals("NUIP")){
            VALIDADOR_P = 212;
        }else if (  VALIDADOR.trim().equals("PASAPORTE") || VALIDADOR.trim().equals("PA") || VALIDADOR.trim().equals("PB")){
            VALIDADOR_P = 213;
        }else if (VALIDADOR.trim().equals("LIBRETA MILITAR") || VALIDADOR.trim().equals("LM")){
            VALIDADOR_P = 214;
        }else if (VALIDADOR.trim().equals("NIT") || VALIDADOR.trim().equals("NI") ){
            VALIDADOR_P = 215;
        }else if (VALIDADOR.trim().equals("INDOCUMENTADO")){
            VALIDADOR_P = 216;
        }else if (VALIDADOR.trim().equals("NO SABE")){
            VALIDADOR_P = 217;
        }else if (VALIDADOR.trim().equals("CEDULA CIUDADANIA") || VALIDADOR.trim().equals("CC")){
            VALIDADOR_P = 218;
        }else if (VALIDADOR.trim().equals("IND")){
            VALIDADOR_P = 219;
        }

        String p = null;
        if (IDPERSONA != null)
            p = IDPERSONA.toString();
        emc_validadores_persona tmValTD = new emc_validadores_persona(IDINSTRUMENTO.toString(), p, VALIDADOR_P.toString(), VALIDADOR.trim(), CODHOGAR, "0");
        tmValTD.save();
    }

    public static void GIC_INSERT_VALIDADOR_HOGAR(Integer IDPERSONA, String CODHOGAR, String VALIDADOR, Integer IDINSTRUMENTO) {
        //INSERTA EL VALIDADOR DEL ESTADO

        Integer VALIDADOR_P = 0;
        if (VALIDADOR != null) {
            if (VALIDADOR.trim().toUpperCase().equals("INCLUIDO")) {
                VALIDADOR_P = 1;
            } else if (VALIDADOR.trim().toUpperCase().equals("NO INCLUIDO")) {
                VALIDADOR_P = 1;
            }
        }

        String p = null;
        if (IDPERSONA != null)
            p = IDPERSONA.toString();
        emc_validadores_persona tmValPer = new emc_validadores_persona(IDINSTRUMENTO.toString(), p, VALIDADOR_P.toString(), VALIDADOR.trim(), CODHOGAR, "0");
        tmValPer.save();

    }


    public static void GIC_INSERT_VALIDADOR_PARENT(Integer IDPERSONA, String CODHOGAR, String VALIDADOR, Integer IDINSTRUMENTO) {
        Integer VALIDADOR_P = 0;

        if (VALIDADOR.trim().equals("JEFE")) {
            VALIDADOR_P = 20;
        } else if (VALIDADOR.trim().equals("NO JEFE")) {
            VALIDADOR_P = 21;
        }

        String p = null;
        if (IDPERSONA != null)
            p = IDPERSONA.toString();
        emc_validadores_persona tmValPer = new emc_validadores_persona(IDINSTRUMENTO.toString(), p, VALIDADOR_P.toString(), VALIDADOR.trim(), CODHOGAR, "0");
        tmValPer.save();
    }


    public static void GIC_INSERT_VALIDADOR_HECHO_AUX(Integer IDPERSONA, String CODHOGAR, Integer ID_HECHO, Integer IDINSTRUMENTO) {

        //INSERTA EL VALIDADOR HECHO
        Integer VALIDADOR_P = 0;
        String VALIDADOR = "";

        /* MODIFICACION: JOSE VASQUEZ: 05.NOV.2015
             DESCRIPCION: SE HOMOLOGA LAS DESCRIPCIONES LARGAS DE LOS HECHOS VS EL CODIGO NUMERICO QUE VIENE DE FRONTEND
            1	Acto terrorista / Atentados / Combates / Enfrentamientos / Hostigamientos
            2	Amenaza
            3	Delitos contra la libertad y la integridad sexual en desarrollo del conflicto armado
            4	DesapariciÃ³n forzada
            5	Desplazamiento forzado
            6	Homicidio
            7	Minas Antipersonal, MuniciÃ³n sin Explotar y Artefacto Explosivo improvisado
            8	Secuestro
            9	Tortura
            10	VinculaciÃ³n de NiÃ±os NiÃ±as y Adolescentes a Actividades Relacionadas con grupos armados
            11	Abandono o Despojo Forzado de Tierras
            12	Perdida de Bienes Muebles o Inmuebles
            13	Otros
            14	Sin informacion
          */

        if (ID_HECHO == 1) {
            VALIDADOR = "Acto terrorista / Atentados / Combates / Enfrentamientos / Hostigamientos";
            VALIDADOR_P = 101;
        } else if (ID_HECHO == 2) {
            VALIDADOR = "Amenaza";
            VALIDADOR_P = 102;
        } else if (ID_HECHO == 3) {
            VALIDADOR = "Delitos contra la libertad y la integridad sexual en desarrollo del conflicto armado";
            VALIDADOR_P = 103;
        } else if (ID_HECHO == 4) {
            VALIDADOR = "Desaparición forzada";
            VALIDADOR_P = 104;
        } else if (ID_HECHO == 5) {
            VALIDADOR = "Desplazamiento forzado";
            VALIDADOR_P = 105;
        } else if (ID_HECHO == 6) {
            VALIDADOR = "Homicidio";
            VALIDADOR_P = 106;
        } else if (ID_HECHO == 7) {
            VALIDADOR = "Minas Antipersonal, Munición sin Explotar y Artefacto Explosivo improvisado";
            VALIDADOR_P = 107;
        } else if (ID_HECHO == 8) {
            VALIDADOR = "Secuestro";
            VALIDADOR_P = 108;
        } else if (ID_HECHO == 9) {
            VALIDADOR = "Tortura";
            VALIDADOR_P = 109;
        } else if (ID_HECHO == 10) {
            VALIDADOR = "Vinculación de Niños Niñas y Adolescentes a Actividades Relacionadas con grupos armados";
            VALIDADOR_P = 110;
        } else if (ID_HECHO == 11) {
            VALIDADOR = "Abandono o Despojo Forzado de Tierras";
            VALIDADOR_P = 111;
        } else if (ID_HECHO == 12) {
            VALIDADOR = "Perdida de Bienes Muebles o Inmuebles";
            VALIDADOR_P = 112;
        } else if (ID_HECHO == 13) {
            VALIDADOR = "Otros";
            VALIDADOR_P = 113;
        } else if (ID_HECHO == 14) {
            VALIDADOR = "Sin informacion";
            VALIDADOR_P = 114;
        }

        if (VALIDADOR_P != 0) {
            String p = null;
            if (IDPERSONA != null)
                p = IDPERSONA.toString();

            emc_validadores_persona tmValPer = new emc_validadores_persona(IDINSTRUMENTO.toString(), p, VALIDADOR_P.toString(), VALIDADOR.trim().toUpperCase(), CODHOGAR, "0");
            tmValPer.save();
        }
    }

    public static Integer FN_COMPROBARVALIDACIONESRESP(Integer pPER_IDPERSONA, Integer pRES_IDRESPUESTA, String pHOG_CODIGO, Integer pINS_IDINSTRUMENTO) {
        Integer Result = 0;
        Integer EXISTE;
        Integer CONT = 0;
        Integer TIENE_VAL;
        String valor = "";
        Integer cValor = 0;
        Integer TIENE_PRE = 0;
        String RES_VAL;
        emc_validadores_persona tmValPer = null;

        //Busca si tiene valor en el campo pre_depende
        List<emc_preguntas_instrumento> lsResp = emc_preguntas_instrumento.find(emc_preguntas_instrumento.class,
                "PREIDPREGUNTA IN (SElECT T.PREIDPREGUNTA FROM EMCRESPUESTAS T WHERE T.RESIDRESPUESTA = ?)", pRES_IDRESPUESTA.toString());

        for (int conP = 0; conP < lsResp.size(); conP++) {
            emc_preguntas_instrumento tmPI = lsResp.get(conP);
            if (tmPI.getPre_depende() != null)
                TIENE_PRE += Integer.valueOf(tmPI.getPre_depende());
        }

        //Busca los validadores para la pregunta
        String[] parVRI = {pRES_IDRESPUESTA.toString(), pINS_IDINSTRUMENTO.toString()};
        List<emc_validadores_respuesta_instrumento> lsVRI = emc_validadores_respuesta_instrumento.find(emc_validadores_respuesta_instrumento.class,
                "RESIDRESPUESTA IN (?) AND INSIDINSTRUMENTO = ?", parVRI);
        TIENE_VAL = lsVRI.size();

        if (TIENE_VAL > 0) {
            //BUSQUEDA VALIDADORES PARA LA RESPUESTA
            String[] parRes = {pRES_IDRESPUESTA.toString(), pINS_IDINSTRUMENTO.toString()};
            List<emc_validadores_respuesta_instrumento> lsValResIns = emc_validadores_respuesta_instrumento.find(emc_validadores_respuesta_instrumento.class,
                    " RESIDRESPUESTA IN (?) AND INSIDINSTRUMENTO = ?",parRes);

            for (int conRI = 0; conRI < lsValResIns.size(); conRI++) {
                emc_validadores_respuesta_instrumento tmVRI = lsValResIns.get(conRI);

                //VERIFICA SI LA PERSONA TIENE VALOR PARA ESTE VALIDADOR
                cValor = 0;

                String[] parValPer = {pPER_IDPERSONA.toString(), tmVRI.getVAL_IDVALIDADOR_PERS().toString(), pHOG_CODIGO};
                List<emc_validadores_persona> lsValPer = emc_validadores_persona.find(emc_validadores_persona.class,
                        "PERIDPERSONA = ? AND VALIDVALIDADOR = ? AND HOGCODIGO = ?", parValPer);

                for (int conVP = 0; conVP < lsValPer.size(); conVP++) {
                    tmValPer = lsValPer.get(conVP);
                    if (tmValPer.getPre_valor() != null) {
                        cValor++;
                        valor = tmValPer.getPre_valor();
                    }
                }

                if(!valor.equals("") && valor != null)
                {
                    valor = "1";
                }
                else
                {
                    valor = "0";
                }

                if (Integer.parseInt(valor) > 0 /*&& TIENE_PRE > 0*/) {
                    //BUSCA EL VALOR DEL VALIDADOR DE LA PERSONA
                    valor = tmValPer.getPre_valor();
                    //--verIFica si la persona cumple con la validacion 1 si cumple, 0 no cumple
                    EXISTE = FN_VALIDARPERSONA(valor, tmVRI.getVAL_IDVALIDADOR().toString());
                } else {
                    if(TIENE_PRE > 0)
                    {
                        EXISTE = FN_VALIDARPERSONA(valor, tmVRI.getVAL_IDVALIDADOR().toString());
                    }else{
                        EXISTE = 1;
                    }

                }

                if (EXISTE == 1)
                {
                    CONT++;
                }

                if (TIENE_VAL == CONT) {
                    Result = 1;
                } else {
                    Result = 0;
                }
            }
        } else {
            Result = 1;
        }

        return Result;
    }

    public static List<emc_respuestas_instrumento> SP_GET_RESPUESTASXPREMOD(Integer pPRE_IDPREGUNTA, Integer pINS_IDINSTRUMENTO, String pHOG_CODIGO, Integer pPER_IDPERSONA, Integer pIDTEMA, Integer pID_JEFE) {
        //TRAE LAS RESPUESTAS A LA PREGUNTA
        Integer pPRE_GENERAL;
        Integer pMULTIPLE;
        Integer pRESPUESTAAN;
        String pRESPUESTAS;
        String pDATOVALIDADOR;
        String pOPCIONESMUL;
        String pCONSULTA;
        List<emc_respuestas_instrumento> lsRespuestas;

        lsRespuestas = new ArrayList<>();

        //--Obtiene el id tema de la pregunta
        String pSQL = "SELECT MAX(PRE.TEMIDTEMA) FROM " +
                "EMCPREGUNTASINSTRUMENTO PRE WHERE PRE.PREIDPREGUNTA = " + pPRE_IDPREGUNTA;
        pIDTEMA = execCalculos(pSQL);

        //--ModIFicacion JAIME LOBATON, PREGUNTAS GENERALES
        String pCSQLIDJefe = "SELECT COUNT(T.PERIDPERSONA) FROM " +
                "EMCMIEMBROSHOGAR T WHERE T.HOGCODIGO = '" + pHOG_CODIGO + "' AND T.INDJEFE = 'SI'; ";
        pID_JEFE = execCalculos(pCSQLIDJefe);

        // --OBTIENE ID PERSONA DEL JEFE DE HOGAR
        if(pID_JEFE > 0)
        {
            List<emc_miembros_hogar> LspID_JEFE = emc_miembros_hogar.find(emc_miembros_hogar.class,
                    " HOGCODIGO = ? AND INDJEFE = 'SI'; ",pHOG_CODIGO);

            emc_miembros_hogar tmspID_JEFE = LspID_JEFE.get(0);
            pID_JEFE = Integer.parseInt(tmspID_JEFE.getPer_idpersona());

            if(pPER_IDPERSONA != null)
            {
                pID_JEFE = pPER_IDPERSONA;
            }

        }
        //--VERFIFICA SI LA RESPUESTA ANTERIOR , MUESTRA OPCIONES DE RESPUESTA PREDEFINIDAS
        String pMulQ = "SELECT COUNT(*) FROM " +
                "EMCPREGUNTASINSTRUMENTO PRE WHERE PRE.VALRESPUESTAMULTIPLE  <> 'null' " +
                "AND PRE.PREIDPREGUNTA = " + pPRE_IDPREGUNTA;
        pMULTIPLE = execCalculos(pMulQ);

        //SI pMULTIPLE ES MAYOR A 0 ENTONCES OBTIENE EL VALOR DE VAL_RESPUESTAMULTIPLE
        if(pMULTIPLE > 0)
        {
            String vMulQ = "SELECT VALRESPUESTAMULTIPLE FROM " +
                    "EMCPREGUNTASINSTRUMENTO PRE WHERE PRE.VALRESPUESTAMULTIPLE  <> 'null' " +
                    "AND PRE.PREIDPREGUNTA = " + pPRE_IDPREGUNTA;
            pMULTIPLE = execCalculos(vMulQ);
        }

        String pGenq = "SELECT COUNT(T.VALPREGUNTAGENERAL) " +
                "FROM EMCPREGUNTASINSTRUMENTO T " +
                "WHERE T.PREIDPREGUNTA = " + pPRE_IDPREGUNTA +
                " AND T.VALPREGUNTAGENERAL  <> 'null'";
        pPRE_GENERAL = execCalculos(pGenq);

        if (pPRE_GENERAL == 0 && pMULTIPLE == 0) {

            String[] parResIns = {pINS_IDINSTRUMENTO.toString(), pPRE_IDPREGUNTA.toString()};
            List<emc_respuestas_instrumento> lsResIns = emc_respuestas_instrumento.find(emc_respuestas_instrumento.class,
                    "INSIDINSTRUMENTO = ? AND RESIDRESPUESTA IN (SELECT r.RESIDRESPUESTA FROM EMCRESPUESTAS r WHERE r.PREIDPREGUNTA = ? AND r.RESACTIVA = 'SI' )", parResIns);
            for (int conR = 0; conR < lsResIns.size(); conR++) {
                emc_respuestas_instrumento tmRes = lsResIns.get(conR);
                if (FN_COMPROBARVALIDACIONESRESP(pID_JEFE, Integer.valueOf(tmRes.getRes_idrespuesta()), pHOG_CODIGO, pINS_IDINSTRUMENTO) == 1)
                    ;
                lsRespuestas.add(tmRes);
            }
        } else if (pPRE_GENERAL > 0) {
            lsRespuestas = SP_GET_RESPUESTASVALXCONTEO(pPRE_IDPREGUNTA, pINS_IDINSTRUMENTO, pHOG_CODIGO, pID_JEFE);
        } else if (pMULTIPLE == 1) {

            /*--LA OPCION 1 INDICA QUE  SE DEBEN MOSTRAR LAS RESPUESTAS CONFIGURADAS ESTA CONDICION SE APLICA A LA
            --ULTIMA RESPUESTA SELECCIONADA*/

            String pRESAq = "SELECT * FROM (SELECT  MAX(CAST(RE.RESIDRESPUESTA AS INTEGER)) RESIDRESPUESTA  " +
                    "FROM EMCRESPUESTASENCUESTA RE " +
                    "JOIN EMCRESPUESTAS R ON (R.RESIDRESPUESTA = RE.RESIDRESPUESTA) " +
                    "JOIN EMCPREGUNTASINSTRUMENTO PR ON (PR.PREIDPREGUNTA = R.PREIDPREGUNTA) " +
                    "WHERE RE.USUFECHACREACION = (SELECT MAX(USUFECHACREACION) " +
                    "FROM EMCRESPUESTASENCUESTA " +
                    "WHERE HOGCODIGO = '" + pHOG_CODIGO + "') " +
                    "AND PR.TEMIDTEMA = " + pIDTEMA + " AND RE.HOGCODIGO = '" + pHOG_CODIGO + "')";

            //genera error
            pRESPUESTAAN = execCalculos(pRESAq);

            List<emc_respuestas_instrumento> lsResMul = emc_respuestas_instrumento.find(emc_respuestas_instrumento.class,
                    "RESIDRESPUESTA = ?", pRESPUESTAAN.toString());
            pOPCIONESMUL = lsResMul.get(0).getRes_respuestashbilitar();


            if (pOPCIONESMUL != null) {

                String[] par4 = {pPRE_IDPREGUNTA.toString(), pOPCIONESMUL};

                //genera error esta consulta
                /*lsRespuestas = emc_respuestas_instrumento.find(emc_respuestas_instrumento.class,
                        "PREIDPREGUNTA = ? AND RESIDRESPUESTA IN (?)", par4);*/
                lsRespuestas = emc_respuestas_instrumento.find(emc_respuestas_instrumento.class,
                        "RESIDRESPUESTA " +
                                "IN " +
                                "(SELECT RESIDRESPUESTA FROM EMCRESPUESTAS WHERE RESACTIVA='SI' AND PREIDPREGUNTA IN" +
                                "(SELECT PREIDPREGUNTA FROM EMCPREGUNTASINSTRUMENTO WHERE PREIDPREGUNTA ="+pPRE_IDPREGUNTA.toString() +
                                ") AND RESIDRESPUESTA IN "+pOPCIONESMUL+");",null);


            } else {
                lsRespuestas = emc_respuestas_instrumento.find(emc_respuestas_instrumento.class,
                        "RESIDRESPUESTA IN (SELECT r.RESIDRESPUESTA FROM EMCRESPUESTAS r WHERE r.PREIDPREGUNTA = ? AND r.RESACTIVA = 'SI')", pPRE_IDPREGUNTA.toString());
            }
        }

        else if (pMULTIPLE == 2) {
            /* --LA OPCION 2 INDICA QUE NO SE DEBEN MOSTRAR LAS RESPUESTAS CONFIGURADAS, ESTA CONDICION SE APLICA A LA
               --ULTIMA RESPUESTA SELECCIONADA*/

            String pRESPUESTASN = SP_GET_RESPUESTANO(pPRE_IDPREGUNTA, pINS_IDINSTRUMENTO, pIDTEMA, pHOG_CODIGO.toString());

            if (pRESPUESTASN == null || pRESPUESTASN.equals("")) {

                lsRespuestas = emc_respuestas_instrumento.find(emc_respuestas_instrumento.class,
                        "RESIDRESPUESTA " +
                                "IN " +
                                "(SELECT RESIDRESPUESTA FROM EMCRESPUESTAS WHERE RESACTIVA='SI' AND PREIDPREGUNTA IN" +
                                "(SELECT PREIDPREGUNTA FROM EMCPREGUNTASINSTRUMENTO WHERE PREIDPREGUNTA =" + pPRE_IDPREGUNTA.toString() + ") )",null);

            } else {

                //String[] par4 = {pPRE_IDPREGUNTA.toString(), pRESPUESTASN};
                lsRespuestas = emc_respuestas_instrumento.find(emc_respuestas_instrumento.class,
                        "RESIDRESPUESTA " +
                                "IN " +
                                "(SELECT RESIDRESPUESTA FROM EMCRESPUESTAS WHERE RESACTIVA='SI' AND PREIDPREGUNTA IN" +
                                "(SELECT PREIDPREGUNTA FROM EMCPREGUNTASINSTRUMENTO WHERE PREIDPREGUNTA ="+pPRE_IDPREGUNTA.toString() +
                                ") AND RESIDRESPUESTA NOT IN " + pRESPUESTASN + ")",null);
            }
            /*--ESTA OPCION INDICA QUE SE DEBEN MOSTRAR LAS RESPUESTAS CONFIGURADAS EN LA COLUMNA RES_RESPUESTAHABILITAR
            --DE LA TABLA GIC_N_INSTRUMENTOXRESP, DADO EL IDRESPUESTA EN LA COLUMNA VAL_RESPUESTAMULTIPLE DE LA TABLA
            --GIC_N_INSTRUMENTOXPREG*/
        }else if(pMULTIPLE > 2) {
            /*SELECT R.VALRESPUESTAMULTIPLE
            FROM  EMCPREGUNTASINSTRUMENTO R
            WHERE R.PREIDPREGUNTA=50
            AND R.VALRESPUESTAMULTIPLE IS NOT NULL;}*/

            String[] sPar = {pPRE_IDPREGUNTA.toString()};
            List<emc_preguntas_instrumento> lsPIN = emc_preguntas_instrumento.find(emc_preguntas_instrumento.class,
                    " PREIDPREGUNTA = ? AND VALRESPUESTAMULTIPLE IS NOT NULL ;", sPar);

            emc_preguntas_instrumento tmPIN = lsPIN.get(0);
            pDATOVALIDADOR = tmPIN.getVal_respuesta_multiple();

            //SELECT COUNT(*) INTO pCANTIDAD FROM GIC_N_RESPUESTASENCUESTA R WHERE R.RES_IDRESPUESTA = /*pSALIDA*/pDATOVALIDADOR
            //AND R.HOG_CODIGO = pHOG_CODIGO;*/
            String[] sParm = {pDATOVALIDADOR,pHOG_CODIGO};
            List<emc_respuestas_encuesta> LsRE = emc_respuestas_encuesta.find(emc_respuestas_encuesta.class,
                    "RESIDRESPUESTA = ? AND HOGCODIGO = ?",sParm);

            //emc_respuestas_encuesta tmRE = LsRE.get(0);
            int pCantidad = LsRE.size();

            if(pCantidad >0)
            {
                /*SELECT T.RES_RESPUESTASHABILITAR INTO pRESPUESTAS
                FROM GIC_N_INSTRUMENTOXRESP T   WHERE T.RES_IDRESPUESTA IN pDATOVALIDADOR;*/

                String[] sParmE = {pDATOVALIDADOR};
                List<emc_respuestas_instrumento> LsRI = emc_respuestas_instrumento.find(emc_respuestas_instrumento.class,
                        "RESIDRESPUESTA IN ("+pDATOVALIDADOR+ ");",null/*sParmE*/);

                emc_respuestas_instrumento tmRI = LsRI.get(0);
                pRESPUESTAS = tmRI.getRes_respuestashbilitar();

                /*
                pCONSULTA := ' SELECT RI.INS_IDINSTRUMENTO, RI.RES_IDRESPUESTA, RE.RES_RESPUESTA, RI.PRE_VALIDADOR, RI.PRE_LONGCAMPO, RE.PRE_IDPREGUNTA,
                RI.PRE_VALIDADOR_MIN, RI.PRE_VALIDADOR_MAX, RI.RES_ORDENRESPUESTA, RI.PRE_CAMPOTEX, RI.RES_OBLIGATORIO, RI.RES_HABILITA, RI.RES_FINALIZA, RI.RES_AUTOCOMPLETAR
                FROM GIC_N_INSTRUMENTOXRESP RI
                JOIN GIC_N_RESPUESTAS RE ON RE.RES_IDRESPUESTA=RI.RES_IDRESPUESTA AND RE.RES_ACTIVA=''SI''
                JOIN GIC_N_INSTRUMENTOXPREG PRE ON PRE.PRE_IDPREGUNTA=RE.PRE_IDPREGUNTA
                WHERE RE.PRE_IDPREGUNTA='||pPRE_IDPREGUNTA||' AND RE.RES_IDRESPUESTA IN '|| pRESPUESTAS || '';
                OPEN cur_OUT FOR pCONSULTA;*/
                lsRespuestas = emc_respuestas_instrumento.find(emc_respuestas_instrumento.class,
                        " RESIDRESPUESTA IN " +
                                " (SELECT RE.RESIDRESPUESTA " +
                                " FROM EMCRESPUESTAS RE WHERE RE.RESACTIVA = 'SI' " +
                                " AND RE.PREIDPREGUNTA IN ( SELECT PRE.PREIDPREGUNTA FROM EMCPREGUNTASINSTRUMENTO PRE WHERE PREIDPREGUNTA = '"+ pPRE_IDPREGUNTA.toString()+"')" +
                                " AND RE.RESIDRESPUESTA IN "+pRESPUESTAS+" " +
                                " );", null);
            }else
            {
                /*
                       pCONSULTA := ' SELECT RI.INS_IDINSTRUMENTO, RI.RES_IDRESPUESTA, RE.RES_RESPUESTA, RI.PRE_VALIDADOR, RI.PRE_LONGCAMPO, RE.PRE_IDPREGUNTA,
                                      RI.PRE_VALIDADOR_MIN, RI.PRE_VALIDADOR_MAX, RI.RES_ORDENRESPUESTA, RI.PRE_CAMPOTEX, RI.RES_OBLIGATORIO, RI.RES_HABILITA, RI.RES_FINALIZA, RI.RES_AUTOCOMPLETAR
                                      FROM GIC_N_INSTRUMENTOXRESP RI
                                      JOIN GIC_N_RESPUESTAS RE ON RE.RES_IDRESPUESTA=RI.RES_IDRESPUESTA AND RE.RES_ACTIVA=''SI''
                                      JOIN GIC_N_INSTRUMENTOXPREG PRE ON PRE.PRE_IDPREGUNTA=RE.PRE_IDPREGUNTA
                                      WHERE RE.PRE_IDPREGUNTA='||pPRE_IDPREGUNTA;
                                      OPEN cur_OUT FOR pCONSULTA;
                 */
                lsRespuestas = emc_respuestas_instrumento.find(emc_respuestas_instrumento.class,
                        " RESIDRESPUESTA IN " +
                                " (SELECT RE.RESIDRESPUESTA " +
                                " FROM EMCRESPUESTAS RE WHERE RE.RESACTIVA = 'SI' " +
                                " AND RE.PREIDPREGUNTA IN ( SELECT PRE.PREIDPREGUNTA FROM EMCPREGUNTASINSTRUMENTO PRE WHERE PREIDPREGUNTA = '"+ pPRE_IDPREGUNTA.toString()+"')" +
                                " );", null);
            }

        }

        return lsRespuestas;
    }


    //actualizado el 08/11/2016
    public static String SP_GET_RESPUESTANO(Integer pPRE_IDPREGUNTA, Integer pINS_IDINSTRUMENTO, Integer pIDTEMA, String pHOG_CODIGO) {
        String pRESPUESTASN = "";
        String pOPCIONESMUL = null;


        String[] sPar = {pHOG_CODIGO,pIDTEMA.toString(),pHOG_CODIGO};
        /*List<emc_respuestas_encuesta> lsRespAnio = emc_respuestas_encuesta.find(emc_respuestas_encuesta.class,
                "SELECT  RE.RESIDRESPUESTA " +
                        "          FROM EMCRESPUESTASENCUESTA RE " +
                        "          JOIN EMCRESPUESTAS R ON R.RESIDRESPUESTA = RE.RESIDRESPUESTA " +
                        "          JOIN EMCPREGUNTASINSTRUMENTO PR ON PR.PREIDPREGUNTA = R.PREIDPREGUNTA " +
                        "          WHERE RE.usufechacreacion=(select max(usufechacreacion) " +
                        "          FROM EMCRESPUESTASENCUESTA WHERE  HOGCODIGO= ? )  " +
                        "          AND  PR.TEMIDTEMA = ? AND RE.HOGCODIGO= ? ", sPar);*/

        List<emc_respuestas_encuesta> lsRespAnio = emc_respuestas_encuesta.find(emc_respuestas_encuesta.class,
                "RESIDRESPUESTA IN (SELECT RE.RESIDRESPUESTA FROM EMCRESPUESTASENCUESTA RE, EMCRESPUESTAS R, EMCPREGUNTASINSTRUMENTO PR\n" +
                        "WHERE R.RESIDRESPUESTA=RE.RESIDRESPUESTA AND PR.PREIDPREGUNTA=R.PREIDPREGUNTA AND\n" +
                        "RE.USUFECHACREACION = (SELECT MAX(USUFECHACREACION) FROM EMCRESPUESTASENCUESTA WHERE HOGCODIGO = ? )\n" +
                        "AND PR.TEMIDTEMA = ? AND RE.HOGCODIGO = ? )", sPar);


        for (int contR = 0; contR < lsRespAnio.size(); contR++) {
            emc_respuestas_encuesta tmResp = lsRespAnio.get(contR);
            List<emc_respuestas_instrumento> lsRepIns = emc_respuestas_instrumento.find(emc_respuestas_instrumento.class, "RESIDRESPUESTA = " + tmResp.getRes_idrespuesta(), null);
            if (lsRepIns.size() > 0) {
                pOPCIONESMUL = lsRepIns.get(0).getRes_respuestashbilitar();
            }

            if (pOPCIONESMUL == null) {
                pRESPUESTASN = pRESPUESTASN;
            } else if (pOPCIONESMUL != null) {
                pRESPUESTASN = pRESPUESTASN + pOPCIONESMUL + ",";
                //pRESPUESTASN = pRESPUESTASN + "," + pOPCIONESMUL;

            }
        }
        if (pRESPUESTASN != null ) {
            if(!pRESPUESTASN.equals("")){
                pRESPUESTASN = "(" + pRESPUESTASN.substring(0, pRESPUESTASN.length()-1) + ")";
            }
        }

        return pRESPUESTASN;
    }


    public static List<emc_respuestas_instrumento> SP_GET_RESPUESTASVALXCONTEO(Integer pPRE_IDPREGUNTA, Integer pINS_IDINSTRUMENTO, String pHOG_CODIGO, Integer pPER_IDPERSONA) {
        //TRAE LAS RESPUESTAS A LA PREGUNTA Y VALIDA QUE EL NUMERO DE MIEMBROS DEL HOGAR
        //CUMPLA CON EL CONTEO DE LOS VALIDADORES POR PERSONA CON ESTA
        List<emc_respuestas_instrumento> lsRespuesta = new ArrayList<>();
        Integer pRE_IDVALIDADOR;
        Integer pCONTEOVAL;
        Integer pCONTEOHOGAR;
        String pFILTRO;
        String pDIFERENCIADO = "(0)";
        Integer pCONTEOFILTRO;
        String pCONSULTA;
        Integer pCANTIDAD = 0;
        String pSALIDA = "";
        Integer pX = 0;
        String pDATOVALIDADOR = "";
        Integer pVAL_IDVALIDADOR = 0;
        Integer VP = 0;
        Integer VD = 0;
        String pDEFAULT = "";

        VP = 2;

        String pREIDq = "SELECT COUNT(*) " +
                "FROM  EMCPREGUNTASINSTRUMENTO R " +
                "WHERE R.PREIDPREGUNTA = " + pPRE_IDPREGUNTA +
                " AND R.VALPREGUNTAGENERAL IS NOT NULL";
        pRE_IDVALIDADOR = execCalculos(pREIDq);


        if (pRE_IDVALIDADOR > 0) {

            //asigna validador para realizar conteo y compara con numeros de miembros total del hogar
            String[] sPar = {String.valueOf(pPRE_IDPREGUNTA).toString()};
            List<emc_preguntas_instrumento> LspDATOVALIDADOR = emc_preguntas_instrumento.find(emc_preguntas_instrumento.class,
                    "PREIDPREGUNTA = ? AND VALPREGUNTAGENERAL IS NOT NULL;",sPar);

            emc_preguntas_instrumento objPI = LspDATOVALIDADOR.get(0);
            pDATOVALIDADOR = objPI.getVal_pregunta_general();

            //--INICIO PRUEBA ANDRES
            /*SELECT length(VAL_PREGUNTA_GENERAL) - length(REPLACE(VAL_PREGUNTA_GENERAL,','))
            INTO   pCANTIDAD  FROM GIC_N_INSTRUMENTOXPREG WHERE PRE_IDPREGUNTA =pPRE_IDPREGUNTA;*/

            String[] sParP = {pPRE_IDPREGUNTA.toString()};
            List<emc_preguntas_instrumento> lsvVal_pregunta_General = emc_preguntas_instrumento.find(emc_preguntas_instrumento.class,
                    " PREIDPREGUNTA  = ?;", sParP);

            emc_preguntas_instrumento tmResp = lsvVal_pregunta_General.get(0);

            pCANTIDAD = pCANTIDAD(tmResp.getVal_pregunta_general().toString());

            for (int VARIABLECONTADOR = 0; VARIABLECONTADOR < pCANTIDAD; VARIABLECONTADOR++ )
            {
                pSALIDA =GetToken(pDATOVALIDADOR,VARIABLECONTADOR+1,",");
                String[] sParVE = {pSALIDA,pPRE_IDPREGUNTA.toString()};
                /*SELECT P.EXPRESION INTO pX FROM GIC_N_VALIDADORXEXPRESION  P WHERE P.VALOR=pSALIDA
                AND P.PRE_IDPREGUNTA=pPRE_IDPREGUNTA;*/
                List<emc_validador_expresion> lsPX = emc_validador_expresion.find(emc_validador_expresion.class,
                        " VALOR  = ? AND PREIDPREGUNTA = ?;", sParVE);

                emc_validador_expresion PXExpresion = lsPX.get(0);
                pX = Integer.parseInt(PXExpresion.getExpresion());
                //--CONTEO VALIDADOR ENCONTRADO

                String squrey = "SELECT COUNT(T.VALIDVALIDADOR)  FROM EMCVALIDADORESPERSONA T " +
                        "WHERE T.HOGCODIGO = '"+pHOG_CODIGO +"' "+"AND T.VALIDVALIDADOR = '"+pSALIDA+"';";

                //pVAL_IDVALIDADOR = emc_calculos.executeQuery("INSERT INTO EMCCALCULOS(VALOR) " + squrey );
                pVAL_IDVALIDADOR = execCalculos(squrey);

                //--CONTEO DE EL NUMERO DE  MIENBROS DEL HOGAR
                List<emc_miembros_hogar> LsMHG = emc_miembros_hogar.find(emc_miembros_hogar.class,
                        " HOGCODIGO = '"+ pHOG_CODIGO +"'",null);

                pCONTEOHOGAR = LsMHG.size();

                //--CONDICION PARA VERIFICAR EL VALOR DE LA TABLA ENCONTRADA
                //-- 0 VERIFICA QUE EL VALIDADOR SE CUMPLA PARA TODOS LOS MIEMBORS DEL HOGAR

                if(pX==0)
                {
                    if ( pVAL_IDVALIDADOR ==  pCONTEOHOGAR  )
                    {
                        /*SELECT regexp_substr(G.VAL_DIFERENCIADONU,'\((\d+,)+(\d+\))',instr(G.VAL_DIFERENCIADONU,pSALIDA)+LENGTH(pSALIDA))
                        INTO pDIFERENCIADO FROM GIC_N_INSTRUMENTOXPREG g WHERE PRE_IDPREGUNTA = pPRE_IDPREGUNTA;*/
                        List<emc_preguntas_instrumento> LsPGI = emc_preguntas_instrumento.find(emc_preguntas_instrumento.class,
                                " PREIDPREGUNTA = '"+ pPRE_IDPREGUNTA.toString() +"'",null);

                        emc_preguntas_instrumento obPI = LsPGI.get(0);
                        String VAL_DIFERENCIADONU = obPI.getVal_diferenciadonu();
                        pDIFERENCIADO = buscarConcidencia(VAL_DIFERENCIADONU,pSALIDA,"(",")");
                        VP = 1;
                        break;
                    }
                //-- 1 VERIFICA QUE EL VALIDADROSE CUMPLA PARA UN SOLO MIEMBORS DEL HOGAR
                }else if(pX==1)
                {
                    if(pVAL_IDVALIDADOR > 0)
                    {
                        List<emc_preguntas_instrumento> LsPGI = emc_preguntas_instrumento.find(emc_preguntas_instrumento.class,
                                " PREIDPREGUNTA = '"+ pPRE_IDPREGUNTA.toString() +"'",null);

                        emc_preguntas_instrumento obPI = LsPGI.get(0);
                        String VAL_DIFERENCIADONU = obPI.getVal_diferenciadonu();
                        pDIFERENCIADO = buscarConcidencia(VAL_DIFERENCIADONU,pSALIDA,"(",")");
                        VP = 1;
                        break;
                    }
                }
                else
                {

                    if(pVAL_IDVALIDADOR > 0) {
                        List<emc_preguntas_instrumento> LsPGI = emc_preguntas_instrumento.find(emc_preguntas_instrumento.class,
                                " PREIDPREGUNTA = '" + pPRE_IDPREGUNTA.toString() + "'", null);

                        emc_preguntas_instrumento obPI = LsPGI.get(0);
                        String VAL_DIFERENCIADONU = obPI.getVal_diferenciadonu();
                        pDIFERENCIADO = buscarConcidencia(VAL_DIFERENCIADONU, pSALIDA, "(", ")");
                    }
                    break;
                }

            }

            //SELECT COUNT(T.R_DEFAULT) INTO VD	FROM GIC_N_INSTRUMENTOXPREG T WHERE t.pre_idpregunta in (pPRE_IDPREGUNTA);
            List<emc_preguntas_instrumento> LsPGI = emc_preguntas_instrumento.find(emc_preguntas_instrumento.class,
                    " PREIDPREGUNTA = '" + pPRE_IDPREGUNTA.toString() + "'", null);
            //VD = LsPGI.size();
            emc_preguntas_instrumento obPI = LsPGI.get(0);
            if(obPI.getR_defualt() != null)
            {
                VD = 1;
            }
            if(VD > 0)
            {
                obPI = LsPGI.get(0);
                pDEFAULT = obPI.getR_defualt();
            }
            else{
                pDEFAULT = null;
            }

            if (!pDIFERENCIADO.equals("") && pVAL_IDVALIDADOR > 0)
            {
                List<emc_respuestas_instrumento> lsResIns = emc_respuestas_instrumento.find(emc_respuestas_instrumento.class,
                        " RESIDRESPUESTA IN " +
                                " (SELECT RE.RESIDRESPUESTA " +
                                " FROM EMCRESPUESTAS RE WHERE RE.RESACTIVA = 'SI' " +
                                " AND RE.PREIDPREGUNTA IN ( SELECT PRE.PREIDPREGUNTA FROM EMCPREGUNTASINSTRUMENTO PRE WHERE PREIDPREGUNTA = '"+ pPRE_IDPREGUNTA.toString()+"')" +
                                " AND RE.RESIDRESPUESTA IN "+pDIFERENCIADO+" " +
                                " );", null);

                for (int conR = 0; conR < lsResIns.size(); conR++) {
                    emc_respuestas_instrumento tmRes = lsResIns.get(conR);

                    lsRespuesta.add(tmRes);
                }


            }
            else if (VP == 2 && VD > 0)
            {
                List<emc_respuestas_instrumento> lsResIns = emc_respuestas_instrumento.find(emc_respuestas_instrumento.class,
                        " RESIDRESPUESTA IN " +
                                " (SELECT RE.RESIDRESPUESTA " +
                                " FROM EMCRESPUESTAS RE WHERE RE.RESACTIVA = 'SI' " +
                                " AND RE.PREIDPREGUNTA IN ( SELECT PRE.PREIDPREGUNTA FROM EMCPREGUNTASINSTRUMENTO PRE WHERE PREIDPREGUNTA = '"+ pPRE_IDPREGUNTA.toString()+"')" +
                                " AND RE.RESIDRESPUESTA IN "+pDEFAULT+" " +
                                " );", null);

                for (int conR = 0; conR < lsResIns.size(); conR++) {
                    emc_respuestas_instrumento tmRes = lsResIns.get(conR);

                    lsRespuesta.add(tmRes);
                }
            }
            else{

                List<emc_respuestas_instrumento> lsResIns = emc_respuestas_instrumento.find(emc_respuestas_instrumento.class,
                        " RESIDRESPUESTA IN " +
                                " (SELECT RE.RESIDRESPUESTA " +
                                " FROM EMCRESPUESTAS RE WHERE RE.RESACTIVA = 'SI' " +
                                " AND RE.PREIDPREGUNTA IN ( SELECT PRE.PREIDPREGUNTA FROM EMCPREGUNTASINSTRUMENTO PRE WHERE PREIDPREGUNTA = '"+ pPRE_IDPREGUNTA.toString()+"')" +
                                " );", null);

                for (int conR = 0; conR < lsResIns.size(); conR++) {
                    emc_respuestas_instrumento tmRes = lsResIns.get(conR);

                    if (FN_COMPROBARVALIDACIONESRESP(pPER_IDPERSONA, Integer.valueOf(tmRes.getRes_idrespuesta()), pHOG_CODIGO, pINS_IDINSTRUMENTO) == 1)
                        lsRespuesta.add(tmRes);
                }

            }

        }
        return lsRespuesta;
    }
    public static String buscarConcidencia(String cadena, String pSalida, String delimitadorinicio, String delimitadorfin)
    {
        String result = null;
        int index = cadena.indexOf(pSalida);
        int star_pos = cadena.indexOf(delimitadorinicio,index);
        System.out.println(index);
        int end_pos = cadena.indexOf(delimitadorfin, index);
        System.out.println(end_pos);
        result = cadena.substring(star_pos, end_pos+1);

        return result.trim();
    }


    public static int pCANTIDAD(String VAL_PREGUNTA_GENERAL) {
        int totalDelimitadores = 0;
        String cadena = VAL_PREGUNTA_GENERAL;
        char[] arrayChar = cadena.toCharArray();

        for (int i = 0; i < arrayChar.length; i++) {

            if (arrayChar[i] == ',') {
                totalDelimitadores++;
            }
        }

        return totalDelimitadores;
    }

    //actualizado el 08/11/2016
    public static String GetToken(String stringvalues, int indice, String delim) {
        String validador = null;

        int start_pos=0; // Posicion inicial de cada substring
        int end_pos; // Posicion final de cada substring

        // Si el primer indice es uno
        if (indice == 1) {
            start_pos = 0; // La posicion inicial sera 1
        } else {

            /* Se calcula la posicion del delimitador segun el substring que se desea conseguir  */
            /*  Ejm: 12;13;  Se desea el inidice 2 del delim ; --> start_pos=3*/

            if (indice < 4) {
                start_pos = stringvalues.indexOf(delim, indice + 1);
            } else if (indice == 4){
                start_pos = stringvalues.indexOf(delim, indice + 4);
            } else if (indice == 5){
                start_pos = stringvalues.indexOf(delim, indice + 8);
            }else if (indice == 6){
                start_pos = stringvalues.indexOf(delim, indice + 12);
            } else if (indice == 7){
                start_pos = stringvalues.indexOf(delim, indice + 16);
            } else if (indice == 8){
                start_pos = stringvalues.indexOf(delim, indice + 19);
            }else if (indice == 9){
                start_pos = stringvalues.indexOf(delim, indice + 22);
            }else if (indice == 10){
                start_pos = stringvalues.indexOf(delim, indice + 25);
            }else if (indice == 11){
                start_pos = stringvalues.indexOf(delim, indice + 28);
            }else if (indice == 12){
                start_pos = stringvalues.indexOf(delim, indice + 31);
            }else if (indice == 13){
                start_pos = stringvalues.indexOf(delim, indice + 34);
            }else if (indice == 14){
                start_pos = stringvalues.indexOf(delim, indice + 37);
            }else if (indice == 15){
                start_pos = stringvalues.indexOf(delim, indice + 40);
            }else if (indice == 16){
                start_pos = stringvalues.indexOf(delim, indice + 43);
            }else if (indice == 17){
                start_pos = stringvalues.indexOf(delim, indice + 46);
            }else if (indice == 18){
                start_pos = stringvalues.indexOf(delim, indice + 49);
            }

            // Si la posicion inicial es 0 se retorna null
            if (start_pos == 0) {
                return validador;
            } else {
                // Se calcula la posicion inicial del substring sin importar el largo del delimitador
                int pdelim = delim.length();
                start_pos = start_pos + pdelim;// length(delim);

            }
        }

        //Se calcula la posicion final del substring
        //end_pos = instr(stringvalues, delim, start_pos, 1);
        end_pos = stringvalues.indexOf(delim, start_pos);

        if (end_pos == 0) // Se retorna el ultimo valor del arreglo
        {
            validador = stringvalues.substring(0, start_pos - 1);//substr(stringvalues, start_pos);
        } else // Se retorna el valor del arreglo segun el inidice y delim indicado
        {
            validador = stringvalues.substring(start_pos, end_pos); //substr(stringvalues, start_pos, end_pos - start_pos);
        }

        return validador;
    }


    public static void SP_GET_PREGUNTAS(int pINS_IDINSTRUMENTO,String pHOG_CODIGO,int pId_Tema,int pPER_IDPERSONA,String pId_RespuestaEncuesta,int pId_PreguntaPadre,int pTipo_valres)
    {
        String pPREGUNTA = "";
        String pTodoHogar = "";
        int pValidador = 0;
        int pConteoHogar ;
        int pConteo  = 0;
        int pTipo_valfun = 0;
        int p_final = 0 ;
        String SALIDA = "";


        String[] sPar = {pId_RespuestaEncuesta.toString()};
        //CURSOR PARA OBTENER TODAS LAS PREGUNTAS CONFIGURADAS POR RESPUESTA
        //CURSOR c_pPREGUNTAS IS
        //SELECT t.PRE_IDPREGUNTA  FROM GIC_N_PREGUNTAHIJOS t
        //WHERE T.RES_IDRESPUESTA IN (pId_RespuestaEncuesta) AND T.PRE_DEPENDE='SI' ORDER BY PRE_DEPENDE DESC;
        List<emc_pregunta_hijos> c_pPREGUNTAS = emc_pregunta_hijos.find(emc_pregunta_hijos.class,
                " RESIDRESPUESTA IN (?) AND PREDEPENDE='SI' ORDER BY PREDEPENDE DESC;", sPar);


        //BEGIN
        //MARCADOR PARA VERIFICAR SALIDA DEL CURSOR CUANDO CUMPLA ALGUNA CONDICION
        p_final = 0;

        //FOR PARA RECORRER EL CURSOR c_pPREGUNTAS
        //FOR r_pPREGUNTAS IN c_pPREGUNTAS LOOP
        for (int r_pPREGUNTAS = 0; r_pPREGUNTAS < c_pPREGUNTAS.size(); r_pPREGUNTAS++) {

            //TRAE TODAS LAS PREGUNTAS QUE DEBEN CUMPLIR VALIDACIONES
            /*SELECT t.preidpregunta, t.valtodohogar into pPREGUNTA, pTodoHogar  from EMCPREGUNTAHIJOS t
            WHERE residrespuesta IN (113) AND T.PREDEPENDE='SI'
            AND T.PREIDPREGUNTA =3  order by PREDEPENDE desc;*/
            emc_pregunta_hijos tmC_preguntas = c_pPREGUNTAS.get(r_pPREGUNTAS);
            String[] sParaM = {pId_RespuestaEncuesta.toString(),tmC_preguntas.getPre_idpregunta()};
            List<emc_pregunta_hijos> lsPreguntasCV = emc_pregunta_hijos.find(emc_pregunta_hijos.class,
                    " RESIDRESPUESTA IN (?) AND PREDEPENDE='SI' AND PREIDPREGUNTA = ? ORDER BY PREDEPENDE DESC;", sParaM);


            if (p_final == 0)
            {
                //FOR CUR_DATOS IN (select DISTINCT T.PER_IDPERSONA from GIC_MIEMBROS_HOGAR t WHERE hog_codigo=pHOG_CODIGO)
                List<emc_miembros_hogar> lsMHogar = emc_miembros_hogar.find(emc_miembros_hogar.class,
                        " HOGCODIGO = "+"'"+pHOG_CODIGO+"';", null);

                for ( int CUR_DATOS = 0; CUR_DATOS < lsMHogar.size(); CUR_DATOS++  )
                {
                    //FUNCION QUE VALIDA QUE LA PREGUNTA CUMPLA CON LA REGLA CUNFIGURADA DE LA TABLA VALIDADORESXRESPUESTA

                    emc_pregunta_hijos tmVRI = c_pPREGUNTAS.get(r_pPREGUNTAS);
                    emc_miembros_hogar tmVRC = lsMHogar.get(CUR_DATOS);

                    pValidador = FN_VALIDARXRESPUESTA(pHOG_CODIGO,Integer.parseInt(tmVRI.getPre_idpregunta()),0, tmVRC.getPer_idpersona(),pINS_IDINSTRUMENTO);
                    //SI pTodoHogar ES MAYOR A 1 ENTONCES TODOS LOS MIEMBROS DEL HOGAR DEBEN RESPONDER LA MISMA RESPUESTA
                    //}
                    emc_pregunta_hijos tmPv = lsPreguntasCV.get(0);
                    pTodoHogar = tmPv.getVal_todohogar();
                    if(Integer.parseInt(pTodoHogar) > 1)
                    {
                        //--CONTEO DE MIEMBROS DE HOGAR

                        List<emc_miembros_hogar> LspConteoHogar = emc_miembros_hogar.find(emc_miembros_hogar.class,
                                " HOGCODIGO = "+"'"+pHOG_CODIGO+"';", null);
                        //SELECT COUNT(*) INTO pConteoHogar FROM GIC_MIEMBROS_HOGAR   MH WHERE MH.HOG_CODIGO=pHOG_CODIGO;
                        pConteoHogar = LspConteoHogar.size();
                        //CONTEO DE RESPUESTAS CONTESTADAS, PASANDOSE COMO PARAMETRO LA RESPUESTA CONFIGURADA, DONDE pTodoHogar
                        //ES LA RESPUESTA QUE VA A VALIDAR
                        String[] sPara = {pHOG_CODIGO, pTodoHogar, String.valueOf(pINS_IDINSTRUMENTO).toString()  };
                        List<emc_respuestas_encuesta> LsRContestadas = emc_respuestas_encuesta.find(emc_respuestas_encuesta.class,
                                " HOGCODIGO = ? AND RESIDRESPUESTA = ? AND INSIDINSTRUMENTO = ? ;", sPara);
                        /*SELECT COUNT (*) INTO pConteo
                        FROM GIC_N_RESPUESTASENCUESTA PD WHERE PD.HOG_CODIGO =pHOG_CODIGO AND PD.RES_IDRESPUESTA =pTodoHogar
                        AND PD.INS_IDINSTRUMENTO = pINS_IDINSTRUMENTO;*/

                        pConteo = LsRContestadas.size();

                        //--SI EL VALIDADOR ES 1 Y EL CONTEO DEL HOGAR ES IGUAL AL CONTEO DE RESPUESTA CONTESTADAS, TERMINA EL LOOP
                        if(pValidador == 1 && pConteoHogar == pConteo )
                        {
                            pTipo_valfun = 1;
                            p_final =  Integer.parseInt(tmVRI.getPre_idpregunta());
                        }else{
                            pTipo_valfun = 0;
                        }
                        //--SI pTodoHogar ES IGUAL A 1, ALMENOS UNA PERSONAS DEL HOGAR, DEBE CUMPLIR LA CONDICIOON
                    }else if (Integer.parseInt(pTodoHogar) == 1)
                    {
                        if(pValidador == 1){
                            pTipo_valfun = 1;
                            p_final =  Integer.parseInt(tmVRI.getPre_idpregunta());
                            break;
                        }else{
                            pTipo_valfun = 0;
                        }

                    }
                }

            }

        }
        if(pTipo_valfun == 1)
        {
            //--Borrar las preguntas derivadas de la pregunta padre
            String[] parDelVal = {pHOG_CODIGO, String.valueOf(pId_PreguntaPadre).toString() ,
                    String.valueOf(pPER_IDPERSONA).toString(), String.valueOf(pINS_IDINSTRUMENTO).toString()};
            emc_preguntas_derivadas.deleteAll(emc_preguntas_derivadas.class,
                    "HOGCODIGO = ? AND PREIDPREGUNTAPADRE = ? AND PERIDPERSONA = ? AND INSIDINSTRUMENTO = ?", parDelVal);
            //--Insertar en preguntas derivadas la pregunta
            /*INSERT INTO GIC_N_PREGUNTASDERIVADAS (hog_codigo,PRE_IDPREGUNTA,
                PER_IDPERSONA,GUARDADO,INS_IDINSTRUMENTO,TEM_IDTEMA,PRE_IDPREGUNTAPADRE)
            values(pHOG_CODIGO,p_final,pPER_IDPERSONA,0,pINS_IDINSTRUMENTO,pId_Tema,pId_PreguntaPadre);
            p_final := 1;
            COMMIT;*/
            String inSQL = "INSERT INTO EMCPREGUNTASDERIVADAS(HOGCODIGO, PREIDPREGUNTA, PERIDPERSONA, GUARDADO, " +
                    "INSIDINSTRUMENTO,TEMIDTEMA,PREIDPREGUNTAPADRE) VALUES ( " +"'" +  pHOG_CODIGO.toString() +"','" +
                    String.valueOf(p_final).toString() + "','" + String.valueOf(pPER_IDPERSONA).toString()+"','"+
                    String.valueOf(0).toString()+"','"+String.valueOf(pINS_IDINSTRUMENTO).toString()+"',"+pId_Tema+
                    ","+pId_PreguntaPadre+")";

            emc_preguntas_derivadas.executeQuery(inSQL);
            p_final = 1;

        }else
        {
            //--Borrar las preguntas derivadas QUE NO CUMPLE CONDICION
            /*FOR t_pPREGUNTAS IN c_pPREGUNTAS LOOP
            DELETE FROM GIC_N_PREGUNTASDERIVADAS
            WHERE hog_codigo=pHOG_CODIGO
            AND pre_idpregunta=t_pPREGUNTAS.Pre_Idpregunta
            AND pre_idpreguntapadre=pId_PreguntaPadre
            AND per_idpersona=pPER_IDPERSONA
            AND ins_idinstrumento=pINS_IDINSTRUMENTO;
            COMMIT;
            END LOOP;
            END IF;*/
            for (int t_pPREGUNTAS = 0; t_pPREGUNTAS < c_pPREGUNTAS.size(); t_pPREGUNTAS++) {
                emc_pregunta_hijos pre_idpregunta = c_pPREGUNTAS.get(t_pPREGUNTAS);
                String[] parDelVal = {pHOG_CODIGO, pre_idpregunta.getPre_idpregunta(),
                        String.valueOf(pId_PreguntaPadre).toString(), String.valueOf(pPER_IDPERSONA).toString(),
                        String.valueOf(pINS_IDINSTRUMENTO).toString()};
                emc_preguntas_derivadas.deleteAll(emc_preguntas_derivadas.class,
                        "HOGCODIGO = ? AND PREIDPREGUNTA = ? AND  PREIDPREGUNTAPADRE = ?  AND PERIDPERSONA = ?" +
                                "AND INSIDINSTRUMENTO = ?", parDelVal);
            }

        }



    }


    public static void SP_FINALIZARCAPITULO(String pcodHogar, Integer pidTema, String pusuario) {
        String[] parCap = {pcodHogar, pidTema.toString()};
        emc_capitulos_terminados.deleteAll(emc_capitulos_terminados.class, "HOGCODIGO = ? AND TEMIDTEMA = ?", parCap);

        emc_capitulos_terminados newCap = new emc_capitulos_terminados();
        newCap.setHog_codigo(pcodHogar);
        newCap.setTem_idtema(pidTema);
        newCap.setUsu_fechacreacion(general.fechaActual());
        newCap.setUsu_usuariocreacion(pusuario);
        newCap.save();
    }

    public static void reiniciarCapitulo(String hogCodigo, Integer TemIDTema, Integer pINS_IDINSTRUMENTO)
    {
        String[] parRes = {TemIDTema.toString(),hogCodigo,  pINS_IDINSTRUMENTO.toString(),hogCodigo};
        emc_validadores_persona.deleteAll(emc_validadores_persona.class,
                "VALIDVALIDADOR IN (" +
                        "    SELECT INR.VALIDVALIDADOR" +
                        "      FROM  EMCPREGUNTASINSTRUMENTO INP,  EMCRESPUESTAS RES,  EMCRESPUESTASINSTRUMENTO INR," +
                        "      EMCVALIDADORESPERSONA VDP" +
                        "      WHERE  INP.PREIDPREGUNTA=RES.PREIDPREGUNTA AND  RES.RESIDRESPUESTA=INR.RESIDRESPUESTA AND" +
                        "      INR.VALIDVALIDADOR=VDP.VALIDVALIDADOR AND INP.TEMIDTEMA = ? AND VDP.HOGCODIGO = ? AND INP.INSIDINSTRUMENTO = ?)  AND HOGCODIGO = ?", parRes);


        emc_respuestas_encuesta.deleteAll(emc_respuestas_encuesta.class,
                "RESIDRESPUESTA IN (" +
                        " SELECT RESIDRESPUESTA FROM EMCRESPUESTAS WHERE PREIDPREGUNTA IN (" +
                        " SELECT PREIDPREGUNTA FROM EMCPREGUNTASINSTRUMENTO  T WHERE T.TEMIDTEMA ='"+ TemIDTema.toString() +"')) AND HOGCODIGO ='"+hogCodigo+"' AND INSIDINSTRUMENTO = "+pINS_IDINSTRUMENTO);



        emc_capitulos_terminados.deleteAll(emc_capitulos_terminados.class,
                "TEMIDTEMA = '"+ TemIDTema.toString() +"' AND HOGCODIGO = '"+hogCodigo+"'");

        emc_preguntas_derivadas.deleteAll(emc_preguntas_derivadas.class,
                "TEMIDTEMA = '"+ TemIDTema.toString() +"' AND HOGCODIGO = '"+hogCodigo+"' AND INSIDINSTRUMENTO ="+pINS_IDINSTRUMENTO );

    }

    //13/02/2020
    public static String SP_INSERTA_SOPORTES(String id_temporal ,String pHOG_CODIGO,  String pGuid,   String pARC_URL,   String pUSU_CREACION,  String pTipopersona ){
        String  pSalida = "";
        emc_gic_archivo_soportes objgas = new emc_gic_archivo_soportes(id_temporal,pHOG_CODIGO,pGuid,pARC_URL,pUSU_CREACION,pTipopersona);
        objgas.save();
        return pSalida;
    }

    public static void SP_UPDATE_SOPORTES(){
        int y = 0;

    }

    public static void CERRAR_ENCUESTA(){
        int y = 0;

    }

    public int FN_RETORNA_TIPO_PERSONA(){
        return 0;
    }

    public void SP_INS_ETNIA_ARES(){
        int y = 0;
    }

    public void SP_CONSTANCIA(){
        int y = 0;
    }

    public void SP_INSERTA_CONSTA_FIRMADA_SAAH(){
        int y = 0;
    }

    public int FN_GET_TIPOPERSONA(){
        return 0;
    }

    public int FN_GET_TOTALCUARTOSXFAMILIA(){
        return 0;
    }
    public int FN_GET_HOGAR_CERRAD_CONSTANCIA(){
        return 0;
    }
    /////////////////13/02/2020
}