package co.com.rni.encuestadormovil.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.github.johnkil.print.PrintView;

import java.util.ArrayList;

import co.com.rni.encuestadormovil.R;
import co.com.rni.encuestadormovil.model.*;
import co.com.rni.encuestadormovil.util.GenerarPDFActivity;

/**
 * Created by javierperez on 15/11/15.
 */
public class listaEncuestasAdapter extends ArrayAdapter<itemEncuesta> {
    private ArrayList<itemEncuesta> items;
    private listaEncuestasResponse callback;
    private Context context;

    public listaEncuestasAdapter(Context context, ArrayList<itemEncuesta> lista, listaEncuestasResponse callback) {
        super(context, 0, lista);
        this.items = lista;
        this.callback = callback;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)parent.getContext().
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.encuesta_item, null);
        }

        TextView tvFecha = (TextView) convertView.findViewById(R.id.tvFecha);
        TextView tvHogar = (TextView) convertView.findViewById(R.id.tvHogar);
        TextView tvIDPersona = (TextView) convertView.findViewById(R.id.tvIDPersona);
        TextView tvEstado = (TextView) convertView.findViewById(R.id.tvEstado);
        //TextView tvPDF = (TextView) convertView.findViewById(R.id.tvPDF);

        final itemEncuesta datosEncuesta = (itemEncuesta) items.get(position);
        tvFecha.setText(datosEncuesta.getFecha());
        tvHogar.setText(datosEncuesta.getIdHogar());
        tvIDPersona.setText(datosEncuesta.getIdPersona());
        tvEstado.setText(datosEncuesta.getEstado());
        //tvPDF.setText(datosEncuesta.getPdf());

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(datosEncuesta.getEstado().equals("Anulada"))
                {
                    Toast.makeText(getContext().getApplicationContext(), "La encuesta se encuentra Anulada", Toast.LENGTH_SHORT).show();
                }
                else if(datosEncuesta.getEstado().equals("Cerrada"))
                {
                    GenerarPDFActivity objGenerarPDFActivity = new GenerarPDFActivity(getContext().getResources());
                    objGenerarPDFActivity.generarPDF(datosEncuesta);
                    Toast.makeText(getContext().getApplicationContext(), "La encuesta se encuentra Cerrada", Toast.LENGTH_SHORT).show();
                }
                else if(datosEncuesta.getEstado().equals("TRANSMITIDA"))
                {
                    GenerarPDFActivity objGenerarPDFActivity = new GenerarPDFActivity(getContext().getResources());
                    objGenerarPDFActivity.generarPDF(datosEncuesta);
                    Toast.makeText(getContext().getApplicationContext(), "La encuesta ya fue transferida al servidor, se genero un reporte PDF en la carpeta descargas", Toast.LENGTH_SHORT).show();
                }else
                    callback.onHogarSelected(datosEncuesta.getIdHogar());
                    return false;

            }
        });
        return convertView;
    }

    public void actualizarRegistros(ArrayList<itemEncuesta> lista){
        this.items.clear();
        this.items.addAll(lista);
        notifyDataSetChanged();
    }
}
