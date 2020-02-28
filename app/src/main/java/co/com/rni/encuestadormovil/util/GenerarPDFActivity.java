package co.com.rni.encuestadormovil.util;

/**
 * Created by ASUS on 14/08/2017.
 */


import co.com.rni.encuestadormovil.R;
import co.com.rni.encuestadormovil.model.emc_miembros_hogar;
import co.com.rni.encuestadormovil.model.emc_preguntas;
import co.com.rni.encuestadormovil.model.emc_respuestas;
import co.com.rni.encuestadormovil.model.emc_respuestas_encuesta;
import co.com.rni.encuestadormovil.model.emc_temas;
import co.com.rni.encuestadormovil.model.itemEncuesta;
import harmony.java.awt.Color;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.DottedLineSeparator;
import com.lowagie.text.pdf.draw.LineSeparator;

public class GenerarPDFActivity extends AppCompatActivity {


    private final static String NOMBRE_DIRECTORIO = "MiPdf";
    private final static String NOMBRE_DOCUMENTO = "prueba.pdf";
    private final static String ETIQUETA_ERROR = "ERROR";
    Resources resources;
    public List<emc_respuestas_encuesta> emc_respuestas_encuestasList = null;

    public GenerarPDFActivity(){

    }
    public GenerarPDFActivity(Resources resources){
        this.resources = resources;
    }

