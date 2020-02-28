package co.com.rni.encuestadormovil.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;


import co.com.rni.encuestadormovil.R;
import co.com.rni.encuestadormovil.model.emc_resumen_encuestas_x_usuario;
import co.com.rni.encuestadormovil.model.itemEncuesta;
import co.com.rni.encuestadormovil.model.item_resumen_encuestas;
import co.com.rni.encuestadormovil.util.GenerarPDFActivity;

public class listaResumenEncuestasAdapter extends ArrayAdapter<emc_resumen_encuestas_x_usuario> {

    private ArrayList<emc_resumen_encuestas_x_usuario> items;
    private Context context;


    public listaResumenEncuestasAdapter(Context context, ArrayList<emc_resumen_encuestas_x_usuario> lista) {
        super(context, 0, lista);
        this.items = lista;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)parent.getContext().
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_resumen_encuestas_por_usuario, null);
        }

        TextView idusuraio = (TextView) convertView.findViewById(R.id.idUsuario);
        TextView idestado = (TextView) convertView.findViewById(R.id.idEstado);
        TextView idtotal = (TextView) convertView.findViewById(R.id.idTotal);


        final emc_resumen_encuestas_x_usuario datosEncuesta = (emc_resumen_encuestas_x_usuario) items.get(position);
        idusuraio.setText(datosEncuesta.getUsuario());
        idestado.setText(datosEncuesta.getEstado());
        idtotal.setText(datosEncuesta.getTotal());

        return convertView;

    }

    public void actualizarRegistros(ArrayList<emc_resumen_encuestas_x_usuario> lista){
        this.items.clear();
        this.items.addAll(lista);
        notifyDataSetChanged();
    }

}