    public  void generarPDF(itemEncuesta itemEncuesta){

        // Creamos el documento.
        Document documento = new Document();

        try {

            // Creamos el fichero con el nombre que deseemos.
            File f = crearFichero(itemEncuesta.getIdHogar().toString()+".pdf");

            // Creamos el flujo de datos de salida para el fichero donde
            // guardaremos el pdf.
            FileOutputStream ficheroPdf = new FileOutputStream(
                    f.getAbsolutePath());

            // Asociamos el flujo que acabamos de crear al documento.
            PdfWriter writer = PdfWriter.getInstance(documento, ficheroPdf);

            // Incluimos el píe de página y una cabecera
            HeaderFooter cabecera = new HeaderFooter(new Phrase(
                    "Informe generado desde la ficha de caracterización móvil"), false);
            HeaderFooter pie = new HeaderFooter(new Phrase(
                    itemEncuesta.getIdHogar().toString()), false);

            documento.setHeader(cabecera);
            documento.setFooter(pie);

            // Abrimos el documento.
            documento.open();

            // Insertamos una imagen que se encuentra en los recursos de la
            // aplicación.
            Bitmap bitmap = BitmapFactory.decodeResource(resources,
                    R.drawable.encabezadoban);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            Image imagen = Image.getInstance(stream.toByteArray());
            imagen.scaleAbsoluteWidth(500f);
            imagen.scaleAbsoluteHeight(100f);
            imagen.setAlignment(Element.ALIGN_CENTER);
            documento.add(imagen);

            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA, 20,
                    Font.BOLD, Color.BLACK);

            // Añadimos un título con la fuente por defecto.
            Paragraph prefaceTitulo = new Paragraph("INFORME ENTREVISTA HOGAR '"+itemEncuesta.getIdHogar().toString()+"'",fontTitulo);
            prefaceTitulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(prefaceTitulo);

            LineSeparator ls = new LineSeparator();
            documento.add(new Chunk(ls));


            // Añadimos un título con una fuente personalizada.
            /*Font font = FontFactory.getFont(FontFactory.HELVETICA, 28,
                    Font.BOLD, Color.RED);
            documento.add(new Paragraph("INFORME ENTREVSITA HOGAR '"+itemEncuesta.getIdHogar().toString()+"'", font));*/

            Font fontSubtitulo = FontFactory.getFont(FontFactory.HELVETICA, 13,
                    Font.BOLD, Color.BLACK);

            Paragraph prefaceSubtitulo = new Paragraph("Miembros hogar",fontSubtitulo);
            prefaceSubtitulo.setAlignment(Element.ALIGN_LEFT);
            documento.add(prefaceSubtitulo);
            documento.add(Chunk.NEWLINE);

            // Insertamos una tabla.
            PdfPTable tablaMiembrosHogar = new PdfPTable(3);
            tablaMiembrosHogar.addCell("PERSONA");
            tablaMiembrosHogar.addCell("DOCUMENTO");
            tablaMiembrosHogar.addCell("ESTADO");
            List<emc_miembros_hogar> emc_miembros_hogarList = emc_miembros_hogar.find(emc_miembros_hogar.class,"HOGCODIGO = ?",itemEncuesta.getIdHogar());
            for(int contMiembros = 0; contMiembros < emc_miembros_hogarList.size(); contMiembros++ ){
                emc_miembros_hogar tmMiembrosH = emc_miembros_hogarList.get(contMiembros);
                tablaMiembrosHogar.addCell(tmMiembrosH.getNombre1()+" "+tmMiembrosH.getNombre2()+" "+tmMiembrosH.getApellido1()+" "+tmMiembrosH.getApellido2());
                tablaMiembrosHogar.addCell(tmMiembrosH.getDocumento());
                tablaMiembrosHogar.addCell(tmMiembrosH.getEstado());
            }
            documento.add(tablaMiembrosHogar);
            documento.add(Chunk.NEWLINE);

            Paragraph prefacePreguntas = new Paragraph("Respuestas por preguntas ",fontSubtitulo);
            prefacePreguntas.setAlignment(Element.ALIGN_LEFT);
            documento.add(prefacePreguntas);
            documento.add(Chunk.NEWLINE);

            emc_respuestas_encuestasList = emc_respuestas_encuesta.find(emc_respuestas_encuesta.class, "HOGCODIGO = ?", itemEncuesta.getIdHogar().toString());
            // Insertamos una tabla.
            PdfPTable tablaRespuestas = new PdfPTable(3);
            tablaRespuestas.addCell("PERSONA");
            tablaRespuestas.addCell("PREGUNTA");
            tablaRespuestas.addCell("TEXTO RESPUESTA");
            for(int contPregunta = 0; contPregunta < emc_respuestas_encuestasList.size(); contPregunta++){
                emc_respuestas_encuesta tmPreguntas = emc_respuestas_encuestasList.get(contPregunta);
                List<emc_respuestas> emc_respuestasList =   emc_respuestas.find(emc_respuestas.class,"RESIDRESPUESTA = ?",tmPreguntas.getRes_idrespuesta().toString());
                emc_respuestas tmRespuestas = emc_respuestasList.get(0);
                String[] parVRI = {itemEncuesta.getIdHogar(),tmPreguntas.getPer_idpersona()};
                List<emc_preguntas> emc_preguntasList = emc_preguntas.find(emc_preguntas.class,"PREIDPREGUNTA = ?", tmRespuestas.getPre_idpregunta());
                emc_preguntas idPregunta = emc_preguntasList.get(0);
                List<emc_miembros_hogar> listaMH = emc_miembros_hogar.find(emc_miembros_hogar.class,"HOGCODIGO = ? AND PERIDPERSONA = ?",parVRI);
                emc_miembros_hogar temMH = listaMH.get(0);
                tablaRespuestas.addCell(temMH.getNombre1()+" "+temMH.getNombre2()+" "+temMH.getApellido1()+" "+temMH.getApellido2());
                tablaRespuestas.addCell(idPregunta.getPre_pregunta());
                if(tmPreguntas.getRxp_textorespuesta().toString().trim().equals("")){
                    tablaRespuestas.addCell(tmRespuestas.getRes_respuesta());
                }else{
                    tablaRespuestas.addCell(tmPreguntas.getRxp_textorespuesta().toString().trim());
                }

            }
            documento.add(tablaRespuestas);

            // Agregar marca de agua
            /*font = FontFactory.getFont(FontFactory.HELVETICA, 42, Font.BOLD,
                    Color.GRAY);
            ColumnText.showTextAligned(writer.getDirectContentUnder(),
                    Element.ALIGN_CENTER, new Paragraph(
                            "amatellanes.wordpress.com", font), 297.5f, 421,
                    writer.getPageNumber() % 2 == 1 ? 45 : -45);*/

        } catch (DocumentException e) {

            Log.e(ETIQUETA_ERROR, e.getMessage());

        } catch (IOException e) {

            Log.e(ETIQUETA_ERROR, e.getMessage());

        } finally {

            // Cerramos el documento.
            documento.close();
        }
    }



    /**
     * Crea un fichero con el nombre que se le pasa a la función y en la ruta
     * especificada.
     *
     * @param nombreFichero
     * @return
     * @throws IOException
     */
    public static File crearFichero(String nombreFichero) throws IOException {
        File ruta = getRuta();
        File fichero = null;
        if (ruta != null)
            fichero = new File(ruta, nombreFichero);
        return fichero;
    }

    /**
     * Obtenemos la ruta donde vamos a almacenar el fichero.
     *
     * @return
     */
    public static File getRuta() {

        // El fichero será almacenado en un directorio dentro del directorio
        // Descargas
        File ruta = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            ruta = new File(
                    Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    NOMBRE_DIRECTORIO);

            if (ruta != null) {
                if (!ruta.mkdirs()) {
                    if (!ruta.exists()) {
                        return null;
                    }
                }
            }
        } else {
        }

        return ruta;
    }
}
